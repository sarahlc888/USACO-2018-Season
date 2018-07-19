import java.io.*;
import java.util.*;
/*
 * version with decimals
 * 
 * convex hull problem 7/10/18
 * computational geometry w/ Dr. Ming
 * see notes in google drive
 * 
 * should work, works on rudimentary test cases / the random one
 * 
 */
public class ConvexHullDecimal {
	static Stack<Pair> ch;
	static TreeSet<Pair> chSet;
	static Pair[] pts;

	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("chDec.in"));
		int N = Integer.parseInt(br.readLine());
		pts = new Pair[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			pts[i] = new Pair(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
		}
		br.close();
		Arrays.sort(pts);
//		System.out.println(Arrays.toString(pts));

		ch = new Stack<Pair>(); // the polygon
		chSet = new TreeSet<Pair>(); // set of points
		
		// basecase
		ch.add(pts[N-1]); 
		chSet.add(pts[N-1]);
		
		// loop from biggest x to smallest
		for (int i = N-2; i >= 0; i--) {
			processPt(i);
		}
//		System.out.println(ch);
		// loop from smallest x back to biggest 
		for (int i = 0; i < N; i++) {
			processPt(i);
		}

		System.out.println(ch);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

		pw.println();
		pw.close();
	}
	public static void processPt(int i) {
		Pair cur = pts[i];
//		System.out.println("cur: " + cur);
//		System.out.println("ch: " + ch);
//		System.out.println("chSet: " + chSet);
		
		if (ch.size() <= 1) {
			// if there's not a previous line
			// to turn left from, just add cur
			if (!chSet.contains(cur)) { // if not already added
				ch.push(cur);
				chSet.add(cur);
			}
			return;
		}

		double cp = getCP(cur);
//		System.out.println("cp: " + cp);
		
		// vectors turn left, do nothing, then add the point

		while (cp < 0) { // turning right

//			System.out.println("A");
			// pop off a point and also remove from the set
			chSet.remove(ch.pop()); 
			
			if (ch.size() <= 1) break; // too small
			
			cp = getCP(cur);
		}

		if (!chSet.contains(cur)) { // if not already added
			ch.push(cur);
			chSet.add(cur);
		}
//		System.out.println();
	}
	public static double getCP(Pair cur) {
		Pair p3 = cur;
		Pair p2 = ch.pop();
		Pair p1 = ch.peek();
		ch.push(p2);
		
//		System.out.println("  p1: " + p1 + "  p2: " + p2 + "  p3: " + p3);

		// ch is p1 p2 p3, where p3 is the new point
		Pair v1 = p2.minus(p1);
		Pair v2 = p3.minus(p2);

		double cp = crossProd(v1, v2);
		return cp;
	}

	public static double crossProd(Pair a, Pair b) {
		return (a.x*b.y-a.y*b.x);
	}
	public static class Pair implements Comparable<Pair> {
		double x;
		double y;

		public Pair(double a, double b) {
			x = a;
			y = b;
		}
		public Pair minus(Pair o) {
			return new Pair(x-o.x, y-o.y);
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) {
				if (y>o.y) return 1;
				else if (y<o.y) return -1;
				return 0;
			}
			if (x<o.x) return -1;
			if (x>o.x) return 1;
			return 0;
		}
		public String toString() {
			return x + " " + y;
		}

	}
}
