import java.io.*;
import java.util.*;

// modified brute force
// always turn strings into int to make it faster

public class CG2 {
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 10; i++) {
			processOneFile("testData-3/" + (i+10) + ".in");
		}
	}
	public static void processOneFile(String filename) throws IOException{
		Scanner scan = new Scanner(new File(filename));
		int n = scan.nextInt(); // number cows in each group
		int m = scan.nextInt(); // length of genome
		int[][] spotty = new int[n][m];
		int[][] plain = new int[n][m];
		scan.nextLine(); // scan the rest of the line
		for (int i = 0; i < n; i++) {
			String gen = scan.nextLine();
			for (int j = 0; j < m; j++) {
				char curChar = gen.charAt(j);
				if (curChar == 'A') spotty[i][j] = 0;
				else if (curChar == 'C') spotty[i][j] = 1;
				else if (curChar == 'G') spotty[i][j] = 2;
				else spotty[i][j] = 3;
			}
		}
		
		for (int i = 0; i < n; i++) {
			String gen = scan.nextLine();
			for (int j = 0; j < m; j++) {
				char curChar = gen.charAt(j);
				if (curChar == 'A') plain[i][j] = 0;
				else if (curChar == 'C') plain[i][j] = 1;
				else if (curChar == 'G') plain[i][j] = 2;
				else plain[i][j] = 3;
			}
		}
		scan.close();
		int maxSame = 0;
		for (int i = 0; i < n; i++) {
			// go through spotty and compare to plain
			// cow spotty[i]
			for (int j = 0; j < n; j++) {
				// cow plain[j]
				int numSame = 0;
				for (int k = 0; k < m; k++) {
					// index m of genome
					if (spotty[i][k] == plain[j][k]) {
						numSame++;
					}
				}
				if (maxSame < numSame) {
					maxSame = numSame;
				}
			}
		}
		
		System.out.println(maxSame);
		
		
	}
}
