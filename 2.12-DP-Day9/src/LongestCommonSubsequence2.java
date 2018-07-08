import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * optimized version O(N)
 * 
 * 10/10 test cases
 * 
 * DP[i][j] is LCS between S1[1...i] and S2[1...j]
 * 
 * see code for notes
 * 
 * for other version, keep the 2 by N array to optimize and keep only DP[i] and [i-1]
 */
public class LongestCommonSubsequence2 {
	static int max;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// have the words start at index 1
		String s1 = br.readLine();
		String s2 = br.readLine();
		s1 = " "+s1;
		s2 = " "+s2;

		int length = Math.max(s1.length(), s2.length());

		int ind = 0;

		int[] DP1 = new int[s2.length()];
		int[] DP2 = new int[s2.length()];

		while (ind < s1.length()) {
			for (int j = 1; j < s2.length(); j++) {
				if (s1.charAt(ind) == s2.charAt(j)) {
					// case: use the character
					DP2[j] = DP1[j-1]+1;
				} else { 
					// case: don't use i or don't use j
					DP2[j] = Math.max(DP1[j], DP2[j-1]);
				}

			}
			
			ind++;
			
			for (int j = 1; j < s2.length(); j++) { // transfer over
				DP1[j] = DP2[j];
			}
		}
		
		
		System.out.println(DP2[s2.length()-1]);

	}
}
