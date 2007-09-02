package yarfraw.coverage;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.w3c.dom.Document;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ValidationException;
import yarfraw.io.FeedReader;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.DOMSerializer;
import yarfraw.utils.FeedFormatDetector;
import yarfraw.utils.ValidationUtils;
import yarfraw.utils.XMLUtils;

public class UtilsTest extends TestCase{
//  private static final Log LOG = LogFactory.getLog(UtilsTest.class);
  @Test
  public void testXml() throws Exception{
    DOMSerializer s = new DOMSerializer();
    Document doc = XMLUtils.parseXml("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
        "<p><i>[Update: The Atom draft is finished.]</i></p>"+
        "</div>", false, true);
    StringWriter w = new StringWriter();
    s.serialize(doc, w);
    Document doc2 = XMLUtils.parseXml(w.toString(), false, true);
    assertNotNull(doc2);
  }
  
  @Test
  public void testDateParsing() throws Exception{
    long time = System.currentTimeMillis();
    String d1 = CommonUtils.formatDate(new Date(time), FeedFormat.ATOM10);
    String d2 = CommonUtils.formatDate(CommonUtils.tryParseDate(d1), FeedFormat.ATOM10);
    assertEquals(d1, d2);
    Date date = CommonUtils.tryParseDate("2003-12-13T08:29:29-04:00");
    Date date2 = CommonUtils.tryParseDate("2003-12-13T08:29:29");
    Date date3 = CommonUtils.tryParseDate("2003-12-13");
    
    assertNotNull(date);
    assertNotNull(date2);
    assertNotNull(date3);
  }
  @Test
  public void testNewAtom() throws Exception{
    FeedReader reader = new FeedReader(Thread.currentThread()
            .getContextClassLoader().getResource("yarfraw/atom10/atom10c.xml").toURI(), 
            FeedFormat.ATOM10);
    reader.readChannel(new ValidationEventHandler(){
      public boolean handleEvent(ValidationEvent event) {
        System.out.println(event);
        return true;
      }
    });
  }
  
  @Test
  public void testFormatDetection() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/digg.xml");
      assertEquals(FeedFormat.RSS20, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  @Test
  public void testFormatDetection2() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/rss10/rdf.xml");
      assertEquals(FeedFormat.RSS10, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  
  @Test
  public void testFormatDetection3() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/atom10/atom10.xml");
      assertEquals(FeedFormat.ATOM10, FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s);
    }
  }
  
  public void testFormatDetection4() throws Exception{
    InputStream s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/format/f1.xml");
      assertTrue(FeedFormat.RSS20 == FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s); //dont forget to close you io streams!!
    }
    
    s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/format/f1.xml");
      assertTrue(FeedFormat.UNKNOWN == FeedFormatDetector.getFormat(s, true));
    }finally{
      IOUtils.closeQuietly(s); //dont forget to close you io streams!!
    }
    
    s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/format/f2.xml");
      assertTrue(FeedFormat.RSS20 == FeedFormatDetector.getFormat(s, true));
    }finally{
      IOUtils.closeQuietly(s); //dont forget to close you io streams!!
    }
    
    s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/format/f2.xml");
      assertTrue(FeedFormat.RSS20 == FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s); //dont forget to close you io streams!!
    } 
    
    s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/format/f3.xml");
      assertTrue(FeedFormat.RSS20 == FeedFormatDetector.getFormat(s));
    }finally{
      IOUtils.closeQuietly(s); //dont forget to close you io streams!!
    }
    
    s = null;
    try {
      s = Thread.currentThread().getContextClassLoader().getResourceAsStream("yarfraw/format/f3.xml");
      assertTrue(FeedFormat.UNKNOWN == FeedFormatDetector.getFormat(s, true));
    }finally{
      IOUtils.closeQuietly(s); //dont forget to close you io streams!!
    }
  }
  
  @Test
  public void testValidation() throws MalformedURLException, URISyntaxException{
    try {
      ValidationUtils.validateEmails("bad", "bad");
      fail("Expecting validation error");
    } catch (ValidationException e) {
      //success
    }
  }
}