<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">
    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:strip-space elements="*"/>
    <xsl:include href="/xsl/layout.xsl"/>
    <xsl:template match="page" mode="head">
        <!-- WILL REFRESH ONLY INPUT PORTS -->
        <xsl:if test="data/summary/dtype[.='Input']">
            <meta http-equiv="refresh" content="2"/>
        </xsl:if>
        <title>
            <xsl:text>BIO scada</xsl:text>
        </title>
    </xsl:template>
    <xsl:template match="page" mode="body">
        <xsl:if test="not(data/summary)">
            <p>
                <xsl:text>There are no device summary.</xsl:text>
            </p>
        </xsl:if>
        <xsl:if test="data/summary">
        <p>
            <xsl:text>Device summary:</xsl:text>
        </p>
        <table>
            <tr>
                <td>Device name</td>
                <td><xsl:value-of select="data/summary/dname"/></td>
            </tr>
            <tr>
                <td>ModBus Id</td>
                <td><xsl:value-of select="data/summary/modbusid"/></td>
            </tr>
            <tr>
                <td>Device type</td>
                <td><xsl:value-of select="data/summary/dtype"/></td>
            </tr>
            <tr>
                <td>Channels count</td>
                <td><xsl:value-of select="data/summary/ccount"/></td>
            </tr>
            <tr>
                <td>Channels type</td>
                <td><xsl:value-of select="data/summary/ctype"/></td>
            </tr>
        </table>
        </xsl:if>
        <xsl:if test="not(data/channels/channel)">
            <p>
                <xsl:text>There are no channels on device.</xsl:text>
            </p>
        </xsl:if>
        <xsl:if test="data/channels/channel">
            <table>
            <xsl:for-each select="data/channels/channel">
                <xsl:apply-templates select="."/>
            </xsl:for-each>
            </table>
        </xsl:if>
    </xsl:template>
    <xsl:template match="channel">
        <tr>
            <td>Channel:<xsl:value-of select="id"/></td>
            <td><xsl:value-of select="value"/></td>
            <!-- WILL SHOW THIS BUTTONS ONLY IF PORT TYPE IS DOS -->
            <xsl:if test="/page/data/summary/dname[.='WAD_DOS_BUS']">
                <td>
                    <a href="{/page/links/link[@rel='self']/@href}/?c={id}&amp;v=on">on</a>
                </td>
                <td>
                    <a href="{/page/links/link[@rel='self']/@href}/?c={id}&amp;v=off">off</a>
                </td>
            </xsl:if>
            <!-- WILL SHOW THIS BUTTONS ONLY IF PORT TYPE IS AO6 -->
            <xsl:if test="/page/data/summary/dname[.='WAD_AO6_BUS']">
                <td>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=0">off </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=6554">10% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=13107">20% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=19660">30% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=26214">40% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=32768">50% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=39321">60% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=45875">70% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=52428">80% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=58980">90% </a>
                    <a href="{/page/links/link[@rel='self']/@href}?c={id}&amp;v=65534">100% </a>
                </td>
            </xsl:if>
        </tr>
    </xsl:template>
</xsl:stylesheet>