import java.io.File;
import javax.sound.sampled.*;

/**
 * Plays music stored as .WAV files
 * 
 * @version January 19 2021
 * @author Riley Power
 *
 */
public class MusicPlayer {
	/**
	 * Plays the specified file through the default speakers
	 * 
	 * @param dir The file to be played
	 */
	public void play(String dir) {
		File mp3 = new File(dir);
		try {
			// Audio streams
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(mp3);
			AudioFormat format = audioIn.getFormat();
			// .class is used here to get the type of audio used
			DataLine.Info audioInfo = new DataLine.Info(Clip.class, format);
			// Clip object is used for the actual playing
			Clip audio = (Clip) AudioSystem.getLine(audioInfo);
			System.out.println("Playing " + dir);
			audio.open(audioIn);
			audio.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}// play
}// MusicPlayer
