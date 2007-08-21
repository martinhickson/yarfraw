package yarfraw.mapping.forward.impl;

import java.util.List;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.AtomAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.generated.atom10.elements.GeneratorType;
import yarfraw.generated.atom10.elements.LinkType;
import yarfraw.generated.atom10.elements.ObjectFactory;
import yarfraw.generated.atom10.elements.PersonType;
import yarfraw.mapping.forward.ToAtom10Channel;

public class ToAtom10ChannelImpl implements ToAtom10Channel{
  private static ToAtom10Channel _instance = new ToAtom10ChannelImpl();
  private static final ObjectFactory FACTORY = new ObjectFactory();

  public static final ToAtom10Channel getInstance(){
    return _instance;
  }
  private ToAtom10ChannelImpl(){}
  
  public JAXBElement<FeedType> execute(Channel ch) throws YarfrawException {
    ObjectFactory factory = FACTORY;
    FeedType ret = factory.createFeedType();
    List<Object> elementList = ret.getAuthorOrCategoryOrContributor();
    if(ch.getOtherElements() != null){
      elementList.addAll(ch.getOtherElements());
    }
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }

    if(ch.getAtomId() != null){
      elementList.add(factory.createEntryTypeId(Atom10MappingUtils.toAtomId(ch.getAtomId())));
    }
    
    AtomAttributes attr = ch.getAtomAttributes();
    if(attr != null){
      ret.setBase(attr.getBase()==null?null:attr.getBase().toString());
      ret.setLang(attr.getLang() == null? null:attr.getLang().getLanguage());
      if(attr.getOtherAttributes() != null){
        ret.getOtherAttributes().putAll(attr.getOtherAttributes());
      }
    }
    
    if(ch.getCategory() != null){
      for(Category c : ch.getCategory()){
        if(c != null){
          elementList.add(factory.createFeedTypeCategory(Atom10MappingUtils.toCategoryType(c)));
        }
      }
    }
    
    //NOT SUPPORTED
//  if(ch.getCloud() != null){
//    elementList.add(Rss20MappingUtils.toRss20Cloud(ch.getCloud()));
//  }
  
    if(ch.getCopyright() != null){
      elementList.add(factory.createFeedTypeRights(
              Atom10MappingUtils.toTextType(
                      ch.getAtomTextAttributeByElement(AtomTextElementEnum.rights),
              ch.getCopyright())));
    }
    
    if(ch.getDescription() != null){
      elementList.add(factory.createFeedTypeSubtitle(
              Atom10MappingUtils.toTextType(
                      ch.getAtomTextAttributeByElement(AtomTextElementEnum.subtitle),
              ch.getDescription())));
    }

    //NOT SUPPORTED
//      if(ch.getDocs() != null){
//        elementList.add(factory.createTRssChannelDocs(ch.getDocs().toString()));
//      }
    //Partially SUPPORTED
    if(ch.getGenerator() != null){
      GeneratorType gen = factory.createGeneratorType();
      gen.setValue(ch.getGenerator());
      elementList.add(factory.createFeedTypeGenerator(gen));
    }

    if(ch.getImage() != null){
      elementList.add(factory.createFeedTypeIcon(Atom10MappingUtils.toIcon(ch.getImage())));
    }

    //already cover by atom attribute
    if(ch.getLanguage() != null){
      ret.setLang(ch.getLanguage().getLanguage());
    }

    //partially supported
    if(ch.getLink() != null){
      LinkType link = factory.createLinkType();
      link.setHref(ch.getLink().toString());
      elementList.add(factory.createFeedTypeLink(link));
    }
    
    //not supported
    
//  if(ch.getLastBuildDate() != null){
//    elementList.add(factory.createDate(format.format(ch.getLastBuildDate())));
//  }
  
    //partially supported
    if(ch.getManagingEditor() != null){
      PersonType person = factory.createPersonType();
      person.getNameOrUriOrEmail().add(factory.createPersonTypeEmail(ch.getManagingEditor()));
      elementList.add(factory.createFeedTypeAuthor(person));
    }
    
    //partially supported
    if(ch.getPubDate() != null){
      DateTimeType date = factory.createDateTimeType();
      date.setValue(Atom10MappingUtils.toGCal(ch.getPubDate()));
      elementList.add(factory.createFeedTypeUpdated(date));
    }
//  not supported
//  if(ch.getSkipDays() != null){
//    TSkipDaysList tdl = new TSkipDaysList();
//    for(Day day : ch.getSkipDays()){
//      tdl.getDay().add(TSkipDay.fromValue(day.toString()));
//    }
//    elementList.add(new ObjectFactory().createSkipDays( tdl));
//  }
//
//  if(ch.getSkipHours() != null){
//    TSkipHoursList thl = new TSkipHoursList();
//    thl.getHour().addAll(ch.getSkipHours());
//    elementList.add(new ObjectFactory().createSkipHours( thl));
//  }
//not supported
//    if(ch.getTexInput() != null){
//      elementList.add(Rss10MappingUtils.toRss10TextInput(ch.getTexInput()));      
//    }

    if(ch.getTitle() != null){
      elementList.add(factory.createFeedTypeTitle(
              Atom10MappingUtils.toTextType(
                      ch.getAtomTextAttributeByElement(AtomTextElementEnum.title),
              ch.getTitle())));
    }

    if(ch.getItems() != null){
      for(Item item : ch.getItems()){
        elementList.add(factory.createFeedTypeEntry(Atom10MappingUtils.toEntry(item)));
      }
    }
    
    //not supported
//    if(ch.getTtl() != null){
//      
//    }
//    if(ch.getWebMaster() != null){
//      
//    }

    return factory.createFeed(ret);
  }

}