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
 * copied the treeSet part off of solution. this is basically the exact solution
 * 
 * 10/10
 */

public class AngryCows6 {	
	public static TreeSet<Long> set;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("angry.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/2.in"));
		
		int N = Integer.parseInt(br.readLine()); // number of haybales
		
		set = new TreeSet<Long>();
		for(int i = 0; i < N; i++) { // loop through, multiply everything by 10 to get rid of decimals
			set.add(10L*Integer.parseInt(br.readLine()));
		}
		br.close();
		
		
		// binary search for the radius
		long loR = 0; // lowest possible radius
		long hiR = set.last()-set.first(); // highest possible radius
		
		while (loR < hiR) { // binary search for R
			System.out.println("S1 loR: " + loR + "  hiR: " + hiR);
			
			long midR = (loR + hiR) / 2; // current R
			
			long loX = set.first(); // lowest possible X
			long hiX = set.last(); // highest possible X
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
		
		System.out.printf("%.1f\n", loR / 10.0);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));
		pw.printf("%.1f\n", loR / 10.0);
		pw.close();
	}
	public static boolean rightWorks(long X, long R) {
		// returns pos of rightmost cow reachable in one explosion chain
		if (R == 0) return false;
		
		if (set.last() > X + R) {
			// if the last cow can't been immediately reached
			long newX = set.floor(X + R);
			long newR = R - 10;
			
			while (newX != set.last()) {
				// while the last cow hasn't been reached
				if (set.floor(newX + newR) <= newX) {
					// if no more rightward progress is being made, you can't reach it
					return false;
				}
				newX = set.floor(newX + newR);
				newR -= 10;
			}
		}
		return true;

	}
	
	public static boolean leftWorks(long X, long R) {
		// returns if pos of leftmost cow reachable in one explosion chain covers whole thing

		if (R == 0) return false;

		if (set.first() < X - R) {
			// if the first cow can't been immediately reached
			long newX = set.ceiling(X - R);
			long newR = R - 10;

			while (newX != set.first()) {
				// while the first cow hasn't been reached
				if (set.ceiling(newX - newR) >= newX) {
					// if no more leftward progress is being made, you can't reach it
					return false;
				}
				newX = set.ceiling(newX - newR);
				newR -= 10;
			}

		}
		return true;

	}
	
}
