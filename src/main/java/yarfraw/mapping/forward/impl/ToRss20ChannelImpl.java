package yarfraw.mapping.forward.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Day;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.generated.rss20.elements.TSkipDay;
import yarfraw.generated.rss20.elements.TSkipDaysList;
import yarfraw.generated.rss20.elements.TSkipHoursList;
import yarfraw.mapping.forward.ToRss20Channel;
import yarfraw.utils.Utils;

public class ToRss20ChannelImpl implements ToRss20Channel{

   private static ToRss20Channel _instance = new ToRss20ChannelImpl();
   
   public static ToRss20Channel getInstance(){
     return _instance;
   }
  
   private ToRss20ChannelImpl(){}
  public JAXBElement<TRssChannel> execute(Channel channel) throws YarfrawException {
    return new ObjectFactory().createChannel(toChannel(channel));
  }
  
  private TRssChannel toChannel(Channel ch){
    ObjectFactory factory = new ObjectFactory();
    TRssChannel ret = factory.createTRssChannel();
    List<Object> elementList = ret.getTitleOrLinkOrDescription();
    if(ch.getOtherElements() != null){
      ret.getAny().addAll(ch.getOtherElements());
    }
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    if(ch.getCategory() != null){
      for(Category c : ch.getCategory()){
        if(c != null){
          elementList.add(Rss20MappingUtils.toRss20Category(c));
        }
      }
    }
    
    if(ch.getCloud() != null){
      elementList.add(Rss20MappingUtils.toRss20Cloud(ch.getCloud()));
    }
    if(ch.getCopyright() != null){
      elementList.add(factory.createTRssChannelCopyright(ch.getCopyright()));
    }
    if(ch.getDescription() != null){
      elementList.add(factory.createTRssChannelDescription(ch.getDescription()));
    }

    if(ch.getDocs() != null){
      elementList.add(factory.createTRssChannelDocs(ch.getDocs().toString()));
    }
    
    if(ch.getGenerator() != null){
      elementList.add(factory.createTRssChannelGenerator(ch.getGenerator()));
    }

    if(ch.getImage() != null){
      elementList.add(Rss20MappingUtils.toRss20Image(ch.getImage()));
    }
    
    if(ch.getItems() != null){
      for(Item t : ch.getItems()){
        if(t != null){
          ret.getItem().add(Rss20MappingUtils.ToRss20Item(t).getValue());
        }
      }
    }
    
    if(ch.getLanguage() != null){
      elementList.add(factory.createTRssChannelLanguage(ch.getLanguage().getLanguage()));
    }
    if(ch.getLink() != null){
      elementList.add(factory.createTRssChannelLink(ch.getLink().toString()));
    }
    SimpleDateFormat format = new SimpleDateFormat(Utils.RFC822DATE_PATTERN);
    if(ch.getLastBuildDate() != null){
      elementList.add(factory.createTRssChannelLastBuildDate(format.format(ch.getLastBuildDate())));
    }
    
    if(ch.getManagingEditor() != null){
      elementList.add(factory.createTRssChannelManagingEditor(ch.getManagingEditor()));
    }
    
    if(ch.getPubDate() != null){
      elementList.add(factory.createTRssChannelPubDate(format.format(ch.getPubDate())));
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

    if(ch.getTitle() != null){
      elementList.add(factory.createTRssChannelTitle(ch.getTitle()));
    }

    if(ch.getTtl() != null){
      elementList.add(factory.createTRssChannelTtl(new BigInteger(String.valueOf(ch.getTtl()))));
    }

    if(ch.getWebMaster() != null){
      elementList.add(factory.createTRssChannelWebMaster(ch.getWebMaster()));
    }
    
    return ret;
  }

}