import java.io.*;
import java.util.*;

// method using loops
// not tested
// complexity O(n) = n^2 * e

public class TopoSortMethod3 {
	
	public static ArrayList<ArrayList<Integer>> edges;
	public static boolean[] visited;
	
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
		
		edges = new ArrayList<ArrayList<Integer>>(); // adjacency lists
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
		
		ArrayList<Integer> nodelist = new ArrayList<Integer>(); // list of all the nodes
		for (int i = 0; i < N; i++) {
			nodelist.add(nodes[i]);
		}
		
		while (!(nodelist.size() == 0)) { // loop through all nodes
			
			//System.out.println("nodelist size: " + nodelist.size());
			//System.out.println("edges size: " + edges.size());
			
			boolean[] notsource = new boolean[nodelist.size()];
			
			for (int i = 0; i < nodelist.size(); i++) { // go through the nodelist; loop 2
				// take out the current highest source node (no edges going into that node)
				
				int curnode = nodelist.get(i);
				
				// loop through all of the edges
				for (int j = 0; j < nodelist.size(); j++) {
					for (int m = 0; m < edges.get(j).size(); m++) {
						if (edges.get(j).get(m) == curnode) { // if the edge leads to curnode, mark true
							notsource[curnode] = true;
						}
					}
				}
				
				// if node i has no edges going into it, it is the top node. remove it from the list
				if (!notsource[i]) {
					edges.remove(edges.get(i)); // remove connections of the node from edges
					System.out.println(nodelist.get(i));
					nodelist.remove(i); // remove the node from the nodelist
					break;
				}
			}
			
			
			
		}
		
		
		
		visited = new boolean[N]; // whether or not the node has been visited
		
		
		
		
		
	}
	


}	
		