package yarfraw.mapping.forward;

import jakarta.xml.bind.JAXBElement;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.mapping.Functor;

public interface ToRss20Channel extends Functor<JAXBElement<TRssChannel>, ChannelFeed, YarfrawException>{}