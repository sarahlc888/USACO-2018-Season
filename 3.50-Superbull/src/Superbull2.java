import java.util.*;
import java.io.*;
/*
 * 12/31/18
 * Max spanning tree idea (got hint) 10/10 test cases
 * USACO 2015 February Contest, Silver
 * Problem 3. Superbull
 */

public class Superbull2 {
	public static void main(String[] args) throws IOException {
		// input ids
		BufferedReader br = new BufferedReader(new FileReader("superbull.in"));
		int N = Integer.parseInt(br.readLine());
		long[] ids = new long[N];
		for (int i = 0; i < N; i++) {
			ids[i] = Integer.parseInt(br.readLine());
		}
		// calculate scores
//		long[][] score = new long[N][N];
		ArrayList<Edge> Q = new ArrayList<Edge>(); // list of all edges
		int ind = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				long xorVal = ids[i]^ids[j];
//				score[i][j] = xorVal;
//				score[j][i] = xorVal;
				Q.add(new Edge(i, j, xorVal));
				ind++;
			}
		}
		
		DisjointSet ds = new DisjointSet(N); // disjoint set, maps nodes into groups
		Collections.sort(Q); // sort all the edges by weight, largest weight first
		
		int i = 0; // iterate through edgelist Q
		long total = 0;
		int numEdges = 0;
		while (i < Q.size() && numEdges < N-1) { // while edges remain and MST != completed
			Edge cur = Q.get(i); // get the next edge, the longest of the remaining
			if (ds.find(cur.v1) != ds.find(cur.v2)) {
				ds.union(cur.v1, cur.v2); // merge the sets for v1 and v2
				total += cur.weight;
				numEdges++;
			}
			i++;
		}
		br.close();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("superbull.out")));
//		System.out.println(total);
		pw.println(total);
		pw.close();
	}
	public static class Point { // represents an edge in the adj lists
		int dest; // destination
		int weight; // cost
		
		public Point() {
			dest = 0;
			weight = 0;
		}
		public Point(int d, int c) {
			dest = d;
			weight = c;
		}
	}
	public static class Edge implements Comparable<Edge> { // represents an edge in the edge list
		int v1; // source
		int v2; // destination
		long weight; // cost
		
		public Edge() {
			v1 = 0;
			v2 = 0;
			weight = 0;
		}
		public Edge(int s, int d, long w) {
			v1 = s;
			v2 = d;
			weight = w;
		}
		public int compareTo(Edge e) { // sort by weight
			if (weight < e.weight) return 1;
			if (weight > e.weight) return -1;
			return 0;
		}
		
	}
	public static class DisjointSet {
		DSNode[] nodes; // array of Nodes
		
		public DisjointSet(int N) {
			// N = number of elements
			
			// create and initialize the node[]
			nodes = new DSNode[N];
			
			for (int i = 0; i < N; i++) {
				nodes[i] = new DSNode();
				nodes[i].ID = i;
				// each node is in its own set, parent = null
			}
			
		}
		
		public int union(int a, int b) {
			// join the sets with node id a and node id b, returns the final parent
			
			DSNode rootA = nodes[find(a)]; // root of A
			DSNode rootB = nodes[find(b)]; // root of B
			rootB.parentID = rootA.ID; // make rootA parent of rootB
			return rootA.ID; // return rootA
		}
		public int find(int a) {
			// returns the head of the set with node a
			DSNode curnode = nodes[a]; 
			int steps = 0; // number of steps between final parent and curnode
			// will = 0 if all connections are direct (* shape instead of chain)
			while (curnode.parentID >= 0) {
				curnode = nodes[curnode.parentID]; // jump up to the parent
				steps++; // increment steps
			}
			if (steps > 1) { // if the connection is not direct, streamline path
				compress(a, curnode.ID);
			}
			return curnode.ID; // the final parent of a
		}
		public void compress(int a, int root) {
			DSNode curnode = nodes[a];
			
			// trace the chain from curnode to root and make cur.parent = root
			
			while (curnode.ID != root) { // while curnode is not the root
				int temp = curnode.parentID; // the parent of curnode
				curnode.parentID = root; // set the direct root path
				curnode = nodes[temp]; // make the parent curnode, cont tracing the path
			}
		}
	}
	public static class DSNode { // node in disjoint set
		int val;
		int ID;
		int parentID;
		
		public DSNode(int v, int id, int pid) {
			val = v;
			ID = id;
			parentID = pid;
		}
		public DSNode() {
			val = 0;
			ID = 0;
			parentID = -1;
		}
	}
}

