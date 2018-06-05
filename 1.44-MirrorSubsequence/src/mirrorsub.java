import java.io.*;
import java.util.*;

// find longest common subsequence between an array and a mirrored version of the array
// Problem: given an int[], find the longest subarray so that its mirror also appears in the array

public class mirrorsub {
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("mirror.in"));
		int N = Integer.parseInt(br.readLine()); // length of the array
		int[] arr = new int[N]; // scan in the array
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		br.close();

		int[] mir = new int[N];
		for (int i = 0; i < N; i++) {
			mir[i] = arr[N-1-i];
		}
		
		// find the longest common consecutive subsequence (LCS) in arr and mir
		// lcs[i][j] returns length of lcs in arr[0...i-1] and mir[0...j-1]
		// when i-1 == j-1 (otherwise, it == 0)
		int[][] lcs = new int[N+1][N+1]; 
		int max = 0;
		
		// O(n^2) because f[i][j] and n^2 spots to fill
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == 0 || j == 0) {
					// indices == 0, no real meaning - there are no vals before index 0
					lcs[i][j] = 0; 
				} else if (arr[i-1] == mir[j-1]) {
					// if vals at the indices being considered are the same, update
					lcs[i][j] = lcs[i-1][j-1] + 1;
					if (lcs[i][j] > max) max = lcs[i][j];
				} else {
					lcs[i][j] = 0; // no longer consecutive, start over
					// CANNOT BE lcs[i][j] = lcs[i-1][j-1]
					// because that won't reset, will increment lcs off of past, nonconsec terms
				}
			}
		}
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= N; j++) {
				System.out.print(lcs[i][j]);
				System.out.println("  i: " + i + " j: " + j);
				
			}
		}
		System.out.println(max);
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

		pw.println();
		pw.close();
	}

}
