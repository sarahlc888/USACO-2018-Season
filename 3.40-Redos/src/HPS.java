import java.io.*;
import java.util.*;
/*
 * USACO 2017 January Contest, Gold
 * Problem 2. Hoof, Paper, Scissors
 */
public class HPS {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("hps.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num plays
		int K = Integer.parseInt(st.nextToken()); // num switches allowed
		int[] opp = new int[N];
		for (int i = 0; i < N; i++) 	{
			String play = br.readLine();
			if (play.equals("H")) opp[i] = 0;
			else if (play.equals("P")) opp[i] = 1;
			else if (play.equals("S")) opp[i] = 2;
		}
		br.close();

		// HPS = 0123
		int maxWins = 0;
		int[][][] DP = new int[N][K+1][3]; // [last play][switches made][play]
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= K; j++) {
				for (int k = 0; k < 3; k++) {
					DP[i][j][k] = -1; // init
				}
			}
		}
		// basecases for first play
		for (int j = 0; j <= K; j++) {
			for (int k = 0; k < 3; k++) {
				DP[0][j][k] = beats(k, opp[0]);
				maxWins = Math.max(maxWins, DP[0][j][k]);
			}
		}
		// basecases for no switching
		for (int i = 1; i < N; i++) {
			for (int k = 0; k < 3; k++) {
				DP[i][0][k] = DP[i-1][0][k] + beats(k, opp[i]);
				maxWins = Math.max(maxWins, DP[i][0][k]);
			}
		}
		
		
		// doing the switches forward from i j k
		for (int i = 0; i < N-1; i++) {
			for (int j = 0; j <= K; j++) {
				for (int k = 0; k < 3; k++) {
					// current state is DP[i][j][k], transition to the next state
					
					if (DP[i][j][k] == -1) {
						System.out.println("ERROR " + i + " " + j + " " + k);
						continue;
					}
					// no switch
					DP[i+1][j][k] = Math.max(DP[i][j][k]+beats(k, opp[i+1]), DP[i+1][j][k]);
					
					maxWins = Math.max(maxWins, DP[i+1][j][k]);
					if (j < K) {
						// switch 1
						DP[i+1][j+1][(k+1)%3] = Math.max(DP[i][j][k] + beats((k+1)%3, opp[i+1]), 
									DP[i+1][j+1][(k+1)%3]);
						maxWins = Math.max(maxWins, DP[i+1][j+1][(k+1)%3]);
						
						// switch 2
						
						DP[i+1][j+1][(k+2)%3] = Math.max(DP[i][j][k] + beats((k+2)%3, opp[i+1]), 
									DP[i+1][j+1][(k+2)%3]);
						maxWins = Math.max(maxWins, DP[i+1][j+1][(k+2)%3]);
					}
					
				}
			}
		}
			
		/*
		// doing the switches (going up to i j k)
		for (int i = 1; i < N; i++) { // game
			for (int j = 1; j <= K; j++) { // switches
				for (int k = 0; k < 3; k++) { // play
					// transition to state DP[i][j][k]
					
					// no switch
					int newVal = DP[i-1][j][k] + beats(k, opp[i]);
					if (newVal > DP[i][j][k]) DP[i][j][k] = newVal;
					
					// switch
					
					int a1 = DP[i-1][j-1][(k+1)%3] + beats((k+1)%3, opp[i]);
					int b1 = DP[i-1][j-1][(k+2)%3] + beats((k+2)%3, opp[i]);
					newVal = Math.max(a1, b1);
					if (newVal > DP[i][j][k]) DP[i][j][k] = newVal;
					
					maxWins = Math.max(maxWins, DP[i][j][k]);
					
				}
			}
		}*/
//		for (int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(DP[i][K]));
//		}
//		System.out.println(maxWins);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));

		pw.println(maxWins);
		pw.close();
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
	public static int beats(int a, int b) { // HPS = 012
		if ((a+1)%3==b) return 1;
		return 0;
	}
}
