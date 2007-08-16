package yarfraw.rss20.mapping;

import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import yarfraw.rss20.datamodel.Category;
import yarfraw.rss20.datamodel.Channel;
import yarfraw.rss20.datamodel.Cloud;
import yarfraw.rss20.datamodel.Day;
import yarfraw.rss20.datamodel.Image;
import yarfraw.rss20.datamodel.TextInput;
import yarfraw.rss20.datamodel.YarfrawException;
import generated.TCategory;
import generated.TCloud;
import generated.TImage;
import generated.TRssChannel;
import generated.TRssItem;
import generated.TSkipDay;
import generated.TSkipDaysList;
import generated.TSkipHoursList;
import generated.TTextInput;
import yarfraw.rss20.utils.Utils;

/**
 * This function converts a JAXB {@link TRssChannel} object to a Yarfraw {@link Channel} object.
 * 
 * @author jliang
 *
 */
public class ChannelMapperImpl implements TChannelMapper<Channel>{

  private final static QName _TRssItemTitle_QNAME = new QName("", "title");
  private final static QName _TRssItemDescription_QNAME = new QName("", "description");
  private final static QName _TRssItemCategory_QNAME = new QName("", "category");
  private final static QName _TRssItemLink_QNAME = new QName("", "link");
  private final static QName _Item_QNAME = new QName("", "item");
//  private final static QName _Channel_QNAME = new QName("", "channel");
  private final static QName _TRssItemPubDate_QNAME = new QName("", "pubDate");
  private final static QName _TRssChannelTtl_QNAME = new QName("", "ttl");
  private final static QName _TRssChannelWebMaster_QNAME = new QName("", "webMaster");
  private final static QName _TRssChannelSkipDays_QNAME = new QName("", "skipDays");
  private final static QName _TRssChannelCloud_QNAME = new QName("", "cloud");
  private final static QName _TRssChannelLanguage_QNAME = new QName("", "language");
  private final static QName _TRssChannelSkipHours_QNAME = new QName("", "skipHours");
  private final static QName _TRssChannelTextInput_QNAME = new QName("", "textInput");
  private final static QName _TRssChannelManagingEditor_QNAME = new QName("", "managingEditor");
  private final static QName _TRssChannelDocs_QNAME = new QName("", "docs");
  private final static QName _TRssChannelLastBuildDate_QNAME = new QName("", "lastBuildDate");
  private final static QName _TRssChannelImage_QNAME = new QName("", "image");
  private final static QName _TRssChannelGenerator_QNAME = new QName("", "generator");
  private final static QName _TRssChannelCopyright_QNAME = new QName("", "copyright");

  private static final TChannelMapper<Channel> _instance = new ChannelMapperImpl();
  
  private ChannelMapperImpl(){}
  public static TChannelMapper<Channel> getInstance(){
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
          c.additem(TItemMapper.getInstance().execute(item));
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
          if(Utils.same(jaxbElement.getName(), _TRssItemTitle_QNAME)){
            c.setTitle((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemLink_QNAME)) {
            c.setLink((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _Item_QNAME)) {
            c.additem(TItemMapper.getInstance().execute((TRssItem)jaxbElement.getValue()));
          }
          else if (Utils.same(jaxbElement.getName(), _TRssItemDescription_QNAME)) {
            c.setDescription((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemDescription_QNAME)) {
            c.setDescription((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemCategory_QNAME)) {
            TCategory cat = (TCategory)jaxbElement.getValue();
            c.addCategory(new Category(cat.getValue(), cat.getDomain()));
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelSkipDays_QNAME)) {
            TSkipDaysList sdl = (TSkipDaysList)jaxbElement.getValue();
            if(sdl != null){
              for(TSkipDay day : sdl.getDay()){
                c.addSkipDay(Day.valueOf(day.value()));
              }
            }
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelSkipHours_QNAME)) {
            TSkipHoursList shl = (TSkipHoursList)jaxbElement.getValue();
            if(shl != null){
              for(Integer h : shl.getHour()){
                c.addSkipHour(h);
              }
            }
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelCloud_QNAME)) {
            TCloud cloud = (TCloud)jaxbElement.getValue();
            c.setCloud(new Cloud(cloud.getDomain(), 
                cloud.getPort() == null? null : cloud.getPort().intValue(), 
                    cloud.getPath(), cloud.getRegisterProcedure(), 
                    cloud.getProtocol() == null ? null : cloud.getProtocol().value()));
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelCopyright_QNAME)) {
            c.setCopyright((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelDocs_QNAME)) {
            c.setDocs((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelGenerator_QNAME)) {
            c.setGenerator((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelImage_QNAME)) {
            TImage image = (TImage)jaxbElement.getValue();
            c.setImage(new Image(image.getUrl(), image.getTitle(), image.getLink(), 
                image.getWidth(), image.getHeight(), image.getDescription()));
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelLanguage_QNAME)) {
            c.setLanguage(new Locale((String)jaxbElement.getValue()));
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelLastBuildDate_QNAME)) {
            c.setLastBuildDate((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemLink_QNAME)) {
            c.setLink((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelManagingEditor_QNAME)) {
            c.setManagingEditor((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemPubDate_QNAME)) {
            c.setPubDate((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelTextInput_QNAME)) {
            TTextInput in = (TTextInput)jaxbElement.getValue();
            c.setTexInput(new TextInput(in.getTitle(), in.getDescription(), in.getName(), in.getLink()));
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelTtl_QNAME)) {
            c.setTtl(Integer.valueOf(jaxbElement.getValue().toString()));
          }else if (Utils.same(jaxbElement.getName(), _TRssChannelWebMaster_QNAME)) {
            c.setWebMaster((String)jaxbElement.getValue());
          }
        }else if (o instanceof TCategory) {
          TCategory cat = (TCategory) o;
          c.addCategory(new Category(cat.getValue(), cat.getDomain()));
        }else if (o instanceof TSkipDaysList) {
          TSkipDaysList sdl = (TSkipDaysList)o;
          for(TSkipDay day : sdl.getDay()){
            c.addSkipDay(Day.valueOf(day.value()));
          }
          
        }else if (o instanceof TSkipHoursList) {
          TSkipHoursList shl = (TSkipHoursList)o;
          for(Integer h : shl.getHour()){
            c.addSkipHour(h);
          }
        }else if (o instanceof TCloud) {
          TCloud cloud = (TCloud)o;
          c.setCloud(new Cloud(cloud.getDomain(), 
              cloud.getPort() == null? null : cloud.getPort().intValue(), 
                  cloud.getPath(), cloud.getRegisterProcedure(), 
                  cloud.getProtocol() == null ? null : cloud.getProtocol().value()));
        }else if (o instanceof TImage) {
          TImage image = (TImage)o;
          c.setImage(new Image(image.getUrl(), image.getTitle(), image.getLink(), 
              image.getWidth().intValue(), image.getHeight().intValue(), image.getDescription()));
        }else if (o instanceof TTextInput) {
          TTextInput in = (TTextInput)o;
          c.setTexInput(new TextInput(in.getTitle(), in.getDescription(), in.getName(), in.getLink()));
        }else if (o instanceof TRssItem) {
          c.additem(TItemMapper.getInstance().execute((TRssItem)o));
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