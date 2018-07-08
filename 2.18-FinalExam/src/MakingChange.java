import java.io.*;
import java.util.*;
/*
 * greedy, naive, 8/10, final 2 are harder
 */
public class MakingChange {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int C = Integer.parseInt(st.nextToken()); // target val
		int N = Integer.parseInt(st.nextToken()); // num coins
		
		int[] coins = new int[N];
		for (int i = 0; i < N; i++) {  
			coins[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(coins);
		//System.out.println(Arrays.toString(coins));
		
		int last = N-1;
		
		int count = 0;
		int sum = C;
		while (sum > 0 && last >= 0) {
			// while you still need more coins
			if (sum >= coins[last]) { // if sum is greater than the biggest coin
				//System.out.println("here");
				int num = sum/coins[last]; // add it
				count += num;
				sum -= num*coins[last];
			} else {
				last--;
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
