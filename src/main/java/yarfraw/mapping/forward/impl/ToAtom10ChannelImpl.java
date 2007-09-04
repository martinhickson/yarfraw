package yarfraw.mapping.forward.impl;

import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toAtomId;
import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toCategoryType;
import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toEntry;
import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toIcon;
import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toLink;
import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toPersonType;
import static yarfraw.mapping.forward.impl.Atom10MappingUtils.toTextType;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Generator;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.generated.atom10.elements.GeneratorType;
import yarfraw.generated.atom10.elements.ObjectFactory;
import yarfraw.mapping.forward.ToAtom10Channel;
import yarfraw.utils.CommonUtils;
/**
 * Util methods for mapping Yarfraw core model to Atom10 Jaxb model
 * @author jliang
 *
 */
public class ToAtom10ChannelImpl implements ToAtom10Channel{
  private static final Log LOG = LogFactory.getLog(ToAtom10ChannelImpl.class);
  private static ToAtom10Channel _instance = new ToAtom10ChannelImpl();
  private static final ObjectFactory FACTORY = new ObjectFactory();

  public static ToAtom10Channel getInstance(){
    return _instance;
  }
  private ToAtom10ChannelImpl(){}
  
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
   */
  public JAXBElement<FeedType> execute(ChannelFeed ch) throws YarfrawException {
    ObjectFactory factory = FACTORY;
    FeedType ret = factory.createFeedType();
    List<Object> elementList = ret.getAuthorOrCategoryOrContributor();
    
    if(ch.getUid() != null){
      elementList.add(factory.createEntryTypeId(toAtomId(ch.getUid())));
    }
    
    if(ch.getManagingEditorOrAuthorOrPublisher() != null){
      for(Person author : ch.getManagingEditorOrAuthorOrPublisher()){
        elementList.add(factory.createFeedTypeAuthor(toPersonType(author)));
      }
    }
    

    if(ch.getCategorySubjects() != null){
      for(CategorySubject c : ch.getCategorySubjects()){
          if(c != null){
            elementList.add(factory.createFeedTypeCategory(toCategoryType(c)));
          }
        }
    }
    
    if(ch.getContributors() != null){
      for(Person c : ch.getContributors()){
        elementList.add(factory.createFeedTypeContributor(toPersonType(c)));
      }
    }
    
    if(ch.getGenerator() != null){
      GeneratorType g = factory.createGeneratorType();
      Generator in = ch.getGenerator();
      g.setUri(in.getUri());
      g.setValue(in.getValue());
      g.setVersion(in.getVersion());
      
      if(in.getOtherAttributes() != null){
        g.getOtherAttributes().putAll(in.getOtherAttributes());
      }

      g.setBase(in.getBase());
      g.setLang(in.getLang());
      elementList.add(factory.createFeedTypeGenerator(g));
    }
    
    if(ch.getImageOrIcon() != null){
      elementList.add(factory.createFeedTypeIcon(toIcon(ch.getImageOrIcon())));
    }
    
    if(ch.getUid() != null){
      elementList.add(factory.createFeedTypeId(toAtomId(ch.getUid())));
    }
    
    if(ch.getLinks() != null ){
      for(Link link : ch.getLinks()){
        elementList.add(factory.createFeedTypeLink(toLink(link)));
      }
    }
    
    if(ch.getLogo() != null){
      elementList.add(factory.createFeedTypeIcon(toIcon(ch.getLogo())));
    }
    
    if(ch.getRights() != null){
      elementList.add(factory.createFeedTypeRights(
              toTextType(ch.getRights())));
    }
    
    if(ch.getDescriptionOrSubtitle() != null){
      elementList.add(factory.createFeedTypeSubtitle(
              toTextType(ch.getDescriptionOrSubtitle())));
    }
    
    if(ch.getTitle() != null){
      elementList.add(factory.createFeedTypeTitle(
              toTextType(ch.getTitle())));
    }
    
    if(ch.getLastBuildOrUpdatedDate() != null){
      DateTimeType date = factory.createDateTimeType();
      String dateString = ch.getLastBuildOrUpdatedDate();
      if(!CommonUtils.isDateFormatValid(dateString, FeedFormat.ATOM10)){
        String newDateString = CommonUtils.formatDate(CommonUtils.tryParseDate(dateString), FeedFormat.ATOM10);
        if(newDateString != null){
          dateString = newDateString;
        }else{
          LOG.warn("The dateString "+dateString+" is in valid according to ATOM 1.0 specs, unabel to convert it to a valid format, writing it as is");
        }
      }
      date.setValue(dateString);
      elementList.add(factory.createFeedTypeUpdated(date));
    }
    
    //partially supported
    if(ch.getPubDate() != null){
      LOG.info("PubDate under <feed> level is not supported, it will be ignored");
    }
    
    if(ch.getItems() != null){
      for(ItemEntry item : ch.getItems()){
        elementList.add(factory.createFeedTypeEntry(toEntry(item)));
      }
    }

    ret.setBase(ch.getBase());
    ret.setLang(ch.getLang());
    
    if(ch.getOtherElements() != null){
      elementList.addAll(ch.getOtherElements());
    }
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    return factory.createFeed(ret);
  }

}