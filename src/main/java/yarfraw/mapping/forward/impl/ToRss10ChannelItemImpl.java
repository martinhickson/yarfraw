package yarfraw.mapping.forward.impl;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.TRss10Item;
import yarfraw.mapping.forward.ToRss10ChannelItem;
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
      elementList.add(FACTORY.createCreator(author));
    }
    
    if(item.getCategorySubjects() != null){
      for(CategorySubject c : item.getCategorySubjects()){
        if(c != null){
          elementList.add(FACTORY.createSubject(c.getCategoryOrSubjectOrTerm()));
        }
      }
    }
    
    String contributor = Utils.getEmailOrText(item.getContributors());
    if(contributor != null){
      elementList.add(FACTORY.createContributor(contributor));
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
      elementList.add(FACTORY.createDate(item.getPubDate()));
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