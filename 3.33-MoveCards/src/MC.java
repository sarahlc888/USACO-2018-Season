import java.io.*;
import java.util.*;
/*
 * 10/16 class, see lesson screenshots folder P2 for problem statement
 * sweeping algo
 * not tested but should be working
 */
public class MC {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("MC.in"));
		int N = Integer.parseInt(br.readLine()); // number of piles
		int[] cards = new int[N]; // cards in each pile
		int totcards = 0;
		for (int i = 0; i < N; i++) {
			cards[i] = Integer.parseInt(br.readLine());
			totcards += cards[i];
		}
		br.close();

		int target = totcards/N; // should always be a whole number
		for (int i = 0; i < N; i++) {
			cards[i] -= target;
		}
		System.out.println(Arrays.toString(cards));
		
		// find min number of moves to even out the piles (can only move cards to adjacent piles)
		// loop through totcards and move cards (can move negatives to signify moving from B to A)
		int moves = 0;
		for (int i = 0; i < N-1; i++) {
			if (cards[i] == 0) // no cards at that spot, so no movement
				continue;
			
			cards[i+1] += cards[i]; // else move cards to the next spot
			moves++;
		}
		System.out.println(moves);
		
//		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("WIL.out")));
//		pw.println();
//		pw.close();
	}
}
