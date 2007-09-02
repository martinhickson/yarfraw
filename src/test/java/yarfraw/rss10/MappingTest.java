package yarfraw.rss10;

import junit.framework.TestCase;

import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.mapping.backward.impl.ToChannelRss10Impl;
import yarfraw.mapping.forward.impl.ToRss10ChannelImpl;

/**
 * Some unit tests.
 * 
 * @author jliang
 *
 */
public class MappingTest extends TestCase{
    
  @Test
  public void testMapping() throws Exception{
    
    ChannelFeed c = BuilderTest.buildChannel();
        
    RDF rdf = ToRss10ChannelImpl.getInstance().execute(c);

    ChannelFeed c2 =  ToChannelRss10Impl.getInstance().execute(rdf);
    c.getTexInput().setResource(c.getTexInput().getAbout());
    c.getImageOrIcon().setResource(c.getImageOrIcon().getAbout());
    assertEquals(c, c2);
    
  }
 
}
