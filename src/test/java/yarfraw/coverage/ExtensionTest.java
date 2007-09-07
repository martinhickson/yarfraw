package yarfraw.coverage;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;
import net.opengis.gml.CoordType;
import net.opengis.gml.PointType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.georss.georss._10.GeoRssExtension;
import org.georss.georss._10.SimplePositionType;
import org.georss.georss._10.Where;
import org.junit.Test;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.generated.googlebase.elements.DateTimeRangeType;
import yarfraw.generated.googlebase.elements.GoogleBaseExtension;
import yarfraw.generated.googlebase.elements.PriceTypeEnumeration;
import yarfraw.generated.itunes.elements.ItunesCategoryType;
import yarfraw.generated.itunes.elements.ItunesExtension;
import yarfraw.generated.itunes.elements.ItunesImageType;
import yarfraw.generated.itunes.elements.ItunesOwnerType;
import yarfraw.generated.mrss.elements.MrssCategoryType;
import yarfraw.generated.mrss.elements.MrssContentType;
import yarfraw.generated.mrss.elements.MrssCopyrightType;
import yarfraw.generated.mrss.elements.MrssDescriptionType;
import yarfraw.generated.mrss.elements.MrssExtension;
import yarfraw.generated.mrss.elements.MrssGroupType;
import yarfraw.generated.mrss.elements.MrssHashType;
import yarfraw.generated.mrss.elements.MrssPlayerType;
import yarfraw.generated.mrss.elements.MrssRatingType;
import yarfraw.generated.mrss.elements.MrssRestrictionType;
import yarfraw.generated.mrss.elements.MrssTextType;
import yarfraw.generated.mrss.elements.MrssThumbnailType;
import yarfraw.generated.mrss.elements.MrssTitleType;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;

public class ExtensionTest extends TestCase{
  
  @Test
  public void testItunes() throws Exception{
    ItunesExtension test = new ItunesExtension();
    test.setAuthor("itune author");
    test.setBlock("itunes block");
    test.setDuration("itune duration");
    ItunesCategoryType cat = new ItunesCategoryType();
    cat.setText("cat");
    test.getCategory().add(cat);
    //<duration> is not allowed under channel, read the specs
    //http://www.apple.com/itunes/store/podcaststechspecs.html
//    test.setDuration("10:10:20");
    test.setExplicit("yes");
    test.setKeywords("some,key, words");
    ItunesImageType image = new ItunesImageType();
    image.setHref("http://someurl");
    image.setRel("ref");
    test.setImage(image);
    ItunesOwnerType owner = new ItunesOwnerType();
    owner.setEmail("owner@email.com");
    owner.setName("pwner");
    test.setOwner(owner);
    test.setSubtitle("subtitle");
    test.setSummary("summary");
    List<Element> list = ExtensionUtils.toItunesElements(test);
    
    ItunesExtension test2 = ExtensionUtils.extractItunesExtension(list);
    assertEquals(0, list.size());
    assertEquals(test.getAuthor(), test2.getAuthor());
    assertEquals(test.getBlock(), test2.getBlock());
    assertEquals(test.getCategory().size(), test2.getCategory().size());
    //FIXME: i dont know why they do not equal using reflectionEquals
    for(int i =0; i<test.getCategory().size() ; i++ ){
      assertEquals(ToStringBuilder.reflectionToString(test.getCategory().get(i), ToStringStyle.SIMPLE_STYLE), 
              ToStringBuilder.reflectionToString(test2.getCategory().get(i), ToStringStyle.SIMPLE_STYLE));
    }
    
    assertEquals(test.getDuration(), test2.getDuration());
    assertEquals(test.getExplicit(), test2.getExplicit());
    test.getImage().setValue(""); //replace null with empty string
    assertEquals(ToStringBuilder.reflectionToString(test.getImage(), ToStringStyle.SIMPLE_STYLE), 
            ToStringBuilder.reflectionToString(test2.getImage(), ToStringStyle.SIMPLE_STYLE));
    assertEquals(test.getKeywords(), test2.getKeywords());
    assertEquals(ToStringBuilder.reflectionToString(test.getOwner(), ToStringStyle.SIMPLE_STYLE), 
            ToStringBuilder.reflectionToString(test2.getOwner(), ToStringStyle.SIMPLE_STYLE));
    assertEquals(test.getSubtitle(), test2.getSubtitle());
    assertEquals(test.getSummary(), test2.getSummary());
  }

  @Test
  public void testGeorss() throws Exception{
    GeoRssExtension test = new GeoRssExtension();
    SimplePositionType polygon = new SimplePositionType();
    polygon.getValue().addAll(Arrays.asList(new Double[]{1.1, 1.2, 1.3, 1.4, 1.5, 1.6}));
    test.setPolygon(polygon);
    
    SimplePositionType box = new SimplePositionType();
    box.getValue().addAll(Arrays.asList(new Double[]{1.1, 1.2, 1.3, 1.4})); //this is not a box i know
    test.setBox(box);
    
    Where where = new Where();
    where.setElev(1.3);
    PointType point = new PointType();
    CoordType coor = new CoordType();
    coor.setX(new BigDecimal("1.2"));
    coor.setY(new BigDecimal("1.3"));
    coor.setZ(new BigDecimal("1.4"));
    point.setCoord(coor);
    where.setPoint(point);
    
    test.setWhere(where);
    List<Element> list = ExtensionUtils.toGeoRssElements(test);    
    GeoRssExtension test2 = ExtensionUtils.extractGeoRssExtension(list);
    assertEquals(0, list.size());
    assertNotNull(test2);
  }

  @Test
  public void testMrss() throws Exception{
    MrssExtension test = new MrssExtension();
    MrssCopyrightType rights = new MrssCopyrightType();
    rights.setUrl("http://someurl");
    rights.setValue("copy");
    test.setCopyright(rights);
    MrssDescriptionType desc = new MrssDescriptionType();
    desc.setType("plain");
    desc.setValue("This was some really bizarre band I listened to as a young lad.");
    test.setDescription(desc);
    MrssHashType hash = new MrssHashType();
    hash.setAlgo("md5");
    hash.setValue("dfdec888b72151965a34b4b59031290a");
    test.setHash(hash);
    test.setKeywords("kitty, cat, big dog, yarn, fluffy");
    MrssPlayerType player = new MrssPlayerType();
    player.setHeight(new BigInteger("200"));
    player.setWidth(new BigInteger("400"));
    player.setUrl("http://www.foo.com/player?id=1111");
    test.setPlayer(player);
    MrssRatingType rating = new MrssRatingType();
    rating.setScheme("urn:simple");
    rating.setValue("adult");
    test.setRating(rating);
    MrssRestrictionType restriction = new  MrssRestrictionType();
    restriction.setType("country");
    restriction.setRelationship("allow");
    restriction.setValue("au us");
    test.setRestriction(restriction);
    MrssTextType text = new MrssTextType();
    text.setType("plain");
    text.setLang(Locale.ENGLISH.getLanguage());
    text.setStart("00:00:03.000");
    text.setEnd("00:00:10.000");
    test.setText(text);
    MrssThumbnailType thumb = new MrssThumbnailType();
    thumb.setHeight(new BigInteger("50"));
    thumb.setWidth(new BigInteger("70"));
    thumb.setTime("12:05:01.123");
    thumb.setUrl("http://www.foo.com/keyframe.jpg");
    test.setThumbnail(thumb);
    MrssTitleType title = new MrssTitleType();
    title.setType("plain");
    title.setValue("The Judy's - The Moo Song");
    test.setTitle(title);
    MrssContentType content = new MrssContentType();
    content.setBitrate(new BigInteger("128"));
    MrssCategoryType cat = new MrssCategoryType();
    cat.setLabel("Ace Ventura - Pet Detective");
    cat.setScheme("http://dmoz.org");
    cat.setValue("music/artist/album/song");
    content.getCategory().add(cat);
    content.setChannels(new BigInteger("2"));
    content.setCopyright(rights);
    content.setDescription(desc);
    content.setDuration(new BigInteger("100"));
    content.setExpression("full");
    content.setFileSize(new BigInteger("100"));
    content.setFramerate(new BigInteger("30"));
    content.setHash(hash);
    content.setHeight(new BigInteger("20"));
    content.setIsDefault(true);
    content.setKeywords("kitty, cat, big dog, yarn, fluffy");
    content.setLang("en");
    content.setMedium("video");
    content.setPlayer(player);
    content.setRating(rating);
    content.setRestriction(restriction);
    content.setSamplingrate(new BigDecimal("44.1"));
    content.setText(text);
    content.setThumbnail(thumb);
    content.setTitle(title);
    content.setType("video");
    content.setUrl("http://www.foo.com/movie.mov");
    content.setValid("start=2002-10-13T09:00+01:00; "+
      " end=2002-10-17T17:00+01:00; "+
      " scheme=W3C-DTF");
    content.setWidth(new BigInteger("20"));
    
    MrssGroupType group = new MrssGroupType();
    group.setCopyright(rights);
    group.setDescription(desc);
    group.setHash(hash);
    group.setKeywords("a, b, c");
    group.setPlayer(player);
    group.getContent().add(content);
    group.setRating(rating);
    group.setText(text);
    group.getContent().add(content);
    List<Element> list = ExtensionUtils.toMrssElements(test);
    MrssExtension test2 = ExtensionUtils.extractMrssExtension(list);
    assertEquals(0, list.size());
    assertEquals(test.getCategory().size(), test2.getCategory().size());
    assertEquals(test.getContent().size(), test2.getContent().size());
    assertEquals(test.getCredit().size(), test2.getCredit().size());

  }
  
  @Test
  public void testGoogleBase() throws Exception{
    ChannelFeed c = new ChannelFeed();
    c.setTitle("title");
    c.setLang(Locale.ENGLISH);
    ItemEntry item = new ItemEntry();
    item.setTitle("Google's 2005 Halloween Fright Fest");
    item.setDescriptionOrSummary("Come face to face with your most horrific fears." +
        " This year's Halloween fest will be sure to scare your socks off," +
        " complete with a real haunted Googleplex.");
    GoogleBaseExtension gbase = new GoogleBaseExtension();
    DatatypeFactory factory = DatatypeFactory.newInstance();
    DateTimeRangeType dateRange = new DateTimeRangeType();
    XMLGregorianCalendar cal = factory.newXMLGregorianCalendar(new GregorianCalendar());
    cal.setYear(2006);
    cal.setMonth(10);
    cal.setDay(31);
    cal.setHour(18);
    dateRange.setStart(cal);
    gbase.setEventDateRange(dateRange);
    
    XMLGregorianCalendar cal2 = factory.newXMLGregorianCalendar(new GregorianCalendar());
    cal.setYear(2006);
    cal.setMonth(12);
    cal.setDay(10);
    gbase.setExpirationDate(cal2);
    
    item.setUid("1");
    
    gbase.getImageLink().add("http://www.example.com/image1.jpg");
    
    item.addLink("http://www.example.com/item1-info-page.html");
    gbase.setLocation("1600 Amphitheatre Parkway, Mountain View, CA, 94043");
    gbase.setPrice("10");
    gbase.setPriceType(PriceTypeEnumeration.STARTING);
    gbase.setQuantity(new BigInteger("100"));
    item.getOtherElements().addAll(ExtensionUtils.toGoogleBaseElements(gbase));
    c.addItem(item);
    //well, you get the idea.
    FeedWriter w = new FeedWriter(File.createTempFile("gbase", ".xml"));
    w.writeChannel(c);
  }
  
  
  @Test
  public void testDc() throws Exception{
//    DublinCoreExtension ext = new DublinCoreExtension();
//    ext.setCoverage("coverage");
//    ext.setCreator("creator");
//    ext.setDate("2007-07-16");
//    ext.setDescription("any description");
//    ext.setFormat("text/html");
//    ext.setIdentifier("id");
//    ext.setLanguage("en");
//    ext.setRelation("whatever");
//    ext.setRights("copy right info");
//    ext.setSource("the source");
//    ext.setTitle("the title");
    
  }
}