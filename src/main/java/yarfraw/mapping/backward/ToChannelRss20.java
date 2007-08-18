package yarfraw.mapping.backward;

import yarfraw.core.datamodel.Channel;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.generated.rss20.elements.TRssChannel;
import yarfraw.mapping.Functor;

public interface ToChannelRss20 extends Functor<Channel, TRssChannel, YarfrawException>{}