package yarfraw.mapping.forward.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;

class Utils{
  private Utils(){}
  
  /**
   * Gets the <code>emailOrText</code> field of the first person in the input list.
   * @param persons
   * @return
   */
  public static String getEmailOrText(List<Person> persons){
    String ret = null;
    if(CollectionUtils.isNotEmpty(persons)){
      Person p = persons.get(0);
      ret = p == null ? null : p.getEmailOrText();
    }
    return ret;
  }
  
  /**
   * Gets the <code>href</code> field of the first link in the input list.
   * @param links
   * @return
   */
  public static String getHrefLink(List<Link> links){
    String ret = null;
    if(CollectionUtils.isNotEmpty(links)){
      Link l = links.get(0);
      ret = l == null ? null : l.getHref();
    }
    return ret;
  }
}