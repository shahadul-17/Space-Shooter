package space.shooter.engine;

import java.util.LinkedList;

import space.shooter.Utility;
import space.shooter.ui.CustomComponent;

public class CollisionDetector implements Runnable {
	
	private CustomComponent player, canvas;
	private LinkedList<CustomComponent> bullets, obstacles;
	
	public CollisionDetector(CustomComponent player, CustomComponent canvas, LinkedList<CustomComponent> bullets, LinkedList<CustomComponent> obstacles) {
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
						CustomComponent stone = obstacles.get(i);
						
						if (stone.getParent() == canvas && stone.getBounds().intersects(player.getBounds())) {
							canvas.remove(player);
							
							break;
						}
					}
				}
				else {
					for (i = 0; i < bullets.size(); i++) {
						for (j = 0; j < obstacles.size(); j++) {
							CustomComponent obstacle = obstacles.get(j);
							
							if (bullets.get(i).getBounds().intersects(obstacle.getBounds())) {
								canvas.remove(obstacle);
								obstacles.remove(obstacle);
								canvas.remove(bullets.get(i));
								bullets.remove(i);
								
								break;
							}
						}
					}
				}
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
			
			Utility.sleep(20);
		}
	}
	
}