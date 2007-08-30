package yarfraw.core.datamodel;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import yarfraw.utils.CommonUtils;

/**
 * This maps to both &lt;guid> in Rss and &lt;id> in Atom.
 * Note: it is not supported by Rss 1.0 format.
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
public class Id extends AbstractBaseObject{
  
  private String _idValue;
  private Boolean _isPermaLink = true;

  public Id(){super();}
  
  public Id(String idValue){
    this();
    setIdValue(idValue);
  }
  
  /**
   * @return A value that uniquely identify a {@link Channel} or a {@link Item}. 
   */
  public String getIdValue() {
    return _idValue;
  }

  /**
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
  
  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Id setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public Id addOtherAttributes(QName namespace, String attribute) {
    if(_otherAttributes == null){
      _otherAttributes = new HashMap<QName, String>();
    }
    _otherAttributes.put(namespace, attribute);
    return this;
  }
  
  ////////////////////////Common setters///////////////////////
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format == FeedFormat.RSS10){
      return;
    }
    CommonUtils.validateNotNull("Id Value should not be null", _idValue);
  }
}