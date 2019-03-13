import java.io.*;
import java.util.*;
/*
 * min spanning tree
 */
public class S5MSS {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("file.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num planets
		int M = Integer.parseInt(st.nextToken()); // num cities on each planet
		int P = Integer.parseInt(st.nextToken()); // num flights (two way) on each planet
		int Q = Integer.parseInt(st.nextToken()); // num portals in each city
		
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		int[][] arr2 = new int[N][2];
		for (int i = 0; i < N; i++) { // scan everything in
			
			arr2[i][0] = Integer.parseInt(st.nextToken());
			arr2[i][1] = Integer.parseInt(st.nextToken());
		}
		br.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

		pw.println();
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
