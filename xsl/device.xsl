<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">
    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:strip-space elements="*"/>
    <xsl:include href="/xsl/layout.xsl"/>
    <xsl:template match="page" mode="head">
        <!-- WILL REFRESH ONLY INPUT PORTS -->
<!--
        <xsl:if test="data/summary/dtype[.='Input']">
            <meta http-equiv="refresh" content="2"/>
        </xsl:if>
-->
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
<!--
            <xsl:if test="/page/data/summary/dname[.='WAD_DOS_BUS']">
-->
            <!-- WILL SHOW THIS BUTTONS ONLY IF PORT TYPE IS DOS -->
            <xsl:if test="/page/data/summary/dname[.='WAD_AIK_BUS']">
                <td>
                    <a href="{/page/links/link[@rel='self']/@href}/?c={id}&amp;v=on">on</a>
                </td>
                <td>
                    <a href="{/page/links/link[@rel='self']/@href}/?c={id}&amp;v=on">off</a>
                </td>
            </xsl:if>
        </tr>
    </xsl:template>
</xsl:stylesheet>