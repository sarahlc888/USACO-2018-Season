import java.io.*;
import java.util.*;
/*
 * uses Tarjans from 3.20
 * 
 * had to look at solution but got pretty close on my own
 * 
 * create a DAG by making graph out of all the strongly connected components
 * loop through all edges, b-->a, find longest path from 1 to a and longest path from b to 1
 * compute length
 * 
 * 14/14 test cases, fixed time out by using a treeset in adj2
 */
public class GC2 {
	static int N;
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
	static ArrayList<TreeSet<Integer>> adj2 = new ArrayList<TreeSet<Integer>>(); // for scc graph
	static ArrayList<ArrayList<Integer>> adj3 = new ArrayList<ArrayList<Integer>>(); // reverse scc graph
	static int[] id;
	static int curid;
	static int[] low;
	static boolean[] onStack;
	static Stack<Integer> s = new Stack<Integer>();
	static int sccCount = 0;
	static ArrayList<Integer> sccSize = new ArrayList<Integer>();
	static int[] sccMap;
	static int[] mdist;
	static int[] mdist2;
	static int SOURCE = -1;
	
	public static void main(String args[]) throws IOException {
		
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("grass.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		
		// init graph and tarjan's stuff
		id = new int[N]; // "discovery array" for labelling after initial DFS
		low = new int[N]; // low[i] = lowest id val of any node reachable from node i
		onStack = new boolean[N]; // if currently under consideration (visited but not put in a cc yet)
		sccMap = new int[N]; // sccMap[i] = scc of node i
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Integer>());
			id[i] = sccMap[i] = -1;
		}
		
		for (int i = 0; i < M; i++) { // scan everything in, adjust indexing
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; 
			int b = Integer.parseInt(st.nextToken()) - 1;
			adj.get(a).add(b);
		}
		br.close();
		
		// DFS and tarjans
		for (int i = 0; i < N; i++) { // loop through all nodes
			if (id[i] == -1) { // if unvisited, DFS
				dfs(i);
			}
		}
		
		// BUILD A NEW GRAPH OUT OF SCCS (now it will be a DAG)
		for (int i = 0; i < sccCount; i++) {
			adj2.add(new TreeSet<Integer>()); // init
			adj3.add(new ArrayList<Integer>()); // init reverse graph
		}
//		System.out.println(System.currentTimeMillis()-t);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < adj.get(i).size(); j++) { 
				// loop through adj, map to SCCs
				int src = sccMap[i];
				int dest = sccMap[adj.get(i).get(j)];
				
				if (!adj2.get(src).contains(dest) && src != dest) { // add to graph
					adj2.get(src).add(dest);
					adj3.get(dest).add(src);
				}
			}
		}
//		System.out.println(System.currentTimeMillis()-t);
		
		mdist = new int[sccCount]; // max "dist" from source to i
		mdist2 = new int[sccCount]; // max "dist" from i to source
		for (int i = 0; i < sccCount; i++) 
			mdist[i] = mdist2[i] = -1;
		
		mdist[SOURCE] = mdist2[SOURCE] = sccSize.get(SOURCE); // basecases
		
		int maxval = 0; // max fields visitable
		
		for (int i = 0; i < sccCount; i++) {
			for (int b : adj2.get(i)) { // loop through adj2, get edge from a to b
				int a = i;
				// simulate flip from b to a, so 0...b and a...0
				int d1 = maxDist(b);
				int d2 = maxDist2(a);

				if (d1 == -2 || d2 == -2) continue;
				int val = d1 + d2;

				maxval = Math.max(maxval, val-sccSize.get(SOURCE)); // don't doublecount source value
			}
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("grass.out")));

//		System.out.println(maxval);

		pw.println(maxval);
		pw.close();
		
	}
	public static int maxDist(int u) { // returns max dist from source to u
		if (mdist[u] != -1) return mdist[u]; // memoization
		mdist[u] = -2; // -2 means unreachable
		
		for (int i = 0; i < adj3.get(u).size(); i++) { // go backwards toward source
			int prev = adj3.get(u).get(i);
			
			int val = maxDist(prev);
			if (val == -2) continue; 
			// if prev is reachable, update current
			mdist[u] = Math.max(mdist[u], val+sccSize.get(u));
		}
		
		return mdist[u];
	}
	public static int maxDist2(int u) { // returns max dist from u to source
		if (mdist2[u] != -1) return mdist2[u]; // memoization
		mdist2[u] = -2; // -2 means unreachable
	
		for (int next : adj2.get(u)) { // go forward toward source
		
			int val = maxDist2(next);
			if (val == -2) continue;
			// if next is reachable, update current
			mdist2[u] = Math.max(mdist2[u], val+sccSize.get(u));
		}
		
		return mdist2[u];
	}
	public static void dfs(int u) { // node u
		id[u] = low[u] = ++curid; // mark id
		onStack[u] = true; // mark "opened" / "active"
		s.add(u);
		
		for (int i = 0; i < adj.get(u).size(); i++) { // neighbors
			int next = adj.get(u).get(i); 
			
			if (id[next] == -1) { // if not visited
				dfs(next);
			}
			if (onStack[next]) { 
				// if the child has been "opened" but not been put in a cc yet ("closed")
				low[u] = Math.min(low[u], low[next]);
			}
		}
		
		if (low[u] == id[u]) { // found a cc
			int num = 0;
			
			// empty cc components from the stack
			while (!s.isEmpty()) { 
				int x = s.pop();
				onStack[x] = false;
				low[x] = id[u]; // mark as part of the cc
				num++;
				sccMap[x] = sccCount;
				if (x == 0) { // source
					SOURCE = sccCount;
				}
				if (x == u) {
					break; // stop when you get back to the "head" of the cc
				}
			}
			sccSize.add(num);
			sccCount++;
		}
	}
}

