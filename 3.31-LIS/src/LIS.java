/*
 * revisit of longest increasing subsequence, N log N algo
 * Dr. Ming's code
 */
import java.util.*;
import java.io.*;

public class LIS {

	public static void main(String[] args) {

		//int A[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 };
		int B[] = { 2, 3, 7, 11, 13 };
		System.out.println(CeilIndex(B, 0, 4, 13));
		int N = 80;
		Random gen = new Random(2017);
		int A[] = new int[N];
		for(int i=0; i<N; i++)
			A[i] = gen.nextInt();

		int n = A.length;
		System.out.println( lisLen(A, n) );
	}

	// Binary search for least >= within (l, r]
	static int CeilIndex(int A[], int l, int r, int key) {
		while (r - l > 1) {
			int m = l + (r - l)/2;
			if (A[m]>=key)
				r = m;
			else
				l = m;
		}

		return r;
	}

	static int lisLen(int A[], int size)  {
		// Add boundary case, when array size is one

		int[] tailTable = new int[size]; // last element of LIS of length i+1
		int len; // always points empty slot

		tailTable[0] = A[0];
		len = 1;
		for (int i = 1; i < size; i++) {
			if (A[i] < tailTable[0])
				// new smallest value
				tailTable[0] = A[i];

			else if (A[i] > tailTable[len-1]) {
				// A[i] wants to extend largest subsequence
				tailTable[len] = A[i];
				len++;
			} 
			else
				// A[i] wants to be current end candidate of an existing
				// subsequence. It will replace ceil value in tailTable
				tailTable[CeilIndex(tailTable, -1, len-1, A[i])] = A[i];
		}

		return len;
	}
}
