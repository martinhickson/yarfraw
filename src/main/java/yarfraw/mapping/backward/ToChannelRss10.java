package yarfraw.mapping.backward;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.mapping.Functor;

public interface  ToChannelRss10 extends Functor<ChannelFeed, RDF, YarfrawException>{}