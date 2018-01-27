package space.shooter.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class CustomPanel extends JComponent {
	
	private static final long serialVersionUID = 7571038211189622511L;
	
	private Image image;
	
	public CustomPanel(ImageIcon imageIcon) {
		initialize(imageIcon);
		setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
	}
	
	private void initialize(ImageIcon imageIcon) {
		try {
			super.setOpaque(false);
			
			image = imageIcon.getImage();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		graphics.drawImage(image, 0, 0, null);
		super.paintComponent(graphics);
	}
	
}