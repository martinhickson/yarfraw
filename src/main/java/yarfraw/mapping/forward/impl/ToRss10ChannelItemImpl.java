package yarfraw.mapping.forward.impl;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
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
  
  public JAXBElement<TRss10Item> execute(Item item)
          throws YarfrawException {
    return toRss10Item(item);
  }
  
  
  public static JAXBElement<TRss10Item> toRss10Item(Item item){
    TRss10Item ret = FACTORY.createTRss10Item();
    List<Object> elementList = ret.getTitleOrDescriptionOrLink();
    if(item.getOtherElements() != null){
      elementList.addAll(item.getOtherElements());
    }
    if(item.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(item.getOtherAttributes());
    }
    if(item.getAuthor() != null){
      elementList.add(FACTORY.createCreator(item.getAuthor()));
    }
    
    if(item.getCategory() != null){
      for(CategorySubject c : item.getCategory()){
        if(c != null){
          elementList.add(FACTORY.createSubject(c.getCategoryOrSubjectOrTerm()));
        }
      }
    }
    
    //not supported
    if(item.getComments() != null){
      LOG.info("Item.Comments is not supported in Rss 1.0 feed. It will be ignored.");      
    }
    if(item.getDescription() != null){
      elementList.add(FACTORY.createTRss10ItemDescription(item.getDescription()));
    }
  //not supported
    if(item.getEnclosure() != null){
      LOG.warn("Item.Enclosure is not supported in Rss 1.0 feed. It will be ignored. Use Rss 2.0 or Atom 1.0 to add enclosure");
    }
    if(item.getGuid() != null){
      LOG.warn("Item.Guid is not supported in Rss 1.0 feed. It will be ignored. Use Rss 2.0 or Atom 1.0 to add unique Id");
    }
    if(item.getLink() != null){
      elementList.add(FACTORY.createTRss10ItemLink(item.getLink().toString()));
      ret.setAbout(item.getLink().toString());
    }
    if(item.getRdfAttributes() != null){//override 'about' if it's explicitly set
      ret.setAbout(item.getRdfAttributes().getAbout() == null ? null : item.getRdfAttributes().getAbout().toString());
      ret.setResource(item.getRdfAttributes().getResource() == null ? null : item.getRdfAttributes().getResource().toString());
    }
    
    if(item.getPubDate() != null){
      elementList.add(FACTORY.createDate(CommonUtils.getDateAsISO8601String(item.getPubDate())));
    }
    //not supported
    if(item.getSource() != null){
      LOG.info("Item.Source is not supported in Rss 1.0 feed. It will be ignored. ");
    }
    if(item.getTitle() != null){
      elementList.add(FACTORY.createTRss10ItemTitle(item.getTitle()));
    }

    return FACTORY.createItem(ret);
  }
}