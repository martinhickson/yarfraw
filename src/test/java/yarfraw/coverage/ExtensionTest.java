package yarfraw.coverage;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.googlebase.elements.DateTimeRangeType;
import yarfraw.generated.googlebase.elements.GoogleBaseExtension;
import yarfraw.generated.googlebase.elements.PriceTypeEnumeration;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class ExtensionTest extends TestCase{


  @Test
  public void testGoogleBase() throws Exception{
    ChannelFeed c = new ChannelFeed();
    c.setTitle("title");
    c.setLang(Locale.ENGLISH);
    ItemEntry item = new ItemEntry();
    item.setTitle("Google's 2005 Halloween Fright Fest");
    item.setDescriptionOrSummary("Come face to face with your most horrific fears." +
        " This year's Halloween fest will be sure to scare your socks off," +
        " complete with a real haunted Googleplex.");
    GoogleBaseExtension gbase = new GoogleBaseExtension();
    DatatypeFactory factory = DatatypeFactory.newInstance();
    DateTimeRangeType dateRange = new DateTimeRangeType();
    XMLGregorianCalendar cal = factory.newXMLGregorianCalendar(new GregorianCalendar());
    cal.setYear(2006);
    cal.setMonth(10);
    cal.setDay(31);
    cal.setHour(18);
    dateRange.setStart(cal);
    gbase.setEventDateRange(dateRange);
    
    XMLGregorianCalendar cal2 = factory.newXMLGregorianCalendar(new GregorianCalendar());
    cal.setYear(2006);
    cal.setMonth(12);
    cal.setDay(10);
    gbase.setExpirationDate(cal2);
    
    item.setUid("1");
    
    gbase.getImageLink().add("http://www.example.com/image1.jpg");
    
    item.addLink("http://www.example.com/item1-info-page.html");
    gbase.setLocation("1600 Amphitheatre Parkway, Mountain View, CA, 94043");
    gbase.setPrice("10");
    gbase.setPriceType(PriceTypeEnumeration.STARTING);
    gbase.setQuantity(new BigInteger("100"));
    item.getOtherElements().addAll(ExtensionUtils.toGoogleBaseElements(gbase));
    c.addItem(item);
    //well, you get the idea.
    FeedWriter w = new FeedWriter("testTmpOutput/gbase.xml");
    w.writeChannel(c);
  }
  
  
}