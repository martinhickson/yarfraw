package yarfraw.mapping.backward.impl.parser;

import static yarfraw.core.datamodel.FeedFormat.RSS20;
import static yarfraw.io.parser.AttributesQName.RSS20_CATEGORY_DOMAIN;
import static yarfraw.io.parser.AttributesQName.RSS20_ENCLOSURE_LENGTH;
import static yarfraw.io.parser.AttributesQName.RSS20_ENCLOSURE_TYPE;
import static yarfraw.io.parser.AttributesQName.RSS20_ENCLOSURE_URL;
import static yarfraw.io.parser.AttributesQName.RSS20_ISPERMALINK;
import static yarfraw.io.parser.CoreRssElementEnum.Channel;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_category;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_description;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_image;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_language;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_link;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_pubdate;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_textinput;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_title;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_ttl;
import static yarfraw.io.parser.CoreRssElementEnum.Item;
import static yarfraw.io.parser.CoreRssElementEnum.Item_Encoded_Content;
import static yarfraw.io.parser.CoreRssElementEnum.Item_author;
import static yarfraw.io.parser.CoreRssElementEnum.Item_category;
import static yarfraw.io.parser.CoreRssElementEnum.Item_description;
import static yarfraw.io.parser.CoreRssElementEnum.Item_enclosure;
import static yarfraw.io.parser.CoreRssElementEnum.Item_guid;
import static yarfraw.io.parser.CoreRssElementEnum.Item_link;
import static yarfraw.io.parser.CoreRssElementEnum.Item_pubdate;
import static yarfraw.io.parser.CoreRssElementEnum.Item_title;
import static yarfraw.io.parser.ElementQName.RSS20_CHANNEL;
import static yarfraw.io.parser.ElementQName.RSS20_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS20_LINK;
import static yarfraw.io.parser.ElementQName.RSS20_NAME;
import static yarfraw.io.parser.ElementQName.RSS20_TITLE;
import static yarfraw.io.parser.ElementQName.RSS20_URL;
import static yarfraw.io.parser.ElementQName.RSS20_WIDTH;
import static yarfraw.mapping.backward.impl.parser.ParserUtils.getStringContent;

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

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Guid;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.TextInput;
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
public class ToChannelDOMParserRss20Impl extends BaseToChannelDOMImpl{
 
  private static final Log LOG = LogFactory.getLog(ToChannelDOMParserRss20Impl.class);
  public ToChannelDOMParserRss20Impl() {
    super();
  }
  
  public ToChannelDOMParserRss20Impl(
      EnumSet<CoreRssElementEnum> elementsOfInterest) {
    super(elementsOfInterest);
  }
  
  //there are quite a bit of duplicate code with Rss 10 impl. The original thought was to make
  //one parser class to handle all format, that's the why the code is set up the way it is right now,
  //but it turns out that it will make the code very confusing to read, so i decided to separate them into one for each format
  public Channel execute(Document doc) throws YarfrawException {
    Channel ret = new Channel();
    Node channel = XMLUtils.getChildrenNodeByName(doc.getDocumentElement(), RSS20_CHANNEL.getLocalPart());    
    XMLUtils.traverseTreeDepthFirst(channel, new ChannelProcessor(_elementsOfInterestMap, _elementsOfInterest, ret));
    return ret;
  }

  private static class ItemProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Item _item;
    EnumSet<CoreRssElementEnum> _elementsOfInterest;
    public ItemProcessor(
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
      }else if(element == Item_guid){
        Guid guid = new Guid();
        guid.setGuid(node.getTextContent());
        String isPermaLink = XMLUtils.getAttributeValue(node, RSS20_ISPERMALINK.getLocalPart());
        if(isPermaLink != null){
          guid.setPermaLink(Boolean.valueOf(isPermaLink.trim()));
        }
        _item.setGuid(guid);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_enclosure){
        Enclosure en = new Enclosure();
        try {
          en.setUrl(XMLUtils.getAttributeValue(node, RSS20_ENCLOSURE_URL.getLocalPart()));
        } catch (URISyntaxException e) {
          LOG.warn("Unable to parse url attribute under <enclosure>");
        }
        String length = XMLUtils.getAttributeValue(node, RSS20_ENCLOSURE_LENGTH.getLocalPart());
        if(length != null){
          en.setLength(Integer.parseInt(length.trim()));
        }else{
          LOG.warn("length attribute under <enclosure> required but does not exist");
        }
        en.setMimeType(XMLUtils.getAttributeValue(node, RSS20_ENCLOSURE_TYPE.getLocalPart()));
        en.setValue(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_title || element == Item_title){
        if(_elementsOfInterest.contains(Item_title)){
          _item.setTitle(node.getTextContent());
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_description || element == Item_description){
        if(_elementsOfInterest.contains(Item_description)){
          _item.setDescription(node.getTextContent());
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_author){
        _item.setAuthor(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        if(_elementsOfInterest.contains(Item_category)){
          _item.addCategory(toCategory(node));
          
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_pubdate || element == Item_pubdate){
        if(_elementsOfInterest.contains(Item_pubdate) && StringUtils.isNotBlank(node.getTextContent())){
          _item.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }
      else if(element == Channel_link || element == Item_link){
        if(_elementsOfInterest.contains(Item_link)){
          try {
            _item.setLink(node.getTextContent());
          }
          catch (Exception e) {
            LOG.warn("Unable to parse <link> element under <channel>", e); 
          }
        }
        
        return NodeFilter.FILTER_REJECT;
      }
      
      return NodeFilter.FILTER_REJECT;
    }
  }
  /*
   * processor for the Channel element
   */
  private static class ChannelProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Channel _channel;
    EnumSet<CoreRssElementEnum> _elementsOfInterest;
    public ChannelProcessor(
        Map<QName, CoreRssElementEnum> elementsOfInterestMap,
        EnumSet<CoreRssElementEnum> elementsOfInterest,
        Channel channel) {
      super();
      _elementsOfInterestMap = elementsOfInterestMap;
      _elementsOfInterest = elementsOfInterest;
      _channel = channel;
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
        XMLUtils.traverseTreeDepthFirst(node, new ItemProcessor(_elementsOfInterestMap, _elementsOfInterest, item));
        if(_elementsOfInterest.contains(Item_Encoded_Content)){
          Node encoded = XMLUtils.getChildrenNodeByName(node, Item_Encoded_Content.getRss20Name().getLocalPart());
          ParserUtils.setItemContent(encoded, item);
        }
        _channel.additem(item);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_language ){
        if(node.getTextContent() != null){
          _channel.setLanguage(new Locale(node.getTextContent()));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_title || element == Item_title){
        if(_elementsOfInterest.contains(Channel_title)){
          _channel.setTitle(node.getTextContent());
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_description || element == Item_description){
        if(_elementsOfInterest.contains(Channel_description)){
          _channel.setDescription(node.getTextContent());
        }        
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        if(_elementsOfInterest.contains(Channel_category)){
          _channel.addCategory(toCategory(node));
        }        
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_ttl){
        String ttl = node.getTextContent();
        if(ttl != null){
          _channel.setTtl(Integer.parseInt(ttl.trim()));          
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_textinput){
        TextInput textinput = new TextInput();
        textinput.setDescription(getStringContent(node, RSS20_DESCRIPTION.getLocalPart()));
        try {
          textinput.setLink(getStringContent(node, RSS20_LINK.getLocalPart()));
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <link> element under <textinput>", e);
        }
        textinput.setName(getStringContent(node, RSS20_NAME.getLocalPart()));
        textinput.setTitle(getStringContent(node, RSS20_TITLE.getLocalPart()));
        _channel.setTextInput(textinput);
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_image ){
        Image img = new Image();
        try {
          img.setUrl(getStringContent(node, RSS20_URL.getLocalPart()));
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <image>'s url element", e);
        }
        try {
          img.setLink(getStringContent(node, RSS20_LINK.getLocalPart()));
          
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <image>'s link element", e);
        }
        
        img.setDescription(getStringContent(node, RSS20_DESCRIPTION.getLocalPart()));
        img.setTitle(getStringContent(node, RSS20_TITLE.getLocalPart()));
        
        String width = getStringContent(node, RSS20_WIDTH.getLocalPart());
        String height = getStringContent(node, RSS20_WIDTH.getLocalPart());
        if(width != null){
          img.setWidth(Integer.parseInt(width.trim()));
        }
        if(height != null){
          img.setHeight(Integer.parseInt(height.trim()));
        }
        _channel.setImage(img);
        
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_pubdate || element == Item_pubdate){
        if(_elementsOfInterest.contains(Channel_pubdate) && StringUtils.isNotBlank(node.getTextContent())){
          _channel.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_link || element == Item_link){
        if(_elementsOfInterest.contains(Channel_link)){
          try {
            _channel.setLink(node.getTextContent());
          }
          catch (Exception e) {
            LOG.warn("Unable to parse <link> element under <channel>", e); 
          }
        }        
        return NodeFilter.FILTER_REJECT;
      }
      return NodeFilter.FILTER_REJECT;
    }
  }

  private static Category toCategory(Node node){
    return new Category().setCategory(node.getTextContent())
                          .setDomain(XMLUtils.getAttributeValue(node, RSS20_CATEGORY_DOMAIN.getLocalPart()));
  }
  
  @Override
  public FeedFormat getFormat() {
    return RSS20;
  }

}


