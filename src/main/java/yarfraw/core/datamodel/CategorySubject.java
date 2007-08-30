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

import yarfraw.utils.CommonUtils;
import yarfraw.utils.XMLUtils;

/**
 * <li>Rss 2.0 - the &ltcategory> element</li>
 * <li>Rss 1.0 - the &ltdc:subject> element</li>
 * <li>Atom 1.0 - the &ltcategory> element</li>
 * 
 * <p>
 * Rss 2.0:  http://cyber.law.harvard.edu/rss/rss.html#syndic8 <br/>
 * Rss 1.0:  http://dublincore.org/documents/1999/07/02/dces/ - 'Dublin Core'
 * Atom 1.0: http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#rfc.section.4.2.2
 * <p/>
 * @author jliang
 *
 */
public class CategorySubject extends AbstractBaseObject{
  private String _categoryOrSubjectOrTerm;
  private String _domainOrScheme;
  private String _label;
  public CategorySubject() {}
  
  /**
   * This value is only used by Atom 1.0, it is ignored by other {@link FeedFormat}.
   * <br/>
   * The "label" attribute provides a human-readable label for display in end-user applications. 
   * The content of the "label" attribute is Language-Sensitive. 
   * The escaped versions of characters such as "&" and ">" represent those characters, not markup. 
   * Category elements MAY have a "label" attribute.
   * @return label value
   */
  public String getLabel() {
    return _label;
  }
  /**
   * This value is only used by Atom 1.0, it is ignored by other {@link FeedFormat}.
   * <br/>
   * The "label" attribute provides a human-readable label for display in end-user applications. 
   * The content of the "label" attribute is Language-Sensitive. 
   * The escaped versions of characters such as "&" and ">" represent those characters, not markup. 
   * Category elements MAY have a "label" attribute.
   *  
   * @param label label value
   * @return this
   */
  public CategorySubject setLabel(String label) {
    _label = label;
    return this;
  }

  /**
   * Creates a new category object.
   * 
   * @param categoryOrSubjectOrTerm
   * <li>Rss 2.0 - text value of the &ltcategory> element</li>
   * <li>Rss 1.0 - text value of the &ltdc:subject> element</li>
   * <li>Atom 1.0 - text value of the &ltcategory> element</li> 
   */
  public CategorySubject(String categoryOrSubjectOrTerm) {
    super();
    _categoryOrSubjectOrTerm = categoryOrSubjectOrTerm;
  }
  
  /**
   * <li>Rss 2.0 - Specify one or more categories that the channel belongs to. </li>
   * <li>Rss 1.0 - The topic of the content of the resource. </li>
   * <li>Atom 1.0 - The "term" attribute is a string that identifies the category to which the entry or feed belongs. Category elements MUST have a "term" attribute. </li>
   * @return <li>Rss 2.0 - text value of the &ltcategory> element</li>
   * <li>Rss 1.0 - text value of the &ltdc:subject> element</li>
   * <li>Atom 1.0 - text value of the &ltcategory> element</li>
   */
  public String getCategoryOrSubjectOrTerm() {
    return _categoryOrSubjectOrTerm;
  }
  /**
   * <li>Rss 2.0 - Specify one or more categories that the channel belongs to. </li>
   * <li>Rss 1.0 - The topic of the content of the resource. </li>
   * <li>Atom 1.0 - The "term" attribute is a string that identifies the category to which the entry or feed belongs. Category elements MUST have a "term" attribute. </li>
   * @param categoryOrSubjectOrTerm <li>Rss 2.0 - text value of the &ltcategory> element</li>
   * <li>Rss 1.0 - text value of the &ltdc:subject> element</li>
   * <li>Atom 1.0 - text value of the &ltcategory> element</li>
   * @return this
   */
  public CategorySubject setCategoryOrSubjectOrTerm(String categoryOrSubjectOrTerm) {
    _categoryOrSubjectOrTerm = categoryOrSubjectOrTerm;
    return this;
  }
  /**
   * <li> Rss 2.0 - 
   * a string that identifies a categorization taxonomy. 
   * <br/>
   * The value of the element is a forward-slash-separated string that identifies 
   * a hierarchic location in the indicated taxonomy. Processors may establish 
   * conventions for the interpretation of categories. Two examples are provided below:
   * <br/>
   * &lt;category>Grateful Dead&lt;/category>
   * <br/>
   * &lt;category domain="http://www.fool.com/cusips">MSFT&lt;/category>
   * </li>
   * <li> Atom 1.0 - 
   * The "scheme" attribute is an IRI that identifies a categorization scheme. Category elements MAY have a "scheme" attribute.
   * </li>
   * <li> Rss 1.0 - 
   * Not supported by Rss 1.0.
   * </li>  
   */
  public String getDomainOrScheme() {
    return _domainOrScheme;
  }
  /**
   * <li> Rss 2.0 - 
   * a string that identifies a categorization taxonomy. 
   * <br/>
   * The value of the element is a forward-slash-separated string that identifies 
   * a hierarchic location in the indicated taxonomy. Processors may establish 
   * conventions for the interpretation of categories. Two examples are provided below:
   * <br/>
   * &lt;category>Grateful Dead&lt;/category>
   * <br/>
   * &lt;category domain="http://www.fool.com/cusips">MSFT&lt;/category>
   * </li>
   * <li> Atom 1.0 - 
   * The "scheme" attribute is an IRI that identifies a categorization scheme. Category elements MAY have a "scheme" attribute.
   * </li>
   * <li> Rss 1.0 - 
   * Not supported by Rss 1.0.
   * </li>  
   * @param domainOrScheme - the value of the attribute
   * @return this
   */
  public CategorySubject setDomainOrScheme(String domainOrScheme) {
    _domainOrScheme = domainOrScheme;
    return this;
  }
  
  
  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public CategorySubject setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public CategorySubject addOtherAttributes(QName namespace, String attribute) {
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
  public CategorySubject setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add an element that is not specified in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public CategorySubject addOtherElement(Element element){
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
  public CategorySubject addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(XMLUtils.parseXml(xmlString, false, false).getDocumentElement());
    return this;
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
  public CategorySubject setBase(String base) {
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
  public CategorySubject setLang(String lang) {
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
  public CategorySubject setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  /**
   * <b>Rss 1.0 only</b><br/>
   * @param resource
   * @return
   */
  public CategorySubject setResource(String resource) {
    _resource = resource;
    return this;
  }
  /**
   * <b>Rss 1.0 only</b><br/>
   * @param about
   * @return
   */
  public CategorySubject setAbout(String about) {
    _about = about;
    return this;
  }
  ////////////////////////Common setters///////////////////////
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    CommonUtils.validateNotNull(_categoryOrSubjectOrTerm, "Category: Category value should not be null");
  }
}