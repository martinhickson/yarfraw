package yarfraw.rss20.mapping;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import yarfraw.core.datamodel.Category;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.Guid;
import yarfraw.core.datamodel.Item;
import yarfraw.core.datamodel.Source;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TCategory;
import yarfraw.generated.rss20.elements.TEnclosure;
import yarfraw.generated.rss20.elements.TGuid;
import yarfraw.generated.rss20.elements.TRssItem;
import yarfraw.generated.rss20.elements.TSource;
import yarfraw.rss20.utils.Utils;

/**
 * This function converts a JAXB {@link TRssItem} object to a Yarfraw {@link Item} object.
 * 
 * @author jliang
 *
 */
public class TItemMapper implements Functor<Item, TRssItem, YarfrawException>{

  private final static QName _TRssItemComments_QNAME = new QName("", "comments");
  private final static QName _TRssItemEnclosure_QNAME = new QName("", "enclosure");
  private final static QName _TRssItemTitle_QNAME = new QName("", "title");
  private final static QName _TRssItemDescription_QNAME = new QName("", "description");
  private final static QName _TRssItemCategory_QNAME = new QName("", "category");
  private final static QName _TRssItemGuid_QNAME = new QName("", "guid");
  private final static QName _TRssItemLink_QNAME = new QName("", "link");
  private final static QName _TRssItemPubDate_QNAME = new QName("", "pubDate");
  private final static QName _TRssItemSource_QNAME = new QName("", "source");
  private final static QName _TRssItemAuthor_QNAME = new QName("", "author");

  private static final Functor<Item, TRssItem, YarfrawException> _instance = new TItemMapper();
  public static Functor<Item, TRssItem, YarfrawException> getInstance(){
    return _instance;
  }
  @SuppressWarnings("unchecked")
  public Item execute(TRssItem ti) throws YarfrawException {
    if(ti == null){
      return null;
    }
    Item item = new Item();
    try {
      for(Object o : ti.getTitleOrDescriptionOrLink()){
        if(o == null){
          continue;
        }
        if(ti.getOtherAttributes() != null){
          for(Map.Entry<QName, String> e : ti.getOtherAttributes().entrySet()){
            item.addOtherAttributes(e.getKey(), e.getValue());
          }
        }
        if (o instanceof JAXBElement) {
          JAXBElement jaxbElement = (JAXBElement) o;
          if(Utils.same(jaxbElement.getName(), _TRssItemAuthor_QNAME)){
            item.setAuthor((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemComments_QNAME)) {
            item.setComments((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemDescription_QNAME)) {
            item.setDescription((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemEnclosure_QNAME)) {
            TEnclosure en = (TEnclosure)jaxbElement.getValue();
            item.setEnclosure(new Enclosure(en.getUrl(), en.getLength().longValue(), en.getType()));
          }else if (Utils.same(jaxbElement.getName(), _TRssItemGuid_QNAME)) {
            TGuid guid = (TGuid)jaxbElement.getValue();
            item.setGuid(new Guid(guid.getValue(), guid.isIsPermaLink()));
          }else if (Utils.same(jaxbElement.getName(), _TRssItemLink_QNAME)) {
            item.setLink((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemPubDate_QNAME)) {
            item.setPubDate((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemSource_QNAME)) {
            TSource source = (TSource)jaxbElement.getValue();
            item.setSource(new Source(source.getUrl(), source.getValue()));
          }else if (Utils.same(jaxbElement.getName(), _TRssItemTitle_QNAME)) {
            item.setTitle((String)jaxbElement.getValue());
          }else if (Utils.same(jaxbElement.getName(), _TRssItemCategory_QNAME)) {
            TCategory cat = (TCategory)jaxbElement.getValue();
            item.addCategory(new Category(cat.getValue(), cat.getDomain()));
          }
        }else if (o instanceof TCategory) {
          TCategory cat = (TCategory) o;
          item.addCategory(new Category(cat.getValue(), cat.getDomain()));
        }else if (o instanceof TEnclosure) {
          TEnclosure en = (TEnclosure)o;
          item.setEnclosure(new Enclosure(en.getUrl(), en.getLength().longValue(), en.getType()));
        }else if (o instanceof TGuid) {
          TGuid guid = (TGuid)o;
          item.setGuid(new Guid(guid.getValue(), guid.isIsPermaLink()));
        }else if (o instanceof TSource) {
          TSource source = (TSource)o;
          item.setSource(new Source(source.getUrl(), source.getValue()));
        }else if (o instanceof Element) {
          Element e = (Element) o;
          item.getOtherElements().add(e);
        }else{
            //FIXME: not sure what to do yet
        }
      }
    } catch (Exception e1) {
      throw new YarfrawException("Unable to convert input to Item", e1);
    }

    return item;
  }
  
}