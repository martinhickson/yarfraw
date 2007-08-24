package yarfraw.io.parser;
import static yarfraw.mapping.ElementQName.*;
import javax.xml.namespace.QName;

import yarfraw.core.datamodel.FeedFormat;

/**
 * The elements that are common to all supported {@link FeedFormat}.
 * 
 * 
 * @author jliang
 *
 */
enum CoreRssElementEnum {
  //XXX: using ">" to guaranteed that no actual name contains this character
  Channel_title(RSS10_TITLE, RSS20_TITLE, ATOM10_TITLE),
  Channel_link(RSS10_LINK, RSS20_LINK, ATOM10_LINK), 
  Channel_description(RSS10_DESCRIPTION, RSS20_DESCRIPTION, ATOM10_SUMMARY),
  //language is an attribute instead of a tag in atom10
  Channel_language(RSS10_LANGUAGE, RSS20_DESCRIPTION, new QName(">")),  
  Channel_pubdate(RSS10_DATE, RSS20_PUBDATE, ATOM10_PUBLISHED),
  //only rss20 has ttl element
  Channel_ttl(new QName(""), RSS20_TTL, new QName(">")),
  Channel_image(RSS10_IMAGE, RSS20_IMAGE, ATOM10_ICON),
  Channel_textinput(RSS10_TEXTINPUT, RSS20_TEXTINPUT, new QName(">")),
  Channel_category(RSS10_SUBJECT, RSS20_CATEGORY, ATOM10_CATEGORY),
  
  Item_title(RSS10_TITLE, RSS20_TITLE, ATOM10_TITLE),
  Item_link(RSS10_LINK, RSS20_LINK, ATOM10_LINK), 
  Item_description(RSS10_DESCRIPTION, RSS20_DESCRIPTION, ATOM10_SUMMARY),  
  Item_author(RSS10_CREATOR, RSS20_AUTHOR, ATOM10_AUTHOR), 
  Item_category(RSS10_SUBJECT, RSS20_CATEGORY, ATOM10_CATEGORY),
  Item_pubdate(RSS10_DATE, RSS20_PUBDATE, ATOM10_PUBLISHED),
  Item_enclosure(new QName(">"), RSS20_ENCLOSURE, new QName(">")),
  Item_guid(new QName(">"), RSS20_GUID, new QName(">"));

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
