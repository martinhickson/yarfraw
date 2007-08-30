package yarfraw.core.datamodel;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The name of the channel. It's how people refer to your service. 
 * If you have an HTML website that contains the same information as your RSS file, 
 * the title of your channel should be the same as the title of your website.
 * <p/>
 * for Atom 1.0 format, the field also mapps to &lt;feed> element. 
 * <br/>
 * @author jliang
 *
 */
public class Channel extends AbstractBaseObject{

  private List<Item> _items;
  private Text _title;
  private List<Link> _links;
  private Text _descriptionOrSubtitle;
  private Text _copyright;
  private Person _managingEditorOrAuthorOrPublisher;
  private Person _webMaster;
  private List<Person> _contributors;
  private Set<CategorySubject> _categorySubjects;
  private String _pubDate;
  private Date _lastBuildOrUpdatedDate;
  private Id _uid;
  private Text _generator;
  private String _docs;
  private Integer _ttl;
  private Cloud _cloud;
  private Image _imageOrIcon;
  private Image _logo;
  private TextInput _texInput;
  private Set<Integer> _skipHours = new HashSet<Integer>();
  private Set<Day> _skipDays = new HashSet<Day>();
  
  
  public List<Item> getItems() {
    return _items;
  }


  public void setItems(List<Item> items) {
    _items = items;
  }


  public Text getTitle() {
    return _title;
  }


  public void setTitle(Text title) {
    _title = title;
  }


  public List<Link> getLinks() {
    return _links;
  }


  public void setLinks(List<Link> links) {
    _links = links;
  }


  public Text getDescriptionOrSubtitle() {
    return _descriptionOrSubtitle;
  }


  public void setDescriptionOrSubtitle(Text descriptionOrSubtitle) {
    _descriptionOrSubtitle = descriptionOrSubtitle;
  }


  public Text getCopyright() {
    return _copyright;
  }


  public void setCopyright(Text copyright) {
    _copyright = copyright;
  }


  public Person getManagingEditorOrAuthorOrPublisher() {
    return _managingEditorOrAuthorOrPublisher;
  }


  public void setManagingEditorOrAuthorOrPublisher(
      Person managingEditorOrAuthorOrPublisher) {
    _managingEditorOrAuthorOrPublisher = managingEditorOrAuthorOrPublisher;
  }


  public Person getWebMaster() {
    return _webMaster;
  }


  public void setWebMaster(Person webMaster) {
    _webMaster = webMaster;
  }


  public List<Person> getContributors() {
    return _contributors;
  }


  public void setContributors(List<Person> contributors) {
    _contributors = contributors;
  }


  public Set<CategorySubject> getCategorySubjects() {
    return _categorySubjects;
  }


  public void setCategorySubjects(Set<CategorySubject> categorySubjects) {
    _categorySubjects = categorySubjects;
  }


  public String getPubDate() {
    return _pubDate;
  }


  public void setPubDate(String pubDate) {
    _pubDate = pubDate;
  }


  public Date getLastBuildOrUpdatedDate() {
    return _lastBuildOrUpdatedDate;
  }


  public void setLastBuildOrUpdatedDate(Date lastBuildOrUpdatedDate) {
    _lastBuildOrUpdatedDate = lastBuildOrUpdatedDate;
  }


  public Id getUid() {
    return _uid;
  }


  public void setUid(Id uid) {
    _uid = uid;
  }


  public Text getGenerator() {
    return _generator;
  }


  public void setGenerator(Text generator) {
    _generator = generator;
  }


  public String getDocs() {
    return _docs;
  }


  public void setDocs(String docs) {
    _docs = docs;
  }


  public Integer getTtl() {
    return _ttl;
  }


  public void setTtl(Integer ttl) {
    _ttl = ttl;
  }


  public Cloud getCloud() {
    return _cloud;
  }


  public void setCloud(Cloud cloud) {
    _cloud = cloud;
  }


  public Image getImageOrIcon() {
    return _imageOrIcon;
  }


  public void setImageOrIcon(Image imageOrIcon) {
    _imageOrIcon = imageOrIcon;
  }


  public Image getLogo() {
    return _logo;
  }


  public void setLogo(Image logo) {
    _logo = logo;
  }


  public TextInput getTexInput() {
    return _texInput;
  }


  public void setTexInput(TextInput texInput) {
    _texInput = texInput;
  }


  public Set<Integer> getSkipHours() {
    return _skipHours;
  }


  public void setSkipHours(Set<Integer> skipHours) {
    _skipHours = skipHours;
  }


  public Set<Day> getSkipDays() {
    return _skipDays;
  }


  public void setSkipDays(Set<Day> skipDays) {
    _skipDays = skipDays;
  }


  @Override
  public void validate(FeedFormat format) throws ValidationException {
//    if(CollectionUtils.isEmpty(_items)){
//      throw new ValidationException("Channel: You should have at least 1 item");
//    }
//    
//    for(Item item : _items){
//      CommonUtils.validateNotNull("Channel: All item should not be null", item);
//      item.validate(format);
//    }
//    
//    
//    CommonUtils.validateNotNull("Channel: Title, Link and Description should not be null", _title, _link, _description);
//  
//    if(_category != null){
//      for(CategorySubject c: _category){
//        c.validate(format);
//      }
//    }
//    if(_cloud != null){
//      _cloud.validate(format);
//    }
//    if(_image != null){
//      _image.validate(format);
//    }
//    if(_texInput != null){
//      _texInput.validate(format);
//    }    
//    
//    
//    if(_atomId != null){
//      _atomId.validate(format);
//    }
//    if(_atomLinks != null){
//      for(Link link : _atomLinks){
//        if(link != null){
//          link.validate(format);
//        }
//      }
//    }
  }
  
}