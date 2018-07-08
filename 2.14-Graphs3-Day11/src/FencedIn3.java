import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 9/10, then times out
 * build a graph between all the intersections
 * get the maximum spanning tree, must include the outer rim
 * 
 * O(NM) edges
 * 
 * made sure the outside rim is in the MST by making their edge weights very very cheap
 */
public class FencedIn3 {
	static boolean[][] visited; // visited for the MST
	static int cost = 0;

	// for getting neighbors
	static int[] dx = {0, 0, -1, 1}; 
	static int[] dy = {-1, 1, 0, 0};

	// v and h, aka x and y
	static int[] v;
	static int[] h;

	static int n;
	static int m;
	static int A;
	static int B;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		A = Integer.parseInt(st.nextToken()); // corner x
		B = Integer.parseInt(st.nextToken()); // corner y
		n = Integer.parseInt(st.nextToken()); // num vertical fences
		m = Integer.parseInt(st.nextToken()); // num horiz fences

		long t = System.currentTimeMillis();
		
		v = new int[n+2]; // x vals of fences
		h = new int[m+2]; // y vals of fences

		// put in bounds
		v[0] = 0;
		h[0] = 0;
		v[n+1] = A;
		h[m+1] = B;

		for (int i = 1; i <= n; i++) 
			v[i] = Integer.parseInt(br.readLine());
		
		for (int i = 1; i <= m; i++) 
			h[i] = Integer.parseInt(br.readLine());
		
		// sort
		Arrays.sort(v);
		Arrays.sort(h);
		
		// correct for indexing now that you added 1 at start and 1 at end
		n+=2;
		m+=2;

		visited = new boolean[n][m]; // for the prims construction

		//System.out.println("time b4 prims: " + (System.currentTimeMillis()-t));
		
		prim();
		int total = (n-2)*B + (m-2)*A; // sum of all edges except the outer border
		//System.out.println("tot: " + total);
		//System.out.println("cost: " + cost);
		System.out.println(total-cost);
		
		System.out.println("time: " + (System.currentTimeMillis()-t));
	}
	public static void prim() {

		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();

		// add the edges that cross from S to VS

		pq.add(new Pair(0, new Point(0, 0, 0, 0))); // weight, destination, starting point
		
		while (!pq.isEmpty()) {
			// let (weight, node) be the thing at top
			Pair p1 = pq.poll();
			int weight = p1.x;
			Point curPoint = p1.p;
			int vind = curPoint.a;
			int hind = curPoint.b;
		
			// check if node is visited
			if (visited[vind][hind]) {
				continue; // if already visited, don't add it
			} else {
				visited[vind][hind] = true; // mark visited and proceed
			}
			
			//System.out.println(weight + " " + vind + " " + hind);
			
			//System.out.println("vind: " + vind + " hind: " + hind);
			//System.out.println("x: " + curPoint.x + " y: " + curPoint.y);

			// look at the neighbors and push to pq
			for (int i = 0; i < 4; i++) {
				// next locations
				int vind2 = vind + dx[i];
				int hind2 = hind + dy[i];
				
				
				
				
				if (vind2 < 0 || hind2 < 0 || vind2 >= n || hind2 >= m) 
					continue; // out of bounds
				
				if (visited[vind2][hind2]) continue;
				
				int x2 = v[vind2];
				int y2 = h[hind2];
				
				Pair neigh = new Pair(weight(x2, y2, curPoint.x, curPoint.y), 
						new Point(x2, y2, vind2, hind2));
				
				//
				
				//
				pq.add(neigh); // add to PQ
			}

			// add weight to the MST
			if (weight == Integer.MAX_VALUE) { // if it's an outer edge, don't count it
				//System.out.println("HERE");
			} else {
				cost += weight;
			}
			
			//System.out.println(weight);
		}
	}
	public static int weight(int ax, int ay, int bx, int by) { // returns weight between a and b
		int w = Math.abs(ax-bx) + Math.abs(ay-by);
		if (ax == 0 && bx == 0 || ax == A && bx == A || 
				ay == 0 && by == 0 || ay == B && by == B) {
			// if it's on the outer edge, make the weight tiny so that it gets included for sure
			w = Integer.MAX_VALUE; 
		}
		return w;
	}
	public static class Point {
		int x; // x pos
		int y; // y pos
		int a; // index in v
		int b; // index in h

		public Point(int a1, int b1, int c, int d) {
			x = a1;
			y = b1;
			a = c;
			b = d;
		}
		

	}
	public static class Pair implements Comparable<Pair> {
		int x; // weight
		Point p; // destination
		
		public Pair(int a1) {
			x = a1;
		}
		public Pair(int a1, Point c) {
			x = a1;
			p = c;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			
			return o.x-x;
		}
		

	}

}
