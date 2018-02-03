package space.shooter.engine;

import java.util.LinkedList;
import java.util.Random;

import space.shooter.Utility;
import space.shooter.ui.CustomComponent;

public class ObstacleController implements Runnable {
	
	private int counter = 0;
	private int[] obstacleIndices = new int[5];
	
	private Random random;
	private CustomComponent canvas;
	private CustomComponent[][] obstacles;
	
	private LinkedList<CustomComponent> obstaclesOnCanvas = new LinkedList<CustomComponent>();
	
	public ObstacleController(CustomComponent canvas, CustomComponent[][] obstacles) {
		this.canvas = canvas;
		this.obstacles = obstacles;
		random = new Random();
	}
	
	private void spawnObstacle() {
		int randomNumber = random.nextInt(obstacles.length);
		
		if (obstacleIndices[randomNumber] > 9) {
			obstacleIndices[randomNumber] = 0;
		}
		
		CustomComponent stone = obstacles[randomNumber][obstacleIndices[randomNumber]];
		stone.setLocation(random.nextInt(canvas.getWidth() - stone.getWidth() - Utility.SCREEN_OFFSET) + Utility.SCREEN_OFFSET, stone.getHeight() * -1);
		obstaclesOnCanvas.add(stone);
		canvas.add(stone);
		
		obstacleIndices[randomNumber]++;
	}
	
	public LinkedList<CustomComponent> getObstaclesOnCanvas() {
		return obstaclesOnCanvas;
	}
	
	@Override
	public void run() {
		while (Utility.run) {
			if (counter == 50) {
				counter = 0;
				
				spawnObstacle();
			}
			
			if (obstaclesOnCanvas.size() > 0) {
				for (int i = 0; i < obstaclesOnCanvas.size(); i++) {
					CustomComponent stone = obstaclesOnCanvas.get(i);
					
					if (stone.getY() < canvas.getHeight()) {
						stone.setLocation(stone.getX(), stone.getY() + (1 * obstaclesOnCanvas.size()));
						canvas.repaint();
						Utility.sleep(1);
					}
					else {
						canvas.remove(obstaclesOnCanvas.get(i));
						obstaclesOnCanvas.remove(i);
					}
				}
			}
			else {
				Utility.sleep(20);
			}
			
			counter++;
		}
	}
	
}