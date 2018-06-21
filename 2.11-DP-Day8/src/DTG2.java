import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * 
 * 1...N indexing
 * similar to subset problem
 * 
 * not done
 */
public class DTG2 {
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // number of coins
		int[] coins = new int[N+1];
		int sum = 0;
		int max = 0;

		for (int i = 1; i <= N; i++) {
			coins[i] = Integer.parseInt(scan.nextLine()); // value of coin
			sum += coins[i];
			if (coins[i] > max) max = coins[i];
		}
		int hind = 0; // not yet at the first one
		int[] DP1 = new int[sum+1]; // DP1[val] = ways to get there up to index hind
		int[] DP2 = new int[sum+1]; // DP2[val] = ways to get there up to index hind+1
		DP1[0] = 1; // basecase
		DP2[coins[hind+1]] = 1;
		boolean[] visited = new boolean[sum+1]; // stores if there's a value
		// (to account for modding, which could yield 0s)

		while (hind < N) { // loop through objects
			//System.out.println("hind: " + hind);
			int curVal = coins[hind+1];
			//System.out.println("  curval for " +(hind+1) + " : " + curVal);

			for (int j = 0; j <= sum; j++) { // loop through capacities

				DP2[j] = 0;
				if (j-curVal >= 0) {
					//System.out.println("    here");
					DP2[j] = DP1[j-curVal]%1000000; // case where you took the coin
				} 
				DP2[j] += DP1[j]%1000000; // case where you didn't take it
				if (DP2[j] > 0) {
					visited[j] = true;
				}
				//System.out.println("  j: " + j + " " + DP2[j]);
			}
			//System.out.println("row " + hind + ": " + Arrays.toString(DP1));
			// push DP2 into DP1
			for (int j = 0; j <= sum; j++) {
				DP1[j] = DP2[j]%1000000;
			}

			hind++;
			
		}
		//System.out.println((DP2[250000]));


		
		// find the first value
		for (int p1 = sum/2; p1 >= 0; p1--) {
			if (visited[p1]) {
				// if there's a value
				if (sum%2 == 1) {
					// if it's odd, so there's something in the middle
					System.out.println((sum/2-p1)*2+1); 
				} else {
					// even
					System.out.println((sum/2-p1)*2);
					
				}
				System.out.println(DP2[p1]%1000000);
				break;
			}
			
		}

	}
}
