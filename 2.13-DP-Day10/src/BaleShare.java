import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 */
public class BaleShare {
	public static void main(String args[]) throws IOException {
		// scan in
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int sum = 0;
		int[] bales = new int[N];
		for (int i = 0; i < N; i++) {
			bales[i] = Integer.parseInt(br.readLine());
			sum += bales[i];
		}
		
		// DP[i][j][k] = DP[num bales used][B1 amt][B2 amt]
		boolean[][] previous = new boolean[sum+1][sum+1];
		boolean[][] cur = new boolean[sum+1][sum+1];
		
		// basecase
		previous[0][0] = true;
		
		for (int i = 0; i < N; i++) { // number haybales used
			for (int j = 0; j <= sum; j++) { // B1 amt
				for (int k = 0; k <= sum; k++) { // B2 amt
					if (previous[j][k]) { // if the state is accessible with i-1 haybales
						// add to the next state
						//System.out.println("j: " + j + " k: " + k);
						cur[j][k] = true; // carry over
						if (j+bales[i] <= sum) {
							cur[j+bales[i]][k] = true; 
						} 
						if (k+bales[i] <= sum) {
							cur[j][k+bales[i]] = true;
						} 
					}
				}
			}
			
			// copy over
			previous = cur;
			cur = new boolean[sum+1][sum+1];
		}
		int min = Integer.MAX_VALUE;
		for (int j = 0; j <= sum; j++) {
			for (int k = 0; k <= sum; k++) {
				if (previous[j][k]) { // if reachable
					////System.out.println("here");
					int current = sum-j-k; // current
					
					//System.out.println("b1: " + current + " b2: " + j + " b3: " + k);
					
					int val = Math.max(Math.max(current, j), k);
					min = Math.min(min, val);
				}
			}
		}
		
		System.out.println(min);
	}
}
