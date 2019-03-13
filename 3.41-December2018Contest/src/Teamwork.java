import java.io.*;
import java.util.*;
/*
 * 
 */
public class Teamwork {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("teamwork.in"));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num cows
		int K = Integer.parseInt(st.nextToken()); // max team size
		int[] skill = new int[N]; // skill levels
		for (int i = 0; i < N; i++) {
			skill[i] = Integer.parseInt(br.readLine());
		}
		br.close();
//		System.out.println(Arrays.toString(skill));
		// DP[i] = max skill sum for up to index i
		int[] DP = new int[N];
		DP[0] = skill[0];
		int curmax = skill[0];
		for (int i = 0; i < K; i++) { // basecases
			// when just 1 team, just take the max
			if (skill[i] > curmax) curmax = skill[i];
			DP[i] = curmax*(i+1);
		}
//		System.out.println(Arrays.toString(DP));
		for (int i = K; i < N; i++) { // calculate normal cases
//			System.out.println("i: " + i);
			int maxsum = 0;
			
			curmax = skill[i];
			
			for (int j = i-1; j >= i-K; j--) { // last group ended at j
//				System.out.println("  j: " + j);
				
				int cursum = DP[j] + curmax*(i-j);
//				System.out.println("    " + cursum);
				if (cursum > maxsum) maxsum = cursum;
				
				// update curmax here because this is not included in the i group (last element of j group)
				if (skill[j] > curmax) curmax = skill[j]; 
				
			}
			DP[i] = maxsum;
		}
		
//		System.out.println(Arrays.toString(DP));
//		System.out.println(DP[N-1]);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));

		pw.println(DP[N-1]);
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
