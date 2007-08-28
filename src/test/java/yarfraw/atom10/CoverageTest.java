package yarfraw.atom10;

import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.AtomId;
import yarfraw.core.datamodel.FeedFormat;

public class CoverageTest extends TestCase{

  @Test
  public void testAtom() throws Exception{
    AtomId id = new AtomId();
    id.setAtomUri("http://uri");
    id.setLang(Locale.ENGLISH);
    id.validate(FeedFormat.ATOM10);
    
    try {
      id.setAtomUri(" ");
      id.validate(FeedFormat.ATOM10);
      fail("this is supposed to fail");
    }
    catch (Exception e) {
      // success
    }
  }
}