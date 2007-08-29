package yarfraw.mapping.forward.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Cloud;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.Guid;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
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
  private Rss20MappingUtils(){}
  public static JAXBElement<TRssItem> ToRss20Item(Item item){
    return FACTORY.createItem(toTItem(item));
  }

  public static JAXBElement<TTextInput> toRss20TextInput(TextInput input){
    TTextInput ret = new TTextInput();
    ret.setDescription(input.getDescription());
    if(input.getLink() != null){
      ret.setLink(input.getLink().toString());
    }
    ret.setName(input.getName());
    ret.setTitle(input.getTitle());
    return FACTORY.createTRssChannelTextInput(ret);
  }
  
  public static JAXBElement<TImage> toRss20Image(Image image){
    TImage ret = new TImage();
    ret.setDescription(image.getDescription());
    ret.setHeight(image.getHeight());
    ret.setWidth(image.getWidth());
    if(image.getLink() != null){
      ret.setLink(image.getLink().toString());      
    }
    ret.setTitle(image.getTitle());
    if(image.getUrl() != null){
      ret.setUrl(image.getUrl().toString());
    }
    return FACTORY.createTRssChannelImage(ret);
  }
  
  public static JAXBElement<TCategory> toRss20Category(Category c){
    TCategory ret = new TCategory();
    ret.setDomain(c.getDomainOrScheme());
    ret.setValue(c.getCategory());
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
    if(en.getUrl() != null){
      ret.setUrl(en.getUrl().toString());
    }
    ret.setValue(en.getValue());
    return FACTORY.createTRssItemEnclosure(ret);
  }
  
  private static JAXBElement<TGuid> toRss20Guid(Guid guid){
    TGuid ret = new TGuid();
    ret.setIsPermaLink(guid.isPermaLink());
    ret.setValue(guid.getGuid());
    return FACTORY.createTRssItemGuid(ret);
  }
  
  private static TRssItem toTItem(Item item){
    TRssItem ret = new ObjectFactory().createTRssItem();
    List<Object> elementList = ret.getTitleOrDescriptionOrLink();
    ObjectFactory factory = FACTORY;
    if(item.getOtherElements() != null){
      ret.getTitleOrDescriptionOrLink().addAll(item.getOtherElements());
    }
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }
    if(item.getAuthor() != null){
      elementList.add(factory.createTRssItemAuthor(item.getAuthor()));
    }
    
    if(item.getCategory() != null){
      for(Category c : item.getCategory()){
        if(c != null){
          elementList.add(toRss20Category(c));
        }
      }
    }
    
    if(item.getComments() != null){
      elementList.add(factory.createTRssItemComments(item.getComments().toString()));      
    }
    if(item.getDescription() != null){
      elementList.add(factory.createTRssItemDescription(item.getDescription()));
    }
    if(item.getEnclosure() != null){
      elementList.add(toRss20Enclosure(item.getEnclosure()));
    }
    if(item.getGuid() != null){
      elementList.add(toRss20Guid(item.getGuid()));
    }
    if(item.getLink() != null){
      elementList.add(factory.createTRssItemLink(item.getLink().toString()));
    }
    
    if(item.getPubDate() != null){
      SimpleDateFormat format = new SimpleDateFormat(CommonUtils.RFC822DATE_PATTERN);
      elementList.add(factory.createTRssItemPubDate(format.format(item.getPubDate())));
    }
    if(item.getSource() != null){
      elementList.add(toRss20Source(item.getSource()));
    }
    if(item.getTitle() != null){
      elementList.add(factory.createTRssItemTitle(item.getTitle()));
    }
    
    return ret;
  }

  private static JAXBElement<TSource> toRss20Source(Source s){
    TSource ret = new TSource();
    if(s.getUrl() != null){
      ret.setUrl(s.getUrl().toString());
    }
    ret.setValue(s.getSource());
    return FACTORY.createTRssItemSource(ret);
  }
  
}