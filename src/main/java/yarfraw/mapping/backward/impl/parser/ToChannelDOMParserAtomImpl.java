package yarfraw.mapping.backward.impl.parser;

import static yarfraw.core.datamodel.FeedFormat.ATOM10;
import static yarfraw.mapping.AttributesQName.ATOM10_CATEGORY_SCHEME;
import static yarfraw.mapping.AttributesQName.ATOM10_CATEGORY_TERM;
import static yarfraw.mapping.AttributesQName.ATOM10_ENTRY_SRC;
import static yarfraw.mapping.AttributesQName.ATOM10_ENTRY_TYPE;
import static yarfraw.mapping.AttributesQName.ATOM10_LINK_HREF;
import static yarfraw.mapping.AttributesQName.ATOM10_LINK_LENGTH;
import static yarfraw.mapping.AttributesQName.ATOM10_LINK_REL;
import static yarfraw.mapping.AttributesQName.ATOM10_LINK_TITLE;
import static yarfraw.mapping.AttributesQName.ATOM10_LINK_TYPE;
import static yarfraw.mapping.CoreRssElementEnum.Atom_Entry_Content;
import static yarfraw.mapping.CoreRssElementEnum.Channel_category;
import static yarfraw.mapping.CoreRssElementEnum.Channel_description;
import static yarfraw.mapping.CoreRssElementEnum.Channel_image;
import static yarfraw.mapping.CoreRssElementEnum.Channel_link;
import static yarfraw.mapping.CoreRssElementEnum.Channel_pubdate;
import static yarfraw.mapping.CoreRssElementEnum.Channel_title;
import static yarfraw.mapping.CoreRssElementEnum.Item;
import static yarfraw.mapping.CoreRssElementEnum.Item_author;
import static yarfraw.mapping.CoreRssElementEnum.Item_category;
import static yarfraw.mapping.CoreRssElementEnum.Item_description;
import static yarfraw.mapping.CoreRssElementEnum.Item_guid;
import static yarfraw.mapping.CoreRssElementEnum.Item_link;
import static yarfraw.mapping.CoreRssElementEnum.Item_pubdate;
import static yarfraw.mapping.CoreRssElementEnum.Item_title;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

import yarfraw.core.datamodel.AtomContent;
import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.AtomLink;
import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.core.datamodel.AtomTextAttributes.TextType;
import yarfraw.mapping.CoreRssElementEnum;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.DOMSerializer;
import yarfraw.utils.NodeProcessor;
import yarfraw.utils.XMLUtils;
/**
 * This class is not thread safe.
 * @author jliang
 *
 */
public class ToChannelDOMParserAtomImpl extends BaseToChannelDOMImpl{
  private Map<CoreRssElementEnum, Node> _elementsNodeMap = new HashMap<CoreRssElementEnum, Node>();
  private static final DOMSerializer DOM_SERIALIZER = new DOMSerializer();
 
  public Channel execute(Document doc) throws YarfrawException {
    Channel ret = new Channel();
    _elementsNodeMap.clear();
    Node feed = doc.getDocumentElement();
    XMLUtils.traverseTreeDepthFirst(feed, 
        new FeedProcessor(_elementsOfInterestMap, ret));
    return ret;
  }

  @Override
  public FeedFormat getFormat() {
    return ATOM10;
  }

  /*
   * processor for the feed element
   */
  private static class FeedProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Channel _channel;
    public FeedProcessor(
        Map<QName, CoreRssElementEnum> elementsOfInterestMap,
        Channel channel) {
      super();
      _elementsOfInterestMap = elementsOfInterestMap;
      _channel = channel;
    }
    public void postProcess(Node node) {}

    public short preProcess(Node node) {
      if(node.getNodeType() != Node.ELEMENT_NODE){
        return NodeFilter.FILTER_REJECT;
      }
      QName name = XMLUtils.getQName(node);
      CoreRssElementEnum element = _elementsOfInterestMap.get(name);
      
      if(element == Item){
        Item item = new Item();
        XMLUtils.traverseTreeDepthFirst(node, new EntryProcessor(_elementsOfInterestMap, item));
        _channel.additem(item);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_title || element == Item_title){
        _channel.setTitle(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_description){
        _channel.setDescription(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        _channel.addCategory(toCategory(node));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_image){
        try {
          _channel.setImage(new Image().setUrl(node.getTextContent()));
        }
        catch (URISyntaxException e) {
          //it's not required to be a valid link, but generally it should be
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_pubdate){
        if(StringUtils.isNotBlank(node.getTextContent())){
          _channel.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }//no text input for atom10
      else if(element == Channel_link || element == Item_link){
        _channel.addAtomLink(toAtomLink(node));
        return NodeFilter.FILTER_REJECT;
      }
      return NodeFilter.FILTER_ACCEPT;
    }
  }


  /*
   * Process entry element
   */
  private static class EntryProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Item _item;
    public EntryProcessor(Map<QName, CoreRssElementEnum> elementsOfInterestMap, Item item){
      _item = item;
      _elementsOfInterestMap = elementsOfInterestMap;
    }
    public void postProcess(Node node) {}

    public short preProcess(Node node) {
      if(node.getNodeType() != Node.ELEMENT_NODE){
        return NodeFilter.FILTER_REJECT;
      }
      QName name = XMLUtils.getQName(node);
      CoreRssElementEnum element = _elementsOfInterestMap.get(name);
      
      if(element == Channel_title || element == Item_title){
        _item.setTitle(StringUtils.trim(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_description){
        _item.setDescription(StringUtils.trim(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Atom_Entry_Content){
        //this is tricky
        AtomContent content = new AtomContent();
        content.setSrc(XMLUtils.getAttributeValue(node, ATOM10_ENTRY_SRC.getLocalPart()));
        String type = XMLUtils.getAttributeValue(node, ATOM10_ENTRY_TYPE.getLocalPart());
        content.setType(type == null ? TextType.text: TextType.valueOf(type));
        if(content.getType() == TextType.xhtml){
          StringWriter writer = new StringWriter();
          try {
            DOM_SERIALIZER.serializeNode(node, writer, "");
          } catch (IOException e) {
            //FIXME: log warning
          }
          content.addContentText(writer.toString());
          _item.setAtomContent(content);
        }else{
          content.addContentText(node.getTextContent());
        }
        _item.setAtomContent(content);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){  
        Category cat = new Category();
        cat.setCategory(XMLUtils.getAttributeValue(node, ATOM10_CATEGORY_TERM.getLocalPart()));
        cat.setDomain(XMLUtils.getAttributeValue(node, ATOM10_CATEGORY_SCHEME.getLocalPart()));
        _item.addCategory(cat);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_pubdate){
        if(StringUtils.isNotBlank(node.getTextContent())){
          _item.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_link || element == Item_link){
        _item.addAtomLink(toAtomLink(node));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_author){
        _item.setAuthor(StringUtils.trim(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_guid){
        _item.setAtomId(new AtomId(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }
      return NodeFilter.FILTER_ACCEPT;
    }
    
  }
  
  private static AtomLink toAtomLink(Node node){
    AtomLink link = new AtomLink().setHref(XMLUtils.getAttributeValue(node, ATOM10_LINK_HREF))
    .setHreflang(XMLUtils.getAttributeValue(node, ATOM10_LINK_HREF))
    .setRel(XMLUtils.getAttributeValue(node, ATOM10_LINK_REL))
    .setType(XMLUtils.getAttributeValue(node, ATOM10_LINK_TYPE))
    .setTitle(XMLUtils.getAttributeValue(node, ATOM10_LINK_TITLE));
    String length = XMLUtils.getAttributeValue(node, ATOM10_LINK_LENGTH);
    if(length != null){
      link.setLength(Integer.valueOf(length));
    }
    return link;
  }
  
  private static Category toCategory(Node node){
    return new Category().setCategory(XMLUtils.getAttributeValue(node, ATOM10_CATEGORY_TERM.getLocalPart()))
                          .setDomain(XMLUtils.getAttributeValue(node, ATOM10_CATEGORY_SCHEME.getLocalPart()));
  }
}

