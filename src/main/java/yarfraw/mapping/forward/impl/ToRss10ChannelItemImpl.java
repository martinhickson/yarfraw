package yarfraw.mapping.forward.impl;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.DcType;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.TRss10Item;
import yarfraw.mapping.forward.ToRss10ChannelItem;
import yarfraw.utils.CommonUtils;
/**
 * Util methods for mapping Yarfraw core model to Rss10 Jaxb model
 * @author jliang
 *
 */
public class ToRss10ChannelItemImpl  implements ToRss10ChannelItem{
  private static ToRss10ChannelItem _instance = new ToRss10ChannelItemImpl();
  private static final ObjectFactory FACTORY = new ObjectFactory();
  private static final Log LOG = LogFactory.getLog(ToRss10ChannelItemImpl.class);
  public static ToRss10ChannelItem getInstance(){
    return _instance;
  }
  
  private ToRss10ChannelItemImpl(){}
  
  public JAXBElement<TRss10Item> execute(ItemEntry item)
          throws YarfrawException {
    return toRss10Item(item);
  }
  
  /*
   * (title, link, description?)
   */
  public static JAXBElement<TRss10Item> toRss10Item(ItemEntry item){
    TRss10Item ret = FACTORY.createTRss10Item();
    List<Object> elementList = ret.getTitleOrDescriptionOrLink();
    
    String author = Utils.getEmailOrText(item.getAuthorOrCreator());
    if(author != null){
      DcType dc = new DcType();
      dc.setValue(author);
      elementList.add(FACTORY.createCreator(dc));
    }
    
    if(item.getRightsText() != null){
      DcType dc = new DcType();
      dc.setValue(item.getRightsText());
      elementList.add(FACTORY.createRights(dc));
    }
    
    if(item.getCategorySubjects() != null){
      for(CategorySubject c : item.getCategorySubjects()){
        if(c != null){
          DcType dc = new DcType();
          dc.setValue(c.getCategoryOrSubjectOrTerm());
          elementList.add(FACTORY.createSubject(dc));
        }
      }
    }
    
    String contributor = Utils.getEmailOrText(item.getContributors());
    if(contributor != null){
      DcType dc = new DcType();
      dc.setValue(contributor);
      elementList.add(FACTORY.createContributor(dc));
    }
    
    //not supported
    if(item.getComments() != null){
      LOG.info("Item.Comments is not supported in Rss 1.0 feed. It will be ignored.");      
    }
    
    if(item.getDescriptionOrSummaryText() != null){
      elementList.add(FACTORY.createTRss10ItemDescription(item.getDescriptionOrSummaryText()));
    }
    
  //not supported
    if(item.getEnclosure() != null){
      LOG.warn("Item.Enclosure is not supported in Rss 1.0 feed. It will be ignored. Use Rss 2.0 or Atom 1.0 to add enclosure");
    }
    if(item.getUid() != null){
      LOG.warn("Item.uid is not supported in Rss 1.0 feed. It will be ignored. Use Rss 2.0 or Atom 1.0 to add unique Id");
    }
    String link = Utils.getHrefLink(item.getLinks());
    if(link != null){
      elementList.add(FACTORY.createTRss10ItemLink(link));
      ret.setAbout(link);
    }
    
    if(item.getPubDate() != null){
      String dateString = item.getPubDate();
      if(!CommonUtils.isDateFormatValid(dateString, FeedFormat.RSS10)){
        String newDateString = CommonUtils.formatDate(CommonUtils.tryParseDate(dateString), FeedFormat.RSS10);
        if(newDateString != null){
          dateString = newDateString;
        }else{
          LOG.warn("The dateString "+dateString+" is in valid according to RSS 1.0 specs, unabel to convert it to a valid format, writing it as is");
        }
      }
      DcType dc = new DcType();
      dc.setValue(dateString);
      elementList.add(FACTORY.createDate(dc));
    }
    
    //not supported
    if(item.getSource() != null){
      LOG.info("Item.Source is not supported in Rss 1.0 feed. It will be ignored. ");
    }
    
    if(item.getTitleText() != null){
      elementList.add(FACTORY.createTRss10ItemTitle(item.getTitleText()));
    }

    if(item.getOtherElements() != null){
      elementList.addAll(item.getOtherElements());
    }
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }
    
    if(item.getAbout() != null){
      ret.setAbout(item.getAbout());
    }
    ret.setResource(item.getResource());
    
    return FACTORY.createItem(ret);
  }
}