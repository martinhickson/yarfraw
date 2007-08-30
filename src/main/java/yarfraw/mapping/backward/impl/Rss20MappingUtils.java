package yarfraw.mapping.backward.impl;
import static yarfraw.io.parser.ElementQName.RSS20_AUTHOR;
import static yarfraw.io.parser.ElementQName.RSS20_COMMENTS;
import static yarfraw.io.parser.ElementQName.RSS20_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS20_LINK;
import static yarfraw.io.parser.ElementQName.RSS20_PUBDATE;
import static yarfraw.io.parser.ElementQName.RSS20_TITLE;

import java.net.URISyntaxException;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.CategorySubject;
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
import yarfraw.utils.CommonUtils;

class Rss20MappingUtils{

  private static final Log LOG = LogFactory.getLog(Rss20MappingUtils.class);
  private static final String ENCODED = "encoded";

  private Rss20MappingUtils(){}
  
  @SuppressWarnings("unchecked")
  public static Item toItem(TRssItem ti) throws YarfrawException {
    if(ti == null){
      return null;
    }
    Item item = new Item();
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
        Object val = jaxbElement.getValue();
        if(CommonUtils.same(jaxbElement.getName(), RSS20_AUTHOR)){
          item.setAuthor((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_COMMENTS)) {
          try {
            item.setComments((String)jaxbElement.getValue());
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_DESCRIPTION)) {
          item.setDescription((String)jaxbElement.getValue());
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_LINK)) {
          try {
            item.setLink((String)jaxbElement.getValue());
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_PUBDATE)) {
          try {
            item.setPubDate((String)jaxbElement.getValue(), CommonUtils.RFC_FORMAT);
          } catch (Exception e) {
            item.setPubDate(CommonUtils.tryParseDate((String)jaxbElement.getValue()));
          }
        }else if (CommonUtils.same(jaxbElement.getName(), RSS20_TITLE)) {
          item.setTitle((String)jaxbElement.getValue());
        }else if (val instanceof TCategory) {
          TCategory cat = (TCategory) val;
          item.addCategory(new CategorySubject(cat.getValue(), cat.getDomain()));
        }else if (val instanceof TEnclosure) {
          TEnclosure en = (TEnclosure)val;
          try {
            item.setEnclosure(new Enclosure(en.getUrl(), 
                    en.getLength().longValue(), 
                    en.getType(), en.getValue()));
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else if (val instanceof TGuid) {
          TGuid guid = (TGuid)val;
          item.setGuid(new Guid(guid.getValue(), guid.isIsPermaLink()));
        }else if (val instanceof TSource) {
          TSource source = (TSource)val;
          try {
            item.setSource(new Source(source.getUrl(), source.getValue()));
          }
          catch (URISyntaxException e) {
            LOG.error("invalid URI, it is ignored", e);
          }
        }else{
          LOG.warn("Unexpected jaxbElement: "+ToStringBuilder.reflectionToString(jaxbElement)+" this should not happen!");
        }
      }else if (o instanceof Element) {
        Element e = (Element) o;
        if(ENCODED.equals(e.getLocalName())){
          item.getContent().addContentText(e.getTextContent());
        }
        item.getOtherElements().add(e);
      }else{
        LOG.warn("Unexpected object: "+ToStringBuilder.reflectionToString(o)+" this should not happen!");
      }
    }

    return item;
  }
}