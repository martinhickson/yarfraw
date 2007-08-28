package yarfraw.coverage;

import java.util.List;
import java.util.concurrent.Executors;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.io.FeedReader;
import yarfraw.utils.reader.FeedReaderUtils;

public class UtilsTest extends TestCase{
  private static final Log LOG = LogFactory.getLog(UtilsTest.class);
  @Test
  public void testConcurrentRead() throws Exception{
    List<Channel> channels = FeedReaderUtils.readAll(Executors.newFixedThreadPool(10), 
            new HttpURL("http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml"),
            new HttpURL("http://bensbargains.net/rss.xml/0"),
            new HttpURL("http://rss.cnn.com/rss/money_topstories.rss"),
            new HttpURL("http://www.perezhilton.com/index.xml"),
            new HttpURL("http://www.csmonitor.com/rss/top.rss"),
            new HttpURL("http://www.comedycentral.com/rss/colbertvideos.jhtml"),
            new HttpURL("http://feeds.feedburner.com/CoolTools"),
            new HttpURL("http://couponbar.coupons.com/rss.asp"),
            new HttpURL("http://www.gotapex.com/deals/daily/RSS2/"),
            new HttpURL("http://couponbar.coupons.com/rss.asp"),
            new HttpURL("http://www.comedycentral.com/rss/tdsvideos.jhtml"),
            new HttpURL("http://rss.dealcatcher.com/rss.xml"),
            new HttpURL("http://content.dealnews.com/dealnews/rss/todays-edition.xml"),
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
            new HttpURL("http://news.google.com/?output=rss"));
    
    for(Channel c : channels){
      if(c != null){
        LOG.info(c.getTitle());
        assertNotNull(c.getTitle());
      }else{
        LOG.error("Yarfraw failed to parse one of the channels");
      }
      
    }
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