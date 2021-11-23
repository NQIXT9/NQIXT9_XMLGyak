<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
	<html>
		<body>
			<xsl:apply-templates/>
		</body>
	</html>
	</xsl:template>
	
	<xsl:template match="student">
  <p>
  <xsl:apply-templates select="@id"/>
  <xsl:apply-templates select="vezeteknev"/>
  <xsl:apply-templates select="keresztnev"/>
  <xsl:apply-templates select="becenev"/>
  <xsl:apply-templates select="kor"/>
  <xsl:apply-templates select="fizetes"/>
  </p>
</xsl:template>

<xsl:template match="@id">
  <span style="font-size:30">
  ID:
  <xsl:value-of select="."/>
  </span>
  <br />
</xsl:template>

<xsl:template match="vezeteknev">
  Vezeteknev: <span style="color:GREEN">
  <xsl:value-of select="."/></span>
  <br />
</xsl:template>

<xsl:template match="keresztnev">
  Keresztnev: <span style="color:BROWN">
  <xsl:value-of select="."/></span>
  <br />
</xsl:template>

<xsl:template match="becenev">
  <xsl:value-of select="."/>
</xsl:template>

<xsl:template match="kor">
   kor: <span style="color:BLUE">
  <xsl:value-of select="."/></span>
  <br />
</xsl:template>

<xsl:template match="fizetes">
  Fizetes: <span style="color:RED">
  <xsl:value-of select="."/></span>
  <br />
</xsl:template>

</xsl:stylesheet>