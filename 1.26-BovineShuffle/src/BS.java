import java.io.*;
import java.util.*;

// all ten test cases

public class BS {
	static int N;
	static int[] shuffleOrder;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
		N = Integer.parseInt(br.readLine()); // number of cows
		
		//System.out.println(N);
		
		shuffleOrder = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) { // scan everything in
			shuffleOrder[i] = Integer.parseInt(st.nextToken()) - 1;
			//System.out.println(shuffleOrder[i]);
		}
		int[] endcows = new int[N];
		StringTokenizer st2 = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) { // scan everything in
			endcows[i] = Integer.parseInt(st2.nextToken());
			//System.out.println(endcows[i]);
		}
		br.close();

		
		int[] original = unshuffle(unshuffle(unshuffle(endcows)));
		
		for (int i = 0; i < N; i++) {
			//System.out.println(original[i]);
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));
		for (int i = 0; i < N; i++) {
			pw.println(original[i]);
		}
		
		pw.close();
	}

	public static int[] unshuffle(int[] curcows) {
		int[] oldcows = new int[N];
		for (int i = 0; i < N; i++) {
			oldcows[i] = curcows[shuffleOrder[i]];
		}
		return oldcows;
		
	}
}
