import java.io.*;
import java.util.*;

/*
 * from 9/11/18 lesson
 * uses algo to find paths
 * See: https://algs4.cs.princeton.edu/41graph/EulerianPath.java.html
 */

public class Sedgewick {
	static ArrayList<ArrayList<Integer>> adj; 
	static int V;
	static int E;
	static Stack<Integer> epath = new Stack<Integer>();
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
			source = odds.get(1);
			dest = odds.get(0);
		} else { // closed cycle path, start anywhere, end at the same place
			source = 0;
			dest = 0;
		}
		
		// go through the graph
		Stack<Integer> s = new Stack<Integer>();
		s.push(source);
		while (!s.isEmpty()) {
			int cur = s.pop(); // trace a path as far down as possible
			System.out.println("cur: " + cur);
			while (!adj.get(cur).isEmpty()) {
				System.out.println("  cur: " + cur);
				int next = adj.get(cur).get(0); // get next
				// remove the edge now
				adj.get(cur).remove(0);
				adj.get(next).remove((Object)cur);
				
				s.push(cur); // keep it on the stack
				cur = next; // keep tracing down
			}
			// when there are no lower nodes, push on cur
			System.out.println("  push: " + cur);
			epath.push(cur);
		}
		
		
		System.out.println(epath);
	}
}
