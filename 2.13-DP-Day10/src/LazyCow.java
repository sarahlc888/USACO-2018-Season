import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 10/10 test cases
 */
public class LazyCow {
	static int max = 0; // max grass consumption
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // N by N grid
		int K = Integer.parseInt(st.nextToken()); // number of steps

		int[][] field = new int[N][N];

		for (int i = 0 ; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				field[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// iterate through starting points
		int curSum = 0; // amt of grass eatable
		// basecase at 0, 0
		for (int i = 0; i <= K; i++) {
			for (int j = 0; j <= K-i; j++) {
				if (i >= N || j >= N) continue; // catch
				curSum += field[i][j];
			}
		}


		// TODO: make sure this is right: transitions with stuff that used to be out of bounds?
		// and is just skipped (only the outline is traced in

		int prevRowStart = curSum; // start of the last row

		max = Math.max(curSum, max); // update

		for (int i = 0 ; i < N; i++) {
			// calculate all new row shifts where j == 0

			if (i != 0) { // precalculated outside

				// vertical shift
				curSum = prevRowStart;
				//System.out.println("prs: " + prevRowStart);

				for (int k = 0; k <= K; k++) { 
					int m = K-k; // m+k = K always

					// add the new new edge
					//System.out.println("  add: " + (i+k) + " " + m);
					if (i+k < 0 || i+k >= N || m < 0 || m >= N) {
						// out of bounds
					} else { // do bottom right / edge
						curSum += field[i+k][m];
					}
					// get rid of the old edge
					//System.out.println("  remove: " + (i-1-k) + " " + m);
					if (i-1-k < 0 || i-1-k >= N || m < 0 || m >= N) {
						// out of bounds
					} else { // do top right \ edge
						curSum -= field[i-1-k][m];
					}
				}

				prevRowStart = curSum;

				max = Math.max(curSum, max); // update

				

			}
			//System.out.println("i: " + i + " j: " + 0 + " sum: " + curSum);
			for (int j = 1; j < N; j++) {


				// if it's just a simple horizontal shift
				for (int k = 0; k <= K; k++) { 
					int m = K-k; // m+k = K always

					// add the new new edge
					//System.out.println("  add: " + (i+k) + " " + (j+m));
					if (i+k < 0 || i+k >= N || m+j < 0 || m+j >= N) {
						// out of bounds
					} else { // do bottom right / edge
						curSum += field[i+k][j+m];
					}
					//System.out.println("  add: " + (i-k) + " " + (j+m));
					if ((i-k < 0 || i-k >= N || m+j < 0 || m+j >= N) ||
							k == 0) { // account for duplicates
						// out of bounds
					} else { // do top right \ edge
						curSum += field[i-k][j+m];
					}

					// subtract the old edge
					//System.out.println("  remove: " + (i-k) + " " + (j-1-m));
					if (i-k < 0 || i-k >= N || j-1-m < 0 || j-1-m >= N) {
						// out of bounds
					} else { // do top left / edge
						curSum -= field[i-k][j-1-m]; // j-1 bc it's the previous
					}
					//System.out.println("  remove: " + (i+k) + " " + (j-1-m));
					if ((i+k < 0 || i+k >= N || j-1-m < 0 || j-1-m >= N) || 
							k == 0) {
						// out of bounds
					} else { // do bottom left \ edge
						curSum -= field[i+k][j-1-m];
					}
				}

				//System.out.println("i: " + i + " j: " + j + " sum: " + curSum);

				if (curSum > max) {
					max = curSum; // update

				}

			}
		}

		System.out.println(max);


	}
}
