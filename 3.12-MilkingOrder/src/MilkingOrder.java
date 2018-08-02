import java.io.*;
import java.util.*;
/*
 * USACO 2018 US Open Contest, Gold
 * Problem 2. Milking Order
 * 8/2/18
 * 
 * 3/10 test cases
 * form DAG out of the observations, return topo sort
 * 
 * the condition to detect a cycle is wrong
 */
public class MilkingOrder {
	static int N;
	static int[] indegrees;
	static ArrayList<ArrayList<Integer>> adj;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new FileReader("milkorder.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // cows 1...N
		int M = Integer.parseInt(st.nextToken()); // num observations
		
		ArrayList<int[]> obs = new ArrayList<int[]>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int len = Integer.parseInt(st.nextToken());
			int[] o = new int[len];
			for (int j = 0; j < len; j++) {
				o[j] = Integer.parseInt(st.nextToken())-1; // correct indexing
			}
			obs.add(o);
		}
		br.close();
		
		// loop through all the obs and construct a graph
		indegrees = new int[N];
		int[] outdegrees = new int[N];
		int[] par = new int[N];
		adj = new ArrayList<ArrayList<Integer>>(); // par to child only
		for (int i = 0; i < N; i++) { // init
			par[i] = -1;
			adj.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < M; i++) {
//			System.out.println("obs: " + i + "  " + Arrays.toString(obs.get(i)));
			for (int j = 0; j < obs.get(i).length-1; j++) {
				int cur = obs.get(i)[j];
				int next = obs.get(i)[j+1];
//				System.out.println("  cur: " + cur + "  next: " + next);
//				if (outdegrees[next] == 0 || indegrees[cur] == 0) {
				if (outdegrees[next] == indegrees[next] || outdegrees[next] == 0) {
					// no cycle, proceed
					indegrees[next]++;
					outdegrees[cur]++;
					adj.get(cur).add(next);
				} else {
					// cycle
//					System.out.println("BREAK");
					break;
				}
			}
		}
		
//		System.out.println(adj);
		System.out.println(Arrays.toString(indegrees));
		int[] order = topoSort();
//		System.out.println(Arrays.toString(order));

		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milkorder.out")));
		for (int i = 0; i < N-1; i++) {
			System.out.print(order[i] + 1 + " ");
			pw.print(order[i] + 1 + " ");
		}
		System.out.print(order[N-1] + 1);
		pw.print(order[N-1]+1);
		pw.close();
	}
	public static int[] topoSort() {
		int[] sorted = new int[N]; // final array
		int ind = 0;
		
		PriorityQueue<Integer> inDegZero = new PriorityQueue<Integer>(); // all nodes with indegrees 0
//		Queue<Integer> inDegZero = new LinkedList<Integer>(); // all nodes with indegrees 0
		for (int i = 0; i < N; i++) {
			if (indegrees[i] == 0) {
				inDegZero.add(i);
			}
		}
		
		while (!inDegZero.isEmpty()) {
			int cur = inDegZero.poll(); // a node with indeg 0
			sorted[ind] = cur;
			ind++;
			for (int i = 0; i < adj.get(cur).size(); i++) { // loop through edges from cur
				int dest = adj.get(cur).get(i); 
				indegrees[dest]--;
				if (indegrees[dest] == 0) { // if indeg = 0, add to queue
					inDegZero.add(dest);
				}
			}
		}
		System.out.println("final: " + ind);
		
		return sorted;
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
