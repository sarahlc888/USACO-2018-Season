import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * greedy
 */
public class A2 {
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
			
			int sum = 0; // current sum

			// take the biggest coin so that sum < C
			for (int i = N-1; i >= 0; i--) { // loop from back
				
				while (coins[i].y > 0) { // while still coins
					if (sum + coins[i].x > C && sum != 0) break; // if would be over min, end
					sum += coins[i].x; 
					totalSum -= coins[i].x; 
					coins[i].y--; 
				}
			}
			
			//System.out.println("  sum: " + sum);
			
			// take the smallest coin until sum >= C
			for (int i = 0; i < N; i++) { // loop from front

				while (coins[i].y > 0) { // while still coins
					if (sum >= C) break; // if over min, end
					sum += coins[i].x; 
					totalSum -= coins[i].x; 
					coins[i].y--; 
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

