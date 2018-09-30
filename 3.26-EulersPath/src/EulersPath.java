import java.io.*;
import java.util.*;

/*
 * from 9/11/18 lesson
 * inefficient way, finds all possible paths until it gets the eulerian path
 */
public class EulersPath {
	static ArrayList<ArrayList<Pair>> adj;
	static int V;
	static int E;
	static ArrayList<Integer> epath = new ArrayList<Integer>();
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("ep.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList<ArrayList<Pair>>();
		int[] degrees = new int[V]; // degrees for each node
		
		for (int i = 0; i < V; i++) 
			 adj.add(new ArrayList<Pair>());
		
		for (int i = 0; i < E; i++) { // scan the edges
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // NO INDEX ADJUSTMENT
			int b = Integer.parseInt(st.nextToken());
			adj.get(a).add(new Pair(b, i)); // add to adj list
			adj.get(b).add(new Pair(a, i));
			degrees[a]++; // increment degrees
			degrees[b]++;
		}
		br.close();
		
		// holds indices of vertices with odd degrees
		ArrayList<Integer> odds = new ArrayList<Integer>(); 
		for (int i = 0; i < V; i++) {
			if (degrees[i]%2 == 1) odds.add(i);
		}
		
		if (odds.size() != 2 && odds.size() != 0) {
			// path only exists if odds == 2 or odds == 0
			System.out.println("NO EULER'S PATH");
			return;
		} 
		int source;
		int dest;
		if (odds.size() == 2) { // path from odd to odd
			source = odds.get(0);
			dest = odds.get(1);
		} else { // closed cycle path, start anywhere, end at the same place
			source = 0;
			dest = 0;
		}
		System.out.println("source: " + source + " dest: " + dest);
		
		dfs(source, new ArrayList<Integer>(), dest, new int[E]);
		
		System.out.println(epath);

		
		
	}
	public static void dfs(int u, ArrayList<Integer> path, int dest, int[] visited) { // dfs from node u
		
		path.add(u);
//		System.out.println("u: " + u + " path: " + path);
//		System.out.println("  visited: " + Arrays.toString(visited));
		if (u == dest && path.size() == E+1) {
//			System.out.println("HERE");
			// finished the path
			epath = path;
			return;
		}
		
		for (int i = adj.get(u).size()-1; i >= 0; i--) { // neighbors
			int next = adj.get(u).get(i).dest; 
//			System.out.println("node: " + u + "  next: " + next);
			if (visited[adj.get(u).get(i).id] == 0) { // if not visited
//				System.out.println("    go");
				
				visited[adj.get(u).get(i).id] = 1;
				int[] visited2 = visited.clone();

				dfs(next, (ArrayList<Integer>) path.clone(), dest, visited2);
				visited[adj.get(u).get(i).id] = 0; // unmark
				
			}
		}
//		s.push(u); // push onto the stack after dfs finishes ("highest" nodes on top)
	}
	public static class Pair {
		int dest;
		int id;

		public Pair(int a, int b) {
			dest = a;
			id = b;
		}
	}
}
