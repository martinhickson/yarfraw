package yarfraw.mapping.backward;

import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.mapping.Functor;

public interface  TChannelMapper<ReturnType> extends Functor<ReturnType, TRssChannel, YarfrawException>{}