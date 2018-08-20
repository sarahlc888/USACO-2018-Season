import java.io.*;
import java.util.*;
/*
 * finds sccs from a directed cyclic graph
 * not fully tested but theoretically works (used in 3.21 properly)
 * O(V+E) from DFSing
 */
public class Tarjans {
	static int N;
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
	static int[] id;
	static int curid;
	static int[] low;
	static boolean[] onStack;
	static Stack<Integer> s = new Stack<Integer>();
	static int sccCount = 0;
	static ArrayList<ArrayList<Integer>> sccs = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("scc.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		
		// init graph
		id = new int[N]; // "discovery array" for labelling after initial DFS
		low = new int[N]; // low[i] = lowest id val of any node reachable from node i
		onStack = new boolean[N]; // if currently under consideration (visited but not put in a cc yet)
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Integer>());
			id[i] = -1;
		}
		
		for (int i = 0; i < M; i++) { // scan everything in, adjust indexing
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; 
			int b = Integer.parseInt(st.nextToken()) - 1;
			adj.get(a).add(b);
		}
		br.close();
		
		System.out.println(adj);
		
		
		// DFS on whole graph
		for (int i = 0; i < N; i++) { // loop through all nodes
			if (id[i] == -1) { // if unvisited, DFS
//				System.out.println("DFS: " + i);
				dfs(i);
			}
		}
		
		System.out.println(Arrays.toString(id));
		System.out.println(sccCount);
		System.out.println(sccs);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("scc.out")));

		pw.println();
		pw.close();
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

