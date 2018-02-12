package space.shooter.engine;

import java.util.LinkedList;

import space.shooter.Utility;
import space.shooter.ui.CustomComponent;

public class BulletController implements Runnable {
	
	private volatile boolean shoot = false;
	
	private int bulletIndex = 0;
	
	private CustomComponent player, canvas;
	private CustomComponent[] bullets;
	
	private LinkedList<CustomComponent> bulletsOnCanvas = new LinkedList<CustomComponent>();
	
	public BulletController(CustomComponent player, CustomComponent canvas, CustomComponent[] bullets) {
		this.player = player;
		this.canvas = canvas;
		this.bullets = bullets;
	}
	
	private void spawnBullet() {
		if (bulletIndex == 50) {
			bulletIndex = 0;
		}
		
		CustomComponent bullet = bullets[bulletIndex];
		bullet.setLocation(player.getX() + (player.getWidth() / 2) - 7, player.getY() - 20);		// some calculations could've been pre-calculated...
		bulletsOnCanvas.add(bullet);
		canvas.add(bullet);
		
		bulletIndex++;
	}
	
	public void shoot() {
		shoot = true;
	}
	
	public boolean isShot() {
		return shoot;
	}
	
	public LinkedList<CustomComponent> getBulletsOnCanvas() {
		return bulletsOnCanvas;
	}
	
	@Override
	public void run() {
		int i = 0;
		
		while (GameController.playerAlive) {
			if (shoot) {
				spawnBullet();
				
				shoot = false;
			}
			
			if (bulletsOnCanvas.size() > 0) {
				for (i = 0; i < bulletsOnCanvas.size(); i++) {
					CustomComponent bullet = bulletsOnCanvas.get(i);
					
					if (bullet.getY() > bullet.getHeight() * -1) {
						bullet.setLocation(bullet.getX(), bullet.getY() - (1 * bulletsOnCanvas.size()));
						canvas.repaint();
						Utility.sleep(1);
					}
					else {
						canvas.remove(bulletsOnCanvas.get(i));
						bulletsOnCanvas.remove(i);
					}
				}
			}
			else {
				Utility.sleep(20);
			}
		}
		
		for (i = 0; i < bulletsOnCanvas.size(); i++) {		// clearing the bullet(s) from screen...
			canvas.remove(bulletsOnCanvas.get(i));
		}

		bulletsOnCanvas.clear();
	}
	
}