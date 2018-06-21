import java.io.*;
import java.util.*;
/*
 * O(T)
 * 10/10 test cases
 */
public class FruitFeast {
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int T = Integer.parseInt(st.nextToken()); // max fullness
		int A = Integer.parseInt(st.nextToken()); // orange fullness
		int B = Integer.parseInt(st.nextToken()); // lemon fullness

		// DP[fullness][water or not] = accessible or not
		boolean[][] DP = new boolean[T+1][2];
		DP[0][0] = true;

		for (int j = 0; j < 2; j++) { // water or not
			for (int i = 0; i <= T; i++) { // loop through fullnesses
				if (!DP[i][j]) // if can't reach it, skip
					continue;

				if (i + A <= T) {
					// if you can eat the orange
					DP[i+A][j] = true;
				}
				if (i + B <= T) {
					// if you can eat the lemon
					DP[i+B][j] = true;
				}

				if (j == 0) { // if you haven't drank water yet, you can drink water
					// will revisit and eat fruit on the second loop
					DP[i/2][1] = true;
				}
			}
		}
		for (int i = T; i >= 0; i--) {
			if (DP[i][0] || DP[i][1]) {
				// if reachable
				System.out.println(i);
				break;
			}
		}


	}
}
