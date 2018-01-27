package space.shooter.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import space.shooter.Main;
import space.shooter.engine.CollisionDetector;
import space.shooter.engine.ObstacleController;
import space.shooter.engine.PlayerController;

public class Frame extends JFrame implements KeyListener {
	
	private int keyCode;
	private static final long serialVersionUID = -8726201312044312651L;
	
	private CustomPanel player, canvas;
	private PlayerController playerController;
	private ObstacleController obstacleController;
	
	public Frame() {
		Main.run = true;
		
		initializeUI();
		initializeEngine();
	}
	
	private void initializeUI() {
		setResizable(false);
		setSize(1024, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		
		canvas = new CustomPanel(new ImageIcon(this.getClass().getResource("/resources/images/canvas-background.jpg")));
		canvas.setLayout(null);
		setContentPane(canvas);
		
		player = new CustomPanel(new ImageIcon(this.getClass().getResource("/resources/images/player.png")));
		player.setLocation(471, 490);
		canvas.add(player);
	}
	
	private void initializeEngine() {
		playerController = new PlayerController(player, canvas);
		obstacleController = new ObstacleController(canvas);
		
		new Thread(playerController).start();
		new Thread(obstacleController).start();
		new Thread(new CollisionDetector(player, canvas, null, obstacleController.getObstaclesOnCanvas())).start();		// parameter is null because we want collision detector to detect collision between player and obstacles...
		new Thread(new CollisionDetector(null, canvas, playerController.getBulletsOnCanvas(), obstacleController.getObstaclesOnCanvas())).start();		// parameter is null because we want collision detector to detect collision between bullets and obstacles...
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