package yarfraw.mapping.backward.impl;

import static yarfraw.mapping.backward.impl.Rss10MappingUtils.toChannel;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.generated.rss10.elements.TRss10Channel;
import yarfraw.mapping.backward.ToChannelRss10;

public class ToChannelRss10Impl implements ToChannelRss10{
  private static final ToChannelRss10 _instance = new ToChannelRss10Impl();
  private static final Log LOG = LogFactory.getLog(ToChannelRss10Impl.class);
  private ToChannelRss10Impl() {}
  public static ToChannelRss10 getInstance(){
    return _instance;
  }

  @SuppressWarnings("unchecked")
  public Channel execute(RDF rdf) throws YarfrawException {
    if(rdf == null){
      LOG.warn("null rdf Element received, this is not normal. ");
      return null;
    }
    TRss10Channel ch = null;
    for(Object o : rdf.getChannelOrItemOrTextinput()){
      if (o instanceof JAXBElement) {
        Object val = ((JAXBElement)o).getValue();
        if (val instanceof TRss10Channel) {
          ch = (TRss10Channel) val;
        } 
      }
    }
    return toChannel(ch, rdf);
  }
}