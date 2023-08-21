/**
 * SoundTest.java
 * This class is responsible for testing sound.
 * 
 * @author Hamad Ayaz, James Lee, Chris Brinkley, May Niu
 * @since 2023-08-06
 */

package test;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Test;

import model.Sound;

class SoundTest {

	@Test
	void test() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		String cwd = System.getProperty("user.dir");
		String filename = cwd + "/src/sound/VictorySoundEffect.wav";
		Sound player = new Sound(filename);
		player.play();
	}

}