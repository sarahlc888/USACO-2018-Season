import java.io.*;
import java.util.*;

public class GameScore {
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 10; i++) {
			processOneFile("testData-2/" + (i+10) + ".in");
		}
	}
	public static void processOneFile(String filename) throws IOException{
		// set instead of array because of size changing due to duplicates
		// DP - use 2D array to keep track of all the rounds/partial progress
		Scanner scan = new Scanner(new File(filename));
		int n = scan.nextInt(); // number rounds
		int m = scan.nextInt(); // number of possible scores per round
		int[] scores = new int[m];
		for (int i = 0; i < m; i++) {
			scores[i] = scan.nextInt();
		}
		HashSet<Integer> prevSet = new HashSet<Integer>();
		for (int i = 0; i < n; i++) {
			// loop through the rounds
			// could do array and then add all at once to curSet to make faster
			HashSet<Integer> curSet = new HashSet<Integer>();
			
			if (prevSet.size() == 0) {
				for (int j = 0; j < scores.length; j++) {
					curSet.add(scores[j]); // add new score to the set
				}
			} else {
				for (int x : prevSet) {
					//System.out.println(x);
					for (int j = 0; j < scores.length; j++) {
						curSet.add(x + scores[j]); // add new score to the set
					}
				}
			}
			
			
			prevSet = curSet;
			// not m^n because the size of the set is not consistently growing with exp (no duplicates)
		}
		System.out.println(prevSet.size());
	}
}
