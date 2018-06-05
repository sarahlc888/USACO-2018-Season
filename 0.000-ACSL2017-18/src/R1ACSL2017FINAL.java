/*
 * Sarah Chen
 * Phillips Academy
 * Senior-3 Contest #1
 */

// PASTE THE TEST DATA INTO THE CONSOLE IN A BLOCK

import java.io.*;
import java.util.*;

public class R1ACSL2017FINAL {
	static int[] P1C;
	static int[] P2C;
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
		try
		{
			String line = scan.nextLine();

			String[] tokens = line.split("\\s*,\\s*");

			P1C = new int[5]; // player 1 cards
			P2C = new int[5]; // player 2 cards

			for (int i = 0; i < 5; i++) {
				// turn tokens into integers
				P1C[i] = cardVal(tokens[i]);
			}
			for (int i = 0; i < 5; i++) {
				P2C[i] = cardVal(tokens[i+5]);
			}
			Arrays.sort(P1C);
			Arrays.sort(P2C);

		}
		catch (Exception e)
		{
			System.out.println("Exception in processing: line #1");
		}
		// Suppose you are given 5 input lines and you need 
		// to process them the same way and print the result for each line...

		for (int k = 1; k <= 5; k++)
		{
			try
			{
				String line = scan.nextLine();
				String[] tokens = line.split("\\s*,\\s*");
				System.out.println(processGame(tokens));	// process the current line's game
				
			}
			catch (Exception e)
			{
				System.out.println("Exception in processing: line #" + k + 1);
			}
		}
		scan.close();
	}
	public static int cardVal(String s) {
		s = s.trim();
		if (s.length() > 1) { 
			// if the string has multiple characters, it is not a letter, so convert immediately
			return Integer.parseInt(s);
		} else if (s.equals("T")) {
			return 10;
		} else if (s.equals("J")) {
			return 11;
		} else if (s.equals("Q")) {
			return 12;
		} else if (s.equals("K")) {
			return 13;
		} else if (s.equals("A")) {
			return 14;
		} else {
			return Integer.parseInt(s);
		}
	}
	public static int processCard(int card, int curPoints) {
		// return the point differential after the player plays the given card
		
		int diff = 0;
		
		// special cases
		if (card == 9) {
			diff = 0; // pass
		} else if (card == 10) {
			diff = -10;
		} else if (card == 7) {
			if (curPoints + 7 > 99) {
				diff = 1; // if 7 puts the point total over 99
			} else {
				diff = 7;
			}
		} else {
			diff = card;
		}
		
		// account for crossing point total borders
		// positive diff or negative diff
		if (diff > 0 && (curPoints <= 33 && curPoints + diff >= 34 || curPoints <= 55 && curPoints + diff >= 56 
				|| curPoints <= 77 && curPoints + diff >= 78)) {
			diff += 5;
		} else if (diff < 0 && (curPoints >= 34 && curPoints + diff <= 33 || curPoints >= 56 && curPoints + diff <= 55
				|| curPoints >= 78 && curPoints + diff <= 77)) {
			diff += 5;
		}
		
		return diff;
		
	}
	public static String processGame(String[] tokens) {
		//System.out.println("NEW GAME");
		// local P1C and P2C to modify as the game goes on
		int[] P1CUR = P1C.clone();
		int[] P2CUR = P2C.clone();
		/*
		System.out.println("  P1");
		for (int i = 0; i < P1CUR.length; i++) {
			System.out.println("  " + P1CUR[i]);
		}
		System.out.println("  P2");
		for (int i = 0; i < P1CUR.length; i++) {
			System.out.println("  " + P2CUR[i]);
		}
		*/
		int points = Integer.parseInt(tokens[0]);
		
		if (points > 99) { // default no winner
			return (points + ", initial val > 99");
		}
		
		int tokenIndVal = 1;
		for (int i = 0; i < 12; i++) {
			// 10 turns total (each player gets 5)
			
			int nextCard = 0; // the next card doesn't matter for i == 10 and i == 11
			if (tokenIndVal < tokens.length) { // get the next card ready to go
				nextCard = cardVal(tokens[tokenIndVal]);
				tokenIndVal++;
			}
				
			// even means P1 turn
			boolean P1turn = false;
			if (i%2 == 0) {
				P1turn = true;
			}
			
			// for P1 or P2, so the array, play the median card, and draw the next card
			if (P1turn) {
				Arrays.sort(P1CUR);
				points += processCard(P1CUR[2], points);
				P1CUR[2] = nextCard;
			} else {
				Arrays.sort(P2CUR);
				points += processCard(P2CUR[2], points);
				P2CUR[2] = nextCard;
			}
			
			// if points > 99, select the proper winner
			if (points > 99) {
				if (P1turn) {
					return (points + ", Player #" + 2);
				} else {
					return (points + ", Player #" + 1);
				}
			}
		}
		return "NO WINNER";
	}
}