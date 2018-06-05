import java.util.*;
import java.io.*;

// use a partial sum ARRAY
//calculate the partial sum at EVERY STEP and then just subtract kind of 

// works - all 15 test cases correct (some of them took very long - almost 4 seconds, but still passed?)

public class BreedCounting {
	public static void main(String Args[]) throws IOException {
		Scanner scan = new Scanner(new File("bcount.in"));
		int N = scan.nextInt(); // number of total cows
		int Q = scan.nextInt(); // number of queries
		
		// arrays of 1s and zeroes denoting whether a cow of breed 1, 2, or 3 is present at each spot
		int[] c1 = new int[N];
		int[] c2 = new int[N];
		int[] c3 = new int[N];
		// partial sum array
		// c1ps[i] = c1[0] + ... + c1[i]
		int[] c1ps = new int[N];
		int[] c2ps = new int[N];
		int[] c3ps = new int[N];
		
		
		int breed = scan.nextInt();
		if (breed == 1) {
			c1[0] = 1;
		} else if (breed == 2) {
			c2[0] = 1;
		} else { // breed == 3
			c3[0] = 1;
		} 
		
		c1ps[0] = c1[0];
		c2ps[0] = c2[0];
		c3ps[0] = c3[0];
		
		
		for (int i = 1; i < N; i++) { 
			// mark 1s for each cow in the proper breed array and collect the proper partial sum data
			
			breed = scan.nextInt();
			
			// default: keep all the partial sums the same

			c1ps[i] = c1ps[i-1]; 
			c2ps[i] = c2ps[i-1];
			c3ps[i] = c3ps[i-1];

			
			if (breed == 1) {
				c1[i] = 1;
				c1ps[i] = c1ps[i-1] + 1; // increase the partial sum
			} else if (breed == 2) {
				c2[i] = 1;
				c2ps[i] = c2ps[i-1] + 1; // increase the partial sum
			} else { // breed == 3
				c3[i] = 1;
				c3ps[i] = c3ps[i-1] + 1; // increase the partial sum
			} 
			
		}
		
		// scan in the queries into a 2D array (inclusive)
		int[][] queries = new int[Q][2];
		for (int i = 0; i < Q; i++) { // - 1 to shift from 1...N indexing to 0...N-1 indexing
			queries[i][0] = scan.nextInt() - 1;
			queries[i][1] = scan.nextInt() - 1;
		}
		
		scan.close();
		PrintWriter pw = new PrintWriter(new File("bcount.out"));
		
		for (int i = 0; i < Q; i++) {
			// go through all of the queries
			int start = queries[i][0];
			int end = queries[i][1];
			
			int c1ct = calcSum(start, end, c1, c1ps);
			
			int c2ct = calcSum(start, end, c2, c2ps);
			
			int c3ct = end - start + 1 - c1ct - c2ct;
			
			System.out.println(c1ct + " " + c2ct + " " + c3ct);
			pw.println(c1ct + " " + c2ct + " " + c3ct);
		}
		pw.close();
		
	}
	public static int calcSum(int start, int end, int[] cows, int[] cowsPS) {
		// calculate the sum of cows[start] + ... + cows[end]
		// cowsPS[end] - cows[start-1]
		
		if (start - 1 < 0) {
			return cowsPS[end];
		} else {
			return cowsPS[end] - cowsPS[start-1];
		}
		
	}
	
}
