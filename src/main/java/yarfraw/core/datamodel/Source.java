package yarfraw.core.datamodel;

/**
 * <b>This is only used by Rss 2.0.</b>
 * <br/>
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
  private String _url;
  private String _source;
  public Source(){}
  
  public Source(String url, String source){
    super();
    setUrl(url);
    setSource(source);
  }
  
  public String getUrl() {
    return _url;
  }
  
  public Source setUrl(String url){
    _url = url;
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
  public void validate(FeedFormat format) throws ValidationException {

  }

}