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
 
package de.enough.glaze.style.definition.converter.background;

import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.StyleSheetDefinition;
import de.enough.glaze.style.definition.converter.BackgroundConverter;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.background.GzBackground;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

/**
 * A {@link Converter} implementation to convert a definition to a layer
 * background
 * 
 * @author Andre
 * 
 */
public class LayerBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static LayerBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static LayerBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LayerBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-backgrounds" };
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

		Property backgroundTypeProp = definition.getProperty("background-type");
		Property backgroundsProp = definition
				.getProperty("background-backgrounds");

		GzBackground[] backgrounds = null;

		if (backgroundsProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundsProp);
			if (result instanceof String) {
				String backgroundId = (String) result;
				GzBackground background = getBackground(backgroundId);
				if (background != null) {
					backgrounds = new GzBackground[] { background };
				} else {
					throw new CssSyntaxError("unable to resolve background",
							backgroundId, backgroundsProp.getLine());
				}
			} else if (result instanceof String[]) {
				String[] backgroundIds = (String[]) result;
				backgrounds = new GzBackground[backgroundIds.length];
				for (int index = 0; index < backgroundIds.length; index++) {
					String backgroundId = backgroundIds[index];
					GzBackground background = getBackground(backgroundId);
					if (background != null) {
						backgrounds[index] = background;
					} else {
						throw new CssSyntaxError(
								"unable to resolve background", backgroundId,
								backgroundsProp.getLine());
					}
				}
			}
		}

		if (backgrounds != null) {
			return GzBackgroundFactory.createLayerBackground(backgrounds);
		} else {
			throw new CssSyntaxError(
					"unable to create layer background, properties are missing",
					backgroundTypeProp);
		}
	}

	private GzBackground getBackground(String id) throws CssSyntaxError {
		GzBackground background = StyleSheet.getInstance().getBackground(id);

		if (background == null) {
			StyleSheetDefinition styleSheetDefinition = StyleSheet
					.getInstance().getDefinition();
			Definition backgroundDefinition = styleSheetDefinition
					.getBackgroundDefinition(id);
			background = (GzBackground) BackgroundConverter.getInstance()
					.convert(backgroundDefinition);
		}

		return background;
	}
}
