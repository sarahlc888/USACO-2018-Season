import java.io.*;
import java.util.*;

/*
 * 10/10 test cases
 * BFS, can't store entire grid. map <Pair, boolean> for visited array
 * get the top left bale, add the first box.
 * go to 8 neighbors, check that neighbor's 4 neighbors to make sure it's next to a bale
 * 
 */

public class Perimeter {
	static int[] dx4 = {0, 0, 1, -1};
	static int[] dy4 = {1, -1, 0, 0};
	static int[] dx8 = {0, 0, 1, -1, -1, +1, -1, +1};
	static int[] dy8 = {1, -1, 0, 0, -1, +1, +1, -1};
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // num bales
		TreeSet<Pair> balesSet = new TreeSet<Pair>();
		
		Pair[] bales = new Pair[N];
		for (int i = 0; i < N; i++) { // scan in bales
			StringTokenizer st = new StringTokenizer(br.readLine());
			Pair p = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			bales[i] = p;
			balesSet.add(p);
		}
		Arrays.sort(bales);
		//System.out.println(Arrays.toString(bales));

		// visited bales in BFS
		//Map<Pair, Boolean> visited = new TreeMap<Pair, Boolean>();
		TreeSet<Pair> visited = new TreeSet<Pair>();
		
		
		
		

		Pair start = new Pair(bales[0].x, bales[0].y+1); // starting pos

		LinkedList<Pair> toVisit = new LinkedList<Pair>();	
		toVisit.add(start);
		visited.add(start); // mark start visited


		

		int hasBales = 0; // number of bales next to the start
		// loop on all sides of the starting point
		for (int j = 0; j < 4; j++) {
			int nnx = start.x + dx4[j];
			int nny = start.y + dy4[j];

			if (balesSet.contains(new Pair(nnx, nny))) {
				// if that is a bale
				hasBales++;
			}
		}
		int count = hasBales; // starting point
		
		
		while (!toVisit.isEmpty()) {
			//count++; // increment perim length
			Pair cur = toVisit.removeFirst(); // current pos

			//System.out.println(cur);
			
			for (int i = 0; i < 8; i++) { // loop through all other positions
				int nx = cur.x+dx8[i];
				int ny = cur.y+dy8[i];
				Pair n = new Pair(nx, ny);
				
				if (visited.contains(n) || balesSet.contains(n)) continue;
				
				// if the next position is not visited and not a bale
					

				// check if the next position has a bale up down left right of it
				hasBales = 0;
				for (int j = 0; j < 4; j++) {
					int nnx = nx + dx4[j];
					int nny = ny + dy4[j];

					if (balesSet.contains(new Pair(nnx, nny))) {
						// if that is a bale
						hasBales++;
					}
				}

				if (i >= 4) { // if you're moving diagonally
					Pair p1 = new Pair(cur.x+dx8[i], cur.y);
					Pair p2 = new Pair(cur.x, cur.y+dy8[i]);
					if (balesSet.contains(p1) && balesSet.contains(p2)) {
						hasBales = 0; // set to 0 so it won't work
					}
				}
				
				if (hasBales > 0) { // if there is a bale
					
					
					
					toVisit.add(n); // add to tovisit
					visited.add(n); // mark visited
					count+= hasBales; // increment perim length
					
				}
			}
		}
		System.out.println(count);

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
				return o.y-y;
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
}
