import java.io.*;
import java.util.*;
/*
 * http://usaco.org/index.php?page=viewproblem2&cpid=622
 * USACO 2016 February Contest, Gold
 * Problem 2. Circular Barn Revisited
 * 
 * 10/10 test cases
 * using the splitting technique (make the circle an arraylist and shift it around)
 */
public class SplitLong {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cbarn2.in"));

		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		ArrayList<Integer> cowReq = new ArrayList<Integer>();

		for (int i = 0; i < N; i++) {
			cowReq.add(Integer.parseInt(br.readLine()));
		}
		br.close();

		long minMin = Integer.MAX_VALUE; // min distance
		
		for (int s = 0; s < N; s++) { // starting point

			// DP array [number of doors opened][last door opened]
			// store the total cost
			long[][] DP = new long[K+1][N];
			for (int i = 0; i < K+1; i++) {
				for (int j = 0; j < N; j++) {
					DP[i][j] = -1;
				}
			}

			//System.out.println(Arrays.toString(cowReq));


			// basecase: door opens at location 0
			DP[1][0] = 0;
			for (int i = 0; i < N; i++) // calculate distance
				DP[1][0] += i*cowReq.get(i);
			if (DP[1][0] < minMin) minMin = DP[1][0];
			
			
			for (int k = 1; k < K; k++) { // number of doors opened
				for (int d = k-1; d < N; d++) { // ind of last door opened
					
					if (k == 1 && d > 0) continue; // only starting point
					
					//System.out.println("k: " + k + " d: " + d);

					long oldCost = DP[k][d]; // cost to fill rooms in current condition
					if (oldCost < minMin) minMin = oldCost;
					//System.out.println("  old cost: " + oldCost);

					for (int j = d+1; j < N; j++) { // next door to open

						long cost = oldCost;

						// calculate difference in cost
						for (int i = j; i < N; i++) {
							// loop through rooms starting with new open one
							// substract num required * diff in distance
							int diff = cowReq.get(i)*(j-d);
							//System.out.println(diff);
							cost -= diff;
						}
						//if (cost < 0) System.out.println("NEGATIVE cost: " + cost);

						if (DP[k+1][j] < 0 || cost < DP[k+1][j] && cost >= 0) {
							DP[k+1][j] = cost; 
							if (cost < minMin) minMin = cost;
						} 
					}	
				}
			}
			// rotate cowReq
			int first = cowReq.remove(0);
			cowReq.add(first);
		}


		//System.out.println(Arrays.toString(DP[K]));


		//System.out.println();
		System.out.println(minMin);


		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn2.out")));

		pw.println(minMin);
		pw.close();
	}


}
