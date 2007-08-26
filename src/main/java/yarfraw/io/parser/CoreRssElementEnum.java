package yarfraw.io.parser;
import static yarfraw.io.parser.ElementQName.ATOM10_AUTHOR;
import static yarfraw.io.parser.ElementQName.ATOM10_CATEGORY;
import static yarfraw.io.parser.ElementQName.ATOM10_CONTENT;
import static yarfraw.io.parser.ElementQName.ATOM10_ENTRY;
import static yarfraw.io.parser.ElementQName.ATOM10_FEED;
import static yarfraw.io.parser.ElementQName.ATOM10_ICON;
import static yarfraw.io.parser.ElementQName.ATOM10_ID;
import static yarfraw.io.parser.ElementQName.ATOM10_LINK;
import static yarfraw.io.parser.ElementQName.ATOM10_PUBLISHED;
import static yarfraw.io.parser.ElementQName.ATOM10_SUBTITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_SUMMARY;
import static yarfraw.io.parser.ElementQName.ATOM10_TITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_UPDATED;
import static yarfraw.io.parser.ElementQName.RSS10_CHANNEL;
import static yarfraw.io.parser.ElementQName.RSS10_CREATOR;
import static yarfraw.io.parser.ElementQName.RSS10_DATE;
import static yarfraw.io.parser.ElementQName.RSS10_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS10_IMAGE;
import static yarfraw.io.parser.ElementQName.RSS10_ITEM;
import static yarfraw.io.parser.ElementQName.RSS10_LANGUAGE;
import static yarfraw.io.parser.ElementQName.RSS10_LINK;
import static yarfraw.io.parser.ElementQName.RSS10_SUBJECT;
import static yarfraw.io.parser.ElementQName.RSS10_TEXTINPUT;
import static yarfraw.io.parser.ElementQName.RSS10_TITLE;
import static yarfraw.io.parser.ElementQName.RSS20_AUTHOR;
import static yarfraw.io.parser.ElementQName.RSS20_CATEGORY;
import static yarfraw.io.parser.ElementQName.RSS20_CHANNEL;
import static yarfraw.io.parser.ElementQName.RSS20_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS20_ENCLOSURE;
import static yarfraw.io.parser.ElementQName.RSS20_GUID;
import static yarfraw.io.parser.ElementQName.RSS20_IMAGE;
import static yarfraw.io.parser.ElementQName.RSS20_ITEM;
import static yarfraw.io.parser.ElementQName.RSS20_LINK;
import static yarfraw.io.parser.ElementQName.RSS20_PUBDATE;
import static yarfraw.io.parser.ElementQName.RSS20_TEXTINPUT;
import static yarfraw.io.parser.ElementQName.RSS20_TITLE;
import static yarfraw.io.parser.ElementQName.RSS20_TTL;

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
  Atom_Entry_Content(new QName(">"), new QName(">"), ATOM10_CONTENT),
  Atom_Id(new QName(">"), new QName(">"), ATOM10_ID);
  
  public  QName _rss10Name;
  public  QName _rss20Name;
  public  QName _atom10Name;  
  private CoreRssElementEnum(QName rss10, QName rss20, QName atom10){
    _rss10Name = rss10;
    _rss20Name = rss20;
    _atom10Name = atom10;
  }
  
  private static QName copyOf(QName name){
    return new QName(name.getNamespaceURI(), name.getLocalPart());
  }
  
  public QName getRss10Name() {
    return copyOf(_rss10Name);
  }
  
  public QName getRss20Name() {
    return copyOf(_rss20Name);
  }
  
  public QName getAtom10Name() {
    return copyOf(_atom10Name);
  }
  
  public QName getName(FeedFormat format){
    if(format == FeedFormat.ATOM10){
      return getAtom10Name();
    }else if(format == FeedFormat.RSS10){
      return getRss10Name();
    }else if(format == FeedFormat.RSS20){
      return getRss20Name();
    }else{
      throw new IllegalArgumentException("Unknown feed format: "+format);
    }
  }
  
}
