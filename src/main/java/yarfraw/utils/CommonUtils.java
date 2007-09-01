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

import yarfraw.core.datamodel.FeedFormat;
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
  public static final String RFC822DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
  public static final String ISO8601DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
  public static final String ISO_8601_LVL1_PATTERN = "yyyy";
  public static final String ISO_8601_LVL2_PATTERN = "yyyy-MM";
  public static final String ISO_8601_LVL3_PATTERN = "yyyy-MM-dd";
  public static final String ISO_8601_LVL4_PATTERN = "yyyy-MM-dd'T'HH:mmZ";
  public static final String ISO_8601_LVL5_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
  public static final String ISO_8601_LVL6_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sZ";
  
  public static final String NON_STANDARD_PATTERN1 = "EEE, dd MMM yyyy HH:mm:ss z";
  public static final String NON_STANDARD_PATTERN2 = "EEE, dd MMM yyyy HH:mm zzzz";
  public static final String NON_STANDARD_PATTERN3 = "EEE, dd MMM yy HH:mm:ss z";
  public static final String NON_STANDARD_PATTERN4 = "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz";
  public static final String NON_STANDARD_PATTERN5 = "yyyy-MM-dd'T'HH:mm:sszzzz";
  public static final String NON_STANDARD_PATTERN6 = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String NON_STANDARD_PATTERN7 = "yyyy-MM-dd'T'HH:mm:ss.sZ";
  
  
  //6 level of ISO 8601 Date
  private static final SimpleDateFormat ISO_8601_LVL1 = new SimpleDateFormat(ISO_8601_LVL1_PATTERN);
  private static final SimpleDateFormat ISO_8601_LVL2 = new SimpleDateFormat(ISO_8601_LVL2_PATTERN);
  private static final SimpleDateFormat ISO_8601_LVL3 = new SimpleDateFormat(ISO_8601_LVL3_PATTERN);
  private static final SimpleDateFormat ISO_8601_LVL4 = new SimpleDateFormat(ISO_8601_LVL4_PATTERN);
  private static final SimpleDateFormat ISO_8601_LVL5 = new SimpleDateFormat(ISO_8601_LVL5_PATTERN);
  private static final SimpleDateFormat ISO_8601_LVL6 = new SimpleDateFormat(ISO_8601_LVL6_PATTERN);
  
  private static final SimpleDateFormat RFC822DATE_FORMAT = new SimpleDateFormat(RFC822DATE_PATTERN);

  private static final SimpleDateFormat[] NON_ISO8601_FORMAT = new SimpleDateFormat[]{
    RFC822DATE_FORMAT,
    new SimpleDateFormat(NON_STANDARD_PATTERN1),
    new SimpleDateFormat(NON_STANDARD_PATTERN2),
    new SimpleDateFormat(NON_STANDARD_PATTERN3),
    new SimpleDateFormat(NON_STANDARD_PATTERN4),
    new SimpleDateFormat(NON_STANDARD_PATTERN5),
    new SimpleDateFormat(NON_STANDARD_PATTERN6),
    new SimpleDateFormat(NON_STANDARD_PATTERN7)
  };
  

  private CommonUtils(){}

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
   * Parse a date string using both ISO and RFC formats and some non standard formats.
   * 
   * @param dateString
   * @return {@link Date} representation of the input string. null if
   * unable to parse input string.
   */
  public synchronized static Date tryParseDate(String dateString){
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
   * Format a {@link Date} object to string based on the input {@link FeedFormat}.
   * For Atom 1.0, it will be formatted as ISO8601 Level 5 string. <br/>
   * http://www.w3.org/TR/NOTE-datetime
   * <br/>
   * For both RSS 2.0 and RSS 1.0, it will be formatted as RFC 822 date string.
   * <br/>
   * http://www.faqs.org/rfcs/rfc822.html
   * @param date any date 
   * @param format any {@link FeedFormat}
   * @return null if either input is null. Formatted date string otherwise.
   */
  public static synchronized String formatDate(Date date, FeedFormat format){
    if(date == null || format == null){
      return null;
    }
    if(format == FeedFormat.ATOM10){
      return getDateAsISO8601String(date);
    }else if(format == FeedFormat.RSS10 || format == FeedFormat.RSS20){
      return RFC822DATE_FORMAT.format(date);
    }else{
      throw new IllegalArgumentException("Unsupported format: "+ format);
    }
  }
  
  /**
   * Simple date format does not support ISO0861 date, so i have to do some hacking
   * <br/>
   * return null if date is null
   */
  private static String getDateAsISO8601String(Date date)
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
   * Try to parse a date string using different formatting string.
   * <br/>
   * return null if dateString is null
   * @throws YarfrawException 
   * @throws ParseException 
   */
  private synchronized static Date tryParseISODate(String dateString) throws YarfrawException, ParseException{
    
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
