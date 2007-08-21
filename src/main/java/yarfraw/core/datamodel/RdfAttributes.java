package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * RDF/Rss 1.0 additional attributes to the feed elements.
 * 
 * @author jliang
 *
 */
public class RdfAttributes extends AbstractBaseObject{
  private URI _resource;
  private URI _about;
  
  public RdfAttributes() {}
  public static RdfAttributes create(){
    return new RdfAttributes();
  }
  public RdfAttributes(URI resource, URI about) {
    super();
    _resource = resource;
    _about = about;
  }
  public RdfAttributes(String resource, String about) throws URISyntaxException {
    super();
    setResource(resource);
    setAbout(about);
  }
  public URI getResource() {
    return _resource;
  }
  public RdfAttributes setResource(String resource) throws URISyntaxException {
    _resource = resource == null?null: new URI(resource.trim());
    return this;
  }
  public URI getAbout() {
    return _about;
  }
  public RdfAttributes setAbout(String about) throws URISyntaxException {
    _about = about==null? null: new URI(about.trim());
    return this;
  }
  public RdfAttributes setResource(URI resource) {
    _resource = resource;
    return this;
  }
  public RdfAttributes setAbout(URI about) {
    _about = about;
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}