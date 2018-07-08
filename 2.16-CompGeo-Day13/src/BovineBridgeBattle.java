import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 10/10 test cases
 * 
 * 4 cows must form a parallelogram (or be collinear and work)
 * 2 segments must have the same midpoint
 * A+D = B+C
 * 
 * 
 * 1. get the midpoints of every pair (N^2)
 * 2. count duplicates, put them into a map <Pair midpoint, int occurrences> O(N^2 log N <--for map)
 * 3. go through the map once and just do the occurrences choose 2 for every thing 
 * 
 * O(N^2 log N)
 * 
 * use treemap, not hashmap, because hashmap is BAD
 */

public class BovineBridgeBattle {
	static Pair[] pts;
	public static void main(String args[]) throws IOException {
		// scan in points
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // num cows
		pts = new Pair[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			pts[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		Map<Pair, Integer> map = new TreeMap<Pair, Integer>(); // hashmap
		
		// loop through points (combinations)
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				Pair mp = midpt(i, j); // midpoint
				
			//	System.out.println(mp);
				
				if (map.containsKey(mp)) { // key already present
				//	System.out.println("HERE1");
					map.put(mp, map.get(mp) + 1); // increment
				} else { // key not present
					map.put(mp, 1); // make it 1
				}
			}
		}
		
		//System.out.println(map);
		
		int count = 0;
		
		Iterator<Pair> it = map.keySet().iterator();
		while (it.hasNext()) {
			Pair p = it.next();
			int ct = map.get(p);
			if (ct > 1) { // if there's more than 1 occurrence
				count += (ct*(ct-1)/2);
				
			}
		}
		System.out.println(count);
		
	}
	public static Pair midpt(int i, int j) {
		// returns midpt of pts[i] and pts[j]
		Pair p1 = pts[i];
		Pair p2 = pts[j];
		
		return new Pair(((double)(p1.x + p2.x))/2 , ((double)(p1.y + p2.y))/2);
		
	}
	public static class Pair implements Comparable<Pair> {
		double x; // value
		double y; // amt

		public Pair(double a, double b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by value
			if (x == o.x) {
				if (y > o.y) {
					return 1;
				} else if ( y == o.y) {
					return 0;
				} else {
					return -1;
				}
			}
			if (x > o.x) {
				return 1;
			} else if ( x == o.x) {
				return 0;
			} else {
				return -1;
			}
		}
		public String toString() {
			return x + " " + y;
		}
		public boolean equals(Pair o ) {
			return (x == o.x && y == o.y);
		}

	}
}
