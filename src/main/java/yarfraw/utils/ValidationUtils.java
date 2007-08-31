package yarfraw.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import yarfraw.core.datamodel.ValidationException;

public class ValidationUtils{
  private ValidationUtils(){}
  private static final Pattern EMAIL = Pattern.compile(
          "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$",
          Pattern.CASE_INSENSITIVE);
  public static void validateNotNull(String message, Object... o) throws ValidationException{
    if(!ArrayUtils.isEmpty(o)){
      for(Object oo : o){
        if(oo== null){
          throw new ValidationException(message);
        }
      }
    }
  }
  
  public static void validateUri(String message, String... uri) throws ValidationException{
    if(!ArrayUtils.isEmpty(uri)){
      for(String s : uri){
        if(s != null){
          try {
            @SuppressWarnings("unused")
            URI u  = new URI(s);
          } catch (URISyntaxException e) {
            throw new ValidationException(message, e);
          }
        }
      }
    }
  }
  /**
   * Validation methods for emails.
   * 
   * @param message - A custom message for the ValidationException
   * @param emails 
   * @throws ValidationException - if any of the input emails are not valid.
   */
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