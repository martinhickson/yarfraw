package yarfraw.core.datamodel;

import java.net.URISyntaxException;

import yarfraw.utils.CommonUtils;
/**
 * <b>&ltTextInput> element of Rss 1.0 and Rss 2.0. This is ignored by Atom 1.0</b>
 * <br/>
 * <li> Rss 2.0 -
 *A channel may optionally contain a <textInput> sub-element, which contains four required sub-elements.<br/>
 *&lt;title> -- The label of the Submit button in the text input area.<br/>
 *&lt;description> -- Explains the text input area.<br/>
 *&lt;name> -- The name of the text object in the text input area.<br/>
 *&lt;link> -- The URI of the CGI script that processes text input requests.<br/>
 *The purpose of the <textInput> element is something of a mystery. You can use it to specify a search engine box. Or to allow a reader to provide feedback. Most aggregators ignore it.<br/>
 *</li>
 *<li> Rss 1.0 - 
 * The textinput element affords a method for submitting form data to an arbitrary URL -- usually located at the parent website. 
 * The form processor at the receiving end only is assumed to handle the HTTP GET method.
 * The field is typically used as a search box or subscription form -- among others. 
 * While this is of some use when RSS documents are rendered as channels (see MNN) and accompanied by human readable title 
 * and description, the ambiguity in automatic determination of meaning of this overloaded element renders it otherwise not 
 * particularly useful. RSS 1.0 therefore suggests either deprecation or augmentation with some form of resource discovery of 
 * this element in future versions while maintaining it for backward compatibility with RSS 0.9.
 * {textinput_uri} must be unique with respect to any other rdf:about attributes in the RSS document and is a URI which identifies the textinput. {textinput_uri} 
 * should be identical to the value of the <link> sub-element of the &lt;textinput> element, if possible. 
 *</li>
 *
 * @author jliang
 *
 */
public class TextInput extends AbstractBaseObject{
  private String _title;
  private String _description;
  private String _name;
  private String _link;
  public TextInput(){}
   
  /**
   *A channel may optionally contain a <textInput> sub-element, which contains four required sub-elements.<br/>
   *&lt;title> -- The label of the Submit button in the text input area.<br/>
   *&lt;description> -- Explains the text input area.<br/>
   *&lt;name> -- The name of the text object in the text input area.<br/>
   *&lt;link> -- The URI of the CGI script that processes text input requests.<br/>
   *The purpose of the <textInput> element is something of a mystery. You can use it to specify a search engine box. Or to allow a reader to provide feedback. Most aggregators ignore it.<br/>
   * @throws URISyntaxException 
   * if <code>link</code> is an invalid URI 
   * 
   */
  public TextInput(String title, String description, String name, String link) throws URISyntaxException {
    _title = title;
    _description = description;
    _name = name;
    setLink(link);
  }
  /**
   * The label of the Submit button in the text input area.<br/>
   */
  public String getTitle() {
    return _title;
  }
  /**
   * The label of the Submit button in the text input area.<br/>
   */  
  public TextInput  setTitle(String title) {
    _title = title;
    return this;
  }
  /**
   * Explains the text input area.<br/>
   */
  public String getDescription() {
    return _description;
  }
  /**
   * Explains the text input area.<br/>
   */  
  public  TextInput  setDescription(String description) {
    _description = description;
    return this;
  }
  /**
   *The name of the text object in the text input area.<br/>
   */
  public String getName() {
    return _name;
  }
  /**
   *The name of the text object in the text input area.<br/>
   */  
  public TextInput setName(String name) {
    _name = name;
    return this;
  }
  /**
   *The URI of the CGI script that processes text input requests.<br/>
   */
  public String getLink() {
    return _link;
  }
  /**
   *The URI of the CGI script that processes text input requests.<br/>
   */  
  public TextInput setLink(String link) {
    _link = link;
    return this;
  }
    
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format == FeedFormat.ATOM10)
       return;
    
    if(format == FeedFormat.RSS20){
      CommonUtils.validateNotNull("Image: All required fields in the Image object should be not null", _title, _link, _description, _name);
      CommonUtils.validateUri("link is not a valid URI",  _link);
    }
    
    if(format == FeedFormat.RSS10){
      CommonUtils.validateNotNull("attribute 'about' is required", getAbout());
    }
  }
}