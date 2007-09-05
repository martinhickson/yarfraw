package yarfraw.io.parser;

import javax.xml.namespace.QName;

public class ExtensionElementQname {
  public final static QName ITUNES_KEYWORDS_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "keywords");
  public final static QName ITUNES_SUBTITLE_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "subtitle");
  public final static QName ITUNES_CATEGORY_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "category");
  public final static QName ITUNES_DURATION_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "duration");
  public final static QName ITUNES_SUMMARY_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "summary");
  public final static QName ITUNES_IMAGE_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "image");
  public final static QName ITUNES_EXPLICIT_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "explicit");
  public final static QName ITUNES_OWNER_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "owner");
  public final static QName ITUNES_OWNER_EMAIL_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "email");
  public final static QName ITUNES_OWNER_NAME_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "name");
  public final static QName ITUNES_AUTHOR_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "author");
  public final static QName ITUNES_BLOCK_QNAME = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "block");

  public final static QName MRSS_PLAYER_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "player");
  public final static QName MRSS_CATEGORY_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "category");
  public final static QName MRSS_GROUP_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "group");
  public final static QName MRSS_HASH_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "hash");
  public final static QName MRSS_CONTENT_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "content");
  public final static QName MRSS_COPYRIGHT_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "copyright");
  public final static QName MRSS_TITLE_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "title");
  public final static QName MRSS_CREDIT_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "credit");
  public final static QName MRSS_KEYWORDS_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "keywords");
  public final static QName MRSS_THUMBNAIL_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "thumbnail");
  public final static QName MRSS_TEXT_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "text");
  public final static QName MRSS_RESTRICTION_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "restriction");
  public final static QName MRSS_RATING_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "rating");
  public final static QName MRSS_DESCRIPTION_QNAME = new QName("http://tools.search.yahoo.com/mrss/", "description");

  public final static QName MRSS_VALID_QNAME = new QName("http://purl.org/dc/terms/", "valid");
  
  public final static QName GEORSS_WHERE_QNAME = new QName("http://www.georss.org/georss/10", "where");
  public final static QName GEORSS_POLYGON_QNAME = new QName("http://www.georss.org/georss/10", "polygon");
  public final static QName GEORSS_BOX_QNAME = new QName("http://www.georss.org/georss/10", "box");
  public final static QName GEORSS_POINT_QNAME = new QName("http://www.georss.org/georss/10", "point");
  public final static QName GEORSS_LINE_QNAME = new QName("http://www.georss.org/georss/10", "line");
  
  private ExtensionElementQname(){}
  
}