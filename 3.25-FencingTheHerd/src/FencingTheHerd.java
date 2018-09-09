import java.io.*;
import java.util.*;
/*
 * convex hull (adapted from 3.01)
 * 2/15, does not work
 * FAIL so far
 */
public class FencingTheHerd {
	static Stack<Pair> ch;
	static TreeSet<Pair> chSet;
	static ArrayList<Line> lines;
	static ArrayList<Pair> cows;
	static int N;

	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("fencing.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // starting num cows
		int Q = Integer.parseInt(st.nextToken()); // num queries
		cows = new ArrayList<Pair>(); // max size
		
		for (int i = 0; i < N; i++) { // scan in cows
			st = new StringTokenizer(br.readLine());
			long a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());
			cows.add( new Pair(a, b) );
		}
		Collections.sort(cows);
//		System.out.println("cows:" + (cows));
		
		ch = new Stack<Pair>(); // the convex hull polygon
		chSet = new TreeSet<Pair>(); // set of points
		lines = new ArrayList<Line>();
		
		// basecase, add the last cow
		ch.add(cows.get(N-1)); 
		chSet.add(cows.get(N-1)); 
		
		getHull(); // get the convex hull
		
//		System.out.println("hull: " + ch);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("fencing.out")));
		
		for (int i = 0; i < Q; i++) { // process queries
			st = new StringTokenizer(br.readLine());
			int type = Integer.parseInt(st.nextToken());
			
			if (type == 1) { // add a new cow
				long a = Long.parseLong(st.nextToken());
				long b = Long.parseLong(st.nextToken());
				Pair ncow = new Pair(a, b);
				cows.add( ncow ); // add to cows
				
				ch = new Stack<Pair>(); // the convex hull polygon
				chSet = new TreeSet<Pair>(); // set of points
				lines = new ArrayList<Line>();
				
				// basecase, add the last cow
				ch.add(cows.get(cows.size()-1)); 
				chSet.add(cows.get(cows.size()-1)); 
				
				getHull(); // TODO: modify the convex hull more efficiently (just do point around it)
//				System.out.println("hull: " + ch);
				
			} else if (type == 2) { // test the fence ax + by = c for intersection
				long a1 = Long.parseLong(st.nextToken());
				long b1 = Long.parseLong(st.nextToken());
				long c1 = Long.parseLong(st.nextToken());
				
				boolean b = intersect(a1, b1, c1);
				if (b) {
					pw.println("YES");
					System.out.println("YES");
				} else {
					pw.println("NO");
					System.out.println("NO");
				}
			}
		}
		br.close();

		pw.close();
	}
	public static boolean intersect(long a, long b, long c) {
		// ax + by = c; y = (c-ax)/b
//		System.out.println("a: " + a + " b: " + b + " c: " + c);
		//System.out.println("  firstP: " + firstP);
//		System.out.println("  lastP: " + lastP);
		
		// TODO: binary search the lines in the hull
		
		// brute force right now
		
		for (int i = 0; i < lines.size(); i++) {
			if (linesIntersect(a, b, c, lines.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	public static void getHull() {
		int curN = cows.size();
		
		for (int i = curN-2; i >= 0; i--) // loop from biggest x to smallest
			processPt(i);
		for (int i = 0; i < curN; i++) // loop from smallest x back to biggest 
			processPt(i);
		
		lines = new ArrayList<Line>();
		for (int i = 0; i < ch.size(); i++) { // loop through hull
			Pair pt = ch.get(i);
			Pair npt = ch.get((i+1) % ch.size());
			Line curl = new Line(pt, npt);
			lines.add(curl);
		}
		Collections.sort(lines);
	}
	public static void processPt(int i) {
		Pair cur = cows.get(i); // current point
		
//		System.out.println(ch.size() + " : " + ch);
		
		if (ch.size() < 2) {
//			System.out.println("  too small");
			if (!chSet.contains(cur)) { 
				
				// if there's not a previous line to turn left from, just add cur
				// if not already added
				ch.push(cur);
				chSet.add(cur);
			}
			return;
		}

		long cp = getCrossProd(cur); // cross product after adding current point
		
		// if the vectors turn left, do nothing
		while (cp < 0) { // if vectors turn right
			Pair p = ch.pop();
			chSet.remove(p); // remove the last point from the hull
			if (ch.size() <= 1) break; // stop if only 1 other point left
			cp = getCrossProd(cur); // keep going
		}

		// add the point if not already added
		if (!chSet.contains(cur)) { 
			ch.push(cur);
			chSet.add(cur);
		}
	}
	public static Pair vector(Pair p1, Pair p2) {
		// returns vector from p1 to p2
		return new Pair(p2.x - p1.x, p2.y - p1.y);
	}
	public static boolean linesIntersect(long a, long b, long c, Line n) {
		Pair s1 = n.p1;
		Pair e1 = n.p2;
		
		long s2x = s1.x;
		long e2x = e1.x;
		// ax + by = c; y = c-ax / b
		double s2y = 0;
		double e2y = 0;
		if (b == 0) {
			s2y = Long.MAX_VALUE;
			e2y = Long.MIN_VALUE;
		} else {
			s2y = (double)(c-a*(long)s2x)/b;
			e2y = (double)(c-a*(long)e2x)/b;
		}
		
		if (s1.y == s2y || e1.y == e2y) return true;
		if (s1.y <= s2y && e1.y >= e2y) return true;
		if (s1.y >= s2y && e1.y <= e2y) return true;
		return false;
	}
	public static boolean linesIntersect(Line m, Line n) {
		// checks if lines a and b intersect

		// points
		Pair m1 = m.p1;
		Pair m2 = m.p2;
		Pair n1 = n.p1;
		Pair n2 = n.p2;

		// vectors from n1 and n2
		Pair v1 = vector(n1, m1);
		Pair v2 = vector(n1, m2);
		long cp1 = crossProd(v1, v2);

		Pair v3 = vector(n2, m1);
		Pair v4 = vector(n2, m2);
		long cp2 = crossProd(v3, v4);

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
	public static long getCrossProd(Pair cur) {
		Pair p3 = cur;
		// get the last 2 points from the convex hull
		Pair p2 = ch.pop();
		Pair p1 = ch.peek();
		ch.push(p2);
		
		// hull would be p1--p2--p3, where p3 is the new point
		// get the lines
		Pair v1 = p2.minus(p1);
		Pair v2 = p3.minus(p2);

		long cp = crossProd(v1, v2);
		return cp;
	}
	public static long crossProd(Pair a, Pair b) {
		return ((long)a.x*b.y-(long)a.y*b.x);
	}
	public static class Pair implements Comparable<Pair> {
		long x;
		long y;

		public Pair(long a, long b) {
			x = a;
			y = b;
		}
		public Pair minus(Pair o) {
			return new Pair(x-o.x, y-o.y);
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x > o.x) return 1;
			if (x<o.x) return -1;
			if (x==o.x) return 0;
			if (y > o.y) return 1;
			if (y<o.y) return -1;
			return 0;
			
		}
		public String toString() {
			return x + " " + y;
		}
	}
	public static class Line implements Comparable<Line> {
		Pair p1;
		Pair p2;
		
		public Line(Pair a, Pair b) {
			p1 = a;
			p2 = b;
		}
		@Override
		public int compareTo(Line o) { // sort by p1, then p2
			if (p1.compareTo(o.p1) == 0) return p2.compareTo(o.p2);
			return p1.compareTo(o.p1);
		}
	}

}
