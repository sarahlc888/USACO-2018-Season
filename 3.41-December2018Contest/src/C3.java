import java.io.*;
import java.util.*;
/*
 * 
 */
public class C3 {
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

		int sum = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i] > 0) sum += count[i]-1;
		}
		sum /= 2;
		long ret = N*(N-1)/2-sum;
		
		System.out.println(ret);
		
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
