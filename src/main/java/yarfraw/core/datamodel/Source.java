package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;
/**
 * {@link Source} is an optional sub-element of {@link Item}.
 * <p/>
 * Its value is the name of the RSS channel that the item came from, derived from its &lt;title>. 
 * It has one required attribute, url, which links to the XMLization of the source.
 * <p/>
 * &lt;source url="http://www.tomalak.org/links2.xml">Tomalak's Realm&lt;/source>
 * <p/>
 * The purpose of this element is to propagate credit for links, to publicize the sources of news items. It can be used in the Post command of an aggregator. It should be generated automatically when forwarding an item from an aggregator to a weblog authoring tool.
 * 
 * @author jliang
 *
 */
public class Source extends AbstractBaseObject{
  private URI _url;
  private String _source;
  public Source(){}
  public static Source create(){
    return new Source();
  }
  public Source(URI url, String source) {
    super();
    _url = url;
    _source = source;
  }
  public Source(String url, String source) throws URISyntaxException {
    super();
    setUrl(url);
    _source = source;
  }
  public URI getUrl() {
    return _url;
  }
  public Source setUrl(URI url) {
    _url = url;
    return this;
  }
  
  public Source setUrl(String url) throws URISyntaxException{
    if(url == null){
      _url = null;
    }else{
      _url = new URI(url.trim());
    }
    return this;
  }
  
  public String getSource() {
    return _source;
  }
  public Source setSource(String source) {
    _source = source;
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {

  }

}