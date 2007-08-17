package yarfraw.core.datamodel;

import java.io.IOException;
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
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.rss20.utils.Utils;
import yarfraw.rss20.utils.XMLUtils;

/**
 * A channel may contain any number of {@link Item}s. 
 * An item may represent a "story" -- much like a story in a newspaper or magazine; 
 * if so its description is a synopsis of the story, and the link points to the full story. 
 * An item may also be complete in itself, if so, the description contains the text 
 * (entity-encoded HTML is allowed; see examples), 
 * and the link and title may be omitted. 
 * All elements of an item are optional, however at least one of title or description must be present.
 * 
 * @author jliang
 *
 */
public class Item extends AbstractBaseObject{
  private String _title;
  private URI _link;
  private String _description;
  private String _author;
  private Set<Category> _category;
  private URI _comments;
  private Enclosure _enclosure;
  private Guid _guid;
  private Date _pubDate;
  private Source _source;
  private RdfAttributes _rdfAttributes;
  private List<Element> _otherElements = new ArrayList<Element>();
  private Map<QName, String> _otherAttributes = new HashMap<QName, String>();
  
  public Item(){}
  public static Item create(){
    return new Item();
  }
  public Item(String title, URI link, String description, String author,
      Set<Category> category, URI comments, Enclosure enclosure, Guid guid,
      Date pubDate, Source source) {
    super();
    _title = title;
    _link = link;
    _description = description;
    _author = author;
    _category = category;
    _comments = comments;
    _enclosure = enclosure;
    _guid = guid;
    _pubDate = pubDate;
    _source = source;
  }
  
  public Item(String title, String link, String description, String author,
      Set<Category> category, String comments, Enclosure enclosure, Guid guid,
      Date pubDate, Source source) throws URISyntaxException {
    super();
    _title = title;
    setLink(link);
    _description = description;
    _author = author;
    _category = category;
    setComments(comments);
    _enclosure = enclosure;
    _guid = guid;
    _pubDate = pubDate;
    _source = source;
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
  public Item setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public Item addOtherAttributes(QName namespace, String attribute) {
    if(_otherAttributes == null){
      _otherAttributes = new HashMap<QName, String>();
    }
    _otherAttributes.put(namespace, attribute);
    return this;
  }
    
  
/**
 * The title of the item.
 */
  public String getTitle() {
    return _title;
  }
  /**
   * The title of the item.
   */  
  public Item setTitle(String title) {
    _title = title;
    return this;
  }
  /**
   * The URI of the item.
   */
  public URI getLink() {
    return _link;
  }
  /**
   * The URI of the item.
   */  
  public Item setLink(URI link) {
    _link = link;
    return this;
  }
  
  /**
   * The URI of the item.
   * @throws URISyntaxException 
   * if link is an invalid url 
   */  
  public Item setLink(String link) throws URISyntaxException {
    if(link == null){
      _link = null;
    }else{
      _link = new URI(link.trim());
    }
    return this;
  }
  
  /**
   *  The item synopsis.
   * 
   */
  public String getDescription() {
    return _description;
  }
  /**
   *  The item synopsis.
   * 
   */
  public Item setDescription(String description) {
    _description = description;
    return this;
  }
  /**
   * Email address of the author of the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt More}
   * 
   */
  public String getAuthor() {
    return _author;
  }
  /**
   * Email address of the author of the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt More}
   * 
   */
  public Item setAuthor(String author) {
    _author = author;
    return this;
  }
  /**
   * Includes the item in one or more categories. {@link http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt More}
   */
  public Set<Category> getCategory() {
    return _category;
  }
  /**
   * Includes the item in one or more categories. {@link http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt More}
   */
  public Item setCategory(Set<Category> category) {
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
  public Item setCategoryString(Set<String> categoryString) {
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
  public Item addCategory(String... category){
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
  public Item addCategory(Category... category){
    if(!ArrayUtils.isEmpty(category)){
      if(CollectionUtils.isEmpty(_category)){
        _category = new HashSet<Category>();
      }
      _category.addAll(Arrays.asList(category));
    }
    
    return this;
  }

  /**
   * URI of a page for comments relating to the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt More}
   */
  public URI getComments() {
    return _comments;
  }
  /**
   * URI of a page for comments relating to the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt More}
   */
  public Item setComments(URI comments) {
    _comments = comments;
    return this;
  }
  
  /**
   * URI of a page for comments relating to the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt More}
   * @throws URISyntaxException 
   * if <code>comments</code> is an invalid url 
   */
  public Item setComments(String comments) throws URISyntaxException {
    if(comments == null){
      _comments = null;
    }else{
      _comments = new URI(comments.trim());
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
  public Item setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add a element that is not specified in the Rss 2.0 specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public Item addOtherElement(Element element){
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
  public Item addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(XMLUtils.parseXml(xmlString, false, false).getDocumentElement());
    return this;
  }  
  
  /**
   * Describes a media object that is attached to the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt More}
   */
  public Enclosure getEnclosure() {
    return _enclosure;
  }
  /**
   * Describes a media object that is attached to the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltenclosuregtSubelementOfLtitemgt More}
   */
  public Item setEnclosure(Enclosure enclosure) {
    _enclosure = enclosure;
    return this;
  }
  /**
   * A string that uniquely identifies the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltguidgtSubelementOfLtitemgt More}
   */
  public Guid getGuid() {
    return _guid;
  }
  /**
   * A string that uniquely identifies the item. {@link http://cyber.law.harvard.edu/rss/rss.html#ltguidgtSubelementOfLtitemgt More}
   */
  public Item setGuid(Guid guid) {
    _guid = guid;
    return this;
  }
  /**
   * Indicates when the item was published. {@link http://cyber.law.harvard.edu/rss/rss.html#ltpubdategtSubelementOfLtitemgt More}
   */
  public Date getPubDate() {
    return _pubDate;
  }
  /**
   * Indicates when the item was published. {@link http://cyber.law.harvard.edu/rss/rss.html#ltpubdategtSubelementOfLtitemgt More}
   */
  public Item setPubDate(Date pubDate) {
    _pubDate = pubDate;
    return this;
  }
  public Item setPubDate(String pubDate) throws ParseException {
    if(pubDate != null){
      SimpleDateFormat format = new SimpleDateFormat(Utils.DATE_FORMAT_PATTERN);
      _pubDate =  new Date(format.parse(pubDate).getTime());      
    }else{
      _pubDate = null;
    }
    return this;
  }
  /**
   *  The RSS channel that the item came from. {@link http://cyber.law.harvard.edu/rss/rss.html#ltsourcegtSubelementOfLtitemgt More}
   */
  public Source getSource() {
    return _source;
  }
  /**
   *  The RSS channel that the item came from. {@link http://cyber.law.harvard.edu/rss/rss.html#ltsourcegtSubelementOfLtitemgt More}
   */
  public Item setSource(Source source) {
    _source = source;
    return this;
  }
  
  /**
   * Attributes that is only supported by RSS 1.0/RDF format
   * @return
   */
  public RdfAttributes getRdfAttributes() {
    return _rdfAttributes;
  }
  /**
   * Attributes that is only supported by RSS 1.0/RDF format
   * @return
   */
  public void setRdfAttributes(RdfAttributes rdfAttributes) {
    _rdfAttributes = rdfAttributes;
  }
  
  
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    
    if(_title == null && _description == null){
      throw new ValidationException("Item: At least one of title or description must be present.");
    }
    if(_category != null){
      for(Category c: _category){
        c.validate(level);
      }
    }
    if(_enclosure != null){
      _enclosure.validate(level);
    }
    if(_guid != null){
      _guid.validate(level);
    }
    if(_source != null){
      _source.validate(level);
    }
    
    if(level == ValidationLevel.STRICT){
      Utils.validateEmails("Item: Author email is invalid", _author);
    }
  }

}