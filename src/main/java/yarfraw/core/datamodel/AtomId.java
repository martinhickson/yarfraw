package yarfraw.core.datamodel;

import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;


/**
 * The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
 * @author jliang
 *
 */
public class AtomId extends AtomAttributes{
  private String _atomUri;

  public static AtomId create(){
    return new AtomId();
  }
  public AtomId(){}
  

  @Override
  public AtomId setBase(String base) {
    super.setBase(base);
    return this;
  }
  @Override
  public AtomId setLang(Locale lang) {
    super.setLang(lang);
    return this;
  }
  
  @Override
  public AtomId setOtherAttributes(Map<QName, String> otherAttributes) {
    super.setOtherAttributes(otherAttributes);
    return this;
  }
  
  
  public AtomId(String atomUri){
    setAtomUri(atomUri);
  }
  
  public String getAtomUri() {
    return _atomUri;
  }

  public AtomId setAtomUri(String atomUri) {
    _atomUri = atomUri;
    return this;
  }
  
}