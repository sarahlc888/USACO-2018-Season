import java.io.*;
import java.util.*;
/*
 * 2/10 test cases on this blind bash
 */
public class BarnPaintingBash {
	static int N;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("barnpainting.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num barns
		int K = Integer.parseInt(st.nextToken()); // num barns already painted
		br.close();
		
		long total = 1;
		for (int i = 0; i < N-K; i++) {
			total = (total * 2)%1000000007;
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));

		pw.println(total);
		pw.close();
		
	}
}
