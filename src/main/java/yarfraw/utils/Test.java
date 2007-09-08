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
import org.apache.commons.lang.StringEscapeUtils;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;


public class Test{

  public static void main(String[] args) throws Exception {
    FeedReader r = new FeedReader(new HttpURL("http://feeds.feedburner.com/javaposse"));
    ChannelFeed c =  r.readChannel();
    System.out.println(c.getItems().get(0));
    System.out.println(System.getProperty("java.io.tmpdir"));

    System.out.println(StringEscapeUtils.unescapeXml("&lt;div xmlns=&quot;http://www.w3.org/1999/xhtml&quot;&gt;&lt;p&gt;&lt;i&gt;"+
  "[Update: The Atom draft is finished.]&lt;/i&gt;&lt;/p&gt;&lt;/div&gt;"));
    
    Source source = new StreamSource(new File("javaposse.xml"));
    Result res = new StreamResult(new File("javaposse.html"));  
    TransformerFactory transFact = TransformerFactory.newInstance();
    Transformer trans;
    
    try {
      trans = transFact.newTransformer(new StreamSource(new File("rss2enclosuresfull.xsl")));
      trans.transform(source, res);
    } catch (TransformerConfigurationException e) {
      throw new YarfrawException("Transformer config exception", e);
    } catch (TransformerException e) {
      throw new YarfrawException("Transform exception", e);
    }
    
  }

}