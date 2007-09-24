package yarfraw.mapping.backward.impl;
import static yarfraw.mapping.backward.impl.Atom03MappingUtils.getElements;
import static yarfraw.mapping.backward.impl.Atom03MappingUtils.toGenerator;
import static yarfraw.mapping.backward.impl.Atom03MappingUtils.toItemEntry;
import static yarfraw.mapping.backward.impl.Atom03MappingUtils.toLink;
import static yarfraw.mapping.backward.impl.Atom03MappingUtils.toPerson;
import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.generated.atom03.elements.EntryType;
import yarfraw.generated.atom03.elements.FeedType;
import yarfraw.generated.atom03.elements.LinkType;
import yarfraw.generated.atom03.elements.PersonType;
import yarfraw.mapping.backward.ToChannelAtom03;


/**
 * TODO: document me
 * @author jliang
 *
 */
public class ToChannelAtom03Impl implements ToChannelAtom03{

  private static final ToChannelAtom03 _instance = new ToChannelAtom03Impl();
    
  private ToChannelAtom03Impl() {}
  public static ToChannelAtom03 getInstance(){
    return _instance;
  }

  public ChannelFeed execute(FeedType feed){
    if(feed == null){
      return null;
    }
    ChannelFeed c = new ChannelFeed();
    if(feed.getAuthor() != null){
      c.addManagingEditorOrAuthorOrPublisher(toPerson(feed.getAuthor()));
    }
    if(feed.getContributor() != null){
      for(PersonType p : feed.getContributor()){
        c.addContributor(toPerson(p));
      }
    }
    
    c.setRights(feed.getCopyright());

    if(feed.getEntry() != null){
      for(EntryType e : feed.getEntry()){
        c.addItem(toItemEntry(e));
      }
    }
    
    
    c.setGenerator(toGenerator(feed.getGenerator()));
    if(feed.getId() != null){
      c.setUid(feed.getId());
    }
    
//  TODO:  feed.getInfo()
    c.setLang(feed.getLang());
    
    if(feed.getLink() != null){
      for(LinkType l : feed.getLink()){
        c.addLink(toLink(l));
      }
    }
    
    c.setLastBuildOrUpdatedDate(feed.getModified());
//    c.setTitle(feed.getTitle());
    
    if(feed.getOtherAttributes() != null){
      c.getOtherAttributes().putAll(feed.getOtherAttributes());
    }
    c.getOtherElements().addAll(getElements(feed.getAny()));    
    return c;
  }
  

}