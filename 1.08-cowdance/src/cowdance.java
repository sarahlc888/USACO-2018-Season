import java.io.*;
import java.util.*;

// works
// changed return lo to return hi in the bsearch interval when there are two and it works??? why???

/* strategy: k can be from 1 to N (10,000)
 * N log N = 130,000 (binary search and then loop through all N during that time)
 * SHOULD BE WITHIN TIME
 * giving a runtime error/out of memory...
 */


public class cowdance {
	static int[] cowtimes;
	
	public static int findTime(int[] cows, int k) {
		if (k==0) return Integer.MAX_VALUE;
				
		// with cows[] and k at a time performing, how much time would it take for them to all be done
		
		int[] perf = new int[k]; // currently performing cows
		for (int i = 0; i < k; i++) 
			perf[i] = cows[i];
		
		// go through the rest of the cows and find out how much time it takes
		for (int i = k; i < cows.length; i++) {
			Arrays.sort(perf);
			// add the next cow to the slot that's finished first
			perf[0] += cows[i];
		}
		
		Arrays.sort(perf);
		return perf[k-1]; // return the last finished slot
	}
	public static void main(String args[]) throws IOException {
		
		Scanner scan = new Scanner(new File("cowdance.in"));
		int N = scan.nextInt();
		int T = scan.nextInt();
		cowtimes = new int[N];
		for (int i = 0; i < N; i++) {
			cowtimes[i] = scan.nextInt();
		}
		scan.close();
		// get 1 through N as possibilities
		
		int ind = bSearchY(T, N, 1);
		
		System.out.println(ind);
		
		PrintWriter PW = new PrintWriter(new File("cowdance.out"));
		PW.println(ind); // is often -1
		PW.close();
	}
	public static int bSearchY(int tLim, int hi, int lo) {
		int mid = (hi + lo)/2;
		
		System.out.println("  lo: " + lo);
		System.out.println("  mid: " + mid);
		System.out.println("  hi: " + hi);
		
		// exception catching
		if (tLim >= findTime(cowtimes, lo)) {
			// if even the smallest k passes the time limit, return lo
			System.out.println("t1");
			return lo;
		} else if (tLim < findTime(cowtimes, hi)) { 
			// if even the biggest k exceeds the time limit, return -1
			System.out.println("t2");
			return -1;
		}
		
		// basecase for when hi - lo range is tiny
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			System.out.println("t3");
			return hi; // ???????????? why does it work??? it used to be lo and didn't work...
			// return the higher K because it has to be within the time limit when its on the boundary
		}
		
		// narrow down
		if (findTime(cowtimes, mid) < tLim) {
			
			// go smaller with k
			hi = mid; // changed this???? why does it work??????
			// keep it in the pool of possibilities
			// lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			System.out.println("t4");
			return bSearchY(tLim, hi, lo);
		} else if (findTime(cowtimes, mid) > tLim) {
			// go bigger with k
			lo = mid + 1; // no need to keep it in the pool of possibilities
			// have to do plus one so there is no infinite loop
			System.out.println("t5");
			return bSearchY(tLim, hi, lo);
		} else {
			System.out.println("t6");
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}
}
