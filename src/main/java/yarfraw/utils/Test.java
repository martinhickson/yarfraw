package yarfraw.utils;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.lang.builder.ToStringBuilder;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.generated.atom03.ext.elements.Atom03Extension;
import yarfraw.io.FeedReader;
import yarfraw.utils.extension.ExtensionUtils;


public class Test{

  public static void main(String[] args) throws Exception {
    
    try {
      FeedReader reader = new FeedReader(new HttpURL("http://www.google.com/news?output%5Cx3datom=&output=atom"));
//      assertTrue("isRemoteRead", reader.isRemoteRead());
      if(reader.getFormat() != FeedFormat.ATOM03){
        System.out.println("Google news' atom feed should be atom 0.3 format");
      }
//      System.out.println(reader.readChannel());
      ChannelFeed c = reader.readChannel();
      System.out.println(c);
      System.out.println(c.getItems().get(0).getOtherElements());
      Atom03Extension ext =  ExtensionUtils.extractAtom03Extension(c.getItems().get(0).getOtherElements());
      System.out.println(ToStringBuilder.reflectionToString(ext));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
//    
//    GetMethod get = new GetMethod("http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml");
//    FeedReader r = new FeedReader(get);
//    ChannelFeed first = r.readChannel();
//    ChannelFeed second = r.readChannel();
//    System.out.println(first == second);
//    
//    FeedReader cacheFeedReader = new CachedFeedReader(
//        new HttpURL("http://fishbowl.pastiche.org/index.rdf"));
//    first = cacheFeedReader.readChannel();
//    second = cacheFeedReader.readChannel();
//    System.out.println(first == second);
//    
////    FeedReader r = new FeedReader(new HttpURL("http://feeds.feedburner.com/javaposse"));
////    ChannelFeed c =  r.readChannel();
////    System.out.println(c.getItems().get(0));
////    System.out.println(System.getProperty("java.io.tmpdir"));
////
////    System.out.println(StringEscapeUtils.unescapeXml("&lt;div xmlns=&quot;http://www.w3.org/1999/xhtml&quot;&gt;&lt;p&gt;&lt;i&gt;"+
////  "[Update: The Atom draft is finished.]&lt;/i&gt;&lt;/p&gt;&lt;/div&gt;"));
//    
//    Source source = new StreamSource(new File("test.xml"));
//    Result res = new StreamResult(new File("test.html"));  
//    TransformerFactory transFact = TransformerFactory.newInstance();
//    Transformer trans;
//    
//    try {
//      trans = transFact.newTransformer(new StreamSource(new File("rss1full.xsl")));
//      trans.transform(source, res);
//    } catch (TransformerConfigurationException e) {
//      throw new YarfrawException("Transformer config exception", e);
//    } catch (TransformerException e) {
//      throw new YarfrawException("Transform exception", e);
//    }
    
  }

}