package yarfraw.io.parser;

import javax.xml.namespace.QName;

/**
 * This class contains (some of) the {@link QName} of Attributes in Rss feeds. 
 * @author jliang
 *
 */
public class AttributesQName{
  private AttributesQName(){}
  public final static QName ATOM10_ENTRY_SRC = new QName("http://www.w3.org/2005/Atom", "src");
  public final static QName ATOM10_ENTRY_TYPE = new QName("http://www.w3.org/2005/Atom", "type");
  public final static QName ATOM10_CATEGORY_TERM = new QName("http://www.w3.org/2005/Atom", "term");
  public final static QName ATOM10_CATEGORY_SCHEME = new QName("http://www.w3.org/2005/Atom", "scheme");
  public final static QName ATOM10_LINK_HREF = new QName("http://www.w3.org/2005/Atom", "href");
  public final static QName ATOM10_LINK_TYPE = new QName("http://www.w3.org/2005/Atom", "type");
  public final static QName ATOM10_LINK_REL = new QName("http://www.w3.org/2005/Atom", "rel");
  public final static QName ATOM10_LINK_LENGTH = new QName("http://www.w3.org/2005/Atom", "length");
  public final static QName ATOM10_LINK_TITLE = new QName("http://www.w3.org/2005/Atom", "title");
  public final static QName ATOM10_LINK_HREF_LANG = new QName("http://www.w3.org/2005/Atom", "hreflang");
  public final static QName ATOM10_LANGUAGE = new QName("http://www.w3.org/XML/1998/namespace", "lang");
  public final static QName RSS20_RSS_VERSION = new QName("", "version");
  public final static QName RSS20_ISPERMALINK = new QName("", "isPermaLink");
  public final static QName RSS20_ENCLOSURE_URL = new QName("", "url");
  public final static QName RSS20_ENCLOSURE_TYPE = new QName("", "type");
  public final static QName RSS20_ENCLOSURE_LENGTH = new QName("", "length");
  public final static QName RSS20_CATEGORY_DOMAIN = new QName("", "domain");
  
  
  
}