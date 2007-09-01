package yarfraw.coverage;

import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Document;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.utils.CommonUtils;
import yarfraw.utils.DOMSerializer;
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
    String d1 = CommonUtils.getDateAsISO8601String(new Date(time));
    String d2 = CommonUtils.getDateAsISO8601String(CommonUtils.tryParseDate(d1));
    assertEquals(d1, d2);
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
}