import java.io.*;
import java.util.*;
/*
 * kosaraju's 2 pass algo to find sccs in 2 dfs
 * not fully tested, should be working, used in 3.21 for full points
 */
public class Kosarajus {
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(); // graph
	static ArrayList<ArrayList<Integer>> adj2 = new ArrayList<ArrayList<Integer>>(); // transpose graph
	static boolean[] visited;
	static Stack<Integer> s = new Stack<Integer>();
	static int sccCount = 0;
	static ArrayList<ArrayList<Integer>> sccs = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("scc.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		
		// init graph 
		visited = new boolean[N];
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Integer>());
			adj2.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < M; i++) { // scan everything in, adjust indexing
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; 
			int b = Integer.parseInt(st.nextToken()) - 1;
			adj.get(a).add(b);
			adj2.get(b).add(a);
		}
		br.close();
		
		System.out.println(adj);
		System.out.println(adj2);
		
		for (int i = 0; i < N; i++) { // dfs to set up the stack
			if (!visited[i]) {
				dfs(i);
			}
		}
		
		visited = new boolean[N]; // reset for dfs round 2
		
		System.out.println(s);
		
		while (!s.isEmpty()) { // pop off the stack
			int next = s.pop();
			System.out.println("next: " + next);

			if (!visited[next]) {
				sccs.add(new ArrayList<Integer>());
				dfs2(next);
				sccCount++;
			}
		}
		
		System.out.println(sccCount);
		System.out.println(sccs);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("scc.out")));

		pw.println();
		pw.close();
	}
	public static void dfs(int u) { // dfs from node u
		visited[u] = true;
		for (int i = 0; i < adj.get(u).size(); i++) { // neighbors
			int next = adj.get(u).get(i); 
			
			if (!visited[next]) { // if not visited
				dfs(next);
				visited[next] = true;
			}
		}
		s.push(u); // push onto the stack after dfs finishes ("highest" nodes on top)
		
	}
	public static void dfs2(int u) { // dfs from node u
		visited[u] = true;
		
		System.out.println("  u: " + u);
		
		for (int i = 0; i < adj2.get(u).size(); i++) { // neighbors
			int next = adj2.get(u).get(i); 
			if (!visited[next]) { // if not visited
				System.out.println("    next: " + next);

				dfs2(next);
				visited[next] = true;
			}
		}
		sccs.get(sccCount).add(u); // add to scc
		
		
	}
}
