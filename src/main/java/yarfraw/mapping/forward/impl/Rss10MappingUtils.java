package yarfraw.mapping.forward.impl;

import static yarfraw.utils.CommonConstants.MIN_PER_DAY;
import static yarfraw.utils.CommonConstants.MIN_PER_MONTH;
import static yarfraw.utils.CommonConstants.MIN_PER_WEEK;
import static yarfraw.utils.CommonConstants.MIN_PER_YEAR;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.TextInput;
import yarfraw.generated.rss10.elements.Items;
import yarfraw.generated.rss10.elements.Li;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.Seq;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.generated.rss10.elements.TRss10Image;
import yarfraw.generated.rss10.elements.TRss10TextInput;
import yarfraw.generated.rss10.elements.UpdatePeriodEnum;
import yarfraw.utils.CommonUtils;

/**
 * Util methods for mapping Yarfraw core model to Rss10 Jaxb model
 * @author jliang
 *
 */
class Rss10MappingUtils {
  private Rss10MappingUtils(){}
  private static final ObjectFactory FACTORY = new ObjectFactory ();
  private static final Log LOG = LogFactory.getLog(Rss10MappingUtils.class);
  public static JAXBElement<TRss10Image> toRss10Image(Image image){
    TRss10Image ret = FACTORY.createTRss10Image();
    //not supported
    if(image.getDescription() != null
        || image.getHeight() != null
        || image.getWidth() != null){
      LOG.info("description, height, width are not supported in Rss 1.0's image element. They will be ignored");
    }
    
    if(image.getRdfAttributes() != null){
      ret.setAbout(image.getRdfAttributes().getAbout() == null ? null : image.getRdfAttributes().getAbout().toString());
      ret.setResource(image.getRdfAttributes().getResource() == null ? null : image.getRdfAttributes().getResource().toString());
    }
    if(image.getLink() != null){
      ret.setLink(image.getLink().toString());      
    }
    ret.setTitle(image.getTitle());
    if(image.getUrl() != null){
      ret.setUrl(image.getUrl().toString());
    }
    return FACTORY.createTRss10ChannelImage(ret);
  }

  public static JAXBElement<TRss10TextInput> toRss10TextInput(TextInput texInput) {
    TRss10TextInput ret = FACTORY.createTRss10TextInput();

    if(texInput.getRdfAttributes() != null){
      ret.setAbout(texInput.getRdfAttributes().getAbout() == null ? null : texInput.getRdfAttributes().getAbout().toString());
      ret.setResource(texInput.getRdfAttributes().getResource() == null ? null : texInput.getRdfAttributes().getResource().toString());
    }
    ret.setDescription(texInput.getDescription());
    if(texInput.getLink() != null){
      ret.setLink(texInput.getLink().toString());
    }
    ret.setName(texInput.getName());
    ret.setTitle(texInput.getTitle());
    return FACTORY.createTextinput(ret);
  }
  
  public static JAXBElement<TRss10Channel> toChannel(Channel ch){
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
      elementList.add(toRss10Image(ch.getImage()));
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