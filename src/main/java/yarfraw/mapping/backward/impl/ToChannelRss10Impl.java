package yarfraw.mapping.backward.impl;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.mapping.backward.ToChannelRss10;

public class ToChannelRss10Impl implements ToChannelRss10{
  private static final ToChannelRss10 _instance = new ToChannelRss10Impl();
  
  private ToChannelRss10Impl() {}
  public static ToChannelRss10 getInstance(){
    return _instance;
  }

  public Channel execute(RDF rdf) throws YarfrawException {
    if(rdf == null){
      return null;
    }
    TRss10Channel ch = null;
    for(Object o : rdf.getChannelOrItemOrTextinput()){
      if (o instanceof TRss10Channel) {
        ch = (TRss10Channel) o; 
      }
    }
    return Rss10MappingUtils.toChannel(ch, rdf);
  }
}