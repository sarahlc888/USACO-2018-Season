import java.io.*;
import java.util.*;
/*
 * uses Tarjans from 3.20
 * 
 * had to look at solution but got pretty close on my own
 * create a DAG by making graph out of all the strongly connected components
 * loop through all edges, b-->a, find longest path from 1 to a and longest path from b to 1
 * compute length
 * 
 * 13/14 test cases, test case 9 times out
 */
public class GrassCownoisseur {
	static int N;
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<Integer>> adj2 = new ArrayList<ArrayList<Integer>>(); // for scc graph
	static ArrayList<ArrayList<Integer>> adj3 = new ArrayList<ArrayList<Integer>>(); // reverse scc graph
	static int[] id;
	static int curid;
	static int[] low;
	static boolean[] onStack;
	static Stack<Integer> s = new Stack<Integer>();
	static int sccCount = 0;
	static ArrayList<ArrayList<Integer>> sccs = new ArrayList<ArrayList<Integer>>();
	static int[] sccSize;
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
		
		// init graph
		id = new int[N]; // "discovery array" for labelling after initial DFS
		low = new int[N]; // low[i] = lowest id val of any node reachable from node i
		onStack = new boolean[N]; // if currently under consideration (visited but not put in a cc yet)
		sccMap = new int[N]; // which scc node i belongs to
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
//				System.out.println("DFS: " + i);
				dfs(i);
			}
		}
		
//		System.out.println(adj);
//		System.out.println(Arrays.toString(id));
//		System.out.println(sccCount);
//		System.out.println("sccs! " + sccs);
//		System.out.println(Arrays.toString(sccMap));

		// BUILD A NEW GRAPH OUT OF SCCS (now it will be a DAG)
		sccSize = new int[sccCount];
		
		
		for (int i = 0; i < sccCount; i++) {
			adj2.add(new ArrayList<Integer>()); // init
			adj3.add(new ArrayList<Integer>()); // init
			ArrayList<Integer> cur = sccs.get(i);
			sccSize[i] = cur.size();
			
			if (SOURCE != -1) continue;
			for (int j = 0; j < cur.size(); j++) {
				if (cur.get(j) == 0) {
					SOURCE = i; // assign "source"
					break;
				}
			}
			
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < adj.get(i).size(); j++) { // loop through adj
				int src = sccMap[i];
				int dest = sccMap[adj.get(i).get(j)];
				if (!adj2.get(src).contains(dest) && src != dest) {
					adj2.get(src).add(dest);
					adj3.get(dest).add(src);
				}
			}
		}

//		System.out.println(adj2);
//		System.out.println(adj3);
		
		
//		System.out.println("SOURCE: " + SOURCE);
//		System.out.println(sccSize[SOURCE]);
		
		mdist = new int[sccCount]; // max dist from source to i
		mdist2 = new int[sccCount]; // max dist from i to source
		for (int i = 0; i < sccCount; i++) {
			mdist[i] = mdist2[i] = -1;
		}
		mdist[SOURCE] = mdist2[SOURCE] = sccSize[SOURCE]; // basecases
		
//		System.out.println();
//		
//		System.out.println(maxDist(4));
//		System.out.println(maxDist2(4));
//		
//		System.out.println();

		int maxval = 0;
		
		for (int i = 0; i < sccCount; i++) {
			for (int j = 0; j < adj2.get(i).size(); j++) { // loop through adj2, get edge from a to b
				int a = i;
				int b = adj2.get(i).get(j);
				// simulate flip from b to a, so 0...b and a...0
				int d1 = maxDist(b);
				int d2 = maxDist2(a);
//				System.out.println("a: " + a + " b: " + b + " d1: " + d1 + " d2: " + d2);
				if (d1 == -2 || d2 == -2) continue;
				int val = d1 + d2;
//				System.out.println("  val: " + val);
//				maxval = Math.max(maxval, val-1);
				maxval = Math.max(maxval, val-sccSize[SOURCE]); // doublecount source
			}
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("grass.out")));

//		if (sccSize[SOURCE] > 1) {
//			maxval -= sccSize[SOURCE];
//			maxval++;
//
//		}
		System.out.println(maxval);

		pw.println(maxval);
		pw.close();
	}
	public static int maxDist(int u) { // returns max dist from source to u
		if (mdist[u] != -1) return mdist[u]; // memoization
		mdist[u] = -2; // -2 means unreachable
		
//		System.out.println("u: " + u);
		
		for (int i = 0; i < adj3.get(u).size(); i++) {
			int prev = adj3.get(u).get(i);
//			System.out.println("u: " + u + " prev: " + prev);
			int val = maxDist(prev);
			if (val == -2) continue;
			mdist[u] = Math.max(mdist[u], val+sccs.get(u).size());
//			System.out.println("  mdist: " + mdist[u]);
		}
		
		return mdist[u];
	}
	public static int maxDist2(int u) { // returns max dist from u to source
		if (mdist2[u] != -1) return mdist2[u]; // memoization
		mdist2[u] = -2; // -2 means unreachable
		
//		System.out.println("u: " + u);

		for (int i = 0; i < adj2.get(u).size(); i++) {
			int next = adj2.get(u).get(i);
//			System.out.println("u: " + u + " next: " + next);
//			System.out.println("  mdist2: " + mdist2[u]);
			
			int val = maxDist2(next);
			if (val == -2) { // unreachable
				continue;
			}
			mdist2[u] = Math.max(mdist2[u], val+sccs.get(u).size());
			
//			System.out.println("  mdist2: " + mdist2[u]);
		}
		
		return mdist2[u];
	}
	public static void dfs(int u) { // node u
		id[u] = low[u] = ++curid; // mark id
		onStack[u] = true; // mark "opened"
		s.add(u);
		
//		System.out.println("  u: " + u + " id: " + id[u] + " low: " + low[u]);
		
		for (int i = 0; i < adj.get(u).size(); i++) { // neighbors
			int next = adj.get(u).get(i); 
//			System.out.println("    next: " + next);
			
			if (id[next] == -1) { // if not visited
				dfs(next);
			}
			if (onStack[next]) { 
//				System.out.println("    next: " + next + " lowval: " + low[next]);
				// if the child has been "opened" but not been put in a cc yet ("closed")
				low[u] = Math.min(low[u], low[next]);
			}
		}
		
		if (low[u] == id[u]) { // found a cc
			sccs.add(new ArrayList<Integer>());
//			System.out.print("    SCC u: " + u + " low: " + low[u] + " stack: ");
//			System.out.println(s);
			// empty cc components from the stack
			while (!s.isEmpty()) { 
				int x = s.pop();
//				System.out.print(" " + x);
				onStack[x] = false;
				low[x] = id[u]; // mark as part of the cc
				sccs.get(sccCount).add(x);
				sccMap[x] = sccCount;
				if (x == u) {
//					System.out.println("  BREAK");
					break; // stop when you get back to the "head" of the cc
				}
			}
//			System.out.println();
			sccCount++;
		}
	}
}

