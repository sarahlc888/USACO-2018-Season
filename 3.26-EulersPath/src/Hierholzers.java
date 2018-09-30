import java.io.*;
import java.util.*;

/*
 * from 9/11/18 lesson
 * uses Hierholzer's algo to find circuits !!!
 * 
 * minimal testing
 */

public class Hierholzers {
	static ArrayList<ArrayList<Integer>> adj;
	static int[] adjCount;
	static int V;
	static int E;
	static ArrayList<Integer> epath = new ArrayList<Integer>();
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("ep.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList<ArrayList<Integer>>();
		adjCount = new int[V]; // num edges left unused
		int[] degrees = new int[V]; // degrees for each node
		
		for (int i = 0; i < V; i++) 
			 adj.add(new ArrayList<Integer>());
		
		for (int i = 0; i < E; i++) { // scan the edges
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // NO INDEX ADJUSTMENT
			int b = Integer.parseInt(st.nextToken());
			adj.get(a).add(b); // add to adj list
			adj.get(b).add(a);
			adjCount[a]++;
			adjCount[b]++;
			
			degrees[a]++; // increment degrees
			degrees[b]++;
		}
		br.close();
		
		// holds indices of vertices with odd degrees
		ArrayList<Integer> odds = new ArrayList<Integer>(); 
		for (int i = 0; i < V; i++) {
			if (degrees[i]%2 == 1) odds.add(i);
		}
		
		if (odds.size() != 0) {
			// path only exists if odds == 2 or odds == 0
			System.out.println("NO EULER'S CIRCUIT (maybe a path, but no cycle)");
			return;
		} 
		// closed cycle path, start anywhere, end at the same place
		epath = getPath();
				
		System.out.println(epath);
		
	}
	public static ArrayList<Integer> getPath() {
		int source = 0;
		int dest = 0;
		
		ArrayList<Integer> circ = new ArrayList<Integer>();
		Stack<Integer> path = new Stack<Integer>(); 
		path.push(source); // push on the source
		
		while (!path.isEmpty()) { // while path isn't empty
			int cur = path.peek(); // prev vertex, top of the stack, last on the path
//			System.out.println("cur: " + cur);
			if (adjCount[cur] > 0) { // if possible, take an edge (remove it)
				int next = adj.get(cur).get(0); 
				adj.get(cur).remove(0); 
				adj.get(next).remove((Object)cur);
				adjCount[cur]--;
				adjCount[next]--;
				path.push(next); // add to the path
			} else { // no edges off the cur vertex
				// backtrack to a point where there are other edges
				
				// there are no new edges possible behind this point, so add those to the permanent circuit
				circ.add(0, cur);
				
				path.pop(); // move back
				
			}
		}
		return circ;
	}
}
