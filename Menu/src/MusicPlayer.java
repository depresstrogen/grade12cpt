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
 * @version January 26 2021
 * @author Riley Power
 *
 */
public class MusicPlayer {
	private boolean skipped = false;

	/**
	 * Plays the specified file through the default speakers
	 * 
	 * @param dir The file to be played
	 */
	public void play(ArrayList<String> songs) {
		long songEndTime;
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
				// Allows songs to be skipped
				songEndTime = System.currentTimeMillis() + (audio.getMicrosecondLength() / 1000);
				System.out.print(audio.getMicrosecondLength());
				while (System.currentTimeMillis() < songEndTime) {
					System.out.print("");
					if (skipped) {
						songEndTime = System.currentTimeMillis();
						System.out.println("Skipped");
						skipped = false;
						audio.stop();
					}
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		shuffle(false);
	}// play

	/**
	 * Sets skipped to true, stopping the current song and playing the next song in
	 * queue
	 */
	public void skipTrack() {
		skipped = true;
	}// skipTrack

	/**
	 * Randomizes the songs to be played, and then calls the play method
	 * @param rickroll Puts rick.wav at the beginning of the song queue
	 */
	public void shuffle(boolean rickroll) {
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

		// The following code is just to rickroll lol
		ArrayList<String> temp = new ArrayList<String>();

		if (rickroll) {
			temp.add("Music Files/rick.wav");
		}

		for (int i = 0; i < songs.size(); i++) {
			temp.add(songs.get(i));
		}

		for (int i = 0; i < temp.size(); i++) {
			System.out.println(temp.get(i));
		}
		play(temp);
	}// shuffle
}// MusicPlayer
