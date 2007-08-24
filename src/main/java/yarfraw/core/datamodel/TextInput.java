package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;
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
  private RdfAttributes _rdfAttributes;
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
  
  /**
   * Attributes that is only supported by RSS 1.0/RDF format
   * @return
   */
  public RdfAttributes getRdfAttributes() {
    return _rdfAttributes;
  }
  /**
   * Attributes that is only supported by RSS 1.0/RDF format
   * @return
   */
  public TextInput setRdfAttributes(RdfAttributes rdfAttributes) {
    _rdfAttributes = rdfAttributes;
    return this;
  }
  
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {

  }
}