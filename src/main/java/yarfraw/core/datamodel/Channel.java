package yarfraw.core.datamodel;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.generated.rss20.elements.ObjectFactory;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.generated.rss20.elements.TSkipDay;
import yarfraw.generated.rss20.elements.TSkipDaysList;
import yarfraw.generated.rss20.elements.TSkipHoursList;
import yarfraw.rss20.utils.Utils;
import yarfraw.rss20.utils.XMLUtils;
/**
 * The name of the channel. It's how people refer to your service. 
 * If you have an HTML website that contains the same information as your RSS file, 
 * the title of your channel should be the same as the title of your website.
 * 
 * @author jliang
 *
 */
public class Channel extends AbstractBaseObject{

  private List<Item> _items = new ArrayList<Item>();
  private String _title;
  private URI _link;
  private String _description;
  
  private Locale _language;
  private String _copyright;
  private String _managingEditor;
  private String _webMaster;
  private Date _pubDate;
  private Date _lastBuildDate;
  private Set<Category> _category = new HashSet<Category>();
  private String _generator;
  private URI _docs;
  private Integer _ttl;
  private Cloud _cloud;
  private Image _image;
  private TextInput _texInput;
  private Set<Integer> _skipHours = new HashSet<Integer>();
  private Set<Day> _skipDays = new HashSet<Day>();
  private List<Element> _otherElements = new ArrayList<Element>();
  private Map<QName, String> _otherAttributes = new HashMap<QName, String>();
  
  public static Channel create(){
    return new Channel();
  }
  public Channel(){}
  /**
   * Title, link, description are required.
   * 
   * @param title The name of the channel. It's how people refer to your service. If you have an HTML website that contains the same information as your RSS file, the title of your channel should be the same as the title of your website.
   * @param link  The URL to the HTML website corresponding to the channel.
   * @param description Phrase or sentence describing the channel.
   */
  public Channel(String title, URI link, String description) {
    super();
    setLink(link);
    setTitle(title);
    setDescription(description);
  }
  
  public Channel(String title, String link, String description) throws URISyntaxException {
    super();
    setLink(link);
    setTitle(title);
    setDescription(description);
  }

  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Map<QName, String> getOtherAttributes() {
    return _otherAttributes;
  }
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public Channel setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public Channel addOtherAttributes(QName namespace, String attribute) {
    if(_otherAttributes == null){
      _otherAttributes = new HashMap<QName, String>();
    }
    _otherAttributes.put(namespace, attribute);
    return this;
  }
    
  
  /**
* A channel may contain any number of {@link Item}s. 
 * An item may represent a "story" -- much like a story in a newspaper or magazine; 
 * if so its description is a synopsis of the story, and the link points to the full story. 
 * An item may also be complete in itself, if so, the description contains the text 
 * (entity-encoded HTML is allowed; see examples), 
 * and the link and title may be omitted. 
 * All elements of an item are optional, however at least one of title or description must be present.
 * 
   */
  public List<Item> getItems() {
    return _items;
  }
  /**
  * A channel may contain any number of {@link Item}s. 
   * An item may represent a "story" -- much like a story in a newspaper or magazine; 
   * if so its description is a synopsis of the story, and the link points to the full story. 
   * An item may also be complete in itself, if so, the description contains the text 
   * (entity-encoded HTML is allowed; see examples), 
   * and the link and title may be omitted. 
   * All elements of an item are optional, however at least one of title or description must be present.
   * 
     */
  public Channel setItems(List<Item> items) {
    _items = items;
    return this;
  }
  /**
   * Adds an item to the {@link Channel}
   */
  public Channel additem(Item... item){
    if(!ArrayUtils.isEmpty(item)){
      if(_items == null){
        _items = new ArrayList<Item>();
      }
      _items.addAll(Arrays.asList(item));
    }
    
    return this;
  }
  
  /**
   * The name of the channel. It's how people refer to your service. If you have an HTML website that contains the same information as your RSS file, the title of your channel should be the same as the title of your website.
   */
  public String getTitle() {
    return _title;
  }
  /**
   * The name of the channel. It's how people refer to your service. If you have an HTML website that contains the same information as your RSS file, the title of your channel should be the same as the title of your website.
   */
  public Channel setTitle(String title) {
    _title = title;
    return this;
  }
  /**
   * The URL to the HTML website corresponding to the channel.
   */
  public URI getLink() {
    return _link;
  }
  /**
   * The URL to the HTML website corresponding to the channel.
   */
  public Channel setLink(URI link) {
    _link = link;
    return this;
  }
  
  /**
   * Convenient method for setting a link.
   * <br/>
   * @throws URISyntaxException if the <code>link</code> is not a valid URL  
   */
  public Channel setLink(String link) throws URISyntaxException{
    if(link == null){
      _link=null;
    }else{
      _link = new URI(link.trim());
    }
    return this;
  }
  /**
   * Phrase or sentence describing the channel.
   */
  public String getDescription() {
    return _description;
  }
  /**
   * Phrase or sentence describing the channel.
   */
  public Channel setDescription(String description) {
    _description = description;
    return this;
  }

  /**
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. 
   * <p>
   * Note: When the locale object is translated to the language elements 
   * in the RSS feed, it uses the ISO3166 country code by calling <code>locale.getCountry</code>
   * <br/>
   * if you want to use something other than that, use <code>setLanguageString</code> to put in 
   * and valid language string: 
   * {@link http://cyber.law.harvard.edu/rss/languages.html}
   * <br/>
   * {@link http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes}
   * <br/>
   * {@link http://cyber.law.harvard.edu/rss/rss.html}
   * </p>
   */
  public Locale getLanguage() {
    return _language;
  }

  /**
   * The language the channel is written in. This allows aggregators to group 
   * all Italian language sites, for example, on a single page. 
   * <p>
   * Note: When the locale object is translated to the language elements 
   * in the RSS feed, it uses the ISO3166 country code by calling <code>locale.getCountry</code>
   * <br/>
   * if you want to use something other than that, use <code>setLanguageString</code> to put in 
   * and valid language string: 
   * {@link http://cyber.law.harvard.edu/rss/languages.html}
   * <br/>
   * {@link http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes}
   * <br/>
   * {@link http://cyber.law.harvard.edu/rss/rss.html}
   * </p>
   */
  public Channel setLanguage(Locale language) {
    _language = language;
    return this;
  }

  /**
   * Copyright notice for content in the channel.
   */
  public String getCopyright() {
    return _copyright;
  }
  /**
   * Copyright notice for content in the channel.
   */
  public Channel setCopyright(String copyright) {
    _copyright = copyright;
    return this;
  }

  /**
   * Email address for person responsible for editorial content.
   * <br/>
   * This should be an valid email address, but nothing prevent you from putting in
   * alternative format such as 'blah AT blah DOT com'
   */
  public String getManagingEditor() {
    return _managingEditor;
  }
  /**
   * Email address for person responsible for editorial content.
   * <br/>
   * This should be an valid email address, but nothing prevent you from putting in
   * alternative format such as 'blah AT blah DOT com'
   */
  public Channel setManagingEditor(String email) {
    _managingEditor = email;
    return this;
  }
  /**
   * Email address for person responsible for technical issues relating to channel.
   * <br/>
   * This should be an valid email address, but nothing prevent you from putting in
   * alternative format such as 'blah AT blah DOT com'
   */
  public String getWebMaster() {
    return _webMaster;
  }
  /**
   * Email address for person responsible for technical issues relating to channel.
   * <br/>
   * This should be an valid email address, but nothing prevent you from putting in
   * alternative format such as 'blah AT blah DOT com'
   */
  public Channel setWebMaster(String webMaster) {
    _webMaster = webMaster;
    return this;
  }

  /**
   * The publication date for the content in the channel. 
   * For example, the New York Times publishes on a daily basis, 
   * the publication date flips once every 24 hours. 
   * That's when the pubDate of the channel changes. 
   * All date-times in RSS conform to the Date and Time Specification of RFC 822, 
   * with the exception that the year may be expressed with two characters or 
   * four characters (four preferred).
   */
  public Date getPubDate() {
    return _pubDate;
  }

  /**
   * The publication date for the content in the channel. 
   * For example, the New York Times publishes on a daily basis, 
   * the publication date flips once every 24 hours. 
   * That's when the pubDate of the channel changes. 
   * All date-times in RSS conform to the Date and Time Specification of RFC 822, 
   * with the exception that the year may be expressed with two characters or 
   * four characters (four preferred).
   */
  public Channel setPubDate(Date pubDate) {
    _pubDate = pubDate;
    return this;
  }
  
  public Channel setPubDate(String pubDate) throws ParseException {
    if(pubDate != null){
      SimpleDateFormat format = new SimpleDateFormat(Utils.DATE_FORMAT_PATTERN);
      _pubDate =  new Date(format.parse(pubDate).getTime());      
    }else{
      _pubDate = null;
    }
    return this;
  }
  /**
   * The last time the content of the channel changed.
   */
  public Date getLastBuildDate() {
    return _lastBuildDate;
  }
  /**
   * The last time the content of the channel changed.
   */
  public Channel setLastBuildDate(Date lastBuildDate) {
    _lastBuildDate = lastBuildDate;
    return this;
  }

  public Channel setLastBuildDate(String lastBuildDate) throws ParseException {
    if(lastBuildDate != null){
      SimpleDateFormat format = new SimpleDateFormat(Utils.DATE_FORMAT_PATTERN);
      _lastBuildDate =  new Date(format.parse(lastBuildDate).getTime());      
    }else{
      _lastBuildDate = null;
    }
    return this;
  }  
  /**
   * Other additional elements that are not in the Rss 2.0 specs.
   */
  public List<Element> getOtherElements() {
    return _otherElements;
  }
  /**
   * Other additional elements that are not in the Rss 2.0 specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   */
  public Channel setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add a element that is not specified in the Rss 2.0 specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public Channel addOtherElement(Element element){
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(element);
    return this;
  }
  
  /**
   * Add a element that is not specified in the Rss 2.0 specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * 
   * @param element - any element
   * @throws ParserConfigurationException 
   * @throws IOException 
   * @throws SAXException 
   */
  public Channel addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(XMLUtils.parseXml(xmlString, false, false).getDocumentElement());
    return this;
  }
  
  
  /**
   * Specify one or more categories that the channel belongs to. 
   * Follows the same rules as the <item>-level {@link http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt category} element. 
   * More info: {@link http://cyber.law.harvard.edu/rss/rss.html#syndic8} 
   */
  public Set<Category> getCategory() {
    return _category;
  }
  /**
   * Specify one or more categories that the channel belongs to. 
   * Follows the same rules as the <item>-level {@link http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt category} element. 
   * More info: {@link http://cyber.law.harvard.edu/rss/rss.html#syndic8} 
   */
  public Channel setCategory(Set<Category> category) {
    _category = category;
    return this;
  }

  /**
   * If you only want a category element with no domain attributes, you can simply use 
   * a string instead of building up a category element.
   * <br/>
   * note that this list will overrides the {@link Channel.getCategory()} list if it's set.
   */
  public List<String> getCategoryString() {
    if(_category == null){
      return null;
    }
    List<String> cat = new ArrayList<String>();
    for(Category c : _category){
      cat.add(c.getCategory());
    }
    return cat;
  }

  /**
   * If you only want a category element with no domain attributes, you can simply use 
   * a string instead of building up a category element.
   * <br/>
   * note that this list will suppress the {@link Channel.getCategory()} list if it's set.
   */
  public Channel setCategoryString(Set<String> categoryString) {
    if(categoryString == null){
      _category = null;
    }else{
      Set<Category> cat = new HashSet<Category>();
      for(String s : categoryString){
        cat.add(new Category(s));
      }
      _category = cat;
    }
    return this;
  }

  /**
   * Add a new category to the category list.
   */
  public Channel addCategory(String... category){
    if(!ArrayUtils.isEmpty(category)){
      for(String c : category){
        if(CollectionUtils.isEmpty(_category)){
          _category = new HashSet<Category>();
        }
        _category.add(new Category(c));
      }
    }
    
    return this;
  }

  /**
   * Add a new category to the category list.
   */
  public Channel addCategory(Category... category){
    if(!ArrayUtils.isEmpty(category)){
      if(_category == null){
        _category = new HashSet<Category>();
      }
      _category.addAll(Arrays.asList(category));
    }

    return this;
  }

  /**
   * A string indicating the program used to generate the channel.
   */
  public String getGenerator() {
    return _generator;
  }
  /**
   * A string indicating the program used to generate the channel.
   */
  public Channel setGenerator(String generator) {
    _generator = generator;
    return this;
  }
  /**
   * A URL that points to the documentation for the format used in the RSS file. 
   * It's probably a pointer to this page. 
   * It's for people who might stumble across an RSS file on a Web server 25 years 
   * from now and wonder what it is.
   */
  public URI getDocs() {
    return _docs;
  }
  /**
   * A URL that points to the documentation for the format used in the RSS file. 
   * It's probably a pointer to this page. 
   * It's for people who might stumble across an RSS file on a Web server 25 years 
   * from now and wonder what it is.
   */
  public Channel setDocs(URI docs) {
    _docs = docs;
    return this;
  }

  /**
   * Convenient method for setting a link.
   * <br/> 
   * @throws URISyntaxException if the <code>link</code> is not a valid URL
   */
  public Channel setDocs(String docs) throws MalformedURLException, URISyntaxException{
    if(docs == null){
      _docs = null;
    }else{
      setDocs(new URI(docs.trim()));
    }
    return this;
  }
  
  /**
   * ttl stands for time to live. It's a number of minutes that indicates how long a channel 
   * can be cached before refreshing from the source. More info here:
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#ltttlgtSubelementOfLtchannelgt
   */
  public Integer getTtl() {
    return _ttl;
  }

  /**
   * ttl stands for time to live. It's a number of minutes that indicates how long a channel 
   * can be cached before refreshing from the source. More info here:
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#ltttlgtSubelementOfLtchannelgt
   */
  public Channel setTtl(Integer ttl) {
    if(ttl == null){
      _ttl = null;
      return this;
    }
    if(ttl < 0){
      throw new IllegalArgumentException("ttl must be non-negative");
    }
    _ttl = ttl;
    return this;
  }
  /**
   * Allows processes to register with a cloud to be notified of updates to the channel, 
   * implementing a lightweight publish-subscribe protocol for RSS feeds. More info here:
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#ltcloudgtSubelementOfLtchannelgt
   */
  public Cloud getCloud() {
    return _cloud;
  }
  
  /**
   * Convenient method for setting cloud.
   */
  public Channel setCloud(String domain, int port, String path,
          String registerProcedure, String protocol){
    _cloud = new Cloud(domain, port, path, registerProcedure, protocol);
    return this;
  }
  
  /**
   * Allows processes to register with a cloud to be notified of updates to the channel, 
   * implementing a lightweight publish-subscribe protocol for RSS feeds. More info here:
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#ltcloudgtSubelementOfLtchannelgt
   */
  public Channel setCloud(Cloud cloud) {
    _cloud = cloud;
    return this;
  }
/**
 * Specifies a GIF, JPEG or PNG image that can be displayed with the channel. More info here.
 * <br/>
 * http://cyber.law.harvard.edu/rss/rss.html#ltimagegtSubelementOfLtchannelgt
 */
  public Image getImage() {
    return _image;
  }
  /**
   * Specifies a GIF, JPEG or PNG image that can be displayed with the channel. More info here.
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#ltimagegtSubelementOfLtchannelgt
   */
  public Channel setImage(Image image) {
    _image = image;
    return this;
  }
  /**
   * Convenient method for setting image.
   * @throws URISyntaxException 
   */  
  public Channel setImage(String url, String title, String link) throws URISyntaxException{
    _image = new Image(url, title, link);
    return this;
  }
  
  /**
   * Specifies a text input box that can be displayed with the channel. More info here.
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#lttextinputgtSubelementOfLtchannelgt
   */
  public TextInput getTexInput() {
    return _texInput;
  }
  /**
   * Specifies a text input box that can be displayed with the channel. More info here.
   * <br/>
   * http://cyber.law.harvard.edu/rss/rss.html#lttextinputgtSubelementOfLtchannelgt
   */
  public Channel setTexInput(TextInput texInput) {
    _texInput = texInput;
    return this;
  }

  /**
   * Convenient method for setting the textInput;
   */
  public Channel setTextInput(String title, String description, String name, URI link){
    _texInput = new TextInput(title, description, name, link);
    return this;
  }
  /**
   * 
   * An XML element that contains up to 24 <hour> sub-elements whose value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not read the channel on hours listed in the skipHours element.
   * <br/>
   * The hour beginning at midnight is hour zero.
   */
  public Set<Integer> getSkipHours() {
    return _skipHours;
  }
  /**
   * 
   * An XML element that contains up to 24 <hour> sub-elements whose value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not read the channel on hours listed in the skipHours element.
   * <br/>
   * The hour beginning at midnight is hour zero.
   */
  public Channel setSkipHours(Set<Integer> skipHours) {
    for(Integer i : skipHours){
      if(i == null || i.intValue() <0 || i.intValue() >23){
        throw new IllegalArgumentException("all skip hour must be a value that is a number between 0 and 23");
      }
    }
    _skipHours = skipHours;
    return this;
  }

  /**
   * Add a skip hour to the feed;
   * @param hour value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not read the channel on hours listed in the skipHours element.
   * <br/>
   * The hour beginning at midnight is hour zero.
   */
  public Channel addSkipHour(int... hour){
    if(!ArrayUtils.isEmpty(hour)){
      for(int h : hour){
        if(h <0 || h >23){
          throw new IllegalArgumentException("all skip hour must be a value that is a number between 0 and 23");
        }
        if(_skipHours == null){
          _skipHours = new HashSet<Integer>();
        }
        _skipHours.add(h);
      }
    }
    return this;
  }

  /**
   * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators may not read the channel during days listed in the skipDays element.
   */
  public Set<Day> getSkipDays() {
    return _skipDays;
  }
  /**
   * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators may not read the channel during days listed in the skipDays element.
   */
  public Channel setSkipDays(Set<Day> skipDays) {
    _skipDays = skipDays;
    return this;
  }

  /**
   * Add a skip day.
   */
  public Channel addSkipDay(Day... day){
    if(!ArrayUtils.isEmpty(day)){
      if(_skipDays == null){
        _skipDays = new HashSet<Day>();
      }
      _skipDays.addAll(Arrays.asList(day));
    }
    
    return this;
  }

  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    if(CollectionUtils.isEmpty(_items)){
      throw new ValidationException("Channel: You should have at least 1 item");
    }
    
    for(Item item : _items){
      Utils.validateNotNull("Channel: All item should not be null", item);
      item.validate(level);
    }
    
    
    Utils.validateNotNull("Channel: Title, Link and Description should not be null", _title, _link, _description);
  
    if(level == ValidationLevel.STRICT){
      Utils.validateEmails("Channel: Email addresses are invalid", _managingEditor, _webMaster);    
    }
    if(_category != null){
      for(Category c: _category){
        c.validate(level);
      }
    }
    if(_cloud != null){
      _cloud.validate(level);
    }
    if(_image != null){
      _image.validate(level);
    }
    if(_texInput != null){
      _texInput.validate(level);
    }    
  }
  
  private TRssChannel toChannel(){
    TRssChannel ret = new TRssChannel();
    List<Object> elementList = ret.getTitleOrLinkOrDescription();
    ObjectFactory factory = new ObjectFactory();
    if(_otherElements != null){
      ret.getAny().addAll(_otherElements);
    }
    if(_otherAttributes != null){
      ret.getOtherAttributes().putAll(_otherAttributes);
    }
    if(_category != null){
      for(Category c : _category){
        if(c != null){
          elementList.add(c.toTCategoryJAXB());
        }
      }
    }
    
    if(_cloud != null){
      elementList.add(_cloud.toTCloudJAXB());
    }
    if(_copyright != null){
      elementList.add(factory.createTRssChannelCopyright(_copyright));
    }
    if(_description != null){
      elementList.add(factory.createTRssChannelDescription(_description));
    }

    if(_docs != null){
      elementList.add(factory.createTRssChannelDocs(_docs.toString()));
    }
    
    if(_generator != null){
      elementList.add(factory.createTRssChannelGenerator(_generator));
    }

    if(_image != null){
      elementList.add(_image.toTImageJAXB());
    }
    
    if(_items != null){
      for(Item t : _items){
        if(t != null){
          ret.getItem().add(t.toTItemJAXB().getValue());
        }
      }
    }
    
    if(_language != null){
      elementList.add(factory.createTRssChannelLanguage(_language.getLanguage()));
    }
    if(_link != null){
      elementList.add(factory.createTRssChannelLink(_link.toString()));
    }
    SimpleDateFormat format = new SimpleDateFormat(Utils.DATE_FORMAT_PATTERN);
    if(_lastBuildDate != null){
      elementList.add(factory.createTRssChannelLastBuildDate(format.format(_lastBuildDate)));
    }
    
    if(_managingEditor != null){
      elementList.add(factory.createTRssChannelManagingEditor(_managingEditor));
    }
    
    if(_pubDate != null){
      elementList.add(factory.createTRssChannelPubDate(format.format(_pubDate)));
    }

    if(_skipDays != null){
      TSkipDaysList tdl = new TSkipDaysList();
      for(Day day : _skipDays){
        tdl.getDay().add(TSkipDay.fromValue(day.toString()));
      }
      elementList.add(new ObjectFactory().createSkipDays( tdl));
    }

    if(_skipHours != null){
      TSkipHoursList thl = new TSkipHoursList();
      thl.getHour().addAll(_skipHours);
      elementList.add(new ObjectFactory().createSkipHours( thl));
    }
    
    if(_texInput != null){
      elementList.add( _texInput.toTTextInputJAXB());      
    }

    if(_title != null){
      elementList.add(factory.createTRssChannelTitle(_title));
    }

    if(_ttl != null){
      elementList.add(factory.createTRssChannelTtl(new BigInteger(String.valueOf(_ttl))));
    }

    if(_webMaster != null){
      elementList.add(factory.createTRssChannelWebMaster(_webMaster));
    }
    
    return ret;
  }

  public JAXBElement<TRssChannel> toTChannelJAXB(){
    return new ObjectFactory().createChannel(toChannel());
  }
}