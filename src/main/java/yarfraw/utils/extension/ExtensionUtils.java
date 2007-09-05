package yarfraw.utils.extension;
import static yarfraw.io.parser.ExtensionAttributesQName.GEORSS_ELEV;
import static yarfraw.io.parser.ExtensionAttributesQName.GEORSS_FEATURETYPETAG;
import static yarfraw.io.parser.ExtensionAttributesQName.GEORSS_FLOOR;
import static yarfraw.io.parser.ExtensionAttributesQName.GEORSS_RADIUS;
import static yarfraw.io.parser.ExtensionAttributesQName.GEORSS_RELATIONSHIPTAG;
import static yarfraw.io.parser.ExtensionElementQname.GEORSS_BOX_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.GEORSS_LINE_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.GEORSS_POINT_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.GEORSS_POLYGON_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.GEORSS_WHERE_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_AUTHOR_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_BLOCK_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_CATEGORY_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_DURATION_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_EXPLICIT_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_IMAGE_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_KEYWORDS_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_OWNER_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_SUBTITLE_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.ITUNES_SUMMARY_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_CATEGORY_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_CONTENT_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_COPYRIGHT_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_CREDIT_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_DESCRIPTION_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_GROUP_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_HASH_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_KEYWORDS_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_PLAYER_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_RATING_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_RESTRICTION_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_TEXT_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_THUMBNAIL_QNAME;
import static yarfraw.io.parser.ExtensionElementQname.MRSS_TITLE_QNAME;
import static yarfraw.utils.XMLUtils.same;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.georss.georss._10.GeoRssExtension;
import org.georss.georss._10.SimplePositionType;
import org.georss.georss._10.Where;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.googlebase.elements.GoogleBaseExtension;
import yarfraw.generated.googlebase.elements.ObjectFactory;

import yarfraw.generated.itunes.elements.ItunesCategoryType;
import yarfraw.generated.itunes.elements.ItunesExtension;
import yarfraw.generated.itunes.elements.ItunesImageType;
import yarfraw.generated.itunes.elements.ItunesOwnerType;
import yarfraw.generated.mrss.elements.MrssCategoryType;
import yarfraw.generated.mrss.elements.MrssContentType;
import yarfraw.generated.mrss.elements.MrssCopyrightType;
import yarfraw.generated.mrss.elements.MrssCreditType;
import yarfraw.generated.mrss.elements.MrssDescriptionType;
import yarfraw.generated.mrss.elements.MrssExtension;
import yarfraw.generated.mrss.elements.MrssGroupType;
import yarfraw.generated.mrss.elements.MrssHashType;
import yarfraw.generated.mrss.elements.MrssPlayerType;
import yarfraw.generated.mrss.elements.MrssRatingType;
import yarfraw.generated.mrss.elements.MrssRestrictionType;
import yarfraw.generated.mrss.elements.MrssTextType;
import yarfraw.generated.mrss.elements.MrssThumbnailType;
import yarfraw.generated.mrss.elements.MrssTitleType;
import yarfraw.utils.XMLUtils;

public class ExtensionUtils{
  public static final String ITUNES_JAXB_CONTEXT = "yarfraw.generated.itunes.elements";
  public static final String MRSS_JAXB_CONTEXT = "yarfraw.generated.mrss.elements";
  public static final String GOOGLEBASE_JAXB_CONTEXT = "yarfraw.generated.googlebase.elements";
  public static final String GEORSS_JAXB_CONTEXT = "org.georss.georss._10";
  public static final String ITUNES_PREFIX = "itunes";
  public static final String MRSS_PREFIX = "media";
  public static final String GEORSS_PREFIX = "georss";
  public static final String GOOGLEBASE_PREFIX = "g";

  private static final ObjectFactory GOOGLEBASE_FACTORY = new ObjectFactory();
  
  private static final Log LOG = LogFactory.getLog(ExtensionUtils.class);
  private ExtensionUtils(){}
  
  /**
   * Extracts the itunes extension elements from the input list into an {@link ItunesExtension}
   * object.
   * <br/>
   * see http://www.apple.com/itunes/store/podcaststechspecs.html about these
   * extension elements
   * @param otherElements - any elements
   * @return an {@link ItunesExtension} object
   * @throws YarfrawException 
   */
  @SuppressWarnings("unchecked")
  public static ItunesExtension extractItunesExtension(List<Element> otherElements) throws YarfrawException {
    ItunesExtension ret = new ItunesExtension();
    try{
      if(otherElements != null){
        Unmarshaller u = JAXBContext.newInstance(ITUNES_JAXB_CONTEXT).createUnmarshaller();
        for(Element e : otherElements){
          QName name = new QName(e.getNamespaceURI(), e.getLocalName());
          if(same(name, ITUNES_AUTHOR_QNAME)){
            ret.setAuthor(e.getTextContent());
          }else if(same(name, ITUNES_BLOCK_QNAME)){
            ret.setBlock(e.getTextContent());
          }else if(same(name, ITUNES_CATEGORY_QNAME)){
            ItunesCategoryType cat = ((JAXBElement<ItunesCategoryType>)u.unmarshal(e)).getValue();
            ret.getCategory().add(cat);
          }else if(same(name, ITUNES_DURATION_QNAME)){
            ret.setDuration(e.getTextContent());
          }else if(same(name, ITUNES_EXPLICIT_QNAME)){
            ret.setExplicit(e.getTextContent());
          }else if(same(name, ITUNES_IMAGE_QNAME)){
            ItunesImageType img  = ((JAXBElement<ItunesImageType>)u.unmarshal(e)).getValue();
            ret.setImage(img);
          }else if(same(name, ITUNES_KEYWORDS_QNAME)){
            ret.setKeywords(e.getTextContent());
          }else if(same(name, ITUNES_OWNER_QNAME)){
            ItunesOwnerType owner = ((JAXBElement<ItunesOwnerType>)u.unmarshal(e)).getValue();
            ret.setOwner(owner);
          }else if(same(name, ITUNES_SUBTITLE_QNAME)){
            ret.setSubtitle(e.getTextContent());
          }else if(same(name, ITUNES_SUMMARY_QNAME)){
            ret.setSummary(e.getTextContent());
          }
        }
      }
    }catch (JAXBException e) {
      throw new YarfrawException("unable to unmarshal element", e);
    }
    return ret;
  }

  /**
   * Extracts the Georss extension elements from the input list into an {@link GeoRssExtension}
   * object.
   * <br/>
   * see http://georss.org/ about these
   * extension elements
   * @param otherElements - any elements
   * @return an {@link GeoRssExtension} object
   * @throws YarfrawException 
   */
  @SuppressWarnings("unchecked")
  public static GeoRssExtension extractGeoRssExtension(List<Element> otherElements) throws YarfrawException {
    GeoRssExtension ret = new GeoRssExtension();
    try{
      if(otherElements != null){
        Unmarshaller u = JAXBContext.newInstance(GEORSS_JAXB_CONTEXT).createUnmarshaller();
        for(Element e : otherElements){
          QName name = new QName(e.getNamespaceURI(), e.getLocalName());
          if(same(name, GEORSS_WHERE_QNAME)){
            Where copy = ((JAXBElement<Where>)u.unmarshal(e)).getValue();
            ret.setWhere(copy);
          }else if(same(name, GEORSS_BOX_QNAME)){
            SimplePositionType pos = new SimplePositionType();
            populatePos(e, pos);
            ret.setBox(pos);
          }else if(same(name, GEORSS_LINE_QNAME)){
            SimplePositionType pos = new SimplePositionType();
            populatePos(e, pos);
            ret.setLine(pos);
          }else if(same(name, GEORSS_POINT_QNAME)){
            SimplePositionType pos = new SimplePositionType();
            populatePos(e, pos);
            ret.setPoint(pos);
          }else if(same(name, GEORSS_POLYGON_QNAME)){
            SimplePositionType pos = new SimplePositionType();
            populatePos(e, pos);
            ret.setPolygon(pos);
          }
        }
      }
    }catch (JAXBException e) {
      throw new YarfrawException("unable to unmarshal element", e);
    }
    return ret;
  }

  private static void populatePos(Element e, SimplePositionType pos){
    String content = e.getTextContent();
    if(content == null){
      return;
    }
    String[] values = content.split(" ");
    List<Double> dl = pos.getValue();
    for(String v : values){
      dl.add(Double.parseDouble(v.trim()));
    }
    String elev = XMLUtils.getAttributeValue(e, GEORSS_ELEV);
    if(elev != null){
      pos.setElev(Double.parseDouble(elev));
    }
    pos.setFeaturetypetag(XMLUtils.getAttributeValue(e, GEORSS_FEATURETYPETAG));
    pos.setRelationshiptag(XMLUtils.getAttributeValue(e, GEORSS_RELATIONSHIPTAG));
    String floor = XMLUtils.getAttributeValue(e, GEORSS_FLOOR);
    if(elev != null){
      pos.setFloor(Double.parseDouble(floor));
    }
    String radius = XMLUtils.getAttributeValue(e, GEORSS_RADIUS);
    if(elev != null){
      pos.setRadius(Double.parseDouble(radius));
    }
  }
  
  /**
   * Extracts the mrss extension elements from the input list into an {@link MrssExtension}
   * object.
   * <br/>
   * see http://search.yahoo.com/mrss about these
   * extension elements
   * @param otherElements - any elements
   * @return an {@link MrssExtension} object
   * @throws YarfrawException 
   */
  @SuppressWarnings("unchecked")
  public static MrssExtension extractMrssExtension(List<Element> otherElements) throws YarfrawException {
    MrssExtension ret = new MrssExtension();
    try{
      if(otherElements != null){
        Unmarshaller u = JAXBContext.newInstance(MRSS_JAXB_CONTEXT).createUnmarshaller();
        for(Element e : otherElements){
          QName name = new QName(e.getNamespaceURI(), e.getLocalName());
          if(same(name, MRSS_CATEGORY_QNAME)){
            MrssCategoryType cat = ((JAXBElement<MrssCategoryType>)u.unmarshal(e)).getValue();
            ret.getCategory().add(cat);
          }else if(same(name, MRSS_CONTENT_QNAME)){
            MrssContentType content = ((JAXBElement<MrssContentType>)u.unmarshal(e)).getValue();
            ret.getContent().add(content);
          }else if(same(name, MRSS_COPYRIGHT_QNAME)){
            MrssCopyrightType copy = ((JAXBElement<MrssCopyrightType>)u.unmarshal(e)).getValue();
            ret.setCopyright(copy);
          }else if(same(name, MRSS_CREDIT_QNAME)){
            MrssCreditType copy = ((JAXBElement<MrssCreditType>)u.unmarshal(e)).getValue();
            ret.getCredit().add(copy);
          }else if(same(name, MRSS_DESCRIPTION_QNAME)){
            MrssDescriptionType copy = ((JAXBElement<MrssDescriptionType>)u.unmarshal(e)).getValue();
            ret.setDescription(copy);
          }else if(same(name, MRSS_GROUP_QNAME)){
            MrssGroupType copy = ((JAXBElement<MrssGroupType>)u.unmarshal(e)).getValue();
            ret.getGroup().add(copy);
          }else if(same(name, MRSS_HASH_QNAME)){
            MrssHashType copy = ((JAXBElement<MrssHashType>)u.unmarshal(e)).getValue();
            ret.setHash(copy);
          }else if(same(name, MRSS_KEYWORDS_QNAME)){
            ret.setKeywords(e.getTextContent());
          }else if(same(name, MRSS_PLAYER_QNAME)){
            MrssPlayerType copy = ((JAXBElement<MrssPlayerType>)u.unmarshal(e)).getValue();
            ret.setPlayer(copy);
          }else if(same(name, MRSS_RATING_QNAME)){
            MrssRatingType copy = ((JAXBElement<MrssRatingType>)u.unmarshal(e)).getValue();
            ret.setRating(copy);
          }else if(same(name, MRSS_RESTRICTION_QNAME)){
            MrssRestrictionType copy = ((JAXBElement<MrssRestrictionType>)u.unmarshal(e)).getValue();
            ret.setRestriction(copy);
          }else if(same(name, MRSS_TEXT_QNAME)){
            MrssTextType copy = ((JAXBElement<MrssTextType>)u.unmarshal(e)).getValue();
            ret.setText(copy);
          }else if(same(name, MRSS_THUMBNAIL_QNAME)){
            MrssThumbnailType copy = ((JAXBElement<MrssThumbnailType>)u.unmarshal(e)).getValue();
            ret.setThumbnail(copy);
          }else if(same(name, MRSS_TITLE_QNAME)){
            MrssTitleType copy = ((JAXBElement<MrssTitleType>)u.unmarshal(e)).getValue();
            ret.setTitle(copy);
          }
        }
      }
    }catch (JAXBException e) {
      throw new YarfrawException("unable to unmarshal element", e);
    }
    return ret;
  }
  /**
   * Converts the input {@link ItunesExtension} object to an element list.
   * <br/>
   * see http://www.apple.com/itunes/store/podcaststechspecs.html about these
   * extension elements
   * 
   * @param extensionObject an valid {@link ItunesExtension} object
   * @return a list of elements representing all the elements in the input extension object
   * @throws YarfrawException if conversion failed
   */
  public static List<Element> toItunesElements(ItunesExtension extensionObject)
  throws YarfrawException{
    return toElements(extensionObject, ITUNES_JAXB_CONTEXT, ITUNES_PREFIX);
  }

  /**
   * Converts the input {@link GeoRssExtension} object to an element list.
   * <br/>
   * see http://georss.org/ about these
   * extension elements
   * 
   * @param extensionObject an valid {@link GeoRssExtension} object
   * @return a list of elements representing all the elements in the input extension object
   * @throws YarfrawException if conversion failed
   */
  public static List<Element> toGeoRssElements(GeoRssExtension extensionObject)
  throws YarfrawException{
    return toElements(extensionObject, GEORSS_JAXB_CONTEXT, GEORSS_PREFIX);
  }
  

  /**
   * Converts the input {@link GoogleBaseExtension} object to an element list.
   * <br/>
   * see http://base.google.com/support/bin/answer.py?answer=58085&hl=en about these
   * extension elements
   * 
   * @param extensionObject an valid {@link GoogleBaseExtension} object
   * @return a list of elements representing all the elements in the input extension object
   * @throws YarfrawException if conversion failed
   */
  public static List<Element> toGoogleBaseElements(GoogleBaseExtension extensionObject)
  throws YarfrawException{
    //XXX: not sure why i have to do this to make it marshall
    JAXBElement<GoogleBaseExtension> jaxb = GOOGLEBASE_FACTORY.createGoogleBaseExtension(extensionObject);
    return toElements(jaxb, GOOGLEBASE_JAXB_CONTEXT, GOOGLEBASE_PREFIX);
  }
  
  /**
   * Converts the input {@link MrssExtension} object to an element list.
   * <br/>
   * see http://search.yahoo.com/mrss about these
   * extension elements
   * 
   * @param extensionObject an valid {@link MrssExtension} object
   * @return a list of elements representing all the elements in the input extension object
   * @throws YarfrawException if conversion failed
   */
  public static List<Element> toMrssElements(MrssExtension extensionObject)
  throws YarfrawException{
    return toElements(extensionObject, MRSS_JAXB_CONTEXT, MRSS_PREFIX);
  }
  
  private static List<Element> toElements(Object extensionObject, String jaxbContext, String forcePrefix) throws YarfrawException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    List<Element> ret = new ArrayList<Element>();
    try {
      Document doc = dbf.newDocumentBuilder().newDocument();
      Marshaller m = JAXBContext.newInstance(jaxbContext).createMarshaller();
      m.marshal(extensionObject, doc);
      Element e = doc.getDocumentElement();
      NodeList list =  e.getChildNodes();
      for(int i =0; i< list.getLength(); i++){
        Node n = list.item(i);
        if (n instanceof Element) {
          Element element = (Element) n;
          element.setPrefix(forcePrefix);
          ret.add(element);
        }else {
          LOG.error("Ignore unexpected node "+n.getNodeName()+", this should not happen");
        }
      }
    }
    catch (ParserConfigurationException e) {
      throw new YarfrawException("Parserconfig exception", e);
    }
    catch (JAXBException e) {
      e.printStackTrace();
      throw new YarfrawException("JAXB exception", e);
    }
    return ret;
  }
  
}