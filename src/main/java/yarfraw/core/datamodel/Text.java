package yarfraw.core.datamodel;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.XMLUtils;

/**
 * This class is mapped to element of type xs:string as well as to text constructs element in Atom 1.0.
 * <br/>
 * Only the text content is used for Rss 1.0 and Rss 2.0, all other fields are ignored. 
 * 
 * <br/>
 * for information about Atom 1.0, see http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#rfc.section.4.1.2
 * 
 * @author jliang
 *
 */
public class Text extends AbstractBaseObject{
  public enum TextType{
    text, html, xhtml
  }
  private TextType _type = TextType.text;
  private Element _xhtmlDiv;
  private String _text;
  
  public Text(){}
  
  /**
   * This constructs a {@link Text} of type 'text'
   */
  public Text(String text){
    _type = TextType.text;
    setText(text);
  }
  
  /**
   * The text content of this text element.
   * @return
   */
  public String getText() {
    return _text;
  }

  /**
   * 
   * @param text The text content of this text element.
   * @return this
   */
  public Text setText(String text) {
    _text = text;
    return this;
  }

/**
 * <b>Atom 1.0 only </b> <br/>
 * The single xhtml div element if the text construct is an xhtml construct 
 */
  public Element getXhtmlDiv() {
    return _xhtmlDiv;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The single xhtml div element if the text construct is an xhtml construct 
   */
  public Text setXhtmlDiv(Element xhtmlDiv) {
    _xhtmlDiv = xhtmlDiv;
    _type = TextType.xhtml;
    return this;
  }
  
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The single xhtml div element if the text construct is an xhtml construct.
   * <br/>
   * This method parses the input xhtml string into an {@link Element} and put it 
   * to the xhtmlDiv field. Therefore it should be a single &lt;div> element.
   * 
   * @param xhtmlDiv any valid xhtml string
   * @return this
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   */
  public Text setXhtmlDiv(String xhtmlDiv) throws SAXException, IOException, ParserConfigurationException {
    setXhtmlDiv(XMLUtils.parseXml(xhtmlDiv, false, false).getDocumentElement());
    return this;
  }

  /**
   * Type of the text. 
   * @param textType
   */
  public Text(TextType textType){
    setType(textType);
  }
  
  /**
   * Type of the text.
   * @return
   */
  public TextType getType() {
    return _type;
  }
  /**
   * Type of the text.
   * @return
   */
  public Text setType(TextType type) {
    _type = type;
    return this;
  }

  @Override
  public void validate(FeedFormat format) throws ValidationException {
    // TODO Auto-generated method stub
    
  }
}