package yarfraw.core.datamodel;

import java.io.IOException;
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

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import yarfraw.utils.ValidationUtils;
import yarfraw.utils.XMLUtils;
/**
 * 
 * The name of the channel. It's how people refer to your service. 
 * If you have an HTML website that contains the same information as your RSS file, 
 * the title of your channel should be the same as the title of your website.
 * <p/>
 * for Atom 1.0 format, the field also maps to &lt;feed> element. 
 * <br/>
 * 
 * <li>Rss 1.0 - &lt;channel> and second level elements  such as &lt;item> elements under &lt;rdf:RDF>
 * </li>
 * <li>Rss 2.0 - &lt;channel>
 * </li>
 * <li>Atom 1.0 - &lt;feed> The "atom:feed" element is the document (i.e., top-level) element of an Atom Feed Document, 
 * acting as a container for metadata and data associated with the feed. Its element children consist of metadata 
 * elements followed by zero or more atom:entry child elements.
 * </li>
 * @author jliang
 *
 */
public class ChannelFeed extends AbstractBaseObject{
  /**
   * 
   */
  private static final long serialVersionUID = 20070927L;
  private static final Log LOG = LogFactory.getLog(ChannelFeed.class);
  private List<ItemEntry> _items;
  private Text _title;
  private List<Link> _links;
  private Text _descriptionOrSubtitle;
  private Text _rights;
  private List<Person> _managingEditorOrAuthorOrPublisher;
  private List<Person> _webMasterOrCreator;
  private List<Person> _contributors;
  private Set<CategorySubject> _categorySubjects;
  private String _pubDate;
  private String _lastBuildOrUpdatedDate;
  private Id _uid;
  private Generator _generator;
  private String _docs;
  private Integer _ttl;
  private Cloud _cloud;
  private Image _imageOrIcon;
  private Image _logo;
  private TextInput _texInput;
  private Set<Integer> _skipHours;
  private Set<Day> _skipDays;
  
  /**
   * <li>Rss 1.0 - &lt;item> 
   * While commonly a news headline, with RSS 1.0's modular extensibility, 
   * this can be just about anything: discussion posting, job listing, software 
   * patch -- any object with a URI. There may be a minimum of one item per RSS 
   * document. While RSS 1.0 does not enforce an upper limit, 
   * for backward compatibility with RSS 0.9 and 0.91, a maximum of fifteen items is recommended.
   * </li>
   * <li>Rss 2.0 - &lt;item> 
   * A channel may contain any number of <item>s. An item may represent a "story" 
   * -- much like a story in a newspaper or magazine; if so its description is a 
   * synopsis of the story, and the link points to the full story. An item may also 
   * be complete in itself, if so, the description contains the text 
   * (entity-encoded HTML is allowed; see examples), and the link and title 
   * may be omitted. All elements of an item are optional, however at least one 
   * of title or description must be present.
   * </li>
   * <li>Atom 1.0 - &lt;entry>
   * The "atom:entry" element represents an individual entry, acting as a 
   * container for metadata and data associated with the entry. This element 
   * can appear as a child of the atom:feed element, or it can appear as the 
   * document (i.e., top-level) element of a standalone Atom Entry Document.
   * </li>
   * @return
   */
  public List<ItemEntry> getItems() {
    return _items;
  }

  /**
   * <li>Rss 1.0 - &lt;item> 
   * While commonly a news headline, with RSS 1.0's modular extensibility, 
   * this can be just about anything: discussion posting, job listing, software 
   * patch -- any object with a URI. There may be a minimum of one item per RSS 
   * document. While RSS 1.0 does not enforce an upper limit, 
   * for backward compatibility with RSS 0.9 and 0.91, a maximum of fifteen items is recommended.
   * </li>
   * <li>Rss 2.0 - &lt;item> 
   * A channel may contain any number of <item>s. An item may represent a "story" 
   * -- much like a story in a newspaper or magazine; if so its description is a 
   * synopsis of the story, and the link points to the full story. An item may also 
   * be complete in itself, if so, the description contains the text 
   * (entity-encoded HTML is allowed; see examples), and the link and title 
   * may be omitted. All elements of an item are optional, however at least one 
   * of title or description must be present.
   * </li>
   * <li>Atom 1.0 - &lt;entry>
   * The "atom:entry" element represents an individual entry, acting as a 
   * container for metadata and data associated with the entry. This element 
   * can appear as a child of the atom:feed element, or it can appear as the 
   * document (i.e., top-level) element of a standalone Atom Entry Document.
   * </li>
   * @param items
   * @return
   */
  public ChannelFeed setItems(List<ItemEntry> items) {
    _items = items;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;item> 
   * While commonly a news headline, with RSS 1.0's modular extensibility, 
   * this can be just about anything: discussion posting, job listing, software 
   * patch -- any object with a URI. There may be a minimum of one item per RSS 
   * document. While RSS 1.0 does not enforce an upper limit, 
   * for backward compatibility with RSS 0.9 and 0.91, a maximum of fifteen items is recommended.
   * </li>
   * <li>Rss 2.0 - &lt;item> 
   * A channel may contain any number of <item>s. An item may represent a "story" 
   * -- much like a story in a newspaper or magazine; if so its description is a 
   * synopsis of the story, and the link points to the full story. An item may also 
   * be complete in itself, if so, the description contains the text 
   * (entity-encoded HTML is allowed; see examples), and the link and title 
   * may be omitted. All elements of an item are optional, however at least one 
   * of title or description must be present.
   * </li>
   * <li>Atom 1.0 - &lt;entry>
   * The "atom:entry" element represents an individual entry, acting as a 
   * container for metadata and data associated with the entry. This element 
   * can appear as a child of the atom:feed element, or it can appear as the 
   * document (i.e., top-level) element of a standalone Atom Entry Document.
   * </li>
   * <br/>
   * This method adds all the input {@link ItemEntry} to the end of the items list.
   * @param items
   * @return
   */
  public ChannelFeed addItem(ItemEntry...items){
    if(ArrayUtils.isEmpty(items)){
      LOG.warn("Empty items array is ignored");
      return this;
    }
    if(_items == null){
      _items = new ArrayList<ItemEntry>();
    }
    for(ItemEntry item : items){
      _items.add(item);
    }
    return this;
  }

  /**
   * <li>Rss 1.0 - The channel's title.
   * </li>
   * <li>Rss 2.0 - The title of the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:title" element is a Text construct that conveys a 
   * human-readable title for an entry or feed.
   * </li>
   * <br/>
   * Note: Rss 1.0 and Rss 2.0 only use the text content of the title field, 
   * the other fields in the {@link Text} object are ignored.
   * @return
   */
  public Text getTitle() {
    return _title;
  }
  
  /**
   * <li>Rss 1.0 - The channel's title.
   * </li>
   * <li>Rss 2.0 - The title of the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:title" element is a Text construct that conveys a 
   * human-readable title for an entry or feed.
   * </li>
   * <br/>
   * This method returns the text content of the <code>title</code> field.
   * @return
   */
  public String getTitleText() {
    return _title == null ? null :_title.getText();
  }

  /**
   * <li>Rss 1.0 - The channel's title.
   * </li>
   * <li>Rss 2.0 - The title of the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:title" element is a Text construct that conveys a 
   * human-readable title for an entry or feed.
   * </li>
   * <br/>
   * Note: Rss 1.0 and Rss 2.0 only use the text content of the title field, 
   * the other fields in the {@link Text} object are ignored.
   * @param title
   * @return
   */
  public ChannelFeed setTitle(Text title) {
    _title = title;
    return this;
  }

  /**
   * <li>Rss 1.0 - The channel's title.
   * </li>
   * <li>Rss 2.0 - The title of the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:title" element is a Text construct that conveys a 
   * human-readable title for an entry or feed.
   * </li>
   * <br/>
   * This method constructs a new {@link Text} object with the input string as its text
   * content.
   * <br/>
   * Note: Rss 1.0 and Rss 2.0 only use the text content of the title field, 
   * the other fields in the {@link Text} object are ignored.
   * @param title
   * @return
   */
  public ChannelFeed setTitle(String title){
    if(title == null){
      _title = null;
      return this;
    }
    return setTitle(new Text(title));
  }

  /**
   * <li>Rss 1.0 - The URL to which an HTML rendering of the channel title will link, 
   * commonly the parent site's home or news page.
   * </li>
   * <li>Rss 2.0 - The URL to the HTML website corresponding to the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:link" element defines a reference from an entry or feed to a Web 
   * resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * Note: According to the specs, only Atom 1.0 allows multiple links under the {@link ChannelFeed}.
   * Therefore, for Rss 1.0 and Rss 2.0, only the first link will be interpreted, the rest will be 
   * ignored.
   * @return
   */
  public List<Link> getLinks() {
    return _links;
  }


  /**
   * <li>Rss 1.0 - The URL to which an HTML rendering of the channel title will link, 
   * commonly the parent site's home or news page.
   * </li>
   * <li>Rss 2.0 - The URL to the HTML website corresponding to the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:link" element defines a reference from an entry or feed to a Web 
   * resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * Note: According to the specs, only Atom 1.0 allows multiple links under the {@link ChannelFeed}.
   * Therefore, for Rss 1.0 and Rss 2.0, only the first link will be interpreted, the rest will be 
   * ignored.
   * @param links
   * @return
   */
  public ChannelFeed setLinks(List<Link> links) {
    _links = links;
    return this;
  }


  /**
   * <li>Rss 1.0 - The URL to which an HTML rendering of the channel title will link, 
   * commonly the parent site's home or news page.
   * </li>
   * <li>Rss 2.0 - The URL to the HTML website corresponding to the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:link" element defines a reference from an entry or feed to a Web 
   * resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * Note: According to the specs, only Atom 1.0 allows multiple links under the {@link ChannelFeed}.
   * Therefore, for Rss 1.0 and Rss 2.0, only the first link will be interpreted, the rest will be 
   * ignored.
   * <br/>
   * This method creates new {@link Link} objects and adds them to the END of the links list. 
   * 
   * @param href
   * @return
   */
  public ChannelFeed addLink(String... href) {
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
   * <li>Rss 1.0 - The URL to which an HTML rendering of the channel title will link, 
   * commonly the parent site's home or news page.
   * </li>
   * <li>Rss 2.0 - The URL to the HTML website corresponding to the channel.
   * </li>
   * <li>Atom 1.0 - 
   * The "atom:link" element defines a reference from an entry or feed to a Web 
   * resource. This specification assigns no meaning to the content (if any) of this element.
   * </li>
   * 
   * <br/>
   * Note: According to the specs, only Atom 1.0 allows multiple links under the {@link ChannelFeed}.
   * Therefore, for Rss 1.0 and Rss 2.0, only the first link will be interpreted, the rest will be 
   * ignored.
   * <br/>
   * 
   * <br/>
   * This method adds input {@link Link} to the END of the links list. 
   * 
   * @param link
   * @return
   */
  public ChannelFeed addLink(Link... link) {
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
   * <li>Rss 1.0 - &lt;description> 
   * A brief description of the channel's content, function, source, etc.
   * </li>
   * <li>Rss 2.0 - &lt;description> Phrase or sentence describing the channel.
   * </li>
   * <li>Atom 1.0 - 
   * This maps to &lt;subtitle>. 
   * The "atom:subtitle" element is a Text construct that conveys a human-readable description or subtitle for a feed.
   * </li>
   * @return
   */
  public Text getDescriptionOrSubtitle() {
    return _descriptionOrSubtitle;
  }
  
  /**
   * <li>Rss 1.0 - &lt;description> 
   * A brief description of the channel's content, function, source, etc.
   * </li>
   * <li>Rss 2.0 - &lt;description> Phrase or sentence describing the channel.
   * </li>
   * <li>Atom 1.0 - 
   * This maps to &lt;subtitle>. 
   * The "atom:subtitle" element is a Text construct that conveys a human-readable description or subtitle for a feed.
   * </li>
   * <br/>
   * This method returns the text content of the <code>descriptionOrSubtitle</code> field.
   * @return
   */
  public String getDescriptionOrSubtitleText() {
    return _descriptionOrSubtitle == null ? null : _descriptionOrSubtitle.getText();
  }

  /**
   * <li>Rss 1.0 - &lt;description> 
   * A brief description of the channel's content, function, source, etc.
   * </li>
   * <li>Rss 2.0 - &lt;description> Phrase or sentence describing the channel.
   * </li>
   * <li>Atom 1.0 - 
   * This maps to &lt;subtitle>. 
   * The "atom:subtitle" element is a Text construct that conveys a human-readable description or subtitle for a feed.
   * </li>
   * @param descriptionOrSubtitle
   * @return
   */
  public ChannelFeed setDescriptionOrSubtitle(Text descriptionOrSubtitle) {
    _descriptionOrSubtitle = descriptionOrSubtitle;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;description> 
   * A brief description of the channel's content, function, source, etc.
   * </li>
   * <li>Rss 2.0 - &lt;description> Phrase or sentence describing the channel.
   * </li>
   * <li>Atom 1.0 - 
   * This maps to &lt;subtitle>. 
   * The "atom:subtitle" element is a Text construct that conveys a human-readable description or subtitle for a feed.
   * </li>
   * 
   * <br/>
   * This method constructs a new {@link Text} object with the input string as its text content.
   * @param descriptionOrSubtitle
   * @return
   */
  public ChannelFeed setDescriptionOrSubtitle(String descriptionOrSubtitle) {
    _descriptionOrSubtitle = descriptionOrSubtitle == null? null: new Text(descriptionOrSubtitle);
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:rights> Information about rights held in and over the resource.
   * </li>
   * <li>Rss 2.0 - &lt;copyright> Copyright notice for content in the channel.
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
   * <li>Rss 2.0 - &lt;copyright> Copyright notice for content in the channel.
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
   * <li>Rss 2.0 - &lt;copyright> Copyright notice for content in the channel.
   * </li>
   * <li>Atom 1.0 - &lt;rights> The "atom:rights" element is a Text construct that conveys information about rights held in and over an entry or feed.
   * </li>
   * @param rights
   * @return
   */
  public ChannelFeed setRights(Text rights) {
    _rights = rights;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:rights> Information about rights held in and over the resource.
   * </li>
   * <li>Rss 2.0 - &lt;copyright> Copyright notice for content in the channel.
   * </li>
   * <li>Atom 1.0 - &lt;rights> The "atom:rights" element is a Text construct that conveys information about rights held in and over an entry or feed.
   * </li>
   * <br/>
   * This method creates a new {@link Text} object using the input string as its content and sets <code>rights</code> field to this object.
   * @param rights
   * @return
   */
  public ChannelFeed setRights(String rights) {
    if(rights == null){
      _rights = null;
      return this;
    }
    return setRights(new Text(rights));
  }

  /**
   * <li>Rss 1.0 - &lt;dc:publisher> An entity responsible for making the resource available
   * </li>
   * <li>Rss 2.0 - &lt;managingEditor> Email address for person responsible for editorial content.
   * </li>
   * <li>Atom 1.0 - &lt;author> 
   * The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * Note: This is a list because Atom 1.0 allows multiple &lt;author>. For Rss 1.0 and Rss 2.0,
   * only the first {@link Person} is interpreted, the rest are ignored.
   * @return
   */
  public List<Person> getManagingEditorOrAuthorOrPublisher() {
    return _managingEditorOrAuthorOrPublisher;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:publisher> An entity responsible for making the resource available
   * </li>
   * <li>Rss 2.0 - &lt;managingEditor> Email address for person responsible for editorial content.
   * </li>
   * <li>Atom 1.0 - &lt;author> 
   * The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * Note: This is a list because Atom 1.0 allows multiple &lt;author>. For Rss 1.0 and Rss 2.0,
   * only the first {@link Person} is interpreted, the rest are ignored.
   * @param managingEditorOrAuthorOrPublisher
   * @return
   */
  public ChannelFeed setManagingEditorOrAuthorOrPublisher(
      List<Person> managingEditorOrAuthorOrPublisher) {
    _managingEditorOrAuthorOrPublisher = managingEditorOrAuthorOrPublisher;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:publisher> An entity responsible for making the resource available
   * </li>
   * <li>Rss 2.0 - &lt;managingEditor> Email address for person responsible for editorial content.
   * </li>
   * <li>Atom 1.0 - &lt;author> 
   * The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * Note: This is a list because Atom 1.0 allows multiple &lt;author>. For Rss 1.0 and Rss 2.0,
   * only the first {@link Person} is interpreted, the rest are ignored.
   * <br/>
   * This method constructs a new {@link Person} object with input email as its email and adds it to the end
   * of the list.
   * @param managingEditorOrAuthorOrPublisher
   * @return
   */
  public ChannelFeed addManagingEditorOrAuthorOrPublisher(
      String... emails) {
    if(ArrayUtils.isEmpty(emails)){
      LOG.warn("Empty email array is ignored");
      return this;
    }
    if(_managingEditorOrAuthorOrPublisher == null){
      _managingEditorOrAuthorOrPublisher = new ArrayList<Person>();
    }
    for(String s :emails){
      _managingEditorOrAuthorOrPublisher.add(new Person(s));
    }
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:publisher> An entity responsible for making the resource available
   * </li>
   * <li>Rss 2.0 - &lt;managingEditor> Email address for person responsible for editorial content.
   * </li>
   * <li>Atom 1.0 - &lt;author> 
   * The "atom:author" element is a Person construct that indicates the author of the entry or feed.
   * </li>
   * <br/>
   * Note: This is a list because Atom 1.0 allows multiple &lt;author>. For Rss 1.0 and Rss 2.0,
   * only the first {@link Person} is interpreted, the rest are ignored.
   * <br/>
   * This method adds all input {@link Person} to the END of the list.
   * @param managingEditorOrAuthorOrPublisher
   * @return
   */
  public ChannelFeed addManagingEditorOrAuthorOrPublisher(
      Person... persons) {
    if(ArrayUtils.isEmpty(persons)){
      LOG.warn("Empty email array is ignored");
      return this;
    }
    if(_managingEditorOrAuthorOrPublisher == null){
      _managingEditorOrAuthorOrPublisher = new ArrayList<Person>();
    }
    for(Person p : persons){
      _managingEditorOrAuthorOrPublisher.add(p);
    }
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
   * the resource.
   * </li>
   * <li>Rss 2.0 - &lt;webMaster> Email address for person responsible for technical issues relating to channel.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * 
   * <br/>
   * Notes: according to Rss 2.0 specs, there can be only 1 &lt;webMaster> element under &lt;channel>,
   * therefore, only the first {@link Person} is interpreted, the rest are ignored.  
   * @return
   */
  public List<Person> getWebMasterOrCreator() {
    return _webMasterOrCreator;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
   * the resource.
   * </li>
   * <li>Rss 2.0 - &lt;webMaster> Email address for person responsible for technical issues relating to channel.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * 
   * <br/>
   * Notes: according to Rss 2.0 specs, there can be only 1 &lt;webMaster> element under &lt;channel>,
   * therefore, only the first {@link Person} is interpreted, the rest are ignored.
   * @param webMasterOrCreator
   * @return
   */
  public ChannelFeed setWebMasterOrCreator(List<Person> webMasterOrCreator) {
    _webMasterOrCreator = webMasterOrCreator;
    return this;
  }


  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
   * the resource.
   * </li>
   * <li>Rss 2.0 - &lt;webMaster> Email address for person responsible for technical issues relating to channel.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * <br/>
   * This method adds all the input {@link Person} to the END of the list.
   * <br/>
   * Notes: according to Rss 2.0 specs, there can be only 1 &lt;webMaster> element under &lt;channel>,
   * therefore, only the first {@link Person} is interpreted, the rest are ignored.
   * @param webMasterOrCreator
   * @return
   */
  public ChannelFeed addWebMasterOrCreator(Person... webMasterOrCreator) {
    if(ArrayUtils.isEmpty(webMasterOrCreator)){
      LOG.warn("empty person array is ignored");
      return this;
    }
    if(_webMasterOrCreator == null){
      _webMasterOrCreator = new ArrayList<Person>();
    }
    for(Person p : webMasterOrCreator){
      _webMasterOrCreator.add(p);
    }
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity primarily responsible for making the content of
   * the resource.
   * </li>
   * <li>Rss 2.0 - &lt;webMaster> Email address for person responsible for technical issues relating to channel.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * <br/>
   * This method constructs new {@link Person} object and adds them all to the END of the list.
   * <br/>
   * Notes: according to Rss 2.0 specs, there can be only 1 &lt;webMaster> element under &lt;channel>,
   * therefore, only the first {@link Person} is interpreted, the rest are ignored.
   * @param emailOrText
   * @return
   */
  public ChannelFeed addWebMasterOrCreator(String... emailOrText) {
    if(ArrayUtils.isEmpty(emailOrText)){
      LOG.warn("empty array is ignored");
      return this;
    }
    if(_webMasterOrCreator == null){
      _webMasterOrCreator = new ArrayList<Person>();
    }
    for(String e : emailOrText){
      _webMasterOrCreator.add(new Person(e));
    }
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity responsible for making contributions to the content of the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;contributor> 
   * The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * 
   * @return
   */
  public List<Person> getContributors() {
    return _contributors;
  }


  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity responsible for making contributions to the content of the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;contributor> 
   * The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * @param contributors
   * @return
   */
  public ChannelFeed setContributors(List<Person> contributors) {
    _contributors = contributors;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;dc:creator> An entity responsible for making contributions to the content of the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;contributor> 
   * The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * <br/>
   * This method adds all the input {@link Person} to the END of the list.
   * 
   * @param contributor
   * @return
   */
  public ChannelFeed addContributor(Person... contributor) {
    if(ArrayUtils.isEmpty(contributor)){
      LOG.warn("empty person array is ignored");
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
   * <li>Rss 1.0 - &lt;dc:creator> An entity responsible for making contributions to the content of the resource.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - &lt;contributor> 
   * The "atom:contributor" element is a Person construct that indicates a person or other entity who contributed to the entry or feed.
   * </li>
   * <br/>
   * This method constructs new {@link Person} object and adds them all to the END of the list.
   * @return
   */
  public ChannelFeed addContributor(String... emailOrText) {
    if(ArrayUtils.isEmpty(emailOrText)){
      LOG.warn("empty array is ignored");
      return this;
    }
    if(_contributors == null){
      _contributors = new ArrayList<Person>();
    }
    for(String e : emailOrText){
      _contributors.add(new Person(e));
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
  public ChannelFeed setCategorySubjects(Set<CategorySubject> categorySubjects) {
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
  public ChannelFeed addCategorySubject(String... categorySubjectOrTerm) {
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
  public ChannelFeed addCategorySubject(CategorySubject... categorySubject) {
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
   * <li>Rss 1.0 - &lt;dc:date>  A date associated with an event in the life cycle of the resource.
   * </li>
   * <li>Rss 2.0 - &lt;pubDate> Indicates when the item was published. 
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
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
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * @param pubDate
   * @return
   */
  public ChannelFeed setPubDate(String pubDate) {
    _pubDate = pubDate;
    return this;
  }
  
  /**
   * <li>Rss 1.0 - &lt;dc:date>  A date associated with an event in the life cycle of the resource.
   * </li>
   * <li>Rss 2.0 - &lt;pubDate> Indicates when the item was published. 
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.
   * </li>
   * 
   * <br/>
   * This method uses the input {@link SimpleDateFormat} object to format the date into a string.
   * @param pubDate
   * @param format
   * @return
   */
  public ChannelFeed setPubDate(Date pubDate, SimpleDateFormat format){
    _pubDate = format.format(pubDate);
    return this;
  }


  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;lastBuildDate> The last time the content of the channel changed. 
   * </li>
   * <li>Atom 1.0 - &lt;updated> The "atom:updated" element is a Date construct indicating the most recent 
   * instant in time when an entry or feed was modified in a way the publisher considers significant. 
   * Therefore, not all modifications necessarily result in a changed atom:updated value.
   * </li>
   * 
   * @return
   */
  public String getLastBuildOrUpdatedDate() {
    return _lastBuildOrUpdatedDate;
  }


  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;lastBuildDate> The last time the content of the channel changed. 
   * </li>
   * <li>Atom 1.0 - &lt;updated> The "atom:updated" element is a Date construct indicating the most recent 
   * instant in time when an entry or feed was modified in a way the publisher considers significant. 
   * Therefore, not all modifications necessarily result in a changed atom:updated value.
   * </li>
   * 
   * @param lastBuildOrUpdatedDate
   * @return
   */
  public ChannelFeed setLastBuildOrUpdatedDate(String lastBuildOrUpdatedDate) {
    _lastBuildOrUpdatedDate = lastBuildOrUpdatedDate;
    return this;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - &lt;lastBuildDate> The last time the content of the channel changed. 
   * </li>
   * <li>Atom 1.0 - &lt;updated> The "atom:updated" element is a Date construct indicating the most recent 
   * instant in time when an entry or feed was modified in a way the publisher considers significant. 
   * Therefore, not all modifications necessarily result in a changed atom:updated value.
   * </li>
   * <br/>
   * This method uses the input {@link SimpleDateFormat} object to format the date into a string.
   * @param lastBuildOrUpdatedDate
   * @return
   */
  public ChannelFeed setLastBuildOrUpdatedDate(Date lastBuildOrUpdatedDate, SimpleDateFormat format) {
    return setLastBuildOrUpdatedDate(format.format(lastBuildOrUpdatedDate));
  }
  
  
  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
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
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
   * </li>
   * @param uid
   * @return
   */
  public ChannelFeed setUid(Id uid) {
    _uid = uid;
    return this;
  }

  /**
   * <li>Rss 1.0 - Not supported, this field is ignored.
   * </li>
   * <li>Rss 2.0 - Not supported, this field is ignored.
   * </li>
   * <li>Atom 1.0 - The "atom:id" element conveys a permanent, universally unique identifier for an entry or feed.
   * </li>
   * <br/>
   * This method constructs a new {@link Id} object and sets the <code>uid</code> field to this object.
   * @param uid
   * @return
   */
  public ChannelFeed setUid(String uid){
    if(_uid == null){
      _uid = null;
      return this;
    }
    _uid = new Id(uid);
    return this;
  }


  /**
   * <li>Rss 1.0 - Not supported, this is ignored.</li>
   * <li>Rss 2.0 - &lt;generator> A string indicating the program used to generate the channel.  
   * </li>
   * <li>Atom 1.0 - &lt;generator> 
   * The "atom:generator" element's content identifies the agent used to generate a feed, for debugging and other purposes.
   * </li>
   * @return
   */
  public Generator getGenerator() {
    return _generator;
  }

  /**
   * <li>Rss 1.0 - Not supported, this is ignored.</li>
   * <li>Rss 2.0 - &lt;generator> A string indicating the program used to generate the channel.  
   * </li>
   * <li>Atom 1.0 - &lt;generator> 
   * The "atom:generator" element's content identifies the agent used to generate a feed, for debugging and other purposes.
   * </li>
   * @param generator
   * @return
   */
  public ChannelFeed setGenerator(Generator generator) {
    _generator = generator;
    return this;
  }

  /**
   * <li>Rss 1.0 - Not supported, this is ignored.</li>
   * <li>Rss 2.0 - &lt;generator> A string indicating the program used to generate the channel.  
   * </li>
   * <li>Atom 1.0 - &lt;generator> 
   * The "atom:generator" element's content identifies the agent used to generate a feed, for debugging and other purposes.
   * </li>
   * <br/>
   * This method constructs a new {@link Generator} object.
   * @param generatorValue
   * @return
   */
  public ChannelFeed setGenerator(String generatorValue) {
    _generator = generatorValue == null?null: new Generator(generatorValue);
    return this;
  }
  
  /**
   * <b>Rss 2.0 only</b><br/>
   * A URL that points to the documentation for the format used in the RSS file. 
   * It's probably a pointer to this page. It's for people who might stumble 
   * across an RSS file on a Web server 25 years from now and wonder what it is.
   * @return
   */
  public String getDocs() {
    return _docs;
  }


  /**
   * <b>Rss 2.0 only</b><br/>
   * A URL that points to the documentation for the format used in the RSS file. 
   * It's probably a pointer to this page. It's for people who might stumble 
   * across an RSS file on a Web server 25 years from now and wonder what it is.
   * @param docs
   * @return
   */
  public ChannelFeed setDocs(String docs) {
    _docs = docs;
    return this;
  }

  /**
   * <li>Rss 1.0 - This value is parsed from both the 
   * &lt;sy:updatePeriod> and  &lt;sy:updateFrequency> <br/>
   * for example: updatePeriod:hourly and updateFrequency:2 = ttl: 30 minutes
   * </li>
   * <li>Rss 2.0 - ttl stands for time to live. It's a number of minutes that 
   * indicates how long a channel can be cached before refreshing from the source.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.</li>
   * @return
   */
  public Integer getTtl() {
    return _ttl;
  }

  /**
   * <li>Rss 1.0 - This value is parsed from both the 
   * &lt;sy:updatePeriod> and  &lt;sy:updateFrequency> <br/>
   * for example: updatePeriod:hourly and updateFrequency:2 = ttl: 30 minutes
   * </li>
   * <li>Rss 2.0 - ttl stands for time to live. It's a number of minutes that 
   * indicates how long a channel can be cached before refreshing from the source.
   * </li>
   * <li>Atom 1.0 - Not supported, this field is ignored.</li>
   * @param ttl
   * @return
   */
  public ChannelFeed setTtl(Integer ttl) {
    _ttl = ttl;
    return this;
  }


  /**
   * <b>Rss 2.0 only</b><br/>
   * Allows processes to register with a cloud to be notified of updates to the 
   * channel, implementing a lightweight publish-subscribe protocol for RSS feeds.
   * @return
   */
  public Cloud getCloud() {
    return _cloud;
  }


  /**
   * <b>Rss 2.0 only</b><br/>
   * Allows processes to register with a cloud to be notified of updates to the 
   * channel, implementing a lightweight publish-subscribe protocol for RSS feeds.
   * @param cloud
   * @return
   */
  public ChannelFeed setCloud(Cloud cloud) {
    _cloud = cloud;
    return this;
  }

  /**
   * <li>Rss 1.0 - &lt;image> 
   * Establishes an RDF association between the optional image element [5.4] and this particular RSS channel. The rdf:resource's {image_uri} must be the same as the image element's rdf:about {image_uri}.
   * </li>
   * <li> Rss 2.0 - &lt;image>
   * Specifies a GIF, JPEG or PNG image that can be displayed with the channel. </li>
   * <li>Atom 1.0 - &lt;icon>
   * The "atom:icon" element's content is an IRI reference [RFC3987] which identifies 
   * an image which provides iconic visual identification for a feed.
   * </li>
   * @return
   */
  public Image getImageOrIcon() {
    return _imageOrIcon;
  }

  /**
   * <li>Rss 1.0 - &lt;image> 
   * Establishes an RDF association between the optional image element [5.4] and this particular RSS channel. The rdf:resource's {image_uri} must be the same as the image element's rdf:about {image_uri}.
   * </li>
   * <li> Rss 2.0 - &lt;image>
   * Specifies a GIF, JPEG or PNG image that can be displayed with the channel. </li>
   * <li>Atom 1.0 - &lt;icon>
   * The "atom:icon" element's content is an IRI reference [RFC3987] which identifies 
   * an image which provides iconic visual identification for a feed.
   * </li>
   * @param imageOrIcon
   * @return
   */
  public ChannelFeed setImageOrIcon(Image imageOrIcon) {
    _imageOrIcon = imageOrIcon;
    return this;
  }


  /**
   * <b>Atom 1.0 only </b><br/>
   * The "atom:logo" element's content is an IRI reference [RFC3987] which identifies an image which provides visual identification for a feed.
   * @return
   */
  public Image getLogo() {
    return _logo;
  }


  /**
   * <b>Atom 1.0 only </b><br/>
   * The "atom:logo" element's content is an IRI reference [RFC3987] which identifies an image which provides visual identification for a feed.
   * @param logo
   * @return
   */
  public ChannelFeed setLogo(Image logo) {
    _logo = logo;
    return this;
  }


  /**
   * <b>Rss 1.0 and Rss 2.0 only </b><br/>
   * <li>Rss 1.0 - Establishes an RDF association between the optional textinput element [5.6] and this particular RSS channel. The {textinput_uri} rdf:resource must be the same as the textinput element's rdf:about {textinput_uri}.
   * </li>
   * <li>Rss 2.0 - Specifies a text input box that can be displayed with the channel. </li>
   * @return
   */
  public TextInput getTexInput() {
    return _texInput;
  }


  /**
   * <b>Rss 1.0 and Rss 2.0 only </b><br/>
   * <li>Rss 1.0 - Establishes an RDF association between the optional textinput element [5.6] and this particular RSS channel. The {textinput_uri} rdf:resource must be the same as the textinput element's rdf:about {textinput_uri}.
   * </li>
   * <li>Rss 2.0 - Specifies a text input box that can be displayed with the channel. </li>
   * @param texInput
   * @return
   */
  public ChannelFeed setTexInput(TextInput texInput) {
    _texInput = texInput;
    return this;
  }

   /**
    * <b>Rss 2.0 only</b><br/>
    * An XML element that contains up to 24 <hour> sub-elements whose value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not read the channel on hours listed in the skipHours element.
    * <br/>
    * The hour beginning at midnight is hour zero.
    */
   public Set<Integer> getSkipHours() {
     return _skipHours;
   }
   /**
    * <b>Rss 2.0 only</b><br/>
    * An XML element that contains up to 24 <hour> sub-elements whose value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not read the channel on hours listed in the skipHours element.
    * <br/>
    * The hour beginning at midnight is hour zero.
    */
   public ChannelFeed setSkipHours(Set<Integer> skipHours) {
     for(Integer i : skipHours){
       if(i == null || i <0 || i >23){
         throw new IllegalArgumentException("all skip hour must be a value that is a number between 0 and 23");
       }
     }
     _skipHours = skipHours;
     return this;
   }
 
   /**
    * <b>Rss 2.0 only</b><br/>
    * Add a skip hour to the feed;
    * @param hour value is a number between 0 and 23, representing a time in GMT, when aggregators, if they support the feature, may not read the channel on hours listed in the skipHours element.
    * <br/>
    * The hour beginning at midnight is hour zero.
    */
   public ChannelFeed addSkipHour(int... hour){
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
    * <b>Rss 2.0 only</b><br/>
    * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators may not read the channel during days listed in the skipDays element.
    */
   public Set<Day> getSkipDays() {
     return _skipDays;
   }
   /**
    * <b>Rss 2.0 only</b><br/>
    * value is Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators may not read the channel during days listed in the skipDays element.
    */
   public ChannelFeed setSkipDays(Set<Day> skipDays) {
     _skipDays = skipDays;
     return this;
   }
 
   /**
    * <b>Rss 2.0 only</b><br/>
    * Add a skip day.
    */
   public ChannelFeed addSkipDay(Day... day){
     if(!ArrayUtils.isEmpty(day)){
       if(_skipDays == null){
         _skipDays = new HashSet<Day>();
       }
       _skipDays.addAll(Arrays.asList(day));
     }
 
     return this;
   }


  ////////////////////////Common setters///////////////////////
   /**
    * Any other attribute that is not in the RSS 2.0 specs.
    */
   public ChannelFeed setOtherAttributes(Map<QName, String> otherAttributes) {
     _otherAttributes = otherAttributes;
     return this;
   }
   /**
    * Add an attribute that is not in the RSS 2.0 specs.
    */
   public ChannelFeed addOtherAttributes(QName namespace, String attribute) {
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
   public ChannelFeed setOtherElements(List<Element> otherElements) {
     _otherElements = otherElements;
     return this;
   }
   /**
    * Add an element that is not specified in the Rss specs.<br/>
    * **Note** The element should not have an empty namespace to avoid collision with the specs elements.
    * @param element - any element
    */
   public ChannelFeed addOtherElement(Element element){
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
   public ChannelFeed addOtherElement(String xmlString) throws SAXException, IOException, ParserConfigurationException{
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
   public ChannelFeed setBase(String base) {
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
   public ChannelFeed setLang(String lang) {
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
   public ChannelFeed setLang(Locale lang) {
     _lang = lang.getLanguage();
     return this;
   }
   /**
    * <b>Rss 1.0 only</b><br/>
    * @param resource
    * @return
    */
   public ChannelFeed setResource(String resource) {
     _resource = resource;
     return this;
   }
   /**
    * <b>Rss 1.0 only</b><br/>
    * @param about
    * @return
    */
   public ChannelFeed setAbout(String about) {
     _about = about;
     return this;
   }
   ////////////////////////Common setters///////////////////////
   
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    if(CollectionUtils.isEmpty(_items)){
      throw new ValidationException("Channel: You should have at least 1 item");
    }
    
    for(ItemEntry item : _items){
      ValidationUtils.validateNotNull("Channel: All item should not be null", item);
      item.validate(format);
    }
    
    ValidationUtils.validateNotNull("Channel: Title, Link and Description should not be null", _title, _links, _descriptionOrSubtitle);
  
    _title.validate(format);
    _descriptionOrSubtitle.validate(format);
    
    for(Link l : _links){
      l.validate(format);
    }
    
    if(_categorySubjects != null){
      for(CategorySubject c: _categorySubjects){
        c.validate(format);
      }
    }
    if(_cloud != null){
      _cloud.validate(format);
    }
    if(_imageOrIcon != null){
      _imageOrIcon.validate(format);
    }
    if(_logo != null){
      _logo.validate(format);
    }
    if(_texInput != null){
      _texInput.validate(format);
    }    
    
    if(_uid != null){
      _uid.validate(format);
    }
    
    if(_generator != null){
      _generator.validate(format);
    }
    
    if(_contributors != null){
      for(Person p : _contributors){
        p.validate(format);
      }
    }
    
    if(_managingEditorOrAuthorOrPublisher != null){
      for(Person p : _managingEditorOrAuthorOrPublisher){
        p.validate(format);
      }
    }
    
    if(_webMasterOrCreator != null){
      for(Person p : _webMasterOrCreator){
        p.validate(format);
      }
    }
    
    if(_rights != null){
      _rights.validate(format);
    }
    
  }
  
}