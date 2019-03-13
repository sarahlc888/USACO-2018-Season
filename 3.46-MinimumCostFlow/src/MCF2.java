import java.io.*;
import java.util.*;
/*
 * 12/26
 * CCO 2017 S4
5 6 2
1 2 5
2 3 5
1 4 5
4 5 5
1 3 1
1 5 1

a random old attempt, out of sequence
 */
public class MCF2 {
	
	static ArrayList<Edge> MST = new ArrayList<Edge>(); // MST edges, max N-1 edges
	static ArrayList<Edge> Q = new ArrayList<Edge>(); // all edges
	static boolean discountUsed = false;
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in); 
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		long D = Long.parseLong(st.nextToken()); // discount
		
		for (int i = 0; i < M; i++) { // edges
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int weight = Integer.parseInt(st.nextToken());
			boolean d = i < N-1; // if edge is included in current plan
			Q.add(new Edge(a, b, weight, d, false, false)); // not discounted
			Q.add(new Edge(a, b, Math.max(0, weight-D), false, true, weight >= D)); // discounted
			
		}
		scan.close();
		
		DisjointSet ds = new DisjointSet(N); // disjoint set, maps nodes into groups
		Collections.sort(Q); // sort all the edges by weight, smallest weight first
		
		
		boolean[] included = new boolean[Q.size()];
		for (int i = 0; i < Q.size(); i++) {
			if (Q.get(i).origActive) included[i] = true;
		}
		boolean[] nextIncluded = new boolean[Q.size()];
		
		int i = 0; // iterate through edges
		while (i < Q.size() && MST.size() < N-1) { // while edges remain and MST != completed
			Edge cur = Q.get(i); // get the next edge, the shortest of the remaining			
			if (cur.discounted && discountUsed) {
				i++;
				continue; // only use one discount
			}
			
			// only add the edge if the nodes are not yet connected
			// otherwise, adding cur would create a cycle--there would already be an edge tethering cur in MST
			if (ds.find(cur.v1) != ds.find(cur.v2)) {
				if (cur.discounted) discountUsed = true; // mark discounted
				MST.add(cur); // add cur to the MST
				nextIncluded[i] = true;
				ds.union(cur.v1, cur.v2); // merge the sets for v1 and v2
			}
			i++;
		}
		long ct = 0;
		for (i = 0; i < included.length; i++) {
			if (included[i] && !nextIncluded[i] ||
					!included[i] && nextIncluded[i] && !Q.get(i).discounted) { // don't count the discount as a day
//				System.out.println("i: " + i);
				ct++;
			}
		}
		ct /= 2;
		System.out.println(ct);
//		System.out.println(Q);
//		System.out.println(Arrays.toString(included));
//		System.out.println(Arrays.toString(nextIncluded));
//		System.out.println(MST);
	}
	public static class Edge implements Comparable<Edge> { // represents an edge in the edge list
		int v1; // source
		int v2; // destination
		long weight; // cost
		boolean origActive; // whether included in plan
		boolean discounted;
		boolean fullDiscount;

		public Edge(int s, int d, long w, boolean act, boolean dis, boolean fd) {
			v1 = s;
			v2 = d;
			weight = w;
			origActive = act;
			discounted = dis;
			fullDiscount = fd;
		}
		public int compareTo(Edge e) { // sort by weight
			// TODO: do not directly prefer lower weight
			
			/*
			prefer fully discounted
			take edges:
			- low weight and og and discounted?
			- low weight and og and not discounted?
			
			- low weight and not og and not discounted
			
			- low weight and not og and discounted not fully
			- low weight and not og and discounted fully
			
			- high weight and og and discounted
			- high weight and og and not discounted
			
			- high weight and not og and not discounted
			
			- high weight and not og and discounted
			 */
			
			// do not prefer !origActive&&discounted
			
			
			// if it is not originally included and discounted, make it alter
			// prefer lower weight IF it is not (one of the ones not included that is discounted)
			
			if (weight != e.weight) { 
				if (weight < e.weight) return -1;
				else if (weight > e.weight) return 1;
			} if (origActive && !e.origActive) { // prefer active
				return -1;
			} else if (origActive && discounted && !(e.origActive && e.discounted)) {
				return -1;
			} else if (e.origActive && e.discounted && !(origActive && discounted)) {
				return 1;
			} else if (fullDiscount && !e.fullDiscount) { // prefer full discount
				return -1;
			} else if (e.fullDiscount && !fullDiscount) {
				return 1;
			
			} else if (discounted && !e.discounted) { // do not prefer discount?
				return 1;
			} else if (e.discounted && !discounted) {
				return -1;
			}
			return 0;
		}
		public String toString() {
			return v1 + " " + v2 + " "  + weight+ " dsct:" + discounted + " fd: " + fullDiscount;
		}
	}
	public static class DisjointSet {
		DSNode[] nodes; // array of Nodes
		
		public DisjointSet(int N) { // N = number of elements
			nodes = new DSNode[N]; // create and initialize the node[]
			
			for (int i = 0; i < N; i++) { 
				nodes[i] = new DSNode();
				nodes[i].ID = i; // each node is in its own set, parent = null
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

