package yarfraw.core.datamodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import yarfraw.utils.ValidationUtils;

/**
 * This maps to both &lt;guid> in Rss and &lt;id> in Atom.
 * Note: it is not supported by Rss 1.0 format.
 * 
 * <p/>
 * Rss 2.0 description:
 * <br/>
 * {@link Guid} is an optional sub-element of {@link ItemEntry}.<br/>
 * guid stands for globally unique identifier. It's a string that uniquely identifies the item. When present, an aggregator may choose to use this string to determine if an item is new.
 * &lt;guid>http://some.server.com/weblogItem3207&lt;/guid>
 * <p/>
 * There are no rules for the syntax of a guid. Aggregators must view them as a string. It's up to the source of the feed to establish the uniqueness of the string.
 * <p/>
 * If the guid element has an attribute named "isPermaLink" with a value of true, 
 * the reader may assume that it is a permalink to the item, that is, 
 * a url that can be opened in a Web browser, that points to the full item described by the 
 * {@link ItemEntry} element.
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
  private static final long serialVersionUID = 20070927L;
  private String _idValue;
  private Boolean _isPermaLink = true;

  public Id(){super();}
  
  public Id(String idValue){
    this();
    setIdValue(idValue);
  }
  
  /**
   * @return A value that uniquely identify a {@link ChannelFeed} or a {@link ItemEntry}. 
   */
  public String getIdValue() {
    return _idValue;
  }

  /**
   * @param idValue - A value that uniquely identify a {@link ChannelFeed} or a {@link ItemEntry}.
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
   * {@link ItemEntry} element.
   */
  public Boolean isPermaLink() {
    return _isPermaLink;
  }
  /**
   * This field is only used by Rss 2.0.
   * 
   * @param isPermaLink If true, the reader may assume that it is a permalink to the item, that is, 
   * a url that can be opened in a Web browser, that points to the full item described by the
   * {@link ItemEntry} element.
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
  

  /**
   * <b>Atom 1.0 only</b><br/>
   * Any element defined by this specification MAY have an xml:base attribute 
   * [W3C.REC-xmlbase-20010627]. When xml:base is used in an Atom Document, 
   * it serves the function described in section 5.1.1 of [RFC3986], establishing 
   * the base URI (or IRI) for resolving any relative references found within the 
   * effective scope of the xml:base attribute.
   * @param base
   * @return
   */
  public Id setBase(String base) {
    _base = base;
    return this;
  }
  /**
   * <li>Rss 2.0 - &lt;language> element. 
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. A list of allowable 
   * values for this element, as provided by Netscape, is here. You may also use values 
   * defined by the W3C.
   * Only &lt;channel> support this element.</li>
   * <li>Rss 1.0 - &lt;dc:language> element. A language of the intellectual content of the resource.
   * Only &lt;channel> and &lt;item> support this element. </li>
   * <li>Atom 1.0 - 'lang' attribute</li>
   * <br/>
   * Note: for Rss 2.0 and Rss 1.0, only &lt;channel> and &lt;item>
   * @param lang
   * @return
   */
  public Id setLang(String lang) {
    _lang = lang;
    return this;
  }
  /**
   * <li>Rss 2.0 - &lt;language> element. 
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. A list of allowable 
   * values for this element, as provided by Netscape, is here. You may also use values 
   * defined by the W3C.
   * Only &lt;channel> support this element.</li>
   * <li>Rss 1.0 - &lt;dc:language> element. A language of the intellectual content of the resource.
   * Only &lt;channel> and &lt;item> support this element. </li>
   * <li>Atom 1.0 - 'lang' attribute</li>
   * <br/>
   * Note: for Rss 2.0 and Rss 1.0, only &lt;channel> and &lt;item>
   * @param lang
   * @return
   */
  public Id setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  ////////////////////////Common setters///////////////////////
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format == FeedFormat.RSS10){
      return;
    }
    ValidationUtils.validateNotNull("Id Value should not be null", _idValue);
    if(format == FeedFormat.ATOM10){
      ValidationUtils.validateUri("Id value should be an valid url", _idValue);
    }
  }
}