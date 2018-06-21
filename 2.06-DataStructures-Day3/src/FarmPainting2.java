import java.io.*;
import java.util.*;

/*
 * sweep through kind of like city horizon
 * seee v3
 * 
 */
public class FarmPainting2 {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // number of enclosures
		
		//Box[] boxes = new Box[N]; 
		Corner[] corners = new Corner[2*N];
		
		int minx = Integer.MAX_VALUE;
		int maxx = 0;

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());

			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			corners[2*i] = new Corner(x1, y1, y2, true);
			corners[2*i+1] = new Corner(x2, y2, y1, false);
			
			// assign the others
			corners[2*i].other = corners[2*i+1];
			corners[2*i+1].other = corners[2*i];
			
			
			if (x1 > maxx) maxx = x1;
			if (x1 < minx) minx = x1;
			if (x2 > maxx) maxx = x2;
			if (x2 < minx) minx = x2;
			
		}
		
		Arrays.sort(corners);
		
		int count = 0;
		
		// track current ranges "active" (sorted by smallest first)
		TreeSet<Range> curRange = new TreeSet<Range>(); 
		int[] curArr; // TODO: replace this with an arrayList or prioritiy queue
		// (will stay sorted if no enclosed boxes are added)
		
		int ind = 0; // for traversing boxes
		
		for (int x = minx; x <= maxx; x++) {
			// sweep through all of the xs
			
			//System.out.println("x: " + x);
			
			curArr = toArr(curRange); // current array version of curRange
			
			//System.out.println(Arrays.toString(curArr));
			
			
			// if a box opens up at this point
			while (ind < 2*N && x == corners[ind].x && corners[ind].LL) {
				//System.out.println("  open " + corners[ind]);
				
				int yval = corners[ind].y;
				//System.out.println("  " + yval);
				
				
				// least element above yval
				int nextInd;
				if (curArr.length == 0) {
					nextInd = -1;
				} else {
					nextInd = leastAbove(curArr, yval); 
					//System.out.println("  nextval: " + curArr[nextInd]);
				}
				
				// if it's odd, it's an end point; even, open point
				if (nextInd%2 == 1) {
					// odd, so it's the end point of another box's yrange
					// it's contained inside something
					
				} else {
					// if it's not inside anything, add the Ys to curRange and curArr
					Range r = new Range(corners[ind].y, corners[ind].y2);
					curRange.add(r);
					corners[ind].added = true;
					//System.out.println("  inc");
					count++;
				}
				
				ind++;
			}
			
			// if a box ENDS at this point  
			
			
			while (ind < 2*N && x == corners[ind].x && !corners[ind].LL) {
				
				if (corners[ind].other.added) {
					//System.out.println("  close " + corners[ind]);
					// this should only happen if the start thing was added
					
					// remove the range from curRange
					Range r1 = new Range(corners[ind].y2, corners[ind].y);
					curRange.remove(r1);
					////System.out.println(r1.equals(new Range(0, 9)));
					////System.out.println(corners[ind].y2 + " " + corners[ind].y);
				}
				
				
				
				ind++;
			}
			
		}
		System.out.println(count);
		
	}
	public static int[] toArr(TreeSet<Range> curRange) {
		int[] arr = new int[curRange.size() * 2]; // array
		Iterator<Range> it = curRange.iterator();
		int ind = 0;
		while (it.hasNext()) {
			Range r = it.next();
			arr[ind] = r.y1;
			ind++;
			arr[ind] = r.y2;
			ind++;
		}
		return arr;
	}
	public static class Corner implements Comparable<Corner> {
		int x;
		int y;
		int y2;
		boolean LL; // or UR
		Corner other;
		boolean added;
		
		public Corner(int a, int b, int c, boolean t) {
			x = a;
			y = b;
			y2 = c;
			LL = t;
		}
		public int compareTo(Corner r) { // sort by x coord, then opening or closing
			if (x < r.x) return -1;
			if (x > r.x) return 1;
			if (LL) return -1;
			if (!LL) return 1;
			return 0;
		}
		
		public String toString() {
			return x + " " + y + " LL: " + LL;
		}
	}
	public static class Point {
		int x;
		int y;
		public Point(int x1, int y1) {
			x = x1;
			y = y1;
		}
		public String toString() {
			return x + " " + y;
		}
	}
	public static class Range implements Comparable<Range> {
		int y1;
		int y2;
		public Range(int a, int b) {
			y1 = a;
			y2 = b;
		}
		public int compareTo(Range r) {
			if (y1 < r.y1) return -1;
			if (y1 > r.y1) return 1;
			if (y2 < r.y2) return -1;
			if (y2 > r.y2) return 1;
			return 0;
		}
		public boolean equals(Range r) {
			return y1 == r.y1 && y2 == r.y2;
		}
		public String toString() {
			return y1 + " " + y2;
		}
	}
	
	public static int leastAbove(int[] arr, long val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
			////System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);

			if (arr[mid] >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
	}

}
