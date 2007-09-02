package yarfraw.mapping.forward.impl;

import static yarfraw.utils.CommonConstants.MIN_PER_DAY;
import static yarfraw.utils.CommonConstants.MIN_PER_MONTH;
import static yarfraw.utils.CommonConstants.MIN_PER_WEEK;
import static yarfraw.utils.CommonConstants.MIN_PER_YEAR;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.ItemEntry;
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
  public static JAXBElement<TRss10Image> toRss10Image(Image in){
    TRss10Image ret = FACTORY.createTRss10Image();
    //not supported
    if(in.getDescription() != null
        || in.getHeight() != null
        || in.getWidth() != null){
      LOG.info("description, height, width are not supported in Rss 1.0's image element. They will be ignored");
    }
    
    ret.setTitle(in.getTitle());
    ret.setUrl(in.getUrl());
    ret.setLink(in.getLink());
    
    ret.setAbout(in.getAbout());
    ret.setResource(in.getResource());
    
    if(ret.getAbout() == null){
      ret.setAbout(in.getUrl());
    }
    
    return FACTORY.createTRss10ChannelImage(ret);
  }

  public static JAXBElement<TRss10TextInput> toRss10TextInput(TextInput in) {
    TRss10TextInput ret = FACTORY.createTRss10TextInput();
    List<Object> elementList = ret.getTitleOrDescriptionOrName();
    
    if(in.getTitle() != null){
      elementList.add(FACTORY.createTRss10TextInputTitle(in.getTitle()));
    }
    
    if(in.getDescription() != null){
      elementList.add(FACTORY.createTRss10TextInputDescription(in.getDescription()));
    }
    if(in.getName() != null){
      elementList.add(FACTORY.createTRss10TextInputName(in.getName()));
    }
    if(in.getLink() != null){
      elementList.add(FACTORY.createTRss10TextInputLink(in.getLink()));
    }
    
    if(in.getOtherElements() != null){
      elementList.addAll(in.getOtherElements());
    }
    ret.setAbout(in.getAbout());
    ret.setResource(in.getResource());
    
    if(ret.getAbout() == null){
      ret.setAbout(in.getLink());
    }
    return FACTORY.createTextinput(ret);
  }
  /*
   *  (title, link, description, image?, items, textinput?)
   */
  public static JAXBElement<TRss10Channel> toChannel(ChannelFeed ch){
    ObjectFactory factory = FACTORY;
    TRss10Channel ret = factory.createTRss10Channel();
    List<Object> elementList = ret.getTitleOrLinkOrDescription();
    
    if(ch.getTitleText() != null){
      elementList.add(factory.createTRss10ChannelTitle(ch.getTitleText()));
    }
    
    String link = Utils.getHrefLink(ch.getLinks());
    if(link != null){
      elementList.add(factory.createTRss10ChannelLink(link));
    }
    
    if(ch.getDescriptionOrSubtitleText() != null){
      elementList.add(factory.createTRss10ChannelDescription(ch.getDescriptionOrSubtitleText()));
    }
    
    if(ch.getImageOrIcon() != null){
      TRss10Image img = factory.createTRss10Image();
      img.setResource(ch.getImageOrIcon().getAbout());
      if(img.getResource() == null){
        img.setAbout(img.getUrl());
      }
      elementList.add(factory.createTRss10ChannelImage(img));
    }
    
    if(ch.getTexInput() != null){
      TRss10TextInput ti = factory.createTRss10TextInput();
      ti.setResource(ch.getTexInput().getAbout());
      if(ti.getResource() == null){
        ti.setResource(ch.getTexInput().getLink());
      }
      elementList.add(factory.createTextinput(ti));
    }
    
    if(ch.getCategorySubjects() != null){
      for(CategorySubject c : ch.getCategorySubjects()){
        if(c != null){
          elementList.add(factory.createSubject(c.getCategoryOrSubjectOrTerm()));
        }
      }
    }
    
    //NOT SUPPORTED
    if(ch.getCloud() != null){
      LOG.info("Channel.Cloud is not supported in Rss 1.0 feed. It will be ignored.");
    }
    
    if(ch.getRightsText() != null){
      elementList.add(factory.createRights(ch.getRightsText()));
    }
    
  //NOT SUPPORTED
    if(ch.getDocs() != null){
      LOG.info("Channel.Docs is not supported in Rss 1.0 feed. It will be ignored.");
    }
    //NOT SUPPORTED
    if(ch.getGenerator() != null){
      LOG.info("Channel.Generator is not supported in Rss 1.0 feed. It will be ignored.");
    }


    Seq seq = factory.createSeq();
    if(ch.getItems() != null){
      for(ItemEntry t : ch.getItems()){
        if(t != null){
          Li li = factory.createLi();
          if(t.getResource() != null){
            li.setResource(t.getResource());
          }else if(CollectionUtils.isNotEmpty(t.getLinks())){
            li.setResource(t.getLinks().get(0).getHref()); //use the link if no resource was specified
          }else{
            LOG.warn("no <link> found under Item, unable to create <li> element under <items>");
          }
          seq.getLi().add(li);
        }
      }
    }
    
    Items items =factory.createItems();
    items.setSeq(seq);
    elementList.add(factory.createItems(items));
    
    if(ch.getLang() != null){
      elementList.add(factory.createLanguage(ch.getLang()));
    }

    //not supported
    if(ch.getLastBuildOrUpdatedDate() != null){
      LOG.info("Channel.LastBuildDate is not supported in Rss 1.0 feed. It will be ignored.");
    }
    
    String creator = Utils.getEmailOrText(ch.getWebMasterOrCreator());
    if(creator != null){
      elementList.add(factory.createCreator(creator));
    }
    
    if(ch.getPubDate() != null){
      String dateString = ch.getPubDate();
      if(!CommonUtils.isDateFormatValid(dateString, FeedFormat.RSS10)){
        String newDateString = CommonUtils.formatDate(CommonUtils.tryParseDate(dateString), FeedFormat.RSS10);
        if(newDateString != null){
          dateString = newDateString;
        }else{
          LOG.warn("The dateString "+dateString+" is in valid according to RSS 1.0 specs, unabel to convert it to a valid format, writing it as is");
        }
      }
      elementList.add(factory.createDate(dateString));
    }
    
//  not supported
    if(ch.getSkipDays() != null){
      LOG.info("Channel.SkipDays is not supported in Rss 1.0 feed. It will be ignored.");
    }

    if(ch.getSkipHours() != null){
      LOG.info("Channel.SkipHours is not supported in Rss 1.0 feed. It will be ignored.");
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

    String publisher = Utils.getEmailOrText(ch.getManagingEditorOrAuthorOrPublisher());
    if(publisher != null){
      elementList.add(factory.createPublisher(publisher));
    } 
    
    String contributor = Utils.getEmailOrText(ch.getContributors());
    if(contributor != null){
      elementList.add(factory.createContributor(contributor));
    }
    
    ret.setAbout(ch.getAbout());
    ret.setResource(ch.getResource());
    
    if(ret.getAbout() == null){
      ret.setAbout(Utils.getHrefLink(ch.getLinks()));
    }
    
    if(ch.getOtherElements() != null){
      elementList.addAll(ch.getOtherElements());
    }
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    return factory.createChannel(ret);
  }
}