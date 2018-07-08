import java.io.*;
import java.util.*;
/*
 * 6/10 test cases
 * O(N log N)
 * get intervals that have even # cows and has #white >= #spotted
 */
// j-i+1 = photo length, must be even. j-i must be odd, j and i must be diff parities
// p[j]-p[i-1] = sum of p[i...j] >= 0 and must be even, so p[j] >= p[i-1]


// separate based on if p[i] is even or odd. each i j must have 1 from each group

// fix j. i must be in the other array and before j
// find min i in the other array so that the p[j] >= p[i-1] (which is in the same)

// look for an i-1 in the same array and then get the i later

// points with same p value - only the leftmost one matters. it won't be a left point (i)
// keep a list of potential left points: lower p vals and lower x pos
// Find the first (leftmost) one (sorted by x pos) so that p[i-1] <= p[j]
// use bsearch

// return j x - i x

// 1...N indexing
public class FairPhotography {

	static int maxRange = 0;
	static Point[] cows;
	static int N;

	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine()); // number of cows

		cows = new Point[N+1]; // (position, state - W == +1, S == -1)
		cows[0] = new Point(0, -1);
		for (int i = 1; i <= N; i++) { // scan in cows (position, 
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			String state = st.nextToken();
			int stateInt = 0;

			if (state.equals("W")) 
				stateInt = 1;
			else 
				stateInt = -1;

			cows[i] = new Point(x, stateInt);
		}
		Arrays.sort(cows);
		for (int i = 0; i <= N; i++) {
			cows[i].i = i;
		}

		//System.out.println(Arrays.toString(cows));

		// need a zero to mark the start in p
		int[] p = new int[N+1];
		p[0] = 0;

		ArrayList<Point> evenPs = new ArrayList<Point>();
		ArrayList<Point> oddPs = new ArrayList<Point>();
		evenPs.add(cows[0]); // add the first 0 state
		
		for (int i = 1; i <= N; i++) { // loop through all the cows and update
			p[i] = p[i-1] + cows[i].y;
			cows[i].z = p[i];
			if (p[i]%2 == 0) // even
				evenPs.add(cows[i]);
			else // odd
				oddPs.add(cows[i]);
		}


		//System.out.println("here1");
		// process evenPs
		//System.out.println("EVENS");
		func(evenPs);

		// process oddPs
		//System.out.println("ODDS");
		func(oddPs);


		System.out.println(maxRange);
	}
	public static void func(ArrayList<Point> Ps) {
		//System.out.println(Ps);
		ArrayList<Point> lefts = new ArrayList<Point>(); // potential left points
		lefts.add(Ps.get(0)); // add the first cow

		for (int i = 1; i < Ps.size(); i++) { // loop through the rest of the cows
			if (Ps.get(i).z > Ps.get(Ps.size()-1).z) { // if p[i] value > previous
				lefts.add(Ps.get(i)); // add it
			}
		}
		// lefts is now sorted in descending order
		// TODO: see if this works, if not, delete
		Collections.sort(lefts, zComparator);
		//System.out.println("lefts: " + lefts);

		for (int a = 0; a < Ps.size(); a++) { // potential right points
			// in potential left points (i-1s), look for leftmost i-1 st p[i-1] <= p[j]
			// greatest less than p[j]
			//System.out.println("a: " + a);
			Point j = Ps.get(a); // right point

			int ind = greatestBelow(lefts, j.z);
			if (ind  == -1) {
				continue; // if there's nothing
			} 
			
			Point im1 = lefts.get(ind); // left point

			if (im1.i == j.i) continue;

			//System.out.println("ind: " + ind + "  i: " + im1.i);
			//System.out.println(im1.i+1);

			if (im1.i+1 >= N) {
				continue; // if there is no i+1
			}
			//System.out.println(j.x - cows[im1.i+1].x);
			// TODO: which one works?
			
			if (j.x - cows[im1.i+1].x > maxRange) {
				//System.out.println("new max");
				//
				maxRange = j.x - cows[im1.i+1].x;
			} 
			/*
			if (Math.abs(j.x - cows[im1.i+1].x) > maxRange) {
				//System.out.println("new max");
				//
				maxRange = Math.abs(j.x - cows[im1.i+1].x);
			}*/
		}

	}
	public static int greatestBelow(ArrayList<Point> arr, int val) {
		// returns greatest i so that arr[i].z <= val

		int lo = -1;
		int hi = arr.size()-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if

			if (arr.get(mid).z <= val) { // if mid is in range, increase (except decrease)
				lo = mid;
			} else { // mid is not in range, decrease (except increase)
				hi = mid - 1;
			}
		}
		return hi;

	}
	public static class Point implements Comparable<Point> {
		int x; // x pos
		int y; // state
		int z; // p[i] value
		int i; // i value
		public Point(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Point o) {
			if (x == o.x) return y - o.y;
			return x-o.x;
		}
		public String toString() {
			return x + " " + y + " " + z + " " + i;
		}
	}
	public static Comparator<Point> zComparator = new Comparator<Point>(){

		@Override
		public int compare(Point a, Point b) {
			if (a.z == b.z) {
				return a.x-b.x;
			}
			return a.z-b.z;
		}
	};
}
