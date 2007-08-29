package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This maps to both &lt;guid> in Rss and &lt;id> in Atom.
 * Note: it is not supported by Rss 1.0 format.
 * <p/>
 * When mapping to Rss, the attributes that are not in the Rss specs will be ignored:
 * 'lang', 'base'
 * <p/>
 * When mapping to Atom, the attributes that are not in the Atom specs will be ignored:
 * 'isPremalink'
 * 
 * <p/>
 * Rss 2.0 description:
 * <br/>
 * {@link Guid} is an optional sub-element of {@link Item}.<br/>
 * guid stands for globally unique identifier. It's a string that uniquely identifies the item. When present, an aggregator may choose to use this string to determine if an item is new.
 * &lt;guid>http://some.server.com/weblogItem3207&lt;/guid>
 * <p/>
 * There are no rules for the syntax of a guid. Aggregators must view them as a string. It's up to the source of the feed to establish the uniqueness of the string.
 * <p/>
 * If the guid element has an attribute named "isPermaLink" with a value of true, 
 * the reader may assume that it is a permalink to the item, that is, 
 * a url that can be opened in a Web browser, that points to the full item described by the 
 * {@link Item} element.
 * <br/> 
 * An example:
 * &lt;guid isPermaLink="true">http://inessential.com/2002/09/01.php#a2&lt;/guid>
 * <p/>
 * isPermaLink is optional, its default value is true. If its value is false, the guid may not be assumed to be a url, or a url to anything in particular.
 * <p/>
 * 
 * Atom 1.0 description:
 * <br/>
 * The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
 * 
 * @author jliang
 *
 */
public class Id extends AtomAttributes{
  
  private static final Log LOG = LogFactory.getLog(Id.class);
  private String _idValue;
  private Boolean _isPermaLink = true;

  public Id(){super();}
  
  public Id(String idValue){
    this();
    setIdValue(idValue);
  }
  
  /**
   * This field is only used by Atom 1.0.
   * 
   * @return A value that uniquely identify a {@link Channel} or a {@link Item}. 
   */
  public String getIdValue() {
    return _idValue;
  }

  /**
   * This field is only used by Atom 1.0.
   * 
   * @param idValue - A value that uniquely identify a {@link Channel} or a {@link Item}.
   * @return - this
   */
  public Id setIdValue(String idValue) {
    _idValue = idValue;
    return this;
  }

  /**
   * This field is only used by Rss 2.0.
   * 
   * @return - If true, the reader may assume that it is a permalink to the item, that is, 
   * a url that can be opened in a Web browser, that points to the full item described by the
   * {@link Item} element.
   */
  public Boolean isPermaLink() {
    return _isPermaLink;
  }
  /**
   * This field is only used by Rss 2.0.
   * 
   * @param isPermaLink If true, the reader may assume that it is a permalink to the item, that is, 
   * a url that can be opened in a Web browser, that points to the full item described by the
   * {@link Item} element.
   * @return this
   */
  public Id setPermaLink(Boolean isPermaLink) {
      _isPermaLink = isPermaLink == null || isPermaLink;
      return this;
  }
  
  @Override
  public Id setBase(String base) {
    super.setBase(base);
    return this;
  }
  @Override
  public Id setLang(Locale lang) {
    super.setLang(lang);
    return this;
  }
  
  @Override
  public Id setOtherAttributes(Map<QName, String> otherAttributes) {
    super.setOtherAttributes(otherAttributes);
    return this;
  }
  @Override
  public Id addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
    return this;
  }

  @Override
  public void validate(FeedFormat format) throws ValidationException {
    try {
      @SuppressWarnings("unused")
      URI uri = new URI(_idValue);
    }
    catch (URISyntaxException e) {
      LOG.warn("idValue should be a valid uri");
    }
  }
}