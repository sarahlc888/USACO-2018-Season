import java.io.*;
import java.util.*;

/*
 * from 9/11/18 lesson
 * uses Fleury's algo to find paths
 * 
 * minimal testing
 */

public class Fleurys {
	static ArrayList<ArrayList<Integer>> adj; 
	static int V;
	static int E;
	static ArrayList<Integer> epath = new ArrayList<Integer>();
	static boolean[] visited; // for dfs and checking bridges
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("ep.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList<ArrayList<Integer>>();
		int[] degrees = new int[V]; // degrees for each node
		
		for (int i = 0; i < V; i++) 
			 adj.add(new ArrayList<Integer>());
		
		
		for (int i = 0; i < E; i++) { // scan the edges
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // NO INDEX ADJUSTMENT
			int b = Integer.parseInt(st.nextToken());
			adj.get(a).add(b); // add to adj list
			adj.get(b).add(a);
			
			
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
//		System.out.println("source: " + source + " dest: " + dest);
				
		
		findPath(source);
		
		System.out.println(epath);

	}
	public static boolean isBridge(int u, int v) {
		// checks if edge between u and v is a bridge
		
				
		// count how many vertices you can reach from u
		visited = new boolean[E];
		int c1 = dfsCount(u);

		adj.get(u).remove((Object)v); // delete the edge
		adj.get(v).remove((Object)u);
		
		visited = new boolean[E];
		int c2 = dfsCount(u); // recount
		
		adj.get(u).add(v); // put back
		adj.get(v).add(u);

		if (c2 < c1) return true; // if you cut off some edges
		return false;
	}
	public static void findPath(int cur) { // get euler path
//		System.out.println("PATH: " + cur);
		epath.add(cur);
		for (int i = 0; i < adj.get(cur).size(); i++) {
			int next = adj.get(cur).get(i); // next vertex
			
			// check if cur-next is a good edge to take
			if (adj.get(cur).size() == 1 || !isBridge(cur, next)) {
				// if it's the only edge or it's not a bridge, take it
				
				// remove the edge
				adj.get(cur).remove((Object)next);
				adj.get(next).remove((Object)cur);
				
				findPath(next);
			}
		}
	}
	public static int dfsCount(int u) { // dfs from node u
		visited[u] = true;
		int count = 1; // how many vertices reachable from u
		for (int i = adj.get(u).size()-1; i >= 0; i--) { // neighbors
			int next = adj.get(u).get(i);  
			if (!visited[next]) { // if not visited, visit it
				visited[next] = true; 
				count += dfsCount(next);
			}
		}
		return count;
	}
}
