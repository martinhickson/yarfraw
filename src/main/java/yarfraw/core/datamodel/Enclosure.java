package yarfraw.core.datamodel;

import java.net.URI;
import java.net.URISyntaxException;

import yarfraw.rss20.utils.Utils;

/**
 * Describes a media object that is attached to the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt More.}
 * <br/>
 * It has three required attributes. url says where the enclosure is located, length says how big it is in bytes, and type says what its type is, a standard MIME type.
 * <br/>
 * The url must be an http url.
 * <p/>
 * example: &lt;enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3" length="12216320" type="audio/mpeg" />
 * @author jliang
 *
 */
public class Enclosure extends AbstractBaseObject{
  private URI _url;
  private long _length;
  private String _mimeType;
  private String _value;
  public static Enclosure create(){
    return new Enclosure();
  }
  public Enclosure(){}
  /**
   * It has three required attributes. url says where the enclosure is located, length says how big it is in bytes, and type says what its type is, a standard MIME type.
   */  
  public Enclosure(URI url, long length, String mimeType, String value) {
    super();
    _url = url;
    _length = length;
    _mimeType = mimeType;
    _value = value;
  }

  /**
   * It has three required attributes. url says where the enclosure is located, length says how big it is in bytes, and type says what its type is, a standard MIME type.
   * @throws URISyntaxException 
   */  
  public Enclosure(String url, long length, String mimeType) throws URISyntaxException {
    super();
    setUrl(url);
    setLength(length);
    setMimeType(mimeType);
  }
  /**
   * where the enclosure is located,
   */
  public URI getUrl() {
    return _url;
  }
  /**
   * where the enclosure is located,
   */
  public Enclosure setUrl(URI url) {
    _url = url;
    return this;
  }
  
  public Enclosure setUrl(String url) throws URISyntaxException {
    if(url == null){
      _url = null;
    }else{
      _url = new URI(url.trim());
    }
    return this;
  }
  /**
   * how big it is in bytes
   */
  public long getLength() {
    return _length;
  }
  /**
   * how big it is in bytes
   */
  public Enclosure setLength(long length) {
    _length = length;
    return this;
  }
  /**
   * a standard MIME type
   */
  public String getMimeType() {
    return _mimeType;
  }
  /**
   * a standard MIME type
   */
  public Enclosure setMimeType(String mimeType) {
    _mimeType = mimeType;
    return this;
  }
  
  
  public String getValue() {
    return _value;
  }
  public Enclosure setValue(String value) {
    _value = value;
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    Utils.validateNotNull("Encloure: All fields in the enclosure object should be not null", _length, _mimeType, _url, _value);
  }
}