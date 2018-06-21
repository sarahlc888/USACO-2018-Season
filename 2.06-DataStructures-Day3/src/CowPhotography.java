import java.io.*;
import java.util.*;

/*
 * 10/10 test cases
 */

public class CowPhotography {
	static ArrayList<HashMap<Integer, Integer>> loc;
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // num cows
		
		int[][] photos = new int[5][N]; // photos
		ArrayList<Integer> cows = new ArrayList<Integer>();
		
		// maps to store position of cow
		// loc.get(i) represents photo i
		loc = new ArrayList<HashMap<Integer, Integer>>();
		
		for (int i = 0; i < 5; i++) { // loop through 5 photos
			
			loc.add(new HashMap<Integer, Integer>()); // initialize the hashmap
			
			for (int j = 0; j < N; j++) { // loop through the cows
				String in = (scan.nextLine());
				while (in.charAt(in.length()-1) == ' ') {
					in = in.substring(0, in.length()-1);
				}
				photos[i][j] = Integer.parseInt(in);
				
				if (i == 0) {
					cows.add(photos[i][j]);
				}
				
				loc.get(i).put(photos[i][j], j); // id to location map
				
			}
		}
		
		// sort the cows
		Collections.sort(cows, integComparator);
		
		for (int i = 0; i < N; i++) {
			System.out.println(cows.get(i));
		}
		
	}
	public static Comparator<Integer> integComparator = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			// cows with id x and y
			int count = 0;
			for (int i = 0; i < loc.size(); i++) {
				if (loc.get(i).get(x) < loc.get(i).get(y)) {
					count++; // if x is before y
				}
			}
			if (count >= 3) {
				// if for most photos x is before y, x is before y
				return -1;
			} else {
				return 1;
			}
			
			
		}
		
		
		
		
	};
}
