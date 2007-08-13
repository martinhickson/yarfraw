package yarfraw.rss20.datamodel;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class AbstractBaseObject{
  
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
  
  public void validate() throws ValidationException{
    validate(ValidationLevel.NORMAL);
  }
  public abstract void validate(ValidationLevel level) throws ValidationException;
}