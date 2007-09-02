package yarfraw.mapping.forward.impl;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.Day;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.generated.rss20.elements.TSkipDay;
import yarfraw.generated.rss20.elements.TSkipDaysList;
import yarfraw.generated.rss20.elements.TSkipHoursList;
import yarfraw.mapping.forward.ToRss20Channel;
import yarfraw.utils.CommonUtils;

/**
 * Util methods for mapping Yarfraw core model to Rss20 Jaxb model
 * @author jliang
 *
 */
public class ToRss20ChannelImpl implements ToRss20Channel{
  private static final Log LOG = LogFactory.getLog(ToRss20ChannelImpl.class);
   private static ToRss20Channel _instance = new ToRss20ChannelImpl();
   
   public static ToRss20Channel getInstance(){
     return _instance;
   }
  
   private ToRss20ChannelImpl(){}
  public JAXBElement<TRssChannel> execute(ChannelFeed channel) throws YarfrawException {
    return new ObjectFactory().createChannel(toChannel(channel));
  }
  
  private TRssChannel toChannel(ChannelFeed ch){
    ObjectFactory factory = new ObjectFactory();
    TRssChannel ret = factory.createTRssChannel();
    List<Object> elementList = ret.getTitleOrLinkOrDescription();
    if(ch.getOtherElements() != null){
      ret.getAny().addAll(ch.getOtherElements());
    }
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    if(ch.getCategorySubjects() != null){
      for(CategorySubject c : ch.getCategorySubjects()){
        if(c != null){
          elementList.add(Rss20MappingUtils.toRss20Category(c));
        }
      }
    }
    
    if(ch.getCloud() != null){
      elementList.add(Rss20MappingUtils.toRss20Cloud(ch.getCloud()));
    }

    if(ch.getRightsText() != null){
      elementList.add(factory.createTRssChannelCopyright(ch.getRightsText()));
    }
    
    if(ch.getDescriptionOrSubtitleText() != null){
      elementList.add(factory.createTRssChannelDescription(ch.getDescriptionOrSubtitleText()));
    }

    if(ch.getDocs() != null){
      elementList.add(factory.createTRssChannelDocs(ch.getDocs().toString()));
    }
    
    if(ch.getGenerator() != null){
      String generator = ch.getGenerator().getValue();
      elementList.add(factory.createTRssChannelGenerator(generator));
    }

    if(ch.getImageOrIcon() != null){
      elementList.add(Rss20MappingUtils.toRss20Image(ch.getImageOrIcon()));
    }
    
    if(ch.getItems() != null){
      for(ItemEntry t : ch.getItems()){
        if(t != null){
          ret.getItem().add(Rss20MappingUtils.ToRss20Item(t).getValue());
        }
      }
    }
    
    if(ch.getLang() != null){
      elementList.add(factory.createTRssChannelLanguage(ch.getLang()));
    }
    String link = Utils.getHrefLink(ch.getLinks());
    if(link != null){
      elementList.add(factory.createTRssChannelLink(link));
    }
    
    if(ch.getLastBuildOrUpdatedDate() != null){
      String dateString = ch.getLastBuildOrUpdatedDate();
      if(!CommonUtils.isDateFormatValid(dateString, FeedFormat.RSS20)){
        String newDateString = CommonUtils.formatDate(CommonUtils.tryParseDate(dateString), FeedFormat.RSS20);
        if(newDateString != null){
          dateString = newDateString;
        }else{
          LOG.warn("The dateString "+dateString+" is in valid according to RSS 1.0 specs, unabel to convert it to a valid format, writing it as is");
        }
      }
      elementList.add(factory.createTRssChannelLastBuildDate(dateString));
    }
    
    String editor = Utils.getEmailOrText(ch.getManagingEditorOrAuthorOrPublisher());
    if(editor != null){
      elementList.add(factory.createTRssChannelManagingEditor(editor));
    }
    
    if(ch.getPubDate() != null){
      String dateString = ch.getPubDate();
      if(!CommonUtils.isDateFormatValid(dateString, FeedFormat.RSS20)){
        String newDateString = CommonUtils.formatDate(CommonUtils.tryParseDate(dateString), FeedFormat.RSS20);
        if(newDateString != null){
          dateString = newDateString;
        }else{
          LOG.warn("The dateString "+dateString+" is in valid according to RSS 1.0 specs, unabel to convert it to a valid format, writing it as is");
        }
      }
      elementList.add(factory.createTRssChannelPubDate(dateString));
    }

    if(ch.getSkipDays() != null){
      TSkipDaysList tdl = new TSkipDaysList();
      for(Day day : ch.getSkipDays()){
        tdl.getDay().add(TSkipDay.fromValue(day.toString()));
      }
      elementList.add(new ObjectFactory().createSkipDays( tdl));
    }

    if(ch.getSkipHours() != null){
      TSkipHoursList thl = new TSkipHoursList();
      thl.getHour().addAll(ch.getSkipHours());
      elementList.add(new ObjectFactory().createSkipHours( thl));
    }
    
    if(ch.getTexInput() != null){
      elementList.add(Rss20MappingUtils.toRss20TextInput(ch.getTexInput()));      
    }

    if(ch.getTitleText() != null){
      elementList.add(factory.createTRssChannelTitle(ch.getTitleText()));
    }

    if(ch.getTtl() != null){
      elementList.add(factory.createTRssChannelTtl(new BigInteger(String.valueOf(ch.getTtl()))));
    }

    String webmaster = Utils.getEmailOrText(ch.getWebMasterOrCreator());
    if(webmaster != null){
      elementList.add(factory.createTRssChannelWebMaster(webmaster));
    }
    
    return ret;
  }

}