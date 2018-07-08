import java.io.*;
import java.util.*;

/*
 * 4/10 test cases so far
 * 
 * IDEA!! O (CN)
 * 1. build the polygons O(N)
 * DFS and then get back to the original vertex each time
 * then start at another point you haven't visited
 * 
 * 2. find area of each polygon (don't actually need
 * use shoelace
 * for all k,  s1 += X k*Y (k-1) and s2 += X (k-1)*Y k
 * area =( s1 - s2 )    /2
 * 
 * sort polygons by area, O(N log N)
 * 
 * 3. for every polygon, find all the cows inside
 * 
 * get the smallest polygon the cow is in
 * O(C N ) (not CNN bc only N fences total)
 * 
 * 
 * 4. for each polygon, count how many cows are inside
 * 
 */

public class CrazyFences {

	static ArrayList<Pair> points;
	static Set<Pair> pointsSet;
	static Pair[] cows;
	static TreeSet<Pair> visited;

	static int V;
	static ArrayList<Polygon> ps;
	static int pInd = 0;
	static Map<Pair, Pair[]> adjs = new TreeMap<Pair, Pair[]>(); // make pair to adjs

	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // num fences
		int C = Integer.parseInt(st.nextToken()); // num cows

		//fences = new Fence[N];
		points = new ArrayList<Pair>();
		cows = new Pair[C];
		pointsSet = new TreeSet<Pair>();

		for (int i = 0; i < N; i++) { // scan in fences and points
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			Pair p1 = new Pair(a, b);
			Pair p2 = new Pair(c, d);

			pointsSet.add(p1);
			pointsSet.add(p2);

			int p1ind = -1;
			int p2ind = -1;


			for (int j = 0; j < points.size(); j++) {
				if (points.get(j).equals(p1)) {
					p1ind = j;
				}
				if (points.get(j).equals(p2)) {
					p2ind = j;
				}
				if (p1ind > -1 && p2ind > -1) break;
			}

			if (p1ind == -1) points.add(p1);
			if (p2ind == -1) points.add(p2);

			if (!adjs.containsKey(p1)) { // if p1 is not a key, init 
				adjs.put(p1, new Pair[2]);
				adjs.get(p1)[0] = p2;
			} else {
				adjs.get(p1)[1] = p2;
			}
			if (!adjs.containsKey(p2)) { // if p1 is not a key, init 
				adjs.put(p2, new Pair[2]);
				adjs.get(p2)[0] = p1;
			} else {
				adjs.get(p2)[1] = p1;
			}
		}

		V = points.size(); // number of nodes


		//System.out.println("pts: " + points);

		for (int i = 0; i < C; i++) { // scan in points
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			cows[i] = new Pair(a, b);
		}
		// BUILD THE POLYGONS

		ps = new ArrayList<Polygon>(); // polygons
		visited = new TreeSet<Pair>(); // visited set

		for (int i = 0; i < V; i++) { // loop through all points
			if (visited.contains(points.get(i))) continue;

			////System.out.println("HERE!");
			// if not visited, find the next polygon
			ps.add(new Polygon());
			ps.get(pInd).pts.add(points.get(i));
			visited.add(points.get(i));
			dfs(points.get(i));

			pInd++;
		}
		//System.out.println("num poly: " + pInd);


		// FIND THE AREAS USING SHOELACE

		for (int i = 0; i < pInd; i++) { // loop through polygons
			Polygon curPol = ps.get(i);
			//	//System.out.println("cp: " + curPol);
			ArrayList<Pair> curPts = curPol.pts;

			double totalsum = 0;

			for (int j = 0; j < curPts.size(); j++) { // loop through all points
				totalsum += (curPts.get(j).x*curPts.get((j+1)%curPts.size()).y - 
						curPts.get(j).y*curPts.get((j+1)%curPts.size()).x);
			}

			totalsum /= (double)2;

			curPol.area = totalsum; // assign the area
			////System.out.println("  area: " + totalsum);
		}
		Collections.sort(ps);


		// FOR EVERY POLYGON, FIND THE COWS INSIDE

		int[] numCowsInside = new int[ps.size()]; // num cows inside each polygon

		for (int i = 0; i < C; i++) { // loop through cows
			Pair curCow = cows[i];
			//System.out.println("CURCOW: " + curCow);
			for (int j = 0; j < pInd; j++) { // loop through polygon, smallest first

				
				Polygon curPol = ps.get(j);

				
				//System.out.println("curPol: " + curPol);

				if (isInside(curCow, curPol)) { // CHECK IF CURCOW IS INSIDE CURPOL
					// if it is inside, mark it
					//System.out.println("Here!");
					numCowsInside[j]++;
					break; // only count the cow as inside something once
				} 
				// otherwise, proceed

			}
		}

		//System.out.println(Arrays.toString(numCowsInside));

		// figure out what the max num cows inside is
		int max = 0;

		for (int j = 0; j < pInd; j++) {
			max = Math.max(max, numCowsInside[j]);
		}
		System.out.println(max);

	}
	public static boolean isInside(Pair cow, Polygon curPol) {

		// draw an almost horizontal ray from the cow

		// connect cow to the corner of the polygon
		// the second part is arbitrary, just chosen so that it doesn't hit any corners
		Line L = new Line(cow, new Pair(Integer.MAX_VALUE-101, cow.y+1));

		int intersectionCount = 0; // number of intersections between L and polygon

		for (int j = 0; j < curPol.pts.size(); j++) {
			// loop through all lines of the polygon
			// polygon segment
			Line L2 = new Line(curPol.pts.get(j), curPol.pts.get((j+1)%curPol.pts.size()));
			
			//System.out.println("    lines: " + L + " " + L2);
			
			if (intersect(L, L2)) {
				//System.out.println("    intersect at corner " + curPol.pts.get(j));
				intersectionCount++;
			}
		}
		//System.out.println("  intersections: " + intersectionCount);
		if (intersectionCount % 2 == 0) {
			// if it's even, it's outside (intersections include the corner)
			//System.out.println("  even, outside");
			return false;
		}

		//System.out.println("  odd, inside");
		return true;
	}
	public static long crossProd(Pair a, Pair b) {
		return ((long)a.x*(long)b.y-(long)a.y*(long)b.x);
	}
	public static Pair vector(Pair p1, Pair p2) {
		// returns vector from p1 to p2
		return new Pair(p2.x - p1.x, p2.y - p1.y);
	}
	public static boolean intersect(Line m, Line n) {
		// checks if lines a and b intersect

		// points
		Pair m1 = m.a;
		Pair m2 = m.b;
		Pair n1 = n.a;
		Pair n2 = n.b;

		// vectors from n1 and n2
		Pair v1 = vector(n1, m1);
		Pair v2 = vector(n1, m2);
		long cp1 = crossProd(v1, v2);

		Pair v3 = vector(n2, m1);
		Pair v4 = vector(n2, m2);
		long cp2 = crossProd(v3, v4);

		//System.out.println("      cp1: " + cp1);
		//System.out.println("      cp2: " + cp2);
		
		if (cp1 < 0 && cp2 > 0 || cp2 < 0 && cp1 > 0 || cp1 == 0 || cp2 == 0) {
			// if they are diff, they are on opposite sides on the line or 1 or both are on the line
			//System.out.println("      chpt1 passed");
		} else {
			return false;
		}

		// vectors from n1 and n2
		v1 = vector(m1, n1);
		v2 = vector(m1, n2);
		cp1 = crossProd(v1, v2);

		v3 = vector(m2, n1);
		v4 = vector(m2, n2);
		cp2 = crossProd(v3, v4);

		if (cp1 < 0 && cp2 > 0 || cp2 < 0 && cp1 > 0 || cp1 == 0 || cp2 == 0) {
			// if they are diff, they are on opposite sides on the line or 1 or both are on the line
			//System.out.println("      chpt2 passed");
		} else {
			return false;
		}

		return true; // if not false, then true
	}
	public static void dfs(Pair u) { // start from node u
		////System.out.println("u: " + u);

		Pair[] curAdj = adjs.get(u); // get the adjacencies

		for (int i = 0; i < 2; i++) { // loop through the adjs
			Pair next = curAdj[i];

			//	//System.out.println("  next: " + next);

			if (!visited.contains(next)) { // if not visited, mark visited and DFS
				visited.add(next);
				ps.get(pInd).pts.add(next); // add to the polygon
				dfs(next);
			}
		}
	}
	public static class Polygon implements Comparable<Polygon> {
		ArrayList<Pair> pts;
		double area;

		public Polygon() {
			pts = new ArrayList<Pair>();
		}

		@Override
		public int compareTo(Polygon o) {
			if (area > o.area) 
				return 1;

			if (area < o.area) 
				return -1;

			return 0;
		}
		public String toString() {
			return pts.toString();
		}


	}
	public static class Line /*implements Comparable<Fence>*/ {
		Pair a; // start pt
		Pair b; // end pt
		public Line(Pair x, Pair y) {
			a = x;
			b = y;
		}
		public String toString() {
			return a.toString() + "  " + b.toString();
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; // value
		int y; // amt

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by value
			if (x == o.x) {
				return o.y-y;
			}
			return x-o.x;
		}
		public String toString() {
			return "x: " + x + " y: " + y ;
		}
		public boolean equals(Pair o) {
			return (x == o.x && y == o.y);
		}

	}

}
