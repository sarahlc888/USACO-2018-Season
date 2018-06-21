import java.io.*;
import java.util.*;
/*
 * 6/10 test cases
 * debug later
 */
public class CityHorizon {
	static long sum = 0;
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // num buildings
		
		TreeSet<Integer> locations = new TreeSet<Integer>(); // locations to loop through
		ArrayList<Integer> curHeights = new ArrayList<Integer>(); // heights valid at that location
		
		Pt[] points = new Pt[2*N]; // array of points to process
		
		for (int i = 0 ; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int s = Integer.parseInt(st.nextToken()); // start
			int e = Integer.parseInt(st.nextToken()); // end
			int h = Integer.parseInt(st.nextToken()); // height
			points[2*i] = new Pt(s, h, 0);
			points[2*i+1] = new Pt(e, h, 1);
			// add the points to be processed
			locations.add(s);
			locations.add(e);
		}
		scan.close();
		Arrays.sort(points);
		//System.out.println(Arrays.toString(points));
		
		int ind = 0; // index in points []
		int prevPos = -1; // for use in set loop below
		
		Iterator<Integer> iterator = locations.iterator();
		
		while(iterator.hasNext()) { // iterate through all the locations
			Collections.sort(curHeights);
			int curPos = iterator.next(); // current position

			//System.out.println("pos: " + curPos);
			//System.out.println("  ind: " + ind + " : " + points[ind]);
			
			//System.out.println(curHeights);

			// update sum UNLESS it's the first element OR there's no buildings
			if (curHeights.size() > 0) {
				sum += (curHeights.get(curHeights.size()-1)) * (curPos - prevPos);

				//////System.out.println("  added: " + maxVal(curHeights) * (next - previ));
				//System.out.println("  add: " + (curHeights.get(curHeights.size()-1)) * (curPos - previ));
			}
			//System.out.println("  sum: " + sum);

			// add to curHeights
			while (points[ind].num == curPos && points[ind].op == 0) { 
				//System.out.println("  start: points[ind]: " + points[ind]);
				// while the point is for the current number
				// and it's a START operation
				curHeights.add(points[ind].height); // add the height to the heights
				ind++;
			}
			

			// calculate the height from set[i-1] to set[i]
			while (ind < points.length && points[ind].num == curPos && points[ind].op == 1) {
				//System.out.println("  end: points[ind]: " + points[ind]);
				// while the point is for the current number
				// and it's an END operation
				// remove the height from the height set
				////System.out.println(points[ind]);
				
				//int bind = Collections.binarySearch(curHeights, points[ind].height);
				////System.out.println(bind);
				curHeights.remove((Integer) points[ind].height);

				ind++;
	
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
	public static class Pt implements Comparable<Pt> {
		int num;
		int height;
		int op; // 0 == start, 1 == end
		public Pt(int n, int h, int o) {
			num = n;
			height = h;
			op = o;
		}
		@Override
		public int compareTo(Pt p) {
			if (num > p.num) return 1;
			if (num < p.num) return -1;
			if (op < p.op) return -1;
			if (op > p.op) return 1;
			else return 0;
		}
		public String toString() {
			return (num + " " + height + " " + op);
		}
	}
}
