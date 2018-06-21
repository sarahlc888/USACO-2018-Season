import java.io.*;
import java.util.*;
/*
 * http://usaco.org/index.php?page=viewproblem2&cpid=622
 * USACO 2016 February Contest, Gold
 * Problem 2. Circular Barn Revisited
 * 
 * 4/10 test cases
 * split on the first part, mod on the second part
 * 
 * DOES NOT WORK
 * 
 * DP
 * circles/arrays/mod
 */
public class CBarn1 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("testData/9.in"));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[] cowReq = new int[N];
		
		for (int i = 0; i < N; i++) {
			cowReq[i] = Integer.parseInt(br.readLine());
		}
		br.close();

		// DP array [number of doors opened][last door opened]
		// store the total cost
		long[][] DP = new long[K+1][N];
		for (int i = 0; i < K+1; i++) {
			for (int j = 0; j < N; j++) {
				DP[i][j] = -1l;
			}
		}
		
		ArrayList<Integer> cowReq2 = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			cowReq2.add(cowReq[i]);
		}
		
		//System.out.println(Arrays.toString(cowReq));
		
		// basecase: 1 door opened at location i
		for (int i = 0; i < N; i++) {
			// loop through the possibilities for the first door opened
			long tempCost = 0;
			
			for (int j = 0; j < N; j++) { // loop through cowReq
				tempCost += j*cowReq2.get(j);
				////System.out.println("  " + tempCost);
			}
			DP[1][i] = tempCost;
			//System.out.println(DP[1][i]);
			
			// rotate cowReq2
			int first = cowReq2.remove(0);
			cowReq2.add(first);

		}
		//System.out.println();
		for (int s = 0; s < N; s++) { // starting point
			for (int k = 1; k < K; k++) { // number of doors opened
				for (int d = s+k-1; d < s+N; d++) { // last door opened
					//System.out.println("k: " + k + " d: " + d);
					
					long oldCost = DP[k][d%N]; // cost to fill rooms in current condition
					
					//System.out.println("old cost: " + oldCost);
					
					for (int j = d+1; j < s+N; j++) { // next door to open
						
						long cost = oldCost;
						
						// calculate difference in cost
						for (int i = j; i < s+N; i++) {
							// loop through rooms starting with new open one
							// substract num required * diff in distance
							int diff = cowReq[i%N]*((j-d+N)%N);
							//System.out.println(diff);
							cost -= diff;
							
							
						}
						if (cost < 0) System.out.println("NEGATIVE cost: " + cost);

						if (DP[k+1][j%N] < 0) {
							DP[k+1][j%N] = cost; 
						} else {
							DP[k+1][j%N] = Math.min(DP[k+1][j%N] , cost); 
						}
						if (k==K-1) {
							//System.out.println(cost);
						}
					}	
				}
			}
			
		}
		
		
		long minMin = Long.MAX_VALUE;
		
		for (int d = 0; d < N; d++) {
			if (DP[K][d] < minMin && DP[K][d] >= 0) {
				minMin = DP[K][d];
			}
		}
		//System.out.println();
		System.out.println(minMin);
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn2.out")));

		pw.println(minMin);
		pw.close();
	}
	

}
