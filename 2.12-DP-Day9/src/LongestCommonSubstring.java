import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// find longest common contiguous substring
// similar to LCsubsequence, only changed the else statement
// 10/10 test cases
// DP[i][j] is LCS between S1[1...i] and S2[1...j] that ends at index i or j
// O(N^2)
public class LongestCommonSubstring {
	static int max = 0;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// have the words start at index 1
		String s1 = br.readLine();
		String s2 = br.readLine();
		s1 = " "+s1;
		s2 = " "+s2;

		int[][] DP = new int[s1.length()][s2.length()];

		for (int i = 1; i < s1.length(); i++) {
			for (int j = 1; j < s2.length(); j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					// case: use the character
					DP[i][j] = DP[i-1][j-1]+1;
				} else { 
					// case: don't use i or don't use j
					DP[i][j] = 0; // end the string bc you don't want to keep building off of it
				}
				if (DP[i][j] > max) max = DP[i][j]; // update max
			}
		}
		
		System.out.println(max); 
		// not just the ending bc that would only give sbtrs with that ending index
	}
}
