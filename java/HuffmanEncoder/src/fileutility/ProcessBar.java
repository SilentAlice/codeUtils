package fileutility;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class ProcessBar extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float scaleSize;
	private float currentValue;

	public ProcessBar() {
		this(100, 30);
	}

	public ProcessBar(float scaleSize, float currentValue) {
		this.scaleSize = scaleSize;
		this.currentValue = currentValue;

		this.setBackground(Color.LIGHT_GRAY);
		this.setForeground(Color.GREEN);
		setSize(500,10);
	}

	public float getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(float currentValue) {
		this.currentValue = Math.max(0, currentValue);
		if (this.scaleSize < this.currentValue) {
			this.currentValue = this.scaleSize;
		}
	}

	public float getScaleSize() {
		return scaleSize;
	}

	public void setScaleSize(float scaleSize) {
		this.scaleSize = Math.max(1.0f, scaleSize);
		if (this.scaleSize < this.currentValue) {
			this.scaleSize = this.currentValue;
		}
	}

	public synchronized void paint(Graphics g) {
		int w = getSize().width;
		int h = getSize().height;

		g.setColor(getBackground());
		g.fillRect(1, 1, w - 2, h - 2);
		g.fill3DRect(0, 0, w - 1, h - 1, true);

		g.setColor(getForeground());
		g.fillRect(3, 3, (int) (currentValue * (w - 6) / scaleSize), h - 6);
	}
}