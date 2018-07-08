import java.io.*;
import java.util.*;


public class DuelingGPSs {
	static int[] dist;
	static Map<Integer, Integer> prev = new TreeMap<Integer, Integer>();
	static boolean[] visited; // visited for the dij
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken()); // num nodes
		int E = Integer.parseInt(st.nextToken()); // num edges
		
		ArrayList<ArrayList<Pair>> mat1 = new ArrayList<ArrayList<Pair>>(); // gps1
		ArrayList<ArrayList<Pair>> mat2 = new ArrayList<ArrayList<Pair>>(); // gps2
		ArrayList<ArrayList<Pair>> mat3 = new ArrayList<ArrayList<Pair>>(); // graph
		
		dist = new int[V+1];
		visited = new boolean[V+1];
		
		// (weight, dest)
		
		for (int i = 0; i <= V; i++) { // initialize
			mat1.add(new ArrayList<Pair>());
			mat2.add(new ArrayList<Pair>());
			mat3.add(new ArrayList<Pair>());
		}
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			
			// bidirectional roads, add to adj lists
			mat1.get(a).add(new Pair(p, b));
			mat1.get(b).add(new Pair(p, a));
			
			mat2.get(a).add(new Pair(q, b));
			mat2.get(b).add(new Pair(q, a));
			
			mat3.get(a).add(new Pair(2, b));
			mat3.get(b).add(new Pair(2, a));
		}
		
		
		// find shortest path from 1...N according to gps1 and gps2
		System.out.println("GPS1");
		dij(1, mat1);
		
		for (int i = 1; i <= V; i++) {
			System.out.println("prev from " + i + " : " + prev.get(i));
		}
		
		int cur = V; // last
		ArrayList<Integer> p1 = new ArrayList<Integer>();
		p1.add(cur);
		boolean[] visited2 = new boolean[V+1];
		while (cur != -1 && prev.containsKey(cur) && !visited2[cur]) {
			visited2[cur] = true;
			System.out.println("here1");
			cur = prev.get(cur);
			p1.add(cur);
		}
		System.out.println("path1: " + p1);
		
		System.out.println(dist[V]);
		
		
		System.out.println("GPS2");
		// reset then dij
		dist = new int[V+1];
		visited = new boolean[V+1];
		dij(1, mat2);
		cur = V; // last
		ArrayList<Integer> p2 = new ArrayList<Integer>();
		p2.add(cur);
		visited2 = new boolean[V+1];
		while (cur != -1 && prev.containsKey(cur) && !visited2[cur]) {
			visited2[cur] = true;
			System.out.println("here2");
			cur = prev.get(cur);
			p2.add(cur);
		}
		System.out.println("path2: " + p2);
		
		// mark the paths that aren't on p1 and p2, decrease the mat 3 weight
		for (int i = p1.size()-2; i > 0; i--) { // loop through path
			int curNode = p1.get(i);
			int prevNode = p1.get(i-1);
			System.out.println("curNode: " + curNode + " prevNode: " + prevNode);
			
			int ind = mat3.get(curNode).indexOf(new Pair(2, prevNode));
			ind = Math.max(ind, mat3.get(curNode).indexOf(new Pair(1, prevNode)));
			mat3.get(curNode).get(ind).x--; // decrement cost
			ind = mat3.get(prevNode).indexOf(new Pair(2, curNode));
			ind = Math.max(ind, mat3.get(prevNode).indexOf(new Pair(1, curNode)));
			mat3.get(prevNode).get(ind).x--; // decrement cost
		}

		for (int i = p2.size()-2; i > 0; i--) { // loop through path
			mat3.get(p2.get(i)).get(p2.get(i-1)).x--; // decrement cost
			mat3.get(p2.get(i-1)).get(p2.get(i)).x--; // decrement cost
		}
		
		// dijkstra to get shortest path
		dij(1, mat3);
		System.out.println(Arrays.toString(dist));
		System.out.println(dist[V]);
		
	}
	public static void dij(int S, ArrayList<ArrayList<Pair>> adj) { // S = starting node
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
			System.out.println("cur: " + p.y + " prev: " + s.prev);
			prev.put(p.y, s.prev); // mark prev node
			
			dist[node] = weight; // assign weight
			
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				Pair next = new Pair(neigh.x + dist[node], neigh.y);
				System.out.println("next: " + next);
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
		int x; 
		int y; 

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by value
			if (x == o.x) {
				return o.y-y;
			}
			return x-o.x;
		}
		public String toString() {
			return "x: " + x + " y: " + y ;
		}
		public boolean equals(Pair o) {
			return (x == o.x && y == o.y);
		}

	}
}
