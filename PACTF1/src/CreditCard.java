import java.io.*;
import java.util.*;
/*
 * 
 */
public class CreditCard {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("ccleak.in"));
		//int N = Integer.parseInt(br.readLine());
		//int[] arr = new int[N];
		for (int i = 0; i < 403947; i++) {
			String cardString = (br.readLine());
			int[] card = new int[16];
			for (int j = 0; j < 16; j++) {
				card[j] = Integer.parseInt(String.valueOf(cardString.charAt(j)));
			}
			int lastdig = card[card.length-1];
			int[] revCard = new int[16];
			for (int j = 0; j < 16; j++) {
				revCard[j] = Integer.parseInt(String.valueOf(cardString.charAt(15-j)));
			}
			for (int j = 1; j < 16; j+=2) {
				// for odd positions in the reverse, multiply by 2
				revCard[j] *= 2;
				if (revCard[j] > 9) revCard[j] -= 9;
			}
			int sum = 0;
			for (int j = 1; j < 16; j++) {
				sum += revCard[j];
			}
			if ((lastdig + sum)%10 != 0) {
				System.out.println("invalid: " + i);
			}
		}
	}


}
