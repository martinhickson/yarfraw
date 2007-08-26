package yarfraw.atom10;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;

public class ParserTest extends TestCase{

  @Test
  public void testBuild() throws Exception{

    Channel ch = BuilderTest.buildChannel();
    File f = File.createTempFile("test", ".xml");
    FeedWriter writer = new FeedWriter(f);
    writer.setFormat(FeedFormat.ATOM10);
    writer.writeChannel(ch);
                                          
    FeedReader r = new FeedReader(f);
    
  }
}