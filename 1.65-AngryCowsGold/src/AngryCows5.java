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
 * basically copied off of solution
 * 
 * 10/10
 */
public class AngryCows5 {
	public static long[] hay;
	
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
		
		// binary search for the radius
		long loR = 0; // lowest possible radius
		long hiR = hay[hay.length-1]-hay[0]; // highest possible radius
		
		while (loR < hiR) { // binary search for R
			//System.out.println("S1 loR: " + loR + "  hiR: " + hiR);
			
			long midR = (loR + hiR) / 2; // current R
			
			long loX = hay[0]; // lowest possible X
			long hiX = hay[hay.length-1]; // highest possible X
			while (loX < hiX) { // binary search for highest X that covers the left
				long midX = (loX + hiX + 1)/2; // +1 because it will make the lo == hi

				//System.out.println("loX: " + loX + " hiX: " + hiX + " midX: " + midX);
				if (leftWorks(midX, midR)) { // if it works, increase
					//System.out.println("A");
					loX = midX;
				} else { // otherwise, decrease
					//System.out.println("B");
					hiX = midX - 1; 
				}				
			}
			if (rightWorks(loX, midR)) { // lo will definitely work on the left
				// if it works on the right too, decrease hiR
				hiR = midR;
			} else {
				loR = midR + 1; // if it doesn't work on the right, increase (will make it hi)
			}
		}
		
		//System.out.printf("%.1f\n", loR / 10.0);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		pw.printf("%.1f\n", loR / 10.0);
		pw.close();
	}
	public static boolean rightWorks(long X, long R) {
		// returns pos of rightmost cow reachable in one explosion chain
		if (R == 0) return false;
		
		if (hay[hay.length-1] > X + R) {
			// if the last cow can't been immediately reached
			long newX = hay[greatestBelow(X + R)];
			long newR = R - 10;
			
			while (newX != hay[hay.length-1]) {
				// while the last cow hasn't been reached
				if (hay[greatestBelow(newX + newR)] <= newX) {
					// if no more rightward progress is being made, you can't reach it
					return false;
				}
				newX = hay[greatestBelow(newX + newR)];
				newR -= 10;
			}
		}
		return true;

	}
	
	public static boolean leftWorks(long X, long R) {
		// returns if pos of leftmost cow reachable in one explosion chain covers whole thing

		if (R == 0) return false;

		if (hay[0] < X - R) {
			// if the first cow can't been immediately reached
			long newX = hay[leastAbove(X - R)];
			long newR = R - 10;

			while (newX != hay[0]) {
				// while the first cow hasn't been reached
				if (hay[leastAbove(newX - newR)] >= newX) {
					// if no more leftward progress is being made, you can't reach it
					return false;
				}
				newX = hay[leastAbove(newX - newR)];
				newR -= 10;
			}

		}
		return true;

	}
	
	public static int greatestBelow(long val) {
		// returns greatest i <= X + R

		int lo = -1;
		int hi = hay.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2;
			
			if (hay[mid] <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;
		
	}
	public static int leastAbove(long val) {
		// returns smallest i >= X - R
		int lo = 0;
		int hi = hay.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2;
			
			if (hay[mid] >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
	}
	
	
}
