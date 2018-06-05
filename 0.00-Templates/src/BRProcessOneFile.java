import java.io.*;
import java.util.*;

public class BRProcessOneFile {
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <=10; i++) {
			String filename = "testData/" + i + ".in";
			processOneFile(filename, i);
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
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


		PrintWriter pw = new PrintWriter((new File("testData/" + k + ".out")));

		pw.println();
		pw.close();
	}


}
