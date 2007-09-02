package yarfraw.mapping.backward.impl;
import static yarfraw.io.parser.ElementQName.ATOM10_AUTHOR;
import static yarfraw.io.parser.ElementQName.ATOM10_CONTRIBUTOR;
import static yarfraw.io.parser.ElementQName.ATOM10_RIGHTS;
import static yarfraw.io.parser.ElementQName.ATOM10_SUBTITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_TITLE;
import static yarfraw.io.parser.ElementQName.ATOM10_UPDATED;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toAtomLink;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toCategorySubject;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toId;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toImage;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toItem;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toPersonType;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toText;
import static yarfraw.utils.XMLUtils.same;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.Generator;
import yarfraw.core.datamodel.Image;
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
  /**
   * atomFeed =
   element atom:feed {
      atomCommonAttributes,
      (atomAuthor*
       & atomCategory*
       & atomContributor*
       & atomGenerator?
       & atomIcon?
       & atomId
       & atomLink*
       & atomLogo?
       & atomRights?
       & atomSubtitle?
       & atomTitle
       & atomUpdated
       & extensionElement*),
      atomEntry*
   }
   */
  public ChannelFeed execute(FeedType feed){
    if(feed == null){
      return null;
    }
    ChannelFeed c = new ChannelFeed();

    c.setLang(feed.getLang());
    c.setBase(feed.getBase());
    c.getOtherAttributes().putAll(feed.getOtherAttributes());
    for(Object o : feed.getAuthorOrCategoryOrContributor()){
      if(o == null){
        continue;
      }
      if (o instanceof JAXBElement<?>) {
        JAXBElement<?> jaxbElement = (JAXBElement<?>) o;
        Object val = jaxbElement.getValue();
        if (same(jaxbElement.getName(), ATOM10_TITLE)) {
          TextType text = (TextType) val;
          c.setTitle(toText(text));
        }else if (same(jaxbElement.getName(), ATOM10_SUBTITLE)) {
          TextType text = (TextType) val;
          c.setDescriptionOrSubtitle(toText(text));
        }else if (same(jaxbElement.getName(), ATOM10_AUTHOR)) {
          c.addManagingEditorOrAuthorOrPublisher(toPersonType((PersonType)val));
        }else if(same(jaxbElement.getName(), ATOM10_CONTRIBUTOR)){
          c.addContributor(toPersonType((PersonType)val));
        }else if(val instanceof CategoryType){
          c.addCategorySubject(toCategorySubject((CategoryType)val));
        }else if (val instanceof GeneratorType) {
          GeneratorType gen = (GeneratorType)val;
          c.setGenerator(toGenerator(gen));
        }else if(val instanceof IconType){
          c.setImageOrIcon(toImage((IconType)val));
        }else if(val instanceof IdType){
          c.setUid(toId((IdType)val));
        }else if(val instanceof LinkType){ 
          c.addLink(toAtomLink((LinkType)val));
        }else if(val instanceof LogoType){ 
          LogoType logo = (LogoType)val;
          c.setLogo(toLogo(logo));
        }
        else if (same(jaxbElement.getName(), ATOM10_RIGHTS)) {
          TextType text = (TextType) val;
          c.setRights(toText(text));
        }else if (same(jaxbElement.getName(), ATOM10_UPDATED)) {
          //partially supported
          DateTimeType dt = (DateTimeType) val;
          if(dt.getValue() != null){
            c.setLastBuildOrUpdatedDate(dt.getValue());
          }
        }else if(val instanceof EntryType){ 
          c.addItem(toItem((EntryType)val));
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
  
  private static Generator toGenerator(GeneratorType in){
    Generator ret = new Generator(in.getValue());
    ret.setUri(in.getUri());
    ret.setVersion(in.getVersion());
    ret.setLang(in.getLang());
    ret.setBase(in.getBase());
    ret.getOtherAttributes().putAll(in.getOtherAttributes());
    return ret;
    
  }
  public static Image toLogo(LogoType in){
    Image ret= new Image();
    ret.setUrl(in.getValue());
    ret.setBase(in.getBase());
    ret.setLang(in.getLang());
    if(in.getOtherAttributes() != null){
      ret.getOtherAttributes().putAll(in.getOtherAttributes());
    }
    
    return ret;
  }

}