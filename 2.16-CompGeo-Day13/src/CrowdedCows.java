import java.io.*;
import java.util.*;

/*
 * for xi to xi+d
 * find hj > 2hi
 * 
 * sweep through, keep a priority queue, poll if the head is outdated
 * 
 * 10/10
 */
public class CrowdedCows {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num cows
		int D = Integer.parseInt(st.nextToken()); // distance

		Pair[] cows = new Pair[N]; // cows

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int pos = Integer.parseInt(st.nextToken()); // position
			int height = Integer.parseInt(st.nextToken()); // height
			cows[i] = new Pair(pos, height);
		}

		Arrays.sort(cows, pComparator); // sort by position
		//System.out.println(Arrays.toString(cows));
		int[] crowded = new int[N]; // stores if cow i is crowded

		
		// go left
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();

		
		for (int i = 0; i < N; i++) {
			// loop through the cows
			Pair curCow = cows[i];
			//System.out.println(curCow);
			//System.out.println("  " + pq);

			while (!pq.isEmpty() && pq.peek().y >= 2*curCow.y) {
				// while the cow is at least twice her height
				if (pq.peek().x + D >= curCow.x) {
					// if it's within distance D
					crowded[i]++;
					break;
				} else {
					pq.poll();
				}
			}

			pq.add(curCow);
		}

		// go right
		pq = new PriorityQueue<Pair>();

		for (int i = N-1; i >= 0; i--) {
			// loop through the cows
			Pair curCow = cows[i];

			while (!pq.isEmpty() && pq.peek().y >= 2*curCow.y) {
				// while the cow is at least twice her height
				if (pq.peek().x <= curCow.x + D) {
					// if it's within distance D
					crowded[i]++;
					break;
				} else {
					pq.poll();
				}
			}

			pq.add(curCow);
		}
		int count = 0;
		for (int i = 0; i < N; i++) {
			if (crowded[i] == 2) count++;
		}
		//System.out.println(Arrays.toString(crowded));
		System.out.println(count);
	}
	public static class Pair implements Comparable<Pair> {
		int x; // value
		int y; // amt

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		
		public String toString() {
			return  x + " " + y ;
		}
		public boolean equals(Pair o) {
			return (x == o.x && y == o.y);
		}

		@Override
		public int compareTo(Pair o) { // sort by height
			return o.y-y;
		}
	}
	public static Comparator<Pair> pComparator = new Comparator<Pair>(){
		// sort by pos
		@Override
		public int compare(Pair m, Pair o) {
			return m.x-o.x;
		}
	};
	
}
