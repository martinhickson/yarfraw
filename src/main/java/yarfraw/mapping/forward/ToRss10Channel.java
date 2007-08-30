package yarfraw.mapping.forward;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.mapping.Functor;

public interface ToRss10Channel extends Functor<RDF, ChannelFeed, YarfrawException>{}