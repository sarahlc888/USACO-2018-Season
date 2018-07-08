/*
 * from 2.35, copied over
 * 10/10 test cases
 */


import java.io.*;
import java.util.*;

public class HighCardLowCard {

	public static void main(String[] args) throws IOException {
		// read input
		Scanner input = new Scanner(new InputStreamReader(System.in));
		// n cards, n is even
		int n = input.nextInt();
		// there will be n rounds, in the first n/2 high card wins, last n/2, low card wins
		// Bessie knows the other that Elsie will play her cards in
		
		// split B and E's cards into halves for higher and lower (make B's higher half have all the higher cards)
		// sort them and do exactly the same thing as silver
		
		// Elsie's cards
		int[] E = new int[n];
		for (int i = 0; i < n; i++) {
			E[i] = input.nextInt();
		}
		input.close();
		
		boolean[] opponent = new boolean[2*n+1];

		// make all of elsie's cards true
		for(int i=0; i<n; i++) {
			int k = E[i];
			opponent[k] = true;
		}
		// index in Bessie's
		int bInd = 0;
		// Bessie's cards, if they're not true
		int[] B = new int[n];
		for(int i=1; i<=2*n; i++) {
			if( !opponent[i] ) {
				B[bInd] = i;
				bInd++;
			}
		}
		
		Arrays.sort(B);
		
		int[] eLo = new int[n/2];
		int[] eHi = new int[n/2];
		int[] bLo = new int[n/2];
		int[] bHi = new int[n/2];
		// System.out.println("ehi");
		int curInd = 0;
		for(int i=0; i<n/2; i++) {
			eHi[curInd] = E[i];
			// System.out.println(eHi[curInd]);
			curInd++;
		}
		// System.out.println("elo");
		curInd = 0;
		for(int i=n/2; i<n; i++) {
			eLo[curInd] = E[i];
			// System.out.println(eLo[curInd]);
			curInd++;
		}
		// System.out.println("blo");
		curInd = 0;
		for(int i=0; i<n/2; i++) {
			bLo[curInd] = B[i];
			// System.out.println(bLo[curInd]);
			curInd++;
		}
		
		// System.out.println("bhi");
		curInd = 0;
		for(int i=n/2; i<n; i++) {
			bHi[curInd] = B[i];
			// System.out.println(bHi[curInd]);
			curInd++;
		}
		
		Arrays.sort(eLo);
		Arrays.sort(bLo);
		
		int[] eLo2 = new int[n/2];
		int[] bLo2 = new int[n/2];
		
		for (int i = 0; i < n/2; i++) {
			eLo2[i] = eLo[n/2 - 1 - i];
			bLo2[i] = bLo[n/2 - 1 - i];
		}
		
		
		Arrays.sort(eHi);
		Arrays.sort(bHi);
		
		// System.out.println("");
		
		int bpoints = 0;
		bInd = 0; 
		int eInd = 0;
		
		while (bInd < n/2 && eInd < n/2) {
			// System.out.println("trigger1");
			if (bLo2[bInd] < eLo2[eInd]) {
				bpoints++;
				bInd++;
				eInd++;
			} else {
				bInd++;
			}
		}
		
		bInd = 0; 
		eInd = 0;
		while (bInd < n/2 && eInd < n/2) {
			// System.out.println("trigger2");
			if (bHi[bInd] > eHi[eInd]) {
				bpoints++;
				bInd++;
				eInd++;
			} else {
				bInd++;
			}
		}
		
		System.out.println(bpoints);
		
		
	}
}