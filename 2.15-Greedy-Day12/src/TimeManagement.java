import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 10/10 test cases
 * O(N log N)
 * binary search from 0 to ending pt of 1st task
 * OR what I actually implemented - do works(0)
 * and then get the min diff between deadline and time you finished
 */
public class TimeManagement {
	static int N;
	static Pair[] tasks;
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // num tasks
		tasks = new Pair[N]; 
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int T = Integer.parseInt(st.nextToken()); // time needed
			int S = Integer.parseInt(st.nextToken()); // deadline
			tasks[i] = new Pair(T, S);
		}
		Arrays.sort(tasks);
		//System.out.println(Arrays.toString(tasks));

		// binary search for the latest time he can start working

		int minSpareTime = Integer.MAX_VALUE; // min diff between time and deadline
		
		int time = 0; // current time
		for (int i = 0; i < N; i++) { // loop through tasks
			time += tasks[i].x; // time after you finish task i
			if (time <= tasks[i].y) {
				int diff = tasks[i].y-time;
				minSpareTime = Math.min(diff,  minSpareTime);
			} else { // can't finish the task in time
				System.out.println(-1);
				return;
			}
		}

		System.out.println(minSpareTime);
	}

	
	public static class Pair implements Comparable<Pair> {
		int x; // time needed
		int y; // deadline
		int z; // needed start time

		public Pair(int a, int b) {
			x = a;
			y = b;
			z = y-x;
		}
		@Override
		public int compareTo(Pair o) { // sort by deadline, then time
			if (y == o.y) return x-o.x;
			return y-o.y;
		}
		public String toString() {
			return x + " " + y;
		}

	}
}
