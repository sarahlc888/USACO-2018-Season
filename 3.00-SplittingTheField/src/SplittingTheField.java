import java.io.*;
import java.util.*;
/*
 * about 40 minute implementation, 10/10 test cases
 * 
 * USACO 2016 US Open Contest, Gold
 * Problem 1. Splitting the Field
 * 
 * IDEA:
 * 
 * somewhat like 4 N log N, so O(N log N)
 * 
 * sort by x and then y
 * split by x, then by y (as if you're drawing a line)
 * then calc prefix
 * 
 * find the max
 * 
 * binary search
 * make sure that the maxY registers when there are multiple pairs with the same x but different y
 */

public class SplittingTheField {
	static long minArea = Long.MAX_VALUE;
	static int[] preMaxX;
	static int[] preMinX;
	static int[] preMaxY;
	static int[] preMinY;
	static int[] suffMaxX;
	static int[] suffMinX;
	static int[] suffMaxY;
	static int[] suffMinY;
	
	static int N;
	static Pair[] cowsX;
	static Pair[] cowsY;
	
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(new File("split.in"));
		N = Integer.parseInt(scan.nextLine()); // num cows
		cowsX = new Pair[N]; // cows sorted by x coord
		cowsY = new Pair[N]; // cows sorted by y coord

		long minx = Long.MAX_VALUE;
		long maxx = 0;
		long miny = Long.MAX_VALUE;
		long maxy = 0;
		
		for (int i = 0; i < N; i++) { // scan in cows
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			cowsX[i] = new Pair(x, y);
			cowsY[i] = new Pair(x, y);
			minx = Math.min(minx, x);
			maxx = Math.max(maxx, x);
			miny = Math.min(miny, y);
			maxy = Math.max(maxy, y);
		}
		Arrays.sort(cowsX);
		Arrays.sort(cowsY, yComparator);

		//System.out.println("cowsX: " + Arrays.toString(cowsX));
		//System.out.println("cowsY: " + Arrays.toString(cowsY));

		precomp();
		
		// split by X
		//System.out.println("SPLIT X");
		for (int i = 0; i < N-1; i++) { // split right after element i
			//System.out.println("i: " + i);
			if (i < N-2 && cowsX[i].x == cowsX[i+1].x) {
				//System.out.println("  C1");
				continue; // skip repeats
			}
			int curX = cowsX[i].x;
			int j = i+1;
			int nextX = cowsX[j].x;
			boolean abort = false; 
			while (curX == nextX) { // while they are on the same x border
				if (curX == cowsX[N-1].x) { // it's the whole rectangle
					abort = true;
					break;
				}
				j++; // if too slow, replace with binary search
				nextX = cowsX[j].x;
			}
			if (abort) {
				//System.out.println("  C2");
				continue;
			}
			
			//System.out.println("proceed with: " + i);
			// area of the left rectangle
			long area = (long)(preMaxY[i] - preMinY[i]) * (long)(curX - cowsX[0].x);
//			//System.out.println("  area1: " + area);
			// area of the right
			area += (long)(suffMaxY[j] - suffMinY[j]) * (long)(cowsX[N-1].x - nextX);
			minArea = Math.min(minArea, area);
			//System.out.println("  area: " + area);
		}
		// split by Y
		//System.out.println("SPLIT Y");
		for (int i = 0; i < N-1; i++) { // split right after element i
			if (i < N-2 && cowsY[i].y == cowsY[i+1].y) continue; // skip repeats
			int curY = cowsY[i].y;
			int j = i+1;
			int nextY = cowsY[j].y;
			boolean abort = false; 
			while (curY == nextY) { // while they are on the same x border
				if (curY == cowsY[N-1].y) { // it's the whole rectangle
					abort = true;
					break;
				}
				j++; // if too slow, replace with binary search
				nextY = cowsY[j].y;
			}
			if (abort) continue;
			
			//System.out.println("i: " + i);
			
			// area of the left rectangle
			long area = (long)(preMaxX[i] - preMinX[i]) * (long)(curY - cowsY[0].y);
			// area of the right
			area += (long)(suffMaxX[j] - suffMinX[j]) * (long)(cowsY[N-1].y - nextY);
			minArea = Math.min(minArea, area);
		}
		
		long origArea = (maxx - minx)*(maxy - miny);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("split.out")));
		pw.print(origArea-minArea);
		//System.out.println(origArea-minArea);
		pw.close();
	}
	public static void precomp() {
		// PREFIX PRECOMP
				// precomputation for cowsX
				preMaxY = new int[N]; // preMaxY[i] = max Y from cosX[1...i]
				preMinY = new int[N]; // preMinY[i] = min Y from cosX[1...i]
				preMaxY[0] = cowsX[0].y;
				preMinY[0] = cowsX[0].y;

				// precomputation for cowsY
				preMaxX = new int[N]; // in cowsY
				preMinX = new int[N]; // in cowsY
				preMinX[0] = cowsY[0].x;
				preMaxX[0] = cowsY[0].x;


				for (int i = 1; i < N; i++) {
					// cowsX precomp
					int curY = cowsX[i].y;

					if (curY > preMaxY[i-1]) { // new max
						preMaxY[i] = curY;
					} else { // same max
						preMaxY[i] = preMaxY[i-1];
					}
					if (curY < preMinY[i-1]) { // new min
						preMinY[i] = curY;
					} else { // same min
						preMinY[i] = preMinY[i-1];
					}

					// cowsY precomp
					int curX = cowsY[i].x;

					if (curX > preMaxX[i-1]) { // new max
						preMaxX[i] = curX;
					} else { // same max
						preMaxX[i] = preMaxX[i-1];
					}
					if (curX < preMinX[i-1]) { // new min
						preMinX[i] = curX;
					} else { // same min
						preMinX[i] = preMinX[i-1];
					}
				}
				

				// SUFFIX PRECOMP
				// precomputation for cowsX
				suffMaxY = new int[N]; // suffMaxY[i] = max Y from cosX[i...N-1]
				suffMinY = new int[N]; // suffMinY[i] = min Y from cosX[i...N-1]
				suffMaxY[N-1] = cowsX[N-1].y;
				suffMinY[N-1] = cowsX[N-1].y;

				// precomputation for cowsY
				suffMaxX = new int[N]; // in cowsY
				suffMinX = new int[N]; // in cowsY
				suffMinX[N-1] = cowsY[N-1].x;
				suffMaxX[N-1] = cowsY[N-1].x;


				for (int i = N-2; i >= 0; i--) {
					// cowsX suffcomp
					int curY = cowsX[i].y;

					if (curY > suffMaxY[i+1]) { // new max
						suffMaxY[i] = curY;
					} else { // same max
						suffMaxY[i] = suffMaxY[i+1];
					}
					if (curY < suffMinY[i+1]) { // new min
						suffMinY[i] = curY;
					} else { // same min
						suffMinY[i] = suffMinY[i+1];
					}

					// cowsY suffcomp
					int curX = cowsY[i].x;

					if (curX > suffMaxX[i+1]) { // new max
						suffMaxX[i] = curX;
					} else { // same max
						suffMaxX[i] = suffMaxX[i+1];
					}
					if (curX < suffMinX[i+1]) { // new min
						suffMinX[i] = curX;
					} else { // same min
						suffMinX[i] = suffMinX[i+1];
					}
				}

				//System.out.println("preMaxX: " + Arrays.toString(preMaxX));
				//System.out.println("preMinX: " + Arrays.toString(preMinX));
				//System.out.println("preMaxY: " + Arrays.toString(preMaxY));
				//System.out.println("preMinY: " + Arrays.toString(preMinY));
				//System.out.println("suffMaxX: " + Arrays.toString(suffMaxX));
				//System.out.println("suffMinX: " + Arrays.toString(suffMinX));
				//System.out.println("suffMaxY: " + Arrays.toString(suffMaxY));
				//System.out.println("suffMinY: " + Arrays.toString(suffMinY));
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
				return y-o.y;
			}
			return x-o.x;
		}
		public String toString() {
			return x + " " + y;
		}
		public boolean equals(Pair o ) {
			return (x == o.x && y == o.y);
		}

	}
	public static Comparator<Pair> yComparator = new Comparator<Pair>(){

		@Override
		public int compare(Pair a, Pair o) {
			if (a.y == o.y) {
				return a.x-o.x;
			}
			return a.y-o.y;
		}
	};
}
