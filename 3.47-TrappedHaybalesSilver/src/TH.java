import java.io.*;
import java.util.*;
/*
 * USACO 2015 US Open, Silver
 * Problem 2. Trapped in the Haybales (Silver)
 * 
 * 14/14 cases
 */
public class TH {
	static int N;
	static int B;
	static Pair[] hay;
	static long minAdd = Long.MAX_VALUE; // min amt needed to trap bessie 
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("trapped.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num haybales
		B = Integer.parseInt(st.nextToken()); // start pos
		hay = new Pair[N];
		int maxPos = 0;
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			int size = Integer.parseInt(st.nextToken());
			int pos = Integer.parseInt(st.nextToken());
			hay[i] = new Pair(pos, size);
			maxPos = Math.max(pos, maxPos);
		}
		br.close();

		Arrays.sort(hay);
//		System.out.println(Arrays.toString(hay));
//		for (int i = 0; i < N; i++) {
//			hay[i].id = i;
//		}
		
		/*
		// just for printing and vis
		int[] roadVis = new int[maxPos+1];
		for (int i = 0; i < N; i++) {
			roadVis[hay[i].p] = hay[i].s;
		}
		roadVis[B] = 101;
		System.out.println(Arrays.toString(roadVis));
		*/
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("trapped.out")));
		if (hay.length <= 1) { // if <2 haybales, you can't add hay
			pw.println(-1);
		} else {
			goLeft();
			goRight();
			if (minAdd == Long.MAX_VALUE) {
//				System.out.println(-1);
				pw.println(-1);
			} else {
//				System.out.println(minAdd);
				pw.println(minAdd);
			}
		}
		pw.close();
	}
	public static void goLeft() {
		// go left until you are stuck, go right until unstuck (if ever), repeat
		int leftInd = greatestBelow(B); 
		int rightInd = leastAbove(B);
		
		if (leftInd < 0 || rightInd < 0) { // no haybale, can't stop motion
			minAdd = -1;
			return;
		}
		
here:	while (leftInd >= 0 && rightInd < N) {
			Pair leftHay = hay[leftInd];
			Pair rightHay = hay[rightInd];
			long power = rightHay.p-leftHay.p; // power from distance
			
			if (power <= leftHay.s && power <= rightHay.s) { // immediately stuck
				minAdd = 0;
				break;
			}
			
//			System.out.println("leftInd: " + leftInd + " rightInd: " + rightInd);
			while (power > leftHay.s) { // go left while power > size (until you are stuck)
				leftInd--;
				if (leftInd < 0) break here;
				leftHay = hay[leftInd];
				power = rightHay.p-leftHay.p;
			}
			// go right while power <= size (until unstuck)
			while (power <= leftHay.s) {
				if (power > rightHay.s) { // power > size (you can go right)
					minAdd = Math.min(power-rightHay.s, minAdd); // consider cost of getting stuck
					rightInd++; // go right
					if (rightInd == N) break here; // escape, the end
					rightHay = hay[rightInd];
					power = rightHay.p-leftHay.p;
				} else { // you cannot go right, already stuck
					minAdd = 0;
					break; 
				}
			}
		}
	}
	public static void goRight() {
		// go right until you are stuck, go left until unstuck (if ever), repeat
		int leftInd = greatestBelow(B); 
		int rightInd = leastAbove(B);
		
		if (leftInd < 0 || rightInd < 0) { // no haybale, can't stop motion
			minAdd = -1;
			return;
		}
		
here:	while (leftInd >= 0 && rightInd < N) {
			Pair leftHay = hay[leftInd];
			Pair rightHay = hay[rightInd];
			long power = rightHay.p-leftHay.p; // power from distance
			if (power <= leftHay.s && power <= rightHay.s) { // if immediately stuck
				minAdd = 0;
				break;
			}
//			System.out.println("leftInd: " + leftInd + " rightInd: " + rightInd);
			// go right while power > size (until you are stuck)
			while (power > rightHay.s) { 
				rightInd++;
				if (rightInd == N) break here; // escape, the end
				rightHay = hay[rightInd];
				power = rightHay.p-leftHay.p;
			}
			// go left while power <= size (until unstuck)
			while (power <= rightHay.s) {
				if (power > leftHay.s) { // power > size (you can go left)
					minAdd = Math.min(power-leftHay.s, minAdd); // consider cost of getting stuck
					leftInd--; // go left
					if (leftInd < 0) break here; // escape, the end
					leftHay = hay[leftInd];
					power = rightHay.p-leftHay.p;
				} else { // you cannot go left, already stuck
//					System.out.println("here2");
					minAdd = 0;
					break; 
				}
			}
		}
	}	
	public static int leastAbove(long val) {
		// returns index of smallest haybale with pos > val
		int lo = 0;
		int hi = hay.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if statement
			if (hay[mid].p > val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		if (lo >= 0 && lo < N && hay[lo].p > val) return lo; // make sure lo works (catches rare exceptions)
		return -1;
	}
	public static int greatestBelow(long val) {
		// returns greatest i < val

//		int lo = -1;
		int lo = 0;
		int hi = hay.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			if (hay[mid].p < val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (hi >= 0 && hi < N && hay[hi].p < val) return hi;
		return -1;
	}
	public static class Pair implements Comparable<Pair> {
		int p;
		int s;
//		int id;

		public Pair(int a, int b) {
			p = a;
			s = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by pos then size
			if (p == o.p) return s-o.s;
			return p-o.p;
		}
		public String toString() {
			return p + " " + s;
		}
	}


}
