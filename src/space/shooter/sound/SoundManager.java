package space.shooter.sound;

import space.shooter.Utility;
import space.shooter.engine.BulletController;

public class SoundManager implements Runnable {
	
	private BulletController bulletController;
	private SoundPlayer soundPlayer;
	
	public SoundManager(BulletController bulletController) throws Exception {
		this.bulletController = bulletController;
		soundPlayer = new SoundPlayer(this.getClass().getResource("/resources/sounds/shoot.wav"));
	}
	
	public void close() {
		soundPlayer.close();
	}
	
	@Override
	public void run() {
		while (Utility.run) {
			if (bulletController.isShot()) {
				soundPlayer.play();
			}
			
			Utility.sleep(100);
		}
	}
	
}