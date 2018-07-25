import java.io.*;
import java.util.*;

/*
 * USACO 2017 December Contest, Gold 
 * Problem 1. A Pie for a Pie
 * 
 * 2/10 test cases first try
 * 5/10 second try (45 minutes in)
 * 6/10 test cases when fixing zeros array
 */
public class PiePie2 {
	static int[] dist;
	static boolean[] visited; // visited for the dij
	static ArrayList<ArrayList<Integer>> adj;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("piepie.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int D = Integer.parseInt(st.nextToken());
		
		LabeledPair[] bpOG = new LabeledPair[N]; // bessie's pies
		Pair[] bp = new Pair[N]; // bessie's pies
		Pair[] ep = new Pair[N]; // elsie's pies
		
		// stuff for dij later
		dist = new int[2*N];
		for (int i = 0; i < 2*N; i++) {
			dist[i] = -1;
		}
		visited = new boolean[2*N];
		ArrayList<Integer> zeros = new ArrayList<Integer>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			bp[i] = new Pair(a, b);
			bpOG[i] = new LabeledPair(i, new Pair(a, b));
		}
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			ep[i] = new Pair(a, b);
		}
		br.close();
		
		// CONSTRUCT GRAPH
		Arrays.sort(bp, (a, b) -> Integer.compare(a.x, b.x)); // sort by x (bessie value)
		Arrays.sort(bpOG, (a, b) -> Integer.compare(a.p.x, b.p.x)); // sort by x (bessie value)
		Arrays.sort(ep, (a, b) -> Integer.compare(a.y, b.y)); // sort by y (elsie value)

		for (int i = 0; i < N; i++) {
//			if (bp[i].x == 0 || bp[i].y == 0) {
			if (bp[i].y == 0) {
				zeros.add(i);
			}
//			if (ep[i].x == 0 || ep[i].y == 0) {
			if (ep[i].x == 0) {
				zeros.add(i+N);
			}
		}
//		
//		System.out.println("bessie's: " + Arrays.toString(bp));
//		System.out.println("elsie's: " + Arrays.toString(ep));
		
		// adj list (node, source) so you can trace backwards from the 'zeros'
		adj = new ArrayList<ArrayList<Integer>>(); 
		for (int i = 0; i < 2*N; i++) { // initialize
			adj.add(new ArrayList<Integer>()); 
		}
		
		for (int i = 0; i < N; i++) { // loop through bessie's pies
			Pair curPie = bp[i];
//			System.out.println("curPie: " + curPie);
			// if elsie receives curPie, she can gift back THESE pies 
			// from her own pies, greater than current one, from her POV (1)
			int loInd = leastAbove(ep, curPie.y, 1);
			// from her own pies, less than the current + ceiling, from her POV (1)
			int hiInd = greatestBelow(ep, curPie.y+D, 1);
//			System.out.println("  loInd: " + loInd + "  hiInd: " + hiInd);
			for (int j = loInd; j <= hiInd; j++) {
//				System.out.println("    here");
				adj.get(N+j).add(i); // link elsie's jth pie to bessie's ith pie
			}
		}
		for (int i = 0; i < N; i++) { // loop through elsie's pies
			Pair curPie = ep[i];
//			System.out.println("curPie: " + curPie);
			// if bessie receives curPie, she can gift back:
			// from her own pies, greater than current one, from her POV (0)
			int loInd = leastAbove(bp, curPie.x, 0);
			// from her own pies, greater than the current + ceiling, from her POV (0)
			int hiInd = greatestBelow(bp, curPie.x+D, 0);
//			System.out.println("  loInd: " + loInd + "  hiInd: " + hiInd);
			for (int j = loInd; j <= hiInd; j++) {
//				System.out.println("    here");
				adj.get(j).add(i+N); // link bessie's jth pie to elsie's ith pie
			}
		}
		
//		System.out.println(adj);
//		System.out.println(zeros);
		
		
		dij(zeros);
		
		int[] answers = new int[N];
		
		for (int i = 0; i < N; i++) { // put the answers back in the right order
			answers[bpOG[i].label] = dist[i];
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
		for (int i = 0; i < N; i++) {
			pw.println(answers[i]);
//			System.out.println(answers[i]);
		}
		
		pw.close();
	}
	public static void dij(ArrayList<Integer> S) { // S = starting nodes
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		
		for (int i = 0; i < S.size(); i++) {
			toVisit.add(S.get(i)); // destination
			dist[S.get(i)] = 1;
			visited[S.get(i)] = true;
		}
		
		while (!toVisit.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			int node = toVisit.poll();
			
			// check neighbors
			for (int i = 0; i < adj.get(node).size(); i++) {
				int neigh = adj.get(node).get(i); // neighbor
				if (visited[neigh]) continue;
				visited[neigh] = true;
				dist[neigh] = dist[node]+1;
				toVisit.add(neigh); // add to PQ
			}
		}
	}
	public static int greatestBelow(Pair[] arr, int val, int ind) {
		// returns greatest arr[i].x or .y <= val
		// if ind == 0, compare by arr[i].x; else compare by arr[i].y

		int lo = -1;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			int curVal;
			if (ind == 0) 
				curVal = arr[mid].x;
			else 
				curVal = arr[mid].y;
			
			if (curVal <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;
		
	}
	public static int leastAbove(Pair[] arr, int val, int ind) {
		// returns smallest arr[i].x or .y >= val
		// if ind == 0, compare by arr[i].x; else compare by arr[i].y
		int lo = 0;
		int hi = arr.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
//			System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);
			int curVal;
			if (ind == 0) 
				curVal = arr[mid].x;
			else 
				curVal = arr[mid].y;
			if (curVal >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
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
	public static class LabeledPair implements Comparable<LabeledPair> {
		int label;
		Pair p;

		public LabeledPair(int a, Pair b) {
			label = a;
			p = b;
		}
		@Override
		public int compareTo(LabeledPair o) { // sort by x, then y
			return p.compareTo(o.p);
		}
		public String toString() {
			return label + "  " + p;
		}
	}

}
