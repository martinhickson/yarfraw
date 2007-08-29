package yarfraw.mapping.forward.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.AtomAttributes;
import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.AtomLink;
import yarfraw.core.datamodel.AtomTextAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.CategoryType;
import yarfraw.generated.atom10.elements.ContentType;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.EntryType;
import yarfraw.generated.atom10.elements.IconType;
import yarfraw.generated.atom10.elements.IdType;
import yarfraw.generated.atom10.elements.LinkType;
import yarfraw.generated.atom10.elements.ObjectFactory;
import yarfraw.generated.atom10.elements.PersonType;
import yarfraw.generated.atom10.elements.TextType;
/**
 * Util methods for mapping Yarfraw core model to Atom10 Jaxb model
 * @author jliang
 *
 */
public class Atom10MappingUtils{
  private static final ObjectFactory FACTORY = new ObjectFactory ();
  private static final Log LOG = LogFactory.getLog(Atom10MappingUtils.class);
  private Atom10MappingUtils(){}

  public static LinkType toLink(AtomLink link){
    LinkType ret = FACTORY.createLinkType();
    ret.setBase(link.getBase() == null?null:link.getBase().toString());
    ret.setLang(link.getLang() == null?null:link.getLang().getLanguage());
    if(link.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(link.getOtherAttributes());
    }
    ret.setHref(link.getHref());
    ret.setHreflang(link.getHreflang());
    ret.setLength(link.getLength() == null? null : new BigInteger(String.valueOf(link.getLength())));
    ret.setRel(link.getRel());
    ret.setTitle(link.getTitle());
    ret.setType(link.getType());
    return ret;
  }
  
  public  static EntryType toEntry(Item item) throws YarfrawException{
    EntryType ret = FACTORY.createEntryType();
    List<Object> elementList = ret.getAuthorOrCategoryOrContent();
    ObjectFactory factory = FACTORY;
    if(item.getOtherElements() != null){
      ret.getAuthorOrCategoryOrContent().addAll(item.getOtherElements());
    }
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }

    AtomAttributes attr = item.getAtomAttributes();
    if(attr != null){
      ret.setBase(attr.getBase()==null?null:attr.getBase().toString());
      ret.setLang(attr.getLang() == null? null:attr.getLang().getLanguage());
      if(attr.getOtherAttributes() != null){
        ret.getOtherAttributes().putAll(attr.getOtherAttributes());
      }
    }
    
    if(item.getAtomId() != null){
      elementList.add(factory.createEntryTypeId(toAtomId(item.getAtomId())));
    }
    
    if(item.getAuthor() != null){
      PersonType person = factory.createPersonType();
      person.getNameOrUriOrEmail().add(factory.createPersonTypeEmail(item.getAuthor()));
      elementList.add(factory.createEntryTypeAuthor(person));
    }

    if(item.getCategory() != null){
      for(Category c : item.getCategory()){
          if(c != null){
            elementList.add(factory.createEntryTypeCategory(toCategoryType(c)));
          }
        }
    }
//    not supported
    if(item.getComments() != null){
      LOG.info("Item.Comments field is not supported by Atom 1.0. It will be ignored");      
    }
    if(item.getDescription() != null  || item.getAtomTextAttributeByElement(AtomTextElementEnum.summary) != null){
      elementList.add(factory.createEntryTypeSummary(
              Atom10MappingUtils.toTextType(
                      item.getAtomTextAttributeByElement(AtomTextElementEnum.summary),
              item.getDescription())));
    }
    
    if(item.getRights() != null || item.getAtomTextAttributeByElement(AtomTextElementEnum.rights) != null){
      elementList.add(factory.createEntryTypeRights(
              Atom10MappingUtils.toTextType(
                      item.getAtomTextAttributeByElement(AtomTextElementEnum.rights),
              item.getRights())));
    }
//  not supported
    if(item.getEnclosure() != null){
      LOG.info("Item.Enclosure field is not supported by Atom 1.0. It will be ignored. Use Item.AtomLink to add enclosure element to item");
    }
//  not supported
    if(item.getGuid() != null){
      LOG.info("Item.Guid field is not supported by Atom 1.0. It will be ignored. Use Item.AtomId to add unique id to item");
    }
    
    //ignore, use atom link
    if(item.getLink() != null ){
      LOG.info("Item.Link field is not supported by Atom 1.0. It will be ignored. Use Item.AtomLink to add link elements to item");
    }

    for(AtomLink atomLink : item.getAtomLinks()){
      elementList.add(factory.createLink(toLink(atomLink)));
    }
    
    //partially supported
    if(item.getPubDate() != null){
      DateTimeType date = factory.createDateTimeType();
      date.setValue(toGCal(item.getPubDate()));
      elementList.add(factory.createEntryTypePublished(date));
    }
//not supported
    if(item.getSource() != null){
      LOG.info("Item.Source field is not supported by Atom 1.0. It will be ignored");
    }
    
    if(item.getTitle() != null  || item.getAtomTextAttributeByElement(AtomTextElementEnum.title) != null){
      elementList.add(factory.createEntryTypeTitle(
              Atom10MappingUtils.toTextType(
                      item.getAtomTextAttributeByElement(AtomTextElementEnum.title),
              item.getTitle())));
    }

    if(item.getContent() !=  null){
      elementList.add(factory.createEntryTypeContent(toContent(item.getContent())));
    }
    
    return ret;
  }
  
  public static ContentType toContent(Content content){
    ContentType ret = FACTORY.createContentType();
    ret.setBase(content.getBase() == null?null:content.getBase().toString());
    ret.setLang(content.getLang() == null?null:content.getLang().getLanguage());
    if(content.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(content.getOtherAttributes());
    }
    ret.setSrc(content.getSrc() == null? null: content.getSrc().toString());
    ret.getContent().addAll(content.getOtherElements());
    ret.getContent().addAll(content.getContentText());
    return ret;
  }
  
  public static IdType toAtomId(AtomId atomId){
    IdType ret = FACTORY.createIdType();
    ret.setBase(atomId.getBase() == null?null:atomId.getBase().toString());
    ret.setLang(atomId.getLang() == null?null:atomId.getLang().getLanguage());
    if(atomId.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(atomId.getOtherAttributes());
    }
    ret.setValue(atomId.getAtomUri());
    return ret;
  }
  
  public static CategoryType toCategoryType(Category cat){
    CategoryType ret = FACTORY.createCategoryType();
    ret.setTerm(cat.getCategory());
    ret.setScheme(cat.getDomainOrScheme());
    AtomAttributes attr = cat.getAtomAttributes();
    if(attr != null){
      ret.setBase(attr.getBase()==null?null:attr.getBase().toString());
      ret.setLang(attr.getLang() == null? null:attr.getLang().getLanguage());
      if(attr.getOtherAttributes() != null){
        ret.getOtherAttributes().putAll(attr.getOtherAttributes());
      }
    }
    
    return ret;
  }
  
  public static TextType toTextType(AtomTextAttributes attr, String content){
    TextType text = FACTORY.createTextType();
    if(attr != null){
      text.setBase(attr.getBase() == null?null:attr.getBase().toString());
      text.setLang(attr.getLang() == null?null:attr.getLang().getLanguage());
      text.setType(attr.getType() == null?null:attr.getType().name());
      if(attr.getXhtmlDiv() != null){
        text.getContent().add(attr.getXhtmlDiv());
      }
    }
    if(content != null){
      text.getContent().add(content);
    }

    return text;
  }

  public static IconType toIcon(Image image) {
    IconType icon = FACTORY.createIconType();
    icon.setValue(image.getUrl() == null?null: image.getUrl().toString());
    AtomAttributes attr = image.getAtomAttributes();
    if(attr != null){
      icon.setBase(attr.getBase()==null?null:attr.getBase().toString());
      icon.setLang(attr.getLang() == null? null:attr.getLang().getLanguage());
      if(attr.getOtherAttributes() != null){
        icon.getOtherAttributes().putAll(attr.getOtherAttributes());
      }
    }
    return icon;
  }
  
  public static XMLGregorianCalendar toGCal(Date date) throws YarfrawException{
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);    
    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(
              cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
              cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)));
    }
    catch (DatatypeConfigurationException e) {
      throw new YarfrawException("Unable to convert Date to XML Gregorian Calendar", e);
    }
  }
}