import java.io.*;
import java.util.*;
/*
 * USACO 2015 January Contest, Gold
 * Problem 1. Cow Rectangles
 * 
 * had to look at answer
 * took forever to write and had so many bugs
 * even though it's really not that hard
 * 
 * 14/14 eventually......
 */
public class CowRectangles {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowrect.in"));
		int N = Integer.parseInt(br.readLine()); // num cows
		Cow[] cows = new Cow[N]; // cows sorted by x
		TreeSet<Integer> ys = new TreeSet<Integer>();
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			String type = st.nextToken();
			boolean h = type.equals("H");
			cows[i] = new Cow(x, y, h);
			ys.add(y);
		}
		br.close();
		
		int[] yvals = new int[ys.size()];
		int i = 0;
		for (int a : ys) {
			yvals[i] = a;
			i++;
		}
		Arrays.sort(yvals);
		
		Arrays.sort(cows, (a, b) -> Integer.compare(a.x, b.x)); // sort by x
		
//		System.out.println(Arrays.toString(arrX));
//		System.out.println(Arrays.toString(arrY));
		
		// max num of consecutive holsteins in cur rect (without a guernsey in the middle)
		int maxnum = 0; 
		int area = 0;
		
		for (i = 0; i < yvals.length; i++) {
			for (int j = i+1; j < yvals.length; j++) {
				boolean gfree = false; // no guernseys in current x iteration
				int count = 0; // current num holsteins
				int lastx = -1;
				
				int y1 = yvals[i]; // bottom of fence
				int y2 = yvals[j]; // top of fence
				
//				System.out.println("ys: " + y1 + " " + y2);
				
				for (int k = 0; k < N;) { // loop through all cows to sweep through xs
//					System.out.println("  k: " + k + " cow: " + cows[k]);

					int m = k;
					int hcount = 0;
					int gcount = 0;
					
					while (m < N && cows[k].x == cows[m].x) { // process all cows on same x

						if (cows[m].y < y1 || cows[m].y > y2) { // if out of bounds, ignore
							m++;
							continue; 
						}
						if (cows[m].holstein) { 
							hcount++;
						} else { // it's a guernsey
							gcount++;
						}
						m++; // increment m
					}
					if (gcount > 0) {
						gfree = false;
						count = 0; // no holsteins in the rectangle ending here, reset!
					} else if (hcount > 0) { // if only h's, add it to the rectangle
						if (!gfree) {
							gfree = true; // valid rectangle now
							lastx = cows[k].x; // start of valid rectangle
						}
//						System.out.println("    NOG");
						
						count += hcount;
						
						int curArea = (y2-y1)*(cows[k].x-lastx);
//						System.out.println("x1: " + x1 + " x2: " + x2);
						if (count > maxnum || count == maxnum && curArea < area) { // check for max, reassign, reset
							maxnum = count; 
							area = curArea;
		
						}
					}
					
					k = m; // update k (-1 bc m needs to be processed next)
				}
						
					
//				System.out.println("  max: " + maxnum);
//				System.out.println("  area: " + area);
			}
		}
//		System.out.println(maxnum);
//		System.out.println(area);
		

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowrect.out")));

		pw.println(maxnum);
		pw.println(area);
		pw.close();

	}
	public static class Cow implements Comparable<Cow> {
		int x;
		int y;
		boolean holstein;
		
		public Cow(int a, int b) {
			x = a;
			y = b;
		}
		public Cow(int a, int b, boolean c) {
			x = a;
			y = b;
			holstein = c;
		}
		@Override
		public int compareTo(Cow o) { // sort by x, then y (lo to hi)
			if (x == o.x) return y-o.y;
			return x-o.x;
		}
		public String toString() {
			return x + " " + y + " " + holstein;
		}
	}


}
