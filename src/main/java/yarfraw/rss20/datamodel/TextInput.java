package yarfraw.rss20.datamodel;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBElement;

import yarfraw.rss20.elements.ObjectFactory;
import yarfraw.rss20.elements.TTextInput;
/**
 *A channel may optionally contain a <textInput> sub-element, which contains four required sub-elements.<br/>
 *&lt;title> -- The label of the Submit button in the text input area.<br/>
 *&lt;description> -- Explains the text input area.<br/>
 *&lt;name> -- The name of the text object in the text input area.<br/>
 *&lt;link> -- The URI of the CGI script that processes text input requests.<br/>
 *The purpose of the <textInput> element is something of a mystery. You can use it to specify a search engine box. Or to allow a reader to provide feedback. Most aggregators ignore it.<br/>
 * 
 * @author jliang
 *
 */
public class TextInput extends AbstractBaseObject{
  private String _title;
  private String _description;
  private String _name;
  private URI _link;
  public TextInput(){}
  public static TextInput create(){
    return new TextInput();
  }
  /**
   *A channel may optionally contain a <textInput> sub-element, which contains four required sub-elements.<br/>
   *&lt;title> -- The label of the Submit button in the text input area.<br/>
   *&lt;description> -- Explains the text input area.<br/>
   *&lt;name> -- The name of the text object in the text input area.<br/>
   *&lt;link> -- The URI of the CGI script that processes text input requests.<br/>
   *The purpose of the <textInput> element is something of a mystery. You can use it to specify a search engine box. Or to allow a reader to provide feedback. Most aggregators ignore it.<br/>
   * 
   */
  public TextInput(String title, String description, String name, URI link) {
    _title = title;
    _description = description;
    _name = name;
    _link = link;
  }
  
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
  public URI getLink() {
    return _link;
  }
  /**
   *The URI of the CGI script that processes text input requests.<br/>
   */  
  public TextInput setLink(URI link) {
    _link = link;
    return this;
  }
  
  /**
   *The URI of the CGI script that processes text input requests.<br/>
   * @throws URISyntaxException 
   */  
  public TextInput setLink(String link) throws URISyntaxException {
    if(link == null){
      _link = null;
    }else{
      _link = new URI(link.trim());
    }
    
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    // TODO Auto-generated method stub
    
  }
  
  private TTextInput toTTextInput(){
    TTextInput ret = new TTextInput();
    ret.setDescription(_description);
    if(_link != null){
      ret.setLink(_link.toString());
    }
    ret.setName(_name);
    ret.setTitle(_title);
    return ret;
  }
  
  public JAXBElement<TTextInput> toTTextInputJAXB(){
    return new ObjectFactory().createTRssChannelTextInput(toTTextInput());
  }
}