package yarfraw.rss20;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.Cloud;
import yarfraw.core.datamodel.FeedFormat;

public class CoverageTest extends TestCase{

  @Test
  public void testRss20() throws Exception{
    Cloud c = new Cloud();
    try {
      c.validate(FeedFormat.RSS20);
      fail("this should fail");
    }
    catch (Exception e) {
      //success
    }
    
    c = new Cloud("domain", "123", "abc", "blah", "soap");
    
    try {
      c.setProtocol("blah");
      c.validate(FeedFormat.RSS20);
      fail("this should fail");
    }
    catch (Exception e) {
      //success
    }
  }
}