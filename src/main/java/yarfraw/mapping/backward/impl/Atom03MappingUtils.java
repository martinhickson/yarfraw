package yarfraw.mapping.backward.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.Generator;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.generated.atom03.elements.EntryType;
import yarfraw.generated.atom03.elements.GeneratorType;
import yarfraw.generated.atom03.elements.LinkType;
import yarfraw.generated.atom03.elements.PersonType;

/**
 * TODO: document me
 * @author jliang
 *
 */
class Atom03MappingUtils{

  private static final Log LOG = LogFactory.getLog(Atom03MappingUtils.class);
  
  public static ItemEntry toItemEntry(EntryType entry){
    if(entry == null){
      return null;
    }
    ItemEntry ret = new ItemEntry();
    
    if(entry.getAuthor() != null){
      ret.addAuthorOrCreator(toPerson(entry.getAuthor()));
    }
//    TODO: entry.getContent()
//    ret.setContent(toContent(entry.getContent()));
    
    if(entry.getContributor() != null){
      for(PersonType p : entry.getContributor()){
        ret.addContributor(toPerson(p));
      }
    }
    
    ret.setPubDate(entry.getCreated());
    ret.setUid(entry.getId());
    
//    TODO: entry.getIssued()
    
    ret.setLang(entry.getLang());
    if(entry.getLink() != null){
      ret.addLink(toLink(entry.getLink()));
    }
    ret.setUpdatedDate(entry.getModified());
    
//    ret.setDescriptionOrSummary(entry.getSummary());
//    ret.setTitle(entry.getTitle());
    if(entry.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(entry.getOtherAttributes());
    }
    ret.getOtherElements().addAll(getElements(entry.getAny()));
    return ret;
  }
  
  public static Person toPerson(PersonType person){
    if(person == null){
      return null;
    }
    Person ret = 
      new Person()
      .setEmailOrText(person.getEmail())
      .setName(person.getName())
      .setUri(person.getUrl());
    
    ret.setEmailOrText(person.getEmail());
    ret.setName(person.getName());
    ret.getOtherElements().addAll(getElements(person.getAny()));
    return ret;
  }
  
  public static List<Element> getElements(List<Object> any){
    List<Element> ret = new ArrayList<Element>();
    if(any != null){
      for(Object o : any){
        if(o == null){
          continue;
        }
        if (o instanceof Element) {
          Element e = (Element) o;
          ret.add(e);
        }else{
          LOG.warn("Unexpected object: "+ToStringBuilder.reflectionToString(o)+" this should not happen!");
        }
      }
    }
    return ret;    
  }
  
  public static Generator toGenerator(GeneratorType gen){
    if(gen == null){
      return null;
    }
    Generator ret = new Generator(gen.getValue())
                    .setUri(gen.getUrl())
                    .setVersion(gen.getVersion());
    return ret;
  }
  
  public static Link toLink(LinkType lnk){
    if(lnk == null){
      return null;
    }
    Link ret = new Link()
               .setHref(lnk.getHref())
               .setRel(lnk.getRel())
               .setTitle(lnk.getTitle())
               .setType(lnk.getType());
    if(lnk.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(lnk.getOtherAttributes());
    }
    return ret;
  }

}