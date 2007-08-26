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
  public final static QName RSS20_RSS_VERSION = new QName("", "version");
  
}