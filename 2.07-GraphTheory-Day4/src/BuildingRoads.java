import java.io.*;
import java.util.*;

/*
 * graph with distance formula distances
 * 
 * construct only an MST
 * 
 * Clever idea
 * 
 * mat[i][j] = dist if there's no road
 * 			 = 0 if there is a road already (no extra cost)
 */
public class BuildingRoads {
	static double[][] mat;
	static int[][] nodes;
	static double[] path;
	static int N;
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		N = Integer.parseInt(st.nextToken()); // number of farms
		int M = Integer.parseInt(st.nextToken()); // number of roads
		
		//System.out.println(N + " " + M);
		
		mat = new double[N][N]; // distances array, inf for n/a
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				mat[i][j] = Double.MAX_VALUE;
			}
		}
		nodes = new int[N][2]; // track the farms
		
		for (int i = 0; i < N; i++) { // scan in the farms
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken()); 
			int b = Integer.parseInt(st.nextToken()); 
			nodes[i][0] = a;
			nodes[i][1] = b;
			//System.out.println(a + " " + b);
		}
		for (int i = 0; i < M; i++) { // scan in the roads
			// a road already connects a to b
			st = new StringTokenizer(scan.nextLine());
			// adjust for indexing
			int a = Integer.parseInt(st.nextToken())-1; 
			int b = Integer.parseInt(st.nextToken())-1; 
			// if there's a road, mark it as no cost
			mat[a][b] = 0;
			mat[b][a] = 0;
			//System.out.println(a + " " + b);
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (mat[i][j] == Double.MAX_VALUE) {
					// calculate the actual distance and make it the weight
					mat[i][j] = dist(i, j);
				}
			}
		}
		
		
		
		TreeSet<Integer> s = new TreeSet<Integer>(); // MST
		TreeSet<Integer> v = new TreeSet<Integer>(); // non MST
		path = new double[N]; 
		Arrays.fill(path, Integer.MAX_VALUE); // initialize all pairs with infinity distance
		// path[i] stores smallest dist to a node in s when i is in v
		
		s.add(0);
		
		for (int i = 0; i < N; i++) {
			// loop through all nodes, add them to v
			//System.out.println();
			if (i != 0) v.add(i);
			
			path[i] = mat[0][i]; // update path to the distance
		}
		
	
		
		double edgeSum = 0; // sum of edges in MST

		while (!v.isEmpty()) {
			//System.out.println(v);
			
			// identify the vertex with the smallest path value
			double minPath = Integer.MAX_VALUE;
			int minID = 0;
			Iterator<Integer> it = v.iterator();
			while (it.hasNext()) { // loop through v
				int id = it.next();
				if (path[id] < minPath) { // if it's the smallest
					minPath = path[id];
					minID = id;
				}
			}
			
			// move the vertex from v to s
			s.add(minID); 
			v.remove((Integer)minID);
			
			edgeSum += path[minID];
			
			for (int i = 0; i < N; i++) {
				path[i] = Math.min(path[i], mat[minID][i]);
			}
		}
		
		//System.out.println(edgeSum);
		System.out.printf("%.2f", edgeSum);
	}
	public static double dist(int i, int j) {
		// distance between pasture i and j
		return Math.sqrt( Math.pow(nodes[i][0] - nodes[j][0], 2) + 
				Math.pow(nodes[i][1] - nodes[j][1], 2));
	}

}
