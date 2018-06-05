import java.util.*;
import java.io.*;

// all 15 test cases correct

public class HighCardWins {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("highcard.in"));
		int N = scan.nextInt(); // number of P2's cards
		int[] P1 = new int[N];
		int[] P2 = new int[N];
		int ind = 0;
		
		// scan in P2's cards and sort them
		for (int i = 0; i < N; i++) 
			P2[i] = scan.nextInt();
		Arrays.sort(P2);
		
		// find P1's cards and sort them
		for (int i = 1; i <= 2*N; i++) {
			if (Arrays.binarySearch(P2, i) < 0) { // if P2 doesn't have the card, give it to P1
				P1[ind] = i;
				ind++;
			}
		}
		Arrays.sort(P1);
		
		int indP1 = 0;
		int indP2 = 0;
		
		int wincount = 0; // number of times P1 wins
		
		while (indP1 < N && indP2 < N) {
			if (P1[indP1] > P2[indP2]) { // if P1 can beat P2 with the current card
				wincount++;
				indP1++;
				indP2++;
			} else { // otherwise, go to P1's next card and hope that it can beat P2's card
				indP1++;
			}
		}
		//System.out.println(wincount);
		PrintWriter pw = new PrintWriter(new File("highcard.out"));
		pw.println(wincount);
		pw.close();
	}
}
