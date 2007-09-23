package yarfraw.coverage;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.utils.reader.FeedReaderUtils;

public class RemoteTestSlow extends TestCase{
  private static final Log LOG = LogFactory.getLog(RemoteTestSlow.class);
  
  @Test
  public void testHttpRequestHeaderSupport() throws Exception{
    GetMethod get = new GetMethod("http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml");
    FeedReader r = new FeedReader(get);
    r.readChannel();
  }
  

  @Test
  public void testRead3() throws Exception{  
    File f1 = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/digg.xml").toURI());
    File f2 = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/reddit.xml").toURI());
    File f3 = new File(Thread.currentThread().getContextClassLoader().getResource("yarfraw/theserverside-rss2.xml").toURI());
    List<ChannelFeed> channels = FeedReaderUtils.readAll(FeedFormat.RSS20, f1, f2, f3);
    assertEquals(3, channels.size());
  }
  @Test
  public void testConcurrentRead() throws Exception{
    System.out.println("I am a very slowwwww unit test! use -Denv=dev to skip me, see BUILD.txt");
    HttpURL[] urls = new HttpURL[]{
            new HttpURL("http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml"),
            new HttpURL("http://bensbargains.net/rss.xml/0"),
            new HttpURL("http://rss.cnn.com/rss/money_topstories.rss"),
            new HttpURL("http://www.perezhilton.com/index.xml"),
            new HttpURL("http://www.csmonitor.com/rss/top.rss"),
            new HttpURL("http://www.comedycentral.com/rss/colbertvideos.jhtml"),
            new HttpURL("http://feeds.feedburner.com/CoolTools"),
            new HttpURL("http://couponbar.coupons.com/rss.asp"),
            new HttpURL("http://www.gotapex.com/deals/daily/RSS2/"),
            new HttpURL("http://www.comedycentral.com/rss/tdsvideos.jhtml"),
            new HttpURL("http://rss.dealcatcher.com/rss.xml"),
            new HttpURL("http://content.dealnews.com/dealnews/rss/todays-edition.xml"), //Rss 0.9x
            new HttpURL("http://www.defamer.com/index.xml"),
            new HttpURL("http://digg.com/rss/index.xml"),
            new HttpURL("http://digg.com/rss/containervideos.xml"),
            new HttpURL("http://www.ebates.com/customer/feed/offer-feed.xml"),
            new HttpURL("http://www.ebates.com/customer/feed/merchant-feed.xml"),
            new HttpURL("http://sports.espn.go.com/espn/rss/news"),
            new HttpURL("http://www.fatwallet.com/rssfeed.php"),
            new HttpURL("http://www.gotapex.com/coupons/expiring/RSS2/"),
            new HttpURL("http://www.fool.com/About/headlines/rss_headlines.asp"),
            new HttpURL("http://www.freakonomics.com/blog/feed/"),
            new HttpURL("http://www.gamespot.com/misc/podcast/onthespot.xml"),
            new HttpURL("http://rss.gamespot.com/misc/rss/gamespot_updates_reviews.xml"),
            new HttpURL("http://www.gametrailers.com/rss/newest.xml"),
            new HttpURL("http://www.gawker.com/index.xml"),
            new HttpURL("http://gladwell.typepad.com/gladwellcom/atom.xml"),
            new HttpURL("http://news.google.com/?output=rss"),
            new HttpURL("http://www.google.com/news?output%5Cx3datom=&output=rss"),
            new HttpURL("http://feeds.feedburner.com/GoogleOperatingSystem"),
            new HttpURL("http://video.google.com/videofeed?type=top100new"),
            new HttpURL("http://video.google.com/videofeed?type=search&q=user%3A%22Google+engEDU%22&so=1&num=20&output=rss"),
            new HttpURL("http://www.gotapex.com/external.php?type=RSS2"),
            new HttpURL("http://www.hot-deals.org/rss/xml/"),
            new HttpURL("http://www.iwillteachyoutoberich.com/atom.xml"),
            new HttpURL("http://www.infoq.com/rss/rss.action?token=QXMumAzp1ulX0kVEl8sk5uT73Rj1yi4N"),
            new HttpURL("http://www.innovationontherun.com/feed"),
            new HttpURL("http://www.kottke.org/remainder/index.rdf"),
            new HttpURL("http://itre.cis.upenn.edu/~myl/languagelog/index.rdf"),
            new HttpURL("http://www.languagehat.com/index.rdf"),
            new HttpURL("http://leoville.tv/podcasts/kfi.xml"),
            new HttpURL("http://lifehacker.com/index.xml"),
            new HttpURL("http://www.makezine.com/blog/index.xml"),
            new HttpURL("http://www.marginalrevolution.com/marginalrevolution/index.rdf"),
            new HttpURL("http://www.marketwatch.com/rss/topstories"),
            new HttpURL("http://feeds.feedburner.com/mysimon/"),
            new HttpURL("http://gafter.blogspot.com/feeds/posts/default"),
            new HttpURL("http://www.npr.org/rss/podcast.php?id=500001")
    };
    List<ChannelFeed> channels = FeedReaderUtils.readAll(Executors.newFixedThreadPool(10), urls );
    
    int i =0;
    for(HttpURL url : urls){
      ChannelFeed c = channels.get(i);
      if(c != null){
        LOG.info(c.getTitle());
        assertNotNull(c.getTitle());
      }else{
        LOG.error("Yarfraw failed to parse one of the channels: "+url);
        System.out.println("Yarfraw failed to parse one of the channels: "+url);
      }
      i++;
    }
  }
  

  @Test
  public void testRemoteReadAtom10() throws Exception{
    try {
      FeedReader reader = new FeedReader(new HttpURL("http://www.google.com/news?output%5Cx3datom=&output=atom"));
      assertTrue("isRemoteRead", reader.isRemoteRead());
      if(reader.getFormat() != FeedFormat.UNKNOWN){
        System.out.println("Google news' atom feed should be an unsupported atom 0.3 format");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  

  @Test
  public void testRemoteRead() throws Exception{  
    
    try{
      FeedReader reader = new FeedReader(new HttpURL("http://digg.com/rss/index.xml"));
      assertTrue(reader.isRemoteRead());
      ChannelFeed c = reader.readChannel();
      //this test can be indeterministic because it requires a network connection 
      //if there no exception thrown, then we should have the channel read
      assertTrue("Remote read failed", c.getTitle() != null);
    }catch (Exception e) {
      System.out.println("Failed to read from a remote url, this test requires a network connection");
      e.printStackTrace();
    }
  }
  
  @Test
  public void testRemoteRead2() throws Exception{
    FeedReader reader = null;
    try{
      reader = new FeedReader(new HttpURL("http://digg.com/rss/index.xml"));
      assertTrue(reader.isRemoteRead());
      HttpClientParams params = new HttpClientParams();
      params.setSoTimeout((int)DateUtils.MILLIS_PER_MINUTE);
      reader.setHttpClientParams(params);
      ChannelFeed c = reader.readChannel();
      //this test can be indeterministic because it requires a network connection 
      //if there no exception thrown, then we should have the channel read
      assertTrue("Remote read failed", c.getTitle() != null);
    }catch (Exception e) {
      System.out.println("Failed to read from a remote url, this test requires a network connection");
      e.printStackTrace();
    }
    
    
    try{
      reader = new FeedReader(new HttpURL("http://www.twit.tv/node/feed"));
      assertTrue(reader.isRemoteRead());
      HttpClientParams params = new HttpClientParams();
      params.setSoTimeout((int)DateUtils.MILLIS_PER_MINUTE);
      reader.setHttpClientParams(params);
      ChannelFeed c = reader.readChannel();
      //this test can be indeterministic because it requires a network connection 
      //if there no exception thrown, then we should have the channel read
      assertTrue("Remote read failed", c.getTitle() != null);
    }catch (Exception e) {
      System.out.println("Failed to read from a remote url, this test requires a network connection");
      e.printStackTrace();
    }
    
    try {
      HttpClientParams params = new HttpClientParams();
      params.setSoTimeout(20);
      reader = new FeedReader(new HttpURL("http://nowhere.com"));
      reader.readChannel();
      fail("should failed");
    }
    catch (Exception e) {
      // success;
    }
  }
}