import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*
 * 10/10 test cases
 * insert, delete, or replace until s1 == s2
 * 1...N indexing
 */
public class StringEditDistance {
	static int min = Integer.MAX_VALUE;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s1 = br.readLine();
		String s2 = br.readLine();
		s1 = " "+s1;
		s2 = " "+s2;
		int[][] DP = new int[s1.length()][s2.length()];
		// initialize
		for (int i = 0; i < s1.length(); i++) {
			
			DP[i][0] = i;
		}
		for (int j = 0; j < s2.length(); j++) {
			DP[0][j] = j;
		}
		
		DP[0][0] = 0;
		//System.out.println(Arrays.toString(DP[0]));
		for (int i = 1; i < s1.length(); i++) {
			for (int j = 1; j < s2.length(); j++) {
				if (s1.charAt(i) == s2.charAt(j) && DP[i-1][j-1] != Integer.MAX_VALUE) {
					// case: same char, do nothing, no increase, ideal case
					DP[i][j] = DP[i-1][j-1];
				} else if (DP[i-1][j-1] != Integer.MAX_VALUE || 
						DP[i-1][j] != Integer.MAX_VALUE || 
						DP[i][j-1] != Integer.MAX_VALUE) { 
					// cases: delete i, insert, replace
					DP[i][j] = Math.min(Math.min(DP[i-1][j],  DP[i][j-1]), DP[i-1][j-1])+1; 
				}
			}
			//System.out.println(Arrays.toString(DP[i]));
		}
		
		System.out.println(DP[s1.length()-1][s2.length()-1]); 

	}
}
