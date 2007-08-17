package yarfraw.mapping.forward.impl;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Image;
import yarfraw.core.datamodel.TextInput;
import yarfraw.generated.rss10.elements.ObjectFactory;
import yarfraw.generated.rss10.elements.TRss10Image;
import yarfraw.generated.rss10.elements.TRss10TextInput;

/**
 * Util methods for mapping Yarfraw core model to Rss10 Jaxb model
 * @author jliang
 *
 */
class Rss10MappingUtils {
  private Rss10MappingUtils(){}
  private static final ObjectFactory FACTORY = new ObjectFactory ();
  
  public static JAXBElement<TRss10Image> toRss10Image(Image image){
    TRss10Image ret = FACTORY.createTRss10Image();
    //not supported
//    ret.setDescription(image.getDescription());
//    ret.setHeight(image.getHeight());
//    ret.setWidth(image.getWidth());

    if(image.getRdfAttributes() != null){
      ret.setAbout(image.getRdfAttributes().getAbout() == null ? null : image.getRdfAttributes().getAbout().toString());
      ret.setResource(image.getRdfAttributes().getResource() == null ? null : image.getRdfAttributes().getResource().toString());
    }
    if(image.getLink() != null){
      ret.setLink(image.getLink().toString());      
    }
    ret.setTitle(image.getTitle());
    if(image.getUrl() != null){
      ret.setUrl(image.getUrl().toString());
    }
    return FACTORY.createTRss10ChannelImage(ret);
  }

  public static JAXBElement<TRss10TextInput> toRss10TextInput(TextInput texInput) {
    TRss10TextInput ret = FACTORY.createTRss10TextInput();

    if(texInput.getRdfAttributes() != null){
      ret.setAbout(texInput.getRdfAttributes().getAbout() == null ? null : texInput.getRdfAttributes().getAbout().toString());
      ret.setResource(texInput.getRdfAttributes().getResource() == null ? null : texInput.getRdfAttributes().getResource().toString());
    }
    ret.setDescription(texInput.getDescription());
    if(texInput.getLink() != null){
      ret.setLink(texInput.getLink().toString());
    }
    ret.setName(texInput.getName());
    ret.setTitle(texInput.getTitle());
    return FACTORY.createTextinput(ret);
  }
  
}