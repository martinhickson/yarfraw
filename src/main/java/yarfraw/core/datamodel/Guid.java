package yarfraw.core.datamodel;

import javax.xml.bind.JAXBElement;

import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TGuid;
import yarfraw.rss20.utils.Utils;

/**
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
 * @author jliang
 *
 */
public class Guid extends AbstractBaseObject{
  private String _guid;
  private Boolean _isPermaLink = true;
  public Guid(){}
  
  public static Guid create(){
    return new Guid();
  }
  public Guid(String guid, Boolean isPermaLink) {
    super();
    _guid = guid;
    _isPermaLink = isPermaLink;
  }


  public Guid(String guid) {
    super();
    _guid = guid;
  }
  public String getGuid() {
    return _guid;
  }
  public Guid setGuid(String guid) {
    _guid = guid;
    return this;
  }
  public Boolean isPermaLink() {
    return _isPermaLink;
  }
  public Guid setPermaLink(Boolean isPermaLink) {
    if(isPermaLink == null){
      _isPermaLink = true;
    }else{
      _isPermaLink = isPermaLink;
    }
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    Utils.validateNotNull("Guid: Guid value should not be null", _guid);
  }
  
  private TGuid toTGuid(){
    TGuid ret = new TGuid();
    ret.setIsPermaLink(_isPermaLink);
    ret.setValue(_guid);
    return ret;
  }
  
  public JAXBElement<TGuid> toTGuidJAXB(){
    return new ObjectFactory().createTRssItemGuid(toTGuid());
  }
}