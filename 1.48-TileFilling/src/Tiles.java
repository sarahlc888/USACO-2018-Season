import java.io.*;
import java.util.*;

public class Tiles {
	static int[] D;
	static int[] A;
	public static void main(String[] args) {
		int N = 13;
		D = new int[N];
		A = new int[N];
		Arrays.fill(D, -1);
		Arrays.fill(A, -1);
		A[0] = 0;
		A[1] = 1;
		D[0] = 1;
		D[1] = 0;
		
		System.out.println(rD(12));
		System.out.println(noRec(12));
	}
	/*public static int recD(int n) {
		D[n] = 2*A[n-1]+D[n-2];
		return D[n];
	}
	public static int recA(int n) {
		A[n] = D[n-1] + A[n-2];
		return A[n];
	}*/
	public static int rD(int n) {
		
		if (D[n] == -1) {
			
			D[n] = 2*rA(n-1)+rD(n-2);
			//System.out.println("run --> D[" + n + "]: " + D[n]);
		} else {
			//System.out.println("D[" + n + "]: " + D[n]);
		}
		return D[n];
	}
	
	public static int rA(int n) {
		
		if (A[n] == -1) {
			A[n] = rD(n-1) + rA(n-2);
			//System.out.println("run --> A[" + n + "]: " + A[n]);
		} else {
			//System.out.println("A[" + n + "]: " + A[n]);
		}
		return A[n];
	}
	
	public static int noRec(int n) {
		for (int i = 2; i <= n; i++) {
			D[i] = D[i - 2] + 2 * A[i - 1];
	        A[i] = D[i - 1] + A[i - 2];
		}
		return D[n];
		 
	}
	
}
