package space.shooter.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import space.shooter.Utility;
import space.shooter.engine.BulletController;
import space.shooter.engine.CollisionDetector;
import space.shooter.engine.ObstacleController;
import space.shooter.engine.PlayerController;
import space.shooter.sound.SoundManager;

public class Frame extends JFrame implements KeyListener {
	
	private int keyCode;
	private static final long serialVersionUID = -8726201312044312651L;
	
	private CustomComponent player, canvas;
	private CustomComponent[] bullets = new CustomComponent[50];
	private CustomComponent[][] obstacles = new CustomComponent[5][10];
	
	private BulletController bulletController;
	private PlayerController playerController;
	private ObstacleController obstacleController;
	
	private SoundManager soundManager;
	
	public Frame() throws Exception {
		Utility.run = true;
		
		initializeUI();
		initializeEngine();
		initializeSound();
	}
	
	private void initializeUI() {
		ImageIcon imageIcon;
		
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		
		canvas = new CustomComponent(CustomComponentType.CANVAS, Utility.screenDimension, new ImageIcon(this.getClass().getResource("/resources/images/canvas-background.jpg")));
		canvas.setLayout(null);
		setContentPane(canvas);
		
		player = new CustomComponent(CustomComponentType.PLAYER, Utility.playerDimension, new ImageIcon(this.getClass().getResource("/resources/images/player-1.png")));
		player.setLocation((canvas.getWidth() / 2) - (player.getWidth() / 2), canvas.getHeight() - player.getHeight() - Utility.screenOffset);
		canvas.add(player);
		
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
		
		new Thread(bulletController).start();
		new Thread(playerController).start();
		new Thread(obstacleController).start();
		new Thread(new CollisionDetector(player, canvas, bulletController.getBulletsOnCanvas(), obstacleController.getObstaclesOnCanvas())).start();		// parameter is null because we want collision detector to detect collision between player and obstacles...
		new Thread(new CollisionDetector(null, canvas, bulletController.getBulletsOnCanvas(), obstacleController.getObstaclesOnCanvas())).start();		// parameter is null because we want collision detector to detect collision between bullets and obstacles...
	}
	
	private void initializeSound() throws Exception {
		soundManager = new SoundManager(bulletController);
		
		new Thread(soundManager).start();
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
			Utility.run = false;
			
			soundManager.close();
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
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