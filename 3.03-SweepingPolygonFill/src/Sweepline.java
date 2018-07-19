import java.io.*;
import java.util.*;
/*
 * from 7/17 lesson and notes
 * currently works on just a plain square... lots to do still
 */
public class Sweepline {
	static Line[] edges;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("fill.in"));
		int N = Integer.parseInt(br.readLine()); // num corners
		Pair[] corners = new Pair[N]; // corners in cw or ccw order
		int minY = Integer.MAX_VALUE;
		int maxY = 0;
		for (int i = 0; i < N; i++) { 
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y =Integer.parseInt(st.nextToken());
			corners[i] = new Pair(x, y);
			minY = Math.min(minY, y);
			maxY = Math.max(maxY, y);
		}
		edges = new Line[N]; // edges of the polygon
		for (int i = 0; i < N; i++) { // corner with lower y coord first
			if (corners[i].y <= corners[(i+1)%N].y) {
				edges[i] = new Line(corners[i], corners[(i+1)%N]);
			} else {
				edges[i] = new Line(corners[(i+1)%N], corners[i]);
			}
		}
		br.close();
		
		Arrays.sort(edges); // sort by p1.y then p2.y, lo to hi
		
		System.out.println(Arrays.toString(corners));
		System.out.println(Arrays.toString(edges));
		
		// numbers to indicate place in the list of edges, update using bsearch
		int minActive = 0;
		int maxActive = N-1;
		
		// LOOP THROUGH ROWS, lo to hi
		int numPoints = 0; // number of points inside, excluding points on boundaries
		for (int y = minY; y <= maxY; y++) { // y value (row)
			System.out.println("y: " + y);
			// TODO: CORRECTLY update min and max active edge indices
//			maxActive = greatestBelow(y);
//			minActive = leastAbove(y);
			// the above part doesn't work...
			ArrayList<Integer> xvals = new ArrayList<Integer>(); // list of xvals that intersect
			for (int j = minActive; j <= maxActive; j++) {
				Line curEdge = edges[j];
				System.out.println("  curEdge: " + curEdge);
				if (curEdge.p1.y < y && curEdge.p2.y > y) { // verify intersection
					// find intersection (use xj formula in notes)
					// TODO: fix this formula part
					// TODO: do the rounding up thing
					double m = (double)(curEdge.p2.y - curEdge.p1.y) / (double)(curEdge.p2.x - curEdge.p1.x);
					int newx = (int)(curEdge.p1.x + (curEdge.p2.y-y-1)/m);
					xvals.add(newx);
				}
			}
			Collections.sort(xvals);
			// TODO: check if it's a corner, account for that by adding it again in xval list or not
			// increment numpoints
			int ind = 0;
			while (ind < xvals.size()) {
				// exclude the actual vals because they are rounded up
				numPoints += xvals.get(ind+1) - xvals.get(ind) - 1;
				ind += 2;
			}
		}
		
		System.out.println(numPoints);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fill.out")));

		pw.println(numPoints);
		pw.close();
	}
	// TODO: make sure the bsearch functions work and do what they're supposed to do
	public static int greatestBelow(long val) {
		// returns greatest i so that edges[i].p1.y <= val
		// returns greatest i <= val

		int lo = -1;
		int hi = edges.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (edges[mid].p2.y <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;
		
	}
	public static int leastAbove(long val) {
		// returns smallest i so that edges[i].p2.y >= val
		// returns smallest i >= val
		int lo = 0;
		int hi = edges.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if

			if (edges[mid].p2.y >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
	}
	public static class Line implements Comparable<Line> {
		Pair p1;
		Pair p2;
		
		public Line(Pair a, Pair b) {
			p1 = a;
			p2 = b;
		}
		public int compareTo(Line o) { // sort by p1.y then p2.y, lo to hi
			if (p1.y == o.p1.y) return p2.y-o.p2.y;
			return p1.y - o.p1.y;
		}
		public String toString() {
			return p1.toString() + " -- " + p2.toString();
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
