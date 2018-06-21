import java.io.*;
import java.util.*;
/*
 * 10/10
 * IDEA
 * DP[pos][num] = # changes to sort 1...pos if we make arr[pos] = num
 * 
 * DP[i][1] = min of changes it takes to make everything before it 1 bc it must be sorted
 * 		if a[i] = 1, DP[i][1] = dp[i-1][1]
 * 		if a[i] != 1, DP[i][1] = DP[i-1][1] + 1
 * 
 * DP[i][2]
 * 		(if the prev is a 1) DP[i-1][1] + (a[i] == 2) (if it's equal , then add 1)
 * 		(if prev is a 2) DP[i-1][2] + a[i] = 2
 * DP[i][3]
 * 		DP[i-1][3] + (a[i] == 3)
 * 		DP[i-1][2] + (a[i] == 3)
 * 		DP[i-1][1] + (a[i] == 3)
 * 
 * O(N) only loop once and only look at previous state
 * 
 * 1...N indexing
 */
public class EatingTogether {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // number of cows
		int[] cows = new int[N+1];
		for (int i = 1; i <= N; i++)
			cows[i] = Integer.parseInt(scan.nextLine());
		scan.close();
		
		// number of changes it takes to be sorted lo to hi up to cow i
		int[][] DP1 = new int[N+1][4]; 
		
		for (int i = 1; i <= N; i++) {
			DP1[i][1] = DP1[i-1][1]; // 1 case
			if (cows[i] != 1) 
				DP1[i][1]++;
			
			DP1[i][2] = Math.min(DP1[i-1][1], DP1[i-1][2]); // 2 case
			if (cows[i] != 2) 
				DP1[i][2]++;
			
			DP1[i][3] = Math.min(DP1[i-1][3], DP1[i-1][2]); // 3 case
			DP1[i][3] = Math.min(DP1[i][3], DP1[i-1][1]);
			
			if (cows[i] != 3) 
				DP1[i][3]++;
			
		}

		// number of changes it takes to be sorted hi to lo up to cow i
		int[][] DP2 = new int[N+1][4]; 
		
		for (int i = 1; i <= N; i++) {
			DP2[i][3] = DP2[i-1][3]; // 3 case
			if (cows[i] != 3) 
				DP2[i][3]++;
			
			DP2[i][2] = Math.min(DP2[i-1][3], DP2[i-1][2]); // 2 case
			if (cows[i] != 2) 
				DP2[i][2]++;
			
			DP2[i][1] = Math.min(DP2[i-1][3], DP2[i-1][2]); // 1 case
			DP2[i][1] = Math.min(DP2[i][1], DP2[i-1][1]);
			
			if (cows[i] != 1) 
				DP2[i][1]++;
			
		}
		
		int min = Integer.MAX_VALUE;
		for (int i = 1; i < 4; i++) {
			if (DP1[N][i] < min)
				min = DP1[N][i];
			if (DP2[N][i] < min) min = DP2[N][i];
		}
	
		System.out.println(min);
		
	}
}
