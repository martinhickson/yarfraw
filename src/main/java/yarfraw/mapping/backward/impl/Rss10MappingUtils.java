package yarfraw.mapping.backward.impl;

import static yarfraw.mapping.ElementQName.RSS10_CREATOR;
import static yarfraw.mapping.ElementQName.RSS10_DATE;
import static yarfraw.mapping.ElementQName.RSS10_DESCRIPTION;
import static yarfraw.mapping.ElementQName.RSS10_LANGUAGE;
import static yarfraw.mapping.ElementQName.RSS10_PUBLISHER;
import static yarfraw.mapping.ElementQName.RSS10_RIGHTS;
import static yarfraw.mapping.ElementQName.RSS10_SUBJECT;
import static yarfraw.mapping.ElementQName.RSS10_LINK;
import static yarfraw.mapping.ElementQName.RSS10_ITEM_TITLE;
import static yarfraw.mapping.ElementQName.RSS10_UPDATEFREQUENCY;
import static yarfraw.utils.CommonUtils.same;

import java.math.BigInteger;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.w3c.dom.Element;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.RdfAttributes;
import yarfraw.core.datamodel.TextInput;
import yarfraw.core.datamodel.YarfrawException;
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
  private static final int MIN_PER_DAY = 60*24;
  private static final int MIN_PER_WEEK = MIN_PER_DAY*7;
  private static final int MIN_PER_MONTH = MIN_PER_DAY*30;
  private static final int MIN_PER_YEAR = MIN_PER_DAY*365;
  
  private Rss10MappingUtils(){}
  
  private static Integer calculateTtl(UpdatePeriodEnum updatePeriod, BigInteger updateFrequency){
    if(updatePeriod == null && updateFrequency == null){
      return null;
    }
    int freq = updateFrequency == null ? 1: updateFrequency.intValue();
    if(updatePeriod == UpdatePeriodEnum.HOURLY){
      return Math.max(1, 60/freq);
    }else if(updatePeriod == UpdatePeriodEnum.DAILY){
      return Math.max(1, MIN_PER_DAY/freq);
    }else if(updatePeriod == UpdatePeriodEnum.MONTHLY){
      return Math.max(1, MIN_PER_MONTH/freq);
    }else if(updatePeriod == UpdatePeriodEnum.WEEKLY){
      return Math.max(1, MIN_PER_WEEK/freq);
    }else if(updatePeriod == UpdatePeriodEnum.YEARLY){
      return Math.max(1, MIN_PER_YEAR/freq);
    }else{
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  public static Channel toChannel(TRss10Channel ch, RDF rdf) throws YarfrawException{
    Channel ret = new Channel();
    if(ch.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(ch.getOtherAttributes());
    }
    UpdatePeriodEnum updatePeriod = null;
    BigInteger updateFrequency = null;
    Map<String, Integer> ordering = new HashMap<String, Integer>();
    try {
      List<Item> items = toItems(rdf.getChannelOrItemOrTextinput());
      for(Object o : ch.getTitleOrLinkOrDescription()){
        if(o == null)
          continue;
        
        if (o instanceof JAXBElement) {
          JAXBElement jaxb = (JAXBElement) o;
          Object val = jaxb.getValue();
          if(same(jaxb.getName(), RSS10_ITEM_TITLE)){
            ret.setTitle((String)jaxb.getValue());
          }else if(same(jaxb.getName(), RSS10_DESCRIPTION)){
            ret.setDescription((String)jaxb.getValue());
          }else if(same(jaxb.getName(), RSS10_LINK)){
            ret.setLink((String)jaxb.getValue());
          }else if(same(jaxb.getName(), RSS10_UPDATEFREQUENCY)){
            updateFrequency = (BigInteger)jaxb.getValue();
          }else if(same(jaxb.getName(), RSS10_SUBJECT)){
            ret.addCategory((String)jaxb.getValue());
            
          }else if(same(jaxb.getName(), RSS10_PUBLISHER)){
            ret.setWebMaster((String)jaxb.getValue());
          }else if(same(jaxb.getName(), RSS10_CREATOR)){
            ret.setManagingEditor((String)jaxb.getValue());
          }else if(same(jaxb.getName(), RSS10_RIGHTS)){
            ret.setCopyright((String)jaxb.getValue());
          }else if(same(jaxb.getName(), RSS10_DATE)){
            ret.setPubDate(CommonUtils.tryParseISODate((String)jaxb.getValue()));
          }else if(same(jaxb.getName(), RSS10_LANGUAGE)){
            ret.setLanguage(new Locale((String)jaxb.getValue()));
          }else if(val instanceof UpdatePeriodEnum){
            updatePeriod = (UpdatePeriodEnum)val;
          }else if(val instanceof TRss10Image){
            ret.setImage(toImage((TRss10Image)val));
          }else if(val instanceof TRss10TextInput){
            ret.setTextInput(toTextInput((TRss10TextInput)val));
          }else if(val instanceof Items){
            Seq seq = ((Items)val).getSeq();
            int i = 0;
            for(Li li : seq.getLi()){
              ordering.put(li.getResource(), i++);
            }
          }else if(val instanceof Seq){
            
          }else{
            //TODO: ignore?
          }
        }else if (o instanceof Element) {
          Element e = (Element) o;
          ret.getOtherElements().add(e);
        }else{
            //FIXME: not sure what to do yet
        }
      }  
      ret.setTtl(calculateTtl(updatePeriod, updateFrequency));
      if(ordering.entrySet().size() != 0){
        Collections.sort(items, new ItemComparacotr(ordering)); 
      }
      ret.setItems(items);
    } catch (Exception e) {
      throw new YarfrawException("Unable to map feed to channel", e);
    }
    return ret;
  }
  
  private static class ItemComparacotr implements Comparator<Item>{
    Map<String, Integer> _ordering = null;
    public ItemComparacotr(Map<String, Integer> ordering){
      _ordering = ordering;
    }
    public int compare(Item o1, Item o2) {
      Integer ord1 = _ordering.get(o1.getRdfAttributes().getAbout());
      Integer ord2 = _ordering.get(o2.getRdfAttributes().getAbout());
      return (ord1 != null && ord2 != null) ? ord1.compareTo(ord2) : 0;
    }
  }
  
  @SuppressWarnings("unchecked")
  private static List<Item> toItems(List<Object> objs) throws URISyntaxException, ParseException, YarfrawException{
    List<Item> items = new ArrayList<Item>();
    for(Object o : objs){
      if (o instanceof JAXBElement) {
        Object value = ((JAXBElement)o).getValue();
        if(value instanceof TRss10Item){
          TRss10Item it = (TRss10Item)value;
          Item item = new Item();
          for(Object io : it.getTitleOrDescriptionOrLink()){
            if (io instanceof JAXBElement) {
              JAXBElement jaxb = (JAXBElement) io;
              if(same(jaxb.getName(), RSS10_ITEM_TITLE)){
                item.setTitle((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_DESCRIPTION)){
                item.setDescription((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_LINK)){
                item.setLink((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_CREATOR)){
                item.setAuthor((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_RIGHTS)){
                item.setRights((String)jaxb.getValue());
              }else if(same(jaxb.getName(), RSS10_DATE)){
                item.setPubDate(CommonUtils.tryParseISODate((String)jaxb.getValue()));
              }else if(same(jaxb.getName(), RSS10_SUBJECT)){
                item.addCategory((String)jaxb.getValue());
              }
            }
          }
          item.setRdfAttributes(new RdfAttributes(it.getResource() == null ? item.getLink().toString() : it.getResource(), 
              it.getAbout()));
          items.add(item);
        }
      }
    }
    return items;
  }
  
  private static TextInput toTextInput(TRss10TextInput input) throws URISyntaxException{
    TextInput ret = new TextInput();
    ret.setDescription(input.getDescription());
    ret.setLink(input.getLink());
    ret.setTitle(input.getTitle());
    ret.setName(input.getName());
    if(input.getAbout() != null || input.getResource() != null){
      ret.setRdfAttributes(new RdfAttributes(input.getResource(), input.getAbout()));
    }
    return ret;
  }
  
  private static Image toImage(TRss10Image img) throws URISyntaxException{
    Image ret = new Image();
    ret.setLink(img.getLink());
    ret.setTitle(img.getTitle());
    ret.setUrl(img.getUrl());
    if(img.getAbout() != null || img.getResource() != null){
      ret.setRdfAttributes(new RdfAttributes(img.getResource(), img.getAbout()));
    }
    return ret;
  }
}