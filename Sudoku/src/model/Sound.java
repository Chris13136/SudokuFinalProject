/**
 * Sound.java
 * This class, Sound, can take a wav file and play it
 * 
 * @author Hamad Ayaz, James Lee, Chris Brinkley, May Niu
 * @since 2023-08-06
 */
package model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private Clip clip;

	/**
	 * Constructor
	 * 
	 * @param wavFileName
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 */
	public Sound(String wavFileName) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		File wavFile = new File(wavFileName);
		AudioInputStream audioStream;
		audioStream = AudioSystem.getAudioInputStream(wavFile);
		// Creates a clip object through a file
		clip = AudioSystem.getClip();
		clip.open(audioStream);
	}

	/**
	 * This method plays the sound file
	 * 
	 * @throws InterruptedException
	 */
	public void play() throws InterruptedException {
		if (clip != null) {
			clip.start();
		}
	}
}