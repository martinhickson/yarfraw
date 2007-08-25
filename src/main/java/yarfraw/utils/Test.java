package yarfraw.utils;

import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedParserReader;
import yarfraw.mapping.backward.impl.ToChannelDOMSimplifiedImpl;

public class Test{

  public static void main(String[] args) throws YarfrawException {
    FeedParserReader p = new FeedParserReader("rdfModule.xml");
//    p.setFormat(FeedFormat.RSS10);
//    p.parseChannel(new ToChannelDOMSimplifiedImpl());
    
    
    p = new FeedParserReader("yarfraw.xml");
    p.setFormat(FeedFormat.RSS20);
    p.parseChannel(new ToChannelDOMSimplifiedImpl());
    
  }

}