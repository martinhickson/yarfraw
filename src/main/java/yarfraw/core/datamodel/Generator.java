package yarfraw.core.datamodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import yarfraw.utils.ValidationUtils;

/**
 * <li>Rss 1.0 - Not supported, this is ignored.</li>
 * <li>Rss 2.0 - &lt;generator> A string indicating the program used to generate the channel.  
 * </li>
 * <li>Atom 1.0 - &lt;generator> 
 * The "atom:generator" element's content identifies the agent used to generate a feed, for debugging and other purposes.
 * </li>
 * @author jliang
 *
 */
public class Generator extends AbstractBaseObject{
  private static final long serialVersionUID = 20070927L;
  private String _value;
  private String _uri;
  private String _version;
  
  public Generator(String generatorValue){
    _value = generatorValue;
  }
  /**
   * <li>Rss 1.0 - Not supported.</li>
   * <li>Rss 2.0 - the text content of the &lt;generator>
   * </li>
   * <li>Atom 1.0 - 
   * The content of this element, when present, MUST be a string that is a 
   * human-readable name for the generating agent. The escaped versions of 
   * characters such as "&" and ">" represent those characters, not markup.
   * </li>
   * @return
   */
  public String getValue() {
    return _value;
  }

  /**
   * <li>Rss 1.0 - Not supported.</li>
   * <li>Rss 2.0 - the text content of the &lt;generator>
   * </li>
   * <li>Atom 1.0 - 
   * The content of this element, when present, MUST be a string that is a 
   * human-readable name for the generating agent. The escaped versions of 
   * characters such as "&" and ">" represent those characters, not markup.
   * </li>
   * @param value
   */
  public Generator setValue(String value) {
    _value = value;
    return this;
  }


  /**
   * <li>Rss 1.0 - Not supported.</li>
   * <li>Rss 2.0 -  - Not supported.
   * </li>
   * <li>Atom 1.0 - 
   * The atom:generator element MAY have a "uri" attribute whose value MUST be 
   * an IRI reference [RFC3987]. When dereferenced, the resulting URI 
   * (mapped from an IRI, if necessary) SHOULD produce a representation that is relevant to that agent.
   * </li>
   * @return
   */
  public String getUri() {
    return _uri;
  }

  /**
   * <li>Rss 1.0 - Not supported.</li>
   * <li>Rss 2.0 -  - Not supported.
   * </li>
   * <li>Atom 1.0 - 
   * The atom:generator element MAY have a "uri" attribute whose value MUST be 
   * an IRI reference [RFC3987]. When dereferenced, the resulting URI 
   * (mapped from an IRI, if necessary) SHOULD produce a representation that is relevant to that agent.
   * </li>
   * @param uri
   * @return
   */
  public Generator setUri(String uri) {
    _uri = uri;
    return this;
  }


  /**
   * <li>Rss 1.0 - Not supported.</li>
   * <li>Rss 2.0 -  - Not supported.
   * </li>
   * <li>Atom 1.0 - 
   * The atom:generator element MAY have a "version" attribute that indicates the version of the generating agent.
   * </li>
   * @return
   */
  public String getVersion() {
    return _version;
  }

  /**
   * <li>Rss 1.0 - Not supported.</li>
   * <li>Rss 2.0 -  - Not supported.
   * </li>
   * <li>Atom 1.0 - 
   * The atom:generator element MAY have a "version" attribute that indicates the version of the generating agent.
   * </li>
   * @param version
   */
  public Generator setVersion(String version) {
    _version = version;
    return this;
  }
  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Generator setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public  Generator addOtherAttributes(QName namespace, String attribute) {
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
  public Generator setBase(String base) {
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
  public Generator setLang(String lang) {
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
  public Generator setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  ////////////////////////Common setters///////////////////////
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format == FeedFormat.RSS10){
      return;
    }
    ValidationUtils.validateNotNull("[Generator] Value should not be null", _value);
    ValidationUtils.validateUri("[Generator] uri should be an valid uri", _uri);
  }
  
}