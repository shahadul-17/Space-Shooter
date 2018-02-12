package space.shooter.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import space.shooter.Utility;

public class CustomButton extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = -2153496513856855494L;
	
	private static final Color ALPHA_BLACK = new Color(0, 0, 0, 90);
	
	private ActionEvent actionEvent;
	private ActionListener actionListener;
	
	public CustomButton() {
		initialize();
	}
	
	private void initialize() {
		actionEvent = new ActionEvent(this, 0, null);
		
		setBackground(ALPHA_BLACK);
		setForeground(Color.WHITE);
		setFont(Utility.FONTS[0]);
		setHorizontalAlignment(SwingConstants.CENTER);
		addMouseListener(this);
	}
	
	public synchronized void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		actionListener.actionPerformed(actionEvent);
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
		setOpaque(true);
	}

	@Override
	public void mouseExited(MouseEvent event) {
		setOpaque(false);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) { }
	
	@Override
	public void mousePressed(MouseEvent event) { }
	
}