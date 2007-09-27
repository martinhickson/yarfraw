package yarfraw.core.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.ValidationUtils;
import yarfraw.utils.XMLUtils;

/**
 * <li>Rss 1.0 & Rss 2.0 - only email filed is used, other fields are ignored.
 * </li>
 * <li>Atom 1.0 - person constructs
 * <br/>http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#rfc.section.3
 * </li>
 * @author jliang
 *
 */
public class Person extends AbstractBaseObject{
  private static final long serialVersionUID = 20070927L;
  private String _emailOrText;
  private String _uri;
  private String _name;
  
  public Person(){}
  
  /**
   * Constructs a new Person object with the input string as its email or text content.
   * @param emailOrText <li>Rss 1.0 - email address or text content of the corresponding person element.
   * </li>
   * <li>Rss 2.0 - should be an email address of the corresponding person element.
   * </li>
   * <li>Atom 1.0 - should be an email address of the corresponding person element.
   * </li>
   */
  public Person(String emailOrText){  
    _emailOrText = emailOrText;
  }
  
  /**
   * <li>Rss 1.0 - email address or text content of the corresponding person element.
   * </li>
   * <li>Rss 2.0 - should be an email address of the corresponding person element.
   * </li>
   * <li>Atom 1.0 - should be an email address of the corresponding person element.
   * </li>
   * @return
   */
  public String getEmailOrText() {
    return _emailOrText;
  }

  /**
   * <li>Rss 1.0 - email address or text content of the corresponding person element.
   * </li>
   * <li>Rss 2.0 - should be an email address of the corresponding person element.
   * </li>
   * <li>Atom 1.0 - should be an email address of the corresponding person element.
   * </li>
   * @param emailOrText
   */
  public Person setEmailOrText(String emailOrText) {
    _emailOrText = emailOrText;
    return this;
  }

  /**
   * <b>Atom 1.0 only</b><br/>
   * The "atom:uri" element's content conveys an IRI associated with the person. Person constructs MAY contain an atom:uri element, but MUST NOT contain more than one. The content of atom:uri in a Person construct MUST be an IRI reference [RFC3987].
   * @return
   */
  public String getUri() {
    return _uri;
  }
/**
 * <b>Atom 1.0 only</b><br/>
 * The "atom:uri" element's content conveys an IRI associated with the person. Person constructs MAY contain an atom:uri element, but MUST NOT contain more than one. The content of atom:uri in a Person construct MUST be an IRI reference [RFC3987].
 * @param uri any valid uri
 * @return this
 */
  public Person setUri(String uri) {
    _uri = uri;
    return this;
  }

  /**
   * <b>Atom 1.0 only</b><br/>
   * The "atom:name" element's content conveys a human-readable name for the person. The content of atom:name is Language-Sensitive. Person constructs MUST contain exactly one "atom:name" element.
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * <b>Atom 1.0 only</b><br/>
   * The "atom:name" element's content conveys a human-readable name for the person. The content of atom:name is Language-Sensitive. Person constructs MUST contain exactly one "atom:name" element.
   * @param name value of the name element
   * @return this
   */
  public Person setName(String name) {
    _name = name;
    return this;
  }

  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Person setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public Person addOtherAttributes(QName namespace, String attribute) {
    if(_otherAttributes == null){
      _otherAttributes = new HashMap<QName, String>();
    }
    _otherAttributes.put(namespace, attribute);
    return this;
  }
  
  /**
   * Other additional elements that are not in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   */
  public Person setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add an element that is not specified in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public Person addOtherElement(Element element){
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(element);
    return this;
  }
  
  /**
   * Add an element that is not specified in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * 
   * @param xmlString - any element
   * @throws ParserConfigurationException 
   * @throws IOException 
   * @throws SAXException 
   */
  public Person addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
    return addOtherElement(XMLUtils.parseXml(xmlString, false, false).getDocumentElement());
  }
  
  /**
   * <b>Atom 1.0 only</b><br/>
   * Any element defined by this specification MAY have an xml:base attribute 
   * [W3C.REC-xmlbase-20010627]. When xml:base is used in an Atom Document, 
   * it serves the function described in section 5.1.1 of [RFC3986], establishing 
   * the base URI (or IRI) for resolving any relative references found within the 
   * effective scope of the xml:base attribute.
   * @param base
   * @return
   */
  public Person setBase(String base) {
    _base = base;
    return this;
  }
  /**
   * <li>Rss 2.0 - &lt;language> element. 
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. A list of allowable 
   * values for this element, as provided by Netscape, is here. You may also use values 
   * defined by the W3C.
   * Only &lt;channel> support this element.</li>
   * <li>Rss 1.0 - &lt;dc:language> element. A language of the intellectual content of the resource.
   * Only &lt;channel> and &lt;item> support this element. </li>
   * <li>Atom 1.0 - 'lang' attribute</li>
   * <br/>
   * Note: for Rss 2.0 and Rss 1.0, only &lt;channel> and &lt;item>
   * @param lang
   * @return
   */
  public Person setLang(String lang) {
    _lang = lang;
    return this;
  }
  
  /**
   * <li>Rss 2.0 - &lt;language> element. 
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. A list of allowable 
   * values for this element, as provided by Netscape, is here. You may also use values 
   * defined by the W3C.
   * Only &lt;channel> support this element.</li>
   * <li>Rss 1.0 - &lt;dc:language> element. A language of the intellectual content of the resource.
   * Only &lt;channel> and &lt;item> support this element. </li>
   * <li>Atom 1.0 - 'lang' attribute</li>
   * <br/>
   * Note: for Rss 2.0 and Rss 1.0, only &lt;channel> and &lt;item>
   * @param lang
   * @return
   */
  public Person setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  ////////////////////////Common setters///////////////////////
  
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    ValidationUtils.validateNotNull("EmailOrText should not be null", _emailOrText);
  }
  
}