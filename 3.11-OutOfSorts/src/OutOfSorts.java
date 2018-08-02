import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 1. Out of Sorts
 * 
 * 3/10 test cases formerly
 * now it doesn't really work at all
 * fix tomorrow...
 */
public class OutOfSorts {
	static int[] A;
	static int[] SA;
	static Pair[] A2;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("sort.in"));
		int N = Integer.parseInt(br.readLine());
		A = new int[N];
		SA = new int[N];
		A2 = new Pair[N]; // track initial indices after sorting (val, orig index)
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(br.readLine());
			SA[i] = A[i];
			A2[i] = new Pair(A[i], i);
		}
		br.close();
		Arrays.sort(SA);
		Arrays.sort(A2);
//		System.out.println(Arrays.toString(A2));

		// changed to be 0...N-1, the place where A[i] should be
		int[] moderated = new int[N]; 
		int ind = 0;
		for (int i = 0; i < N; i++) {
//			System.out.println("i: " + i);
//			System.out.println("  ind: " + ind);
//			System.out.println("  " + A2[i].y);
			moderated[A2[i].y] = ind; // mark original index as new index
//			ind++;
			
			if (i < N-1 && SA[i] != SA[i+1]) {
				ind++;
			}
		}
		
		
		int[] diff = new int[N]; // dist travelled to get sorted
		Pair[] diff2 = new Pair[N]; 
		for (int i = 0; i < N; i++) {
			diff[i] = A2[i].y-i;
			diff2[i] = new Pair(A2[i].y, A2[i].y-i);
		}
//		System.out.println(Arrays.toString(diff));
		Arrays.sort(diff2);
//		System.out.println(Arrays.toString(diff2));
//		System.out.println();
		
		int[] moves = new int[N];
		for (int i = 0; i < N; i++) {
			moves[i] = diff2[i].y;
		}
//		System.out.println(Arrays.toString(moderated));
//		System.out.println(Arrays.toString(moves));
		
		int maxval = 0;
		boolean[] seen = new boolean[N];
		int prev = 0;
		for (int i = 1; i < N; i++) {
			// i represents the split between i-1 and i
			int temp = prev;
			int val = moderated[i-1];
			seen[val] = true;
			int val2 = i-1;
			if (!seen[val2]) {
				temp++;
			} else {
				if (temp > 0)
					temp--;
			}
//			System.out.println("i: " + i + " temp: " + temp);
			maxval = Math.max(maxval, temp);
			prev = temp;
		}
//		System.out.println("A!:" + maxval);
		
		
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));

		pw.println(maxval);
		pw.close();
	}
	public static void swap(int i, int j) {
		int temp = A[j];
		A[j] = A[i];
		A[i] = temp;
	}
	
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return x-o.x;
		}
		public String toString() {
			return x + " " + y;
//			return ""+y;
		}
	}


}
