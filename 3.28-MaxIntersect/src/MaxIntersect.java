import java.io.*;
import java.util.*;

/*
 * see 9/25/18 google doc for explanation of problem
 * not fully tested but theoretically works
 */
public class MaxIntersect {
	public static void main(String args[]) throws IOException {
		// starting point is 0
		BufferedReader br = new BufferedReader(new FileReader("mi.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		// Ax + By = C (C can vary)
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		
		Pair[] points = new Pair[2*N];
		Line[] lines = new Line[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			lines[i] = new Line(x1, y1, x2, y2, i);
			points[2*i] = new Pair(x1, y1, i);
			points[2*i+1] = new Pair(x2, y2, i);
		}
		
		for (int i = 0; i < 2*N; i++) {
			int x = points[i].x;
			int y = points[i].y;
			int C = A*x+B*y;
			points[i].c = C;
		}
		
		Arrays.sort(points); // sort by c, distance from the line

		boolean[] active = new boolean[N]; // checks if lines are active
		int acount = 0;
		int maxa = 0;
		
		for (int i = 0; i < points.length; i++) {
			Pair cur = points[i];
			System.out.println("cur: " + cur);
			
			if (!active[cur.lineID]) { // opening
				active[cur.lineID] = true;
				System.out.println("  OPEN");
				acount++;
				maxa = Math.max(maxa, acount);
			} else { // closing
				System.out.println("  CLOSE");
				acount--;
			}
		}
		
		System.out.println(maxa);
		
	}
	public static class Line implements Comparable<Line> {
		Pair p1;
		Pair p2;
		public Line(int a1, int a2, int b1, int b2, int LI) {
			p1 = new Pair(a1, a2, LI);
			p2 = new Pair(b1, b2, LI);
		}
		public Line(Pair a1, Pair a2) {
			p1 = a1;
			p2 = a2;
		}
		@Override
		public int compareTo(Line o) { // placeholder
			return p1.compareTo(o.p1);
		}
		public String toString() {
			return p1.toString() + "   " + p2.toString();
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;
		int c;
		int lineID;
		public Pair(int a, int b, int li) {
			x = a;
			y = b;
			lineID = li;
		}
		@Override
		public int compareTo(Pair o) { // sort by c (highest to lowest)
			return o.c-c;
		}
		public String toString() {
			return x + " " + y;
		}
	}

}
