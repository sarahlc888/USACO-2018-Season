import java.io.*;
import java.util.*;
/*
 * <= N-1 steps
 */
public class P21 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("sleepy.in"));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
//		boolean[] present = new boolean[N+1];
//		LinkedList<Integer> order = new LinkedList<Integer>();
		ArrayList<Integer> order = new ArrayList<Integer>();
		order.add(0,arr[N-1]);
		int ind = N-2;
		for (ind = N-2; ind >= 0; ind--) {
			if (arr[ind] > arr[ind+1]) {
				break;
			}
//			order.addFirst(arr[ind]);
			order.add(0, arr[ind]);
//			present[arr[ind]] = true;
		}
		// anything after ind sorted
//		System.out.println(order);
		
		int[] moves = new int[N];
		
		for (int i = 0; i < N-1; i++) { // turns
//			System.out.println(order);
			if (order.size() == N) break;
			int curCow = arr[i];
			int dist = 0;
			int bin1 = -1;
			int bin2 = -1;
//			bin1 = leastAbove(order, curCow);
			bin1 = -1*Collections.binarySearch(order, curCow)-1;
			if (bin1 >= 0) { // use bin1
				order.add(bin1, curCow);
				dist = N-order.size()+bin1;
			} 
			else { // use bin2
				bin2 = greatestBelow(order, curCow);
				order.add(bin2+1, curCow);
				dist = N-order.size()+bin2+1;
			}
			moves[i] = dist;
		}
		ArrayList<Integer> ret = new ArrayList<Integer>();
//		System.out.println(Arrays.toString(moves));
		
		for (int i = 0; i < moves.length; i++) {
			if (moves[i] == 0) break;
			ret.add(moves[i]);
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
		pw.println(ret.size());
		System.out.println(ret.size());
		for (int i = 0; i < ret.size()-1; i++) {
			pw.print(ret.get(i) + " ");
//			System.out.print(ret.get(i) + " ");
		}
		pw.println(ret.get(ret.size()-1));
		pw.close();
		
//		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
//		pw.println("3");
//		pw.println("2 2 3");
//		pw.close();
	}
	public static int greatestBelow(ArrayList<Integer> arr, long val) {
		// returns greatest i <= val

//		int lo = -1;
		int lo = 0;
		int hi = arr.size()-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
//			System.out.println("lo: " + lo+ " hi: " + hi + " mid: " + mid);
			if (arr.get(mid) <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (arr.get(hi) <= val) return hi;
		return -1;
	}
	public static int leastAbove(LinkedList<Integer> arr, long val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.size()-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if statement
//			System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);

			if (arr.get(mid) >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		if (arr.get(lo) >= val) return lo; // make sure lo works (catches rare exceptions)
		return -1;
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
