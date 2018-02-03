package space.shooter;

import javax.swing.JOptionPane;

import space.shooter.ui.Frame;

public class Main {
	
	public static void main(String[] args) {
		Utility.calculateDimensions();		// calculates appropriate dimensions for screen size...
		
		try {
			Frame frame = new Frame();
			frame.setTitle(Utility.TITLE);
			frame.setVisible(true);
		}
		catch (Exception exception) {
			exception.printStackTrace();		// for easier debugging...
			JOptionPane.showMessageDialog(null, exception.getMessage(), Utility.TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
}