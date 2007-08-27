package yarfraw.mapping.forward;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss10.elements.RDF;
import yarfraw.mapping.Functor;

public interface ToRss10Channel extends Functor<RDF, Channel, YarfrawException>{}