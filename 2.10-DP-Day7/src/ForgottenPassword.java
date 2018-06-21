import java.io.*;
import java.util.*;

/*
 * 10/10 test cases
 * 
 * dp[position] = best string ending in position
 * 
 * loop through all the words, j, if it fits, then...
 * words starts at pos[j]
 * look at dp[pos[j] - 1]+newWord
 * find the best, lexographically least
 * 
 * O(l * NW)
 */
public class ForgottenPassword {
	public static void main(String args[]) throws IOException {
		// INPUT
		Scanner scan = new Scanner((System.in));
		StringTokenizer st = new StringTokenizer(scan.nextLine());

		int L = Integer.parseInt(st.nextToken()); // length of password
		int NW = Integer.parseInt(st.nextToken()); // number of words
		String pw = scan.nextLine(); // password
		String[] dict = new String[NW];
		for (int i = 0; i < NW; i++) {
			dict[i] = scan.nextLine();
		}
		scan.close();
		Arrays.sort(dict); // sort
		// DP[pos] = best string you can make that ends in position
		String[] dp = new String[L]; // initialize into some nonsense string
		for (int i = 0; i < L; i++) {
			dp[i] = "";
		}
		
		for (int i = 0; i < L; i++) { // loop through positions
			for (int j = 0; j < NW; j++) { // loop through the words
				//System.out.println("i: " + i + " j: " + j );
				////System.out.println("  " + dict[j]);
				// check if it fits
				int sind = i-dict[j].length()+1;
				if (sind < 0) {
					continue;
				} 
				//System.out.println("sind: " + sind);
				String pwSub = pw.substring(sind, i+1);
				////System.out.println("  " + pwSub);
				boolean equal = true;
				
				for (int k = 0; k < pwSub.length(); k++) {
					if (dict[j].charAt(k) == pwSub.charAt(k) || pwSub.charAt(k) == '?') {
						// same
					} else { 
						equal = false;
						break;
					}
				}
				/*
				for (int k = sind; k < sind+dict[j].length(); k++) { // loop through indices
					if (dict[j].charAt(k-sind) == pw.charAt(k) || pw.charAt(k) == '?') {
						// same
					} else { 
						equal = false;
					}
				}*/
				
				if (equal ) { 
					// if the word fits and comes before dp[i], put it in
					// TODO: max it?
					//System.out.println("here");
					String temp;
					if (sind == 0) {
						temp = dict[j];
					} else {
						//System.out.println("  prev: " + dp[sind-1]);
						temp = dp[sind-1]+dict[j];
					}
					
					//System.out.println("  temp: " + temp);

					if (temp.length()<i) {
						//System.out.println("!here");
						continue;
					}
					if (dp[i].compareTo(temp) > 0 || dp[i] == "") {
						//System.out.println("dp[i]: " + dp[i]);
						dp[i] = temp;
					}
					
					
				}
				
			}

		}
		//System.out.println(Arrays.toString(dp));
		System.out.println(dp[L-1]);
		
	}
}
