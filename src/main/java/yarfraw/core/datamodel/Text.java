package yarfraw.core.datamodel;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.ValidationUtils;
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
  private static final long serialVersionUID = 20070927L;
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
    return setXhtmlDiv(XMLUtils.parseXml(xhtmlDiv, false, false).getDocumentElement());
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
  public Text setBase(String base) {
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
  public Text setLang(String lang) {
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
  public Text setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    ValidationUtils.validateNotNull("Text value should not be null", _text);
  }
}