import java.io.*;
import java.util.*;

/*
 * 
 * 10/10 test cases
 * 
 * dp[time(only start and stops)] = max milk produce-able
 * 
 * interval classic problem
 * sort by endpoint and get the ones with the earliest endpoints as long as they don't overlap
 * 
 * idea
 * - sort by endpoint
 * - DP[i] = max number of milk produceable in first i intervals
 * - DP[i] =
 * 		if you use it
 * 		for j = i-1 to 1, if (end of j + R <= start of i)
 * 		DP[i] = max(dp[i], dp[j] + milk[i])
 * 
 * 		if you don't
 * 		DP[i] = DP[i-1]
 * 
 * O(M^2)
 * 
 * 1..M indexing
 */
public class MilkingTime {
	static int max = 0;
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int N = Integer.parseInt(st.nextToken()); // number of hours
		int M = Integer.parseInt(st.nextToken()); // number of intervals
		int R = Integer.parseInt(st.nextToken()); // number of rest hours needed
		
		Point[] intervals = new Point[M+1];
		
		for (int i = 1; i <= M; i++ ) { // scan in intervals
			st = new StringTokenizer(scan.nextLine());
			intervals[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		intervals[0] = new Point(-1, -1, -1);
		Arrays.sort(intervals);
		//System.out.println(Arrays.toString(intervals));
		
		// DP[i] = max amt milk produceable in first i intervals
		int[] DP = new int[M+1];
		
		for (int i = 1; i <= M; i++) { // intervals
			//System.out.println("i: " + i + "  " + intervals[i]);
			// case: use the interval
			for (int j = i-1; j >= 1; j--) { // previous intervals
				if (intervals[j].end + R <= intervals[i].start) { // if enough rest
					//System.out.println("  j: " + j);
					DP[i] = Math.max(DP[i], DP[j] + intervals[i].milk); // take the milk
				}
			}
			DP[i] = Math.max(DP[i], intervals[i].milk); // take the interval alone
			// case: don't use it
			DP[i] = Math.max(DP[i], DP[i-1]); 
			
			if (DP[i] > max) max = DP[i];
			//System.out.println("  " + DP[i]);
		}
		System.out.println(max);
		
	}
	public static class Point implements Comparable<Point> {
		int start;
		int end;
		int milk;
		
		public Point(int a, int b, int c) {
			start = a;
			end = b;
			milk = c;
		}
		@Override
		public int compareTo(Point o) {
			if (end == o.end) return start - o.start;
			return end - o.end;
		}
		public String toString() {
			return start + " " + end + " " + milk;
		}
		
	}
}
