import java.io.*;
import java.util.*;
	
// also could use normal partial sum by going through each case of H to P change and 
// going through all the different changing points

// works but test case 10 times out

public class HPS {
	public static void main (String args[]) throws IOException {
		
		// counts for how many of each move there is in p2m before the change
		int H1 = 0;
		int P1 = 0;
		int S1 = 0;

		// counts for how many of each move there is in p2m after the change
		int H2 = 0;
		int P2 = 0;
		int S2 = 0;

		Scanner scan = new Scanner(new File("hps.in"));
		int N = scan.nextInt(); // number of rounds
		int[] p2m = new int[N]; // player 2's moves
		
		for (int i = 0; i < N; i++) {
			
			String inp = scan.next();
			
			// H = 0, P = 1, S = 2
			if (inp.equals("H")) {
				p2m[i] = 0;
				H2++;
			}
			if (inp.equals("P")) {
				p2m[i] = 1;
				P2++;
			}
			if (inp.equals("S")) {
				p2m[i] = 2;
				S2++;
			}
		}
		scan.close();
		
		System.out.println(Arrays.toString(p2m));

		int max = 0;
		
		for (int i = 0; i < N; i++) {
			// i is the location where the change takes effect

			// account for the change
			
			if (i != 0) { // account for the one before the "line" of divide but not on the line
				if (p2m[i - 1] == 0) {
					H1++;
					H2--;
				} else if (p2m[i - 1] == 1) {
					P1++;
					P2--;
				} else if (p2m[i - 1] == 2) {
					S1++;
					S2--;
				}
			}

			// count the wins
			
			int wins = Integer.max(Integer.max(H1, P1), S1) + Integer.max(Integer.max(H2, P2), S2);
			
			if (H1 > P1 && H1 > S1) {
				System.out.println("H1: " + H1);
			} else if (P1 > S1) {
				System.out.println("P1: " + P1);
			} else {
				System.out.println("S1: " + S1);
			}
			
			if (H2 > P2 && H2 > S2) {
				System.out.println("H2: " + H2);
			} else if (P2 > S2) {
				System.out.println("P2: " + P2);
			} else {
				System.out.println("S2: " + S2);
			}
			
			if (wins > max) max = wins;
			System.out.println("switch at: " + i);
			System.out.println("wins: " + wins);
		}
		//System.out.println(max);
		PrintWriter pw = new PrintWriter(new File("hps.out"));
		pw.println(max);
		pw.close();
		
	}
}
