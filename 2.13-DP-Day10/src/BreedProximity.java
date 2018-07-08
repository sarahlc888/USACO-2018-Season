import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 10/10 test cases
 * 
 * sliding window of size K+1
 * O(N)
 */
public class BreedProximity {
	static int maxAns = 0;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // cows
		int K = Integer.parseInt(st.nextToken()); // crowded criteria

		int maxID = 0;

		int[] cows = new int[N];


		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(br.readLine());

			maxID = Math.max(maxID, cows[i]);

		}
		// sliding window
		// counts[i] = times i occurs in the current sliding window
		int[] counts = new int[maxID+1]; 

		for (int i = 0; i <= K; i++) { // first sliding window
			counts[cows[i]]++; // increment counts array
			if (counts[cows[i]] > 1) { // if repeats
				if (cows[i] > maxAns) maxAns = cows[i];
			}
		}
		
		// slide the window
		for (int i = 1; i < N-K; i++) {
			// i is the start of the sliding window, i+K is the end
			counts[cows[i-1]]--; // decrement the count for cow left behind
			counts[cows[i+K]]++; // increment the count for cow added

			if (counts[cows[i+K]] > 1) { // if repeats, update
				if (cows[i+K] > maxAns) maxAns = cows[i+K];
			}
			//System.out.println(Arrays.toString(counts));
		}
		System.out.println(maxAns);

	}
}
