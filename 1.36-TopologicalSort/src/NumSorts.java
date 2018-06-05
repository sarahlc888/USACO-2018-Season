import java.io.*;
import java.util.*;

// calculates number of different sorts that are possible
// method using in-degrees (basically BFS)
// O(N*E)

public class NumSorts {
	
	public static int[] indegrees;
	
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <=1; i++) {
			String filename = "test" + i + ".in";
			processOneFile(filename, i);
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		int N = Integer.parseInt(br.readLine()); // number of nodes
		
		int[] nodes = new int[N]; // array of nodes
		for (int i = 0; i < N; i++) {
			nodes[i] = i;
		}
		
		int[][] adjMatrix = new int[N][N]; // adjacency matrix
		
		ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>(); // adjacency lists
		for (int i = 0; i < N; i++) {
			edges.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				adjMatrix[i][j] = Integer.parseInt(st.nextToken()); // fill in matrix
				if (adjMatrix[i][j] > 0) edges.get(i).add(j); // add an edge from i to j
			}
		}
		br.close();
		
		indegrees = new int[N]; // indegrees of all nodes 0...N-1
		
		// calculate indegrees
		for (int i = 0; i < N; i++) { // for every node
			for (int j = 0; j < edges.get(i).size(); j++) { // for every neighbor
				
				indegrees[edges.get(i).get(j)]++; // increment indegrees of the neighbor
				
			}
		}
	
		LinkedList<Integer> tovisit = new LinkedList<Integer>(); // list to visit, all have indegrees = 0
		
		
		int numSorts = 0; // number of different possible sorts
	
		
		for (int i = 0; i < N; i++) { // initial loop to add all nodes with indegrees = 0 to to visit
			//System.out.println("ind: " + indegrees[i]);
			if (indegrees[i] == 0) { 
				tovisit.add(i);
				numSorts++; // initial number of zeroes
			}
		}
		
		// note: no need for a visited boolean array because 0 --> -1, won't get added repeatedly
		
		PrintWriter pw = new PrintWriter((new File("test" + k + ".out")));

		
		while (!tovisit.isEmpty()) {
			
			// count how many zeroes are at the head of the queue
			int numZs = 0;
			while (tovisit.get(numZs) == 0 && numZs > tovisit.size()) {
				numZs++;
			}
			
			
			
			int curnode = tovisit.removeFirst(); // get a node
			System.out.println(curnode); // print out the node (to put it into the sorted output)
			pw.println(curnode);
			
			
			for (int i = 0; i < edges.get(curnode).size(); i++) { // loop through all the neighbors
				int neigh = edges.get(curnode).get(i);
				
				indegrees[neigh]--; // decrease indegrees for all of them by 1
				
				if (indegrees[neigh] == 0) { // indegree == 0, add to tovisit
					tovisit.add(neigh);
				}
			}
			
			numSorts *= fact(numZs); // count perms
			
		}
		
		System.out.println("numSorts: " + numSorts);
		
		pw.close();
	}
	public static int fact(int n) { // factorial
		int prod = 1;
		for (int i = n; i > 1; i++) {
			prod *= i;
		}
		return prod;
	}
}	
		