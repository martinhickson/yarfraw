package yarfraw.core.datamodel;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.XMLUtils;

/**
 * Atom text common constructs.
 * 
 * see http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#rfc.section.3.1
 * @author jliang
 *
 */
public class AtomTextAttributes extends AtomAttributes{
  public enum TextType{
    text, html, xhtml
  }
  private TextType _type = TextType.text;
  private Element _xhtmlDiv;
  
  public static AtomTextAttributes create(){
    return new AtomTextAttributes();
  }
  
  public AtomTextAttributes(){}
  
  /**
   * This constructs a {@link AtomTextAttributes} of type 'xhtml'
   * @param xhtml
   * @throws ParserConfigurationException 
   * @throws IOException 
   * @throws SAXException 
   */
  public AtomTextAttributes(String xhtml) throws SAXException, IOException, ParserConfigurationException{
    _type = TextType.xhtml;
    setXhtmlDiv(xhtml);
  }

  @Override
  public AtomTextAttributes setBase(String base) {
    super.setBase(base);
    return this;
  }
  @Override
  public AtomTextAttributes setLang(Locale lang) {
    super.setLang(lang);
    return this;
  }
  
  @Override
  public AtomTextAttributes setOtherAttributes(Map<QName, String> otherAttributes) {
    super.setOtherAttributes(otherAttributes);
    return this;
  }
/**
 * The single xhtml div element if the text construct is an xhtml construct 
 */
  public Element getXhtmlDiv() {
    return _xhtmlDiv;
  }
  /**
   * The single xhtml div element if the text construct is an xhtml construct 
   */
  public AtomTextAttributes setXhtmlDiv(Element xhtmlDiv) {
    _xhtmlDiv = xhtmlDiv;
    _type = TextType.xhtml;
    return this;
  }
  
  public AtomTextAttributes setXhtmlDiv(String xhtmlDiv) throws SAXException, IOException, ParserConfigurationException {
    setXhtmlDiv(XMLUtils.parseXml(xhtmlDiv, false, false).getDocumentElement());
    return this;
  }

  public AtomTextAttributes(TextType textType){
    setType(textType);
  }
  
  public TextType getType() {
    return _type;
  }
  public AtomTextAttributes setType(TextType type) {
    _type = type;
    return this;
  }
  @Override
  public AtomTextAttributes addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
    return this;
  }
}