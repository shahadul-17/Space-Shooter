package space.shooter.engine;

import java.util.LinkedList;

import space.shooter.Main;
import space.shooter.ui.CustomPanel;

public class CollisionDetector implements Runnable {
	
	private CustomPanel player, canvas;
	private LinkedList<CustomPanel> bullets, obstacles;
	
	public CollisionDetector(CustomPanel player, CustomPanel canvas, LinkedList<CustomPanel> bullets, LinkedList<CustomPanel> obstacles) {
		this.player = player;
		this.canvas = canvas;
		this.bullets = bullets;
		this.obstacles = obstacles;
	}
	
	@Override
	public void run() {
		int i = 0, j = 0;
		
		while (true) {
			try {
				if (bullets == null) {
					for (i = 0; i < obstacles.size(); i++) {
						CustomPanel stone = obstacles.get(i);
						
						if (stone.getParent() == canvas && stone.getBounds().intersects(player.getBounds())) {
							canvas.remove(player);
							
							break;
						}
					}
				}
				else {
					for (i = 0; i < bullets.size(); i++) {
						for (j = 0; j < obstacles.size(); j++) {
							CustomPanel obstacle = obstacles.get(j);
							
							if (bullets.get(i).getBounds().intersects(obstacle.getBounds())) {
								canvas.remove(obstacle);
								canvas.remove(bullets.get(i));
								
								break;
							}
						}
					}
				}
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
			
			Main.sleep(20);
		}
	}
	
}