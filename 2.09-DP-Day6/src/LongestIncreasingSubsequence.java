import java.io.*;
import java.util.*;

/*
 * 10/10 test cases
 */

public class LongestIncreasingSubsequence {
	public static void main(String[] args) throws IOException {
		
		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // length of array
		
		int[] a = new int[N+1]; // values
		for (int i = 1; i <= N; i++) {
			a[i] = Integer.parseInt(scan.nextLine().trim());
			//System.out.println(a[i]);
		}
		a[0] = Integer.MIN_VALUE; // so everything can be at least 1
		
		int[] lis = new int[N+1]; // DP[]
		lis[1] = 1;
		for (int i = 1; i <= N; i++) {
			//if (i < 5) System.out.println("i: " + i);
			for (int j = i-1; j >= 0; j--) {
				if (a[j] < a[i]) {
					// if you can attach it, see if it'll be bigger, then do it
					lis[i] = Math.max(lis[i], lis[j] + 1);
					/*if (i < 5) {
						System.out.println(lis[i] + " vs " + (lis[j]+1));

					}*/
					//System.out.println("lis j : " + lis[j]);
				}
			}
			//System.out.println("l: " + i + "  " + lis[i]);
		}
		
		// answer: get the biggest number in the array
		int max = 0;
		for (int i = 1; i <= N; i++) {
			if (lis[i] > max) {
				
				max = lis[i];
			}
		}
		//System.out.println(Arrays.toString(lis));
		System.out.println(max);
	}
}
