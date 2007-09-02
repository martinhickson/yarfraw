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
import static yarfraw.utils.XMLUtils.same;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.ChannelFeed;
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

public class ToChannelRss20Impl implements ToChannelRss20{
  private static final Log LOG = LogFactory.getLog(ToChannelRss20Impl.class);
  private static final ToChannelRss20 _instance = new ToChannelRss20Impl();
  
  private ToChannelRss20Impl() {}
  public static ToChannelRss20 getInstance(){
    return _instance;
  }
  
  @SuppressWarnings("unchecked")
  public ChannelFeed execute(TRssChannel ch) throws YarfrawException {
    if(ch == null){
      return null;
    }
    ChannelFeed c = new ChannelFeed();
    
    if(ch.getItem() != null){
      for(TRssItem item : ch.getItem()){
        c.addItem(Rss20MappingUtils.toItem(item));
      }
    }
    c.getOtherAttributes().putAll(ch.getOtherAttributes());
    
    for(Object o : ch.getTitleOrLinkOrDescription()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement) {
        JAXBElement jaxbElement = (JAXBElement) o;
        Object val = jaxbElement.getValue();
        if(same(jaxbElement.getName(), RSS20_TITLE)){
          c.setTitle((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_LINK)) {  
          c.addLink((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_DESCRIPTION)) {
          c.setDescriptionOrSubtitle((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_COPYRIGHTS)) {
          c.setRights((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_DOCS)) {
          c.setDocs((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_GENERATOR)) {
          c.setGenerator((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_LANGUAGE)) {
          c.setLang((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_LAST_BUILD_DATE)) {
          c.setLastBuildOrUpdatedDate((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_MANAGINGEDITOR)) {
          c.addManagingEditorOrAuthorOrPublisher((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_PUBDATE)) {
          c.setPubDate((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_TTL)) {
          c.setTtl(Integer.valueOf(jaxbElement.getValue().toString()));
        }else if (same(jaxbElement.getName(), RSS20_WEBMASTER)) {
          c.addWebMasterOrCreator((String)jaxbElement.getValue());
        }else if (val instanceof TCategory) {
          TCategory cat = (TCategory) val;
          c.addCategorySubject(new CategorySubject(cat.getValue())
                                    .setDomainOrScheme(cat.getDomain()));
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
              cloud.getPort() == null ? null : cloud.getPort().toString(), 
                  cloud.getPath(), cloud.getRegisterProcedure(), 
                  cloud.getProtocol()==null?null:cloud.getProtocol().value()));
        }else if (val instanceof TImage) {
          TImage image = (TImage)val;
          c.setImageOrIcon(new Image(image.getUrl(), image.getTitle(), image.getLink(), 
                image.getWidth(), image.getHeight(), image.getDescription()));
        }else if (val instanceof TTextInput) {
          TTextInput in = (TTextInput)val;
          c.setTexInput(new TextInput(in.getTitle(), in.getDescription(), in.getName(), in.getLink()));
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