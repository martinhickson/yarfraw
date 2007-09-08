<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:xhtml="http://www.w3.org/1999/xhtml" xmlns:feedburner="http://rssnamespace.org/feedburner/ext/1.0">
	<xsl:output method="html" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>
	<xsl:variable name="godecoding">go_decoding();</xsl:variable>
	<xsl:variable name="title" select="/atom:feed/atom:title"/>
	<xsl:variable name="feedUrl" select="/atom:feed/atom:link[@rel='self']/@href"/>
	<xsl:template match="/">
		<xsl:element name="html">
			<head>
				<title><xsl:value-of select="$title"/> - powered by FeedBurner</title>
					<link href="http://www.feedburner.com/fb/lib/stylesheets/undohtml.css" rel="stylesheet" type="text/css" media="all"/>
					<link href="http://www.feedburner.com/fb/feed-styles/bf30.css" rel="stylesheet" type="text/css" media="all"/>
					<link rel="alternate" type="application/atom+xml" title="{$title}" href="{$feedUrl}"/>
					<xsl:if test="/atom:feed/atom:icon">
					<link rel="shortcut icon" href="{/atom:feed/atom:icon}"/>
					</xsl:if>
			<xsl:element name="script">
			  <xsl:attribute name="type">text/javascript</xsl:attribute>
					<xsl:attribute name="src">http://www.feedburner.com/fb/feed-styles/bf30.js</xsl:attribute>
			</xsl:element>
			</head>
			<xsl:apply-templates select="atom:feed"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="atom:feed">
		<body id="browserfriendly" onload="jsFeedUrl='{$feedUrl}';loadSubscribeAreaUltra('standard');go_decoding()">
			<div id="cometestme" style="display:none;">
				<xsl:text disable-output-escaping="yes" >&amp;amp;</xsl:text>
			</div>
			<div id="bodycontainer">
				<div id="bannerblock">
					<xsl:apply-templates select="atom:logo" />
					<h1>
						<xsl:choose>
							<xsl:when test="atom:link[@rel='alternate' or not(@rel)]">
								<a href="{normalize-space(atom:link[@rel='alternate' or not(@rel)]/@href)}">
									<xsl:call-template name="outputTitle"/>
								</a>
							</xsl:when>
							<xsl:otherwise><xsl:call-template name="outputTitle"/></xsl:otherwise>
						</xsl:choose>
					</h1>
					<h2>syndicated content powered by FeedBurner</h2>
					<p style="clear:both"/>
				</div>
				<div id="bodyblock">
					<div id="subscribenow" class="subscribeblock action">
						<div id="subscribe-userchoice" style="display:none">
							<p id="subscribeLink">
								<a href="#">...</a>
							</p>
							<p id="resetLink">Reset this favorite; <a href="#" onclick="return clearUserchoice('standard')">show all Subscribe options</a></p>
						</div>
						<div id="subscribe-options">
							<h3>Subscribe Now!</h3>
							<h4>...with web-based news readers. Click your choice below:</h4>
		                    <div id="webbased">
							<xsl:choose>
								<xsl:when test="feedburner:feedFlare">
									<xsl:apply-templates select="feedburner:feedFlare"/>
								</xsl:when>
								<xsl:otherwise><a href="http://add.my.yahoo.com/rss?url={$feedUrl}" onclick="this.href = subscribeNow(this.href,'My Yahoo!');return true"><img src="http://us.i1.yimg.com/us.yimg.com/i/us/my/addtomyyahoo4.gif" width="91" height="17" alt="addtomyyahoo4"/></a><a class="img" href="http://www.newsgator.com/ngs/subscriber/subext.aspx?url={$feedUrl}" onclick="subscribeNow(this.href,'NewsGator Online');return true"><img src="http://www.newsgator.com/images/ngsub1.gif" alt="Subscribe in NewsGator Online"/></a><a href="http://feeds.my.aol.com/add.jsp?url={$feedUrl}" onclick="this.href=subscribeNow(this.href,'My AOL');return true"><img src="http://o.aolcdn.com/myfeeds/html/vis/myaol_cta1.gif" alt="Add to My AOL" border="0"/></a><br/><a class="img" href="http://www.rojo.com/add-subscription?resource={$feedUrl}" onclick="this.href=subscribeNow(this.href,'Rojo');return true"><img src="http://www.rojo.com/corporate/images/add-to-rojo.gif" alt="Subscribe in Rojo"/></a><a class="img" href="http://www.bloglines.com/sub/{$feedUrl}" onclick="this.href=subscribeNow(this.href,'Bloglines');return true"><img src="http://www.bloglines.com/images/sub_modern5.gif" alt="Subscribe with Bloglines"/></a><a href="http://www.netvibes.com/subscribe.php?url={$feedUrl}" onclick="this.href=subscribeNow(this.href,'Netvibes');return true"><img src="http://www.netvibes.com/img/add2netvibes.gif" alt="Add to netvibes" /></a><br/><a href="http://fusion.google.com/add?feedurl={$feedUrl}" onclick="this.href=subscribeNow(this.href,'Google');return true"><img src="http://buttons.googlesyndication.com/fusion/add.gif" width="104" height="17" alt="Add to Google"/></a><a href="http://www.pageflakes.com/subscribe.aspx?url={$feedUrl}" onclick="this.href=subscribeNow(this.href,'Pageflakes');return true"><img src="http://www.pageflakes.com/subscribe2.gif" border="0"/></a></xsl:otherwise>
							</xsl:choose>
							</div>
                            <xsl:if test="true()">
							<h4>...with other readers:</h4>
							<form action="http://www.feedburner.com" method="get">
								<select onchange="location.href=subscribeNowUltra('feed:{$feedUrl}',this.options[this.selectedIndex].value)">
									<option value="--" disabled="disabled" selected="selected" style="padding-left:0">(Choose Your Reader)</option>
									<option value="FeedDemon">FeedDemon</option>
									<option value="NetNewsWire">NetNewsWire</option>
									<option value="NewsFire">NewsFire</option>
									<option value="NewsGator Outlook Edition">NewsGator Outlook Edition</option>
									<option value="RSSOwl">RSSOwl</option>
									<option value="shrook">Shrook</option>
									<option value="USM">Universal Subscription Mechanism (USM)</option>
								</select>
							</form>
							</xsl:if>
							<xsl:if test="feedburner:emailServiceId">
								<xsl:variable name="feedhost" select="/atom:feed/feedburner:feedburnerHostname"/>
								<xsl:variable name="ffid" select="/atom:feed/feedburner:emailServiceId"/>
								<p id="emailthis"><a onclick="window.open('{$feedhost}/fb/a/emailverifySubmit?feedId={$ffid}', 'popupwindow', 'scrollbars=yes,width=550,height=520');return true" target="popupwindow" href="{$feedhost}/fb/a/emailverifySubmit?feedId={$ffid}">Get <xsl:value-of select="$title"/> delivered by email</a></p>
							</xsl:if>
							<xsl:choose>
							<xsl:when test="feedburner:xmlView">
								<xsl:variable name="originalHref" select="/atom:feed/feedburner:xmlView/@href"/>
								<p><a href="{$originalHref}"><img src="http://www.feedburner.com/fb/lib/images/icons/feed-icon-12x12-orange.gif" alt="original feed"/></a><xsl:text> </xsl:text><a href="{$originalHref}">View Feed XML</a></p>
							</xsl:when>
							<xsl:otherwise>
								<!-- purely for spacing -->
								<p><xsl:text> </xsl:text></p>
							</xsl:otherwise>
							</xsl:choose>
                            <xsl:if test="true()">
							<div id="embed">
							<h3>Embed this content on your site</h3>
							<p id="embedthis">Embed with: <a href="http://www.springwidgets.com/widgetize/23/?param={$feedUrl}">SpringWidgets</a></p>
							</div>
							</xsl:if>
						</div>
							<input id="savechoice" type="hidden" value="standard"/>
					</div>
					<p class="about">FeedBurner makes it easy to receive content updates in My Yahoo!, Newsgator, Bloglines, and other news readers.</p>
					<p class="about">
						<a href="http://www.feedburner.com/fb/a/aboutrss">Learn more about syndication and FeedBurner...</a>
					</p>
					<xsl:apply-templates select="feedburner:browserFriendly"/>
					<xsl:apply-templates select="atom:entry"/>
				</div>
				<div id="footer">
					<a href="http://www.feedburner.com">
						<img src="http://www.feedburner.com/fb/feed-styles/images/footer_logo.gif"/>
					</a>
					<p>FeedBurner delivers the world's subscriptions wherever they need to go. Publish a feed for text or podcasting? <a href="http://www.feedburner.com" target="_blank"><br/>You should try FeedBurner today</a>.</p>
					<img src="http://www.feedburner.com/fb/feed-styles/images/tk.gif?rdm={generate-id()}" width="1" height="1" alt="" border="0"/>
				</div>
			</div>
		</body>
	</xsl:template>
	<xsl:template match="feedburner:feedFlare">
		<xsl:variable name="alttext" select="."/>
		 <a href="{@href}" onclick="this.href = subscribeNowUltra(this.href,'{$alttext}');return true"><img src="{@src}" alt="{$alttext}"/></a>
	</xsl:template>	
	<xsl:template match="atom:entry">
		<xsl:if test="position() = 1">
			<h3 id="currentFeedContent">Current Feed Content</h3>
		</xsl:if>
		<ul>
			<li class="regularitem">
				<h4 class="itemtitle">
					<xsl:choose>
						<xsl:when test="atom:link[@rel='alternate' or not(@rel)]">
							<a href="{normalize-space(atom:link[@rel='alternate' or not(@rel)]/@href)}">
								<xsl:call-template name="outputTitle"/>
							</a>
						</xsl:when>
						<xsl:when test="atom:content[@src]">
							<a href="{normalize-space(atom:content/@src)}">
								<xsl:call-template name="outputTitle"/>
							</a>
						</xsl:when>
						<xsl:otherwise><xsl:call-template name="outputTitle"/></xsl:otherwise>
					</xsl:choose>
				</h4>
				<h5 class="itemposttime">
				   <span>Posted: </span>
				   <xsl:value-of select="substring(atom:updated,1,10)"/>
				   <xsl:text> </xsl:text>
				   <xsl:value-of select="substring(atom:updated,12,8)"/>
				   <xsl:text> </xsl:text>
				   <xsl:choose>
				      <xsl:when test="contains(substring-after(atom:updated,'T'),'Z') or contains(substring-after(atom:updated,'T'),'+00:00') or contains(substring-after(atom:updated,'T'),'-00:00')">
				         <xsl:text>UTC</xsl:text>
				      </xsl:when>
				      <xsl:when test="contains(substring-after(atom:updated,'T'),'+')">
				         <xsl:text>UTC+</xsl:text>
				         <xsl:value-of select="substring-after(substring-after(atom:updated,'T'),'+')"/>
				      </xsl:when>
				      <xsl:when test="contains(substring-after(atom:updated,'T'),'-')">
				         <xsl:text>UTC-</xsl:text>
				         <xsl:value-of select="substring-after(substring-after(atom:updated,'T'),'-')"/>
				      </xsl:when>
				   </xsl:choose> 
				</h5>
				<div class="itemcontent">
					<xsl:call-template name="outputContent"/>
				</div>
			</li>
		</ul>
	</xsl:template>
	<xsl:template match="atom:logo">
		<xsl:choose>
			<xsl:when test="/atom:feed/atom:link[@rel='alternate' or not(@rel)]">
				<a href="{normalize-space(/atom:feed/atom:link[@rel='alternate' or not(@rel)]/@href)}" title="Link to original website"><img src="{.}" id="feedimage" alt=""/></a>
			</xsl:when>
			<xsl:otherwise><img src="{.}" id="feedimage" alt=""/></xsl:otherwise>
		</xsl:choose>
		<xsl:text/>
	</xsl:template>
	<xsl:template match="feedburner:browserFriendly">
		<p class="about">
			<span style="color:#000">A message from this feed's publisher: </span>
			<xsl:apply-templates/>
		</p>
	</xsl:template>
	<xsl:template name="outputTitle">
		<xsl:choose>
			<xsl:when test="atom:title[@type='xhtml']">
				<xsl:copy-of select="atom:title[@type='xhtml']/xhtml:div/child::node()"/>
			</xsl:when>
			<xsl:when test="atom:title[@type='html']">
				<xsl:attribute name="name">decodeable</xsl:attribute>
				<xsl:value-of select="atom:title" disable-output-escaping="yes"/>
			</xsl:when>
			<xsl:when test="atom:title[@type='text' or not(@type)]">
				<xsl:value-of select="atom:title"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>	
	<xsl:template name="outputContent">
		<xsl:choose>
			<xsl:when test="atom:content[@type='xhtml']">
				<xsl:copy-of select="atom:content[@type='xhtml']/xhtml:div/child::node()"/>
			</xsl:when>
			<xsl:when test="atom:content[@type='html']">
				<xsl:attribute name="name">decodeable</xsl:attribute>
				<xsl:value-of select="atom:content" disable-output-escaping="yes"/>
			</xsl:when>
			<xsl:when test="atom:content[@type='text' or not(@type)]">
				<xsl:value-of select="atom:content"/>
			</xsl:when>
			<xsl:when test="atom:summary[@type='xhtml']">
				<xsl:copy-of select="atom:summary[@type='xhtml']/xhtml:div/child::node()"/>
			</xsl:when>
			<xsl:when test="atom:summary[@type='html']">
				<xsl:attribute name="name">decodeable</xsl:attribute>
				<xsl:value-of select="atom:summary" disable-output-escaping="yes"/>
			</xsl:when>
			<xsl:when test="atom:summary[@type='text' or not(@type)]">
				<xsl:value-of select="atom:summary"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
