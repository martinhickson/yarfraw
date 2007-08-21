package yarfraw.core.datamodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Atom 1.0 additional attributes to the feed elements.
 * 
 * @author jliang
 *
 */
public class AtomAttributes extends AbstractBaseObject{
  private String _base;
  private Locale _lang;
  private Map<QName, String> _otherAttributes = new HashMap<QName, String>();
  
  
  public AtomAttributes() {}
  public static AtomAttributes create(){
    return new AtomAttributes();
  }
  public String getBase() {
    return _base;
  }
  public AtomAttributes setBase(String base) {
    _base = base;
    return this;
  }
  

  public Locale getLang() {
    return _lang;
  }
  public AtomAttributes setLang(Locale lang) {
    _lang = lang;
    return this;
  }
  public Map<QName, String> getOtherAttributes() {
    return _otherAttributes;
  }
  public AtomAttributes setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    
  }

}