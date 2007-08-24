package yarfraw.core.datamodel;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * An abstract base object for the core data model
 */
abstract class AbstractBaseObject{
  
  @Override
  public String toString(){
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  @Override
  public boolean equals(Object other){
    return EqualsBuilder.reflectionEquals(this, other);
  }
  @Override
  public int hashCode(){
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public abstract void validate(FeedFormat format) throws ValidationException;
}