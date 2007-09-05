package yarfraw.io.parser;

import javax.xml.namespace.QName;

public class ExtensionAttributesQName {
  public final static QName ITUNES_CATEGORY_TEXT = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "text");
  public final static QName ITUNES_IMAGE_HREF = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "href");
  public final static QName ITUNES_IMAGE_TYPE = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "type");
  public final static QName ITUNES_IMAGE_REL = new QName("http://www.itunes.com/dtds/podcast-1.0.dtd", "rel");
  
  public final static QName GEORSS_FEATURETYPETAG = new QName("http://www.georss.org/georss/10", "featuretypetag");
  public final static QName GEORSS_RELATIONSHIPTAG = new QName("http://www.georss.org/georss/10", "relationshiptag");
  public final static QName GEORSS_ELEV = new QName("http://www.georss.org/georss/10", "elev");
  public final static QName GEORSS_FLOOR = new QName("http://www.georss.org/georss/10", "floor");
  public final static QName GEORSS_RADIUS = new QName("http://www.georss.org/georss/10", "radius");
  
  
  
  private ExtensionAttributesQName(){}
  
}
