import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * 
 * 1D DP boolean
 * O(HC)
 */
public class HayForSale {
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int C = Integer.parseInt(st.nextToken()); // capacity
		int H = Integer.parseInt(st.nextToken()); // num haybales
		int[] bales = new int[H];
		for (int i = 0; i < H; i++) { // scan in the bales
			bales[i] = Integer.parseInt(scan.nextLine());
		}
		// loop through haybales
		// DP[capacity] = boolean if you can reach the capacity
		boolean[] DP = new boolean[C+1]; 
		DP[0] = true;
		for (int i = 0; i < H; i++) { // loop through haybales
			for (int j = C; j >= 0; j--) { 
				// !!! so you don't use haybales more than once !!!
				// so you don't loop over the things you just set as true
				if (DP[j] && j+bales[i] <= C) { // if reachable
					DP[j+bales[i]] = true;
				}
			}
		}
		for (int j = C; j >= 1; j--) {
			if (DP[j]) {
				System.out.println(j);
				break;
			}
		}
	}
}
