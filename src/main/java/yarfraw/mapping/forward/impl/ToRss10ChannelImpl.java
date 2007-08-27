package yarfraw.mapping.forward.impl;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.Items;
import yarfraw.generated.rss10.elements.Li;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.Seq;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.generated.rss10.elements.UpdatePeriodEnum;
import yarfraw.mapping.forward.ToRss10Channel;
import yarfraw.utils.CommonUtils;
/**
 * Util methods for mapping Yarfraw core model to Rss10 Jaxb model
 * @author jliang
 *
 */
public class ToRss10ChannelImpl implements ToRss10Channel{
  private static ToRss10Channel _instance = new ToRss10ChannelImpl();
  private static final Log LOG = LogFactory.getLog(ToRss10ChannelImpl.class);
  private static final ObjectFactory FACTORY = new ObjectFactory();
  private static final int MIN_PER_DAY = 60*24;
  private static final int MIN_PER_WEEK = MIN_PER_DAY*7;
  private static final int MIN_PER_MONTH = MIN_PER_DAY*30;
  private static final int MIN_PER_YEAR = MIN_PER_DAY*365;
  
  public static final ToRss10Channel getInstance(){
    return _instance;
  }
  private ToRss10ChannelImpl(){}
  
  public JAXBElement<TRss10Channel> execute(Channel ch)
      throws YarfrawException {
    ObjectFactory factory = FACTORY;
    TRss10Channel ret = factory.createTRss10Channel();
    List<Object> elementList = ret.getTitleOrLinkOrDescription();
    if(ch.getOtherElements() != null){
      elementList.addAll(ch.getOtherElements());
    }
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    if(ch.getCategory() != null){
      for(Category c : ch.getCategory()){
        if(c != null){
          elementList.add(factory.createSubject(c.getCategory()));
        }
      }
    }
    
    //NOT SUPPORTED
    if(ch.getCloud() != null){
      LOG.info("Channel.Cloud is not supported in Rss 1.0 feed. It will be ignored.");
    }
    
    if(ch.getCopyright() != null){
      elementList.add(factory.createRights(ch.getCopyright()));
    }
    if(ch.getDescription() != null){
      elementList.add(factory.createTRss10ChannelDescription(ch.getDescription()));
    }

  //NOT SUPPORTED
    if(ch.getDocs() != null){
      LOG.info("Channel.Docs is not supported in Rss 1.0 feed. It will be ignored.");
    }
    //NOT SUPPORTED
    if(ch.getGenerator() != null){
      LOG.info("Channel.Generator is not supported in Rss 1.0 feed. It will be ignored.");
    }

    if(ch.getImage() != null){
      elementList.add(Rss10MappingUtils.toRss10Image(ch.getImage()));
    }

    Seq seq = factory.createSeq();
    if(ch.getItems() != null){
      for(Item t : ch.getItems()){
        if(t != null){
          Li li = factory.createLi();
          if(t.getRdfAttributes() != null){
            li.setResource(t.getRdfAttributes().getAbout().toString());
          }else{
            li.setResource(t.getLink().toString()); //use the link if no resource was specified
          }
          seq.getLi().add(li);
        }
      }
    }
    
    Items items =factory.createItems();
    items.setSeq(seq);
    elementList.add(factory.createItems(items));
    
    if(ch.getLanguage() != null){
      elementList.add(factory.createLanguage(ch.getLanguage().getLanguage()));
    }

    if(ch.getLink() != null){
      elementList.add(factory.createTRss10ChannelLink(ch.getLink().toString()));
    }
    //not supported
    
    if(ch.getLastBuildDate() != null){
      LOG.info("Channel.LastBuildDate is not supported in Rss 1.0 feed. It will be ignored.");
    }
    
    if(ch.getManagingEditor() != null){
      elementList.add(factory.createCreator(ch.getManagingEditor()));
    }
    
    if(ch.getPubDate() != null){
      elementList.add(factory.createDate(CommonUtils.getDateAsISO8601String(ch.getPubDate())));
    }
//  not supported
    if(ch.getSkipDays() != null){
      LOG.info("Channel.SkipDays is not supported in Rss 1.0 feed. It will be ignored.");
    }

    if(ch.getSkipHours() != null){
      LOG.info("Channel.SkipHours is not supported in Rss 1.0 feed. It will be ignored.");
    }
    
    if(ch.getTexInput() != null){
      elementList.add(Rss10MappingUtils.toRss10TextInput(ch.getTexInput()));      
    }

    if(ch.getTitle() != null){
      elementList.add(factory.createTRss10ChannelTitle(ch.getTitle()));
    }

    
    if(ch.getTtl() != null){
      int ttl = ch.getTtl().intValue();
      UpdatePeriodEnum updatedPeriod = UpdatePeriodEnum.DAILY;
      int frequency = 1;
      //calculate updatePeriod, updateFrequency using ttl
      if(ttl < 60){
        updatedPeriod = UpdatePeriodEnum.HOURLY;
        frequency = Math.max(1, 60/ttl);
      }else if(ttl < MIN_PER_DAY){
        frequency = Math.max(1, MIN_PER_DAY/ttl);
      }else if(ttl < MIN_PER_WEEK){
        updatedPeriod = UpdatePeriodEnum.WEEKLY;
        frequency = Math.max(1, MIN_PER_WEEK/ttl);
      }else if(ttl < MIN_PER_MONTH){
        updatedPeriod = UpdatePeriodEnum.MONTHLY;
        frequency = Math.max(1, MIN_PER_MONTH/ttl);
      }else{
        updatedPeriod = UpdatePeriodEnum.YEARLY;
        frequency = Math.max(1, MIN_PER_YEAR/ttl);
      }
      elementList.add(factory.createUpdatePeriod(updatedPeriod));
      elementList.add(factory.createUpdateFrequency(new BigInteger(String.valueOf(frequency))));
    }

    if(ch.getWebMaster() != null){
      elementList.add(factory.createPublisher(ch.getWebMaster()));
    }

    return factory.createChannel(ret);
  }
  
}