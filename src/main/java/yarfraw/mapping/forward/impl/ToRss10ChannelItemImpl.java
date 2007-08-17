package yarfraw.mapping.forward.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.TRss10Item;
import yarfraw.mapping.forward.ToRss10ChannelItem;
import yarfraw.utils.Utils;

public class ToRss10ChannelItemImpl  implements ToRss10ChannelItem{
  private static ToRss10ChannelItem _instance = new ToRss10ChannelItemImpl();
  private static final ObjectFactory FACTORY = new ObjectFactory();
  
  public static final ToRss10ChannelItem getInstance(){
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
      for(Category c : item.getCategory()){
        if(c != null){
          elementList.add(FACTORY.createSubject(c.getCategory()));
        }
      }
    }
    
    //not supported
//    if(item.getComments() != null){
//      elementList.add(factory.createTRssItemComments(item.getComments().toString()));      
//    }
    if(item.getDescription() != null){
      elementList.add(FACTORY.createTRss10ItemDescription(item.getDescription()));
    }
  //not supported
//    if(item.getEnclosure() != null){
//      elementList.add(toRss20Enclosure(item.getEnclosure()));
//    }
//    if(item.getGuid() != null){
//      elementList.add(toRss20Guid(item.getGuid()));
//    }
    if(item.getLink() != null){
      elementList.add(FACTORY.createTRss10ItemLink(item.getLink().toString()));
      ret.setAbout(item.getLink().toString());
    }
    if(item.getRdfAttributes() != null){//override 'about' if it's explicitly set
      ret.setAbout(item.getRdfAttributes().getAbout() == null ? null : item.getRdfAttributes().getAbout().toString());
      ret.setResource(item.getRdfAttributes().getResource() == null ? null : item.getRdfAttributes().getResource().toString());
    }
    
    if(item.getPubDate() != null){
      SimpleDateFormat format = new SimpleDateFormat(Utils.DATE_FORMAT_PATTERN);
      elementList.add(FACTORY.createDate(format.format(item.getPubDate())));
    }
    //not supported
//    if(item.getSource() != null){
//      elementList.add(toRss20Source(item.getSource()));
//    }
    if(item.getTitle() != null){
      elementList.add(FACTORY.createTRss10ItemTitle(item.getTitle()));
    }

    return FACTORY.createItem(ret);
  }
}