import java.io.*;
import java.util.*;
/*
 * USACO 2014 December Contest, Gold
 * Problem 3. Cow Jog
 *
 * had to look at solution
 * naive way times out, gets 9/14 cases
 */
public class CowJog {
	static int N;
	static int[] start;
	static int[] speed;
	static int[] end;
	static int count = 0;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowjog.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num cows
		int T = Integer.parseInt(st.nextToken()); // num minutes
		
		start = new int[N];
		end = new int[N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			start[i] = Integer.parseInt(st.nextToken()); 
			int speed = Integer.parseInt(st.nextToken()); 
			end[i] = start[i] + T*speed;
		}
		br.close();

//		System.out.println(Arrays.toString(start));
//		System.out.println(Arrays.toString(speed));
//		System.out.println(Arrays.toString(end));
		
		int lanes = 1;

		while (count < N) {
			
			
			int curmax = 0;
			boolean ret = true;
			for (int i = 0; i < start.length; i++) {
				if (start[i] == -1) continue; // if marked invalid, skip it
//				System.out.println(Arrays.toString(start));
//				System.out.println("i: " + i);
				if (end[i] <= curmax) {
//					System.out.println("   intersection");
					ret = false;
				} else {
//					System.out.println("   pass");
					curmax = end[i];
					start[i] = -1; // mark visited
					count++;
				}
			}
			
			
			if (!ret) { // if there is an intersection
				lanes++;
			} else {
				break;
			}
		}
//		System.out.println(lanes);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));

		pw.println(lanes);
		pw.close();
	}
	
	public static int countIntersect(int[] curstart, int[] curend) {
		int curmax = 0;
		int count = 0;
		for (int i = 0; i < curstart.length; i++) {
			if (curstart[i] == -1) continue; // if marked invalid, skip it
			if (curend[i] <= curmax) {
				count++;
			} else {
				curmax = curend[i];
			}
		}
		return count;
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
