package yarfraw.mapping.backward.impl;

import static yarfraw.io.parser.ElementQName.ATOM10_AUTHOR;
import static yarfraw.io.parser.ElementQName.ATOM10_CONTRIBUTOR;
import static yarfraw.io.parser.ElementQName.ATOM10_EMAIL;
import static yarfraw.io.parser.ElementQName.ATOM10_NAME;
import static yarfraw.io.parser.ElementQName.ATOM10_PUBLISHED;
import static yarfraw.io.parser.ElementQName.ATOM10_RIGHTS;
import static yarfraw.io.parser.ElementQName.ATOM10_SUMMARY;
import static yarfraw.io.parser.ElementQName.ATOM10_TITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_UPDATED;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.Id;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.core.datamodel.Text;
import yarfraw.generated.atom10.elements.CategoryType;
import yarfraw.generated.atom10.elements.ContentType;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.EntryType;
import yarfraw.generated.atom10.elements.IconType;
import yarfraw.generated.atom10.elements.IdType;
import yarfraw.generated.atom10.elements.LinkType;
import yarfraw.generated.atom10.elements.PersonType;
import yarfraw.generated.atom10.elements.TextType;
import yarfraw.generated.atom10.elements.UriType;
import yarfraw.utils.CommonUtils;

/**
 * TODO: document me
 * @author jliang
 *
 */
class Atom10MappingUtils{

  private static final Log LOG = LogFactory.getLog(Atom10MappingUtils.class);
  
  public static Text toText(TextType in){
    Text ret = new Text();
    for(Object o : in.getContent()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxb = (JAXBElement<?>) o;
        Object val = jaxb.getValue();
        ret.setText(String.valueOf(val));
      }else if(o instanceof Element){
        ret.setXhtmlDiv((Element)o);
      }else{
        ret.setText(String.valueOf(o));
      }
    }
    
    ret.setLang(in.getLang());
    ret.setBase(in.getBase());
    ret.getOtherAttributes().putAll(in.getOtherAttributes());
    
    return ret;
  }
  
  public static CategorySubject toCategorySubject(CategoryType in) {
    CategorySubject ret = new CategorySubject();
    ret.setLabel(in.getLabel());
    ret.setCategoryOrSubjectOrTerm(in.getTerm());
    ret.setDomainOrScheme(in.getScheme());
    
    ret.setLang(in.getLang());
    ret.setBase(in.getBase());
    ret.getOtherAttributes().putAll(in.getOtherAttributes());
    return ret;
  }
  
  public static Person toPersonType(PersonType in){
    Person ret = new Person();
    
    for(Object o : in.getNameOrUriOrEmail()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxb = (JAXBElement<?>) o;
        Object val = jaxb.getValue();
        if(CommonUtils.same(jaxb.getName(), ATOM10_EMAIL)){
          ret.setEmailOrText((String)val);
        }else if(CommonUtils.same(jaxb.getName(), ATOM10_NAME)){
          ret.setName((String)val);
        }else if(val instanceof UriType){
          ret.setUri(((UriType)val).getValue());
        }else{
          LOG.warn("Unexpected JAXB Element: "+ ToStringBuilder.reflectionToString(val));
        }
      }else if(o instanceof Element){
        ret.getOtherElements().add((Element)o);
      }else{
        LOG.warn("Unexpected Element: "+ ToStringBuilder.reflectionToString(o));
      }
    }
    
    ret.setLang(in.getLang());
    ret.setBase(in.getBase());
    ret.getOtherAttributes().putAll(in.getOtherAttributes());
    
    return ret;
  }
  
  private static Content toContent(ContentType in){
    Content ret = new Content();
    for(Object o : in.getContent()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxb = (JAXBElement<?>) o;
        Object val = jaxb.getValue();
        ret.addContentText(String.valueOf(val));
      }else if(o instanceof Element){
        ret.getOtherElements().add((Element)o);
      }else{
        String s = String.valueOf(o);
        if(StringUtils.isNotBlank(s)){
          ret.addContentText(s);
        }
      }
    }
    
    ret.setSrc(in.getSrc());
    ret.setType(in.getType());
    ret.setLang(in.getLang());
    ret.setBase(in.getBase());
    ret.getOtherAttributes().putAll(in.getOtherAttributes());
    return ret;
  }
  
  /**
   * atomEntry =
   element atom:entry {
      atomCommonAttributes,
      (atomAuthor*
       & atomCategory*
       & atomContent?
       & atomContributor*
       & atomId
       & atomLink*
       & atomPublished?
       & atomRights?
       & atomSource?
       & atomSummary?
       & atomTitle
       & atomUpdated
       & extensionElement*)
   }
   * @param in
   * @return
   */
  public static ItemEntry toItem(EntryType in){
    
    ItemEntry ret = new ItemEntry();

    ret.setLang(in.getLang());
    ret.setBase(in.getBase());
    ret.getOtherAttributes().putAll(in.getOtherAttributes());
    
    for(Object o : in.getAuthorOrCategoryOrContent()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxb = (JAXBElement<?>) o;
        Object val = jaxb.getValue();
        
        if (CommonUtils.same(jaxb.getName(), ATOM10_AUTHOR)) {
          ret.addAuthorOrCreator(toPersonType((PersonType)val));
        }else if(val instanceof CategoryType){
          ret.addCategorySubject(toCategorySubject((CategoryType)val));
        }else if(val instanceof ContentType){
          ret.setContent(toContent((ContentType)val));
        }else if(CommonUtils.same(jaxb.getName(), ATOM10_CONTRIBUTOR)){
          ret.addContributor(toPersonType((PersonType)val));
        }
        //contributor are ignored
        else if(val instanceof LinkType){ 
          ret.addLink(toAtomLink((LinkType)val));
        }else if (CommonUtils.same(jaxb.getName(), ATOM10_PUBLISHED)) {
          //partially supported
          DateTimeType dt = (DateTimeType) val;
          if(dt.getValue() != null){
            ret.setPubDate(dt.getValue());
          }
        }else if (CommonUtils.same(jaxb.getName(), ATOM10_RIGHTS)) {
          TextType text = (TextType) val;
          ret.setRights(toText(text));
        }//FIXME: source not supported
        else if (CommonUtils.same(jaxb.getName(), ATOM10_SUMMARY)) {
          TextType text = (TextType) val;
          ret.setDescriptionOrSummary(toText(text));
        }else if (CommonUtils.same(jaxb.getName(), ATOM10_TITLE)) {
          TextType text = (TextType) val;
          ret.setTitle(toText(text));
        }else if (CommonUtils.same(jaxb.getName(), ATOM10_UPDATED)) {
          //  partially supported
          DateTimeType dt = (DateTimeType) val;
          if(dt.getValue() != null){
            ret.setUpdatedDate(dt.getValue());
          }
        }else if(val instanceof IdType){
          ret.setUid(toId((IdType)val));
        }else{
          LOG.warn("Unexpected JAXB Element: "+ ToStringBuilder.reflectionToString(val));
        }
      }else if (o instanceof Element) {
        Element e = (Element) o;
        ret.getOtherElements().add(e);
      }else{
        LOG.warn("Unexpected Element: "+ ToStringBuilder.reflectionToString(o));
      }
    }
    return ret;
  }
  
  public static Link toAtomLink(LinkType link){
    Link ret = new Link();
    ret.setBase(link.getBase());
    ret.setLang(link.getLang());
    if(link.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(link.getOtherAttributes());
    }

    ret.setHref(link.getHref());
    ret.setHreflang(link.getHreflang());
    ret.setLength(link.getLength() == null ? null : link.getLength().intValue());
    ret.setRel(link.getRel());
    ret.setTitle(link.getTitle());
    ret.setType(link.getType());
    
    return ret;
  }
  
  public static Id toId(IdType in){
    Id ret = new Id();
    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }
    ret.setIdValue(in.getValue());
    return ret;
  }
  
  public static Image toImage(IconType in){
    Image ret= new Image();
    ret.setUrl(in.getValue());
    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }
    
    return ret;
  }

}