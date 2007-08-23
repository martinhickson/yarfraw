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

public class AtomContent extends AtomAttributes{
  private List<Element> _otherElements = new ArrayList<Element>();
  private List<String> _contentText = new ArrayList<String>();
  private AtomTextAttributes.TextType _type = AtomTextAttributes.TextType.text;
  private String _src;
  
  public AtomContent() {}
  public static AtomContent create(){
    return new AtomContent();
  }

  public AtomContent addContentText(String contentText){
    _contentText = _contentText != null ? _contentText : new ArrayList<String>();
    _contentText.add(contentText);
    return this;
  }
  
  public List<String> getContentText() {
    return _contentText;
  }
  public AtomContent setContentText(List<String> contentText) {
    _contentText = contentText;
    return this;
  }
  @Override
  public AtomContent setBase(String base) {
    super.setBase(base);
    return this;
  }
  @Override
  public AtomContent setLang(Locale lang) {
    super.setLang(lang);
    return this;
  }
  
  @Override
  public AtomContent setOtherAttributes(Map<QName, String> otherAttributes) {
    super.setOtherAttributes(otherAttributes);
    return this;
  }
  
  @Override
  public AtomContent addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
    return this;
  }
  
  public String getSrc(){
    return _src;
  }
  
  public AtomContent setSrc(String src) {
    _src = src;
    return this;
  }
    
  public AtomTextAttributes.TextType getType() {
    return _type;
  }
  public AtomContent setType(AtomTextAttributes.TextType type) {
    _type = type;
    return this;
  }
  /**
   * Other additional elements that are not in the Rss 2.0 specs.
   */
  public List<Element> getOtherElements() {
    return _otherElements;
  }
  /**
   * Other additional elements that are not in the specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   */
  public AtomContent setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add a element that is not specified in the specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public AtomContent addOtherElement(Element element){
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
  public AtomContent addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
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