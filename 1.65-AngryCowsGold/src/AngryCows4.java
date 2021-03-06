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
 * but doesn't work at all
 */
public class AngryCows4 {
	public static long[] hay;
	
	public static void main(String args[]) throws IOException {

		//BufferedReader br = new BufferedReader(new FileReader("angry.in"));
		BufferedReader br = new BufferedReader(new FileReader("testData/2.in"));
		
		int N = Integer.parseInt(br.readLine()); // number of haybales
		hay = new long[N]; // holds positions of the haybales
		
		for (int i = 0; i < N; i++) { // loop through, multiply everything by 10 to get rid of decimals
			hay[i] = Long.parseLong(br.readLine()) * 10;
		}
		br.close();
		Arrays.sort(hay);
		
		// binary search for the radius
		long loR = 0; // lowest possible radius
		long hiR = hay[hay.length-1]-hay[0]; // highest possible radius
		
		long ret = -1;
		
		while (loR < hiR) {
			System.out.println("S1 loR: " + loR + "  hiR: " + hiR);
			//long loX = hay[0]; // lowest possible starting point
			//long hiX = hay[hay.length-1]; // highest possible starting point
			
			if (bSearchModX(hay[hay.length-1], hay[0], loR)) {
				System.out.println("HERE1");
				ret = loR;
				break;
			} else if (!bSearchModX(hay[hay.length-1], hay[0], hiR)) {
				System.out.println("HERE2");
				ret = -1;
				break;
			} else if (hiR - loR == 1) {
				System.out.println("HERE3");
				ret = hiR;
				break;
			}
			
			long midR = (loR + hiR) / 2; // current R
			System.out.println("  midR: " + midR);
			
			if (bSearchModX(hay[hay.length-1], hay[0], midR)) {
				System.out.println("E1a");
				hiR = midR;
			} else {
				System.out.println("E1b");
				loR = midR + 1;
			}
			
		}

		System.out.println(ret);

		System.out.println(loR);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		pw.println(loR);
		pw.close();
	}
	public static boolean bSearchModX(long hiX, long loX, long R) {
		// return if there exists some X between hiX and loX for which R works
		System.out.println("  start X hiX: " + hiX + " loX: " + loX + " R: " + R);
		long ret = -1;
	
		while (true) {
			// get the smallest X so that the right side still explodes
			if (R == 0) {
				return false;
			} 
			if (rightWorks(loX, R)) { // if even the loX works
				ret = loX;
				break;
			}

			long midX = (loX + hiX)/2; // current midX

			boolean right = rightWorks(midX, R);
			
			if (right) { // if the right side works
				hiX = midX; // decrease X range
			} else { // if the right side doesn't work
				loX = midX + 1;
			}
			/*boolean left = leftWorks(midX, R);
			
			if (right && left) { // current X and R are valid
				ret = true;
				break;
			} else if (right) { // only right side works
				hiX = midX - 1; // update X range
			} else if (left) { // only left side works
				loX = midX + 1; // update X range
			} else { // neither side works
				ret = false;
				break;
			}*/
			if (hiX-loX <= 1) {
				ret = hiX;
				break;
			}
		}
		//System.out.println("  end X " + ret);
		return leftWorks(ret, R);
	}
	public static boolean rightWorks(long X, long R) {
		// returns pos of rightmost cow reachable in one explosion chain
		System.out.println("    rw  X: " + X + "  R: " + R);

		long curX = X;
		long curR = R;

		while (true) {
			// break if there is no more radius left or if the blast has reached the rightmost cow
			if (curR <= 0 || curX >= hay[hay.length-1]) {
				break; 
			}

			int nextStart = rightMost(curX, curR); // right-most cow reachable in current blast
			//System.out.println("NS: " + nextStart);
			if (nextStart == -1 || hay[nextStart] <= curX) break; // if the next one isn't further right

			curX = hay[nextStart]; // increase curX
			curR -= 10; // reduce radius
		}
		System.out.println("    " + (curX >= hay[hay.length-1]));
		return curX >= hay[hay.length-1];
	}
	public static int rightMost(long X, long R) { 
		// returns index of rightmost cow reachable in one section of an explosion
		int lo = 0;
		int hi = hay.length-1;
		int ret = -1;
		
		while (lo < hi) {
			if (hay[hi] <= X + R) { // if hi is in range
				ret = hi;
				break;
			}
			if (hay[lo] > X + R) { // if even lo is NOT in range
				ret = -1;
				break;
			}
			if (hi - lo == 1) return lo; // bc it's the only option left
			int mid = (lo + hi)/2;
			
			if (hay[mid] <= X + R) { // if mid is in range
				lo = mid;
			} else { // mid is not in range
				hi = mid - 1;
			}
		}
		return ret;
	}
	public static boolean leftWorks(long X, long R) {
		// returns if pos of leftmost cow reachable in one explosion chain covers whole thing

		System.out.println("    lw  X: " + X + " R: " + R );

		long curX = X;
		long curR = R;

		while (true) {
			// break if there is no more radius left or if the blast has reached the leftmost cow
			if (curR <= 0 || curX <= hay[0]) break; 

			int nextStart = leftMost(curX, curR); // left-most cow reachable in current blast

			if (nextStart == -1 || hay[nextStart] >= curX) break; // if the next one isn't further left

			curX = hay[nextStart]; // decrease curX
			curR -= 10; // reduce radius
		}
		System.out.println("    " + (curX <= hay[0]));
		return curX <= hay[0];
	}
	
	public static int leftMost(long X, long R) { 
		// returns index of leftmost cow reachable in one section of an explosion
		int lo = 0;
		int hi = hay.length-1;
		int ret = -1;
		
		while (lo < hi) {
			if (hay[lo] >= X - R) { // if lo is in range
				ret = lo;
				break;
			}
			if (hay[hi] < X - R) { // if even hi is NOT in range
				ret = -1;
				break;
			}
			if (hi - lo == 1) return hi; // bc it's the only option left
			int mid = (lo + hi)/2;
			
			if (hay[mid] >= X - R) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return ret;
	}
	
}
