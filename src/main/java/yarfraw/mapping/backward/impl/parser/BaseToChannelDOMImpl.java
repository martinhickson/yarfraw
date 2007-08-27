package yarfraw.mapping.backward.impl.parser;

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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.parser.CoreRssElementEnum;
import yarfraw.io.parser.ToChannelDOMParser;

/**
 * This class is not thread safe.
 * @author jliang
 *
 */
abstract class BaseToChannelDOMImpl implements ToChannelDOMParser{

  private static final EnumSet<CoreRssElementEnum> CHANNEL_SET = EnumSet.of(
      Channel,Channel_title,Channel_link, Channel_description,Channel_language,  
      Channel_pubdate,Channel_ttl,Channel_image,Channel_textinput,Channel_category);
  private static final EnumSet<CoreRssElementEnum> ITEM_SET = EnumSet.complementOf(CHANNEL_SET);
  protected EnumSet<CoreRssElementEnum> _elementsOfInterest;
  
  //use hash map for lookup so we dont need to iterate thru the list every time
  protected final Map<QName, CoreRssElementEnum> _elementsOfInterestMap = new HashMap<QName, CoreRssElementEnum>();
  
  public BaseToChannelDOMImpl() {
    super();
    setElementsOfInterest(EnumSet.allOf(CoreRssElementEnum.class));
  }
  public BaseToChannelDOMImpl(
      EnumSet<CoreRssElementEnum> elementsOfInterest) {
    super();
    setElementsOfInterest(elementsOfInterest);
  }
  
  abstract public Channel execute(Document doc) throws YarfrawException;
  abstract public FeedFormat getFormat();
  
  public EnumSet<CoreRssElementEnum> getElementsOfInterest() {
    return EnumSet.copyOf(_elementsOfInterest);
  }
  
  public void setElementsOfInterest(EnumSet<CoreRssElementEnum> elementsOfInterest) {
    //make sure if sub element is of interest, the parent is also in the interest set
    for(CoreRssElementEnum e : elementsOfInterest){
      if(CHANNEL_SET.contains(e)) 
          if(!elementsOfInterest.contains(Channel)){
            throw new IllegalArgumentException("ElementsOfInterest Set contains sub-element of Channel, but does not contain Channel");
          }else{
            break;
          }
      }
  
    for(CoreRssElementEnum e : elementsOfInterest){
      if(ITEM_SET.contains(e)){
        if(!elementsOfInterest.contains(Channel)){
          throw new IllegalArgumentException("ElementsOfInterest Set contains sub-element of Item, but does not contain Item");
        }else{
          break;
        }
      }      
    }
      
    _elementsOfInterest = EnumSet.copyOf(elementsOfInterest);
    populateQNameMap();
  }
  
  private void populateQNameMap(){
    _elementsOfInterestMap.clear();
    for(CoreRssElementEnum e : _elementsOfInterest){
      _elementsOfInterestMap.put(e.getName(getFormat()), e);
    }
    
  }  
}

