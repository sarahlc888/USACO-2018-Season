import java.io.*;
import java.util.*;
/*
 * USACO 2015 January Contest, Silver
 * Problem 1. Stampede
 * 
 * FAILED go to v2
 */
public class S {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("stampede.in"));
		int N = Integer.parseInt(br.readLine()); // num cows
		Cow[] arr = new Cow[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			arr[i] = new Cow(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i);
		}
		br.close();
		for (int i = 0; i < N; i++) {
			arr[i].t2 = (long)(0-arr[i].x)*(long)arr[i].r;
			arr[i].t1 = arr[i].t2-arr[i].r;
		}
//		System.out.println(Arrays.toString(arr));
		Arrays.sort(arr, T1Comp);
//		System.out.println(Arrays.toString(arr));
		
		int ind = 1; // next cow in arr to add
		PriorityQueue<Cow> pq = new PriorityQueue<Cow>(T2Comp);
		pq.add(arr[0]);
		for (int i = 1; i < N; i++) {
			if (arr[i].t1 == arr[0].t1) {
				pq.add(arr[i]);
				ind++;
			}
			else break;
		}
		boolean[] seen = new boolean[N];
		int seenCount = 0;
		while (pq.size() > 0) {
//			System.out.println("pq: " + pq + " ind: " + ind + " ct: " + seenCount);
		
			// look at current cows
			if (pq.size() != 0) {
				Iterator<Cow> it = pq.iterator();
				Cow minCow = it.next();
				while (it.hasNext()) {
					Cow cur = it.next();
					if (cur.y < minCow.y) {
						minCow = cur;
					}
				}
				if (!seen[minCow.id]) {
//					System.out.println("  seen: " + minID);
					seenCount++;
					seen[minCow.id] = true;
				}
			}
			
			// go to the next cows
			long nextTime = Long.MAX_VALUE;
			if (ind < N) {
				nextTime = Math.min(nextTime, arr[ind].t1);
			} 
			if (pq.size() > 0){
				nextTime = Math.min(nextTime, pq.peek().t2);
			} 
			if (nextTime == -1) break;
//			System.out.println("  nextTime: " + nextTime);
			// remove cows
			while (pq.size() > 0 && pq.peek().t2 <= nextTime) {
				Cow rem = pq.poll();
//				System.out.println("  remove: " + rem);
			}
			// add cows
			while (ind < N && arr[ind].t1 == nextTime) {
//				System.out.println("  add: " + arr[ind]);
				pq.add(arr[ind]);
				ind++;
			}
		}
		System.out.println(seenCount);
//		System.out.println(Arrays.toString(seen));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("stampede.out")));
		pw.println(seenCount);
		pw.close();
	}
	public static Comparator<Cow> T1Comp = new Comparator<Cow>(){
		@Override
		public int compare(Cow x, Cow y) {
			if (x.t1 < y.t1) return -1;
			if (x.t1 > y.t1) return 1;
			return 0;
		}
	};
	public static Comparator<Cow> T2Comp = new Comparator<Cow>(){
		@Override
		public int compare(Cow x, Cow y) {
			if (x.t2 < y.t2) return -1;
			if (x.t2 > y.t2) return 1;
			return 0;
		}
	};
	public static class Cow implements Comparable<Cow> {
		int x;
		int y;
		int r;
		int id;
		long t1;
		long t2;
		public Cow(int a, int b, int c, int d) {
			x = a;
			y = b;
			r = c;
			id = d;
		}
		@Override
		public int compareTo(Cow o) { // sort by t1
			if (t1 < o.t1) return -1;
			if (t1 > o.t1) return 1;
			return 0;
		}
		public String toString() {
			return t1 + " " + t2 + " y: " + y;
		}
	}


}
