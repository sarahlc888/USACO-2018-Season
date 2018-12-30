import java.io.*;
import java.util.*;
/*
 * 11/20/18
 * 
 * DP knapsack
 * correct
 */
public class Half {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("10.in"));
		int N = Integer.parseInt(br.readLine()); // max N = 150
		Integer[] arr = new Integer[N+1]; // max weight = 300
		int sum = 0;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			sum += arr[i];
		}
		br.close();
		
//		Arrays.sort(arr, Collections.reverseOrder());
		int target = sum/2; // goal to reach
		
		// knapsack to get collected weight of the cows to reach target, then divide by 2
		long[][] DP = new long[N+1][sum+1]; // [last cow used][sum reachable]
		
		DP[0][0] = 1;
		
		for (int i = 1; i <= N; i++) {
						
			for (int j = 0; j <= sum; j++) { 
				// carry over all the possibilities from beforehand
				DP[i][j] = DP[i-1][j];
			}
			for (int j = 0; j <= sum; j++) {
				
				if (j-arr[i] >= 0) {
					DP[i][j] += DP[i-1][j-arr[i]];
					
					DP[i][j] %= 1000000007;
					
				}
				
//				if (j+arr[i] <= sum) {
//					if (DP[i-1][j] > 0)
//						System.out.println("  reach weight " + (j+arr[i]));
//					DP[i][j+arr[i]] += DP[i-1][j];
//				}
					
			}
		}
		
		System.out.println(DP[N-1][target]);
		
//		for (int i = 0; i <= N; i++) {
//			System.out.println(Arrays.toString(DP[i]));
//		}
		
		
//		int[] DP = new int[sum+1]; // DP[i] = how many ways you can reach weight i
//		int[] aux = new int[sum+1]; // auxiliary array
		
//		for (int i = 0; i < N; i++) { // loop through cows
//			System.out.println("i: " + i + "  weight: " + arr[i]);
//			
//			aux = DP.clone();
//			aux[arr[i]]++;
//			for (int j = 0; j < DP.length; j++) { // loop through previous array
//				if (j+arr[i] < sum)
//					aux[j+arr[i]]++;
//			
//			}
//			DP = aux;
//			System.out.println(Arrays.toString(DP));
//			
//		}
//		System.out.println(DP[target]);
		
		
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("half.out")));

		pw.println();
		pw.close();
	}
}
