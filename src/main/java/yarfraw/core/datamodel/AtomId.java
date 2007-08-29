package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
 * <br/>
 * This class is only used when reading/writing to Atom format, the other {@link FeedFormat} will
 * ignore it.
 * 
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
  @Override
  public AtomId addOtherAttributes(QName namespace, String attribute) {
    super.addOtherAttributes(namespace, attribute);
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
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format != FeedFormat.ATOM10){
      return ;
    }
    try {
      @SuppressWarnings("unused")
      URI uri = new URI(_atomUri);
    }
    catch (URISyntaxException e) {
      throw new ValidationException("Atom uri should be a valid uri");
    }
  }
}