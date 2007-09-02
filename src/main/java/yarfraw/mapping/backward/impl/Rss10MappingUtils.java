package yarfraw.mapping.backward.impl;
import static yarfraw.io.parser.ElementQName.RSS10_CONTRIBUTOR;
import static yarfraw.io.parser.ElementQName.RSS10_CREATOR;
import static yarfraw.io.parser.ElementQName.RSS10_DATE;
import static yarfraw.io.parser.ElementQName.RSS10_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS10_LANGUAGE;
import static yarfraw.io.parser.ElementQName.RSS10_LINK;
import static yarfraw.io.parser.ElementQName.RSS10_NAME;
import static yarfraw.io.parser.ElementQName.RSS10_PUBLISHER;
import static yarfraw.io.parser.ElementQName.RSS10_RIGHTS;
import static yarfraw.io.parser.ElementQName.RSS10_SUBJECT;
import static yarfraw.io.parser.ElementQName.RSS10_TITLE;
import static yarfraw.io.parser.ElementQName.RSS10_UPDATEBASE;
import static yarfraw.io.parser.ElementQName.RSS10_UPDATEFREQUENCY;
import static yarfraw.utils.XMLUtils.same;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.TextInput;
import yarfraw.generated.rss10.elements.Items;
import yarfraw.generated.rss10.elements.Li;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss10.elements.Seq;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.generated.rss10.elements.TRss10Image;
import yarfraw.generated.rss10.elements.TRss10Item;
import yarfraw.generated.rss10.elements.TRss10TextInput;
import yarfraw.generated.rss10.elements.UpdatePeriodEnum;
import yarfraw.utils.CommonUtils;
class Rss10MappingUtils{

  private static final Log LOG = LogFactory.getLog(Rss10MappingUtils.class);
  
  private Rss10MappingUtils(){}
  
  public static Integer calculateTtl(UpdatePeriodEnum updatePeriod, BigInteger updateFrequency){
    return CommonUtils.calculateTtl(updatePeriod, updateFrequency);
  }
  
  @SuppressWarnings("unchecked")
  public static ChannelFeed toChannel(TRss10Channel ch, RDF rdf){
    ChannelFeed ret = new ChannelFeed();
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    UpdatePeriodEnum updatePeriod = null;
    BigInteger updateFrequency = null;
    Map<String, Integer> ordering = new HashMap<String, Integer>();
    List<ItemEntry> items = toItems(rdf.getChannelOrImageOrItem());
    ret.setAbout(ch.getAbout());
    ret.setResource(ch.getResource());
    ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    for(Object o : ch.getTitleOrLinkOrDescription()){
      if(o == null)
        continue;
      
      if (o instanceof JAXBElement) {
        JAXBElement jaxb = (JAXBElement) o;
        Object val = jaxb.getValue();
        if(same(jaxb.getName(), RSS10_TITLE)){
          ret.setTitle((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_DESCRIPTION)){
          ret.setDescriptionOrSubtitle((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_LINK)){
          ret.addLink((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_UPDATEFREQUENCY)){
          updateFrequency = (BigInteger)jaxb.getValue();
        }else if(same(jaxb.getName(), RSS10_SUBJECT)){
          ret.addCategorySubject((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_PUBLISHER)){
          ret.addManagingEditorOrAuthorOrPublisher((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_CREATOR)){
          ret.addWebMasterOrCreator((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_RIGHTS)){
          ret.setRights((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_DATE)){
          ret.setPubDate((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_LANGUAGE)){
          ret.setLang((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_CONTRIBUTOR)){
          ret.addContributor((String)jaxb.getValue());
        }else if(val instanceof UpdatePeriodEnum){
          updatePeriod = (UpdatePeriodEnum)val;
        }else if(val instanceof TRss10Image){
          //this only put resources
          Image image = new Image();
          image.setResource(((TRss10Image)val).getResource());
          ret.setImageOrIcon(image);
        }else if(val instanceof TRss10TextInput){
          //this only put resources
          TextInput in = new TextInput();
          in.setResource(((TRss10TextInput)val).getResource());
          ret.setTexInput(in);
        }else if(same(jaxb.getName(), RSS10_UPDATEBASE)){
          LOG.info("<updateBase> element is ignored.");
        }else if(val instanceof Items){
          Seq seq = ((Items)val).getSeq();
          int i = 0;
          for(Li li : seq.getLi()){
            ordering.put(li.getResource(), i++);
          }
        }else{
          LOG.warn("Unexpected JAXBElement: "+ToStringBuilder.reflectionToString(jaxb)+" this should not happen!");
        }
      }else if (o instanceof Element) {
        Element e = (Element) o;
        ret.getOtherElements().add(e);
      }else{
        LOG.warn("Unexpected object: "+ToStringBuilder.reflectionToString(o)+" this should not happen!");
      }
    }  
    ret.setTtl(calculateTtl(updatePeriod, updateFrequency));
    if(ordering.entrySet().size() != 0){
      Collections.sort(items, new ItemComparacotr(ordering)); 
    }
    ret.setItems(items);
    
    return ret;
  }
  
  private static class ItemComparacotr implements Comparator<ItemEntry>{
    Map<String, Integer> _ordering = null;
    public ItemComparacotr(Map<String, Integer> ordering){
      _ordering = ordering;
    }
    public int compare(ItemEntry o1, ItemEntry o2) {
      Integer ord1 = _ordering.get(o1.getAbout());
      Integer ord2 = _ordering.get(o2.getAbout());
      return (ord1 != null && ord2 != null) ? ord1.compareTo(ord2) : 0;
    }
  }
  
  @SuppressWarnings("unchecked")
  private static List<ItemEntry> toItems(List<Object> objs){
    List<ItemEntry> items = new ArrayList<ItemEntry>();
    for(Object o : objs){
      if (o instanceof JAXBElement) {
        Object value = ((JAXBElement)o).getValue();
        if(value instanceof TRss10Item){
          TRss10Item it = (TRss10Item)value;
          ItemEntry item = new ItemEntry();
          for(Object io : it.getTitleOrDescriptionOrLink()){
            if (io instanceof JAXBElement) {
              JAXBElement jaxb = (JAXBElement) io;
              if(same(jaxb.getName(), RSS10_TITLE)){
                item.setTitle((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_DESCRIPTION)){
                item.setDescriptionOrSummary((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_LINK)){
                item.addLink((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_CONTRIBUTOR)){
                item.addContributor((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_CREATOR)){
                item.addAuthorOrCreator((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_RIGHTS)){
                item.setRights((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_DATE)){
                item.setPubDate((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_SUBJECT)){
                item.addCategorySubject((String)jaxb.getValue());
              }else{
                LOG.warn("Unexpected jaxbElement under <item>: "+ ToStringBuilder.reflectionToString(jaxb));
              }
            }else if (io instanceof Element) {
              Element e = (Element) io;
              item.getOtherElements().add(e);
            }else{
              LOG.warn("Unexpected object under <item>: "+ToStringBuilder.reflectionToString(io));
            }
          }
          
          if(it.getResource() == null){
            it.setResource(Utils.getHrefLink(item.getLinks()));
          }
          
          items.add(item);
        }
      }
    }
    return items;
  }
  
  /**
   * Copies everything except 'resource' over to <code>ret</code>.
   * @param ret
   * @param input
   * @return
   */
  public static TextInput populateTextinput(TextInput ret, TRss10TextInput input){
    for(Object o : input.getTitleOrDescriptionOrName()){
      if(o == null)
        continue;
      
      if (o instanceof JAXBElement) {
        JAXBElement<?> jaxb = (JAXBElement<?>) o;
        if(same(jaxb.getName(), RSS10_TITLE)){
          ret.setTitle((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_DESCRIPTION)){
          ret.setDescription((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_LINK)){
          ret.setLink((String)jaxb.getValue());
        }else if(same(jaxb.getName(), RSS10_NAME)){
          ret.setName((String)jaxb.getValue());
        }else{
          LOG.warn("Unexpected JAXBElement: "+ToStringBuilder.reflectionToString(jaxb)+" this should not happen!");
        }
      }else if (o instanceof Element) {
        Element e = (Element) o;
        ret.getOtherElements().add(e);
      }else{
        LOG.warn("Unexpected object: "+ToStringBuilder.reflectionToString(o)+" this should not happen!");
      }
    }
    
    ret.setAbout(input.getAbout());
    
    return ret;
  }
  
  /**
   * Copies everything except 'resource' over to <code>ret</code>.
   * @param ret
   * @param img
   * @return
   */
  public static Image populateImage(Image ret, TRss10Image img){
    ret.setLink(img.getLink());
    ret.setTitle(img.getTitle());
    ret.setUrl(img.getUrl());
    ret.setAbout(img.getAbout());
    
    return ret;
  }
}