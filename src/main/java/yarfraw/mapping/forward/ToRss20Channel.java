package yarfraw.mapping.forward;

import javax.xml.bind.JAXBElement;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.mapping.Functor;

public interface ToRss20Channel extends Functor<JAXBElement<TRssChannel>, Channel, YarfrawException>{}