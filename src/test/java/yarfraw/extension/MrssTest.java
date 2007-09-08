package yarfraw.extension;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import junit.framework.TestCase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
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

public class MrssTest extends TestCase{
  @Test
  public void testBuild() throws Exception{
    MrssExtension ext = new MrssExtension();
    MrssCopyrightType rights = new MrssCopyrightType();
    rights.setUrl("http://someurl");
    rights.setValue("copy");
    ext.setCopyright(rights);
    MrssDescriptionType desc = new MrssDescriptionType();
    desc.setType("plain");
    desc.setValue("This was some really bizarre band I listened to as a young lad.");
    ext.setDescription(desc);
    MrssHashType hash = new MrssHashType();
    hash.setAlgo("md5");
    hash.setValue("dfdec888b72151965a34b4b59031290a");
    ext.setHash(hash);
    ext.setKeywords("kitty, cat, big dog, yarn, fluffy");
    MrssPlayerType player = new MrssPlayerType();
    player.setHeight(new BigInteger("200"));
    player.setWidth(new BigInteger("400"));
    player.setUrl("http://www.foo.com/player?id=1111");
    ext.setPlayer(player);
    MrssRatingType rating = new MrssRatingType();
    rating.setScheme("urn:simple");
    rating.setValue("adult");
    ext.setRating(rating);
    MrssRestrictionType restriction = new  MrssRestrictionType();
    restriction.setType("country");
    restriction.setRelationship("allow");
    restriction.setValue("au us");
    ext.setRestriction(restriction);
    MrssTextType text = new MrssTextType();
    text.setType("plain");
    text.setLang(Locale.ENGLISH.getLanguage());
    text.setStart("00:00:03.000");
    text.setEnd("00:00:10.000");
    text.setValue(" Oh, say, can you see");
    ext.setText(text);
    MrssThumbnailType thumb = new MrssThumbnailType();
    thumb.setHeight(new BigInteger("50"));
    thumb.setWidth(new BigInteger("70"));
    thumb.setTime("12:05:01.123");
    thumb.setUrl("http://www.foo.com/keyframe.jpg");
    ext.setThumbnail(thumb);
    MrssTitleType title = new MrssTitleType();
    title.setType("plain");
    title.setValue("The Judy's - The Moo Song");
    ext.setTitle(title);
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
    
    ext.getGroup().add(group);
    File f = new File("testTmpOutput/mrss.xml");
    
    FeedWriter w = new FeedWriter(f);
    ChannelFeed c = new ChannelFeed();
    c.setTitle("a title");
    c.getOtherElements().addAll(ExtensionUtils.toMrssElements(ext));
    ItemEntry i = new ItemEntry();
    i.getOtherElements().addAll(ExtensionUtils.toMrssElements(ext));
    c.addItem(i.setTitle("item title"));
    w.writeChannel(c);
    
    FeedReader r = new FeedReader(f);
    ChannelFeed c2 = r.readChannel();
    
    MrssExtension ext2 = ExtensionUtils.extractMrssExtension(c2.getOtherElements());
    
    assertEquals(ext.getKeywords(), ext2.getKeywords());
    assertTrue(EqualsBuilder.reflectionEquals(ext.getContent(), ext2.getContent()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getCopyright(), ext2.getCopyright()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getCredit(), ext2.getCredit()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getDescription(), ext2.getDescription()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getGroup(), ext2.getGroup()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getHash(), ext2.getHash()));
    
    assertTrue(EqualsBuilder.reflectionEquals(ext.getPlayer(), ext2.getPlayer()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getRating(), ext2.getRating()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getRestriction(), ext2.getRestriction()));
    
    assertTrue(EqualsBuilder.reflectionEquals(ext.getText(), ext2.getText()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getThumbnail(), ext2.getThumbnail()));
    assertTrue(EqualsBuilder.reflectionEquals(ext.getTitle(), ext2.getTitle()));

    assertEquals(ext.getCategory().size(), ext2.getCategory().size());
    assertEquals(ext.getContent().size(), ext2.getContent().size());
    assertEquals(ext.getCredit().size(), ext2.getCredit().size());
    assertEquals(ext.getGroup().size(), ext2.getGroup().size());
  }
}