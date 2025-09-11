package yarfraw.mapping.forward;

import jakarta.xml.bind.JAXBElement;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.atom10.elements.FeedType;
import yarfraw.mapping.Functor;

public interface ToAtom10Channel extends Functor<JAXBElement<FeedType>, ChannelFeed, YarfrawException>{}