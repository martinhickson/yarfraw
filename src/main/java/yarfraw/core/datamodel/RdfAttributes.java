package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * RDF/Rss 1.0 additional attributes to the feed elements.
 * 
 * @author jliang
 *
 */
public class RdfAttributes{
  public URI _resource;
  public URI _about;
  
  public RdfAttributes() {}
  
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
  public void setResource(String resource) throws URISyntaxException {
    _resource = new URI(resource);
  }
  public URI getAbout() {
    return _about;
  }
  public void setAbout(String about) throws URISyntaxException {
    _about = new URI(about);
  }
  public void setResource(URI resource) {
    _resource = resource;
  }
  public void setAbout(URI about) {
    _about = about;
  }

}