import java.io.*;
import java.util.*;
/*
 * USACO 2018 February Contest, Gold
 * Problem 2. Directory Traversal
 * 
 * dijkstra from FILES (go backward)
 * 2/10 test cases
 * 3/10
 */
public class DT2 {
	static int N;
	static int[] dist;
	static boolean[][] visited;
	static int[] visitedCount;
	static ArrayList<ArrayList<Pair>> adj;
	static boolean[] isFile;
	static String[] nodes;
	static int filecount;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("dirtraverse.in"));
		N = Integer.parseInt(br.readLine()); // total number of files and dirs

		filecount = 0;
		isFile = new boolean[N]; // if false, then it's a dir
		nodes = new String[N];
		
		// set up graph
		// (dest, weight)
		adj = new ArrayList<ArrayList<Pair>>(); 
		for (int i = 0; i < N; i++)
			adj.add(new ArrayList<Pair>());
		
		for (int i = 0; i < N; i++) { // scan in each file or dir
			StringTokenizer st = new StringTokenizer(br.readLine());
			String name = st.nextToken(); // name
			nodes[i] = name;
			int numc = Integer.parseInt(st.nextToken()); // num children inside
			if (numc == 0) {
				isFile[i] = true;
				filecount++;
			}
			for (int j = 0; j < numc; j++) {
				int child = Integer.parseInt(st.nextToken()) - 1; // to fix indexing 
				adj.get(child).add(new Pair(i, -1)); // "../"
				adj.get(i).add(new Pair(child, 3)); // not known yet
//				adj.get(i).add(new Pair(child, name.length()+1)); // "name/"
			}
		}
		br.close();
//		System.out.println(adj);
//		System.out.println(Arrays.toString(nodes));
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < adj.get(i).size(); j++) {
				if (adj.get(i).get(j).y == -1) { // if the weight is uninitialized
					adj.get(i).get(j).y = nodes[i].length()+1; // "name/"
				}
			}
		}
		
//		System.out.println(adj);
		
		dist = new int[N];
		visited = new boolean[N][filecount]; // 0 == unvisited, 4 == all visited
		visitedCount = new int[N];
		long minCost = dij();
		
		System.out.println(minCost-filecount);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		pw.println(minCost-filecount);
		pw.close();
	}
	public static int dij() { // S = starting node
		PriorityQueue<Triple> pq = new PriorityQueue<Triple>();
		int cost = Integer.MAX_VALUE;

		// add the edges that cross from S to VS
		
//		for (int i = 0; i < files.size(); i++) {
//			pq.add(new Triple(i, new Pair(0, files.get(i)))); // par, weight, destination
//		}
		int count = 0;
		for (int i = 0; i < N; i++) {
			if (!isFile[i]) continue;
			pq.add(new Triple(count, new Pair(0, i))); // par, weight, destination
			count++;
		}
		
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Triple cur = pq.poll();
			int curpar = cur.par;
			int weight = cur.p.x;
			int node = cur.p.y;
			
			// check if node is visited
			visitedCount[node]++;
			
//			System.out.println("weight: " + weight + "  node: " + node + "  par: " + curpar);
			
			dist[node] += weight; // assign weight
			
//			System.out.println("  visited: " + Arrays.toString(visited[node]) );
			
			if (visitedCount[node] >= filecount && !isFile[node]) {
				cost = Math.min(cost, dist[node]);
			}
			
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				
				if (visited[neigh.x][curpar] || isFile[neigh.x]) continue;
				visited[neigh.x][curpar] = true;
				
				Triple next = new Triple(curpar, new Pair(neigh.y + weight, neigh.x));
//				System.out.println("    next: " + next);
				pq.add(next); // add to PQ
			}
//			System.out.println("  " + Arrays.toString(dist));
		}
		return cost;
	}
	public static class Triple implements Comparable<Triple> {
		int par;
		Pair p;

		public Triple(int a, Pair b) {
			par = a;
			p = b;
		}
		@Override
		public int compareTo(Triple o) { // sort by x, then y
			return p.compareTo(o.p);
		}
		public String toString() {
			return par + "  " + p;
		}
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
		}
	}
}
