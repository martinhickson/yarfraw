package yarfraw.coverage;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ValidationException;
import yarfraw.generated.itunes.elements.ItunesCategoryType;
import yarfraw.generated.itunes.elements.ItunesExtension;
import yarfraw.generated.itunes.elements.ItunesImageType;
import yarfraw.generated.itunes.elements.ItunesOwnerType;
import yarfraw.generated.mrss.elements.MrssCopyrightType;
import yarfraw.generated.mrss.elements.MrssExtension;
import yarfraw.io.FeedReader;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.DOMSerializer;
import yarfraw.utils.FeedFormatDetector;
import yarfraw.utils.ValidationUtils;
import yarfraw.utils.XMLUtils;
import yarfraw.utils.extension.ExtensionUtils;

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
    
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer trans =  factory.newTransformer();
    Source source = new StreamSource(new StringReader("<div xmlns=\"http://www.w3.org/1999/xhtml\">"+
        "<p><i>[Update: The Atom draft is finished.]</i></p>"+
        "</div>"));
    StringWriter writer = new StringWriter();
    Result result = new StreamResult(writer);
    trans.transform(source, result);
    
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
  
  @Test
  public void testItunes() throws Exception{
    ItunesExtension test = new ItunesExtension();
    test.setAuthor("itune author");
    test.setBlock("itunes block");
    test.setDuration("itune duration");
    ItunesCategoryType cat = new ItunesCategoryType();
    cat.setText("cat");
    test.getCategory().add(cat);
    //<duration> is not allowed under channel, read the specs
    //http://www.apple.com/itunes/store/podcaststechspecs.html
//    test.setDuration("10:10:20");
    test.setExplicit("yes");
    test.setKeywords("some,key, words");
    ItunesImageType image = new ItunesImageType();
    image.setHref("http://someurl");
    image.setRel("ref");
    test.setImage(image);
    ItunesOwnerType owner = new ItunesOwnerType();
    owner.setEmail("owner@email.com");
    owner.setName("pwner");
    test.setOwner(owner);
    test.setSubtitle("subtitle");
    test.setSummary("summary");
    List<Element> list = ExtensionUtils.toItunesElements(test);
    ItunesExtension test2 = ExtensionUtils.extractItunesExtension(list);
    assertEquals(test.getAuthor(), test2.getAuthor());
    assertEquals(test.getBlock(), test2.getBlock());
    assertEquals(test.getCategory().size(), test2.getCategory().size());
    //FIXME: i dont know why they do not equal using reflectionEquals
    for(int i =0; i<test.getCategory().size() ; i++ ){
      assertEquals(ToStringBuilder.reflectionToString(test.getCategory().get(i), ToStringStyle.SIMPLE_STYLE), 
              ToStringBuilder.reflectionToString(test2.getCategory().get(i), ToStringStyle.SIMPLE_STYLE));
    }
    
    assertEquals(test.getDuration(), test2.getDuration());
    assertEquals(test.getExplicit(), test2.getExplicit());
    test.getImage().setValue(""); //replace null with empty string
    assertEquals(ToStringBuilder.reflectionToString(test.getImage(), ToStringStyle.SIMPLE_STYLE), 
            ToStringBuilder.reflectionToString(test2.getImage(), ToStringStyle.SIMPLE_STYLE));
    assertEquals(test.getKeywords(), test2.getKeywords());
    assertEquals(ToStringBuilder.reflectionToString(test.getOwner(), ToStringStyle.SIMPLE_STYLE), 
            ToStringBuilder.reflectionToString(test2.getOwner(), ToStringStyle.SIMPLE_STYLE));
    assertEquals(test.getSubtitle(), test2.getSubtitle());
    assertEquals(test.getSummary(), test2.getSummary());
  }
  
  @Test
  public void testMrss() throws Exception{
    MrssExtension test = new MrssExtension();
    MrssCopyrightType rights = new MrssCopyrightType();
    rights.setUrl("http://someurl");
    rights.setValue("copy");
    test.setCopyright(rights);
    List<Element> list = ExtensionUtils.toMrssElements(test);
    MrssExtension test2 = ExtensionUtils.extractMrssExtension(list);
//    System.out.println(ToStringBuilder.reflectionToString(test.getCopyright()));
//    System.out.println(ToStringBuilder.reflectionToString(test2.getCopyright()));
//    assertEquals(test, test2);
  }
}