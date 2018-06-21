import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * 
 * s = sum of original set, must be even
 * each of the partitions must equal s/2
 * knapsack building up to s/2
 * 
 * DP[index][sum] = number of ways to get sum using 1...index
 * 
 * for each index loop through previous index
 * 		
 * O(N^3) = O(N*S)
 */
public class SubsetSums {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine());
		int totsum = (N*(N+1))/2; // so from 1...N;
		if (totsum%2 != 0) {
			// if the total sum is odd
			System.out.println(0);
			return;
		}
		int sum = (N*(N+1))/4; // s/2, each of the partitions must equal this
		
		long[][] DP = new long[N+1][sum+1]; // initialize with zeros
		DP[0][0] = 1;
		
		for (int i = 1; i <= N; i++) { // loop through numbers
			for (int j = 0; j <= sum; j++) { // loop through previous
				// see how many ways you can get to sum j
				// either use i or don't use i
				if (j-i < 0) {
					DP[i][j] = DP[i-1][j];
				} else {
					DP[i][j] = DP[i-1][j-i]+DP[i-1][j];
				}
			}
			//System.out.println(Arrays.toString(DP[i]));
		}
		System.out.println(DP[N][sum]/2); // all the sums have to be paired
	}
}
