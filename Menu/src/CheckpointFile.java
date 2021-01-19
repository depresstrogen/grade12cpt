import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class loads checkpoints from a .race file
 * The file structure is as follows
 * <code>(Checkpoint Type) x1 y1 x2 y2 </code>
 * as an example
 * <code> Start 100 100 400 400 </code>
 * 
 * @version January 18, 2021
 * @author Riley Power
 *
 */
public class CheckpointFile {
	FileReader in;
	BufferedReader readFile;
	String lineOfText;
	String scoreNameParsed;
	static String top5Names[] = new String[5];
	static int top5Scores[] = new int[5];

	
	/**
	 * Loads a .race file into a Checkpoint array list and returns it
	 * @return An array with every checkpoint in the specified race
	 */
	public ArrayList<Checkpoint> readCheckpoints() {
		ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		int x1 = 0;
		int x2 = 0;
		int y1 = 1;
		int y2 = 2;
		String type = "";
		File checkpointFile = new File("Race Files/race1.race");
		try {
			in = new FileReader(checkpointFile);
			readFile = new BufferedReader(in);
			int i = 0;
			//Runs until there is nothing left in the file
			while ((lineOfText = readFile.readLine()) != null) {
				int j = 0;
				int stringIndex = -1;
				//Parse each line
				do {
					lineOfText = lineOfText.substring(stringIndex + 1);
					stringIndex = lineOfText.indexOf(' ');
					//Each value has to be in a certain variable and this was the best way i could do it
					switch(j) {
					case 0:
						type = lineOfText.substring(0, stringIndex);
						break;
					case 1:
						x1 = Integer.parseInt(lineOfText.substring(0, stringIndex));
						break;
					case 2:
						y1 = Integer.parseInt(lineOfText.substring(0, stringIndex));
						break;
					case 3:
						x2 = Integer.parseInt(lineOfText.substring(0, stringIndex));
						break;
					case 4:
						y2 = Integer.parseInt(lineOfText.substring(0));
						break;
					}
					j ++;
				} while(j < 5);
				Checkpoint tempCheckpoint = new Checkpoint(x1, y1 , y2 - y1, x2 - x1 , "CP" + i, type);
				checkpoints.add(tempCheckpoint);
				i ++;
			}
			//Memory leaks not pog
			readFile.close();
			in.close();
		} catch (IOException e) {
			//Errors not pog
			System.out.println("Problem reading file");
			System.err.println("IOExeption: " + e.getMessage());
		}
		return checkpoints;
	}//readCheckpoints
}
