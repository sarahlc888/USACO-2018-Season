import java.io.*;
import java.util.*;

// calculate the shortest distance from node 0 to all other nodes (unweighted)

public class shortestDist {
	
	static AdjList[] adjs;
	static int N;
	
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <=5; i++) {
			String filename = "testData/adj" + i + ".in";
			processOneFile(filename, i);
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		N = Integer.parseInt(br.readLine()); // number of nodes
		
		adjs = new AdjList[N]; // array of lists of neighbors for node 0...N-1
		for (int i = 0; i < N; i++) {
			adjs[i] = new AdjList();
		}
		
		for (int i = 0; i < N; i++) { // node i
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) { // to node j
				if (Integer.parseInt(st.nextToken()) == 1) { // if there is an edge
					// add to adjacency lists
					adjs[i].adj.add(j);
					adjs[j].adj.add(i);
				}
			}
		}
		
		br.close();
	
		int[] steps = sDist(0); // find distance from node 0 to all other fields
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(k + ".out")));
		for (int i = 0; i < steps.length; i++) {
			pw.println(steps[i]);
		}
		
		pw.close();
	}
	public static class AdjList {
		ArrayList<Integer> adj;
		public AdjList() {
			adj = new ArrayList<Integer>();
		}
	}
	public static int[] sDist(int A) {
		// returns shortest distance between field A to any other field
		
		int[] steps = new int[N]; // number of steps from field A to field i
		LinkedList<Integer> LL = new LinkedList<Integer>(); // to visit
		boolean[] hasBeenVisited = new boolean[N]; // hbv boolean array
		
		LL.add(A);
		hasBeenVisited[A] = true;
		
		while (!LL.isEmpty()) {
			int cur = LL.removeFirst(); // get a field out
			
			for (int nb : adjs[cur].adj) { // get all neighbors of cur
				if (!hasBeenVisited[nb]) {
					// if the neighbor has not been visited, then process it
					hasBeenVisited[nb] = true; // mark it
					steps[nb] = steps[cur] + 1; // calculate steps to reach cur
					
					LL.add(nb); // add to LL for processing
				}
			}
		}
		
		return steps;
		
	}

}
