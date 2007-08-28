package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.namespace.QName;

import yarfraw.utils.CommonUtils;


/**
 * The "atom:link" element defines a reference from an entry or feed to a Web resource. 
 * This specification assigns no meaning to the content (if any) of this element.
 * 
 * @author jliang
 *
 */
public class AtomLink extends AtomAttributes{
  private String _href;
  private String _rel;
  private String _type;
  private String _hreflang;
  private String _title;
  private Integer _length;
  
  public AtomLink(){}

  public AtomLink(String href, String rel, String type, String hreflang,
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
   * The "href" attribute contains the link's IRI. atom:link elements MUST have a href attribute, whose value MUST be a IRI reference [RFC3987].
   */
  public String getHref() {
    return _href;
  }
  /**
   * The "href" attribute contains the link's IRI. atom:link elements MUST have a href attribute, whose value MUST be a IRI reference [RFC3987].
   */
  public AtomLink setHref(String href) {
    _href = href;
    return this;
  }
  /**
   * atom:link elements MAY have a "rel" attribute that indicates the link relation type. If the "rel" attribute is not present, the link element MUST be interpreted as if the link relation type is "alternate".
   */
  public String getRel() {
    return _rel;
  }
  /**
   * atom:link elements MAY have a "rel" attribute that indicates the link relation type. If the "rel" attribute is not present, the link element MUST be interpreted as if the link relation type is "alternate".
   */
  public AtomLink setRel(String rel) {
    _rel = rel;
    return this;
  }
  /**
   * On the link element, the "type" attribute's value is an advisory media type; it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced. Note that the type attribute does not override the actual media type returned with the representation. Link elements MAY have a type attribute, whose value MUST conform to the syntax of a MIME media type [MIMEREG].
   */
  public String getType() {
    return _type;
  }
  /**
   * On the link element, the "type" attribute's value is an advisory media type; it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced. Note that the type attribute does not override the actual media type returned with the representation. Link elements MAY have a type attribute, whose value MUST conform to the syntax of a MIME media type [MIMEREG].
   */
  public AtomLink setType(String type) {
    _type = type;
    return this;
  }
  /**
   * The "hreflang" attribute's content describes the language of the resource pointed to by the href attribute. When used together with the rel="alternate", it implies a translated version of the entry. Link elements MAY have an hreflang attribute, whose value MUST be a language tag [RFC3066].
   */
  public String getHreflang() {
    return _hreflang;
  }
  /**
   * The "hreflang" attribute's content describes the language of the resource pointed to by the href attribute. When used together with the rel="alternate", it implies a translated version of the entry. Link elements MAY have an hreflang attribute, whose value MUST be a language tag [RFC3066].
   */
  public AtomLink setHreflang(String hreflang) {
    _hreflang = hreflang;
    return this;
  }
  /**
   * The "title" attribute conveys human-readable information about the link. The content of the "title" attribute is Language-Sensitive. Link elements MAY have a title attribute. 
   */
  public String getTitle() {
    return _title;
  }
  /**
   * The "title" attribute conveys human-readable information about the link. The content of the "title" attribute is Language-Sensitive. Link elements MAY have a title attribute. 
   */
  public AtomLink setTitle(String title) {
    _title = title;
    return this;
  }
  /**
   * The "length" attribute indicates an advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced. Note that the length attribute does not override the actual content length of the representation as reported by the underlying protocol. Link elements MAY have a length attribute.
   */
  public Integer getLength() {
    return _length;
  }
  /**
   * The "length" attribute indicates an advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced. Note that the length attribute does not override the actual content length of the representation as reported by the underlying protocol. Link elements MAY have a length attribute.
   */
  public AtomLink setLength(Integer length) {
    _length = length;
    return this;
  }

  @Override
  public AtomLink addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
    return this;
  }
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format != FeedFormat.ATOM10){
      return ;
    }
    CommonUtils.validateNotNull("href is required", _href);
    try {
      @SuppressWarnings("unused")
      URI uri = new URI(_href);
    }
    catch (URISyntaxException e) {
      throw new ValidationException("href should be a valid uri");
    }
  }
}