import java.util.*;
import java.io.*;

// partial sum strategy!

public class ConnectK {

	public static void main(String[] args) throws IOException {
		
		
		Scanner scan = new Scanner(new File("ConnectK.in"));
		int R = scan.nextInt(); // number of rows
		int C = scan.nextInt(); // number of columns
		int K = scan.nextInt(); // number to connect
		int N = scan.nextInt(); // number of lines to come
		
		// set up the board
		int[][] board = new int[R][C];
		for (int i = 0; i < N; i++) 
			board[scan.nextInt()][scan.nextInt()] = scan.nextInt();
		scan.close();
		
		// but there can the duplicates - 1 + 1 + 1 + 1  = 2 + 1 + 1 + 0
		// If there are only two non-zero elements, we can tell them apart 
		// still using the partial sums.
		// We need to re-assign the two non-zero elements.
		
		int A = 1; // values to reassign 1 to
		int B = K+1; // values to reassign 2 to
		
		for (int i = 0; i < R; i++) { // reassign the board
			for (int j = 0; j < C; j++) {
				if (board[i][j] == 1) board[i][j] = A;
				if (board[i][j] == 2) board[i][j] = B;
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		
		// go through the rows to see if there's a winner
		for (int i = 0; i < R; i++) {
			int[] row = board[i];
	
			int[] sum = new int[row.length+1]; // sum[i] is up to but not including row[i]
			// sum[0] = 0; sum[1] = row[0]; sum[i] = row[0] + ... + row[i-1]
			// so that row[i] = sum[i+1] - sum[i]
			// row[i] + ... row[j] = sum[j+1] - sum[i]
			
			for(int j=1; j<=C; j++) { // loop through and get all the sums
				sum[j] = sum[j-1] + row[j-1];
			}
		
			for (int j = K; j <= C; j++) { 
				// i goes from K to C - it's the up to but not including index of the K group
				int curSum = sum[j] - sum[Math.max(0, j-K)];
				
				for (int k = 4; k > 0; k--) {
					System.out.print(row[j-k]);
				}
				System.out.println();
				
				if (curSum != 0 && (curSum == A * K || curSum == B * K)) {
					System.out.println("WINNER!");
				}
			}
		}
		
		
		// go through the columns to see if there's a winner
		for (int i = 0; i < C; i++) {
			int[] col = new int[R];
			for (int j = 0; j < R; j++) // get the column
				col[j] = board[j][i];
			
			int[] sum = new int[col.length+1]; // sum[i] is up to but not including row[i]
			// sum[0] = 0; sum[1] = row[0]; sum[i] = row[0] + ... + row[i-1]
			// so that row[i] = sum[i+1] - sum[i]
			// row[i] + ... row[j] = sum[j+1] - sum[i]
					
			for(int j=1; j<=R; j++) // loop through and get all the sums
				sum[j] = sum[j-1] + col[j-1];
				
			for (int j = K; j <= R; j++) { 
				// i goes from K to C - it's the up to but not including index of the K group
				int curSum = sum[j] - sum[Math.max(0, j-K)];
				
				for (int k = 4; k > 0; k--) {
					System.out.print(col[j-k]);
				}
				System.out.println();
						
				if (curSum != 0 && (curSum == B * K || curSum == A * K)) {
					System.out.println("WINNER!");
				}
			}
		}
		
		// go through the \ diagonals to see if there's a winner
		for (int i = 0; i < C; i++) { // go across the top bar first
			int[] diagTB = new int[Math.min(C-i, R)]; // two limiting factors
			int x = 0;
			int y = i;
			
			for (int j = 0; j < diagTB.length; j++) { // get the diag
				diagTB[j] = board[x][y];
				x++;
				y++;
			}
			
			int[] sum = new int[diagTB.length+1]; // sum[i] is up to but not including row[i]
					// sum[0] = 0; sum[1] = row[0]; sum[i] = row[0] + ... + row[i-1]
					// so that row[i] = sum[i+1] - sum[i]
					// row[i] + ... row[j] = sum[j+1] - sum[i]
							
			for(int j=1; j<=diagTB.length; j++) // loop through and get all the sums
				sum[j] = sum[j-1] + diagTB[j-1];
						
			for (int j = K; j <= diagTB.length; j++) { 
				// i goes from K to C - it's the up to but not including index of the K group
				int curSum = sum[j] - sum[Math.max(0, j-K)];
						
				for (int k = 4; k > 0; k--) {
					System.out.print(diagTB[j-k]);
				}
				System.out.println();
								
				if (curSum != 0 && (curSum == A * K || curSum == B * K)) {
					System.out.println("DIAG WINNER!");
				}
			}
		}		
		for (int i = 0; i < R; i++) { // go across the left bar next
			int[] diagTB = new int[Math.min(R-i, C)]; // two limiting factors
			int x = i;
			int y = 0;
			
			for (int j = 0; j < diagTB.length; j++) { // get the diag
				diagTB[j] = board[x][y];
				x++;
				y++;
			}
			
			int[] sum = new int[diagTB.length+1]; // sum[i] is up to but not including row[i]
					// sum[0] = 0; sum[1] = row[0]; sum[i] = row[0] + ... + row[i-1]
					// so that row[i] = sum[i+1] - sum[i]
					// row[i] + ... row[j] = sum[j+1] - sum[i]
							
			for(int j=1; j<=diagTB.length; j++) // loop through and get all the sums
				sum[j] = sum[j-1] + diagTB[j-1];
						
			for (int j = K; j <= diagTB.length; j++) { 
				// i goes from K to C - it's the up to but not including index of the K group
				int curSum = sum[j] - sum[Math.max(0, j-K)];
						
				for (int k = 4; k > 0; k--) {
					System.out.print(diagTB[j-k]);
				}
				System.out.println();
								
				if (curSum != 0 && (curSum == A * K || curSum == B * K)) {
					System.out.println("DIAG2 WINNER!");
				}
			}
		}		
		
		
		
		// go through the / diagonals to see if there's a winner
		for (int i = 0; i < C; i++) { // go across the bottom bar first
			int[] diagBT = new int[Math.min(C-i, R)]; // two limiting factors
			if (diagBT.length < K) continue;
			int x = R-1;
			int y = i;
					
			for (int j = 0; j < diagBT.length; j++) { // get the diag
				diagBT[j] = board[x][y];
				x--;
				y++;
			}
				
			int[] sum = new int[diagBT.length+1]; // sum[i] is up to but not including row[i]
					// sum[0] = 0; sum[1] = row[0]; sum[i] = row[0] + ... + row[i-1]
					// so that row[i] = sum[i+1] - sum[i]
					// row[i] + ... row[j] = sum[j+1] - sum[i]
									
			for(int j=1; j<=diagBT.length; j++) // loop through and get all the sums
				sum[j] = sum[j-1] + diagBT[j-1];
								
			for (int j = K; j <= diagBT.length; j++) { 
						// i goes from K to C - it's the up to but not including index of the K group
				int curSum = sum[j] - sum[Math.max(0, j-K)];
								
				for (int k = 4; k > 0; k--) {
					System.out.print(diagBT[j-k]);
				}
				System.out.println();
										
				if (curSum != 0 && (curSum == A * K || curSum == B * K)) {
					System.out.println("DIAG3 WINNER!");
				}
			}
		}		
		for (int i = 0; i < R; i++) { // go across the left bar next
			int[] diagBT = new int[Math.min(i, C)]; // two limiting factors
			int x = i;
			int y = 0;
					
			for (int j = 0; j < diagBT.length; j++) { // get the diag
				diagBT[j] = board[x][y];
				x--;
				y++;
			}
					
			int[] sum = new int[diagBT.length+1]; // sum[i] is up to but not including row[i]
							// sum[0] = 0; sum[1] = row[0]; sum[i] = row[0] + ... + row[i-1]
							// so that row[i] = sum[i+1] - sum[i]
							// row[i] + ... row[j] = sum[j+1] - sum[i]
									
			for(int j=1; j<=diagBT.length; j++) // loop through and get all the sums
				sum[j] = sum[j-1] + diagBT[j-1];
								
			for (int j = K; j <= diagBT.length; j++) { 
				// i goes from K to C - it's the up to but not including index of the K group
				int curSum = sum[j] - sum[Math.max(0, j-K)];
								
				for (int k = 4; k > 0; k--) {
					System.out.print(diagBT[j-k]);
				}
				System.out.println();
										
				if (curSum != 0 && (curSum == A * K || curSum == B * K)) {
					System.out.println("DIAG4 WINNER!");
				}
			}
		}		
		
		// create a new problem (similar); or modify the current one
		
		
	}

}


/*
 * Connect K
 * 
 * Problem description
 * 
 * Given a matrix with R rows and C columns, where each element is 1, 2, or 0.
 * Check whether there are K copies of connected “1” or K copies of connected
 * “2” in a row, or a column, or diagonally. The diagonal goes either
 * northwest-southeast or northeast-southwest directions.
 * 
 * Input file 
 * 
 * • The first row gives three integers R, C, K, and N, separated by
 * spaces, 4 ≤R, C ≤ 5000, 1≤K≤min(R/2, C/2). 
 * 
 * • The next N lines each shows a
 * non-zero element in the matrix: the row index, column index, and the value.
 * 
 * Output file 
 * 1 if there are K copies of connected “1”, but not K copies of connected “2”; 
 * 2 if there are K copies of connected “2”, but not K copies of connected “1”; 
 * 3 if there are K copies of connected “1” and K copies of connected “2”; 
 * 0 otherwise.
 * 
 * Sample
 * 
 * Input: 
 * 5 7 4 4 
 * 2 2 1 
 * 2 3 1 
 * 2 4 1 
 * 2 5 1
 * 
 * Output: 
 * 1
 * 
 * In this sample, there are four connected 1’s in row 2.
 */