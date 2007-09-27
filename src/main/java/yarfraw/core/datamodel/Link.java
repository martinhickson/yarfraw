package yarfraw.core.datamodel;

import java.util.Locale;

import yarfraw.utils.ValidationUtils;


/**
 * <li>Rss 2.0 and Rss 1.0 - the &lt;link> element. The href field will be used as the text 
 * content of the &lt;link> element. 
 * </li>
 * <li>Atom 1.0 - 
 * The "atom:link" element defines a reference from an entry or feed to a Web resource. 
 * This specification assigns no meaning to the content (if any) of this element.
 * <br/>
 * Please note that in an Atom feed, there is no elements for 'enclosure' (@see {@link Enclosure}) objects.
 * You can, however, add enclosure object to an Atom feed using this class. 
 * </li>
 * @author jliang
 *
 */
public class Link extends AbstractBaseObject{
  private static final long serialVersionUID = 20070927L;
  private String _href;
  private String _rel;
  private String _type;
  private String _hreflang;
  private String _title;
  private Integer _length;

  public Link(){}
  
  public Link(String href) {
    _href = href;
  }
  public Link(String href, String rel, String type, String hreflang,
          String title, Integer length) {
    super();
    _href = href;
    _rel = rel;
    _type = type;
    _hreflang = hreflang;
    _title = title;
    _length = length;
  }

  /**
   * <li>Rss 1.0 & Rss 2.0 - text content of the &lt;link> element </li>
   * <li>Atom 1.0 - 
   * The "href" attribute contains the link's IRI. atom:link elements MUST have a href attribute, whose value MUST be a IRI reference [RFC3987].
   * </li>
   */
  public String getHref() {
    return _href;
  }
  /**
   * <li>Rss 1.0 & Rss 2.0 - text content of the &lt;link> element </li>
   * <li>Atom 1.0 - 
   * The "href" attribute contains the link's IRI. atom:link elements MUST have a href attribute, whose value MUST be a IRI reference [RFC3987].
   * </li>
   */
  public Link setHref(String href) {
    _href = href;
    return this;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * atom:link elements MAY have a "rel" attribute that indicates the link relation type. If the "rel" attribute is not present, the link element MUST be interpreted as if the link relation type is "alternate".
   */
  public String getRel() {
    return _rel;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * atom:link elements MAY have a "rel" attribute that indicates the link relation type. If the "rel" attribute is not present, the link element MUST be interpreted as if the link relation type is "alternate".
   */
  public Link setRel(String rel) {
    _rel = rel;
    return this;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * On the link element, the "type" attribute's value is an advisory media type; it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced. Note that the type attribute does not override the actual media type returned with the representation. Link elements MAY have a type attribute, whose value MUST conform to the syntax of a MIME media type [MIMEREG].
   */
  public String getType() {
    return _type;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * On the link element, the "type" attribute's value is an advisory media type; it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced. Note that the type attribute does not override the actual media type returned with the representation. Link elements MAY have a type attribute, whose value MUST conform to the syntax of a MIME media type [MIMEREG].
   */
  public Link setType(String type) {
    _type = type;
    return this;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "hreflang" attribute's content describes the language of the resource pointed to by the href attribute. When used together with the rel="alternate", it implies a translated version of the entry. Link elements MAY have an hreflang attribute, whose value MUST be a language tag [RFC3066].
   */
  public String getHreflang() {
    return _hreflang;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "hreflang" attribute's content describes the language of the resource pointed to by the href attribute. When used together with the rel="alternate", it implies a translated version of the entry. Link elements MAY have an hreflang attribute, whose value MUST be a language tag [RFC3066].
   */
  public Link setHreflang(String hreflang) {
    _hreflang = hreflang;
    return this;
  }
  
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "hreflang" attribute's content describes the language of the resource pointed to by the href attribute. When used together with the rel="alternate", it implies a translated version of the entry. Link elements MAY have an hreflang attribute, whose value MUST be a language tag [RFC3066].
   */
  public Link setHreflang(Locale hreflang) {
    _hreflang = hreflang.getLanguage();
    return this;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "title" attribute conveys human-readable information about the link. The content of the "title" attribute is Language-Sensitive. Link elements MAY have a title attribute. 
   */
  public String getTitle() {
    return _title;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "title" attribute conveys human-readable information about the link. The content of the "title" attribute is Language-Sensitive. Link elements MAY have a title attribute. 
   */
  public Link setTitle(String title) {
    _title = title;
    return this;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "length" attribute indicates an advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced. Note that the length attribute does not override the actual content length of the representation as reported by the underlying protocol. Link elements MAY have a length attribute.
   */
  public Integer getLength() {
    return _length;
  }
  /**
   * <b>Atom 1.0 only </b> <br/>
   * The "length" attribute indicates an advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced. Note that the length attribute does not override the actual content length of the representation as reported by the underlying protocol. Link elements MAY have a length attribute.
   */
  public Link setLength(Integer length) {
    _length = length;
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
  public Link setBase(String base) {
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
  public Link setLang(Locale lang) {
    _lang = lang.getLanguage();
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
  public Link setLang(String lang) {
    _lang = lang;
    return this;
  }
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    ValidationUtils.validateNotNull("href is required", _href);
    ValidationUtils.validateUri("href should be an valid uri", _href);
  }
}