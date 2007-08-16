package yarfraw.rss20.datamodel;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBElement;

import generated.ObjectFactory;
import generated.TImage;
import yarfraw.rss20.utils.Utils;

/**
 * {@link Image} is an optional sub-element of {@link Image}, 
 * which contains three required and three optional sub-elements.
 * <p>
 * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel.
 * <p>
 * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
 * &lt;img> tag when the channel is rendered in HTML.
 * <p>
 * <code>link</code> is the URI of the site, when the channel is rendered, 
 * the image is a link to the site. 
 * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
 * &lt;title> and &lt;link>.
 * <p>
 * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
 * the image in pixels.
 * <p> 
 * <code>description</code> contains text that is included in the TITLE attribute 
 * of the link formed around the image in the HTML rendering.
 * <p>
 * Maximum value for width is 144, default value is 88.
 * Maximum value for height is 400, default value is 31.
 * 
 * @author jliang
 *
 */
public class Image extends AbstractBaseObject{
  private URI _url;
  private String _title;
  private URI _link;
  private Integer _width =88;
  private Integer _height = 31;
  private String _description;
  public Image(){}
  public static Image create(){
    return new Image();
  }
  public Image(URI url, String title, URI link, int width, int height,
      String description) {
    super();
    _url = url;
    _title = title;
    _link = link;
    _width = width;
    _height = height;
    _description = description;
  }

  public Image(String url, String title, String link, Integer width, Integer height,
      String description) throws URISyntaxException {
    super();
    setUrl(url);
    _title = title;
    setLink(link);
    setHeight(height);
    setWidth(width);
    _description = description;
  }

  /**
 * {@link Image} is an optional sub-element of {@link Image}, 
 * which contains three required and three optional sub-elements.
 * <p>
 * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel.
 * <p>
 * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
 * &lt;img> tag when the channel is rendered in HTML.
 * <p>
 * <code>link</code> is the URI of the site, when the channel is rendered, 
 * the image is a link to the site. 
 * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
 * &lt;title> and &lt;link>.
 * <p>
   * @throws URISyntaxException  if <code>link</code> or <code>url</code> is an invalid URI
   */
  public Image(String url, String title, String link) throws URISyntaxException {
    _title = title;
    setUrl(url);
    setLink(link);
  }
  
  /**
   * {@link Image} is an optional sub-element of {@link Image}, 
   * which contains three required and three optional sub-elements.
   * <p>
   * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel.
   * <p>
   * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
   * &lt;img> tag when the channel is rendered in HTML.
   * <p>
   * <code>link</code> is the URI of the site, when the channel is rendered, 
   * the image is a link to the site. 
   * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
   * &lt;title> and &lt;link>.
   * <p>
     */
    public Image(URI url, String title, URI link) {
      super();
      _url = url;
      _title = title;
      _link = link;
    }
  /**
   * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel.
   */
  public URI getUrl() {
    return _url;
  }
  /**
   * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel.
   * @throws URISyntaxException  
   * if url is not an valid url 
   * 
   */  
  public Image setUrl(String url) throws URISyntaxException{
    if(url == null){
      _url = null;
    }else{
      _url = new URI(url.trim());
    }
    return this;
  }
  /**
   * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel. 
   */  
  public Image setUrl(URI url){
    _url = url;
    return this;
  }
  
  
  
  /**
   * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
   * &lt;img> tag when the channel is rendered in HTML.
   */
  public String getTitle() {
    return _title;
  }
  /**
   * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
   * &lt;img> tag when the channel is rendered in HTML.
   */  
  public Image setTitle(String title) {
    _title = title;
    return this;
  }
  /**
    * <code>link</code> is the URI of the site, when the channel is rendered, 
     * the image is a link to the site. 
     * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
     * &lt;title> and &lt;link>.
   */
  public URI getLink() {
    return _link;
  }
  /**
   * <code>link</code> is the URI of the site, when the channel is rendered, 
    * the image is a link to the site. 
    * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
    * &lt;title> and &lt;link>.
    * 
   * @throws URISyntaxException 
   * if link is an invalid url  
   * 
  */  
  public Image setLink(String link) throws URISyntaxException  {
    if(link == null){
      _link = null;
    }else{
      _link = new URI(link.trim());
    }
    return this;
  }
  
  /**
   * <code>link</code> is the URI of the site, when the channel is rendered, 
    * the image is a link to the site. 
    * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
    * &lt;title> and &lt;link>.  
  */  
  public Image setLink(URI link) {
    _link = link;
    return this;
  }
  
  /**
   * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
   * the image in pixels.
   */
  public Integer getWidth() {
    return _width;
  }
  /**
   * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
   * the image in pixels.
 * Maximum value for width is 144, default value is 88. 
   */  
  public Image setWidth(Integer width) {
    if(width == null){
      _width = 88;
      return this;
    }
    if(width > 144 || width < 0){
      throw new IllegalArgumentException("Maximum value for width is 144, according to RSS20 specs");
    }    
    _width = width;
    return this;
  }
  /**
   * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
   * the image in pixels.
   */  
  public Integer getHeight() {
    return _height;
  }
  /**
   * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
   * the image in pixels.
   * 
 * Maximum value for height is 400, default value is 31.
   */  
  public Image setHeight(Integer height) {
    if(height == null){
      _width = 31;
      return this;
    }
    if(height > 400 || height < 0){
      throw new IllegalArgumentException("Maximum value for height is 400, according to RSS20 specs");
    }
    _height = height;
    return this;
  }
  /**
   * <code>description</code> contains text that is included in the TITLE attribute 
   * of the link formed around the image in the HTML rendering.
   */  
  public String getDescription() {
    return _description;
  }
  /**
   * <code>description</code> contains text that is included in the TITLE attribute 
   * of the link formed around the image in the HTML rendering.
   */    
  public Image setDescription(String description) {
    _description = description;
    return this;
  }
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    Utils.validateNotNull("Image: All required fields in the Image object should be not null", _url, _title, _link);
  }
  
  private TImage toTImage(){
    TImage ret = new TImage();
    ret.setDescription(_description);
    ret.setHeight(_height);
    ret.setWidth(_width);
    if(_link != null){
      ret.setLink(_link.toString());      
    }
    ret.setTitle(_title);
    if(_url != null){
      ret.setUrl(_url.toString());
    }
    return ret;
  }
  
  public JAXBElement<TImage> toTImageJAXB(){
    return new ObjectFactory().createTRssChannelImage(toTImage());
  }
}