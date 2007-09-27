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

import yarfraw.utils.XMLUtils;
/**
 * <li>Rss 2.0 -
 * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
 * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
 * </li>
 * <li>Rss 1.0 -
 * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
 * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
 * </li>
 * <li> Atom 1.0 - 
 * Data model of the 'atom:content' element in Atom 1.0 specs.<br/>
 * http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#atomContent
 * <br/>
 * If the content is XHTML, the xhtml elements can be found at the 'otherElements' list. <br/>
 * </li>
 * @author jliang
 *
 */
public class Content extends AbstractBaseObject{
  private static final long serialVersionUID = 20070927L;
  private List<String> _contentText;
  private String _type;
  private String _src;
  
  public Content() {}

  public Content(String contentText) {
    _contentText = new ArrayList<String>();
    _contentText.add(contentText);
    _type = "text";
  }
  /**
   * Any text content.
   */
  public Content addContentText(String contentText){
    _contentText = _contentText != null ? _contentText : new ArrayList<String>();
    _contentText.add(contentText);
    return this;
  }
  /**
   * Any text content.
   */
  public List<String> getContentText() {
    return _contentText;
  }
  /**
   * Any text content.
   */
  public Content setContentText(List<String> contentText) {
    _contentText = contentText;
    return this;
  }
  
  /**
   * <b>Atom 1.0 only </b> 
   * <br/>
   * atom:content MAY have a "src" attribute, whose value MUST be an IRI reference [RFC3987]. If the "src" attribute is present, atom:content MUST be empty. Atom Processors MAY use the IRI to retrieve the content, and MAY chose to ignore remote content or present it in a different manner than local content.
   * <p/>
   * If the "src" attribute is present, the "type" attribute SHOULD be provided and MUST be a MIME media type [MIMEREG], rather than "text", "html", or "xhtml". The value is advisory; that is to say, when the corresponding URI (mapped from an IRI, if necessary), is dereferenced, if the server providing that content also provides a media type, the server-provided media type is authoritative.
   */
  public String getSrc(){
    return _src;
  }
  /**
   * <b>Atom 1.0 only </b> 
   * <br/>
   * atom:content MAY have a "src" attribute, whose value MUST be an IRI reference [RFC3987]. If the "src" attribute is present, atom:content MUST be empty. Atom Processors MAY use the IRI to retrieve the content, and MAY chose to ignore remote content or present it in a different manner than local content.
   * <p/>
   * If the "src" attribute is present, the "type" attribute SHOULD be provided and MUST be a MIME media type [MIMEREG], rather than "text", "html", or "xhtml". The value is advisory; that is to say, when the corresponding URI (mapped from an IRI, if necessary), is dereferenced, if the server providing that content also provides a media type, the server-provided media type is authoritative.
   */  
  public Content setSrc(String src) {
    _src = src;
    return this;
  }
  /**
   * <b>Atom 1.0 only </b> 
   * <br/>
   * On the atom:content element, the value of the "type" attribute MAY be one of "text", "html", or "xhtml". Failing that, it MUST conform to the syntax of a MIME media type, but MUST NOT be a composite type (see Section 4.2.6 of [MIMEREG]). If the type attribute is not provided, Atom Processors MUST behave as though it were present with a value of "text".
   * 
   */  
  public String getType() {
    return _type;
  }
  /**
   * <b>Atom 1.0 only </b> 
   * <br/>
   * On the atom:content element, the value of the "type" attribute MAY be one of "text", "html", or "xhtml". Failing that, it MUST conform to the syntax of a MIME media type, but MUST NOT be a composite type (see Section 4.2.6 of [MIMEREG]). If the type attribute is not provided, Atom Processors MUST behave as though it were present with a value of "text".
   */  
  public Content setType(String type) {
    _type = type;
    return this;
  }
  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Content setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public Content addOtherAttributes(QName namespace, String attribute) {
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
  public Content setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add an element that is not specified in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public Content addOtherElement(Element element){
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
  public Content addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
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
  public Content setBase(String base) {
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
  public Content setLang(String lang) {
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
  public Content setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  ////////////////////////Common setters///////////////////////
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    // TODO Auto-generated method stub
    
  }
  
}