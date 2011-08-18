package de.enough.glaze.style.background;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.image.Image;
import de.enough.glaze.drawing.DrawUtils;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;

public class VerticalGradientBackground extends GzBackground {


	/**
	 * Start and end colors for the gradient
	 */
	private final Color startColor;
	
	/**
	 * End color for the gradient
	 */
	private final Color endColor;
	
	/**
	 * Start position for the gradient
	 */
	private final Dimension startPosition;
	
	/**
	 * End position for the gradient
	 */
	private final Dimension endPosition;
	
	public VerticalGradientBackground(Color[] colors, Dimension[] offsets) {
		this.startColor = colors[0];
		this.endColor = colors[1];
		this.startPosition = offsets[0];
		this.endPosition = offsets[1];
	}
	
	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#draw(net.rim.device.api.ui.Graphics, net.rim.device.api.ui.XYRect)
	 */
	public void draw(Graphics graphics, XYRect rect) {
		int width = rect.width;
		int height = rect.height;
		
		int startGradientPixels = this.startPosition.getValue(height);
		int endGradientPixels = this.endPosition.getValue(height);
		
		// remember original color
		int originalColor = graphics.getColor();
		
		// Draw the pre-gradient area
		graphics.setColor(this.startColor.getColor());
		graphics.fillRect(0, 0, width, startGradientPixels);
		
		// Draw the gradient
		int [] gradientColors = DrawUtils.getGradient(startColor.getColor(), endColor.getColor(), Math.abs(endGradientPixels-startGradientPixels));
		int pos = startGradientPixels;
		int i=0;
		while (pos < endGradientPixels ) {
			graphics.setColor(gradientColors[i]);
			graphics.drawLine(0, pos, width, pos);
			i++;
			pos++;
		}
		
		// Draw the post-gradient area
		graphics.setColor(this.endColor.getColor());
		graphics.fillRect(0, endGradientPixels, width, endGradientPixels);
		
		// restore original color
		graphics.setColor(originalColor);
	
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.ui.decor.Background#isTransparent()
	 */
	public boolean isTransparent() {
		return false;
	} 
}
