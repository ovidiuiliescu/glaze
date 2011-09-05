package de.enough.glaze.style.parser.property;import java.util.Vector;import de.enough.glaze.style.URL;import de.enough.glaze.style.parser.exception.CssSyntaxError;/** * A {@link PropertyParser} implementation to parse a URL *  * @author Andre *  */public class UrlPropertyParser extends PropertyParser {	/**	 * the instance	 */	private static UrlPropertyParser INSTANCE;	/**	 * Returns the instance	 * 	 * @return the instance	 */	public static UrlPropertyParser getInstance() {		if (INSTANCE == null) {			INSTANCE = new UrlPropertyParser();		}		return INSTANCE;	}	/*	 * (non-Javadoc)	 * 	 * @see	 * de.enough.glaze.style.parser.ValueParser#parseValue(java.lang.String)	 */	protected Object parse(String value, Property property)			throws CssSyntaxError {		String url = null;		int startPos = value.indexOf('"');		if (startPos != -1) {			int endPos = value.indexOf('"', startPos + 1);			if (endPos != -1) {				url = value.substring(startPos + 1, endPos);			}		}		if (url == null) {			if (value.startsWith("url")) {				startPos = value.indexOf('(');				int endPos = value.indexOf(')');				if (startPos != -1 && endPos > startPos) {					url = value.substring(startPos + 1, endPos).trim();				}			}		}		if (url == null) {			throw new CssSyntaxError("invalid url", property);		}		if (url.length() > 0 && url.charAt(0) != '/' && !url.startsWith("http")) {			url = '/' + url;		}		return new URL(url);	}	/*	 * (non-Javadoc)	 * 	 * @see	 * de.enough.glaze.style.parser.property.PropertyParser#toArray(java.util	 * .Vector)	 */	protected Object toArray(Vector vector) {		URL[] urls = new URL[vector.size()];		vector.copyInto(urls);		return urls;	}}