import java.io.*;
import java.util.*;
/*
 * see v2
 * 
 * doesn't work bc of modding issues
 * 
 * 1...N indexing
 * similar to subset problem
 * 
 * not done
 */
public class DividingTheGold {
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


		int p1 = sum/2;
		int p2 = sum/2;

		if (sum%2 == 0 && DP2[sum/2] >= 2) {
			//System.out.println("here");
			System.out.println(0);
			p1 = sum/2;
			p2 = sum/2;
		} else {

			if (DP2[sum/2] > 0) { // if valid

				// go down
				int downDiff = Integer.MAX_VALUE;
				for (p1 = sum/2-1; p1 >= 0; p1--) {
					// checking the first possible sum
					if (DP2[p1] > 0) { // it's possible
						downDiff = sum-p1-p1;
						break;
					}
				}


				// go up
				int upDiff = Integer.MAX_VALUE;
				for (p2 = sum/2+1; p2 <= sum; p2++) {
					// checking the first possible sum
					if (DP2[p2] > 0) { // it's possible
						upDiff = p2-(sum-p2);
						break;
					}
				}

				System.out.println(Math.min(upDiff, downDiff));

			} else {
				for (p1 = sum/2-1; p1 >= 0; p1--) {
					// checking the first possible sum
					if (DP2[p1] > 0) { // it's possible
						break;
					}
				}
				for (p2 = sum/2+1; p2 <= sum; p2++) {
					// checking the first possible sum
					if (DP2[p2] > 0) { // it's possible
						if (p2 == sum/2 && DP2[p2] < 2) { // if there's not enough
							continue;
						}
						break;
					}
				}
				System.out.println(p2-p1);
			}
		}


		System.out.println(Math.min(DP2[p1], DP2[p2])%1000000);
	}
}
