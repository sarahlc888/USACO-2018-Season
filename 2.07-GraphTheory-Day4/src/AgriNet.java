import java.io.*;
import java.util.*;

/*
 * 
 * works
 */

public class AgriNet {
	static int N;
	static int[][] mat;
	static int[] path;
	/*public static Comparator<Integer> aComparator = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (path[x] < path[y]) return -1;
			if (path[x] > path[y]) return 1;
			if (x < y) return -1;
			if (x > y) return 1;
			else return 0;
		}
	};*/
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		N = Integer.parseInt(scan.nextLine());
		
		mat = new int[N][N];
		
		// scan in adj matrix
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int j = 0;
			while (j < N) {
				if (!st.hasMoreTokens()) {
					// if st ran out, do a new one
					st = new StringTokenizer(scan.nextLine());
				}
				mat[i][j] = Integer.parseInt(st.nextToken());
				j++;
			}
			
			
		}
		scan.close();
		
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
		
	
		
		int edgeSum = 0; // sum of edges in MST

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
			
			// move the vertex from v to s
			s.add(minID); 
			v.remove((Integer)minID);
			
			edgeSum += path[minID];
			
			for (int i = 0; i < N; i++) {
				path[i] = Math.min(path[i], mat[minID][i]);
			}
		}
		
		System.out.println(edgeSum);
	}
	
	
}
