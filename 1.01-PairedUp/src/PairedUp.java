/*
 * USACO 2017 US Open Contest, Silver
 * Problem 1. Paired Up
 * All test cases correct
 */

import java.io.*;
import java.util.*;

public class PairedUp {
	public static class Point implements Comparable<Point> {
		public int x;
		public int y;
		public Point(int xin, int yin) {
			x = xin;
			y = yin;
		}
		@Override
		public int compareTo(Point o) {
			if (y > o.y) {
				return 1;
			} else if (y < o.y) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("pairup.in"));
		int N = scan.nextInt(); // the number of lines to follow
		Point[] cows = new Point[N]; // x = number of cows, y = milk output for each
		for (int i = 0; i < N; i++) 
			cows[i] = new Point(scan.nextInt(), scan.nextInt());
		scan.close();
		Arrays.sort(cows);
		
		int loInd = 0; // free cow with the lowest milking time
		int hiInd = N-1; // free cow with the highest milking time
		
		int maxTime = 0; // the max time to milk a pair (all milking is simul)
		
		while (loInd <= hiInd) {
			// increase the loInd and decrease the hiInd until there are available cows in each slot
			if (loInd == hiInd && cows[loInd].x == 0) 
				break;
			while (cows[loInd].x < 1) 
				loInd++;
			while (cows[hiInd].x < 1) 
				hiInd--;
			// decrement the available cows
			cows[loInd].x -= 1;
			cows[hiInd].x -= 1;
			// increment the total milking time
			int time = cows[loInd].y + cows[hiInd].y;
			// totalTime += cows[loInd].y + cows[hiInd].y;
			if (time > maxTime) {
				maxTime = time;
			}
		}
		
		PrintWriter pw = new PrintWriter(new File("pairup.out"));
		// pw.println(totalTime);
		pw.println(maxTime);
		pw.close();
	}
}
