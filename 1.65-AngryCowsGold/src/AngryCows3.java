import java.io.*;
import java.util.*;
/*
 * USACO 2016 January Contest, Gold
 * Problem 1. Angry Cows
 * 
 * week of 6/4/18
 * 
 * Binary search 1 for the smallest radius R that will work
 * Within search 1, search for any starting point that works
 * 
 * uses long and multiplies everything by 10
 * but doesn't work bc of stack overflow errors
 */
public class AngryCows3 {
	public static long[] hay;
	public static long min = Long.MAX_VALUE; // min haybale value
	public static long max = 0; // max haybale value
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("angry.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/2.in"));
		
		int N = Integer.parseInt(br.readLine()); // number of haybales
		hay = new long[N]; // holds positions of the haybales
		
		for (int i = 0; i < N; i++) { // loop through, multiply everything by 10 to get rid of decimals
			hay[i] = Long.parseLong(br.readLine()) * 10;
		}
		br.close();
		Arrays.sort(hay);
		min = hay[0]; 
		max = hay[hay.length-1];
		System.out.println(Arrays.toString(hay));
		
		String ans = String.valueOf(bSearchModRad(max-min, 0)); // find the smallest working radius
		String ret = ans.substring(0, ans.length()-1) + "." + ans.charAt(ans.length()-1); // restore decimal
 		
		System.out.println(ret);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		pw.println(ret);
		pw.close();
	}
	public static long rightBound(long X, long R) { // returns rightmost cow reachable
		//System.out.println("    rb  X: " + X + "  R: " + R);
		
		if (R <= 0) return X; // radius has run out

		int nextStart = bSearchModHi(hay.length-1, 0, X, R); // right-most cow reachable in current blast
		
		if (nextStart == -1 || hay[nextStart] == X) { // if no cow or no other is reachable from the blast 
			System.out.println("      ret X: " + X);
			return X;
		} else {
			return rightBound(hay[nextStart], R-10);
		}
	}
	
	public static long leftBound(long X, long R) { // returns leftmost cow reachable
		System.out.println("    lb  X: " + X + " R: " + R );
		
		if (R <= 0) return X; // radius has run out
		
		int nextStart = bSearchModLo(hay.length -1, 0, X, R); // leftmost cow reachable in current blast
		System.out.println("    nextStart: " + hay[nextStart]);
		if (nextStart == 0) { // if the leftmost cow is the first cow, return the first cow
			return hay[nextStart];
		}
		
		if (nextStart == -1 || hay[nextStart] == X) { // if no cow or no other is reachable from the blast 
			//System.out.println("    ret1: " + X);
			return X;
		} else {
			return leftBound(hay[nextStart], R-10);
		}
	}
	
	public static boolean bSearchModX(long hi, long lo, long R) { 
		// returns true IF for some value X in range lo to hi, all haybales will explode 
		System.out.println("  bSearchModX hi: " + hi + " lo: " + lo + " R: " + R);

		if (R == 0) {
			System.out.println("HERE!");
			return false;
		}
		
		System.out.println((long)rightBound(lo, R));
		/*if ((long)rightBound(lo, R) >= (long)hay[hay.length-1] && leftBound(lo, R) <= hay[0]
				|| rightBound(hi, R) >= hay[hay.length-1] && leftBound(hi, R) <= hay[0]) { 
			//System.out.println("  TRUE");
			return true; // if lo or hi already works
		} */
		if ((long)rightBound(lo, R) >= (long)hay[hay.length-1] && leftBound(lo, R) <= hay[0]) { 
			//System.out.println("  TRUE");
			return true; // if lo or hi already works
		}
		if (hi == lo) {
			System.out.println("HERE!!");
			return false;
		}
		
		long mid = (hi + lo)/2;
		//System.out.println("  mid: " + mid);
		// if it mid works and reaches the right and left boundaries
		boolean rightValid = (rightBound(mid, R) >= hay[hay.length-1]);
		boolean leftValid = (leftBound(mid, R) <= hay[0]);
		
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
		} 
		return bSearchModX(hi, lo, R);
	}
	public static long bSearchModRad(long hi, long lo) { // returns lowest working radius R (all haybales exploded)
		System.out.println("Rad hi: " + hi + " lo: " + lo);
		if (bSearchModX(max, 0, lo)) return lo; // if lo already works, return lo
		else if (!bSearchModX(max, 0, hi)) {
			
			return -1; // if even hi doesn't work, return -1
		}
		if (hi - lo == 1) return hi; // lo doesn't work, hi works, hi is the only option		
		
		long mid = (long)(hi + lo)/2;
		System.out.println("Rad hi: " + hi + " lo: " + lo + " mid: " + mid);
		boolean curWorks = bSearchModX(max, 0, mid); // if current radius (mid) works
		
		if (!curWorks) lo = mid + 1; // go bigger if the radius doesn't work
		else if (curWorks) hi = mid; // go smaller if it does work but keep mid in range
		
		return bSearchModRad(hi, lo);
	}
	public static int bSearchModHi(int hi, int lo, long X, long R) { 
		// returns highest i so hay[i] <= X + R
		//System.out.println("hi: " + hi + " lo: " + lo + " X: " + X + " R: " + R);
		// catch base cases
		if (hay[hi] <= X + R) { // if hi already works
			//System.out.println("  ret: " + hi);
			return hi;
		} else if (!(hay[lo] <= X + R)) { // if even lo doesn't work
			//System.out.println("  ret: -1");
			return -1;
		}
		// hi doesn't work, lo doesn't not work, lo MUST work
		if (hi - lo == 1) {
			//System.out.println("  ret: " + lo);
			return lo;
		}
		
		int mid = (hi + lo)/2;
		boolean curWorks = (hay[mid] <= X + R); // current status
			
		// narrow the range
		if (curWorks) lo = mid; // go bigger, don't exclude bc the mid could be the last one that works!
		else if (!curWorks) hi = mid - 1;  // go smaller
		else hi = mid; // equals --> desired result
			
		return bSearchModHi(hi, lo, X, R);
	}
	public static int bSearchModLo(int hi, int lo, long X, long R) { 
		// returns lowest i so X - R <= hay[i]
		//System.out.println("  bSearchModLo hi: " + hi + " lo: " + lo + " X:" + X + lo + " R: " + R);

		// catch base cases
		if (X - R <= hay[lo]) return lo; // if lo already works
		else if (!(X - R <= hay[hi])) return -1; // if even hi doesn't work
			
		// now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;

		int mid = (hi + lo)/2;
		boolean curWorks = (X - R <= hay[mid]); // current status

		// narrow the range
		if (!curWorks) lo = mid + 1; // if it doesn't work, go bigger
		else if (curWorks) hi = mid;  // if it works, go smaller
		else hi = mid; // equals --> desired result
		
		return bSearchModLo(hi, lo, X, R);
	}
}
