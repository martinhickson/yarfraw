package yarfraw.extension;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;

import junit.framework.TestCase;
import net.opengis.gml.CoordType;
import net.opengis.gml.PointType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.georss.georss._10.GeoRssExtension;
import org.georss.georss._10.SimplePositionType;
import org.georss.georss._10.Where;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class GeorssTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    GeoRssExtension ext = new GeoRssExtension();
    SimplePositionType polygon = new SimplePositionType();
    polygon.getValue().addAll(Arrays.asList(new Double[]{1.1, 1.2, 1.3, 1.4, 1.5, 1.6}));
    ext.setPolygon(polygon);
    
    SimplePositionType box = new SimplePositionType();
    box.getValue().addAll(Arrays.asList(new Double[]{1.1, 1.2, 1.3, 1.4})); //this is not a box i know
    ext.setBox(box);
    
    Where where = new Where();
    where.setElev(1.3);
    PointType point = new PointType();
    CoordType coor = new CoordType();
    coor.setX(new BigDecimal("1.2"));
    coor.setY(new BigDecimal("1.3"));
    coor.setZ(new BigDecimal("1.4"));
    point.setCoord(coor);
    where.setPoint(point);
    
    ext.setWhere(where);
    
    
    File f = new File("testTmpOutput/georss.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toGeoRssElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toGeoRssElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    GeoRssExtension ext2 = ExtensionUtils.extractGeoRssExtension(c2.getOtherElements());
    assertTrue(EqualsBuilder.reflectionEquals(ext.getBox(), ext2.getBox()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getLine(), ext2.getLine()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getPoint(), ext2.getPoint()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getPolygon(), ext2.getPolygon()));

    assertTrue(EqualsBuilder.reflectionEquals(ext.getWhere().getPoint().getCoord(), ext2.getWhere().getPoint().getCoord()));
  }
}