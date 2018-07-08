import java.io.*;
import java.util.*;

/*
 * adj matrix version
 * prims with adj matrix (proper version)
 * 10/10 test cases
 */

public class WTF5 {
	static boolean[] visited; // visited for the MST
	static int[][] mat;
	static int N;
	static int cost = 0;
	static int[] path;
	static boolean broke;
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num nodes
		int C = Integer.parseInt(st.nextToken()); // min cost
		
		mat = new int[N][N]; // matrix
		Pair[] fields = new Pair[N]; // locations of nodes
		
		for (int i = 0; i < N; i++) { // scan in the field locations
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			fields[i] = new Pair(a, b); 
		}
		for (int i = 0; i < N; i++) { // all fields
			for (int j = 0; j < N; j++) { // all fields
				mat[i][j] = cost(fields[i], fields[j]); // add edges
				if (mat[i][j] < C) mat[i][j] = Integer.MAX_VALUE; // edge doesn't exist if too small
			}
		}
		
		visited = new boolean[N];
		
		prim();
		if (broke) System.out.println(-1);
		else System.out.println(cost);
	}
	public static void prim() {
		TreeSet<Integer> s = new TreeSet<Integer>(); // MST
		TreeSet<Integer> v = new TreeSet<Integer>(); // non MST
		path = new int[N]; 
		Arrays.fill(path, Integer.MAX_VALUE); // initialize all pairs with infinity distance
		// path[i] stores smallest dist to a node in s when i is in v
		
		s.add(0);
		
		for (int i = 0; i < N; i++) {
			// loop through all nodes, add them to v
			//System.out.println();
			if (i != 0) v.add(i);
			
			path[i] = mat[0][i]; // update path to the distance
		}
		
		broke = false;
		while (!v.isEmpty()) {
			//System.out.println(v);
			
			// identify the vertex with the smallest path value
			int minPath = Integer.MAX_VALUE;
			int minID = 0;
			Iterator<Integer> it = v.iterator();
			while (it.hasNext()) { // loop through v
				int id = it.next();
				if (path[id] < minPath) { // if it's the smallest
					minPath = path[id];
					minID = id;
				}
			}
			
			if (minPath == Integer.MAX_VALUE) {
				// if there are no edges
				broke = true; // indicate
				break;
				
			}
			
			// move the vertex from v to s
			s.add(minID); 
			v.remove((Integer)minID);
			
			cost += path[minID];
			
			for (int i = 0; i < N; i++) {
				path[i] = Math.min(path[i], mat[minID][i]);
			}
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; // weight
		int y; // dest
		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			return x-o.x;
		}
	}
	public static int cost(Pair a, Pair b) { // distance formula squared
		return (a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y);
	}
}
