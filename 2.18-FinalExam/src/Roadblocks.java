import java.io.*;
import java.util.*;
// 4/10
/*
 * dijkstra from start and end
 * go through every edge
 * loop through all possible intermediate edges and go from 1 to start to end to N
 * 
 * or on dijkstra store first and second shortest path to every node
 */
public class Roadblocks {
	// maps paths using previous
	static Map<Integer, Integer> prev = new TreeMap<Integer, Integer>();
	static int[] dist;
	static boolean[] visited; // visited for the dij
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // adj list (weight, dest)
	static int cost = 0;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		int start = 1;
		int end = V;
		for (int i = 0; i <= V; i++) { // initialize adj list
			adj.add(new ArrayList<Pair>()); 
		}

		visited = new boolean[V+1];

		dist = new int[V+1]; // distance array from source to i

		for (int i = 0; i < E; i++) {
			// scan in edges
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			// 2 way edges
			adj.get(a).add(new Pair(c, b));
			adj.get(b).add(new Pair(c, a));
		}
		Arrays.fill(dist, Integer.MAX_VALUE);
		dij(start);

		//		System.out.println(dist[V]);


		int cur = V; // last
		ArrayList<Integer> path = new ArrayList<Integer>();
		path.add(cur);
		visited = new boolean[V+1];
		while (cur != -1 && prev.containsKey(cur) && !visited[cur]) {
			visited[cur] = true;
			//			System.out.println("here2");
			cur = prev.get(cur);
			path.add(cur);
		}
		//		System.out.println(path);

		int length = dist[V];
		int min = Integer.MAX_VALUE;

		ArrayList<Integer> path2 = new ArrayList<Integer>();
		ArrayList<Integer> path3 = new ArrayList<Integer>();

		for (int i = path.size()-2; i > 0; i--) { // loop through path
			// copy path 1

			// assess
			int node1 = path.get(i);
			int node2 = path.get(i-1); 
			// add a new node detour between node 1 and node2

			for (int j = 1; j <= V; j++) {
				if (j != node1 && j != node2) {

					path2 = new ArrayList<Integer>();
					for (int k = 0; k < path.size(); k++) { 
						path2.add(path.get(k));
					}
					//				System.out.println("path2 1: " + path2);

					path2.add(i, j);
					//				System.out.println("path2 2: " + path2);
				}
				
				// remove a node somwhere on the path
				for (int k = 0; k < path2.size(); k++) {
					path3 = new ArrayList<Integer>();
					for (int m = 0; m < path2.size(); m++) { 
						path3.add(path2.get(m));
					}
					if (k != 0 && k < path2.size()-2) {
						path3.remove(k);
					}
					
					
				//	System.out.println("path: " + path3);
					
					int curLen = calcLen(path3);
					if (curLen < min && curLen > length) {
						//					System.out.println("HERE: " + curLen);
						min = curLen;
				//		System.out.println("here");
					}
					
					
				}
				
			}


		}
		System.out.println(min);
	}
	public static int calcLen(ArrayList<Integer> path) {
		int count = 0;
		for (int i = path.size()-2; i > 0; i--) {
			int node1 = path.get(i);
			int node2 = path.get(i-1);
			//			System.out.println("node1: " + node1);
			//			System.out.println("node2: " + node2);

			boolean found = false;

			for (int j = 0; j < adj.get(node1).size(); j++) {
				if (adj.get(node1).get(j).y == node2) {
					found = true;
					count += adj.get(node1).get(j).x;
					break;
				}
			}
			if (!found) {
				return Integer.MAX_VALUE;
			}
		}
		return count;
	}
	public static void dij(int S) { // S = starting node
		PriorityQueue<Struct> pq = new PriorityQueue<Struct>();
		// add the edges that cross from S to VS
		pq.add(new Struct(new Pair(0, S), -1)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Struct s = pq.poll();
			Pair p = s.cur;
			int weight = p.x;
			int node = p.y;

			// check if node is visited
			if (visited[node]) {
				continue; // if already visited, don't add it
			} else {
				visited[node] = true; // mark visited and proceed
			}
			prev.put(p.y, s.prev); // mark prev node

			dist[node] = weight; // assign weight

			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				Pair next = new Pair(neigh.x + dist[node], neigh.y);
				pq.add(new Struct(next, node)); // add to PQ
			}


		}
	}
	public static class Struct implements Comparable<Struct> {
		Pair cur; 
		int prev; 

		public Struct(Pair a) {
			cur = a;
		}
		public Struct(Pair a, int b) {
			cur = a;
			prev = b;
		}
		@Override
		public int compareTo(Struct o) { // sort by value
			if (cur.x == o.cur.x) {
				return o.cur.y-cur.y;
			}
			return cur.x-o.cur.x;
		}

	}
	public static class Pair implements Comparable<Pair> {
		int x; // weight
		int y; // dest
		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			return x-o.x;
		}

	}
}
