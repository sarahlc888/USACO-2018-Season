import java.io.*;
import java.util.*;
/*
 * USACO 2018 January Contest, Gold
 * Problem 2. Cow at Large
 * 
 * tree traversal
 * 13/13 first try!
 * 
 * 1 hr
 */
public class CowAtLarge {
	static ArrayList<Integer> exits;
	static int N;
	static int K;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new FileReader("atlarge.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num barns
		K = Integer.parseInt(st.nextToken()) - 1; // starting barn
		
		int[] par = new int[N]; // par[i] = parent of barn i
		int[] dist = new int[N]; // dist[i] = dust from i to K
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < N; i++) { // init adj lists
			adj.add(new ArrayList<Integer>());
			par[i] = dist[i] = -1;
		}
		
		for (int i = 1; i < N; i++) { // scan in connections
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1; 
			int b = Integer.parseInt(st.nextToken())-1; 
			adj.get(a).add(b);
			adj.get(b).add(a);
		}
		br.close();
		
//		System.out.println(adj);

		exits = new ArrayList<Integer>(); // list of exits
		for (int i = 0; i < N; i++) {
			if (adj.get(i).size() == 1) exits.add(i);
		}
//		System.out.println(exits);
		
		// root the tree at K, calc all distances to K
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		toVisit.add(K); // root at K
		dist[K] = 0; // distance from K to K is 0
		
		while (!toVisit.isEmpty()) {
			int i = toVisit.removeFirst(); // current barn
			for (int j = 0; j < adj.get(i).size(); j++) {
				int child = adj.get(i).get(j);
				
				if (dist[child] == -1) { // if unvisited
					par[child] = i;
					toVisit.add(child);
					dist[child] = dist[i]+1;
				}
			}
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("atlarge.out")));
		if (exits.contains(K)) {
			System.out.println(1);
			pw.println(1);
			return;
		}
//		System.out.println(Arrays.toString(dist));
		
		int count = 0;
		
		// go down the tree starting from K
		// go up the tree starting from the exits
		int[] status = new int[N]; // 0 == empty, -1 = bessie, 1 = police
		status[K] = -1;
		toVisit = new LinkedList<Integer>();
		
		for (int i = 0; i < exits.size(); i++) {
			toVisit.addLast(exits.get(i));
			status[exits.get(i)] = 1;
		}
		toVisit.add(K);
		while (!toVisit.isEmpty()) {
			int b = toVisit.removeFirst(); // current
			int val = status[b];
			if (val > 0) {
				// if it's from an exit, process just the parent
				int next = par[b];
				if (status[next] == 0) { // normal
					status[next] = status[b];
					toVisit.add(next);
				} else if (status[next] < 0) {
					// ran into Bessie
				} else { // status[next] > 0, already visited
					status[next] += status[b];
					count++;
				}
				
			} else if (val < 0) {
				// if it's from K, process all adjs except the parent
				for (int i = 0; i < adj.get(b).size(); i++) {
					int next = adj.get(b).get(i);
					if (next == par[b]) continue;
					
					if (status[next] == 0) {
						status[next] = -1; // mark as Bessie's
						toVisit.add(next);
					}
					// if it clashes with something else, do nothing
				}
			}
			
		}
		
//		System.out.println(count);
		System.out.println(exits.size()-count);
		
		// old ideas
		// binary search for the proper number of farmers
		// min amount that works (least above)
//		int F = leastAbove();
		
		pw.println(exits.size()-count);
		pw.close();
	}
	public static int leastAbove() {
		// returns smallest i so that Bessie cannot escape
		int lo = 1; // min == at least 1 farmer 
		int hi = exits.size(); // max == farmer at every exit
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
			System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);

			if (works(mid)) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		if (works(lo)) return lo; // make sure lo works (catches rare exceptions)
		return -1;
	}
	public static boolean works(int f) {
		// TODO: write this function
		int[] track = new int[N]; // 0 == empty, 1 = bessie, -1 = police
		track[K] = 1;
		int count = N-1-f; // number of empty barns
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		while (count > 0 && !toVisit.isEmpty()) {
			// while there are still empty barns
			
			
		}
		
		
		
		return true;
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
