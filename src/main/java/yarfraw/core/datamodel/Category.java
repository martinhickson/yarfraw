package yarfraw.core.datamodel;

import yarfraw.rss20.utils.Utils;


/**
 * Specify one or more categories that the channel belongs to. 
 * Follows the same rules as the <item>-level {@link http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt category} element. 
 * More info: {@link http://cyber.law.harvard.edu/rss/rss.html#syndic8}
 * 
 * @author jliang
 *
 */
public class Category extends AbstractBaseObject{
  private String _category;
  private String _domain;
  public Category() {}
  public static Category create(){
    return new Category();
  }
  public Category(String category) {
    super();
    _category = category;
  }
  public Category(String category, String domain) {
    super();
    _category = category;
    _domain = domain;
  }
  public String getCategory() {
    return _category;
  }
  public void setCategory(String category) {
    _category = category;
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
   */
  public String getDomain() {
    return _domain;
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
   */
  public Category setDomain(String domain) {
    _domain = domain;
    return this;
  }
  
  @Override
  public void validate(ValidationLevel level) throws ValidationException {
    Utils.validateNotNull(_category, "Category: Category value should not be null");
  }
}