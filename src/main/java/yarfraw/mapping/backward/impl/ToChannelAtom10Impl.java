package yarfraw.mapping.backward.impl;

import static yarfraw.mapping.backward.impl.Atom10MappingUtils.extractEmail;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.extractTextContent;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toAtomId;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toCategory;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toImage;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toItem;
import static yarfraw.mapping.backward.impl.Atom10MappingUtils.toAtomLink;

import java.util.Locale;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import yarfraw.core.datamodel.AtomAttributes;
import yarfraw.core.datamodel.AtomTextAttributes;
import yarfraw.core.datamodel.AtomTextElementEnum;
import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.CategoryType;
import yarfraw.generated.atom10.elements.DateTimeType;
import yarfraw.generated.atom10.elements.EntryType;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.generated.atom10.elements.GeneratorType;
import yarfraw.generated.atom10.elements.IconType;
import yarfraw.generated.atom10.elements.IdType;
import yarfraw.generated.atom10.elements.LinkType;
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

  private final static QName _EntryTypeTitle_QNAME = new QName("http://www.w3.org/2005/Atom", "title");
  private final static QName _EntryTypeRights_QNAME = new QName("http://www.w3.org/2005/Atom", "rights");
  private final static QName _EntryTypeUpdated_QNAME = new QName("http://www.w3.org/2005/Atom", "updated");
  private final static QName _EntryTypeAuthor_QNAME = new QName("http://www.w3.org/2005/Atom", "author");
  private final static QName _SourceTypeSubtitle_QNAME = new QName("http://www.w3.org/2005/Atom", "subtitle");
  
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
  
  public Channel execute(FeedType feed) throws YarfrawException {
    if(feed == null){
      return null;
    }
    Channel c = new Channel();
    try {
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
          if (CommonUtils.same(jaxbElement.getName(), _EntryTypeTitle_QNAME)) {
            TextType text = (TextType) val;
            c.setTitle(convenientExtractText(c, AtomTextElementEnum.title, text));
          }else if (CommonUtils.same(jaxbElement.getName(), _SourceTypeSubtitle_QNAME)) {
            TextType text = (TextType) val;
            c.setDescription(convenientExtractText(c, AtomTextElementEnum.subtitle, text));
          }else if (CommonUtils.same(jaxbElement.getName(), _EntryTypeAuthor_QNAME)) {
            c.setManagingEditor(extractEmail((PersonType)val));
          }else if(val instanceof CategoryType){
            c.addCategory(toCategory((CategoryType)val));
          }else if (val instanceof GeneratorType) {
            //partially supported
            GeneratorType gen = (GeneratorType)val;
            c.setGenerator(gen.getValue());
          }else if(val instanceof IconType){
            c.setImage(toImage((IconType)val));
          }else if(val instanceof IdType){
            c.setAtomId(toAtomId((IdType)val));
          }else if(val instanceof LinkType){ 
            c.addAtomLink(toAtomLink((LinkType)val));
          }//logo not supported
          else if (CommonUtils.same(jaxbElement.getName(), _EntryTypeRights_QNAME)) {
            //partially supported
            TextType text = (TextType) val;
            c.setCopyright(convenientExtractText(c, AtomTextElementEnum.rights, text));
          }else if (CommonUtils.same(jaxbElement.getName(), _EntryTypeUpdated_QNAME)) {
            //partially supported
            DateTimeType dt = (DateTimeType) val;
            c.setPubDate(dt.getValue().toGregorianCalendar().getTime());
          }else if(val instanceof EntryType){ 
            c.additem(toItem((EntryType)val));
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