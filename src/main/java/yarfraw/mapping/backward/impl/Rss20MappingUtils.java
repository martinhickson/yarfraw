package yarfraw.mapping.backward.impl;
import static yarfraw.io.parser.ElementQName.RSS20_AUTHOR;
import static yarfraw.io.parser.ElementQName.RSS20_COMMENTS;
import static yarfraw.io.parser.ElementQName.RSS20_DESCRIPTION;
import static yarfraw.io.parser.ElementQName.RSS20_LINK;
import static yarfraw.io.parser.ElementQName.RSS20_PUBDATE;
import static yarfraw.io.parser.ElementQName.RSS20_TITLE;
import static yarfraw.utils.XMLUtils.same;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Enclosure;
import yarfraw.core.datamodel.Id;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Source;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TCategory;
import yarfraw.generated.rss20.elements.TEnclosure;
import yarfraw.generated.rss20.elements.TGuid;
import yarfraw.generated.rss20.elements.TRssItem;
import yarfraw.generated.rss20.elements.TSource;
class Rss20MappingUtils{

  private static final Log LOG = LogFactory.getLog(Rss20MappingUtils.class);
 

  private Rss20MappingUtils(){}
  
  @SuppressWarnings("unchecked")
  public static ItemEntry toItem(TRssItem ti) throws YarfrawException {
    if(ti == null){
      return null;
    }
    ItemEntry item = new ItemEntry();
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
        if(same(jaxbElement.getName(), RSS20_AUTHOR)){
          item.addAuthorOrCreator((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_COMMENTS)) {
          item.setComments((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_DESCRIPTION)) {
          item.setDescriptionOrSummary((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_LINK)) {
          item.addLink((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_PUBDATE)) {
          item.setPubDate((String)jaxbElement.getValue());
        }else if (same(jaxbElement.getName(), RSS20_TITLE)) {
          item.setTitle((String)jaxbElement.getValue());
        }else if (val instanceof TCategory) {
          TCategory cat = (TCategory) val;
          item.addCategorySubject(new CategorySubject().setCategoryOrSubjectOrTerm(cat.getValue())
                  .setDomainOrScheme(cat.getDomain()));
        }else if (val instanceof TEnclosure) {
          TEnclosure en = (TEnclosure)val;
          item.setEnclosure(new Enclosure(en.getUrl(), 
                    en.getLength() == null ? null : en.getLength().toString(), 
                    en.getType(), en.getValue()));
        }else if (val instanceof TGuid) {
          TGuid guid = (TGuid)val;
          item.setUid(new Id(guid.getValue()).setPermaLink(guid.isIsPermaLink()));
        }else if (val instanceof TSource) {
          TSource source = (TSource)val;
          item.setSource(new Source(source.getUrl(), source.getValue()));
        }else{
          LOG.warn("Unexpected jaxbElement: "+ToStringBuilder.reflectionToString(jaxbElement)+" this should not happen!");
        }
      }else if (o instanceof Element) {
        Element e = (Element) o;
        item.getOtherElements().add(e);
      }else{
        LOG.warn("Unexpected object: "+ToStringBuilder.reflectionToString(o)+" this should not happen!");
      }
    }

    return item;
  }
}