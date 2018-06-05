// USACO 2016 December Contest, Silver Problem 1. Counting Haybales

import java.util.*;
import java.io.*;
public class Queries {

	public static void main(String[] args) throws IOException {
	
		//Scanner in = new Scanner(new File("Queries.in"));
		Scanner in = new Scanner(new File("haybales.in"));
		int N = in.nextInt(); // number of integers
		int Q = in.nextInt(); // number of queries
		
		// scan in the integers
		int[] pts = new int[N]; 
		for(int k=0; k<N; k++) 
			pts[k] = in.nextInt();
		Arrays.sort(pts);
		
		// scan in intervals
		int[][] intervals = new int[Q][2];
		for(int k=0; k<Q; k++) {
			intervals[k][0] = in.nextInt();
			intervals[k][1] = in.nextInt();
		}
		in.close();
		
		
		// For each query [a, b], find the first index x such that pts[x] >= a
		// and find the last index y such that pts[y] <= b
		// Then the number of integers in [a, b] is y-x+1

		//PrintWriter pw = new PrintWriter(new File("Queries.out"));
		PrintWriter pw = new PrintWriter(new File("haybales.out"));
		
		for(int k=0; k<Q; k++) {
			//System.out.println("query: " + (k+1));

			// number of integers inside interval
			int count = 0;
			
			// 	first index x
			int x = bSearchX(pts, intervals[k][0], N-1, 0);
			//System.out.println("x: " + x);
			
			// last index y
			int y = bSearchY(pts, intervals[k][1], N-1, 0);
			//System.out.println("y: " + y);
			
			// if the interval contains any points, count the points (otherwise leave it at 0)
			if (x != -1 && y != -1) 
				count = y-x + 1;
			
			pw.println(count);
		}		
		pw.close();
	}
	
	public static int bSearchX(int[] pts, int lbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (lbound <= pts[lo]) {
			return lo;
		} else if (lbound > pts[hi]) { // not in any 
			return -1;
		}
		
		// basecase
		// now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) {
			return hi;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid] < lbound) {
			// go bigger
			lo = mid + 1;
			return bSearchX(pts, lbound, hi, lo);
		} else if (pts[mid] > lbound) {
			// go smaller
			hi = mid;     // lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			return bSearchX(pts, lbound, hi, lo);
		} else {
			while (pts[mid] == pts[mid-1]) mid--;
			return mid;
			// equals --> desired result
		}
		
	}
	public static int bSearchY(int[] pts, int hbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (hbound >= pts[hi]) {
			//System.out.println("t1");
			return hi;
		} else if (hbound < pts[lo]) { // no point are in the interval
			//System.out.println("t2");
			return -1;
		}
		
		// basecase for when hi - lo range is tiny
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			//System.out.println("t3");
			return lo;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid] < hbound) {
			// go bigger
			lo = mid;
			//System.out.println("t4");
			return bSearchY(pts, hbound, hi, lo);
		} else if (pts[mid] > hbound) {
			// go smaller
			hi = mid - 1;     // lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			//System.out.println("t5");
			return bSearchY(pts, hbound, hi, lo);
		} else {
			//System.out.println("t6");
			while (pts[mid] == pts[mid+1]) mid++;
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}

}