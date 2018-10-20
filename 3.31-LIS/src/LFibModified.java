/*
 * longest MODIFIED fibonacci sequence inside array of n distinct positive ints, n <= 10000
 * MODIFICATION: A(n+2) = A(n+1) + A(n) + 1
 * DP[i][j] = length of LFS ending in i, j
 * DP[j][k] = DP[i][j] + 1 where A[i]+A[j] = k
 * map values to indices
 * 
 * not fully tested, works on basic test cases
 */
import java.util.*;
import java.io.*;

public class LFibModified {

	public static void main(String[] args) {

//		int A[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 }; // A[index] == value
//		int A[] = { 2, 1, 3, 4, 5, 6, 8, 10, 13 };
//		int A[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int A[] = {1, 9, 342, 390, 2, 3, 10, 93, 484, 4, 7, 12, 11, 20};
		TreeMap<Integer, Integer> m = new TreeMap<Integer, Integer>();
		for (int i = 0; i < A.length; i++) {
			m.put(A[i], i); // m[value] == index
		}
		
		int[][] DP = new int[A.length][A.length];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A.length; j++) {
				DP[i][j] = 2;
			}
		}
		
		int maxlen = 0;
		for (int i = 0; i < A.length; i++) {
			for (int j = i+1; j < A.length; j++) {
				// i then j 
				
				int Ak = A[i] + A[j] + 1;
				if (m.containsKey(Ak) && m.get(Ak) > j) {
					int k = m.get(Ak);
					
					DP[j][k] = DP[i][j]+1;
					maxlen = Math.max(DP[j][k], maxlen);

					System.out.println("i: " + i + "  j: " + j + "  k: " + k + "  len: " + DP[j][k]);
				}
				
			}
		}
		
		System.out.println(maxlen);
	}
}
