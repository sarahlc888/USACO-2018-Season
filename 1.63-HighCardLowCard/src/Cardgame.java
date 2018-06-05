import java.io.*;
import java.util.*;
/*
 * USACO 2015 December Contest, Gold
 * Problem 1. High Card Low Card (Gold)
 * 
 * Greedy algorithm
 */
public class Cardgame {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("cardgame.in"));
		int N = Integer.parseInt(br.readLine()); // number of cards
		int[] arr1 = new int[N]; // P1 cards
		boolean[] arrB = new boolean[2*N+1]; // all card vals
		for (int i = 0; i < N; i++) {
			arr1[i] = Integer.parseInt(br.readLine());
			arrB[arr1[i]] = true;
		}
		br.close();

		int[] arr2 = new int[N]; // P2 cards
		int ind = 0;
		for (int i = 1; i < arrB.length; i++) {
			if (!arrB[i]) { // if not in P1 cards 
				arr2[ind] = i;
				ind++;
			}
		}
		// sort P2 array, split into the low and high groups
		// don't sort P1 cards because they're already in their set order
		
		Arrays.sort(arr2);
		
		int[] lo1 = new int[N/2]; // P1 lo cards
		int[] hi1 = new int[N/2]; // P1 hi cards
		int[] lo2 = new int[N/2]; // P2 lo cards
		int[] hi2 = new int[N/2]; // P2 hi cards
		
		for (int i = 0; i < N/2; i++) {
			hi1[i] = arr1[i]; // P1 already has a set order, hi is the first round
			lo1[i] = arr1[N/2+i]; // lo is the second
			lo2[i] = arr2[N/2-1-i];
			hi2[i] = arr2[N/2+i];
		}
		/*
		System.out.print(Arrays.toString(lo1));
		System.out.println(Arrays.toString(hi1));
		System.out.print(Arrays.toString(lo2));
		System.out.println(Arrays.toString(hi2));
		*/
		// cards should go from weaker to stronger, so lo group should be sorted hi to lo
		
		
		// sort lo1 and hi1 separately
		Arrays.sort(lo1);
		Arrays.sort(hi1);
		int[] lo11 = new int[N/2]; // P1 lo cards
		for (int i = 0; i < N/2; i++) {
			lo11[i] = lo1[N/2-1-i];
		}
		
		
		// determine winners in the low and high groups
		int wins = 0; // count when P2 beats P1
		
		int ind1 = 0;
		int ind2 = 0;

		
		while (ind1 < N/2 && ind2 < N/2) {
			// step through the arrays
			if (lo2[ind2] < lo11[ind1]) { // if cow 2 wins
				//System.out.println("HERE ind1: " + ind1 + " ind2: " + ind2);
				wins++;
				ind1++;
				ind2++;
			} else { // if cow 1 would win, skip P2 card! 
				//System.out.println("ind1: " + ind1 + " ind2: " + ind2);
				// the low P1 cards are the easiest to beat, 
				// so if P2 can't beat that with the current card,
				// it should increment so that it can
				ind2++;
			}
		}
		//System.out.println("loop 2");
		ind1 = 0;
		ind2 = 0;
		while (ind1 < N/2 && ind2 < N/2) {
			// step through the arrays
			if (hi2[ind2] > hi1[ind1]) { // if cow 2 wins
				//System.out.println("HERE ind1: " + ind1 + " ind2: " + ind2);
				wins++;
				ind1++;
				ind2++;
			} else { // if cow 1 would win, skip P2 card! 
				//System.out.println("ind1: " + ind1 + " ind2: " + ind2);
				ind2++;
			}
		}
		
		//System.out.println(wins);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cardgame.out")));

		pw.println(wins);
		pw.close();
	}


}
