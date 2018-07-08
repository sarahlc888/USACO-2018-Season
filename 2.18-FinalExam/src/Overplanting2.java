import java.io.*;
import java.util.*;

/*
 * answer: N^2 
 * sweep from left to right
 * for each sweep line, find all the segments and the heights
 * check for what intervals of heights there's at least 1 open
 */
public class Overplanting2 {
	static long sum = 0;
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // num buildings
		
		TreeSet<Integer> locations = new TreeSet<Integer>(); // locations to loop through
		
		Polygon[] polys = new Polygon[N];

		int ind = 0;
		
		for (int i = 0 ; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x1 = Integer.parseInt(st.nextToken()); // start
			int y1 = Integer.parseInt(st.nextToken()); // end
			int x2 = Integer.parseInt(st.nextToken()); // start
			int y2 = Integer.parseInt(st.nextToken()); // end
			
			polys[i] = new Polygon();
			polys[i].pts.add(new Pair(x1, y1));
			polys[i].pts.add(new Pair(x1, y2));
			polys[i].pts.add(new Pair(x2, y1));
			polys[i].pts.add(new Pair(x2, y2));
//			System.out.println(polys[i]);
		}
		scan.close();

		
		// loop through all pairs of polygons
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j) continue;
				Polygon p1 = polys[i];
				Polygon p2 = polys[j];
				for (int k = 0; k < 4; k++) {
					// loop through k corners of p1
					for (int m = 0; m < 4; m++) {
						// loop through k corners of p2
						
						
						Pair pt1 = p1.pts.get(k);
						Pair pt2 = p2.pts.get(m);
						
//						System.out.println("  pt1: " + pt1);
//						System.out.println("  pt2: " + pt2);
						
						if (isInside(pt1, p2) && isInside(pt2, p1)) { 
							// if there's an intersection
//							System.out.println("HERE");
							p1.pts.add(new Pair(pt1.x, pt2.y));
							p1.pts.add(new Pair(pt2.x, pt1.y));
							
							p2 = null; // merge
						}
						
						
					}
					
				}
			}
		}
		int area = 0;
		for (int i = 0; i < N; i++) {
			if (polys[i] == null) continue;
			
			Polygon curPol = polys[i];
//			System.out.println("cp: " + curPol);
			ArrayList<Pair> curPts = curPol.pts;

			double totalsum = 0;

			for (int j = 0; j < curPts.size(); j++) { // loop through all points
				totalsum += (curPts.get(j).x*curPts.get((j+1)%curPts.size()).y - 
						curPts.get(j).y*curPts.get((j+1)%curPts.size()).x);
			}

			totalsum /= (double)2;

			area += totalsum; // assign the area
			////System.out.println("  area: " + totalsum);
		}
		
		System.out.println(area);
		
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
