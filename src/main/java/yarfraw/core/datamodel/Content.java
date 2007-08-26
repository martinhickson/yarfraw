package yarfraw.core.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.XMLUtils;
/**
 * Data model of the 'atom:content' element in Atom 1.0 specs.<br/>
 * http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#atomContent
 * @author jliang
 *
 */
public class Content extends AtomAttributes{
  private List<Element> _otherElements = new ArrayList<Element>();
  private List<String> _contentText = new ArrayList<String>();
  private AtomTextAttributes.TextType _type = AtomTextAttributes.TextType.text;
  private String _src;
  
  public Content() {}
  public static Content create(){
    return new Content();
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
  @Override
  public Content setBase(String base) {
    super.setBase(base);
    return this;
  }
  @Override
  public Content setLang(Locale lang) {
    super.setLang(lang);
    return this;
  }
  
  @Override
  public Content setOtherAttributes(Map<QName, String> otherAttributes) {
    super.setOtherAttributes(otherAttributes);
    return this;
  }
  
  @Override
  public Content addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
    return this;
  }
  
  /**
   * atom:content MAY have a "src" attribute, whose value MUST be an IRI reference [RFC3987]. If the "src" attribute is present, atom:content MUST be empty. Atom Processors MAY use the IRI to retrieve the content, and MAY chose to ignore remote content or present it in a different manner than local content.
   * <p/>
   * If the "src" attribute is present, the "type" attribute SHOULD be provided and MUST be a MIME media type [MIMEREG], rather than "text", "html", or "xhtml". The value is advisory; that is to say, when the corresponding URI (mapped from an IRI, if necessary), is dereferenced, if the server providing that content also provides a media type, the server-provided media type is authoritative.
   */
  public String getSrc(){
    return _src;
  }
  /**
   * atom:content MAY have a "src" attribute, whose value MUST be an IRI reference [RFC3987]. If the "src" attribute is present, atom:content MUST be empty. Atom Processors MAY use the IRI to retrieve the content, and MAY chose to ignore remote content or present it in a different manner than local content.
   * <p/>
   * If the "src" attribute is present, the "type" attribute SHOULD be provided and MUST be a MIME media type [MIMEREG], rather than "text", "html", or "xhtml". The value is advisory; that is to say, when the corresponding URI (mapped from an IRI, if necessary), is dereferenced, if the server providing that content also provides a media type, the server-provided media type is authoritative.
   */  
  public Content setSrc(String src) {
    _src = src;
    return this;
  }
  /**
   * On the atom:content element, the value of the "type" attribute MAY be one of "text", "html", or "xhtml". Failing that, it MUST conform to the syntax of a MIME media type, but MUST NOT be a composite type (see Section 4.2.6 of [MIMEREG]). If the type attribute is not provided, Atom Processors MUST behave as though it were present with a value of "text".
   */  
  public AtomTextAttributes.TextType getType() {
    return _type;
  }
  /**
   * On the atom:content element, the value of the "type" attribute MAY be one of "text", "html", or "xhtml". Failing that, it MUST conform to the syntax of a MIME media type, but MUST NOT be a composite type (see Section 4.2.6 of [MIMEREG]). If the type attribute is not provided, Atom Processors MUST behave as though it were present with a value of "text".
   */  
  public Content setType(AtomTextAttributes.TextType type) {
    _type = type;
    return this;
  }
  /**
   * Other additional elements that are not in the specs.
   */
  public List<Element> getOtherElements() {
    return _otherElements;
  }
  /**
   * Other additional elements that are not in the specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   */
  public Content setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add a element that is not specified in the specs.<br/>
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
   * Add a element that is not specified in the specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * 
   */
  public Content addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(XMLUtils.parseXml(xmlString, false, false).getDocumentElement());
    return this;
  }
  
  /**
   * Search through the other element list and return the first element that matches
   * both input the namespaceURI and the localName.
   * 
   * @param namespaceURI - namespaceURI of the element to be search for
   * @param localName - localName of the element
   * @return - null if no matching element is found,
   * the matching element otherwise.
   */
  public Element getElementByNS(String namespaceURI, String localName){
    if(CollectionUtils.isEmpty(_otherElements)){
      return null;
    }
    for(Element e : _otherElements){
      if(ObjectUtils.equals(localName, e.getLocalName()) && ObjectUtils.equals(namespaceURI, e.getNamespaceURI())){
        return e;
      }
    }
    return null;
  }
  
}