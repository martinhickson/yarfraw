//package yarfraw.mapping.backward.impl;
//
//import static yarfraw.mapping.CoreRssElementEnum.Channel;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_category;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_description;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_image;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_language;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_link;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_pubdate;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_textinput;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_title;
//import static yarfraw.mapping.CoreRssElementEnum.Channel_ttl;
//
//import java.util.EnumSet;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.xml.namespace.QName;
//
//import org.w3c.dom.Document;
//
//import yarfraw.core.datamodel.Channel;
//import yarfraw.core.datamodel.FeedFormat;
//import yarfraw.core.datamodel.Item;
//import yarfraw.core.datamodel.YarfrawException;
//import yarfraw.mapping.CoreRssElementEnum;
//import yarfraw.mapping.backward.ToChannelDOM;
//
///**
// * This class is not thread safe.
// * @author jliang
// *
// */
//public class ToChannelDOMSimplifiedImpl implements ToChannelDOM{
//  private static final EnumSet<CoreRssElementEnum> CHANNEL_SET = EnumSet.of(
//      Channel,Channel_title,Channel_link, Channel_description,Channel_language,  
//      Channel_pubdate,Channel_ttl,Channel_image,Channel_textinput,Channel_category);
//  private static final EnumSet<CoreRssElementEnum> ITEM_SET = EnumSet.complementOf(CHANNEL_SET);
//  
//  protected EnumSet<CoreRssElementEnum> _elementsOfInterest;
//  protected FeedFormat _format;
//  protected Channel _channel = null;
//  protected Item _item = null;
//  //use hash map for lookup so we dont need to iterate thru the list every time
//  private final Map<QName, CoreRssElementEnum> _elementsOfInterestMap = new HashMap<QName, CoreRssElementEnum>();
//  
//  public ToChannelDOMSimplifiedImpl() {
//    super();
//    _elementsOfInterest = EnumSet.allOf(CoreRssElementEnum.class);
//  }
//  public ToChannelDOMSimplifiedImpl(
//      EnumSet<CoreRssElementEnum> elementsOfInterest) {
//    super();
//    setElementsOfInterest(elementsOfInterest);
//  }
//
//  public EnumSet<CoreRssElementEnum> getElementsOfInterest() {
//    return EnumSet.copyOf(_elementsOfInterest);
//  }
//
//  public Channel execute(Document doc) throws YarfrawException {
//
//    return null;
//  }
//  
//  public void setElementsOfInterest(EnumSet<CoreRssElementEnum> elementsOfInterest) {
//    //make sure if sub element is of interest, the parent is also in the interest set
//    for(CoreRssElementEnum e : elementsOfInterest){
//      if(CHANNEL_SET.contains(e)) 
//          if(!elementsOfInterest.contains(Channel)){
//            throw new IllegalArgumentException("ElementsOfInterest Set contains sub-element of Channel, but does not contain Channel");
//          }else{
//            break;
//          }
//      }
//  
//    for(CoreRssElementEnum e : elementsOfInterest){
//      if(ITEM_SET.contains(e)){
//        if(!elementsOfInterest.contains(Channel)){
//          throw new IllegalArgumentException("ElementsOfInterest Set contains sub-element of Item, but does not contain Item");
//        }else{
//          break;
//        }
//      }      
//    }
//      
//    _elementsOfInterest = EnumSet.copyOf(elementsOfInterest);
//    populateQNameMap();
//  }
//  
//  public void setFeedFormat(FeedFormat format) {
//    _format = format;
//    populateQNameMap();
//  }
//  
//  private void populateQNameMap(){
//    _elementsOfInterestMap.clear();
//    for(CoreRssElementEnum e : _elementsOfInterest){
//      _elementsOfInterestMap.put(e.getName(_format), e);
//    }
//  }  
//}
//
