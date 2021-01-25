import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
	public void play(ArrayList<String> songs) {
		for (int i = 0; i < songs.size(); i++) {
			File mp3 = new File(songs.get(i));
			try {
				// Audio streams
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(mp3);
				AudioFormat format = audioIn.getFormat();
				// .class is used here to get the type of audio used
				DataLine.Info audioInfo = new DataLine.Info(Clip.class, format);
				// Clip object is used for the actual playing
				Clip audio = (Clip) AudioSystem.getLine(audioInfo);
				System.out.println("Playing " + songs.get(i));
				audio.open(audioIn);
				audio.start();
				try {
					System.out.print(audio.getMicrosecondLength());
					Thread.sleep(audio.getMicrosecondLength() / 1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		shuffle();
	}// play

	/**
	 * Randomizes the songs to be played, and then calls the play method
	 */
	public void shuffle() {
		FileReader in;
		BufferedReader readFile;
		String lineOfText;
		ArrayList<String> songs = new ArrayList<String>();
		File checkpointFile = new File("Music Files/songs.txt");
		try {
			in = new FileReader(checkpointFile);
			readFile = new BufferedReader(in);
			// Runs until there is nothing left in the file
			while ((lineOfText = readFile.readLine()) != null) {
				songs.add("Music Files/" + lineOfText);
			}
			// Memory leaks not pog
			readFile.close();
			in.close();
		} catch (IOException e) {
			// Errors not pog
			System.out.println("Problem reading file");
			System.err.println("IOExeption: " + e.getMessage());
		}
		Collections.shuffle(songs);
		for (int i = 0; i < songs.size(); i++) {
			System.out.println(songs.get(i));
		}
		play(songs);
	}// shuffle
}// MusicPlayer
