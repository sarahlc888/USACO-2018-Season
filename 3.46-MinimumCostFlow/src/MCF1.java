import java.io.*;
import java.util.*;
/*
 * 12/26
 * CCO 2017 S4
 * get normal MST and just apply discount to max edge weight
 */
public class MCF1 {
	static HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>(); // node, ids of edges
	static ArrayList<Edge> MST = new ArrayList<Edge>(); // MST edges, max N-1 edges
	static ArrayList<Edge> Q = new ArrayList<Edge>(); // all edges
	static boolean discountUsed = false;
	
	public static void main(String[] args) throws IOException {
//		Scanner scan = new Scanner(System.in); 
		Scanner scan = new Scanner(new File("42.in")); 
		

		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		long D = Long.parseLong(st.nextToken()); // discount
		for (int i = 0; i < N; i++) { 
			map.put(i, new ArrayList<Integer>());
		}
		for (int i = 0; i < M; i++) { // edges
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int weight = Integer.parseInt(st.nextToken());
			boolean d = i < N-1; // if edge is included in current plan
			Q.add(new Edge(a, b, weight, d)); // not discounted
		}
		scan.close();
		
		DisjointSet ds = new DisjointSet(N); // disjoint set, maps nodes into groups
		Collections.sort(Q, edgeWAComp); // sort all the edges by weight and activeness
		
		for (int i = 0; i < M; i++) { // map edges
			int a = Q.get(i).v1;
			int b = Q.get(i).v2;
			map.get(a).add(i);
			map.get(b).add(i);
		}
		
		
		boolean[] included = new boolean[Q.size()];
		for (int i = 0; i < Q.size(); i++) {
			if (Q.get(i).origActive) included[i] = true;
			Q.get(i).id = i;
		}
		boolean[] mstIncluded = new boolean[Q.size()];
		
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Integer>());
		}
		
		long mstCost = 0;
		long mstMax = 0;
		int mstMaxInd = 0;
		int i = 0; // iterate through edges
		while (i < Q.size() && MST.size() < N-1) { // while edges remain and MST != completed
			Edge cur = Q.get(i); // get the next edge, the shortest of the remaining			
			
			// only add the edge if the nodes are not yet connected
			// otherwise, adding cur would create a cycle--there would already be an edge tethering cur in MST
			if (ds.find(cur.v1) != ds.find(cur.v2)) {
				if (cur.discounted) discountUsed = true; // mark discounted
				MST.add(cur); // add cur to the MST
				mstIncluded[i] = true;
				ds.union(cur.v1, cur.v2); // merge the sets for v1 and v2
				
//				// connect 
//				adj.get(cur.v1).add(cur.v2);
//				adj.get(cur.v2).add(cur.v1);
				
				// list edge ID 
				adj.get(cur.v1).add(i);
				adj.get(cur.v2).add(i);
				
				mstCost += cur.weight;
				if (cur.weight > mstMax) {
					mstMax = cur.weight;
					mstMaxInd = i;
				}
			}
			i++;
		}
		mstCost -= Math.max(mstMax, D); // apply discount
		
		long ct = 0;
		for (i = 0; i < included.length; i++) {
			if (included[i] && !mstIncluded[i]) {
//				System.out.println("i: " + i);
				ct++;
			}
		}
//		System.out.println("ct: " + ct);
		
		// look for greatest new edge and if it uses the discount try to use a discount on a different 
//		Collections.sort(MST, edgeWAComp);
		
		if (mstMax < D) { // if discount is not fully used, try to find better
//			System.out.println("checkpoint mstMax: " + mstMax + " D: " + D);
			i = MST.size()-1;
here:		while (MST.get(i).weight == mstMax && i > 0) { // discount used on mstMax only
				
				if (MST.get(i).origActive) {
					i--;
					continue; // if og, skip it
				}
				// otherwise, check if the edge can be replaced with an OG edge
				Edge curEdge = MST.get(i);
				System.out.println("i: " + i + " curEdge: " + curEdge + " w: " + curEdge.weight + " D: " + D);
				int a = curEdge.v1;
				int b = curEdge.v2;
				int src = -1;
				int leaf = -1;
				if (adj.get(a).size() > 1 && adj.get(b).size() == 1) {
					// a is the source, b is the leaf
					src = a;
					leaf = b;
				} else if (adj.get(b).size() > 1 && adj.get(a).size() == 1){
					// b is the source, a is the leaf
					src = b;
					leaf = a;
				} else {
					System.out.println("Something is wrong");
					
				}
				System.out.println("src: " + src + " leaf: " + leaf);
				System.out.println(adj.get(a));
				System.out.println(adj.get(b));
				System.out.println(curEdge.id);
				System.out.println();
				// if any other edge coming from the node that is being connected is <= D, use it
				for (int j = 0; j < adj.get(leaf).size(); j++) {
					int curInd = adj.get(leaf).get(j);
					System.out.println("ci: " + curInd + " w: " + Q.get(curInd).weight);
					if (!included[curInd]) {
						System.out.println("exit 1");
						continue; // only consider included edges
					}
					if (mstIncluded[curInd]) {
						System.out.println("exit 2");
						continue; // only consider nonMST edges
					}
					Edge replacement = Q.get(curInd);
					if (replacement.weight > D) continue; // only consider weight <= D

					System.out.println("REDUCE");
					ct--; // reduce the count
					break here; // stop looping
				}
			i--;
		}

	}

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
		int id;
		public Edge(int s, int d, long w, boolean act) {
			v1 = s;
			v2 = d;
			weight = w;
			origActive = act;
		}
		public int compareTo(Edge e) { // sort by weight
			if (weight != e.weight) { // prefer lower weight
				if (weight < e.weight) return -1;
				else if (weight > e.weight) return 1;
			} if (origActive && !e.origActive) { // prefer active
				return -1;
			} 
			return 0;
		}
		public String toString() {
			return v1 + " " + v2;
		}
	}
	public static Comparator<Edge> edgeWAComp = new Comparator<Edge>(){
		@Override
		// todo: replace this with the real comparator
		public int compare(Edge f, Edge e) { // sort by weight
			if (f.weight != e.weight) { // prefer lower weight
				if (f.weight < e.weight) return -1;
				else if (f.weight > e.weight) return 1;
			} if (f.origActive && !e.origActive) { // prefer active
				return -1;
			} 
			return 0;
		}
	};
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


