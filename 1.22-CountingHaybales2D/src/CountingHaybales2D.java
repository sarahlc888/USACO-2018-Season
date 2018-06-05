import java.io.*;
import java.util.*;

// 9/10 test cases correct - missed #2??

public class CountingHaybales2D {
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <=10; i++) {
		//for (int i = 3; i <= 3; i++) {
			String filename = "testData/" + (i+20) + ".in";
			processOneFile(filename, i);
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of points
		int Q = Integer.parseInt(st.nextToken()); // number of queries
		
		int[][] pts = new int[N][2];
		int[] ptsx = new int[N];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st2 = new StringTokenizer(br.readLine());
			pts[i][0] = Integer.parseInt(st2.nextToken());
			pts[i][1] = Integer.parseInt(st2.nextToken());
		}
		Arrays.sort(pts, (a, b) -> Integer.compare(a[0], b[0])); // sort the fields by the x coord
		
		for (int i = 0; i < N; i++) {
			ptsx[i] = pts[i][0];
		}
		
		int[][][] queries = new int[Q][2][2];
		
		for (int i = 0; i < Q; i++) {
			StringTokenizer st2 = new StringTokenizer(br.readLine());
			queries[i][0][0] = Integer.parseInt(st2.nextToken());
			queries[i][0][1] = Integer.parseInt(st2.nextToken());
			queries[i][1][0] = Integer.parseInt(st2.nextToken());
			queries[i][1][1] = Integer.parseInt(st2.nextToken());
		}
		
		br.close();
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(k + ".out"))));

		for (int i = 0; i < Q; i++) {
			// go through all the queries
			int[] LLC = {queries[i][0][0], queries[i][0][1]}; // lower left corner
			int[] URC = {queries[i][1][0], queries[i][1][1]}; // upper right corner
						
			// search for the first point past (above) the lower bound (LLC)
			int startInd = bSearch1(ptsx, LLC[0], N-1, 0);
			// last point within (below) the upper bound (URC)
			int endInd = bSearch2(ptsx, URC[0], N-1, 0);
			
			int cowcount = endInd - startInd + 1; // number of cows in the rectangle
			
			// if need be, make it faster by doing if not in bounds decrease (fewer times true)
			for (int j = startInd; j <= endInd; j++) {
				if (!(pts[j][1] >= LLC[1] && pts[j][1] <= URC[1])) {
					cowcount--; // if the y coordinate is not in bounds, decrement
				}
			}
			pw.println(cowcount);
			System.out.println(cowcount);
		}
		
		
		
		pw.close();
	}
	public static int bSearch1(int[] pts, int lbound, int hi, int lo) {
		// big side
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
			return bSearch1(pts, lbound, hi, lo);
		} else if (pts[mid] > lbound) {
			// go smaller
			hi = mid;     // lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			return bSearch1(pts, lbound, hi, lo);
		} else {
			while (pts[mid] == pts[mid-1]) {
				mid--;
			}
			return mid;
			// equals --> desired result
		}
		
	}
	public static int bSearch2(int[] pts, int hbound, int hi, int lo) {
		// small side
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
			return lo;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid] < hbound) {
			// go bigger
			lo = mid;
			//System.out.println("t4");
			return bSearch2(pts, hbound, hi, lo);
		} else if (pts[mid] > hbound) {
			// go smaller
			hi = mid - 1;
			return bSearch2(pts, hbound, hi, lo);
		} else {
			while (pts[mid] == pts[mid+1]) {
				mid++;
			}
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}

}
