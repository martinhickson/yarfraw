package yarfraw.mapping;
import static yarfraw.mapping.ElementQName.ATOM10_AUTHOR;
import static yarfraw.mapping.ElementQName.ATOM10_CATEGORY;
import static yarfraw.mapping.ElementQName.ATOM10_ENTRY;
import static yarfraw.mapping.ElementQName.ATOM10_FEED;
import static yarfraw.mapping.ElementQName.ATOM10_ICON;
import static yarfraw.mapping.ElementQName.ATOM10_LINK;
import static yarfraw.mapping.ElementQName.ATOM10_PUBLISHED;
import static yarfraw.mapping.ElementQName.ATOM10_SUBTITLE;
import static yarfraw.mapping.ElementQName.*;
import static yarfraw.mapping.ElementQName.ATOM10_TITLE;
import static yarfraw.mapping.ElementQName.ATOM10_UPDATED;
import static yarfraw.mapping.ElementQName.RSS10_CHANNEL;
import static yarfraw.mapping.ElementQName.RSS10_CREATOR;
import static yarfraw.mapping.ElementQName.RSS10_DATE;
import static yarfraw.mapping.ElementQName.RSS10_DESCRIPTION;
import static yarfraw.mapping.ElementQName.RSS10_IMAGE;
import static yarfraw.mapping.ElementQName.RSS10_ITEM;
import static yarfraw.mapping.ElementQName.RSS10_LANGUAGE;
import static yarfraw.mapping.ElementQName.RSS10_LINK;
import static yarfraw.mapping.ElementQName.RSS10_SUBJECT;
import static yarfraw.mapping.ElementQName.RSS10_TEXTINPUT;
import static yarfraw.mapping.ElementQName.RSS10_TITLE;
import static yarfraw.mapping.ElementQName.RSS20_AUTHOR;
import static yarfraw.mapping.ElementQName.RSS20_CATEGORY;
import static yarfraw.mapping.ElementQName.RSS20_CHANNEL;
import static yarfraw.mapping.ElementQName.RSS20_DESCRIPTION;
import static yarfraw.mapping.ElementQName.RSS20_ENCLOSURE;
import static yarfraw.mapping.ElementQName.RSS20_GUID;
import static yarfraw.mapping.ElementQName.RSS20_IMAGE;
import static yarfraw.mapping.ElementQName.RSS20_ITEM;
import static yarfraw.mapping.ElementQName.RSS20_LINK;
import static yarfraw.mapping.ElementQName.RSS20_PUBDATE;
import static yarfraw.mapping.ElementQName.RSS20_TEXTINPUT;
import static yarfraw.mapping.ElementQName.RSS20_TITLE;
import static yarfraw.mapping.ElementQName.RSS20_TTL;

import java.util.EnumSet;

import javax.xml.namespace.QName;

import yarfraw.core.datamodel.FeedFormat;

/**
 * The elements that are common to all supported {@link FeedFormat}.
 * 
 * 
 * @author jliang
 *
 */
public enum CoreRssElementEnum {
  //XXX: using ">" to guaranteed that no actual name contains this character
  Channel(RSS10_CHANNEL, RSS20_CHANNEL, ATOM10_FEED),
  Channel_title(RSS10_TITLE, RSS20_TITLE, ATOM10_TITLE),
  Channel_link(RSS10_LINK, RSS20_LINK, ATOM10_LINK), 
  Channel_description(RSS10_DESCRIPTION, RSS20_DESCRIPTION, ATOM10_SUBTITLE),
  //language is an attribute instead of a tag in atom10
  Channel_language(RSS10_LANGUAGE, RSS20_DESCRIPTION, new QName(">")),  
  Channel_pubdate(RSS10_DATE, RSS20_PUBDATE, ATOM10_UPDATED),
  //only rss20 has ttl element
  Channel_ttl(new QName(""), RSS20_TTL, new QName(">")),
  Channel_image(RSS10_IMAGE, RSS20_IMAGE, ATOM10_ICON),
  Channel_textinput(RSS10_TEXTINPUT, RSS20_TEXTINPUT, new QName(">")),
  Channel_category(RSS10_SUBJECT, RSS20_CATEGORY, ATOM10_CATEGORY),
  
  Item(RSS10_ITEM, RSS20_ITEM, ATOM10_ENTRY),
  Item_title(RSS10_TITLE, RSS20_TITLE, ATOM10_TITLE),
  Item_link(RSS10_LINK, RSS20_LINK, ATOM10_LINK), 
  Item_description(RSS10_DESCRIPTION, RSS20_DESCRIPTION, ATOM10_SUMMARY),  
  Item_author(RSS10_CREATOR, RSS20_AUTHOR, ATOM10_AUTHOR), 
  Item_category(RSS10_SUBJECT, RSS20_CATEGORY, ATOM10_CATEGORY),
  Item_pubdate(RSS10_DATE, RSS20_PUBDATE, ATOM10_PUBLISHED),
  Item_enclosure(new QName(">"), RSS20_ENCLOSURE, new QName(">")),
  Item_guid(new QName(">"), RSS20_GUID, new QName(">")),
  Atom_Entry_Content(new QName(">"), new QName(">"), ATOM10_CONTENT);
  
  public  QName _rss10Name;
  public  QName _rss20Name;
  public  QName _atom10Name;  
  private CoreRssElementEnum(QName rss10, QName rss20, QName atom10){
    _rss10Name = rss10;
    _rss20Name = rss20;
    _atom10Name = atom10;
  }
  public QName getRss10Name() {
    return _rss10Name;
  }
  public void setRss10Name(QName rss10Name) {
    _rss10Name = rss10Name;
  }
  public QName getRss20Name() {
    return _rss20Name;
  }
  public void setRss20Name(QName rss20Name) {
    _rss20Name = rss20Name;
  }
  public QName getAtom10Name() {
    return _atom10Name;
  }
  public void setAtom10Name(QName atom10Name) {
    _atom10Name = atom10Name;
  }
  
  public static final EnumSet<CoreRssElementEnum> CHANNEL_SET = EnumSet.of(
      Channel,Channel_title,Channel_link, Channel_description,Channel_language,  
      Channel_pubdate,Channel_ttl,Channel_image,Channel_textinput,Channel_category);
  public static final EnumSet<CoreRssElementEnum> ITEM_SET = EnumSet.complementOf(CHANNEL_SET);
  
  public QName getName(FeedFormat format){
    if(format == FeedFormat.ATOM10){
      return _atom10Name;
    }else if(format == FeedFormat.RSS10){
      return _rss10Name;
    }else if(format == FeedFormat.RSS20){
      return _rss20Name;
    }else{
      throw new IllegalArgumentException("Unknown feed format: "+format);
    }
  }
  
}
