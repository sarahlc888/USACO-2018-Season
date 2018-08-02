import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 1. Out of Sorts
 * 
 * 10/10 test cases
 * had to look at answer
 * supposed to be binary index tree, but did smth else instead
 */
public class OOS2 {
	static Pair[] trackedArr;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("sort.in"));
		int N = Integer.parseInt(br.readLine());
		trackedArr = new Pair[N];
		for (int i = 0; i < N; i++) {
			trackedArr[i] = new Pair(Integer.parseInt(br.readLine()), i);
		}
		br.close();
		Arrays.sort(trackedArr);

//		System.out.println(Arrays.toString(trackedArr));
		
		int maxval = 0; // max crossings of the border
		int prev = 0;
		
		boolean[] seen = new boolean[N];
		
		for (int i = 0; i < N-1; i++) {
			// i represents the split after i
//			System.out.println("i: " + i);
			int temp = prev;
			int movedInd = trackedArr[i].ind;
			
			// if i was already seen before and incremented for
			// decrement it now because now it's on the right side
			if (seen[i]) temp--; 
			
			seen[movedInd] = true;
			if (movedInd > i) { // if it's on the wrong side, increment count	
				temp++;
			}
//			System.out.println("  temp: " + temp);
//			System.out.println("i: " + i + " temp: " + temp);
			if (temp > maxval) {
				maxval = temp;
//				System.out.println("i: " + i);
			}
			prev = temp;
		}
//		System.out.println("A!:" + maxval);
		if (maxval == 0) maxval = 1;
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
		System.out.println(maxval);
		pw.println(maxval);
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int ind;

		public Pair(int a, int b) {
			x = a;
			ind = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return ind-o.ind;
			return x-o.x;
		}
		public String toString() {
//			return x + " " + y;
			return ""+ind;
		}
	}


}
