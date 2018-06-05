/*
 * Sarah Chen
 * Phillips Academy
 * Senior-3 Contest #1
 * 
 * uses file input
 */

import java.io.*;
import java.util.*;

public class R1ACSL2 {
	static int[] P1C;
	static int[] P2C;
	public static void main(String[] args) throws IOException {
		//Scanner scan = new Scanner(System.in);
		Scanner scan = new Scanner(new File("acsl.in"));

		try
		{
			//System.out.println("Line " + 1 + ": ");
			String line = scan.nextLine();

			// If an input string has tokens separated by a comma,
			// possibly surrounded by spaces, e.g. "1 ,   2,3",
			// the following code will split the string into tokens
			// and eliminate all whitespace and commas separating the tokens:

			String[] tokens = line.split("\\s*,\\s*");

			// Here "\\s" is a "regular expression" that means a whitespace
			// character (space, tab, newline, etc.); 
			// * means repeated 0 or more times (use "\\s+" if you need
			// repeated 1 or more times).
			// "\\s*,\\*" means the separator between tokens is some (optional) whitespace
			// followed by (optional) comma(s), followed by some (optional) whitespace.

			// Your solution code goes here, or, better, into a separate method, process(tokens).
			// For example:

			P1C = new int[5]; // player 1 cards
			P2C = new int[5]; // player 2 cards

			for (int i = 0; i < 5; i++) {
				// turn token into an integer
				
				P1C[i] = cardVal(tokens[i]);
				//system.out.println(P1C[i]);
			}
			for (int i = 0; i < 5; i++) {
				P2C[i] = cardVal(tokens[i+5]);
				//system.out.println(P2C[i]);
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

			// To protect your program from crashing while processing one of the
			// input lines, use a try-catch block:

			try
			{
				//System.out.println("\nTrial " + k + ": ");
				
				String line = scan.nextLine();
				//system.out.println("here1");
				// If an input string has tokens separated by a comma,
				// possibly surrounded by spaces, e.g. "1 ,   2,3",
				// the following code will split the string into tokens
				// and eliminate all whitespace and commas separating the tokens:

				String[] tokens = line.split("\\s*,\\s*");
				//system.out.println("here2");
				// Here "\\s" is a "regular expression" that means a whitespace
				// character (space, tab, newline, etc.); 
				// * means repeated 0 or more times (use "\\s+" if you need
				// repeated 1 or more times).
				// "\\s*,\\*" means the separator between tokens is some (optional) whitespace
				// followed by (optional) comma(s), followed by some (optional) whitespace.

				// Your solution code goes here, or, better, into a separate method, process(tokens).
				
				// exception here
				System.out.println(processGame(tokens));	
				//system.out.println("here3");
			}
			catch (Exception e)
			{
				System.out.println("Exception in processing: line #" + k);
			}
		}
		scan.close();
	}
	public static int cardVal(String s) {
		s = s.trim();
		if (s.length() > 1) { // if the string has multiple characters, it is not a letter, so convert
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
		if (diff > 0 && (curPoints <= 33 && curPoints + diff >= 34 || curPoints <= 55 && curPoints + diff >= 56 
				|| curPoints <= 77 && curPoints + diff >= 78)) {
			diff += 5;
		} if (diff < 0 && (curPoints >= 34 && curPoints + diff <= 33 || curPoints >= 56 && curPoints + diff <= 55
				|| curPoints >= 78 && curPoints + diff <= 77)) {
			diff += 5;
		}
		
		return diff;
		
	}
	public static String processGame(String[] tokens) {
		// local P1C and P2C to modify as the game goes on
		int[] P1CUR = P1C.clone();
		int[] P2CUR = P2C.clone();
		
		int points = Integer.parseInt(tokens[0]);
		//system.out.println("cur points: " + points);
		
		if (points > 99) {
			//system.out.println("ret 1");
			return (points + ", initial val > 99");
		}
		
		int tokenIndVal = 1;
		for (int i = 0; i < 12; i++) {
			// 10 turns total (each player gets 5)
			//System.out.println("turn " + i);
			//System.out.println("points: " + points);
			
			int nextCard = 0; // the next card doesn't matter for i == 10 and i == 11
			if (tokenIndVal < tokens.length) {
				nextCard = cardVal(tokens[tokenIndVal]);
				tokenIndVal++;
			}
			//System.out.println("new card: " +nextCard);
			
			
			
			
			// even means P1 turn
			boolean P1turn = false;
			if (i%2 == 0) {
				P1turn = true;
				////System.out.println("  P1T");
			} else {
				////System.out.println("  P2T");
			}
			if (P1turn) {
				Arrays.sort(P1CUR);
				//System.out.println("  P1");
				for (int j = 0; j < 5; j++) {

					//System.out.println("    " + P1CUR[j]);
				}
				//System.out.println("  curCard: " + P1CUR[2]);
				//system.out.println("  point diff: " + processCard(P1CUR[2], points));
				
				points += processCard(P1CUR[2], points);
				
				//system.out.println("  points: " + points);
				
				//system.out.println("  tokenInd: " + tokenIndVal);
				//system.out.println("  token: " + tokens[tokenIndVal]);
				
				P1CUR[2] = nextCard;
				
				//system.out.println("  tokenInd: " + tokenIndVal);
				
				
			} else {
				
				Arrays.sort(P2CUR);
				//System.out.println("  P2");
				for (int j = 0; j < 5; j++) {

					//System.out.println("    " + P2CUR[j]);
				}
				//System.out.println("  curCard: " + P2CUR[2]);
				
				points += processCard(P2CUR[2], points);
				//System.out.println("tok: " + tokens[tokenIndVal].trim());
				//System.out.println("processed tok: " + cardVal(tokens[tokenIndVal]));
				P2CUR[2] = nextCard;
				
			}
			
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