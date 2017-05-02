<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" version="1.0">
    <xsl:output method="html" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:strip-space elements="*"/>
    <xsl:include href="/xsl/layout.xsl"/>
    <xsl:template match="page" mode="head">
        <title>
            <xsl:text>BIO scada</xsl:text>
        </title>
    </xsl:template>
    <xsl:template match="page" mode="body">
        <p>
            <xsl:text>BIOsmarTEX Plant Controller</xsl:text>
        </p>
        <xsl:if test="devices/device">
            <p>
                <xsl:text>There are </xsl:text>
                <strong>
                    <xsl:value-of select="count(devices/device)"/>
                    <xsl:text> devices</xsl:text>
                </strong>
                <xsl:text> installed:</xsl:text>
            </p>
            <xsl:apply-templates select="devices"/>
        </xsl:if>
        <xsl:if test="not(devices/device)">
            <p>
                <xsl:text>There are no installed modules.</xsl:text>
            </p>
        </xsl:if>
    </xsl:template>
    <xsl:template match="devices">
            <table>
                <thead>
                    <tr>
                        <td>Device name</td><td>type</td><td>ModBus</td>
                    </tr>
                </thead>
                <xsl:for-each select="device">
                    <xsl:sort select="id"/>
                    <xsl:apply-templates select="."/>
                </xsl:for-each>
            </table>
    </xsl:template>
    <xsl:template match="device">
            <tr>
                <td>
                    <a href="{/page/links/link[@rel='self']/@href}/{name}">
                        <xsl:value-of select="name"/>
                    </a>
                </td>
                <td><xsl:value-of select="type"/></td>
                <td><xsl:value-of select="id"/></td>
            </tr>
    </xsl:template>
</xsl:stylesheet>