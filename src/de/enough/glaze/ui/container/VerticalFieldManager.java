package de.enough.glaze.ui.container;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import de.enough.glaze.style.handler.FieldStyleManager;
import de.enough.glaze.ui.delegate.GzManager;
import de.enough.glaze.ui.delegate.ManagerDelegate;

public class VerticalFieldManager extends
		net.rim.device.api.ui.container.VerticalFieldManager implements
		GzManager {

	private final FieldStyleManager handlers;

	public VerticalFieldManager() {
		this(0);
	}

	public VerticalFieldManager(long style) {
		super(style);
		this.handlers = new FieldStyleManager(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#add(net.rim.device.api.ui.Field)
	 */
	public void add(Field field) {
		add(field, null);
	}

	public void add(Field field, String id) {
		super.add(field);
		ManagerDelegate.add(field, id, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#addAll(net.rim.device.api.ui.Field[])
	 */
	public void addAll(Field[] fields) {
		super.addAll(fields);
		this.handlers.addAll(fields);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#insert(net.rim.device.api.ui.Field,
	 * int)
	 */
	public void insert(Field field, int index) {
		super.insert(field, index);
		this.handlers.insert(field, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#delete(net.rim.device.api.ui.Field)
	 */
	public void delete(Field field) {
		super.delete(field);
		this.handlers.delete(field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteAll()
	 */
	public void deleteAll() {
		super.deleteAll();
		this.handlers.deleteAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#deleteRange(int, int)
	 */
	public void deleteRange(int start, int count) {
		super.deleteRange(start, count);
		this.handlers.deleteRange(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rim.device.api.ui.Manager#replace(net.rim.device.api.ui.Field,
	 * net.rim.device.api.ui.Field)
	 */
	public void replace(Field oldField, Field newField) {
		super.replace(oldField, newField);
		this.handlers.replace(oldField, newField);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#getHandlers()
	 */
	public FieldStyleManager getStyleManager() {
		return this.handlers;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzFieldManager#sublayout(int, int, int)
	 */
	/*protected void sublayout(int maxWidth, int maxHeight, XYRect fieldRect) {
		Log.d("layout : " + maxWidth + "x" + maxHeight);
		for (int index = 0; index < getFieldCount(); index++) {
			FieldStyleHandler handler = this.handlers.get(index);
			if (handler.isVisualStateChanged()) {
				handler.updateStyle(maxWidth);
				handler.updateVisualState();
			}
		}

		super.sublayout(maxWidth, maxHeight);

		
		  int x = 0; int y = 0;
		  
		  for (int index = 0; index < getFieldCount(); index++) { Field field =
		  getField(index); Style style = getStyle(index);
		  
		  x = field.getMarginLeft(); // if the field is the first one ... if
		  (index == 0) { y += field.getMarginTop(); }
		  
		  int fieldLayoutWidth = ManagerUtils.getChildLayoutWidth(maxWidth,
		  maxHeight, field, style); int fieldLayoutHeight =
		  ManagerUtils.getChildLayoutHeight(maxWidth, maxHeight, field, style);
		  layoutChild(field, fieldLayoutWidth, fieldLayoutHeight);
		  
		  // calculate the bounds for the field layout fieldRect.x = x;
		  fieldRect.y = y; fieldRect.width = fieldLayoutWidth; fieldRect.height
		  = fieldLayoutHeight; // get the layouted position long fieldHAlign =
		  field.getStyle() & Field.FIELD_HALIGN_MASK; long fieldVAlign =
		  field.getStyle() & Field.FIELD_VALIGN_MASK;
		  ManagerUtils.layoutField(fieldRect, field, fieldHAlign, fieldVAlign);
		  setPositionChild(field, fieldRect.x, fieldRect.y);
		  
		  // add the field height y += field.getHeight();
		  
		  // if the current field is not the last field ... if (index <
		  getFieldCount() - 1) { // add the collapsed margin Field nextField =
		  getField(index + 1); y +=
		  ManagerUtils.getCollapsedVerticalMargin(field, nextField); //
		  otherwise ... } else { // add the bottom margin y +=
		  field.getMarginBottom(); } }
		  
		  setExtent(maxWidth, y);
		 
	}*/

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.container.VerticalFieldManager#sublayout(int, int)
	 */
	protected void sublayout(int maxWidth, int maxHeight) {
		ManagerDelegate.sublayout(maxWidth, maxHeight, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.rim.device.api.ui.container.VerticalFieldManager#subpaint(net.rim
	 * .device.api.ui.Graphics)
	 */
	protected void subpaint(Graphics graphics) {
		ManagerDelegate.subpaint(graphics, this);
	}

	/* (non-Javadoc)
	 * @see de.enough.glaze.ui.container.GzManager#gz_setExtent(int, int)
	 */
	public void gz_setExtent(int width, int height) {
		super.setExtent(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#gz_sublayout(int, int)
	 */
	public void gz_sublayout(int maxWidth, int maxHeight) {
		super.sublayout(maxWidth, maxHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.ui.container.GzManager#gz_paintChild(net.rim.device.api
	 * .ui.Graphics, net.rim.device.api.ui.Field)
	 */
	public void gz_paintChild(Graphics graphics, Field field) {
		super.paintChild(graphics, field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.ui.container.GzManager#gz_updateLayout()
	 */
	public void gz_updateLayout() {
		super.updateLayout();
	}

}
