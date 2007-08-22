package yarfraw.core.datamodel;

import javax.xml.namespace.QName;


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
  private String _otherContent;
  
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

  public String getHref() {
    return _href;
  }
  public AtomLink setHref(String href) {
    _href = href;
    return this;
  }
  public String getRel() {
    return _rel;
  }
  public AtomLink setRel(String rel) {
    _rel = rel;
    return this;
  }
  public String getType() {
    return _type;
  }
  public AtomLink setType(String type) {
    _type = type;
    return this;
  }
  public String getHreflang() {
    return _hreflang;
  }
  public AtomLink setHreflang(String hreflang) {
    _hreflang = hreflang;
    return this;
  }
  public String getTitle() {
    return _title;
  }
  public AtomLink setTitle(String title) {
    _title = title;
    return this;
  }
  public Integer getLength() {
    return _length;
  }
  public AtomLink setLength(Integer length) {
    _length = length;
    return this;
  }

  public String getOtherContent() {
    return _otherContent;
  }

  public AtomLink setOtherContent(String otherContent) {
    _otherContent = otherContent;
    return this;
  }
  
  @Override
  public AtomLink addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
    return this;
  }
  
}