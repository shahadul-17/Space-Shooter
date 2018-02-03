package space.shooter;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Utility {
	
	public static volatile boolean run = false;
	public static final int SCREEN_OFFSET = 35;
	
	public static final String TITLE = "Space Shooter";
	private static final Dimension BASE_BULLET_DIMENSION = new Dimension(16, 15),
			BASE_PLAYER_DIMENSION = new Dimension(157, 155),
			BASE_SCREEN_DIMENSION = new Dimension(1920, 1080);		// Full HD resolution...
	
	private static final Dimension[] BASE_OBSTACLE_DIMENSIONS = {
		new Dimension(50, 52),		// obstacle-1
		new Dimension(60, 50),		// obstacle-2
		new Dimension(82, 50),		// obstacle-3
		new Dimension(62, 50),		// obstacle-4
		new Dimension(50, 55)		// obstacle-5
	};
	
	public static Dimension playerDimension, bulletDimension, screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	public static Dimension[] obstacleDimensions;
	
	public static void calculateDimensions() {
		if (BASE_SCREEN_DIMENSION.equals(screenDimension)) {
			bulletDimension = BASE_BULLET_DIMENSION;
			playerDimension = BASE_PLAYER_DIMENSION;
			obstacleDimensions = BASE_OBSTACLE_DIMENSIONS;
		}
		else {
			bulletDimension = new Dimension((BASE_BULLET_DIMENSION.width * screenDimension.width) / BASE_SCREEN_DIMENSION.width, (BASE_BULLET_DIMENSION.height * screenDimension.height) / BASE_SCREEN_DIMENSION.height);
			playerDimension = new Dimension((BASE_PLAYER_DIMENSION.width * screenDimension.width) / BASE_SCREEN_DIMENSION.width, (BASE_PLAYER_DIMENSION.height * screenDimension.height) / BASE_SCREEN_DIMENSION.height);
			obstacleDimensions = new Dimension[BASE_OBSTACLE_DIMENSIONS.length];
			
			for (int i = 0; i < obstacleDimensions.length; i++) {
				obstacleDimensions[i] = new Dimension((BASE_OBSTACLE_DIMENSIONS[i].width * screenDimension.width) / BASE_SCREEN_DIMENSION.width, (BASE_OBSTACLE_DIMENSIONS[i].height * screenDimension.height) / BASE_SCREEN_DIMENSION.height);
			}
		}
	}
	
	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}