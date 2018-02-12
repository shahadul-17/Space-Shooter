package space.shooter.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import space.shooter.Utility;
import space.shooter.engine.BulletController;
import space.shooter.engine.CollisionDetector;
import space.shooter.engine.GameController;
import space.shooter.engine.ObstacleController;
import space.shooter.engine.PlayerController;
import space.shooter.sound.SoundManager;

public class Frame extends JFrame implements ActionListener, KeyListener {
	
	private boolean escapeFlag = false;		// controls "ESC" key functions...
	private int keyCode;
	private static final long serialVersionUID = -8726201312044312651L;
	
	private String[] titleTexts = {
		"Are you sure you want to exit to main menu?", "Main Menu"
	},
	buttonTexts = {
		"New Game", "Options", "Exit",
		"Solo", "Multiplayer", "< Back",
		"Yes", "No"
	};
	
	private Point defaultPlayerLocation;
	
	private JLabel header;
	private CustomComponent player, canvas;
	private CustomComponent[] bullets = new CustomComponent[50];
	private CustomComponent[][] obstacles = new CustomComponent[5][10];
	private CustomButton[] buttons = new CustomButton[3];		// three buttons will serve six functions...
	
	private BulletController bulletController;
	private PlayerController playerController;
	private ObstacleController obstacleController;
	private CollisionDetector collisionDetector;
	
	private SoundManager soundManager;
	
	public Frame() throws Exception {
		initializeUI();
	}
	
	private void initializeUI() {
		ImageIcon imageIcon;
		
		setIconImage(new ImageIcon(this.getClass().getResource("/resources/images/icon-32x32.png")).getImage());
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		
		canvas = new CustomComponent(CustomComponentType.CANVAS, Utility.screenDimension, new ImageIcon(this.getClass().getResource("/resources/images/canvas-background.jpg")));
		canvas.setLayout(null);
		setContentPane(canvas);
		
		header = new JLabel("Main Menu");
		header.setFont(Utility.FONTS[1]);
		header.setForeground(Color.WHITE);
		header.setBounds(Utility.screenOffset, Utility.screenOffset, canvas.getWidth() - Utility.screenOffset, 100);
		add(header);
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new CustomButton();
			buttons[i].setText(buttonTexts[i]);
			
			switch (i) {
			case 0:
				buttons[i].setBounds((Utility.screenDimension.width / 2) - 200, (Utility.screenDimension.height / 2) - (((100 + 10) * buttons.length) / 2) + Utility.screenOffset, 400, 100);
				
				break;
			default:
				buttons[i].setBounds(buttons[i - 1].getX(), buttons[i - 1].getY() + buttons[i - 1].getHeight() + 10, 400, 100);
				
				break;
			}
			
			buttons[i].addActionListener(this);
			add(buttons[i]);
		}
		
		player = new CustomComponent(CustomComponentType.PLAYER, Utility.playerDimension, new ImageIcon(this.getClass().getResource("/resources/images/player-1.png")));
		defaultPlayerLocation = new Point((canvas.getWidth() / 2) - (player.getWidth() / 2), canvas.getHeight() - player.getHeight() - Utility.screenOffset);
		imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/bullet.png"));
		
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new CustomComponent(CustomComponentType.BULLET, Utility.bulletDimension, imageIcon);
		}
		
		for (int i = 0; i < obstacles.length; i++) {
			imageIcon = new ImageIcon(this.getClass().getResource("/resources/images/obstacle-" + (i + 1) + ".png"));
			
			for (int j = 0; j < obstacles[i].length; j++) {
				obstacles[i][j] = new CustomComponent(CustomComponentType.OBSTACLE, Utility.obstacleDimensions[i], imageIcon);
			}
		}
	}
	
	private void initializeEngine() {
		bulletController = new BulletController(player, canvas, bullets);
		playerController = new PlayerController(player, canvas, bulletController);
		obstacleController = new ObstacleController(canvas, obstacles);
		collisionDetector = new CollisionDetector(player, canvas, bulletController.getBulletsOnCanvas(), obstacleController.getObstaclesOnCanvas());
		
		new Thread(bulletController).start();
		new Thread(playerController).start();
		new Thread(obstacleController).start();
		new Thread(collisionDetector).start();
	}
	
	private void initializeSound() throws Exception {
		soundManager = new SoundManager(bulletController);
		
		new Thread(soundManager).start();
	}
	
	private void hideConfirmation() {
		header.setVisible(false);
		
		for (int i = 0; i < buttons.length - 1; i++) {
			buttons[i].setVisible(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String buttonText = ((CustomButton) event.getSource()).getText();
		
		if (buttonText.equals(buttonTexts[0])) {		// new game button...
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setText(buttonTexts[i + 3]);
			}
		}
		else if (buttonText.equals(buttonTexts[1])) {		// options button...
			JOptionPane.showMessageDialog(this, "Sorry, this feature is not available yet.", Utility.TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (buttonText.equals(buttonTexts[2])) {		// exit button...
			GameController.run = false;
			
			if (soundManager != null) {
				soundManager.close();
			}
			
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (buttonText.equals(buttonTexts[3])) {		// solo button...
			header.setVisible(false);
			
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setVisible(false);
			}
			
			GameController.run = GameController.playerAlive = escapeFlag = true;
			
			player.setLocation(defaultPlayerLocation);
			canvas.add(player);
			initializeEngine();
			
			try {
				initializeSound();
			}
			catch (Exception exception) {
				JOptionPane.showMessageDialog(this, "Sorry, we were unable to load sound.", Utility.TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (buttonText.equals(buttonTexts[4])) {		// multiplayer button...
			JOptionPane.showMessageDialog(this, "Sorry, this feature is not available yet.", Utility.TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		else if (buttonText.equals(buttonTexts[5])) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setText(buttonTexts[i]);
			}
		}
		else if (buttonText.equals(buttonTexts[6])) {		// yes button...
			GameController.run = GameController.playerAlive = false;
			
			canvas.remove(player);
			header.setText(titleTexts[1]);
			header.setFont(Utility.FONTS[1]);
			
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setText(buttonTexts[i]);
				buttons[i].setVisible(true);
			}
		}
		else if (buttonText.equals(buttonTexts[7])) {		// no button...
			escapeFlag = true;
			
			hideConfirmation();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		keyCode = event.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W) {
			playerController.setKey(0, 1);
		}
		else if (keyCode == KeyEvent.VK_A) {
			playerController.setKey(1, 1);
		}
		else if (keyCode == KeyEvent.VK_S) {
			playerController.setKey(2, 1);
		}
		else if (keyCode == KeyEvent.VK_D) {
			playerController.setKey(3, 1);
		}
		else if (keyCode == KeyEvent.VK_SPACE) {
			playerController.setKey(4, 1);
		}
		else if (keyCode == KeyEvent.VK_ESCAPE) {
			if (GameController.run) {
				if (escapeFlag) {
					int i = 0;
					
					header.setText(titleTexts[0]);
					header.setFont(Utility.FONTS[0]);
					header.setVisible(true);
					
					buttons[0].setText(buttonTexts[6]);
					buttons[1].setText(buttonTexts[7]);
					
					for (i = 0; i < buttons.length - 1; i++) {
						buttons[i].setVisible(true);
					}
					
					buttons[i].setVisible(false);
				}
				else {
					hideConfirmation();
				}
				
				escapeFlag = !escapeFlag;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		keyCode = event.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W) {
			playerController.setKey(0, 0);
		}
		else if (keyCode == KeyEvent.VK_A) {
			playerController.setKey(1, 0);
		}
		else if (keyCode == KeyEvent.VK_S) {
			playerController.setKey(2, 0);
		}
		else if (keyCode == KeyEvent.VK_D) {
			playerController.setKey(3, 0);
		}
		else if (keyCode == KeyEvent.VK_SPACE) {
			playerController.setKey(4, 0);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent event) { }
	
}