package yarfraw.core.datamodel;


/**
 * RDF/Rss 1.0 additional attributes to the feed elements.
 * 
 * @author jliang
 *
 */
public class RdfAttributes extends AbstractBaseObject{
  private String _resource;
  private String _about;
  
  public RdfAttributes() {}

  public RdfAttributes(String resource, String about){
    super();
    setResource(resource);
    setAbout(about);
  }
  public String getResource() {
    return _resource;
  }
  public RdfAttributes setResource(String resource){
    _resource = resource == null?null: new String(resource.trim());
    return this;
  }
  public String getAbout() {
    return _about;
  }
  public RdfAttributes setAbout(String about){
    _about = about==null? null: new String(about.trim());
    return this;
  }
  
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    
  }

}