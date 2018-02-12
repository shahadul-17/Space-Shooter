package space.shooter.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class CustomComponent extends JComponent {
	
	private static final long serialVersionUID = 7571038211189622511L;
	
	private CustomComponentType customComponentType;
	private Dimension dimension;
	private Image image;
	
	public CustomComponent(CustomComponentType customComponentType, Dimension dimension, ImageIcon imageIcon) {
		this.customComponentType = customComponentType;
		this.dimension = dimension;
		this.image = imageIcon.getImage();
		
		initialize(imageIcon);
	}
	
	private void initialize(ImageIcon imageIcon) {
		if (dimension == null) {
			setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
		}
		else {
			image = image.getScaledInstance(dimension.width, dimension.height, Image.SCALE_FAST);
			
			setSize(dimension);
		}
		
		super.setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		graphics.drawImage(image, 0, 0, null);
		super.paintComponent(graphics);
		repaint();
	}
	
	public CustomComponentType getCustomComponentType() {
		return customComponentType;
	}
	
}