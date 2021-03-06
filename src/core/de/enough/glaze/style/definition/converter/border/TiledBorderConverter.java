/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.definition.converter.border;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.UrlPropertyParser;
import de.enough.glaze.style.property.border.GzBorderFactory;
import de.enough.glaze.style.resources.StyleResources;

/**
 * A {@link Converter} implementation to convert a definition to a bitmap border
 * 
 * @author Andre
 * 
 */
public class TiledBorderConverter implements Converter {
	/**
	 * the instance
	 */
	private static TiledBorderConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static TiledBorderConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TiledBorderConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "border-image", "border-width" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.definition.converter.Converter#convert(de.enough
	 * .glaze.style.definition.Definition)
	 */
	public Object convert(Definition definition) throws CssSyntaxError {
		if (!definition.hasProperties(this)) {
			return null;
		}

		Property borderImageProp = definition.getProperty("border-image");
		Property borderWidthProp = definition.getProperty("border-width");

		Bitmap imageBitmap = null;
		XYEdges borderWidths = DimensionConverterUtils.toXYEdges(1);

		if (borderImageProp != null) {
			Object result = UrlPropertyParser.getInstance().parse(
					borderImageProp);
			if (result instanceof Url) {
				Url url = (Url) result;
				try {
					imageBitmap = StyleResources.getInstance().loadBitmap(url);
				} catch (ContentException e) {
					throw new CssSyntaxError("unable to load image",
							borderImageProp);
				}
			} else {
				throw new CssSyntaxError("must be a single url",
						borderImageProp);
			}
		}

		if (borderWidthProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					borderWidthProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				borderWidths = DimensionConverterUtils.toXYEdges(dimension);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				borderWidths = DimensionConverterUtils.toXYEdges(dimensions,
						borderWidthProp);
			}
		}

		return GzBorderFactory.createTiledBorder(borderWidths, borderWidths,
				imageBitmap);
	}

}
