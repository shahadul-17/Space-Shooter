package space.shooter;

import javax.swing.JOptionPane;

import space.shooter.ui.Frame;

public class Main {
	
	public static volatile boolean run = false;
	
	public static final String TITLE = "Space Shooter";
	
	public static void main(String[] args) {
		try {
			Frame frame = new Frame();
			frame.setTitle(TITLE);
			frame.setVisible(true);
		}
		catch (Exception exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, exception.getMessage(), TITLE, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}