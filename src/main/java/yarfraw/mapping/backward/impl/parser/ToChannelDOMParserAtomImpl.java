package yarfraw.mapping.backward.impl.parser;

import static yarfraw.core.datamodel.FeedFormat.ATOM10;
import static yarfraw.io.parser.AttributesQName.ATOM10_CATEGORY_SCHEME;
import static yarfraw.io.parser.AttributesQName.ATOM10_CATEGORY_TERM;
import static yarfraw.io.parser.AttributesQName.ATOM10_LANGUAGE;
import static yarfraw.io.parser.AttributesQName.ATOM10_LINK_HREF;
import static yarfraw.io.parser.AttributesQName.ATOM10_LINK_HREF_LANG;
import static yarfraw.io.parser.AttributesQName.ATOM10_LINK_LENGTH;
import static yarfraw.io.parser.AttributesQName.ATOM10_LINK_REL;
import static yarfraw.io.parser.AttributesQName.ATOM10_LINK_TITLE;
import static yarfraw.io.parser.AttributesQName.ATOM10_LINK_TYPE;
import static yarfraw.io.parser.CoreRssElementEnum.Atom_Id;
import static yarfraw.io.parser.CoreRssElementEnum.Channel;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_category;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_description;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_image;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_language;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_link;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_pubdate;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_title;
import static yarfraw.io.parser.CoreRssElementEnum.Item;
import static yarfraw.io.parser.CoreRssElementEnum.Item_Encoded_Content;
import static yarfraw.io.parser.CoreRssElementEnum.Item_author;
import static yarfraw.io.parser.CoreRssElementEnum.Item_category;
import static yarfraw.io.parser.CoreRssElementEnum.Item_description;
import static yarfraw.io.parser.CoreRssElementEnum.Item_guid;
import static yarfraw.io.parser.CoreRssElementEnum.Item_link;
import static yarfraw.io.parser.CoreRssElementEnum.Item_pubdate;
import static yarfraw.io.parser.CoreRssElementEnum.Item_title;
import static yarfraw.io.parser.ElementQName.ATOM10_EMAIL;

import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.AtomLink;
import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.parser.CoreRssElementEnum;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.NodeProcessor;
import yarfraw.utils.XMLUtils;
/**
 * This class is not thread safe.
 * @author jliang
 *
 */
public class ToChannelDOMParserAtomImpl extends BaseToChannelDOMImpl{
 
  private static final Log LOG = LogFactory.getLog(ToChannelDOMParserAtomImpl.class);

  public ToChannelDOMParserAtomImpl() {
    super();
  }
  public ToChannelDOMParserAtomImpl(
      EnumSet<CoreRssElementEnum> elementsOfInterest) {
    super(elementsOfInterest);
  }
  
  public Channel execute(Document doc) throws YarfrawException {
    Channel ret = new Channel();
    Node feed = doc.getDocumentElement();
  //atom 1.0 has the language element as an attribute at the channel/feed level
    if(_elementsOfInterest.contains(Channel_language)){
      String lang = XMLUtils.getAttributeValue(feed, ATOM10_LANGUAGE.getLocalPart());
      if(lang != null){
        ret.setLanguage(new Locale(lang)); 
      }
    }
    XMLUtils.traverseTreeDepthFirst(feed, 
        new FeedProcessor(_elementsOfInterestMap, _elementsOfInterest, ret));
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
    EnumSet<CoreRssElementEnum> _elementsOfInterest;
    public FeedProcessor(
            Map<QName, CoreRssElementEnum> elementsOfInterestMap,
            EnumSet<CoreRssElementEnum> elementsOfInterest,
            Channel channel) {
          super();
          _elementsOfInterestMap = elementsOfInterestMap;
          _channel = channel;
          _elementsOfInterest = elementsOfInterest;
        }
    public void postProcess(Node node) {}

    public short preProcess(Node node) {
      if(node.getNodeType() != Node.ELEMENT_NODE){
        return NodeFilter.FILTER_REJECT;
      }
      QName name = XMLUtils.getQName(node);
      CoreRssElementEnum element = _elementsOfInterestMap.get(name);
      
      if(element == Channel){
        return NodeFilter.FILTER_ACCEPT;
      }else if(element == Item){
        Item item = new Item();
        XMLUtils.traverseTreeDepthFirst(node, new EntryProcessor(_elementsOfInterestMap, _elementsOfInterest, item));
        _channel.additem(item);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Atom_Id){
        _channel.setAtomId(new AtomId(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_title || element == Item_title){
        if(_elementsOfInterest.contains(Channel_title)){
          _channel.setTitle(node.getTextContent());
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_description){
        _channel.setDescription(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        if(_elementsOfInterest.contains(Channel_category)){
          _channel.addCategory(toCategory(node));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_image){
        try {
          _channel.setImage(new Image().setUrl(node.getTextContent()));
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <"+Channel_image.getName(ATOM10).getLocalPart()+">'s url content");
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_pubdate){
        if(StringUtils.isNotBlank(node.getTextContent())){
          _channel.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }//no text input for atom10
      else if(element == Channel_link || element == Item_link){
        if(_elementsOfInterest.contains(Channel_link)){
          _channel.addAtomLink(toAtomLink(node));
        }
        return NodeFilter.FILTER_REJECT;
      }
      return NodeFilter.FILTER_REJECT;
    }
  }


  /*
   * Process entry element
   */
  private static class EntryProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Item _item;
    EnumSet<CoreRssElementEnum> _elementsOfInterest;
    public EntryProcessor(
            Map<QName, CoreRssElementEnum> elementsOfInterestMap,
            EnumSet<CoreRssElementEnum> elementsOfInterest,
            Item item) {
          super();
          _elementsOfInterestMap = elementsOfInterestMap;
          _item = item;
          _elementsOfInterest = elementsOfInterest;
        }
    public void postProcess(Node node) {}

    public short preProcess(Node node) {
      if(node.getNodeType() != Node.ELEMENT_NODE){
        return NodeFilter.FILTER_REJECT;
      }
      QName name = XMLUtils.getQName(node);
      CoreRssElementEnum element = _elementsOfInterestMap.get(name);
      
      if(element == Item){
        return NodeFilter.FILTER_ACCEPT;
      }if(element == Channel_title || element == Item_title){
        if(_elementsOfInterest.contains(Item_title)){
          _item.setTitle(StringUtils.trim(node.getTextContent()));
        }        
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_description){
        if(_elementsOfInterest.contains(Item_description)){
          _item.setDescription(StringUtils.trim(node.getTextContent()));
        }        
        return NodeFilter.FILTER_REJECT;
      }else if(element == Atom_Id){
        _item.setAtomId(new AtomId(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_Encoded_Content){
        ParserUtils.setItemContent(node, _item);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        if(_elementsOfInterest.contains(Item_category)){
          _item.addCategory(toCategory(node));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_pubdate){
        if(StringUtils.isNotBlank(node.getTextContent())){
          _item.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_link || element == Item_link){
        if(_elementsOfInterest.contains(Item_link)){
          _item.addAtomLink(toAtomLink(node));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_author){
        Node email = XMLUtils.getChildrenNodeByName(node, ATOM10_EMAIL.getLocalPart());
        if(email != null){
          _item.setAuthor(email.getTextContent());
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_guid){
        _item.setAtomId(new AtomId(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }
      return NodeFilter.FILTER_REJECT;
    }
    
  }
  
  private static AtomLink toAtomLink(Node node){
    AtomLink link = new AtomLink().setHref(XMLUtils.getAttributeValue(node, ATOM10_LINK_HREF.getLocalPart()))
    .setHreflang(XMLUtils.getAttributeValue(node, ATOM10_LINK_HREF_LANG.getLocalPart()))
    .setRel(XMLUtils.getAttributeValue(node, ATOM10_LINK_REL.getLocalPart()))
    .setType(XMLUtils.getAttributeValue(node, ATOM10_LINK_TYPE.getLocalPart()))
    .setTitle(XMLUtils.getAttributeValue(node, ATOM10_LINK_TITLE.getLocalPart()));
    String length = XMLUtils.getAttributeValue(node, ATOM10_LINK_LENGTH.getLocalPart());
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


