import java.io.*;
import java.util.*;


/*
 * 10/10 test cases
 * 
 * look through all the nodes O(N)
 * look for a node that has forward and back connections
 * 
 * look through all pairs of nodes O(N^2)
 * look for parallels
 * 
 * each thing gets rid of 1 edge
 * go at most M times until you only have 1 edge left
 * 
 * 
 * debug
 */


public class TotalFlow {
	static int V = 52; // number of nodes
	static int[][] mat = new int[V][V]; // stores edge weights
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		int M = Integer.parseInt(scan.nextLine()); // number of edges
		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				mat[i][j] = -1; // initialize non-edges as -1
			}
		}
		
		for (int i = 0; i < M; i++) { // scan in edges
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			
			String a = st.nextToken();
			char a1 = a.charAt(0);
			
			int v1;
			
			if (a1 > 90) { // lowercase
				v1 = a1-71;
			} else {
				v1 = a1-65;
			}
			
			String b = st.nextToken();
			char b1 = b.charAt(0);
			
			int v2;
			
			if (b1 > 90) { // lowercase
				v2 = b1 - 71;
			} else {
				v2 = b1 - 65;
			}
			
			////System.out.println(v1 + " " + v2);
			
			int flow = Integer.parseInt(st.nextToken());
			
			// graph them
			if (mat[v1][v2] == -1) {
				// if there's no edge yet
				mat[v1][v2] = flow;
				mat[v2][v1] = flow;
			} else {
				// add the flows together
				mat[v1][v2] += flow;
				mat[v2][v1] += flow;
			}
			
		}
		
		// count the edges left
		int edgeCount = 0;
		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				if (mat[i][j] != -1) 
					edgeCount++;
			}
		}
		
		// while there are still edges left
		while (edgeCount > 2) {
			//System.out.println("edgeCount: " + edgeCount);
			/*
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					// TODO: loop through all pairs of nodes to check for parallels
				}
			}
			*/
			
			for (int i = 0; i < V; i++) {
				
				// loop through all the nodes to look for connections
				// if it has two connections and ONLY two edges, merge
				// if it has only 1 connection, drop it
				
				int edges = 0;
				int n1 = -1;
				int n2 = -1;
				
				for (int j = 0; j < V; j++) {
					if (mat[i][j] != -1) { // connection, increase count and track it
						edges++;
						// store
						if (n1 == -1) n1 = j;
						else n2 = j;
					}
				}
				if (edges == 0) continue;
				//System.out.println("  i: " + i);
				//System.out.println("    edges: " + edges);
				// if a thing has only 1 connection, it means that it's a stump
				// get rid of it
				// make sure it's not the start to the end (A to Z)
				//if (edges == 1 && i != 0 && n1 != 0 && i != 25 && n1 != 25) {
				if (edges == 1 && !(i == 0 && n1 == 25 || i == 25 && n1 == 0))  {
					
					if (i != 0 && i != 25) { // if i is not the start or end point
						//System.out.println("    remove");
						mat[i][n1] = -1;
						mat[n1][i] = -1;
					} else {
						// if i is 0 or 25, check if the thing it's connected to works
						
						int edgesHere = 0; // count the edges connected to n1
						
						for (int k = 0; k < V; k++) {
							if (mat[n1][k] != -1) edgesHere++;
							
						}
						// TODO: optimize
						if (edgesHere == 1) { // if there's only 1 edge
							////System.out.println("    remove");
							mat[i][n1] = -1;
							mat[n1][i] = -1;
						}
						
					}
					
					
					
				}
				
				if (edges == 2 && i != 0 && i != 25) { 
					// if there are 2 connections only AND the middle one isn't 0 or 25
					//System.out.println("    connect " + n1 + " " + n2);
					// n1 and n2 should be connected, get rid of all connections to i
					
					// if n1 and n2 are not already connected, connect them
					if (mat[n1][n2] == -1) {
						mat[n1][n2] = Math.min(mat[i][n1], mat[i][n2]);
						mat[n2][n1] = Math.min(mat[i][n1], mat[i][n2]);
					} else {
						// there's parallel pipes, so add them
						mat[n1][n2] += Math.min(mat[i][n1], mat[i][n2]);
						mat[n2][n1] += Math.min(mat[i][n1], mat[i][n2]);
					}
					
					// delete all connections to i
					mat[i][n1] = -1;
					mat[i][n2] = -1;
					mat[n1][i] = -1;
					mat[n2][i] = -1;
				}
			}
			
			// recount the edges as the exit condition
			edgeCount = 0;
			for (int i = 0; i < V; i++) {
				
				for (int j = 0; j < V; j++) {
					if (mat[i][j] != -1) {
						edgeCount++;
						////System.out.println("  " + i + " " + j);
						////System.out.println("  m: " + mat[i][j]);
						
					}
				}
			}
			////System.out.println("edges: " + edgeCount);
		}
		// print that final edge
		/*
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				if (mat[i][j] != -1) {
					////System.out.println(i + " " + j);
					////System.out.println("m: " + mat[i][j]);
				}
			}
		}*/
		// what it should be
		System.out.println(mat[0][25]);
		
		scan.close();
	}
}
