package yarfraw.utils;

import static yarfraw.utils.CommonConstants.MIN_PER_DAY;
import static yarfraw.utils.CommonConstants.MIN_PER_MONTH;
import static yarfraw.utils.CommonConstants.MIN_PER_WEEK;
import static yarfraw.utils.CommonConstants.MIN_PER_YEAR;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.UpdatePeriodEnum;

/**
 * Utilities methods.
 * 
 * @author jliang
 *
 */
public class CommonUtils{
  private static final Log LOG = LogFactory.getLog(CommonUtils.class);
  
  public static final String RSS20_JAXB_CONTEXT = "yarfraw.generated.rss20.elements";
  public static final String RSS10_JAXB_CONTEXT = "yarfraw.generated.rss10.elements";
  public static final String ATOM10_JAXB_CONTEXT = "yarfraw.generated.atom10.elements";
  
  /////////////////////DATE PARSING///////////////////////////////////
  private static final String RFC822DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
  private static final String ISO8601DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
  
  //6 level of ISO 8601 Date
  public static final SimpleDateFormat ISO_8601_LVL1 = new SimpleDateFormat("yyyy");
  public static final SimpleDateFormat ISO_8601_LVL2 = new SimpleDateFormat("yyyy-MM");
  public static final SimpleDateFormat ISO_8601_LVL3 = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat ISO_8601_LVL4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
  public static final SimpleDateFormat ISO_8601_LVL5 = new SimpleDateFormat(ISO8601DATE_PATTERN);
  public static final SimpleDateFormat ISO_8601_LVL6 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sZ");
  public static final SimpleDateFormat RFC_822_DATE_FORMAT = new SimpleDateFormat(RFC822DATE_PATTERN);
  
  
  
  public static final SimpleDateFormat[] NON_ISO8601_FORMAT = new SimpleDateFormat[]{
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z"),
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm zzzz"),
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz"),
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzzz"),
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    RFC_822_DATE_FORMAT,
    new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss z")
  };
  

  private CommonUtils(){}

  /**
   * Search through the input element list and return the first element that matches
   * both input the namespaceURI and the localName.
   * 
   * @param namespaceURI - namespaceURI of the element to be search for
   * @param localName - localName of the element
   * @return - null if no matching element is found,
   * the matching element otherwise.
   */
  public static Element getElementByNS(List<Element> elements, String namespaceURI, String localName){
    if(CollectionUtils.isEmpty(elements)){
      return null;
    }
    for(Element e : elements){
      if(ObjectUtils.equals(localName, e.getLocalName()) && 
              ObjectUtils.equals(namespaceURI, CommonUtils.emptyIfNull(e.getNamespaceURI()))){
        return e;
      }
    }    
    return null;
  }

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
    String result = ISO_8601_LVL5.format(date);
    //convert YYYYMMDDTHH:mm:ss+HH00 into YYYYMMDDTHH:mm:ss+HH:00
    //- note the added colon for the Timezone
    result = result.substring(0, result.length()-2)
      + ":" + result.substring(result.length()-2);
    return result;
  }

  /**
   * Parse a date string using both ISO and RFC formats.
   * 
   * @param dateString
   * @return
   */
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
    if(ret == null){
      LOG.warn("Unparsable dateString "+dateString+", returning null");
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
      return ISO_8601_LVL1.parse(dateString);
    }else if(dateString.length() == 7){
      return ISO_8601_LVL2.parse(dateString);
    }else if(dateString.length() == 10){
      return ISO_8601_LVL3.parse(dateString);
    }else if(dateString.length() == 22){
      return ISO_8601_LVL4.parse(removeLast(dateString, ':'));
    }else if(dateString.length() == 25){
      return ISO_8601_LVL5.parse(removeLast(dateString, ':'));
    }else if(dateString.length() == 28){
      return ISO_8601_LVL6.parse(removeLast(dateString, ':'));
    }else{
      throw new YarfrawException("Invalid ISO 8601 Date format: "+dateString);
    }
  }
  
/////////////////////DATE PARSING///////////////////////////////////
  
  public static boolean same(QName qn1, QName qn2){
    return ObjectUtils.equals(qn1, qn2);
  }

  
  /**
   * Return empty string if input is null.
   * @param str
   * @return
   */
  public static String emptyIfNull(String str){
    return str == null?StringUtils.EMPTY:str;
  }
  
  /**
   * calculate the ttl value from updatePeriod and updateFrequency
   * @return null if anything unexpcted occurs
   */
  public static Integer calculateTtl(UpdatePeriodEnum updatePeriod, BigInteger updateFrequency){
    if(updatePeriod == null && updateFrequency == null){
      return null;
    }
    int freq = updateFrequency == null ? 1: updateFrequency.intValue();
    if(updatePeriod == UpdatePeriodEnum.HOURLY){
      return Math.max(1, 60/freq);
    }else if(updatePeriod == UpdatePeriodEnum.DAILY){
      return Math.max(1, MIN_PER_DAY/freq);
    }else if(updatePeriod == UpdatePeriodEnum.MONTHLY){
      return Math.max(1, MIN_PER_MONTH/freq);
    }else if(updatePeriod == UpdatePeriodEnum.WEEKLY){
      return Math.max(1, MIN_PER_WEEK/freq);
    }else if(updatePeriod == UpdatePeriodEnum.YEARLY){
      return Math.max(1, MIN_PER_YEAR/freq);
    }else{
      return null;
    }
  }
}
