package yarfraw.rss20;

import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;

import net.opengis.gml.CoordType;
import net.opengis.gml.PointType;

import org.georss.georss._10.GeoRssExtension;
import org.georss.georss._10.SimplePositionType;
import org.georss.georss._10.Where;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.FeedFormat;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.ValidationException;
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
import yarfraw.io.FeedReader;
import yarfraw.io.FeedWriter;
import yarfraw.utils.extension.ExtensionUtils;
/**
 * Some unit tests.
 * 
 * @author jliang
 *
 */
public class BuilderTest{
    


  public static ChannelFeed buildChannel() throws MalformedURLException, URISyntaxException{
      ChannelFeed channel = new ChannelFeed().setTitle("Liftoff News")
                                             .addLink("http://liftoff.msfc.nasa.gov/")
                                             .setDescriptionOrSubtitle("Liftoff to Space Exploration.")
                                             .setLang("en-us")
                                             .setPubDate("Tue, 10 Jun 2003 04:00:00 GMT")
                                             .setLastBuildOrUpdatedDate("Tue, 10 Jun 2003 09:41:01 GMT")
                                             .setDocs("http://blogs.law.harvard.edu/tech/rss")
                                             .setGenerator("Weblog Editor 2.0")
                                             .addManagingEditorOrAuthorOrPublisher("editor@example.com")
                                             .addWebMasterOrCreator("webmaster@example.com")
                             .addItem(new ItemEntry().setTitle("Star City")
                                                     .addLink("http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp")
                                                     .setDescriptionOrSummary("How do Americans get ready to work with " +
                                                     		"Russians aboard the International Space Station? They take a " +
                                                     		"crash course in culture, language and protocol at Russia's " +
                                                     		"&lt;a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\"&gt;Star City&lt;/a&gt;.")
                                                     .setPubDate("Tue, 03 Jun 2003 09:39:21 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/06/03.html#item573"))
                             .addItem(new ItemEntry().setDescriptionOrSummary("Sky watchers in Europe, Asia, and parts of " +
                             		                      "Alaska and Canada will experience a " +
                             		                      "&lt;a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\"&gt;" +
                             		                      "partial eclipse of the Sun&lt;/a&gt; on Saturday, May 31st.")
                                                     .setPubDate("Fri, 30 May 2003 11:06:42 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/05/30.html#item572"))
                             .addItem(new ItemEntry().setTitle("The Engine That Does More")
                                                     .addLink("http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp")
                                                     .setDescriptionOrSummary("Before man travels to Mars, NASA hopes to design new " +
                                                     		"engines that will let us fly through the Solar System more quickly.  " +
                                                     		"The proposed VASIMR engine would do that.")
                                                     .setPubDate("Tue, 27 May 2003 08:37:32 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/05/27.html#item571"))
                             .addItem(new ItemEntry().setTitle("Astronauts' Dirty Laundry")
                                                     .addLink("http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp</")
                                                     .setDescriptionOrSummary("Compared to earlier spacecraft, the International Space" +
                                                     		" Station has many luxuries, but laundry facilities are not one of them.  " +
                                                     		"Instead, astronauts have other options.")
                                                     .setPubDate("Tue, 20 May 2003 08:56:02 GMT")
                                                     .setUid("http://liftoff.msfc.nasa.gov/2003/05/20.html#item570"));                                                     
                                                     
      return channel;
  }


  @Test
  public void testValidation() throws MalformedURLException, URISyntaxException{
    ChannelFeed c = buildChannel();
    c.setItems(null);
    try {
      c.validate(FeedFormat.RSS20);
      fail("Expecting validation error");
    } catch (ValidationException e) {
      //success
    }
  }
  
  @Test
  public void testValidation2() throws MalformedURLException, URISyntaxException{
    ChannelFeed c = buildChannel();
    c.addLink((String)null);
    try {
      c.validate(FeedFormat.RSS20);
      fail("Expecting validation error");
    } catch (ValidationException e) {
      //success
    }
  }
  
  @Test
  public void testItunesExtension() throws Exception {
    ChannelFeed c = buildChannel();
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
    c.getOtherElements().addAll(list);
    FeedWriter w = new FeedWriter(File.createTempFile("itunes",".xml"));
    w.writeChannel(c);
  }
  
  @Test
  public void testGeoRssExtension() throws Exception {
    ChannelFeed c = buildChannel();
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
    c.getOtherElements().addAll(list);
    FeedWriter w = new FeedWriter(File.createTempFile("georss",".xml"));
    w.writeChannel(c);
  }
  
  @Test
  public void testMrssExtension() throws Exception {
    ChannelFeed c = buildChannel();
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
    test.getContent().add(content);
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
    test.getGroup().add(group);
    List<Element> list = ExtensionUtils.toMrssElements(test);
    c.getOtherElements().addAll(list);
    FeedWriter w = new FeedWriter(File.createTempFile("mrss",".xml"));
    w.writeChannel(c);
  }
  
  @Test
  public void testOtherElements() throws Exception {
    ChannelFeed c = buildChannel();
    
    c.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    c.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    c.addOtherElement("<my:otherNewElement xmlns:my=\"http://my.company.com/\">new element 2</my:otherNewElement>");
    
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    Document doc = factory.newDocumentBuilder().newDocument();
    c.addOtherElement(doc.createElementNS("http://my.company.com/", "oneMoreElement"));
    
    ItemEntry item = new ItemEntry().setTitle("title")
                          .setDescriptionOrSummary("desc")
                          .addLink("http://my.company.com");
    item.addOtherAttributes(new QName("http://my.company.com/", "testAttribute", "my"), "test");
    item.addOtherElement("<my:newElement xmlns:my=\"http://my.company.com/\">new element</my:newElement>");
    item.addOtherElement("<my:otherNewElement xmlns:my=\"http://my.company.com/\">new element 2</my:otherNewElement>");    
    
    item.addOtherElement(doc.createElementNS("http://my.company.com/", "oneMoreElement"));
    
    c.addItem(item);
    File file = File.createTempFile("YarfrawTestOtherElements", ".xml");
    FeedWriter w = new FeedWriter(file);
    w.writeChannel(c);
    
    //make sure we can read it back
    FeedReader reader = new FeedReader(file);
    reader.readChannel();
  }
}


