package yarfraw.io.parser;

import javax.xml.namespace.QName;

public class ElementQName{
  private ElementQName(){}
  public final static QName ATOM10_PUBLISHED = new QName("http://www.w3.org/2005/Atom", "published");
  public final static QName ATOM10_TITLE = new QName("http://www.w3.org/2005/Atom", "title");
  public final static QName ATOM10_ID = new QName("http://www.w3.org/2005/Atom", "id");
  public final static QName ATOM10_RIGHTS = new QName("http://www.w3.org/2005/Atom", "rights");
  public final static QName ATOM10_LINK = new QName("http://www.w3.org/2005/Atom", "link");
  public final static QName ATOM10_UPDATED = new QName("http://www.w3.org/2005/Atom", "updated");
  public final static QName ATOM10_CONTRIBUTOR = new QName("http://www.w3.org/2005/Atom", "contributor");
  public final static QName ATOM10_SUMMARY = new QName("http://www.w3.org/2005/Atom", "summary");
  public final static QName ATOM10_CATEGORY = new QName("http://www.w3.org/2005/Atom", "category");
  public final static QName ATOM10_CONTENT = new QName("http://www.w3.org/2005/Atom", "content");
  public final static QName ATOM10_AUTHOR = new QName("http://www.w3.org/2005/Atom", "author");
  public final static QName ATOM10_SOURCE = new QName("http://www.w3.org/2005/Atom", "source");
  public final static QName ATOM10_ICON = new QName("http://www.w3.org/2005/Atom", "icon");
  public final static QName ATOM10_SUBTITLE = new QName("http://www.w3.org/2005/Atom", "subtitle");
  public final static QName ATOM10_LOGO = new QName("http://www.w3.org/2005/Atom", "logo");
  public final static QName ATOM10_GENERATOR = new QName("http://www.w3.org/2005/Atom", "generator");
  public final static QName ATOM10_ENTRY = new QName("http://www.w3.org/2005/Atom", "entry");
  public final static QName ATOM10_EMAIL = new QName("http://www.w3.org/2005/Atom", "email");
  public final static QName ATOM10_NAME = new QName("http://www.w3.org/2005/Atom", "name");
  public final static QName ATOM10_URI = new QName("http://www.w3.org/2005/Atom", "uri");
  public final static QName ATOM10_FEED = new QName("http://www.w3.org/2005/Atom", "feed");

  public final static QName RSS10_LINK = new QName("http://purl.org/rss/1.0/", "link");
  public final static QName RSS10_URL = new QName("http://purl.org/rss/1.0/", "url");
  public final static QName RSS10_NAME = new QName("http://purl.org/rss/1.0/", "name");
  public final static QName RSS10_IMAGE = new QName("http://purl.org/rss/1.0/", "image");
  public final static QName RSS10_TITLE = new QName("http://purl.org/rss/1.0/", "title");
  public final static QName RSS10_DESCRIPTION = new QName("http://purl.org/rss/1.0/", "description");
  public final static QName RSS10_UPDATEBASE = new QName("http://purl.org/rss/1.0/modules/syndication/", "updateBase");
  public final static QName RSS10_TEXTINPUT = new QName("http://purl.org/rss/1.0/", "textinput");
  public final static QName RSS10_RELATION = new QName("http://purl.org/dc/elements/1.1/", "relation");
  public final static QName RSS10_CHANNEL = new QName("http://purl.org/rss/1.0/", "channel");
  
  public final static QName RSS10_UPDATEFREQUENCY = new QName("http://purl.org/rss/1.0/modules/syndication/", "updateFrequency");
  
  public final static QName RSS10_DC_CONTRIBUTOR = new QName("http://purl.org/dc/elements/1.1/", "contributor");
  public final static QName RSS10_DC_ITEM_TITLE = new QName("http://purl.org/dc/elements/1.1/", "title");
  public final static QName RSS10_DC_PUBLISHER = new QName("http://purl.org/dc/elements/1.1/", "publisher");
  public final static QName RSS10_DC_TYPE = new QName("http://purl.org/dc/elements/1.1/", "type");
  public final static QName RSS10_DC_LANGUAGE = new QName("http://purl.org/dc/elements/1.1/", "language");
  public final static QName RSS10_DC_CREATOR = new QName("http://purl.org/dc/elements/1.1/", "creator");
  public final static QName RSS10_DC_RIGHTS = new QName("http://purl.org/dc/elements/1.1/", "rights");
  public final static QName RSS10_DC_IDENTIFIER = new QName("http://purl.org/dc/elements/1.1/", "identifier");
  public final static QName RSS10_ITEM = new QName("http://purl.org/rss/1.0/", "item");
  public final static QName RSS10_ITEMS = new QName("http://purl.org/rss/1.0/", "items");
  //This is the dc description name, it's ignored
//  public final static QName _Description_QNAME = new QName("http://purl.org/dc/elements/1.1/", "description");
  public final static QName RSS10_DC_FORMAT = new QName("http://purl.org/dc/elements/1.1/", "format");
  public final static QName RSS10_DC_SUBJECT = new QName("http://purl.org/dc/elements/1.1/", "subject");
  public final static QName RSS10_DC_SOURCE = new QName("http://purl.org/dc/elements/1.1/", "source");
  public final static QName RSS10_DC_COVERAGE = new QName("http://purl.org/dc/elements/1.1/", "coverage");
  public final static QName RSS10_DC_DATE = new QName("http://purl.org/dc/elements/1.1/", "date");
  public final static QName RSS10_UPDATEPERIOD = new QName("http://purl.org/rss/1.0/modules/syndication/", "updatePeriod");

  public final static QName RSS20_TITLE = new QName("", "title");
  public final static QName RSS20_NAME = new QName("", "name");
  public final static QName RSS20_TTL = new QName("", "ttl");
  public final static QName RSS20_URL = new QName("", "url");
  public final static QName RSS20_CATEGORY = new QName("", "category");
  public final static QName RSS20_LINK = new QName("", "link");
  public final static QName RSS20_PUBDATE = new QName("", "pubDate");
  public final static QName RSS20_WEBMASTER = new QName("", "webMaster");
  public final static QName RSS20_SKIPDAYS = new QName("", "skipDays");
  public final static QName RSS20_LANGUAGE = new QName("", "language");
  public final static QName RSS20_CLOUD = new QName("", "cloud");
  public final static QName RSS20_SKIPHOURS = new QName("", "skipHours");
  public final static QName RSS20_TEXTINPUT = new QName("", "textInput");
  public final static QName RSS20_MANAGINGEDITOR = new QName("", "managingEditor");
  public final static QName RSS20_DOCS = new QName("", "docs");
  public final static QName RSS20_LAST_BUILD_DATE = new QName("", "lastBuildDate");
  public final static QName RSS20_DESCRIPTION = new QName("", "description");
  public final static QName RSS20_IMAGE = new QName("", "image");
  public final static QName RSS20_GENERATOR = new QName("", "generator");
  public final static QName RSS20_COPYRIGHTS = new QName("", "copyright");
  public final static QName RSS20_RSS = new QName("", "rss");
  public final static QName RSS20_ITEM = new QName("", "item");
  public final static QName RSS20_CHANNEL = new QName("", "channel");
  public final static QName RSS20_COMMENTS = new QName("", "comments");
  public final static QName RSS20_ENCLOSURE = new QName("", "enclosure");
  public final static QName RSS20_GUID = new QName("", "guid");
  public final static QName RSS20_SOURCE = new QName("", "source");
  public final static QName RSS20_AUTHOR = new QName("", "author");

  public final static QName RSS20_WIDTH = new QName("", "width");
  public final static QName RSS20_HEIGHT = new QName("", "height");
}