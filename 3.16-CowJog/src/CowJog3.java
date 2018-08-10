import java.io.*;
import java.util.*;
/*
 * USACO 2014 December Contest, Gold
 * Problem 3. Cow Jog
 * 
 * v2 is also fully working, and it's "my version" or at least more of "my code" 
 * (still using the idea from solution)
 * Also, it has documentation about what is going on
 *
 * had to look at solution and basically COPY it
 * 14/14 it was just because I didn't use longs
 */
public class CowJog3 {
	static int N;
	static int count = 0;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowjog.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num cows
		int T = Integer.parseInt(st.nextToken()); // num minutes

		// stores max ending position of any cow in that lane so far
		ArrayList<Long> laneMax = new ArrayList<Long>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			long start = Integer.parseInt(st.nextToken()); 
			long speed = Integer.parseInt(st.nextToken()); 
			long end = -(start + T*speed); // flip sign to use upper bound later
			
//			System.out.println(start + " " + end);
			
			if (laneMax.isEmpty() || end >= laneMax.get(laneMax.size()-1)) {
				laneMax.add(end);
			} else {
				int ind = 0;
				while (laneMax.get(ind) <= end) {
					ind++;
				}
				laneMax.set(ind, end);
			}
			
//			Collections.sort(laneMax);
//			System.out.println(laneMax);
		}
		br.close();

		System.out.println(laneMax.size());
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));

		pw.println(laneMax.size());
		pw.close();
	}
	
	public static int countIntersect(int[] curstart, int[] curend) {
		int curmax = 0;
		int count = 0;
		for (int i = 0; i < curstart.length; i++) {
			if (curstart[i] == -1) continue; // if marked invalid, skip it
			if (curend[i] <= curmax) {
				count++;
			} else {
				curmax = curend[i];
			}
		}
		return count;
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
