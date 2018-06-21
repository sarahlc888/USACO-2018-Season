import java.io.*;
import java.util.*;

/*
 * TLE
 * 
 * doesn't work ??? see version 2
 */

public class MSTPrims {
	static int N;
	static int[][] mat;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		N = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		mat = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			Arrays.fill(mat[i], Integer.MAX_VALUE);
		}
		for (int i = 0; i < E; i++) {
			// scan in the edges, put into the matrix
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken()) - 1; // v1
			int b = Integer.parseInt(st.nextToken()) - 1; // v2
			int c = Integer.parseInt(st.nextToken()); // weight
			mat[a][b] = c;
			mat[b][a] = c;
		}
		/*for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(mat[i]));
		}*/
		scan.close();
		
		TreeSet<Integer> s = new TreeSet<Integer>(); // MST
		TreeSet<Pair> v = new TreeSet<Pair>(); // non MST
		
		s.add(0);
		
		int edgeSum = 0;
		
		for (int i = 1; i < N; i++) { // initialize all pairs with infinity distance
			// to the closest point in S
			Pair p = new Pair(i, Integer.MAX_VALUE);
			if (mat[0][i] != Integer.MAX_VALUE) {
				// if there is a connection
				p.dist = mat[0][i];
			}
			v.add(p);
		}
		//System.out.println("here");
		
		while (!v.isEmpty()) {
			System.out.println(v);
			// move the vertex from v to s
			Pair p = v.first();
			s.add(p.id); 
			v.remove(p);
			//if (!v.remove(p)) {
				//System.out.println("fail");
			//}
			edgeSum += p.dist;
			
			// TODO: try iterating through mat[p.id] instead
			
			Iterator<Pair> it = v.iterator();
			while (it.hasNext()) { // loop through v
				// compare the dist val to any edge connecting to p
				Pair p2 = it.next();
				//System.out.println("here");
				if (mat[p.id][p2.id] < p2.dist) {
					p2.dist = mat[p.id][p2.id];
				}
				
			}
			
			
		}
		
		System.out.println(edgeSum);
	}
	public static class Pair implements Comparable<Pair> {
		int id;
		int dist; // smallest distance to a point in S
		public Pair(int i, int p) {
			id = i;
			dist = p;
		}
		@Override
		public int compareTo(Pair o) {
			if (dist < o.dist) {
				return -1;
			}
			if (dist > o.dist) {
				return 1;
			}
			return 0;
		}
		public String toString() {
			return "(" + id + ", " + dist + ")";
		}
	}
}
