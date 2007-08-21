package yarfraw.core.datamodel;

import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Atom text common constructs.
 * {@link http://atompub.org/2005/07/11/draft-ietf-atompub-format-10.html#rfc.section.3.1}
 * @author jliang
 *
 */
public class AtomTextAttributes extends AtomAttributes{
  public enum TextType{
    text, html, xhtml;
  }
  private TextType _type = TextType.text;
  public static AtomTextAttributes create(){
    return new AtomTextAttributes();
  }
  
  public AtomTextAttributes(){}

  @Override
  public AtomTextAttributes setBase(String base) {
    super.setBase(base);
    return this;
  }
  @Override
  public AtomTextAttributes setLang(Locale lang) {
    super.setLang(lang);
    return this;
  }
  
  @Override
  public AtomTextAttributes setOtherAttributes(Map<QName, String> otherAttributes) {
    super.setOtherAttributes(otherAttributes);
    return this;
  }
  
  public AtomTextAttributes(TextType textType){
    setType(textType);
  }
  
  public TextType getType() {
    return _type;
  }
  public AtomTextAttributes setType(TextType type) {
    _type = type;
    return this;
  }
  
}