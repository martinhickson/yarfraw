package yarfraw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import yarfraw.core.datamodel.ValidationException;
import yarfraw.core.datamodel.YarfrawException;

/**
 * Utilities methods.
 * 
 * @author jliang
 *
 */
public class CommonUtils{
  
  private CommonUtils(){}
  
  public static final String RFC822DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
  public static final String ISO8601DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
  
  //6 level of ISO 8601 Date
  public static final SimpleDateFormat LVL1 = new SimpleDateFormat("yyyy");
  public static final SimpleDateFormat LVL2 = new SimpleDateFormat("yyyy-MM");
  public static final SimpleDateFormat LVL3 = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat LVL4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
  public static final SimpleDateFormat LVL5 = new SimpleDateFormat(ISO8601DATE_PATTERN);
  public static final SimpleDateFormat LVL6 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sZ");

  public static final String RSS20_JAXB_CONTEXT = "yarfraw.generated.rss20.elements";
  public static final String RSS10_JAXB_CONTEXT = "yarfraw.generated.rss10.elements";
  public static final String ATOM10_JAXB_CONTEXT = "yarfraw.generated.atom10.elements";
  private static final Pattern EMAIL = Pattern.compile(
          "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$",
          Pattern.CASE_INSENSITIVE);
  public static final SimpleDateFormat RFC_FORMAT = new SimpleDateFormat(RFC822DATE_PATTERN);
  
  public static final SimpleDateFormat[] NON_ISO8601_FORMAT = new SimpleDateFormat[]{
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z"),
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm zzzz"),
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz"),
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzzz"),
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    RFC_FORMAT,
    new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss z")
  };
  
//  public static final SimpleDateFormat ISO_FORMAT = new SimpleDateFormat(ISO8601DATE_PATTERN);

  /**
   * Remove last occurrence of the character c in s
   */
  private static String removeLast(String s, char c){
    int idx = s.lastIndexOf(c);
    if(idx < 0){
      return s;//nothing to remove
    }
    return s.substring(0, idx)+ (idx==s.length()-1? StringUtils.EMPTY: s.substring(idx+1));
  }
  
  /**
   * Simple date format does not support ISO0861 date, so i have to do some hacking
   * <br/>
   * return null if date is null
   */
  public static String getDateAsISO8601String(Date date)
  { 
    if(date == null){
      return null;
    }
    String result = LVL5.format(date);
    //convert YYYYMMDDTHH:mm:ss+HH00 into YYYYMMDDTHH:mm:ss+HH:00
    //- note the added colon for the Timezone
    result = result.substring(0, result.length()-2)
      + ":" + result.substring(result.length()-2);
    return result;
  }

  public static Date tryParseDate(String dateString){
    Date ret = null;
    try {
      ret = tryParseISODate(dateString);
      return ret;
    } catch (Exception e) {
      for(SimpleDateFormat format : NON_ISO8601_FORMAT){
        try {
          ret = format.parse(dateString);
          return ret;
        } catch (Exception ee) {
          //keep trying
        }
      }
    }
    return ret;
  }
  /**
   * Try to parse a date string using different formatting string.
   * <br/>
   * return null if dateString is null
   * @throws YarfrawException 
   * @throws ParseException 
   */
  public static Date tryParseISODate(String dateString) throws YarfrawException, ParseException{
    
    if(dateString == null){
      return null;
    }else if(dateString.length() == 4){
      return LVL1.parse(dateString);
    }else if(dateString.length() == 7){
      return LVL2.parse(dateString);
    }else if(dateString.length() == 10){
      return LVL3.parse(dateString);
    }else if(dateString.length() == 22){
      return LVL4.parse(removeLast(dateString, ':'));
    }else if(dateString.length() == 25){
      return LVL5.parse(removeLast(dateString, ':'));
    }else if(dateString.length() == 28){
      return LVL6.parse(removeLast(dateString, ':'));
    }else{
      throw new YarfrawException("Invalid ISO 8601 Date format: "+dateString);
    }
  }
  
  public static boolean same(QName qn1, QName qn2){
    return ObjectUtils.equals(qn1, qn2);
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
  
  public static String emptyIfNull(String str){
    return str == null?StringUtils.EMPTY:str;
  }
}
