package space.shooter.engine;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import space.shooter.Main;
import space.shooter.ui.CustomPanel;

public class ObstacleController implements Runnable {
	
	private int counter = 0;
	private int[] obstacleIndices = new int[5];
	
	private Random random;
	private CustomPanel canvas;
	private CustomPanel[][] obstacles = new CustomPanel[obstacleIndices.length][10];
	
	private LinkedList<CustomPanel> obstaclesOnCanvas = new LinkedList<CustomPanel>();
	
	public ObstacleController(CustomPanel canvas) {
		this.canvas = canvas;
		random = new Random();
		
		for (int i = 0; i < obstacles.length; i++) {
			ImageIcon obstacleImageIcon = new ImageIcon(this.getClass().getResource("/resources/images/obstacle-" + (i + 1) + ".png"));
			
			for (int j = 0; j < obstacles[i].length; j++) {
				obstacles[i][j] = new CustomPanel(obstacleImageIcon);
			}
		}
	}
	
	private void spawnObstacle() {
		int randomNumber = random.nextInt(obstacles.length);
		
		if (obstacleIndices[randomNumber] > 9) {
			obstacleIndices[randomNumber] = 0;
		}
		
		CustomPanel stone = obstacles[randomNumber][obstacleIndices[randomNumber]];
		stone.setLocation(random.nextInt(canvas.getWidth() - 70) + 35, stone.getHeight() * -1);
		obstaclesOnCanvas.add(stone);
		canvas.add(stone);
		
		obstacleIndices[randomNumber]++;
	}
	
	public LinkedList<CustomPanel> getObstaclesOnCanvas() {
		return obstaclesOnCanvas;
	}
	
	@Override
	public void run() {
		while (Main.run) {
			if (counter == 50) {
				counter = 0;
				
				spawnObstacle();
			}
			
			if (obstaclesOnCanvas.size() > 0) {
				for (int i = 0; i < obstaclesOnCanvas.size(); i++) {
					CustomPanel stone = obstaclesOnCanvas.get(i);
					
					if (stone.getY() < canvas.getHeight()) {
						stone.setLocation(stone.getX(), stone.getY() + (1 * obstaclesOnCanvas.size()));
						canvas.repaint();
						Main.sleep(1);
					}
					else {
						canvas.remove(obstaclesOnCanvas.get(i));
						obstaclesOnCanvas.remove(i);
					}
				}
			}
			else {
				Main.sleep(20);
			}
			
			counter++;
		}
	}
	
}