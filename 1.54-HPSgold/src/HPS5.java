import java.io.*;
import java.util.*;
/*
 * USACO 2017 January Contest, Gold
 * Problem 2. Hoof, Paper, Scissors
 * 
 * 10/10 test cases
 */
public class HPS5 {
	static int max;
	static int N;
	static int K;
	static int[][][] DP;
	
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new FileReader("hps.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/3.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // number of games
		K = Integer.parseInt(st.nextToken()); // number of switches, <= 20
		
		int[] opp = new int[N+1]; // FJ's moves; H = 0, P = 1, S = 2
		for (int i = 1; i <= N; i++) {
			String move = (br.readLine());
			if (move.equals("H")) opp[i] = 0;
			else if (move.equals("P")) opp[i] = 1;
			else opp[i] = 2;	
		}
		br.close();
		
		DP = new int[N+1][K+1][3];
		
		// basecases = 0, so no need to declare

		// precalculate all cases as having no switches
		for (int i = 1; i <= N; i++) {
			for (int k = 0; k < 3; k++) {
				// assign current state as the same as last round, no changes to j
				DP[i][0][k] = DP[i-1][0][k];

				// if the current state wins, increment wins
				if (k == beats(opp[i])) {
					DP[i][0][k]++;
				}
			}
		}
		
		// bottom up - go FORWARD
		// array DP[game #][# of switches][current play] = Math.max(original, new # of wins)
		
		for (int i = 1; i <= N; i++) { // round
			for (int j = 1; j <= K; j++) { // number of switches
				for (int k = 0; k < 3; k++) { // play
					// two cases: switch or don't switch
					//System.out.println("j: " + j + " i: " + i + " k: " + k);

					// don't switch
					// DP[i][j][k] = DP[i-1][j][k]
					int newval = DP[i-1][j][k]; // previous i, otherwise identical
					if (k == beats(opp[i])) {
						newval++;
					}
					//System.out.println("  new: " + newval);
					DP[i][j][k] = Math.max(newval, DP[i][j][k]);
					
					// switch (EVEN if it doesn't win)
					// DP[i][j][k] = DP[i-1][j-1][k]
					// previous i, new j, change to what beats, increment wins
					// takes the larger of the two possible previous types of moves
					
					newval = Math.max(DP[i-1][j-1][fitMove(k+1)], DP[i-1][j-1][fitMove(k+2)]); 
					if (k == beats(opp[i])) {
						newval++;
					}					
					DP[i][j][k] = Math.max(newval, DP[i][j][k]);
					

				}
			}
		}
		
		int maxval = 0;
		
		for (int k = 0; k < 3; k++) {
			if (DP[N][K][k] > maxval) maxval = DP[N][K][k];
		}
		
		System.out.println(maxval);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));

		pw.println(maxval);
		pw.close();
	}
	public static boolean wins(int a, int b) {
		// returns if a beats b
		// a tie is still false
		if (a == 1 && b == 0) return true;
		else if (a == 2 && b == 1) return true;
		else if (a == 0 && b == 2) return true;
		else return false;	
	}
	public static int beats(int a) {
		// returns the int that beats a
		return (a+1)%3;
	}
	public static int loses(int a) {
		// returns the int that loses to a
		int l = (a-1)%3;
		if (l < 0) l += 3;
		return l;
	}
	public static void printDP() {
		
		for (int j = 0; j <= K; j++) {
			for (int i = 0; i <= N; i++) {
			
				for (int k = 0; k < 3; k++) {
					//System.out.print(DP[i][j][k] + " ");
				}
				//System.out.println();
			}
			//System.out.println();
		}
	}
	public static int fitMove(int a) {
		// fits a into a valid move
		a = a%3;
		if (a < 0) a += 3;
		return a;
	}

}
