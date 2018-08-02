import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 1. Out of Sorts
 * 
 * 5/10 test cases on a plain bash
 */
public class OutOfSortsBash {
	static int[] A;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("sort.in"));
		int N = Integer.parseInt(br.readLine());
		A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(br.readLine());
		}
		br.close();

//		System.out.println(Arrays.toString(A));
		
		int count = 0;
		
		
		boolean sorted = false;
		while (!sorted) {
			
			sorted = true;
//			System.out.println("moo");
			count++;
			for (int i = 0; i <= N-2; i++) {
				if (A[i+1] < A[i]) {
					swap(i, i+1);
//					System.out.println(Arrays.toString(A));
				}
			}
			for (int i = N-2; i >= 0; i--) {
				if (A[i+1] < A[i]) {
					swap(i, i+1);
//					System.out.println(Arrays.toString(A));
				}
			}
			for (int i = 0; i <= N-2; i++) {
				if (A[i+1] < A[i]) {
					sorted = false;
				}
			}
		}
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));

		pw.println(count);
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
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
