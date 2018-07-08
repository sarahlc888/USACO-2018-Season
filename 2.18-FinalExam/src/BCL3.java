import java.io.*;

// optimized, 3/10

public class BCL3 {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // num cows
		char[] cows = new char[N+1]; // pad it at the start
		for (int i = 1; i <= N; i++) {
			cows[i] = br.readLine().charAt(0);
		}
		
		// DP[i][j] = optimal new string possible using 0...i and j...N-1

		String[] DP1 = new String[N+2];
		String[] DP2 = new String[N+2]; // index i
		
		int i = 0;
		

		DP2[N+1] = "";
	//	DP[0][N+1] = ""; // basecase, redundant
		String best = "a"; // will always come after
		
		
		
		while ( i <= N+1) {
			for (int j = N+1; j > i; j--) {
				
				if (i == 0 && j == N+1) continue; // basecase
				if (i == N+1 && j == N+1) continue; // redundant
				if (i == 0 && j == 0) continue; // redundant
				
//				System.out.println("i: " + i + " j: " + j);
				if (i-1 >= 0 && j+1 <= N+1 ) { // left and right
					
					System.out.println("  h1");
					DP2[j] = minString(DP1[j] + cows[i], DP2[j+1] + cows[j]).trim();
				} else if (i-1 >= 0) { // left
//					System.out.println("  h2");
					
					DP2[j] = DP1[j]+cows[i];
				} else { // right
//					System.out.println("  h3");
					DP2[j] = DP2[j+1] + cows[j];
				}
//				System.out.println("  " + DP2[j]);
				
				if (j == i+1) {
					if (DP2[j] != "" && DP2[j].length() == N) {
						best = minString(best, DP2[j]).trim();
					}
				}
//				System.out.println(DP2[j].length());
				
			}
			i++;
			DP1 = DP2;
			DP2 = new String[N+2];
		}
		
		//System.out.println(best.length());
		//System.out.println(best.charAt(1));
		int ind1 = 0;
		int ind2 = 80;
		while (ind2 <= best.length()) {
			System.out.println(best.substring(ind1, ind2));
			
			ind1 += 80;
			ind2 += 80;
		}
		System.out.println(best.substring(ind1));

		//System.out.println(best);
		
	}
	
	public static String minString(String a, String b) {
		if (a.length() > b.length()) return a;
		if (b.length() > a.length()) return b;
		
		if (a.compareTo(b) < 0) return a;
		else return b;
	}
}
