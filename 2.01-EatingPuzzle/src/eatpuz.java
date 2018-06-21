import java.io.*;
import java.util.*;

public class eatpuz {
	static int C;
	static int[] feed;
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		C = Integer.parseInt(st.nextToken()); // max number of calories
		int B = Integer.parseInt(st.nextToken()); // number of buckets
		
		feed = new int[B]; // calories in the buckets
		
		st = new StringTokenizer(scan.nextLine());
		
		for (int i = 0; i < B; i++) { // scan in calories in the buckets
			feed[i] = Integer.parseInt(st.nextToken());
		}
		////System.out.println(C + " " + B);
		////System.out.println(Arrays.toString(feed));
		
		
		int max = 0;
		
		
		// DYNAMIC PROGRAMMING
		// [number of buckets eaten][index of last bucket processed]
		// store the number of calories eaten
		int[][] DP = new int[B+1][B];
		// default as 0 calories
		
		// basecases: 1 bucket eaten at each location
		for (int i = 0; i < B; i++) {
			DP[1][i] = feed[i];
		}
		
		for (int i = 1; i < B; i++) { // number of buckets eaten
			for (int j = 0; j < B-1; j++) { // index of last bucket processed
				int curCal = DP[i][j]; // current calories
				
				// options:
				// - eat the next bucket (j+1)
				int next = curCal + feed[j+1];
				//DP[i+1][j+1] = next; 
				DP[i+1][j+1] = closest(DP[i+1][j+1], next); 
				// - skip the next bucket 
				//DP[i][j+1] = curCal;
				DP[i][j+1] = closest(DP[i][j+1], curCal);
				// - don't move on
				DP[i+1][j] =  closest(DP[i+1][j], curCal);
				
				if (next > max && next <= C) max = next;
				if (curCal > max && curCal <= C) max = curCal;
				
				
			}
		}
		
		for (int i = 0; i <= B; i++) {
			//System.out.println(Arrays.toString(DP[i]));
		}
		
		System.out.println(max);
		
		scan.close();
		
	}
	public static boolean nextTooBig(int i, int sum) {
		if (sum + feed[i+1] > C) return true;
		return false;
	}
	public static int closest(int a, int b) {
		if (a > b && a < C) {
			return a;
		}
		if (b > a && b < C) {
			return b;
		}
		if (a == b && a < C) {
			return a;
		}
		return -1; // both > C 
	}

}
/*
40 6
7 13 17 19 29 31
*/