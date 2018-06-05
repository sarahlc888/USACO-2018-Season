import java.io.*;
import java.math.BigInteger;
import java.util.*;
/*
 * 
 */
public class Reverse {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("file.in"));
		
		int N = 100;
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		br.close();

		
		for (int i = N-1; i >= 0; i--) {
			//System.out.println(arr[i]);
			String x = String.valueOf(arr[i]);
			
			String a = new BigInteger(x, 10).toString(16);
			System.out.println(a);
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

		pw.println();
		pw.close();
	}


}
