package yarfraw.core.datamodel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.XMLUtils;

/**
 * 
 * A channel may contain any number of {@link ItemEntry}s. 
 * An item may represent a "story" -- much like a story in a newspaper or magazine; 
 * if so its description is a synopsis of the story, and the link points to the full story. 
 * An item may also be complete in itself, if so, the description contains the text 
 * (entity-encoded HTML is allowed; see examples), 
 * and the link and title may be omitted. 
 * All elements of an item are optional, however at least one of title or description must be present.
 * <p/>
 * for Atom 1.0 format, the field also mapps to &lt;entry> element. 
 * <br/>
 * @author jliang
 *
 */
public class ItemEntry extends AbstractBaseObject{
  private static final long serialVersionUID = 20070927L;
  private static final Log LOG = LogFactory.getLog(ItemEntry.class);
  private Text _title;
  private List<Link> _links;
  private Text _descriptionOrSummary;
  private List<Person> _authorOrCreator;
  private List<Person> _contributors;
  private Set<CategorySubject> _categorySubjects;
  private String _comments;
  private Enclosure _enclosure;
  private Id _uid;
  private String _pubDate;
  private String _updatedDate;
  private Source _source;
  private Text _rights;
  private Content _content;
  
  public ItemEntry(){}
  
  /**
   * <li>Rss 1.0 - The item's title.
   * </li>
   * <li>Rss 2.0 - The title of the item.
   * </li>
   * <li>Atom 1.0 -
   * The "atom:title" element is a Text construct that conveys a human-readable title for an entry or feed.
   * </li>
   * @return
   */
  public Text getTitle() {
    return _title;
  }

  /**
   * <li>Rss 1.0 - The item's title.
   * </li>
   * <li>Rss 2.0 - The title of the item.
   * </li>
   * <li>Atom 1.0 -
   * The "atom:title" element is a Text construct that conveys a human-readable title for an entry or feed.
   * </li>
   * @param title
   * @return
   */
  public ItemEntry setTitle(Text title) {
    _title = title;
    return this;
  }
  
  /**
   * <li>Rss 1.0 - The item's title.
   * </li>
   * <li>Rss 2.0 - The title of the item.
   * </li>
   * <li>Atom 1.0 -
   * The "atom:title" element is a Text construct that conveys a human-readable title for an entry or feed.
   * </li>
   * <br/>
   * This method creates a new {@link Text} object using the input string and sets title to this object. 
   * @param title 
   * @return
   */
  public ItemEntry setTitle(String title) {
    return setTitle(new Text(title));
  }

  /**
   * <li>Rss 1.0 - The item's title.
   * </li>
   * <li>Rss 2.0 - The title of the item.
   * </li>
   * <li>Atom 1.0 -
   * The "atom:title" element is a Text construct that conveys a human-readable title for an entry or feed.
   * </li>
   * <br/>
   * Gets the text content of the title element.
   * @return
   */
  public String getTitleText(){
    return _title == null?null:_title.getText();
  }
  
  /**
   * <li>Rss 1.0 - The item's URL.
   * </li>
   * <li>Rss 2.0 - The URL of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:link" element defines a reference from an entry or 
   * feed to a Web resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * <b>Only Atom 1.0 supports multiple link elements. Both Rss 1.0 and Rss 2.0 will only use the FIRST link in the list
   * as the link element</b>
   * @return
   */
  public List<Link> getLinks() {
    return _links;
  }
  /**
   * <li>Rss 1.0 - The item's URL.
   * </li>
   * <li>Rss 2.0 - The URL of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:link" element defines a reference from an entry or 
   * feed to a Web resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * <b>Only Atom 1.0 supports multiple link elements. Both Rss 1.0 and Rss 2.0 will only use the FIRST link in the list
   * as the link element</b>
   * @param links
   * @return
   */
  public ItemEntry setLinks(List<Link> links) {
    _links = links;
    return this;
  }
  
  /**
   * <li>Rss 1.0 - The item's URL.
   * </li>
   * <li>Rss 2.0 - The URL of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:link" element defines a reference from an entry or 
   * feed to a Web resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * This method creates new {@link Link} objects and adds them to the END of the links list. 
   * <br/>
   * <b>Only Atom 1.0 supports multiple link elements. Both Rss 1.0 and Rss 2.0 will only use the FIRST link in the list
   * as the link element</b>
   * @param href
   * @return
   */
  public ItemEntry addLink(String... href) {
    if(ArrayUtils.isEmpty(href)){
      LOG.warn("Empty href array is ignored");
      return this;
    }
    if(_links == null){
      _links = new ArrayList<Link>();
    }
    for (String s:href){
      _links.add(new Link(s));
    }
    return this;
  }

  /**
   * <li>Rss 1.0 - The item's URL.
   * </li>
   * <li>Rss 2.0 - The URL of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:link" element defines a reference from an entry or 
   * feed to a Web resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * This method adds input {@link Link} to the END of the links list. 
   * <br/>
   * <b>Only Atom 1.0 supports multiple link elements. Both Rss 1.0 and Rss 2.0 will only use the FIRST link in the list
   * as the link element</b>
   * @param link
   * @return
   */
  public ItemEntry addLink(Link... link) {
    if(ArrayUtils.isEmpty(link)){
      LOG.warn("Empty link array is ignored");
      return this;
    }
    if(_links == null){
      _links = new ArrayList<Link>();
    }
    for(Link l : link){
      _links.add(l);
    }
    return this;
  }
  /**
   * <li>Rss 1.0 - A brief description/abstract of the item.
   * </li>
   * <li>Rss 2.0 -  The item synopsis.
   * </li>
   * <li>Atom 1.0 - The "atom:summary" element is a Text construct that conveys a short summary, abstract, or excerpt of an entry.
   * </li>
   * 
   * <br/>
   * Note: for Rss 1.0, this is mapped to the &lt;description> element, not the &lt;dc:description> element.
   * @return
   */
  public Text getDescriptionOrSummary() {
    return _descriptionOrSummary;
  }

  /**
   * <li>Rss 1.0 - A brief description/abstract of the item.
   * </li>
   * <li>Rss 2.0 -  The item synopsis.
   * </li>
   * <li>Atom 1.0 - The "atom:summary" element is a Text construct that conveys a short summary, abstract, or excerpt of an entry.
   * </li>
   * 
   * @param descriptionOrSummary
   * @return
   */
  public ItemEntry setDescriptionOrSummary(Text descriptionOrSummary) {
    _descriptionOrSummary = descriptionOrSummary;
    return this;
  }
  
  /**
   * <li>Rss 1.0 - A brief description/abstract of the item.
   * </li>
   * <li>Rss 2.0 -  The item synopsis.
   * </li>
   * <li>Atom 1.0 - The "atom:summary" element is a Text construct that conveys a short summary, abstract, or excerpt of an entry.
   * </li>
   * 
   * <br/>
   * This method creates a new {@link Text} object of type 'text' with the input string as its text content.
   * @param descriptionOrSummary
   * @return
   */
  public ItemEntry setDescriptionOrSummary(String descriptionOrSummary) {
    if(descriptionOrSummary == null){
      _descriptionOrSummary = null;
      return this;
    }
    return setDescriptionOrSummary(new Text(descriptionOrSummary));
  }
  
  /**
   * <li>Rss 1.0 - A brief description/abstract of the item.
   * </li>
   * <li>Rss 2.0 -  The item synopsis.
   * </li>
   * <li>Atom 1.0 - The "atom:summary" element is a Text construct that conveys a short summary, abstract, or excerpt of an entry.
   * </li>
   * 
   * <br/>
   * This methods gets the text content of the <code>descriptionOrSummary</code> field on the current instance.
   * @return
   */
  public String getDescriptionOrSummaryText() {
    return _descriptionOrSummary == null?null:_descriptionOrSummary.getText();
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
               the resource.
   * </li>
   * <li>Rss 2.0 - Email address of the author of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * Note: only Rss 1.0 and Rss 2.0 only uses the email field of a {@link Person} object, all the other fields are ignored.
   * 
   * @return
   */
  public List<Person> getAuthorOrCreator() {
    return _authorOrCreator;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
               the resource.
   * </li>
   * <li>Rss 2.0 - Email address of the author of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * Note: only Rss 1.0 and Rss 2.0 only uses the email field of a {@link Person} object, all the other fields are ignored.
   * @param author
   * @return
   */
  public ItemEntry setAuthorOrCreator(List<Person> authorOrCreator) {
    _authorOrCreator = authorOrCreator;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
               the resource.
   * </li>
   * <li>Rss 2.0 - Email address of the author of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * This method constructs a new {@link Person} object using only the email address and 
   * adds them to the END of the <code>authorOrCreator</code> list.
   * <br/>
   * Note: only Rss 1.0 and Rss 2.0 only uses the email field of a {@link Person} object, all the other fields are ignored.
   * @param email
   * @return
   */
  public ItemEntry addAuthorOrCreator(String... email) {
    if(ArrayUtils.isEmpty(email)){
      LOG.warn("Empty email array is ignored");
      return this;
    }
    
    if(_authorOrCreator == null){
      _authorOrCreator = new ArrayList<Person>();
    }
    for(String e : email){
      _authorOrCreator.add(new Person(e));      
    }
    return this;
  }
  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
               the resource.
   * </li>
   * <li>Rss 2.0 - Email address of the author of the item.
   * </li>
   * <li>Atom 1.0 - The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * This method constructs a new {@link Person} object using only the email address and 
   * adds them to the END of the <code>authorOrCreator</code> list.
   * <br/>
   * Note: only Rss 1.0 and Rss 2.0 only uses the email field of a {@link Person} object, all the other fields are ignored.
   * @param authorOrCreator
   * @return
   */
  public ItemEntry addAuthorOrCreator(Person... authorOrCreator) {
    if(ArrayUtils.isEmpty(authorOrCreator)){
      LOG.warn("Empty author array is ignored");
      return this;
    }
    
    if(_authorOrCreator == null){
      _authorOrCreator = new ArrayList<Person>();
    }
    for(Person p : authorOrCreator){
      _authorOrCreator.add(p);
    }
    return this;
  }
  
  
  /**
   * <li>Rss 1.0 - &lt;dc:contributor> An entity responsible for making contributions to the content of the resource.
   * <br/><b>When this element is map to contributor in Rss 1.0, only the email address is used, other fields are ignored.</b>
   * </li>
   * <li>Rss 2.0 - Not supported, this list is ignored.
   * </li>
   * <li>Atom 1.0 - The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * @return
   */
  public List<Person> getContributors() {
    return _contributors;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:contributor> An entity responsible for making contributions to the content of the resource.
   * <br/><b>When this element is map to contributor in Rss 1.0, only the email address is used, other fields are ignored.</b>
   * </li>
   * <li>Rss 2.0 - Not supported, this list is ignored.
   * </li>
   * <li>Atom 1.0 - The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * @param contributors
   * @return
   */
  public ItemEntry setContributors(List<Person> contributors) {
    _contributors = contributors;
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:contributor> An entity responsible for making contributions to the content of the resource.
   * <br/><b>When this element is map to contributor in Rss 1.0, only the email address is used, other fields are ignored.</b>
   * </li>
   * <li>Rss 2.0 - Not supported, this list is ignored.
   * </li>
   * <li>Atom 1.0 - The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * 
   * <br/>
   * This method adds the input contributor to the END of the contributors list.
   * @param contributor
   * @return
   */
  public ItemEntry addContributor(Person... contributor){
    if(ArrayUtils.isEmpty(contributor)){
      LOG.warn("Empty contributor array is ignored");
      return this;
    }
    
    if(_contributors == null){
      _contributors = new ArrayList<Person>();
    }
    for(Person p : contributor){
      _contributors.add(p);
    }
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:contributor> An entity responsible for making contributions to the content of the resource.
   * <br/><b>When this element is map to contributor in Rss 1.0, only the email address is used, other fields are ignored.</b>
   * </li>
   * <li>Rss 2.0 - Not supported, this list is ignored.
   * </li>
   * <li>Atom 1.0 - The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * 
   * <br/>
   * This method adds the input contributor to the END of the contributors list.
   * @param contributor
   * @return
   */
  public ItemEntry addContributor(String... contributor){
    if(ArrayUtils.isEmpty(contributor)){
      LOG.warn("Empty contributor array is ignored");
      return this;
    }
    
    if(_contributors == null){
      _contributors = new ArrayList<Person>();
    }
    for(String c : contributor){
      _contributors.add(new Person(c));      
    }
    return this;
  }
  /**
   * <li>Rss 1.0 - &lt;dc:subject> The topic of the content of the resource.
   * </li>
   * <li>Rss 2.0 - Includes the item in one or more categories.
   * </li>
   * <li>Atom 1.0 - The "atom:category" element conveys information about a category 
   * associated with an entry or feed. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * @return
   */
  public Set<CategorySubject> getCategorySubjects() {
    return _categorySubjects;
  }


  /**
   * <li>Rss 1.0 - &lt;dc:subject> The topic of the content of the resource.
   * </li>
   * <li>Rss 2.0 - Includes the item in one or more categories.
   * </li>
   * <li>Atom 1.0 - The "atom:category" element conveys information about a category 
   * associated with an entry or feed. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * @param categorySubjects
   * @return
   */
  public ItemEntry setCategorySubjects(Set<CategorySubject> categorySubjects) {
    _categorySubjects = categorySubjects;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:subject> The topic of the content of the resource.
   * </li>
   * <li>Rss 2.0 - Includes the item in one or more categories.
   * </li>
   * <li>Atom 1.0 - The "atom:category" element conveys information about a category 
   * associated with an entry or feed. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * This method creates new {@link CategorySubject} objects using the input strings and add them to the category set.
   * @param categorySubjectOrTerm
   * @return
   */
  public ItemEntry addCategorySubject(String... categorySubjectOrTerm) {
    if(ArrayUtils.isEmpty(categorySubjectOrTerm)){
      LOG.warn("Empty category array is ignored");
      return this;
    }
    if(_categorySubjects == null){
      _categorySubjects = new HashSet<CategorySubject>();
    }
    for(String c : categorySubjectOrTerm){
      _categorySubjects.add(new CategorySubject(c));
    }
    
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:subject> The topic of the content of the resource.
   * </li>
   * <li>Rss 2.0 - Includes the item in one or more categories.
   * </li>
   * <li>Atom 1.0 - The "atom:category" element conveys information about a category 
   * associated with an entry or feed. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * This method adds the input {@link CategorySubject} objects to the category set.
   * @param categorySubject
   * @return
   */
  public ItemEntry addCategorySubject(CategorySubject... categorySubject) {
    if(ArrayUtils.isEmpty(categorySubject)){
      LOG.warn("Empty category array is ignored");
      return this;
    }
    
    if(_categorySubjects == null){
      _categorySubjects = new HashSet<CategorySubject>();
    }
    for(CategorySubject s : categorySubject){
      _categorySubjects.add(s);
    }
    return this;
  }
  
  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - URL of a page for comments relating to the item.
   * </li>
   * <li>Atom 1.0 - Not supported, this is field is ignored.
   * </li>
   * @return
   */
  public String getComments() {
    return _comments;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - URL of a page for comments relating to the item.
   * </li>
   * <li>Atom 1.0 - Not supported, this is field is ignored.
   * </li>
   * @param comments
   * @return
   */
  public ItemEntry setComments(String comments) {
    _comments = comments;
    return this;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Describes a media object that is attached to the item.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * @return
   */
  public Enclosure getEnclosure() {
    return _enclosure;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Describes a media object that is attached to the item.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * @param enclosure
   * @return
   */
  public ItemEntry setEnclosure(Enclosure enclosure) {
    _enclosure = enclosure;
    return this;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;guid> A string that uniquely identifies the item.
   * </li>
   * <li>Atom 1.0 - The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
   * </li>
   * @return
   */
  public Id getUid() {
    return _uid;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;guid> A string that uniquely identifies the item.
   * </li>
   * <li>Atom 1.0 - The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
   * </li>
   * @param uid
   * @return
   */
  public ItemEntry setUid(Id uid) {
    _uid = uid;
    return this;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;guid> A string that uniquely identifies the item.
   * </li>
   * <li>Atom 1.0 - The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
   * </li>
   * <br/>
   * This method constructs a new {@link Id} object and sets the <code>uid</code> field to this object.
   * @param uid
   * @return
   */
  public ItemEntry setUid(String uid){
    if(_uid == null){
      _uid = null;
      return this;
    }
    return setUid(new Id(uid));
  }


  /**
   * <li>Rss 1.0 - &lt;dc:date>  A date associated with an event in the life cycle of the resource.
   * </li>
   * <li>Rss 2.0 - &lt;pubDate> Indicates when the item was published. 
   * </li>
   * <li>Atom 1.0 - &lt;published> The "atom:published" element is a Date construct indicating an instant in time 
   * associated with an event early in the life cycle of the entry.
   * </li>
   * @return
   */
  public String getPubDate() {
    return _pubDate;
  }


  /**
   * <li>Rss 1.0 - &lt;dc:date>  A date associated with an event in the life cycle of the resource.
   * </li>
   * <li>Rss 2.0 - &lt;pubDate> Indicates when the item was published. 
   * </li>
   * <li>Atom 1.0 - &lt;published> The "atom:published" element is a Date construct indicating an instant in time 
   * associated with an event early in the life cycle of the entry.
   * </li>
   * @param pubDate
   * @return
   */
  public ItemEntry setPubDate(String pubDate) {
    _pubDate = pubDate;
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:date>  A date associated with an event in the life cycle of the resource.
   * </li>
   * <li>Rss 2.0 - &lt;pubDate> Indicates when the item was published. 
   * </li>
   * <li>Atom 1.0 - &lt;published> The "atom:published" element is a Date construct indicating an instant in time 
   * associated with an event early in the life cycle of the entry.
   * </li>
   * 
   * <br/>
   * This method uses the input {@link SimpleDateFormat} object to format the date into a string.
   * @param pubDate
   * @param format
   * @return
   */
  public ItemEntry setPubDate(Date pubDate, SimpleDateFormat format){
    return setPubDate(format.format(pubDate));
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;updated> The "atom:updated" element is a Date construct indicating the most recent 
   * instant in time when an entry or feed was modified in a way the publisher considers significant. 
   * Therefore, not all modifications necessarily result in a changed atom:updated value.
   * </li>
   * @return
   */
  public String getUpdatedDate() {
    return _updatedDate;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;updated> The "atom:updated" element is a Date construct indicating the most recent 
   * instant in time when an entry or feed was modified in a way the publisher considers significant. 
   * Therefore, not all modifications necessarily result in a changed atom:updated value.
   * </li>
   * @param updatedDate
   * @return
   */
  public ItemEntry setUpdatedDate(String updatedDate) {
    _updatedDate = updatedDate;
    return this;
  }


  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;updated> The "atom:updated" element is a Date construct indicating the most recent 
   * instant in time when an entry or feed was modified in a way the publisher considers significant. 
   * Therefore, not all modifications necessarily result in a changed atom:updated value.
   * </li>
   * 
   * <br/>
   * This method uses the input {@link SimpleDateFormat} object to format the date into a string.
   * @param date
   * @param format
   * @return
   */
  public ItemEntry setUpdatedDate(Date date, SimpleDateFormat format) {
    return setUpdatedDate(format.format(date));
  }


  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - The RSS channel that the item came from.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * @return
   */
  public Source getSource() {
    return _source;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;source> The RSS channel that the item came from.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * @param source
   * @return
   */
  public ItemEntry setSource(Source source) {
    _source = source;
    return this;
  }


  /**
   * <li>Rss 1.0 - &lt;dc:rights> Information about rights held in and over the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;rights> The "atom:rights" element is a Text construct that conveys information about rights held in and over an entry or feed.
   * </li>
   * @return
   */
  public Text getRights() {
    return _rights;
  }
  /**
   * <li>Rss 1.0 - &lt;dc:rights> Information about rights held in and over the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;rights> The "atom:rights" element is a Text construct that conveys information about rights held in and over an entry or feed.
   * </li>
   * 
   * @return the text content of the <code>rights</code> field.
   */
  public String getRightsText() {
    return _rights == null ? null:_rights.getText();
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:rights> Information about rights held in and over the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;rights> The "atom:rights" element is a Text construct that conveys information about rights held in and over an entry or feed.
   * </li>
   * @param rights
   * @return
   */
  public ItemEntry setRights(Text rights) {
    _rights = rights;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:rights> Information about rights held in and over the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;rights> The "atom:rights" element is a Text construct that conveys information about rights held in and over an entry or feed.
   * </li>
   * <br/>
   * This method creates a new {@link Text} object using the input string as its content and sets <code>rights</code> field to this object.
   * @param rights
   * @return
   */
  public ItemEntry setRights(String rights) {
    if(rights == null){
      _rights = null;
      return this;
    }
    return setRights(new Text(rights));
  }
  
  /**
   * <li>Rss 2.0 -
   * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
   * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
   * </li>
   * <li>Rss 1.0 -
   * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
   * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
   * </li>
   * <li>Atom 1.0 - &lt;content> 
   * The "atom:content" element either contains or links to the content of the entry. The content of atom:content is Language-Sensitive.
   * </li>
   * @return
   */
  public Content getContent() {
    return _content;
  }

  /**
   * <li>Rss 2.0 -
   * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
   * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
   * </li>
   * <li>Rss 1.0 -
   * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
   * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
   * </li>
   * <li>Atom 1.0 - &lt;content> 
   * The "atom:content" element either contains or links to the content of the entry. The content of atom:content is Language-Sensitive.
   * </li>
   * @param content
   * @return
   */
  public ItemEntry setContent(Content content) {
    _content = content;
    return this;
  }


  /**
   * <li>Rss 2.0 -
   * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
   * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
   * </li>
   * <li>Rss 1.0 -
   * This is not officially supported, but if there's a &lt;content:encoded /> element under &lt;Item>,
   * the content of the encoded element will be mapped to this class. The type will always be 'text' in this case.
   * </li>
   * <li>Atom 1.0 - &lt;content> 
   * The "atom:content" element either contains or links to the content of the entry. The content of atom:content is Language-Sensitive.
   * </li>
   * <br/>
   * This method constructs a new {@link Content} object of type 'text' and uses the input string as its text content.
   * @param contentText
   * @return
   */
  public ItemEntry setContent(String contentText) {
    if(_content == null){
      _content = null;
      return this;
    }
    setContent(new Content(contentText));
    return this;
  }

  ////////////////////////Common setters///////////////////////
  /**
   * Any other attribute that is not in the RSS 2.0 specs.
   */
  public ItemEntry setOtherAttributes(Map<QName, String> otherAttributes) {
    _otherAttributes = otherAttributes;
    return this;
  }
  /**
   * Add an attribute that is not in the RSS 2.0 specs.
   */
  public ItemEntry addOtherAttributes(QName namespace, String attribute) {
    if(_otherAttributes == null){
      _otherAttributes = new HashMap<QName, String>();
    }
    _otherAttributes.put(namespace, attribute);
    return this;
  }
  
  /**
   * Other additional elements that are not in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   */
  public ItemEntry setOtherElements(List<Element> otherElements) {
    _otherElements = otherElements;
    return this;
  }
  /**
   * Add an element that is not specified in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * @param element - any element
   */
  public ItemEntry addOtherElement(Element element){
    if(_otherElements == null){
      _otherElements = new ArrayList<Element>();
    }
    _otherElements.add(element);
    return this;
  }
  
  /**
   * Add an element that is not specified in the Rss specs.<br/>
   * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
   * 
   * @param xmlString - any element
   * @throws ParserConfigurationException 
   * @throws IOException 
   * @throws SAXException 
   */
  public ItemEntry addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
    return addOtherElement(XMLUtils.parseXml(xmlString, false, false).getDocumentElement());
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
  public ItemEntry setBase(String base) {
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
  public ItemEntry setLang(String lang) {
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
  public ItemEntry setLang(Locale lang) {
    _lang = lang.getLanguage();
    return this;
  }
  /**
   * <b>Rss 1.0 only</b><br/>
   * @param resource
   * @return
   */
  public ItemEntry setResource(String resource) {
    _resource = resource;
    return this;
  }
  /**
   * <b>Rss 1.0 only</b><br/>
   * @param about
   * @return
   */
  public ItemEntry setAbout(String about) {
    _about = about;
    return this;
  }
  ////////////////////////Common setters///////////////////////
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    
    if(_title == null && _descriptionOrSummary == null){
      throw new ValidationException("Item: At least one of title or description must be present.");
    }
    if(_categorySubjects != null){
      for(CategorySubject c: _categorySubjects){
        c.validate(format);
      }
    }
    if(_enclosure != null){
      _enclosure.validate(format);
    }
    if(_uid != null){
      _uid.validate(format);
    }
    if(_source != null){
      _source.validate(format);
    }
    
    if(_links != null){
      for(Link link : _links){
        if(link != null){
          link.validate(format);
        }
      }
    }
    
  }

}