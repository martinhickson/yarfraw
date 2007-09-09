package yarfraw.extension;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.admin.elements.AdminExtension;
import yarfraw.generated.admin.elements.AdminType;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class AdminTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    AdminExtension ext = new AdminExtension();
    AdminType reportTo = new AdminType();
    reportTo.setResource("http://somewhere");
    ext.setErrorReportsTo(reportTo);
    AdminType agent = new AdminType();
    agent.setResource("http://agent");
    ext.setGeneratorAgent(agent);
    
    File f = new File("testTmpOutput/admin.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toAdminAtomElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toAdminAtomElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    AdminExtension ext2 = ExtensionUtils.extractAdminExtension(c2.getOtherElements());
    
    assertTrue(EqualsBuilder.reflectionEquals(ext2.getErrorReportsTo(), ext.getErrorReportsTo()));
    assertTrue(EqualsBuilder.reflectionEquals(ext2.getGeneratorAgent(), ext.getGeneratorAgent()));
  }
}