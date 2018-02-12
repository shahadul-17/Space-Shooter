package space.shooter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class Utility {
	
	public static int screenOffset;
	public static final int BASE_SCREEN_OFFSET = 50;
	
	public static final String TITLE = "Space Shooter";
	
	public static Dimension playerDimension, bulletDimension, screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Dimension BASE_BULLET_DIMENSION = new Dimension(16, 15),
			BASE_PLAYER_DIMENSION = new Dimension(157, 155),
			BASE_SCREEN_DIMENSION = new Dimension(1920, 1080);		// Full HD resolution...
	public static Dimension[] obstacleDimensions;
	private static final Dimension[] BASE_OBSTACLE_DIMENSIONS = {
		new Dimension(50, 52),		// obstacle-1
		new Dimension(60, 50),		// obstacle-2
		new Dimension(82, 50),		// obstacle-3
		new Dimension(62, 50),		// obstacle-4
		new Dimension(50, 55)		// obstacle-5
	};
	
	public static final Font[] FONTS = {
		new Font("MV Boli", Font.PLAIN, 46),
		new Font("MV Boli", Font.BOLD, 88)
	};
	
	public static int scaleRegardingScreenWidth(int value) {
		return (value * screenDimension.width) / BASE_SCREEN_DIMENSION.width;
	}
	
	public static int scaleRegardingScreenHeight(int value) {
		return (value * screenDimension.height) / BASE_SCREEN_DIMENSION.height;
	}
	
	public static void calculateDimensions() {
		if (BASE_SCREEN_DIMENSION.equals(screenDimension)) {
			screenOffset = BASE_SCREEN_OFFSET;
			bulletDimension = BASE_BULLET_DIMENSION;
			playerDimension = BASE_PLAYER_DIMENSION;
			obstacleDimensions = BASE_OBSTACLE_DIMENSIONS;
		}
		else {
			screenOffset = scaleRegardingScreenWidth(BASE_SCREEN_OFFSET);
			bulletDimension = new Dimension(scaleRegardingScreenWidth(BASE_BULLET_DIMENSION.width), scaleRegardingScreenHeight(BASE_BULLET_DIMENSION.height));
			playerDimension = new Dimension(scaleRegardingScreenWidth(BASE_PLAYER_DIMENSION.width), scaleRegardingScreenHeight(BASE_PLAYER_DIMENSION.height));
			obstacleDimensions = new Dimension[BASE_OBSTACLE_DIMENSIONS.length];
			
			for (int i = 0; i < obstacleDimensions.length; i++) {
				obstacleDimensions[i] = new Dimension(scaleRegardingScreenWidth(BASE_OBSTACLE_DIMENSIONS[i].width), scaleRegardingScreenHeight(BASE_OBSTACLE_DIMENSIONS[i].height));
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