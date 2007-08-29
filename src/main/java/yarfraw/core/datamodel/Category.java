package yarfraw.core.datamodel;

import yarfraw.utils.CommonUtils;


/**
 * Specify one or more categories that the channel belongs to. 
 * Follows the same rules as the <item>-level category element.
 * <p/> 
 * More info:  http://cyber.law.harvard.edu/rss/rss.html#syndic8
 * 
 * <p/>
 * for Rss 1.0 format, the &lt;dc:subject> element of RDF's extension module is mapped to this field.
 * <br/>
 * see http://web.resource.org/rss/1.0/ - 'Dublin Core'
 * <p/>
 * for Atom 1.0 format, the field also mapps to &lt;category> element. However, the 'domain' field
 * is mapped to the 'scheme' attribute. 
 * <br/>
 * see http://web.resource.org/rss/1.0/ - 'Dublin Core'
 *  
 * @author jliang
 *
 */
public class Category extends AbstractBaseObject{
  private String _category;
  private String _domainOrScheme;
  private AtomAttributes _atomAttributes;
  public Category() {}
  public static Category create(){
    return new Category();
  }
  public Category(String category) {
    super();
    _category = category;
  }
  public Category(String category, String domainOrScheme) {
    super();
    _category = category;
    _domainOrScheme = domainOrScheme;
  }
  public String getCategory() {
    return _category;
  }
  public Category setCategory(String category) {
    _category = category;
    return this;
  }
  /**
   * a string that identifies a categorization taxonomy. 
   * <br/>
   * The value of the element is a forward-slash-separated string that identifies 
   * a hierarchic location in the indicated taxonomy. Processors may establish 
   * conventions for the interpretation of categories. Two examples are provided below:
   * <br/>
   * &lt;category>Grateful Dead&lt;/category>
   * <br/>
   * &lt;category domain="http://www.fool.com/cusips">MSFT&lt;/category>
   * <p/>
   * for Atom 1.0 format, the field also mapps to &lt;category> element. However, the 'domain' field
   * is mapped to the 'scheme' attribute. 
   * <br/>
   */
  public String getDomainOrScheme() {
    return _domainOrScheme;
  }
  /**
   * a string that identifies a categorization taxonomy. 
   * <br/>
   * The value of the element is a forward-slash-separated string that identifies 
   * a hierarchic location in the indicated taxonomy. Processors may establish 
   * conventions for the interpretation of categories. Two examples are provided below:
   * <br/>
   * &lt;category>Grateful Dead&lt;/category>
   * <br/>
   * &lt;category domain="http://www.fool.com/cusips">MSFT&lt;/category>
   * <p/>
   * for Atom 1.0 format, the field also mapps to &lt;category> element. However, the 'domain' field
   * is mapped to the 'scheme' attribute. 
   * <br/>
   */
  public Category setDomainOrScheme(String domainOrScheme) {
    _domainOrScheme = domainOrScheme;
    return this;
  }
  
  public AtomAttributes getAtomAttributes() {
    return _atomAttributes;
  }
  public Category setAtomAttributes(AtomAttributes atomAttributes) {
    _atomAttributes = atomAttributes;
    return this;
  }
  
  @Override
  public void validate(FeedFormat format) throws ValidationException {
    CommonUtils.validateNotNull(_category, "Category: Category value should not be null");
  }
}