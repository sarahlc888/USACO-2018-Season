import java.io.*;
import java.util.*;
/*
 * copied from 1.54 but optimized
 * 
 * USACO 2017 January Contest, Gold
 * Problem 2. Hoof, Paper, Scissors
 * 
 * 10/10 test cases
 * 
 * O(2*K*3) because optimized (2 instead of N)
 */
public class HoofPaperScissors {
	static int max;
	static int N;
	static int K;
	static int[][] DP1;
	static int[][] DP2;

	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
		// [games][swaps][state] = max wins in 1...games with swaps swaps

		int ind = 0; // ind for DP1, go from 0...N-1 so DP2 goes from 1...N

		DP1 = new int[K+1][3];
		DP2 = new int[K+1][3];


		// basecases = 0, so no need to declare
		

		// bottom up - go FORWARD
		// array DP[game #][# of switches][current play] = Math.max(original, new # of wins)
		while (ind < N) { // same as i goes from 1...N

			for (int j = 0; j <= K; j++) { // number of switches
				for (int k = 0; k < 3; k++) { // play
					// two cases: switch or don't switch
					//System.out.println("j: " + j + " i: " + i + " k: " + k);

					// don't switch
					// DP[i][j][k] = DP[i-1][j][k]
					int newval = DP1[j][k]; // previous i, otherwise identical
					if (k == beats(opp[ind+1])) {
						newval++;
					}
					//System.out.println("  new: " + newval);
					DP2[j][k] = Math.max(newval, DP2[j][k]);

					if (j == 0) continue; // if there's no switches to do, skip the next part
					// switch (EVEN if it doesn't win)
					// DP[i][j][k] = DP[i-1][j-1][k]
					// previous i, new j, change to what beats, increment wins
					// takes the larger of the two possible previous types of moves

					newval = Math.max(DP1[j-1][fitMove(k+1)], DP1[j-1][fitMove(k+2)]); 
					if (k == beats(opp[ind+1])) {
						newval++;
					}					
					DP2[j][k] = Math.max(newval, DP2[j][k]);


				}
			}
			for (int i = 0; i <= K; i++) { // shift over
				DP1[i] = DP2[i];
			}
			ind++;
		}

	int maxval = 0;

	for (int k = 0; k < 3; k++) {
		if (DP2[K][k] > maxval) maxval = DP2[K][k];
	}

	System.out.println(maxval);

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
