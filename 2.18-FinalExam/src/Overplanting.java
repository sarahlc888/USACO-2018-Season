import java.io.*;
import java.util.*;

public class Overplanting {
	static long sum = 0;
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // num buildings
		
		TreeSet<Integer> locations = new TreeSet<Integer>(); // locations to loop through
		
		Pair[] points = new Pair[4*N]; // array of points to process
		int ind = 0;
		
		for (int i = 0 ; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x1 = Integer.parseInt(st.nextToken()); // start
			int y1 = Integer.parseInt(st.nextToken()); // end
			int x2 = Integer.parseInt(st.nextToken()); // start
			int y2 = Integer.parseInt(st.nextToken()); // end
			
			points[ind] = new Pair(x1, y1, true);
			ind++;
			points[ind] = new Pair(x1, y2, true);
			ind++;
			points[ind] = new Pair(x2, y1, false);
			ind++;
			points[ind] = new Pair(x2, y2, false);
			ind++;
			
			// add the points to be processed
			locations.add(x1);
			locations.add(x2);
		}
		scan.close();
		Arrays.sort(points);
		System.out.println(Arrays.toString(points));
		
		int area = 0;
		
		// holds the current top and bottom
		PriorityQueue<Integer> curTop = new PriorityQueue<Integer>(1, Collections.reverseOrder()); // heights valid at that location
		PriorityQueue<Integer> curBot = new PriorityQueue<Integer>();
		
		ind = 0; // index in points []
		int prevPos = -1; // for use in set loop below
		
		Iterator<Integer> iterator = locations.iterator();
		
		while(iterator.hasNext()) { // iterate through all the locations
			
			int curPos = iterator.next(); // current position

			System.out.println("pos: " + curPos);
			System.out.println("  ind: " + ind);
			
			System.out.println(curTop);
			System.out.println(curBot);
			
			// update area UNLESS it's the first element OR there's no buildings
			
			// calculate the height from set[i-1] to set[i]
			if (curTop.size() > 0 && curBot.size() > 0) {
				sum += (long)(curTop.peek()-curBot.peek()) * (long)(curPos - prevPos);

				//////System.out.println("  added: " + maxVal(curHeights) * (next - previ));
				//System.out.println("  add: " + (curHeights.get(curHeights.size()-1)) * (curPos - previ));
			}
			//System.out.println("  sum: " + sum);

			// add to curHeights
			while (points[ind].x == curPos && points[ind].open) { 
				//System.out.println("  start: points[ind]: " + points[ind]);
				// while the point is for the current number
				// and it's a START operation
				curBot.add(points[ind].y); // add the height to the heights
				curTop.add(points[ind+1].y); // add the height to the heights
				ind+=2;
			}
			
			
			while (ind < points.length && points[ind].x == curPos && !points[ind].open) {
				//System.out.println("  end: points[ind]: " + points[ind]);
				// while the point is for the current number
				// and it's an END operation
				// remove the height from the height set
				////System.out.println(points[ind]);
				
				//int bind = Collections.binarySearch(curHeights, points[ind].height);
				////System.out.println(bind);
				curBot.remove((Integer) points[ind].y);
				curTop.remove((Integer) points[ind+1].y);
				
				ind+=2;
	
			}

			prevPos = curPos;
		}
		System.out.println(sum);
		
	}
	public static int maxVal(ArrayList<Integer> a) {
		int m = 0;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i ) > m) m = a.get(i);
		}
		return m;
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;
		boolean open; // true == start, 1 == end
		public Pair(int n, int h, boolean o) {
			x = n;
			y = h;
			open = o;
		}
		@Override
		public int compareTo(Pair p) {
			if (x == p.x) return y-p.y;
			return x-p.x;
		}
		public String toString() {
			return (x + " " + y + " " + open);
		}
	}
}
