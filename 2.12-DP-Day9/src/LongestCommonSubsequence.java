import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 10/10 test cases
 * 
 * DP[i][j] is LCS between S1[1...i] and S2[1...j]
 * 
 * see code for notes
 * 
 * for other version, keep the 2 by N array to optimize and keep only DP[i] and [i-1]
 */
public class LongestCommonSubsequence {
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// have the words start at index 1
		String s1 = br.readLine();
		String s2 = br.readLine();
		s1 = " "+s1;
		s2 = " "+s2;
		
		int[][] DP = new int[s1.length()+1][s2.length()+1];
		
		for (int i = 1; i < s1.length(); i++) {
			for (int j = 1; j < s2.length(); j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					// case: use the character
					DP[i][j] = DP[i-1][j-1]+1;
				} else { 
					// case: don't use i or don't use j
					DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);
				}
			}
		}
		System.out.println(DP[s1.length()-1][s2.length()-1]);

	}
}
