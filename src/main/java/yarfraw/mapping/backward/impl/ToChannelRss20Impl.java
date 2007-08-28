package yarfraw.mapping.backward.impl;
import static yarfraw.io.parser.ElementQName.RSS20_COPYRIGHTS;
import static yarfraw.io.parser.ElementQName.RSS20_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS20_DOCS;
import static yarfraw.io.parser.ElementQName.RSS20_GENERATOR;
import static yarfraw.io.parser.ElementQName.RSS20_LANGUAGE;
import static yarfraw.io.parser.ElementQName.RSS20_LAST_BUILD_DATE;
import static yarfraw.io.parser.ElementQName.RSS20_LINK;
import static yarfraw.io.parser.ElementQName.RSS20_MANAGINGEDITOR;
import static yarfraw.io.parser.ElementQName.RSS20_PUBDATE;
import static yarfraw.io.parser.ElementQName.RSS20_TITLE;
import static yarfraw.io.parser.ElementQName.RSS20_TTL;
import static yarfraw.io.parser.ElementQName.RSS20_WEBMASTER;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.Cloud;
import yarfraw.core.datamodel.Day;
import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.TextInput;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TCategory;
import yarfraw.generated.rss20.elements.TCloud;
import yarfraw.generated.rss20.elements.TImage;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.generated.rss20.elements.TRssItem;
import yarfraw.generated.rss20.elements.TSkipDay;
import yarfraw.generated.rss20.elements.TSkipDaysList;
import yarfraw.generated.rss20.elements.TSkipHoursList;
import yarfraw.generated.rss20.elements.TTextInput;
import yarfraw.mapping.backward.ToChannelRss20;
import yarfraw.utils.CommonUtils;

public class ToChannelRss20Impl implements ToChannelRss20{
  private static final Log LOG = LogFactory.getLog(ToChannelRss20Impl.class);
  private static final ToChannelRss20 _instance = new ToChannelRss20Impl();
  
  private ToChannelRss20Impl() {}
  public static ToChannelRss20 getInstance(){
    return _instance;
  }
  
  @SuppressWarnings("unchecked")
  public Channel execute(TRssChannel ch) throws YarfrawException {
    if(ch == null){
      return null;
    }
    Channel c = new Channel();
    
    if(ch.getItem() != null){
      for(TRssItem item : ch.getItem()){
        c.additem(Rss20MappingUtils.toItem(item));
      }
    }
    if(ch.getOtherAttributes() != null){
      for(Map.Entry<QName, String> e : ch.getOtherAttributes().entrySet()){
        c.addOtherAttributes(e.getKey(), e.getValue());
      }
    }
    for(Object o : ch.getTitleOrLinkOrDescription()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement) {
        JAXBElement jaxbElement = (JAXBElement) o;
        Object val = jaxbElement.getValue();
        if(CommonUtils.same(jaxbElement.getName(), RSS20_TITLE)){
          c.setTitle((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_LINK)) {
          try {
            c.setLink((String)jaxbElement.getValue());
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_DESCRIPTION)) {
          c.setDescription((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_COPYRIGHTS)) {
          c.setCopyright((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_DOCS)) {
          try {
            c.setDocs((String)jaxbElement.getValue());
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_GENERATOR)) {
          c.setGenerator((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_LANGUAGE)) {
          c.setLanguage(new Locale((String)jaxbElement.getValue()));
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_LAST_BUILD_DATE)) {
          try {
            c.setLastBuildDate((String)jaxbElement.getValue(), CommonUtils.RFC_FORMAT);
          } catch (Exception e) {
            c.setLastBuildDate(CommonUtils.tryParseDate((String)jaxbElement.getValue()));
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_MANAGINGEDITOR)) {
          c.setManagingEditor((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_PUBDATE)) {
          try {
            c.setPubDate((String)jaxbElement.getValue(), CommonUtils.RFC_FORMAT);
          } catch (Exception e) {
            c.setPubDate(CommonUtils.tryParseDate((String)jaxbElement.getValue()));
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_TTL)) {
          c.setTtl(Integer.valueOf(jaxbElement.getValue().toString()));
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_WEBMASTER)) {
          c.setWebMaster((String)jaxbElement.getValue());
        }else if (val instanceof TCategory) {
          TCategory cat = (TCategory) val;
          c.addCategory(new Category(cat.getValue(), cat.getDomain()));
        }else if (val instanceof TSkipDaysList) {
          TSkipDaysList sdl = (TSkipDaysList)val;
          for(TSkipDay day : sdl.getDay()){
            c.addSkipDay(Day.valueOf(day.value()));
          }
          
        }else if (val instanceof TSkipHoursList) {
          TSkipHoursList shl = (TSkipHoursList)val;
          for(Integer h : shl.getHour()){
            c.addSkipHour(h);
          }
        }else if (val instanceof TCloud) {
          TCloud cloud = (TCloud)val;
          c.setCloud(new Cloud(cloud.getDomain(), 
              cloud.getPort() == null? null : cloud.getPort().intValue(), 
                  cloud.getPath(), cloud.getRegisterProcedure(), 
                  cloud.getProtocol() == null ? null : cloud.getProtocol().value()));
        }else if (val instanceof TImage) {
          TImage image = (TImage)val;
          try {
            c.setImage(new Image(image.getUrl(), image.getTitle(), image.getLink(), 
                image.getWidth(), image.getHeight(), image.getDescription()));
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else if (val instanceof TTextInput) {
          TTextInput in = (TTextInput)val;
          try {
            c.setTextInput(new TextInput(in.getTitle(), in.getDescription(), in.getName(), in.getLink()));
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else{
          LOG.warn("Unexpected jaxbElement: "+ToStringBuilder.reflectionToString(jaxbElement)+" this should not happen!");
        }
      }
      else if (o instanceof Element) {
        Element e = (Element) o;
        c.getOtherElements().add(e);
      }else{
        LOG.warn("Unexpected object: "+ToStringBuilder.reflectionToString(o)+" this should not happen!");
      }
    }                                           
    
    return c;
  }
  
}