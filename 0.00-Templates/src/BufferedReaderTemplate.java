import java.io.*;
import java.util.*;
/*
 * 
 */
public class BufferedReaderTemplate {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("file.in"));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		int[][] arr2 = new int[N][2];
		for (int i = 0; i < N; i++) { // scan everything in
			StringTokenizer st = new StringTokenizer(br.readLine());
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
