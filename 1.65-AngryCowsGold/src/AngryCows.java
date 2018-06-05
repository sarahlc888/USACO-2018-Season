import java.io.*;
import java.util.*;
/*
 * USACO 2016 January Contest, Gold
 * Problem 1. Angry Cows
 * 
 * week of 5/8/18
 * 
 * Binary search 1 for the smallest radius R that will work
 * Within search 1, search for any starting point that works
 * 
 * error reports:
 * bsearchmodx didn't work because it only allows x to be a haybale, fixed
 * still wrong but promising (yields 4 instead of 3)
 * 4/10 test cases correct (fixed > --> >=
 * 
 */
public class AngryCows {
	public static int[] hay;
	public static int max = 0; // max haybale value
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("angry.in"));
		int N = Integer.parseInt(br.readLine());
		hay = new int[N]; // holds positions of the haybales
		for (int i = 0; i < N; i++) {
			hay[i] = Integer.parseInt(br.readLine());
			if (hay[i] > max) max = hay[i];
		}
		br.close();
		Arrays.sort(hay);
		//System.out.println(Arrays.toString(hay));
		
		////System.out.println(rightBound(2, 3, -1));
		////System.out.print(bSearchModRad(hay.length-1, 0));
		System.out.print(bSearchModRad(hay.length-1, 0));
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));

		pw.println(bSearchModRad(hay.length-1, 0)+".0");
		pw.close();
	}
	public static int bSearchModRad(int hi, int lo) { 
		// returns the lowest value R where all haybales can be exploded
		//System.out.println("bSearchModRad hi: " + hi + " lo: " + lo);
		
		// catch outer cases (if lo already works; if even hi doesn't work)
		if (bSearchModX(max, 0, lo)) {
			return lo;
		}
		else if (!bSearchModX(max, 0, hi)) {
			return -1;
		}
		
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		boolean curWorks = bSearchModX(max, 0, mid); // current status
			
		// narrow the range
		if (!curWorks) lo = mid + 1; // go bigger
		else if (curWorks) hi = mid; // go smaller
		else hi = mid; // equals --> desired result
		
		return bSearchModRad(hi, lo);
	}
	public static int bSearchModLo(int[] hay, int hi, int lo, int X, int R) { 
		// modified bsearch, returns lowest index i so hay[i] > X + R

		// values -- basecases for when hi - lo range reduced already
		// catch outer cases
		if (hay[lo] >= X - R) return lo; // if lo already works
		else if (!(hay[hi] >= X - R)) return -1; // if even hi doesn't work
			
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;

		int mid = (hi + lo)/2;
		boolean curWorks = (hay[mid] >= X - R); // current status

		// narrow the range
		if (!curWorks) lo = mid + 1; // if it doesn't work, go bigger
		else if (curWorks) hi = mid;  // if it works, go smaller
		else hi = mid; // equals --> desired result
		
		return bSearchModLo(hay, hi, lo, X, R);
	}
	public static int bSearchModHi(int hi, int lo, int X, int R) { 
		// modified bsearch, returns highest index i so hay[i] < X + R
		
		// values -- basecases for when hi - lo range reduced already
		
		// catch outer cases
		if (hay[hi] <= X + R) { // if hi already < X + R
			return hi;
		} else if (!(hay[lo] <= X + R)) { // if even lo is not < X + R
			////System.out.println("lo: " + lo + " hi: " + hi);
			return -1;
		}
		
		// basecase: hi doesn't work, lo doesn't not work, lo MUST work
		if (hi - lo == 1) {
			return lo;
		}
		
		int mid = (hi + lo)/2;
		boolean curWorks = hay[mid] <= X + R; // current status
			
		// narrow the range
		if (curWorks) lo = mid; // go bigger, don't exclude bc the mid could be the last one that works!
		else if (!curWorks) hi = mid - 1;  // go smaller
		else hi = mid; // equals --> desired result
			
		return bSearchModHi(hi, lo, X, R);
	}
	public static int rightBound(int X, int R, int prevX) {
		// returns the furthest right cow reachable from starting point X and radius R
		//System.out.println("    rb  X: " + X + "  R: " + R + "  prevX: " + prevX);
		if (X == -1) {
			//System.out.println("  ret prevX: " + prevX);
			return prevX;
		}
		if (R == 0) {
			//System.out.println("      ret x: " + X);
			return X;
		}
		int nextStart = bSearchModHi(hay.length-1, 0, X, R); // right-most cow reachable in current blast
		//System.out.println("    nextStart: " + hay[nextStart]);
		if (nextStart == -1 || hay[nextStart] == X) {
			//System.out.println("      ret X: " + X);
			return X;
		} else {
			int ret = rightBound(hay[nextStart], R-1, X);
			//System.out.println("      ret: " + ret);
			return ret;
		}
	}
	public static int leftBound(int X, int R, int prevX) {
		//System.out.println("    lb  X: " + X + " R: " + R + " prevX: " + prevX);
		if (X == -1) {
			//System.out.println("      ret prevX: " + prevX);
			return prevX;
		}
		if (R == 0) {
			//System.out.println("      ret x: " + X);
			return X;
		}
		
		// returns the furthest left cow reachable from starting point X and radius R
		int nextStart = bSearchModLo(hay, hay.length -1, 0, X, R); // right-most cow reachable in current blast
		//System.out.println("    nextStart: " + hay[nextStart]);
		if (nextStart == -1 || hay[nextStart] == X) {
			//System.out.println("    ret1: " + X);
			return X;
		} else {
			int ret = leftBound(hay[nextStart], R-1, -1);
			//System.out.println("    ret2: " + ret);
			return ret;
		}
	}
	public static boolean bSearchModX(int hi, int lo, int R) { 
		// returns if starting at any value X in range lo to hi will cover the whole range
		//System.out.println("  bSearchModX hi: " + hi + " lo: " + lo + " R: " + R);
		if (hi == lo) return false;
		
		// basecases for when hi - lo range reduced already
		
		// if lo or hi already works, return true
		if (R != 0 &&
				(rightBound(lo, R, -1) >= hay[hay.length-1] && leftBound(lo, R, -1) <= hay[0]
				|| rightBound(hi, R, -1) >= hay[hay.length-1] && leftBound(hi, R, -1) <= hay[0])) { 
			//System.out.println("  TRUE");
			return true;
		} else {
			//System.out.println("  NO1");
		}
		
		int mid = (hi + lo)/2;
		//System.out.println("  mid: " + mid);
		// if it mid works and reaches the right and left boundaries
		boolean rightValid = (rightBound(mid, R, -1) >= hay[hay.length-1]);
		boolean leftValid = (leftBound(mid, R, -1) <= hay[0]);
		
		// if mid works, return true
		if (rightValid && leftValid) {
			//System.out.println("  TRUE");
			return true;
		}
		// if there are gaps on both sides, it doesn't work
		if (!rightValid && !leftValid) {
			//System.out.println("  FALSE");
			return false;
		}
		
		// narrow the range
		if (!rightValid) { // go bigger (higher X)
			lo = mid + 1;
		} else if (!leftValid) { // go smaller (lower X)
			hi = mid; 
		} else { // equals --> desired result
			hi = mid;
			
		}
		return bSearchModX(hi, lo, R);
	}
}
