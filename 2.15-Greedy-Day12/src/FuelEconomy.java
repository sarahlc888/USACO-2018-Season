import java.io.*;
import java.util.*;

/*
 * 4/10 test cases
 * 
 */
public class FuelEconomy {
	static int N;
	static int G;
	static int B;
	static int D;
	static Pair[] stations;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num fuel stations
		G = Integer.parseInt(st.nextToken()); // max tank capacity
		B = Integer.parseInt(st.nextToken()); // starting fuel amt
		D = Integer.parseInt(st.nextToken()); // length of route
		stations = new Pair[N+2]; // include the starting point, add a dest

		// add start and end points, don't give them gas prices
		stations[0] = new Pair(0, Integer.MAX_VALUE, 0); 
		stations[N+1] = new Pair(D, Integer.MAX_VALUE, N+1); 

		for (int i = 1; i <= N; i++) { // intervening stations
			st = new StringTokenizer(br.readLine());
			stations[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i);
		}
		Arrays.sort(stations, posComparator); // sort by position

		for (int i = 0; i <= N+1; i++) { // update ID
			stations[i].id = i;
		}
		for (int i = 0; i <= N+1; i++) {
			System.out.println(stations[i]);
		}
		//System.out.println(Arrays.toString(stations));

		// CALCS

		// calculate next array
		Stack<Pair> s = new Stack<Pair>(); // increasing stack

		int[] next = new int[N+1]; // next[i] = min j > i where price[j] <= price[i]
		Arrays.fill(next, Integer.MAX_VALUE); // default as nonexistent

		// leave the last station with next = nonexistent
		s.push(stations[N]); 

		next[N] = N+1;

		for (int i = N-1; i >= 0; i--) { 
			System.out.println("i: " + i);
			System.out.println("  s: " + s);
			if (stations[i].y > s.peek().y) {
				// if the new price is greater than the top of the stack
				System.out.println("  assign");
				next[i] = s.peek().id; // assign it
				s.push(stations[i]); // push
			} else {
				// if the new price is lower, pop
				System.out.println("  don't assign");
				while (s.size() > 0 &&
						stations[i].y < s.peek().y) {
					System.out.println("  pop");
					s.pop(); // pop off when the new one is lower
				}
				if (s.size() == 0) { // if nothing
					System.out.println("  inf");
					next[i] = Integer.MAX_VALUE; // no next
				} else {
					next[i] = s.peek().id;
				}
				s.push(stations[i]); // add current
			}
			System.out.println("  s2: " + s);
			System.out.println("  result: " + next[i]);
		}

		System.out.println(Arrays.toString(next));

		long cost = 0;

		// loop through stations, look FORWARD
		int fuel = B; // current fuel amt left
		int i = 0; // current index

		while (i <= N) {

			if (fuel < 0) { // impossible
				System.out.println(-1);
				return;
			}
			
			int n = next[i]; 

//			System.out.println("station ind: " + i);
//			System.out.println("cur fuel: " + fuel);
//			System.out.println("cur cost: " + cost);
//			System.out.println("next ind: " + n);
			// case: if there's no cheaper gas anywhere
			if (n == Integer.MAX_VALUE) {
				
				// fill up to a full tank
				int diff = G-fuel; // how much you can add
//				System.out.println("  A " + diff);
				cost += (long)stations[i].y * (long)diff;
				fuel += diff; // update fuel

				fuel -= (stations[i+1].x-stations[i].x); // update fuel
				i++;
				continue;
			}

			// case: if there's cheaper gas within range
			if (stations[i].x + fuel >= stations[n].x) { // don't get gas here
//				System.out.println("  B " );


				fuel -= (stations[n].x-stations[i].x); // update fuel
				i = n; // just go
				continue; 
			}

			// case: if there's cheaper gas outside the range
			if (stations[i].x + fuel < stations[n].x && n != Integer.MAX_VALUE) { 
				
				// get enough gas to get there
				int diff = stations[n].x - (stations[i].x + fuel); // gas needed
//				System.out.println("  C " + diff);
				cost += (long)stations[i].y * (long)diff; // price * cost
				fuel += diff; // update fuel

				fuel -= (stations[n].x-stations[i].x); // update fuel
				i = n;
				continue;
			}


		}
		System.out.println(cost);

	}
	public static Comparator<Pair> posComparator = new Comparator<Pair>(){
		// sort by position
		@Override
		public int compare(Pair h, Pair o) {
			if (h.x == o.x) return h.y-o.y;
			return h.x-o.x;
		}

	};
	public static Comparator<Pair> priceComparator = new Comparator<Pair>(){
		// sort by price
		@Override
		public int compare(Pair h, Pair o) {
			if (h.y == o.y) return h.x-o.x;
			return h.y-o.y;
		}

	};
	public static class Pair implements Comparable<Pair> {
		int x; // distance
		int y; // price
		int id; // index in stations[]

		public Pair(int a, int b, int c) {
			x = a;
			y = b;
			id = c;
		}
		@Override
		public int compareTo(Pair o) { // sort by distance, then price
			if (x == o.x) return y-o.y;
			return x-o.x;
		}
		public String toString() {
			return x + " " + y + " id: " + id;
		}

	}
}
