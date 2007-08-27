package yarfraw.mapping.backward.impl.parser;

import static yarfraw.core.datamodel.FeedFormat.RSS10;
import static yarfraw.io.parser.CoreRssElementEnum.Channel;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_category;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_description;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_image;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_link;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_pubdate;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_textinput;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_title;
import static yarfraw.io.parser.CoreRssElementEnum.Channel_ttl;
import static yarfraw.io.parser.CoreRssElementEnum.Item;
import static yarfraw.io.parser.CoreRssElementEnum.Item_author;
import static yarfraw.io.parser.CoreRssElementEnum.Item_category;
import static yarfraw.io.parser.CoreRssElementEnum.Item_description;
import static yarfraw.io.parser.CoreRssElementEnum.Item_link;
import static yarfraw.io.parser.CoreRssElementEnum.Item_pubdate;
import static yarfraw.io.parser.CoreRssElementEnum.Item_title;
import static yarfraw.io.parser.ElementQName.RSS10_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS10_LINK;
import static yarfraw.io.parser.ElementQName.RSS10_NAME;
import static yarfraw.io.parser.ElementQName.RSS10_TEXTINPUT;
import static yarfraw.io.parser.ElementQName.RSS10_TITLE;
import static yarfraw.io.parser.ElementQName.RSS10_UPDATEFREQUENCY;
import static yarfraw.io.parser.ElementQName.RSS10_UPDATEPERIOD;
import static yarfraw.io.parser.ElementQName.RSS10_URL;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.List;
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
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.TextInput;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.UpdatePeriodEnum;
import yarfraw.io.parser.CoreRssElementEnum;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.NodeProcessor;
import yarfraw.utils.XMLUtils;
/**
 * This class is not thread safe.
 * @author jliang
 *
 */
public class ToChannelDOMParserRss10Impl extends BaseToChannelDOMImpl{
 
  private static final Log LOG = LogFactory.getLog(ToChannelDOMParserRss10Impl.class);

  public ToChannelDOMParserRss10Impl() {
    super();
  }
  
  public ToChannelDOMParserRss10Impl(
      EnumSet<CoreRssElementEnum> elementsOfInterest) {
    super(elementsOfInterest);
  }
  
  public Channel execute(Document doc) throws YarfrawException {
    Channel ret = new Channel();
    Node rdf = doc.getDocumentElement();
    if(_elementsOfInterest.contains(Item)){
      List<Node> items = XMLUtils.getChildrenNodesByName(rdf, Item.getRss10Name().getLocalPart());
      for(Node inode : items){
        Item item = new Item();
        XMLUtils.traverseTreeDepthFirst(inode, new ItemProcessor(_elementsOfInterestMap, item));
        ret.additem(item);
      }
    }
    if(_elementsOfInterest.contains(Channel)){
      Node channel = XMLUtils.getChildrenNodeByName(rdf, Channel.getRss10Name().getLocalPart());
      XMLUtils.traverseTreeDepthFirst(channel, new ChannelProcessor(_elementsOfInterestMap, ret));
    }
    
    if(_elementsOfInterest.contains(Channel_image)){
      Node image = XMLUtils.getChildrenNodeByName(rdf, Channel_image.getRss10Name().getLocalPart());
      if(image != null){
        Image img = new Image();
        try {
          img.setUrl(getStringContent(image, RSS10_URL.getLocalPart()));
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <image>'s url element", e);
        }
        try {
          img.setLink(getStringContent(image, RSS10_LINK.getLocalPart()));
          
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <image>'s link element", e);
        }
        
        img.setDescription(getStringContent(image, RSS10_DESCRIPTION.getLocalPart()));
        img.setTitle(getStringContent(image, RSS10_TITLE.getLocalPart()));
      }
      
    }
    
    if(_elementsOfInterest.contains(Channel_ttl)){
      String frequency = getStringContent(rdf, RSS10_UPDATEFREQUENCY.getLocalPart());
      String period = getStringContent(rdf, RSS10_UPDATEPERIOD.getLocalPart());
      if(StringUtils.isNotBlank(period)){
        ret.setTtl(CommonUtils.calculateTtl(UpdatePeriodEnum.valueOf(StringUtils.trim(period)),
                new BigInteger(frequency)));
      }
    }
    
    if(_elementsOfInterest.contains(Channel_textinput)){
      Node n = XMLUtils.getChildrenNodeByName(rdf, RSS10_TEXTINPUT.getLocalPart());
      if(n!= null){
        TextInput textinput = new TextInput();
        textinput.setDescription(getStringContent(n, RSS10_DESCRIPTION.getLocalPart()));
        try {
          textinput.setLink(getStringContent(n, RSS10_LINK.getLocalPart()));
        }
        catch (URISyntaxException e) {
          LOG.warn("Unable to parse <link> element under <textinput>", e);
        }
        textinput.setName(getStringContent(n, RSS10_NAME.getLocalPart()));
        textinput.setTitle(getStringContent(n, RSS10_TITLE.getLocalPart()));
      }
    }
    
    return ret;
  }

  private static class ItemProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Item _item;
    public ItemProcessor(
            Map<QName, CoreRssElementEnum> elementsOfInterestMap,
            Item item) {
          super();
          _elementsOfInterestMap = elementsOfInterestMap;
          _item = item;
        }
    public void postProcess(Node node) {}
    public short preProcess(Node node) {
      if(node.getNodeType() != Node.ELEMENT_NODE){
        return NodeFilter.FILTER_REJECT;
      }
      QName name = XMLUtils.getQName(node);
      CoreRssElementEnum element = _elementsOfInterestMap.get(name);
      
      if(element == Channel_title || element == Item_title){
        _item.setTitle(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_description || element == Item_description){
        _item.setDescription(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Item_author){
        _item.setAuthor(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        _item.addCategory(new Category(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_pubdate || element == Item_pubdate){
        if(StringUtils.isNotBlank(node.getTextContent())){
          _item.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }
      else if(element == Channel_link || element == Item_link){
        try {
          _item.setLink(node.getTextContent());
        }
        catch (Exception e) {
          LOG.warn("Unable to parse <link> element under <channel>", e); 
        }
        return NodeFilter.FILTER_REJECT;
      }
      
      return 0;
    }
  }
  /*
   * processor for the Channel element
   */
  private static class ChannelProcessor implements NodeProcessor{
    Map<QName, CoreRssElementEnum> _elementsOfInterestMap;
    Channel _channel;
    public ChannelProcessor(
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
        LOG.warn("Unexpected element <item> under <channel>");
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_title || element == Item_title){
        _channel.setTitle(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_description || element == Item_description){
        System.out.println(node.getTextContent());
        _channel.setDescription(node.getTextContent());
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_category || element == Item_category){
        _channel.addCategory(new Category(node.getTextContent()));
        return NodeFilter.FILTER_REJECT;
      }else if(element == Channel_pubdate || element == Item_pubdate){
        if(StringUtils.isNotBlank(node.getTextContent())){
          _channel.setPubDate(CommonUtils.tryParseDate(StringUtils.trim(node.getTextContent())));
        }
        return NodeFilter.FILTER_REJECT;
      }
      else if(element == Channel_link || element == Item_link){
        try {
          _channel.setLink(node.getTextContent());
        }
        catch (Exception e) {
          LOG.warn("Unable to parse <link> element under <channel>", e); 
        }
        return NodeFilter.FILTER_REJECT;
      }
      return NodeFilter.FILTER_ACCEPT;
    }
  }
  
  private static String getStringContent(Node parent, String childLocalName){
    Node n = XMLUtils.getChildrenNodeByName(parent, childLocalName);
    if(n != null){
      return n.getTextContent();
    }
    return null;
  }
  
  @Override
  public FeedFormat getFormat() {
    return RSS10;
  }

}


