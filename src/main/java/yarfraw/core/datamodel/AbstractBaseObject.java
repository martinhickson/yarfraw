package yarfraw.core.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.w3c.dom.Element;

import yarfraw.utils.CommonUtils;
/**
 * An abstract base object for the core data model
 */
abstract class AbstractBaseObject{
  protected String _base;
  protected String _lang;
  protected String _resource;
  protected String _about;
  //this is initial to 0 in the interest of saving space since most of the time
  //these elements are expectted to be empty
  protected Map<QName, String> _otherAttributes = new HashMap<QName, String>(0);
  protected List<Element> _otherElements = new ArrayList<Element>(0);
  
  /**
   * This maps to the 'base' attribute that is common in all Atom 1.0 elements.
   * Other {@link FeedFormat} will ignore this attribute.
   * 
   * @return - attribute value.
   */
  public String getBase() {
    return _base;
  }
  /**
   * The language attribute indicates the language that is used by the enclosed
   * element. 
   * 
   * @return - attribute value.
   */
  public String getLang() {
    return _lang;
  }

  /**
   * The language attribute indicates the language that is used by the enclosed
   * element. 
   * 
   * @return - a new Locale Object by parsing the lang attribute.
   */
  public Locale getLangAsLocale() {
    if(_lang == null)
      return null;
    
    return new Locale(_lang);
  }
  
  /**
   * This maps to the optional 'resource' attribute that present in some Rss 1.0 elements.
   * Other {@link FeedFormat} will ignore this attribute.
   * 
   * @return - attribute value.
   */
  public String getResource() {
    return _resource;
  }

  /**
   * This maps to the required 'about' attribute that present of all second level elements
   * (channel, image, item, and textinput).
   * Other {@link FeedFormat} will ignore this attribute.
   * 
   * @return - attribute value.
   */
  public String getAbout() {
    return _about;
  }

  /**
   * Other additional elements that are not in the Rss specs.
   */
  public List<Element> getOtherElements() {
    return _otherElements;
  }

  /**
   * Search through the other element list and return the first element that matches
   * both input the namespaceURI and the localName.
   * 
   * @param namespaceURI - namespaceURI of the element to be search for
   * @param localName - localName of the element
   * @return - null if no matching element is found,
   * the matching element otherwise.
   */
  public Element getElementByNS(String namespaceURI, String localName){
    return CommonUtils.getElementByNS(_otherElements, namespaceURI, localName);
  }
  
  /**
   * Any other attribute that is not in the RSS specs.
   */
  public Map<QName, String> getOtherAttributes() {
    return _otherAttributes;
  }
  
  /**
   * Search for attributes that are not in the spec by its local name.
   * @param localName localName of the attribute
   * @return null if attribute is not found, the value of the attribute otherwise
   */
  public String getAttributeValueByLocalName(String localName){
    if(_otherAttributes != null && localName != null){
      for(Entry<QName, String> e : _otherAttributes.entrySet()){
        if(localName.equals(e.getKey().getLocalPart())){
          return e.getValue();
        }
      }
    }
    return null;
  }
  /**
   * Search for attributes that are not in the spec by its {@link QName}.
   * @param name {@link QName} of the attribute
   * @return null if attribute is not found, the value of the attribute otherwise
   */
  public String getAttributeValueByQName(QName name){
    if(_otherAttributes != null){
      return _otherAttributes.get(name);
    }
    return null;
  }
  
  @Override
  public String toString(){
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  @Override
  public boolean equals(Object other){
    return EqualsBuilder.reflectionEquals(this, other);
  }
  @Override
  public int hashCode(){
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public abstract void validate(FeedFormat format) throws ValidationException;
}