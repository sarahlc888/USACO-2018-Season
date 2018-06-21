import java.io.*;
import java.util.*;
/*
 * USACO 2016 January Contest, Gold
 * Problem 3. Lights Out
 * 
 * 4/10 test cases, forgot to add modulos in the first half of processCorner 
 * 10/10 test cases
 * 
 * String manipulation
 * 
 * copy and pasted from 1.67
 */ 
public class LightsOut {
	public static int[][] barn;
	public static int N;
	public static String path;
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		//BufferedReader br = new BufferedReader(new FileReader("testData/3.in"));
		N = Integer.parseInt(scan.nextLine());
		barn = new int[N][2];
		for (int i = 0; i < N; i++) { // scan all the corners in
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			barn[i][0] = Integer.parseInt(st.nextToken());
			barn[i][1] = Integer.parseInt(st.nextToken());
			//System.out.println(Arrays.toString(barn[i]));
		}
		scan.close();
		
		int[] pathind = new int[N+1]; // store the index of each corner in path string
		pathind[0] = -1; // exit doesn't get put in
		
		path = ""; // string to represent barn in clockwise path
		
		for (int i = 1; i < N; i++) { // loop through the corners
			
			// number of steps to reach the current corner i
			path += Math.max(Math.abs(barn[i][0]-barn[i-1][0]), Math.abs(barn[i][1]-barn[i-1][1]));
			
			// what kind of corner (L or R)
			path += processCorner(i);
			pathind[i] = path.length()-1; // mark the index in path
		}
		pathind[N] = path.length(); // for when you want to get the whole path string
		
		// number of steps to reach the exit from the last corner
		path += Math.max(Math.abs(barn[N-1][0]-barn[0][0]), Math.abs(barn[N-1][1]-barn[0][1]));
		
		//System.out.println("path: " + path);
		//System.out.println(Arrays.toString(pathind));
		
		int max = 0; // max difference
		
		for (int i = 1; i < N; i++) { // starting corner index
			
			// get actual indices of corner i and j in the path string
			int sind = pathind[i];
			int eind = -1;
			
			//System.out.println("i: " + i + " sind: " + sind);
			
			int j = -1; // ending corner index (exclusive)
			
			String sub = ""; // string between the two points
			
			for (j = i+1; j <= N; j++) {
				eind = pathind[j]; // update eind
				//System.out.println("  j: " + j);
				//System.out.println(" eind: " + eind);
				sub = path.substring(sind, eind+1); // substring, include ending corner 
				
				//System.out.println("  j: " + j + " sub: " + sub);
				
				if (!path.substring(path.indexOf(sub)+1).contains(sub)) {
					// if path doesn't contain the substring again,
					// she can identify her position
					break;	
				}
				
			}
			// she is at position j-1, decrement j to make it her position
			j--;
			
			// find the shortest possible path to the exit from this point
			int endDist = distToExit(eind);

			// length of the path
			int dist = endDist + count(sub);
			
			//System.out.println("  ogdist: " + distToExit(sind) + " dist: " + dist);
			
			int diff = dist - distToExit(sind);
			
			if (diff > max) max = diff;
			//System.out.println("diff: " + diff);
		}
		
		System.out.println(max);
		
	}
	public static int distToExit(int pos) {
		// returns distance to the nearest exit from given position
		int fDist = count(path.substring(pos+1));
		int bDist = count(path.substring(0, pos));
		
		/*
		System.out.println("  forward: " + path.substring(pos+1));
		System.out.println("  backward: " + path.substring(0, pos));
		
		System.out.println("  fDist: " + count(path.substring(pos+1)));
		System.out.println("  bDist: " + count(path.substring(0, pos)));
		*/
		
		return Math.min(fDist, bDist);
	}
	public static int count(String s) {
		// adds all the integers together in a string
		if (s.equals("")) return 0;
		int total = 0;
		int lastLetter = -1;
		int ind = 0;
		while (ind < s.length()) {
			if (!Character.isDigit(s.charAt(ind))) { // if the character is not a digit
				String num = s.substring(lastLetter+1, ind);
				int numVal;
				if (num.equals("")) {
					numVal = 0;
				} else {
					numVal = Integer.parseInt(num);
				}
			
				total += numVal;
				lastLetter = ind;
			}
			ind++;
		}
		
		if (lastLetter == -1) {
			// if there were no letters, the whole thing is just a number
			total = Integer.parseInt(s);
		} else if (lastLetter != s.length()-1) {
			// if the last thing isn't a letter, get the number after it
			total += Integer.parseInt(s.substring(lastLetter+1));
		}
		
		return total;
		
	}
	public static String processCorner(int i) {
		// returns L or R for the current corner
		
		if (barn[i][0] < barn[i-1][0] && barn[i][1] < barn[(i+1)%N][1]) {
			// i+1
			//  i	i-1
			return "R";
		} else if (barn[i][0] < barn[i-1][0] && barn[i][1] > barn[(i+1)%N][1]) {
			//  i	i-1
			// i+1
			return "L";
		} else if (barn[i][0] > barn[i-1][0] && barn[i][1] < barn[(i+1)%N][1]) {
			//	 	i+1
			// i-1	i
			return "L";
		} else if (barn[i][0] > barn[i-1][0] && barn[i][1] > barn[(i+1)%N][1]) {
			// i-1	i
			//	 	i+1
			return "R";
		}
		
		else if (barn[i][1] < barn[i-1][1] && barn[i][0] < barn[(i+1)%N][0]) {
			// i-1
			//  i	i+1
			return "L";
		} else if (barn[i][1] < barn[i-1][1] && barn[i][0] > barn[(i+1)%N][0]) {
			// 		i-1	
			// i+1	i
			return "R";
		} else if (barn[i][1] > barn[i-1][1] && barn[i][0] < barn[(i+1)%N][0]) {
			// i		i+1
			// i-1	
			return "R";
		} else if (barn[i][1] > barn[i-1][1] && barn[i][0] > barn[(i+1)%N][0]) {
			// i+1	i	
			// 		i-1	
			return "L";
		} else {
			return "ERROR";
		}
		
	}

}
