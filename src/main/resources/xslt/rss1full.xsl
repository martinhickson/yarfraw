<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:rss="http://purl.org/rss/1.0/" xmlns:feedburner="http://rssnamespace.org/feedburner/ext/1.0">
	<xsl:output method="html" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>
	<xsl:variable name="godecoding">go_decoding();</xsl:variable>
	<xsl:variable name="title">
		<xsl:choose>
			<xsl:when test="/rdf:RDF/rss:channel/rss:title"><xsl:value-of select="/rdf:RDF/rss:channel/rss:title"/></xsl:when>
			<xsl:when test="/rdf:RDF/rss:channel/dc:title"><xsl:value-of select="/rdf:RDF/rss:channel/dc:title"/></xsl:when>
			<xsl:otherwise>RSS 1.0 Feed</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="feedUrl" select="/rdf:RDF/rss:channel/atom10:link[@rel='self']/@href" xmlns:atom10="http://www.w3.org/2005/Atom"/>
	<xsl:template match="/">
		<xsl:element name="html">
			<head>
				<title><xsl:value-of select="$title"/> - powered by FeedBurner</title>
            <link href="http://www.feedburner.com/fb/lib/stylesheets/undohtml.css" rel="stylesheet" type="text/css" media="all"/>
            <link href="http://www.feedburner.com/fb/feed-styles/bf30.css" rel="stylesheet" type="text/css" media="all"/>
				<link rel="alternate" type="application/rdf+xml" title="{$title}" href="{$feedUrl}" />
			<xsl:element name="script">
			  <xsl:attribute name="type">text/javascript</xsl:attribute>
			  <xsl:attribute name="src">http://www.feedburner.com/fb/feed-styles/bf30.js</xsl:attribute>
			</xsl:element>
			</head>
			<xsl:apply-templates select="rdf:RDF/rss:channel"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="rss:channel">
		<body id="browserfriendly" onload="jsFeedUrl='{$feedUrl}';loadSubscribeAreaUltra('standard');go_decoding()">
			<div id="cometestme" style="display:none;">
			<xsl:text disable-output-escaping="yes" >&amp;amp;</xsl:text>
			</div>
	         <div id="bodycontainer">
	            <div id="bannerblock">
	               <xsl:apply-templates select="/rdf:RDF/rss:image[1]"/>
	               <h1>
                  <xsl:choose>
                     <xsl:when test="rss:link">
                  		<a href="{rss:link}" title="Link to original website"><xsl:value-of select="$title"/></a>
                  	 </xsl:when>
                  	 <xsl:otherwise>
                  	 	<xsl:value-of select="$title"/>
                  	 </xsl:otherwise>
                  </xsl:choose>                  	
	               </h1>
	               <h2>syndicated content powered by FeedBurner</h2>
	               <p style="clear:both"/>
	            </div>
	            <div id="bodyblock">
	               <div id="subscribenow" class="subscribeblock action">
							<div id="subscribe-userchoice" style="display:none">
								<p id="subscribeLink"><a href="#">...</a></p>
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
	                        <select onchange="location.href=subscribeNow('feed:{$feedUrl}',this.options[this.selectedIndex].value)">
	                           <option value="--" disabled="disabled" selected="selected" style="padding-left:0">(Choose Your Reader)</option>
										<option value="FeedDemon">FeedDemon</option>
										<option value="NetNewsWire">NetNewsWire</option>
										<option value="NewsFire">NewsFire</option>
										<option value="NewsGator Outlook Edition">NewsGator Outlook Edition</option>
									<option value="RSSOwl">RSSOwl</option>
										<option value="shrook">Shrook</option>
										<option value="USM">Universal Subscription Mechanism (USM)</option>
	                        </select>
	                        <p>Learn More about <a href="http://www.kbcafe.com/rss/whatisthis.html#whatisusm" target="usm">USM</a></p>
	                     </form>
	                     </xsl:if>
						<xsl:if test="feedburner:emailServiceId">
							<xsl:variable name="ffid" select="/rdf:RDF/rss:channel/feedburner:emailServiceId"/>
							<xsl:variable name="feedhost" select="/rdf:RDF/rss:channel/feedburner:feedburnerHostname"/>
							<p id="emailthis"><a onclick="window.open('{$feedhost}/fb/a/emailverifySubmit?feedId={$ffid}', 'popupwindow', 'scrollbars=yes,width=550,height=520');return true" target="popupwindow" href="{$feedhost}/fb/a/emailverifySubmit?feedId={$ffid}">Get <xsl:value-of select="$title"/> delivered by email</a></p>
						</xsl:if>
						<xsl:choose>
						<xsl:when test="feedburner:xmlView">
							<xsl:variable name="originalHref" select="/rdf:RDF/rss:channel/feedburner:xmlView/@href"/>
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
						<xsl:apply-templates select="/rdf:RDF/rss:item"/>
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
	<xsl:template match="rss:item">
		<xsl:if test="position() = 1">
         <h3 id="currentFeedContent">Current Feed Content</h3>
		</xsl:if>
		<xsl:variable name="date">
			<xsl:choose>
					<xsl:when test="dc:date"><xsl:value-of select="dc:date"/></xsl:when>
					<xsl:when test="dcterms:modified" xmlns:dcterms="http://purl.org/dc/terms/"><xsl:value-of select="dcterms:modified"/></xsl:when>
					<xsl:otherwise>                    </xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
      <ul>
         <li class="regularitem">
            <h4 class="itemtitle">
					<a href="{rss:link}">
					<xsl:choose>
						<xsl:when test="rss:title"><xsl:value-of select="rss:title"/></xsl:when>
						<xsl:when test="dc:title"><xsl:value-of select="dc:title"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="rss:link"/></xsl:otherwise>
					</xsl:choose>
					</a>
            </h4>
            <h5 class="itemposttime">
               <span>Posted: </span>
               <xsl:value-of select="substring($date,1,10)"/><xsl:text> </xsl:text><xsl:value-of select="substring($date,12,5)"/>
            </h5>
            <div class="itemcontent" name="decodeable">
               <xsl:call-template name="outputContent"/>
            </div>
         </li>
      </ul>
	</xsl:template>
	<xsl:template match="rss:image">
		<a href="{rss:link}"><img src="{rss:url}" id="feedimage" alt="Link to {rss:title}"/></a>
		<xsl:text> </xsl:text>
	</xsl:template>
   <xsl:template match="feedburner:browserFriendly">
      <p class="about">
         <span style="color:#000">A message from this feed's publisher: </span>
         <xsl:apply-templates/>
      </p>
   </xsl:template>
	<xsl:template name="outputContent">
			<xsl:choose>
				<xsl:when test="xhtml:body"  xmlns:xhtml="http://www.w3.org/1999/xhtml"><xsl:copy-of select="xhtml:body/*"/></xsl:when>
				<xsl:when test="xhtml:div"  xmlns:xhtml="http://www.w3.org/1999/xhtml"><xsl:copy-of select="xhtml:div"/></xsl:when>
				<xsl:when test="content:encoded" xmlns:content="http://purl.org/rss/1.0/modules/content/"><xsl:value-of select="content:encoded" disable-output-escaping="yes"/></xsl:when>
				<xsl:when test="rss:description"><xsl:value-of select="rss:description" disable-output-escaping="yes"/></xsl:when>
			</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
