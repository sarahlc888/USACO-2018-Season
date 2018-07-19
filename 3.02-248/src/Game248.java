import java.io.*;
import java.util.*;
/*
 * brute force version, times out, 1/10 correct
 */
public class Game248 {
	static int maxVal = 0;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("248.in"));
		int N = Integer.parseInt(br.readLine());
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			arr.add( Integer.parseInt(br.readLine()) );
		}
		br.close();

//		System.out.println(arr);
		
		LinkedList<ArrayList<Integer>> toVisit = new LinkedList<ArrayList<Integer>>();
		toVisit.add(arr);
		
		while (!toVisit.isEmpty()) {
			ArrayList<Integer> cur = toVisit.removeFirst();
			for (int i = 0; i < cur.size() - 1; i++) { // loop through positions
				if (cur.get(i) == cur.get(i+1)) { // if adjacent values are the same
					int val = cur.get(i);
					
					ArrayList<Integer> next = copyFunc(cur); // copy over value
					
					next.remove(i);
					next.set(i, val+1);
					
					maxVal = Math.max(maxVal, val+1);
					
					toVisit.addLast(next);
				}
			}
		}
		
//		System.out.println(maxVal);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("248.out")));

		pw.println(maxVal);
		pw.close();
	}
	public static ArrayList<Integer> copyFunc(ArrayList<Integer> a1) {
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		for (int i = 0; i < a1.size(); i++) {
			a2.add(a1.get(i));
		}
		return a2;
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
