package yarfraw.rss20.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import yarfraw.rss20.datamodel.ValidationException;

/**
 * Utilities methods.
 * 
 * @author jliang
 *
 */
public class Utils{
  
  private Utils(){}
  
  public static boolean same(QName qn1, QName qn2){
    return EqualsBuilder.reflectionEquals(qn1, qn2);
  }
  
  public static void validateNotNull(String message, Object... o) throws ValidationException{
    if(!ArrayUtils.isEmpty(o)){
      for(Object oo : o){
        if(oo== null){
          throw new ValidationException(message);
        }
      }
    }
  }
  public static final String DATE_FORMAT_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
  public static final String JAXB_CONTEXT = "generated";
  private static final Pattern EMAIL = Pattern.compile(
          "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$",
          Pattern.CASE_INSENSITIVE);
  public static void validateEmails(String message, String... emails) throws ValidationException{
    if(!ArrayUtils.isEmpty(emails)){
      for(String email : emails){
        if(email != null){
          Matcher m = EMAIL.matcher(email);
          if(!m.matches()){
            throw new ValidationException(message);
          }
        }
      }
    }
  }
}