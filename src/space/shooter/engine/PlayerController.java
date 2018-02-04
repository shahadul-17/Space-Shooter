package space.shooter.engine;

import java.awt.Point;

import space.shooter.Utility;
import space.shooter.ui.CustomComponent;

public class PlayerController implements Runnable {
	
	private volatile int[] keys = new int[5];
	
	private Point playerLocation;
	private CustomComponent player, canvas;
	private BulletController bulletController;
	
	public PlayerController(CustomComponent player, CustomComponent canvas, BulletController bulletController) {
		this.player = player;
		this.canvas = canvas;
		this.bulletController = bulletController;
		
		playerLocation = player.getLocation();
	}
	
	public void setKey(int index, int value) {
		keys[index] = value;
	}
	
	@Override
	public void run() {
		while (Utility.run) {
			if (keys[0] == 1 && playerLocation.y > Utility.screenOffset) {
				playerLocation.y -= 5;
			}
			
			if (keys[1] == 1 && playerLocation.x > Utility.screenOffset) {
				playerLocation.x -= 5;
			}
			
			if (keys[2] == 1 && playerLocation.y < canvas.getHeight() - player.getHeight() - Utility.screenOffset) {
				playerLocation.y += 5;
			}
			
			if (keys[3] == 1 && playerLocation.x < canvas.getWidth() - player.getWidth() - Utility.screenOffset) {
				playerLocation.x += 5;
			}
			
			if (keys[4] == 1) {
				bulletController.shoot();
			}
			
			player.setLocation(playerLocation);
			canvas.repaint();
			Utility.sleep(5);
		}
	}
	
}