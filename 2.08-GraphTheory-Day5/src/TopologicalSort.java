import java.io.*;
import java.util.*;
/*
 * topo sort, implemented 7/25
 */
public class TopologicalSort {
	static int N;
	static int[] indegrees;
	static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
	
	public static int[] topoSort() {
		int[] sorted = new int[N]; // final array
		int ind = 0;
		
		Queue<Integer> inDegZero = new LinkedList<Integer>(); // all nodes with indegrees 0
		for (int i = 0; i < N; i++) {
			if (indegrees[i] == 0) {
				inDegZero.add(i);
			}
		}
		
		while (!inDegZero.isEmpty()) {
			int cur = inDegZero.poll(); // a node with indeg 0
			sorted[ind] = cur;
			ind++;
			for (int i = 0; i < adj.get(cur).size(); i++) { // loop through edges from cur
				int dest = adj.get(cur).get(i); 
				indegrees[dest]--;
				if (indegrees[dest] == 0) { // if indeg = 0, add to queue
					inDegZero.add(dest);
				}
			}
		}
		return sorted;
	}
}
