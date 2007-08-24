package yarfraw.mapping.backward.impl;

import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

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

  private final static QName _TRssItemTitle_QNAME = new QName("", "title");
  private final static QName _TRssItemDescription_QNAME = new QName("", "description");
//  private final static QName _TRssItemCategory_QNAME = new QName("", "category");
  private final static QName _TRssItemLink_QNAME = new QName("", "link");
//  private final static QName _Item_QNAME = new QName("", "item");
//  private final static QName _Channel_QNAME = new QName("", "channel");
  private final static QName _TRssItemPubDate_QNAME = new QName("", "pubDate");
  private final static QName _TRssChannelTtl_QNAME = new QName("", "ttl");
  private final static QName _TRssChannelWebMaster_QNAME = new QName("", "webMaster");
//  private final static QName _TRssChannelSkipDays_QNAME = new QName("", "skipDays");
//  private final static QName _TRssChannelCloud_QNAME = new QName("", "cloud");
  private final static QName _TRssChannelLanguage_QNAME = new QName("", "language");
//  private final static QName _TRssChannelSkipHours_QNAME = new QName("", "skipHours");
//  private final static QName _TRssChannelTextInput_QNAME = new QName("", "textInput");
  private final static QName _TRssChannelManagingEditor_QNAME = new QName("", "managingEditor");
  private final static QName _TRssChannelDocs_QNAME = new QName("", "docs");
  private final static QName _TRssChannelLastBuildDate_QNAME = new QName("", "lastBuildDate");
//  private final static QName _TRssChannelImage_QNAME = new QName("", "image");
  private final static QName _TRssChannelGenerator_QNAME = new QName("", "generator");
  private final static QName _TRssChannelCopyright_QNAME = new QName("", "copyright");

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
    try {
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
          if(CommonUtils.same(jaxbElement.getName(), _TRssItemTitle_QNAME)){
            c.setTitle((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssItemLink_QNAME)) {
            c.setLink((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssItemDescription_QNAME)) {
            c.setDescription((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelCopyright_QNAME)) {
            c.setCopyright((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelDocs_QNAME)) {
            c.setDocs((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelGenerator_QNAME)) {
            c.setGenerator((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelLanguage_QNAME)) {
            c.setLanguage(new Locale((String)jaxbElement.getValue()));
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelLastBuildDate_QNAME)) {
            c.setLastBuildDate((String)jaxbElement.getValue(), CommonUtils.RFC_FORMAT);
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelManagingEditor_QNAME)) {
            c.setManagingEditor((String)jaxbElement.getValue());
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssItemPubDate_QNAME)) {
            c.setPubDate((String)jaxbElement.getValue(), CommonUtils.RFC_FORMAT);
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelTtl_QNAME)) {
            c.setTtl(Integer.valueOf(jaxbElement.getValue().toString()));
          }else if (CommonUtils.same(jaxbElement.getName(), _TRssChannelWebMaster_QNAME)) {
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
            c.setImage(new Image(image.getUrl(), image.getTitle(), image.getLink(), 
                image.getWidth(), image.getHeight(), image.getDescription()));
          }else if (val instanceof TTextInput) {
            TTextInput in = (TTextInput)val;
            c.setTextInput(new TextInput(in.getTitle(), in.getDescription(), in.getName(), in.getLink()));
          }else{
            //TODO: ignore?
          }
        }
        else if (o instanceof Element) {
          Element e = (Element) o;
          c.getOtherElements().add(e);
        }else{
          //FIXME not sure what to do yet
        }
      }                                           
    }
    catch (Exception e) {
      throw new YarfrawException("Unable to convert input to Channel", e);
    }
    return c;
  }
  
}