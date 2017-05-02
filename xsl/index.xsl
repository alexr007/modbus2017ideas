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
        <a href="{links/link[@rel='config']/@href}">
            config
        </a>
    </xsl:template>
</xsl:stylesheet>