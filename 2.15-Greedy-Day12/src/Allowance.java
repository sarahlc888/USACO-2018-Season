import java.io.*;
import java.util.*;

public class Allowance {
	static long totalSum = 0;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num types of coins
		int C = Integer.parseInt(st.nextToken()); // min money
		Pair[] coins = new Pair[N];
		
		int ind = 0;
		
		while (ind < N) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
		
			coins[ind] = new Pair(a, b);
			
			totalSum += (long)coins[ind].x * (long)coins[ind].y;
			ind++;
		}
		Arrays.sort(coins);
		//System.out.println(Arrays.toString(coins));
		
		// index of the first and last values that still have coins
		int first = 0;
		int last = N-1;
		// TODO: make sure first <= last
		
		int count = 0;
		
//		System.out.println(totalSum);
//		System.out.println(totalSum >= C);
		
		while (true) { 

			if (totalSum < C) { // while there's still enough total money
				/*System.out.println("here1");
				for (int i = 0; i < N; i++) {
					if (coins[i].y != 0) {
						System.out.print(coins[i]+ " , ");
					}
				}
				System.out.println();*/
				break;
			}
			if (first > last) {
				//System.out.println("here2");
				break;
			}
			
			int sum = 0; // current sum

			// take the biggest coin so that sum < C
			while (first <= last && coins[last].y > 0) {
				if (sum + coins[last].x > C && sum != 0) break;
				
				
				sum += coins[last].x; 
				totalSum -= coins[last].x; 
				coins[last].y--; 
				if (coins[last].y == 0) {
					last--;
					//System.out.println("last: " + last);
				}
			}
			//System.out.println("  sum: " + sum);
			
			// take the smallest coin until sum >= C
			while (first <= last && coins[first].y > 0 && sum < C) {
				
				
				sum += coins[first].x; 
				totalSum -= coins[first].x; 
				coins[first].y--; 
				if (coins[first].y == 0) {
					first++;
					//System.out.println("first: " + first);
				}
			}
			//System.out.println("  sum: " + sum);

			count++;
			//System.out.println();
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
			return x-o.x;
		}
		public String toString() {
			return x + " " + y;
		}

	}
}

