import java.io.*;
import java.util.*;
/*
 * brute force
 */
public class C2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowpatibility.in"));
		int N = Integer.parseInt(br.readLine());
		
		int[][] nums = new int[N][6]; // index 5 is the group
		int[] count = new int[1000001];

		for (int i = 0; i < N; i++) { // scan everything in
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				nums[i][j] = Integer.parseInt(st.nextToken());
				count[nums[i][j]]++;
			}
			nums[i][5] = -1;
		}
		br.close();

		// count distinct
		boolean[] distinct = new boolean[N];
		for (int i = 0; i < N; i++) { // scan everything in
			boolean allDistinct = true;
			for (int j = 0; j < 5; j++) {
				if (count[nums[i][j]] > 1) {
					allDistinct = false;
					break;
				}
			}
			if (allDistinct) {
				distinct[i] = true;
			}
		}
		
		// compare between nums
		long compatibleCt = 0;
		for (int i = 0; i < N; i++) {
			if (distinct[i]) continue;
 			for (int j = i+1; j < N; j++) {
 				if (distinct[j]) continue;
				
here:			for (int p = 0; p < 5; p++) {
 					for (int q = 0; q < 5; q++) {
 						if (nums[i][p] == nums[j][q]) { // compatible bc they share a number
 							compatibleCt++;
 							break here;
 						}
 					}
 				}
// 				if (comp) compatibleCt++;
						

 			}
		}

//		System.out.println("cc: " + compatibleCt);
		
//		System.out.println("cc: " + compatibleCt);
		
		long ret = N*(N-1)/2-compatibleCt;
		
//		System.out.println(ret);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		pw.println(ret);
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int num;
		int ct;

		public Pair(int a, int b) {
			num = a;
			ct = b;
		}
		@Override
		public int compareTo(Pair o) { // sort ct descending
			return o.ct-ct;
		}
		public String toString() {
			return num + " " + ct;
		}
	}


}
