package space.shooter.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	
	private Clip clip;
	private AudioInputStream audioInputStream;
	
	public SoundPlayer(URL url) throws Exception {
		clip = AudioSystem.getClip();
		audioInputStream = AudioSystem.getAudioInputStream(url);
		clip.open(audioInputStream);
	}
	
	protected void play() {
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	protected void close() {
		clip.stop();
		clip.close();
	}
	
}