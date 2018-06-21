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
public class CBarn {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cbarn2.in"));
		
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
		int[][] DP = new int[K+1][N];
		
		
		ArrayList<Integer> cowReq2 = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			cowReq2.add(cowReq[i]);
		} 
		
		//System.out.println(Arrays.toString(cowReq));
		
		// basecase: 1 door opened at any location
		for (int i = 0; i < N; i++) {
			// loop through the possibilities for the first door opened
			int tempCost = 0;
			
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
		
		for (int k = 1; k < K; k++) {
			// loop through number of doors opened
			for (int d = 0; d < N; d++) {
				// last door opened
				//System.out.println("k: " + k + " d: " + d);
				
				int oldCost = DP[k][d];
				//System.out.println(oldCost);
				
				int minCost = Integer.MAX_VALUE;
				int finalj = 0;
				for (int j = d+1; j < N+d; j++) {
					// next door to open
					
					// calc difference in cost
					int cost = DP[k][d];
					
					
					for (int i = j; i < N+d; i++) {
						// loop through doors starting with new open one
						// substract num required * diff in distance
						cost -= cowReq[i%N]*((j-d+N)%N);
						
					}
					//System.out.println("  cost: " + cost);
					
					if (cost < minCost) {
						//System.out.println("j: " + j);
						minCost = cost;
						
					}
					
					finalj = j%N;
					//System.out.println("finalj: " + finalj);
				}
				// see if this is wrong TODO
				DP[k+1][finalj] = minCost; 
				
			}
			
			
		}
		
		int minMin = Integer.MAX_VALUE;
		for (int d = 0; d < N; d++) {
			if (DP[K][d] < minMin) minMin = DP[K][d];
		}
		//System.out.println();
		System.out.println(minMin);
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn2.out")));

		pw.println(minMin);
		pw.close();
	}
	

}
