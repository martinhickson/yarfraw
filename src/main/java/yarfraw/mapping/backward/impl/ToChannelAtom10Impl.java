package yarfraw.mapping.backward.impl;
import static yarfraw.io.parser.ElementQName.ATOM10_AUTHOR;
import static yarfraw.io.parser.ElementQName.ATOM10_RIGHTS;
import static yarfraw.io.parser.ElementQName.ATOM10_SUBTITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_TITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_UPDATED;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.extractEmail;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.extractTextContent;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toAtomId;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toAtomLink;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toCategory;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toImage;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toItem;

import java.util.Locale;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.AtomAttributes;
import yarfraw.core.datamodel.AtomTextAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Channel;
import yarfraw.generated.atom10.elements.CategoryType;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.EntryType;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.generated.atom10.elements.GeneratorType;
import yarfraw.generated.atom10.elements.IconType;
import yarfraw.generated.atom10.elements.IdType;
import yarfraw.generated.atom10.elements.LinkType;
import yarfraw.generated.atom10.elements.LogoType;
import yarfraw.generated.atom10.elements.PersonType;
import yarfraw.generated.atom10.elements.TextType;
import yarfraw.mapping.backward.ToChannelAtom10;
import yarfraw.utils.CommonUtils;

/**
 * TODO: document me
 * @author jliang
 *
 */
public class ToChannelAtom10Impl implements ToChannelAtom10{

  private static final Log LOG = LogFactory.getLog(ToChannelAtom10Impl.class);
  private static final ToChannelAtom10 _instance = new ToChannelAtom10Impl();
    
  private ToChannelAtom10Impl() {}
  public static ToChannelAtom10 getInstance(){
    return _instance;
  }
  private static String convenientExtractText(Channel ch, AtomTextElementEnum textEnum, TextType text){
    AtomTextAttributes textAttr = new AtomTextAttributes();
    String ret = extractTextContent(textAttr, text);
    if(textAttr.getBase() != null || textAttr.getLang() != null || textAttr.getOtherAttributes() != null
            || textAttr.getXhtmlDiv() != null || textAttr.getType() != null ){
      ch.putAtomTextAttribute(textEnum, textAttr);
    }
    return ret;
  }
  
  public Channel execute(FeedType feed){
    if(feed == null){
      return null;
    }
    Channel c = new Channel();
    if(feed.getOtherAttributes() != null){
      c.getOtherAttributes().putAll(feed.getOtherAttributes());
    }
    
    if(feed.getLang() != null){
      c.setAtomAttributes(new AtomAttributes(feed.getBase(), new Locale(feed.getLang())));
      c.setLanguage(new Locale(feed.getLang()));
    }
    
    for(Object o : feed.getAuthorOrCategoryOrContributor()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxbElement = (JAXBElement<?>) o;
        Object val = jaxbElement.getValue();
        if (CommonUtils.same(jaxbElement.getName(), ATOM10_TITLE)) {
          TextType text = (TextType) val;
          c.setTitle(convenientExtractText(c, AtomTextElementEnum.title, text));
        }else if (CommonUtils.same(jaxbElement.getName(), ATOM10_SUBTITLE)) {
          TextType text = (TextType) val;
          c.setDescription(convenientExtractText(c, AtomTextElementEnum.subtitle, text));
        }else if (CommonUtils.same(jaxbElement.getName(), ATOM10_AUTHOR)) {
          c.setManagingEditor(extractEmail((PersonType)val));
        }else if(val instanceof CategoryType){
          c.addCategory(toCategory((CategoryType)val));
        }else if (val instanceof GeneratorType) {
          //partially supported
          LOG.info("only the text content of the <generator> element is parsed, the attributes are ignored");
          GeneratorType gen = (GeneratorType)val;
          c.setGenerator(gen.getValue());
        }else if(val instanceof IconType){
          c.setImage(toImage((IconType)val));
        }else if(val instanceof IdType){
          c.setAtomId(toAtomId((IdType)val));
        }else if(val instanceof LinkType){ 
          c.addAtomLink(toAtomLink((LinkType)val));
        }else if(val instanceof LogoType){ 
          LOG.warn("The <logo> element is not supported, it will be ignored");
        }//logo not supported
        else if (CommonUtils.same(jaxbElement.getName(), ATOM10_RIGHTS)) {
          TextType text = (TextType) val;
          c.setCopyright(convenientExtractText(c, AtomTextElementEnum.rights, text));
        }else if (CommonUtils.same(jaxbElement.getName(), ATOM10_UPDATED)) {
          //partially supported
          DateTimeType dt = (DateTimeType) val;
          if(dt.getValue() != null){
            c.setPubDate(dt.getValue().toGregorianCalendar().getTime());
          }
        }else if(val instanceof EntryType){ 
          c.additem(toItem((EntryType)val));
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