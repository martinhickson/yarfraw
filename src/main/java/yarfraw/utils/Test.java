package yarfraw.utils;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.methods.GetMethod;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.CachedFeedReader;
import yarfraw.io.FeedReader;


public class Test{

  public static void main(String[] args) throws Exception {
    GetMethod get = new GetMethod("http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml");
    FeedReader r = new FeedReader(get);
    ChannelFeed first = r.readChannel();
    ChannelFeed second = r.readChannel();
    System.out.println(first == second);
    
    FeedReader cacheFeedReader = new CachedFeedReader(
        new HttpURL("http://fishbowl.pastiche.org/index.rdf"));
    first = cacheFeedReader.readChannel();
    second = cacheFeedReader.readChannel();
    System.out.println(first == second);
    
//    FeedReader r = new FeedReader(new HttpURL("http://feeds.feedburner.com/javaposse"));
//    ChannelFeed c =  r.readChannel();
//    System.out.println(c.getItems().get(0));
//    System.out.println(System.getProperty("java.io.tmpdir"));
//
//    System.out.println(StringEscapeUtils.unescapeXml("&lt;div xmlns=&quot;http://www.w3.org/1999/xhtml&quot;&gt;&lt;p&gt;&lt;i&gt;"+
//  "[Update: The Atom draft is finished.]&lt;/i&gt;&lt;/p&gt;&lt;/div&gt;"));
    
    Source source = new StreamSource(new File("test.xml"));
    Result res = new StreamResult(new File("test.html"));  
    TransformerFactory transFact = TransformerFactory.newInstance();
    Transformer trans;
    
    try {
      trans = transFact.newTransformer(new StreamSource(new File("rss1full.xsl")));
      trans.transform(source, res);
    } catch (TransformerConfigurationException e) {
      throw new YarfrawException("Transformer config exception", e);
    } catch (TransformerException e) {
      throw new YarfrawException("Transform exception", e);
    }
    
  }

}