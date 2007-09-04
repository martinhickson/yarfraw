package yarfraw.mapping.forward.impl;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Cloud;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.Id;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Source;
import yarfraw.core.datamodel.TextInput;
import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TCategory;
import yarfraw.generated.rss20.elements.TCloud;
import yarfraw.generated.rss20.elements.TCloudProtocol;
import yarfraw.generated.rss20.elements.TEnclosure;
import yarfraw.generated.rss20.elements.TGuid;
import yarfraw.generated.rss20.elements.TImage;
import yarfraw.generated.rss20.elements.TRssItem;
import yarfraw.generated.rss20.elements.TSource;
import yarfraw.generated.rss20.elements.TTextInput;
import yarfraw.utils.CommonUtils;

/**
 * Util methods for mapping Yarfraw core model to Rss20 Jaxb model
 * @author jliang
 *
 */
class Rss20MappingUtils {
  private static final ObjectFactory FACTORY = new ObjectFactory();
  private static final Log LOG = LogFactory.getLog(Rss20MappingUtils.class);
  private Rss20MappingUtils(){}
  public static JAXBElement<TRssItem> toRss20Item(ItemEntry item){
    return FACTORY.createItem(toTItem(item));
  }

  public static JAXBElement<TTextInput> toRss20TextInput(TextInput input){
    TTextInput ret = new TTextInput();
    ret.setDescription(input.getDescription());
    ret.setLink(input.getLink());
    ret.setName(input.getName());
    ret.setTitle(input.getTitle());
    return FACTORY.createTRssChannelTextInput(ret);
  }
  
  public static JAXBElement<TImage> toRss20Image(Image image){
    TImage ret = new TImage();
    ret.setDescription(image.getDescription());
    ret.setHeight(image.getHeight());
    ret.setWidth(image.getWidth());
    ret.setLink(image.getLink());
    ret.setTitle(image.getTitle());
    ret.setUrl(image.getUrl());
    return FACTORY.createTRssChannelImage(ret);
  }
  
  public static JAXBElement<TCategory> toRss20Category(CategorySubject c){
    TCategory ret = new TCategory();
    ret.setDomain(c.getDomainOrScheme());
    ret.setValue(c.getCategoryOrSubjectOrTerm());
    return FACTORY.createTRssChannelCategory(ret);
  }
  
  public static JAXBElement<TCloud> toRss20Cloud(Cloud cl){
    TCloud ret = new TCloud();
    ret.setDomain(cl.getDomain());
    ret.setPath(cl.getPath());
    ret.setPort(new BigInteger(String.valueOf(cl.getPort())));
    ret.setProtocol(TCloudProtocol.fromValue(cl.getProtocol()));
    ret.setRegisterProcedure(cl.getRegisterProcedure());
    return FACTORY.createTRssChannelCloud(ret);
  }
  
  private static JAXBElement<TEnclosure> toRss20Enclosure(Enclosure en){
    TEnclosure ret = new TEnclosure();
    ret.setLength(new BigInteger(String.valueOf(en.getLength())));
    ret.setType(en.getMimeType());
    ret.setUrl(en.getUrl());
    ret.setValue(en.getValue());
    return FACTORY.createTRssItemEnclosure(ret);
  }
  
  private static JAXBElement<TGuid> toRss20Guid(Id guid){
    TGuid ret = new TGuid();
    ret.setIsPermaLink(guid.isPermaLink());
    ret.setValue(guid.getIdValue());
    return FACTORY.createTRssItemGuid(ret);
  }
  
  private static TRssItem toTItem(ItemEntry item){
    TRssItem ret = new ObjectFactory().createTRssItem();
    List<Object> elementList = ret.getTitleOrDescriptionOrLink();
    ObjectFactory factory = FACTORY;
    
    String author = Utils.getEmailOrText(item.getAuthorOrCreator());
    if(author != null){
      elementList.add(factory.createTRssItemAuthor(author));
    }
    
    if(item.getCategorySubjects() != null){
      for(CategorySubject c : item.getCategorySubjects()){
        if(c != null){
          elementList.add(toRss20Category(c));
        }
      }
    }
    
    if(item.getComments() != null){
      elementList.add(factory.createTRssItemComments(item.getComments()));      
    }
    
    if(item.getDescriptionOrSummaryText() != null){
      elementList.add(factory.createTRssItemDescription(item.getDescriptionOrSummaryText()));
    }
    if(item.getEnclosure() != null){
      elementList.add(toRss20Enclosure(item.getEnclosure()));
    }
    if(item.getUid() != null){
      elementList.add(toRss20Guid(item.getUid()));
    }
    String link = Utils.getHrefLink(item.getLinks());
    if(link != null){
      elementList.add(factory.createTRssItemLink(link));
    }
    
    if(item.getPubDate() != null){
      String dateString = item.getPubDate();
      if(!CommonUtils.isDateFormatValid(dateString, FeedFormat.RSS20)){
        String newDateString = CommonUtils.formatDate(CommonUtils.tryParseDate(dateString), FeedFormat.RSS20);
        if(newDateString != null){
          dateString = newDateString;
        }else{
          LOG.warn("The dateString "+dateString+" is in valid according to RSS 2.0 specs, unabel to convert it to a valid format, writing it as is");
        }
      }
      elementList.add(factory.createTRssItemPubDate(dateString));
    }
    if(item.getSource() != null){
      elementList.add(toRss20Source(item.getSource()));
    }
    if(item.getTitleText() != null){
      elementList.add(factory.createTRssItemTitle(item.getTitleText()));
    }
    
    if(item.getOtherElements() != null){
      ret.getTitleOrDescriptionOrLink().addAll(item.getOtherElements());
    }
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }
    return ret;
  }

  private static JAXBElement<TSource> toRss20Source(Source s){
    TSource ret = new TSource();
    if(s.getUrl() != null){
      ret.setUrl(s.getUrl());
    }
    ret.setValue(s.getSource());
    return FACTORY.createTRssItemSource(ret);
  }
  
}