import java.io.*;
import java.util.*;

// method using recursion and stack (basically DFS)
// if you need to print out as you calculate, flip dir of all edges and use same alg w/o stack
// not tested

public class TopoSortMethod2 {
	
	public static Deque<Integer> st;
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
		
		st = new LinkedList<Integer>(); 
		visited = new boolean[N]; // whether or not the node has been visited
		
		for (int i = 0; i < N; i++) {
			if (!visited[i]) { // if the node has not been visited, visit it
				visited[i] = true; 
				toposort(nodes[i]);
			}
		}
		for (int i = 0; i < N; i++) { // print out in reverse order for the properly sorted elements
			System.out.println(st.pop());
		}
		
		
	}
	public static void toposort(int a) { // argument = starting node
		for (int i = 0; i < edges.get(a).size(); i++) { // get all the neighbors
			int curnode = edges.get(a).get(i);
			if (!visited[curnode]) { // if not visited, visit it
				visited[curnode] =  true;
				toposort(curnode);
			}
		}
		st.push(a); 
		// push the source node onto the stack 
		// everything below it has already been pushed recursively
		
	}


}	
		