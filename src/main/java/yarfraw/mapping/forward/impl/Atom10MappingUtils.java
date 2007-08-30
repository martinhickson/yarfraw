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

import yarfraw.core.datamodel.Id;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.core.datamodel.Text;
import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.ItemEntry;
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
import yarfraw.generated.atom10.elements.UriType;
import yarfraw.utils.CommonUtils;
/**
 * Util methods for mapping Yarfraw core model to Atom10 Jaxb model
 * @author jliang
 *
 */
public class Atom10MappingUtils{
  private static final ObjectFactory FACTORY = new ObjectFactory ();
  private static final Log LOG = LogFactory.getLog(Atom10MappingUtils.class);
  private Atom10MappingUtils(){}

  public static LinkType toLink(Link link){
    LinkType ret = FACTORY.createLinkType();
    ret.setBase(link.getBase());
    ret.setLang(link.getLang());
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
  
  public static PersonType toPersonType(Person p){
    PersonType ret = new PersonType();
    ret.getNameOrUriOrEmail().add(FACTORY.createPersonTypeEmail(p.getEmailOrText()));
    ret.getNameOrUriOrEmail().add(FACTORY.createPersonTypeName(p.getName()));
    if(p.getUri() != null){
      UriType uri = FACTORY.createUriType();
      uri.setValue(p.getUri());
      ret.getNameOrUriOrEmail().add(FACTORY.createPersonTypeUri(uri));
    }
    
    if(p.getOtherElements() != null){
      ret.getNameOrUriOrEmail().addAll(p.getOtherElements());
    }
    if(p.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(p.getOtherAttributes());
    }

    ret.setBase(p.getBase());
    ret.setLang(p.getLang());
    return ret;
  }
  
  /*
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
   */
  public  static EntryType toEntry(ItemEntry item) throws YarfrawException{
    EntryType ret = FACTORY.createEntryType();
    List<Object> elementList = ret.getAuthorOrCategoryOrContent();
    ObjectFactory factory = FACTORY;
    
    
    if(item.getAuthorOrCreator() != null){
      for(Person author : item.getAuthorOrCreator()){
        elementList.add(factory.createEntryTypeAuthor(toPersonType(author)));
      }
    }

    if(item.getCategorySubjects() != null){
      for(CategorySubject c : item.getCategorySubjects()){
          if(c != null){
            elementList.add(factory.createEntryTypeCategory(toCategoryType(c)));
          }
        }
    }

    if(item.getContent() !=  null){
      elementList.add(factory.createEntryTypeContent(toContent(item.getContent())));
    }
    
    if(item.getContributors() != null){
      for(Person c : item.getContributors()){
        elementList.add(factory.createEntryTypeContributor(toPersonType(c)));
      }
    }
    
    if(item.getUid() != null){
      elementList.add(factory.createEntryTypeId(toAtomId(item.getUid())));
    }
    
    
    if(item.getLinks() != null ){
      for(Link link : item.getLinks()){
        elementList.add(factory.createEntryTypeLink(toLink(link)));
      }
    }
    
    //partially supported
    if(item.getPubDate() != null){
      DateTimeType date = factory.createDateTimeType();
      date.setValue(toGCal(CommonUtils.tryParseDate(item.getPubDate())));
      elementList.add(factory.createEntryTypePublished(date));
    }
    
    if(item.getRights() != null){
      elementList.add(factory.createEntryTypeRights(
              Atom10MappingUtils.toTextType(item.getRights())));
    }
    
    //FIXME: atomSource is not supported
    
    if(item.getDescriptionOrSummary() != null ){
      elementList.add(factory.createEntryTypeSummary(
              toTextType(item.getDescriptionOrSummary())));
    }
    
//    not supported
    if(item.getComments() != null){
      LOG.info("Item.Comments field is not supported by Atom 1.0. It will be ignored");      
    }
    
    if(item.getTitle() != null){
      elementList.add(factory.createEntryTypeTitle(
              Atom10MappingUtils.toTextType(item.getTitle())));
    }
    
    //partially supported
    if(item.getUpdatedDate() != null){
      DateTimeType date = factory.createDateTimeType();
      date.setValue(toGCal(CommonUtils.tryParseDate(item.getUpdatedDate())));
      elementList.add(factory.createEntryTypeUpdated(date));
    }
    
//  not supported
    if(item.getEnclosure() != null){
      LOG.info("Item.Enclosure field is not supported by Atom 1.0. It will be ignored. Use Item.AtomLink to add enclosure element to item");
    }
    
    if(item.getSource() != null){
      LOG.info("Item.Source field is not supported by Atom 1.0. It will be ignored");
    }
    
    if(item.getOtherElements() != null){
      ret.getAuthorOrCategoryOrContent().addAll(item.getOtherElements());
    }
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }

    ret.setBase(item.getBase());
    ret.setLang(item.getLang());
          
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }
    return ret;
  }
  
  public static ContentType toContent(Content in){
    ContentType ret = FACTORY.createContentType();
    ret.setSrc(in.getSrc());
    ret.getContent().addAll(in.getContentText());
    
    if(in.getOtherElements() != null){
      ret.getContent().addAll(in.getOtherElements());
    }
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }

    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    return ret;
  }
  
  public static IdType toAtomId(Id in){
    IdType ret = FACTORY.createIdType();
    ret.setValue(in.getIdValue());
    
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }

    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    return ret;
  }
  
  public static CategoryType toCategoryType(CategorySubject in){
    CategoryType ret = FACTORY.createCategoryType();
    ret.setTerm(in.getCategoryOrSubjectOrTerm());
    ret.setScheme(in.getDomainOrScheme());
    ret.setLabel(in.getLabel());
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }
    
    return ret;
  }
  
  public static TextType toTextType(Text in){
    TextType ret = FACTORY.createTextType();
    
    if(in.getType() != null){
      ret.setType(in.getType().toString());
    }
    if(in.getText() != null){
      ret.getContent().add(in.getText());
    }
    if(in.getXhtmlDiv() != null){
      ret.getContent().add(in.getXhtmlDiv());
    }
    if(in.getOtherElements() != null){
      ret.getContent().addAll(in.getOtherElements());
    }
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }

    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    
    return ret;
  }

  public static IconType toIcon(Image in) {
    IconType ret = FACTORY.createIconType();
    ret.setValue(in.getUrl());
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }

    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    
    return ret;
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