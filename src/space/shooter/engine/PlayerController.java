package space.shooter.engine;

import java.awt.Point;
import java.util.LinkedList;

import space.shooter.Main;
import space.shooter.ui.CustomPanel;

public class PlayerController implements Runnable {
	
	private volatile int[] keys = new int[5];
	
	private CustomPanel player, canvas;
	private Point playerLocation;
	private BulletController bulletController;
	
	public PlayerController(CustomPanel player, CustomPanel canvas) {
		this.player = player;
		this.canvas = canvas;
		this.playerLocation = player.getLocation();
		bulletController = new BulletController(player, canvas);
		
		new Thread(bulletController).start();
	}
	
	public void setKey(int index, int value) {
		keys[index] = value;
	}
	
	public LinkedList<CustomPanel> getBulletsOnCanvas() {
		return bulletController.getBulletsOnCanvas();
	}
	
	@Override
	public void run() {
		while (Main.run) {
			if (keys[0] == 1 && playerLocation.y > 35) {
				playerLocation.y -= 5;
			}
			
			if (keys[1] == 1 && playerLocation.x > 35) {
				playerLocation.x -= 5;
			}
			
			if (keys[2] == 1 && playerLocation.y < canvas.getHeight() - player.getHeight() - 35) {
				playerLocation.y += 5;
			}
			
			if (keys[3] == 1 && playerLocation.x < canvas.getWidth() - player.getWidth() - 35) {
				playerLocation.x += 5;
			}
			
			if (keys[4] == 1) {
				bulletController.shoot();
			}
			
			player.setLocation(playerLocation);
			canvas.repaint();
			Main.sleep(5);
		}
	}
	
}