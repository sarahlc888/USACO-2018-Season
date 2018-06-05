import java.util.*;
import java.io.*;

// all 15 test cases correct and under time

// calculate the partial sum at EVERY STEP and then just subtract kind of 
// after each step

public class BreedCounting2 {
	public static void main(String Args[]) throws IOException {
		Scanner scan = new Scanner(new File("bcount.in"));
		int N = scan.nextInt(); // number of total cows
		int Q = scan.nextInt(); // number of queries
		
		// arrays of 1s and zeroes denoting whether a cow of breed 1, 2, or 3 is present at each spot
		long[] cows = new long[N];
		long val1 = 1;
		long val2 = 100001;
		long val3 = 10000200001L;
		long[] cowsPS = new long[N];
		// partial sum array
		// cowsPS[i] = cows[0] + ... + cows[i]
		
		int breed = scan.nextInt();
		if (breed == 1) {
			cows[0] = val1;
		} else if (breed == 2) {
			cows[0] = val2;
		} else {
			cows[0] = val3;
		}
		
		cowsPS[0] = cows[0];
		
		
		for (int i = 1; i < N; i++) { // mark each 

			breed = scan.nextInt();
			// default: keep the partial sum the same

			cowsPS[i] = cowsPS[i-1];

			if (breed == 1) {
				cows[i] = val1;
				cowsPS[i] += val1;
			} else if (breed == 2) {
				cows[i] = val2;
				cowsPS[i] += val2;
			} else {
				cows[i] = val3;
				cowsPS[i] += val3;
			}
			// do the partial sums here
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
			
			long count;
			
			if (start == 0) {
				count = cowsPS[end];
			} else {
				count = cowsPS[end] - cowsPS[start - 1];
			}
			
			//System.out.println("rc: " + runningct);
			
			long c1ct = count%val2;
			long c2ct = (count%val3)/val2;
			long c3ct = count/val3;
			
			//System.out.println(c1ct + " " + c2ct + " " + c3ct);
			pw.println(c1ct + " " + c2ct + " " + c3ct);
		}
		pw.close();
		
	}
}
