package space.shooter.engine;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import space.shooter.Main;
import space.shooter.ui.CustomPanel;

public class BulletController implements Runnable {
	
	private volatile boolean shoot = false;
	
	private int bulletIndex = 0;
	
	private CustomPanel player, canvas;
	private CustomPanel[] bullets = new CustomPanel[50];
	
	private LinkedList<CustomPanel> bulletsOnCanvas = new LinkedList<CustomPanel>();
	
	public BulletController(CustomPanel player, CustomPanel canvas) {
		this.player = player;
		this.canvas = canvas;
		
		ImageIcon bulletImageIcon = new ImageIcon(this.getClass().getResource("/resources/images/bullet.png"));
		
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new CustomPanel(bulletImageIcon);
		}
	}
	
	private void spawnBullet() {
		if (bulletIndex == 50) {
			bulletIndex = 0;
		}
		
		CustomPanel bullet = bullets[bulletIndex];
		bullet.setLocation(player.getX() + (player.getWidth() / 2) - 7, player.getY() - 20);		// some calculations could've been pre-calculated...
		bulletsOnCanvas.add(bullet);
		canvas.add(bullet);
		
		bulletIndex++;
	}
	
	public void shoot() {
		shoot = true;
	}
	
	public LinkedList<CustomPanel> getBulletsOnCanvas() {
		return bulletsOnCanvas;
	}
	
	@Override
	public void run() {
		int i = 0;
		
		while (player.getParent() == canvas) {
			if (shoot) {
				spawnBullet();
				
				shoot = false;
			}
			
			if (bulletsOnCanvas.size() > 0) {
				for (i = 0; i < bulletsOnCanvas.size(); i++) {
					CustomPanel bullet = bulletsOnCanvas.get(i);
					
					if (bullet.getY() > bullet.getHeight() * -1) {
						bullet.setLocation(bullet.getX(), bullet.getY() - (1 * bulletsOnCanvas.size()));
						canvas.repaint();
						Main.sleep(1);
					}
					else {
						canvas.remove(bulletsOnCanvas.get(i));
						bulletsOnCanvas.remove(i);
					}
				}
			}
			else {
				Main.sleep(20);
			}
		}
	}
	
}