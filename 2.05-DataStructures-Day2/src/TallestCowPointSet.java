import java.io.*;
import java.util.*;
import java.awt.Point;

/*
 * all test cases correct
 * queries and array and prefix sum
 * see the proper stack solution?
 * 
 * point set has point comparator here that works
 */

public class TallestCowPointSet {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int N = Integer.parseInt(st.nextToken()); // number of cows
		int I = Integer.parseInt(st.nextToken()); // index of tallest cow
		int H = Integer.parseInt(st.nextToken()); // height of tallest cow
		int R = Integer.parseInt(st.nextToken()); // number of queries
		
		Set<Point> s = new TreeSet<Point>(pointComparator);
		
		// SHL, doesn't WORK!!!!!!
		
		
		int[] prefixSum = new int[N]; // array of prefix sums
		
		for (int i = 0; i < R; i++) { // scan in queries
			st = new StringTokenizer(scan.nextLine());
			// correct for 0...N indexing
			int a = Integer.parseInt(st.nextToken()) - 1; 
			int b = Integer.parseInt(st.nextToken()) - 1;
						
			// make queries low to high
			int lo = Math.min(a, b);
			int hi = Math.max(a, b);
			
			// check for uniqueness
			Point p = new Point(lo, hi);
			if (s.contains(p)) { // if it is a duplicate, continue
				//System.out.println("DUP!");
				continue;
			} else {
				s.add(p);
			}
			// mark in the prefixSum array if they haven't appeared before
			prefixSum[lo+1] -= 1;
			prefixSum[hi] += 1;
			
		}
		
		int[] sum = new int[N]; // array of computing the prefix sums
		sum[0] = prefixSum[0];
		System.out.println(H+sum[0]);
		for (int i = 1; i < N; i++) {
			// loop through prefix sum array
			sum[i] = sum[i-1] + prefixSum[i];
			System.out.println(H+sum[i]);
		}
		
		scan.close();
		
	}
	public static Comparator<Point> pointComparator = new Comparator<Point>(){

		@Override
		public int compare(Point a, Point b) {
			if (a.x > b.x) {
				return 1;
			} else if (a.x < b.x) {
				return -1;
			} else if (a.y > b.y) {
				return 1;
			} else if (a.y < b.y) {
				return -1;
			} else {
				return 0;
			}

		}
	};
	
	
}
/*
input:
9 3 5 5
1 3
1 3
5 3
4 3
3 7
9 8

 */
