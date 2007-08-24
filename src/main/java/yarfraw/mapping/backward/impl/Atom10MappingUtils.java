package yarfraw.mapping.backward.impl;

import java.net.URISyntaxException;
import java.util.Locale;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.AtomAttributes;
import yarfraw.core.datamodel.AtomContent;
import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.AtomLink;
import yarfraw.core.datamodel.AtomTextAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.generated.atom10.elements.CategoryType;
import yarfraw.generated.atom10.elements.ContentType;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.EntryType;
import yarfraw.generated.atom10.elements.IconType;
import yarfraw.generated.atom10.elements.IdType;
import yarfraw.generated.atom10.elements.LinkType;
import yarfraw.generated.atom10.elements.PersonType;
import yarfraw.generated.atom10.elements.TextType;
import yarfraw.utils.CommonUtils;

/**
 * TODO: document me
 * @author jliang
 *
 */
class Atom10MappingUtils{
  private static final String XHTML = "xhtml";
  private final static QName _PersonTypeEmail_QNAME = new QName("http://www.w3.org/2005/Atom", "email");
  private final static QName _EntryTypePublished_QNAME = new QName("http://www.w3.org/2005/Atom", "published");
  private final static QName _EntryTypeTitle_QNAME = new QName("http://www.w3.org/2005/Atom", "title");
  private final static QName _EntryTypeRights_QNAME = new QName("http://www.w3.org/2005/Atom", "rights");
  private final static QName _EntryTypeUpdated_QNAME = new QName("http://www.w3.org/2005/Atom", "updated");
  private final static QName _EntryTypeSummary_QNAME = new QName("http://www.w3.org/2005/Atom", "summary");
  private final static QName _EntryTypeAuthor_QNAME = new QName("http://www.w3.org/2005/Atom", "author");
  /**
   * Use this method with cautions, it checks the type of the input {@link TextType},
   * and automatically copy all the attributes from to input {@link AtomTextAttributes}.
   * <br /> 
   * if it's NOT of type xhtml, it assumes that there is only one element under the input  
   * {@link TextType} and put this single element to the input {@link AtomTextAttributes}.
   * Method returns null in this case.
   * <br/>
   * Otherwise (type = text or html), it extracts the content as string and returns it.
   *  
   * @param textAttr
   * @param text
   * @return null or the content of the input {@link TextType} depending on the type. 
   * 
   */
  public static String extractTextContent(AtomTextAttributes textAttr, TextType text){
    if(text == null){
      return null;
    }
    if(textAttr == null){
      throw new IllegalArgumentException("textAttr cannot be null");
    }
    textAttr.setType(text.getType() == null ? null :
      yarfraw.core.datamodel.AtomTextAttributes.TextType.valueOf(text.getType()));
    textAttr.setBase(text.getBase());
    textAttr.setLang(text.getLang() == null ? null : new Locale(text.getLang()));
    if(text.getOtherAttributes() != null){
      textAttr.getOtherAttributes().putAll(text.getOtherAttributes());
    }
    if(CollectionUtils.isEmpty(text.getContent())){
      return null;
    }
    if(text.getType() == null || !XHTML.equals(text.getType())){
      return String.valueOf(text.getContent().get(0));
    }else{
      if(text.getContent().get(0) instanceof Element){
        textAttr.setXhtmlDiv((Element)text.getContent().get(0));
      }
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  public static String extractEmail(PersonType person){
    if(person == null || CollectionUtils.isEmpty(person.getNameOrUriOrEmail())){
      return null;
    }
    for(Object o : person.getNameOrUriOrEmail()){
      if (o instanceof JAXBElement) {
        JAXBElement jaxb = (JAXBElement) o;
        if(CommonUtils.same(_PersonTypeEmail_QNAME, jaxb.getName())){
          return (String)jaxb.getValue();
        }
      }
    }
    return null;
  }
  
  public static Item toItem(EntryType entry) throws URISyntaxException{
    AtomAttributes attr = new AtomTextAttributes();
    attr.setBase(entry.getBase());
    attr.setLang(entry.getLang() == null ? null : new Locale(entry.getLang()));
    if(entry.getOtherAttributes() != null){
      attr.getOtherAttributes().putAll(entry.getOtherAttributes());
    }
    Item ret = new Item();
    ret.setAtomAttributes(attr);
    for(Object o : entry.getAuthorOrCategoryOrContent()){
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxb = (JAXBElement<?>) o;
        Object val = jaxb.getValue();
        
        if (CommonUtils.same(jaxb.getName(), _EntryTypeAuthor_QNAME)) {
          ret.setAuthor(extractEmail((PersonType)val));
        }else if(val instanceof CategoryType){
          ret.addCategory(toCategory((CategoryType)val));
        }else if(val instanceof ContentType){
          ContentType c = (ContentType)val;
          AtomContent content = new AtomContent();
          attr.setBase(c.getBase());
          attr.setLang(c.getLang() == null ? null : new Locale(c.getLang()));
          if(c.getOtherAttributes() != null){
            attr.getOtherAttributes().putAll(c.getOtherAttributes());
          }
          for(Object co : c.getContent()){
            if (co instanceof Element) {
              content.addOtherElement((Element)co);
            }else if(co instanceof String){
              content.addContentText((String)co);
            }else{
              //not sure what to do with these
            }
          }
          content.setSrc(c.getSrc());
          content.setType(c.getType() == null? null : yarfraw.core.datamodel.AtomTextAttributes.TextType.valueOf(c.getType()));
          ret.setAtomContent(content);
        }//contributor are ignored
        else if(val instanceof LinkType){ 
          ret.addAtomLink(toAtomLink((LinkType)val));
        }else if (CommonUtils.same(jaxb.getName(), _EntryTypePublished_QNAME)) {
          //partially supported
          DateTimeType dt = (DateTimeType) val;
          ret.setPubDate(dt.getValue().toGregorianCalendar().getTime());
        }else if (CommonUtils.same(jaxb.getName(), _EntryTypeRights_QNAME)) {
          TextType text = (TextType) val;
          ret.setRights(convenientExtractText(ret, AtomTextElementEnum.rights, text));
        }//source not supported
        else if (CommonUtils.same(jaxb.getName(), _EntryTypeSummary_QNAME)) {
          TextType text = (TextType) val;
          ret.setDescription(convenientExtractText(ret, AtomTextElementEnum.summary, text));
        }else if (CommonUtils.same(jaxb.getName(), _EntryTypeTitle_QNAME)) {
          TextType text = (TextType) val;
          ret.setTitle(convenientExtractText(ret, AtomTextElementEnum.title, text));
        }else if (CommonUtils.same(jaxb.getName(), _EntryTypeUpdated_QNAME)) {
          //partially supported
          DateTimeType dt = (DateTimeType) val;
          ret.setPubDate(dt.getValue().toGregorianCalendar().getTime());
        }else{
          //FIXME: ignore?
        }
      }else if (o instanceof Element) {
        Element e = (Element) o;
        ret.getOtherElements().add(e);
      }else{
        //FIXME not sure what to do yet
      }
    }
    return ret;
  }
  
  public static AtomLink toAtomLink(LinkType link){
    AtomLink ret = new AtomLink();
    ret.setBase(link.getBase());
    ret.setLang(link.getLang() == null ? null : new Locale(link.getLang()));
    if(link.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(link.getOtherAttributes());
    }
    ret.setOtherContent(link.getContent());
    ret.setHref(link.getHref());
    ret.setHreflang(link.getHreflang());
    ret.setLength(link.getLength() == null ? null : link.getLength().intValue());
    ret.setRel(link.getRel());
    ret.setTitle(link.getTitle());
    ret.setType(link.getType());
    
    return ret;
  }
  
  private static String convenientExtractText(Item item, AtomTextElementEnum textEnum, TextType text){
    AtomTextAttributes textAttr = new AtomTextAttributes();
    String ret = extractTextContent(textAttr, text);
    if(textAttr.getBase() != null || textAttr.getLang() != null || textAttr.getOtherAttributes() != null
            || textAttr.getXhtmlDiv() != null || textAttr.getType() != null ){
      item.putAtomTextAttribute(textEnum, textAttr);
    }
    return ret;
  }
  
  private final static QName CAT_LABEL_QNAME = new QName("http://www.w3.org/2005/Atom", "label");
  public static Category toCategory(CategoryType cat){
    AtomAttributes attr = new AtomTextAttributes();
    attr.setBase(cat.getBase());
    attr.setLang(cat.getLang() == null ? null : new Locale(cat.getLang()));
    if(cat.getOtherAttributes() != null){
      attr.getOtherAttributes().putAll(cat.getOtherAttributes());
    }
    Category ret = new Category();
    ret.setAtomAttributes(attr);
    ret.setCategory(cat.getTerm());
    ret.setDomain(cat.getScheme());
    attr.getOtherAttributes().put(CAT_LABEL_QNAME, cat.getLabel());
    
    return ret;
  }
  public static AtomId toAtomId(IdType id){
    AtomId ret = new AtomId();
    ret.setBase(id.getBase());
    ret.setLang(id.getLang() == null ? null : new Locale(id.getLang()));
    if(id.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(id.getOtherAttributes());
    }
    ret.setAtomUri(id.getValue());
    return ret;
  }
  public static Image toImage(IconType icon){
    Image image = new Image();
    AtomAttributes attr = new AtomTextAttributes();
    attr.setBase(icon.getBase());
    attr.setLang(icon.getLang() == null ? null : new Locale(icon.getLang()));
    if(icon.getOtherAttributes() != null){
      attr.getOtherAttributes().putAll(icon.getOtherAttributes());
    }
    image.setAtomAttributes(attr);
    try {
      image.setLink(icon.getValue());
    }
    catch (URISyntaxException e) {
      //it's not required to be a valid link, but generally it should be
    }
    
    return image;
  }

}