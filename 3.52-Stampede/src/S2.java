import java.io.*;
import java.util.*;
/*
 * USACO 2015 January Contest, Silver
 * Problem 1. Stampede
 * 
 * 15/15
 * sweepline to detect overlapping intervals
 */
public class S2 {
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
		
		
		PriorityQueue<Event> timeline = new PriorityQueue<Event>();
		for (int i = 0; i < N; i++) {
			Cow cur = arr[i];
			timeline.add(new Event(cur.y, cur.t1, true, cur));
			timeline.add(new Event(cur.y, cur.t2, false, cur));
		}
		// go through all events
//		ArrayList<Cow> active = new ArrayList<Cow>();
		boolean[] seen = new boolean[N];
		int seenCount = 0;
		PriorityQueue<Cow> active = new PriorityQueue<Cow>(YComp);
		while (!timeline.isEmpty()) {
			ArrayList<Event> todo = new ArrayList<Event>();
			todo.add(timeline.poll());
			while (!timeline.isEmpty() && timeline.peek().t == todo.get(0).t) {
				todo.add(timeline.poll());
			}
			
			for (int i = 0; i < todo.size(); i++) {
				Event curEvent = todo.get(i);
				if (curEvent.start) active.add(curEvent.curCow);
				else active.remove(curEvent.curCow);
			}
			
			if (active.size() > 0 && !seen[active.peek().id]) {
				seen[active.peek().id] = true;
				seenCount++;
			}
			
			
			
		}
		
//		System.out.println(seenCount);
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
	public static Comparator<Cow> YComp = new Comparator<Cow>(){
		@Override
		public int compare(Cow x, Cow y) {
			if (x.y < y.y) return -1;
			if (x.y > y.y) return 1;
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
	public static class Event implements Comparable<Event> {
		int y;
		long t;
		boolean start; // if it is to add to the stack
		Cow curCow;
		public Event(int a, long b, boolean c, Cow d) {
			y = a;
			t = b;
			start = c;
			curCow = d;
		}
		
		@Override
		public int compareTo(Event o) { // sort by t
			if (t < o.t) return -1;
			if (t > o.t) return 1;
			if (start && !o.start) return -1;
			if (!start && o.start) return 1;
			return 0;
		}
		public String toString() {
			return t + " " + y;
		}
	}

}
