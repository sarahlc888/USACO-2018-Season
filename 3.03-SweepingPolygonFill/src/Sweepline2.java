import java.io.*;
import java.util.*;
/*
 * from 7/17 lesson and notes
 * added functionality for kites and trapezoids
 * doesn't work on things with horizontal edges that also have points on that line to be counted
 */
public class Sweepline2 {
	static Line[] edges;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("fill.in"));
		int N = Integer.parseInt(br.readLine()); // num corners
		Pair[] corners = new Pair[N]; // corners in cw or ccw order
		int minY = Integer.MAX_VALUE;
		int maxY = 0;
		
		// map corner to the indices of edges
		TreeMap<Pair, int[]> cornerToEdge = new TreeMap<Pair, int[]>();
				
		for (int i = 0; i < N; i++) { 
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y =Integer.parseInt(st.nextToken());
			corners[i] = new Pair(x, y);
			minY = Math.min(minY, y);
			maxY = Math.max(maxY, y);
			cornerToEdge.put(corners[i], new int[2]);
			cornerToEdge.get(corners[i])[0] = -1;
			cornerToEdge.get(corners[i])[1] = -1;
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
		
		for (int i = 0; i < N; i++) { // map corners to edges
			Line e = edges[i];
			Pair c1 = new Pair(e.p1.x, e.p1.y);
			Pair c2 = new Pair(e.p2.x, e.p2.y);
			
			if (cornerToEdge.get(c1)[0] == -1) {
				cornerToEdge.get(c1)[0] = i;
			} else {
				cornerToEdge.get(c1)[1] = i;
			}
			if (cornerToEdge.get(c2)[0] == -1) {
				cornerToEdge.get(c2)[0] = i;
			} else {
				cornerToEdge.get(c2)[1] = i;
			}
		}
		
		System.out.println(Arrays.toString(corners));
		System.out.println(Arrays.toString(edges));
		
		// numbers to indicate place in the list of edges, update using bsearch
		int minActive = 0;
		int maxActive = N-1;
		
		int onBounds = 0; // number of points on the boundaries
		int numPoints = 0; // number of points inside, excluding points on boundaries
		
		for (int y = minY; y <= maxY; y++) { // LOOP THROUGH ROWS, lo to hi
			System.out.println("y: " + y);
			
			// get the lowest and highest edge that have a lower y val < cur y
			maxActive = greatestBelow(y);
			minActive = leastAbove(y);

			ArrayList<Integer> xvals = new ArrayList<Integer>(); // xvals of intersections w/ sweepline
			for (int j = minActive; j <= maxActive; j++) { // edges
				Line curEdge = edges[j];
				
				// TODO: fix this
				if (curEdge.p1.y == y && curEdge.p2.y == y) break;
				
				if (curEdge.p1.y < y && curEdge.p2.y > y ||
						curEdge.p1.y <= y && curEdge.p2.y > y ||
						curEdge.p1.y < y && curEdge.p2.y >= y) { // verify intersection
					System.out.println("  curEdge: " + curEdge);
					// find intersection
					
					double m = (double)(curEdge.p2.y - curEdge.p1.y) / (double)(curEdge.p2.x - curEdge.p1.x);
					int dy = y-curEdge.p1.y; // difference in y (from cur.y to corner.y)
					double newx = (curEdge.p1.x + (double)dy/(double)m); // xval of intersection
//					System.out.println("    m: " + m);
//					System.out.println("    dy: " + dy);
//					System.out.println("    newx: " + newx);
					int nnewx = (int) newx; // truncated
					System.out.println("    nnewx: " + nnewx);
					
					// count if the points are on the edge
					if (newx == nnewx && !xvals.contains(nnewx)) {
						// if on the bounds and not already counted (cuz of corners)
						onBounds++; // count it
						System.out.println("ON bounds: " + onBounds);
					}
					xvals.add(nnewx);
				}
				
			}
			
			
			Collections.sort(xvals);
			
			// account for corners (regulate how many copies in xvals)
			int ind = 0;
			while (ind+1 < xvals.size()) {
				int cur = xvals.get(ind);
				int next = xvals.get(ind+1);
				if (cur == next) {
					System.out.println("  cur: " + cur + " next: " + next);
					// it's a corner (added to list twice because intersects two lines)
					// the edges that intersect at the corner
					int[] inters = cornerToEdge.get(new Pair(cur, y));
					
					Line l1 = edges[inters[0]];
					Line l2 = edges[inters[1]];
					
					System.out.println("    lines: " + l1 + "   " + l2);
					
					int y1 = l1.p1.y;
					if (y1 == y) y1 = l1.p2.y;
					int y2 = l2.p1.y;
					if (y2 == y) y2 = l2.p2.y;
	
					System.out.println("    ys: " + y1 + "   " + y2);
					
					// check what side of the sweep line they are on
					int d1 = map(y-y1);
					int d2 = map(y-y2);
					
					if (d1 == d2) {
						// both above or below cur yn(keep two copies)
						System.out.println("    out");
						// if not incrementing, then remove
//						xvals.remove(ind);
//						xvals.remove(ind);
						ind++; // if not removing, then increment
					} else {
						// get rid of one copy because the sweep line enteres the shape
						System.out.println("    in");
						xvals.remove(ind);
						// don't increment
					}
					continue;
				}
				ind++;
			}
//			System.out.println(xvals);
			// increment numpoints
			ind = 0;
			while (ind+1 < xvals.size()) {
				// include the actual vals on lines
				numPoints += xvals.get(ind+1) - xvals.get(ind) + 1;
				ind += 2;
			}
			System.out.println("  num points: " + numPoints);
		}
		// subtract off all points on the boundaries
		numPoints -= onBounds;
		System.out.println(numPoints);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fill.out")));

		pw.println(numPoints);
		pw.close();
	}
	public static int map(int n) {
		// returns -1 if n < 0; 0 if n == 0; 1 if n > 0
		if (n < 0) return -1;
		if (n > 0) return 1;
		return 0;
	}
	// get the lowest and highest edge that have a lower y val <= cur y
	public static int greatestBelow(long val) {
		// returns greatest i so that edges[i].p1.y <= val
		// returns greatest i <= val

		int lo = -1;
		int hi = edges.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (edges[mid].p1.y <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;
		
	}
	public static int leastAbove(long val) {
		// returns smallest i so that edges[i].p1.y <= val
		// returns smallest i >= val
		int lo = 0;
		int hi = edges.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if

			if (edges[mid].p1.y <= val) { // if mid is in range
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
