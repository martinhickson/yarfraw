package yarfraw.core.datamodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;

import yarfraw.utils.CommonUtils;

/**
 * 
 * <li>Rss 2.0 - 
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
 * </li>
 * 
 * <li> Rss 1.0 -
 * An image to be associated with an HTML rendering of the channel. 
 * This image should be of a format supported by the majority of Web browsers. 
 * While the later 0.91 specification allowed for a width of 1-144 and height of 1-400, 
 * convention (and the 0.9 specification) dictate 88x31.
 * </li>
 * <li>Atom 1.0 - 
 * The "atom:icon" element's content is an IRI reference [RFC3987] 
 * which identifies an image which provides iconic visual identification for a feed.
 * <br/>
 * The "atom:logo" element's content is an IRI reference [RFC3987] which identifies an image which provides visual identification for a feed.
 * </li>
 * @author jliang
 *
 */
public class Image extends AbstractBaseObject{
  private String _url;
  private String _title;
  private String _link;
  private Integer _width =88;
  private Integer _height = 31;
  private String _description;  
  public Image(){}

  public Image(String url, String title, String link, Integer width, Integer height,
      String description){
    super();
    setUrl(url);
    _title = title;
    setLink(link);
    setHeight(height);
    setWidth(width);
    _description = description;
  }

  /**
   * <li>Rss 2.0 -
   * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel. 
   * if url is not an valid url
   * </li>
   * <li>Rss 1.0 -
   * The URL of the image to used in the "src" attribute of the channel's image tag when rendered as HTML. 
   * </li> 
   * <li>
   * Atom 1.0 -
   * url of the image
   * </li>
   * @param url - any valid url
   * @return this
   */
  public Image setUrl(String url){
    _url = url;
    return this;
  }
  
  /**
   * <li>Rss 2.0 - 
   * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
   * &lt;img> tag when the channel is rendered in HTML.
   * </li>
   * <li>Rss 1.0 -
   * The alternative text ("alt" attribute) associated with the channel's image tag when rendered as HTML.
   * </li>
   * <li>
   * Atom 1.0 - not supported, this field is ignored.
   * </li>
   */
  public String getTitle() {
    return _title;
  }

  /**
   * <li>Rss 2.0 - 
   * <code>title</code> describes the image, it's used in the ALT attribute of the HTML 
   * &lt;img> tag when the channel is rendered in HTML.
   * </li>
   * <li>Rss 1.0 -
   * The alternative text ("alt" attribute) associated with the channel's image tag when rendered as HTML.
   * </li>
   * <li>
   * Atom 1.0 - not supported, this field is ignored.
   * </li>
   * @param title - string value of the title
   * @return this
   */
  public Image setTitle(String title) {
    _title = title;
    return this;
  }
  
  /**
   * <li> Rss 2.0 -
   * <code>link</code> is the URI of the site, when the channel is rendered, 
    * the image is a link to the site. 
    * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
    * &lt;title> and &lt;link>.
    *</li>   
    *<li> Rss 1.0 - 
    *The URL to which an HTML rendering of the channel image will link. This, as with the channel's title link, is commonly the parent site's home or news page.
    *</li>
    *<li>
   * Atom 1.0 - not supported, this field is ignored.
   * </li> 
  */  
  public Image setLink(String link){
    _link = link;
    return this;
  }
  
  
  /**
   * <li>Rss 2.0 -
   * <code>url</code> is the URI of a GIF, JPEG or PNG image that represents the channel. 
   * if url is not an valid url
   * </li>
   * <li>Rss 1.0 -
   * The URL of the image to used in the "src" attribute of the channel's image tag when rendered as HTML. 
   * </li> 
   * <li>
   * Atom 1.0 - url of the image
   * </li>
   * @return url of the image
   */
  public String getUrl() {
    return _url;
  }

  /**
   * <li> Rss 2.0 -
   * <code>link</code> is the URI of the site, when the channel is rendered, 
    * the image is a link to the site. 
    * (Note, in practice the image &lt;title> and &lt;link> should have the same value as the channel's 
    * &lt;title> and &lt;link>.
    *</li>   
    *<li> Rss 1.0 - 
    *The URL to which an HTML rendering of the channel image will link. This, as with the channel's title link, is commonly the parent site's home or news page.
    *</li>
    *<li>
   * Atom 1.0 - not supported, this field is ignored.
   * </li> 
   * @return
   */
  public String getLink() {
    return _link;
  }

  /**
   * This field is only used by Rss 2.0, it is ignored by other {@link FeedFormat}<br/>
   * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
   * the image in pixels.
   */
  public Integer getWidth() {
    return _width;
  }
  /**
   * <b>This field is only used by Rss 2.0, it is ignored by other {@link FeedFormat}</b><br/>
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
   * <b>This field is only used by Rss 2.0, it is ignored by other {@link FeedFormat}</b><br/>
   * Optional elements include <code>width</code> and <code>height</code>, indicating the width and height of 
   * the image in pixels.
   */  
  public Integer getHeight() {
    return _height;
  }
  /**
   * <b>This field is only used by Rss 2.0, it is ignored by other {@link FeedFormat}</b><br/>
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
   * <b>This field is only used by Rss 2.0, it is ignored by other {@link FeedFormat}</b><br/>
   * <code>description</code> contains text that is included in the TITLE attribute 
   * of the link formed around the image in the HTML rendering.
   */  
  public String getDescription() {
    return _description;
  }
  /**
   * <b>This field is only used by Rss 2.0, it is ignored by other {@link FeedFormat}</b><br/>
   * <code>description</code> contains text that is included in the TITLE attribute 
   * of the link formed around the image in the HTML rendering.
   */    
  public Image setDescription(String description) {
    _description = description;
    return this;
  }
  
  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Image setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public Image addOtherAttributes(QName namespace, String attribute) {
    if(_otherAttributes == null){
      _otherAttributes = new HashMap<QName, String>();
    }
    _otherAttributes.put(namespace, attribute);
    return this;
  }
  

  /**
   * <b>Atom 1.0 only</b><br/>
   * Any element defined by this specification MAY have an xml:base attribute 
   * [W3C.REC-xmlbase-20010627]. When xml:base is used in an Atom Document, 
   * it serves the function described in section 5.1.1 of [RFC3986], establishing 
   * the base URI (or IRI) for resolving any relative references found within the 
   * effective scope of the xml:base attribute.
   * @param base
   * @return
   */
  public Image setBase(String base) {
    _base = base;
    return this;
  }
  /**
   * <li>Rss 2.0 - &lt;language> element. 
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. A list of allowable 
   * values for this element, as provided by Netscape, is here. You may also use values 
   * defined by the W3C.
   * Only &lt;channel> support this element.</li>
   * <li>Rss 1.0 - &lt;dc:language> element. A language of the intellectual content of the resource.
   * Only &lt;channel> and &lt;item> support this element. </li>
   * <li>Atom 1.0 - 'lang' attribute</li>
   * <br/>
   * Note: for Rss 2.0 and Rss 1.0, only &lt;channel> and &lt;item>
   * @param lang
   * @return
   */
  public Image setLang(String lang) {
    _lang = lang;
    return this;
  }
  
  /**
   * <li>Rss 2.0 - &lt;language> element. 
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. A list of allowable 
   * values for this element, as provided by Netscape, is here. You may also use values 
   * defined by the W3C.
   * Only &lt;channel> support this element.</li>
   * <li>Rss 1.0 - &lt;dc:language> element. A language of the intellectual content of the resource.
   * Only &lt;channel> and &lt;item> support this element. </li>
   * <li>Atom 1.0 - 'lang' attribute</li>
   * <br/>
   * Note: for Rss 2.0 and Rss 1.0, only &lt;channel> and &lt;item>
   * @param lang
   * @return
   */
  public Image setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  /**
   * <b>Rss 1.0 only</b><br/>
   * @param resource
   * @return
   */
  public Image setResource(String resource) {
    _resource = resource;
    return this;
  }
  /**
   * <b>Rss 1.0 only</b><br/>
   * @param about
   * @return
   */
  public Image setAbout(String about) {
    _about = about;
    return this;
  }
  ////////////////////////Common setters///////////////////////
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(format == FeedFormat.RSS20){
      CommonUtils.validateNotNull("Image: All required fields in the Image object should be not null", _url, _title, _link);
      CommonUtils.validateUri("Url or link is not a valid URI", _url, _link);
      
    }else{
      CommonUtils.validateNotNull("Image: url should not be null", _url);
      CommonUtils.validateUri("Url is not a valid URI", _url);
    }
    
    if(format == FeedFormat.RSS10){
      CommonUtils.validateNotNull("attribute 'about' is required", getAbout());
    }
  }
}