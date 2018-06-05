import java.io.*;
import java.util.*;

// 10 test cases correct !!! USE BUFFERED READER - it's faster (the smarter version is Sevens.java)

public class Sevens2 {
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("div7.in"));
		int N = Integer.parseInt(br.readLine()); // number of cows
		int[] cows = new int[N]; // array of cow ids
		long[] cowsPS = new long[N+1]; // partial sum array, up to but not including the current index
		// cowsPS[i] = cowsPS[0] + ... + cowsPS[i-1]
		cowsPS[0] = 0; 
		for (int i = 0; i < N; i++) {
			cows[i] = Integer.parseInt(br.readLine());
			cowsPS[i+1] = cowsPS[i] + cows[i];
		}
		br.close();
		// go through all potential sizes of the group and all potential starting points
		// start from the biggest possibility. break and flip a boolean when the condition is fulfilled
		// if the boolean is never flipped, output 0. otherwise, output L
		
		boolean ansFound = false;
		
		int L;
		for (L = N; L > 0; L--) {
			// L = length of the group
			for (int start = 0; start <= N - L; start++) {
				// inclusive start and end
				int end = start + L - 1; 
				// use partial sum to calculate the sum
				long sum = cowsPS[end + 1] - cowsPS[start]; 
				if (sum%7==0) {
					// if the sum is divisible by 7
					ansFound = true;
					break;
				}
			}
			if (ansFound) {
				break;
			}
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("div7.out")));
		if (ansFound) {
			//System.out.println(L);
			pw.println(L);
		} else {
			//System.out.println(0);
			pw.println(0);
		}
		pw.close();
	}
}
