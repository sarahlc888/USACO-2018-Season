import java.io.*;
import java.util.*;

// dynamic programming: at each step either break out or don't break out

public class Taming {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("taming.in"));
		int N = Integer.parseInt(br.readLine()); // num days in log
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] log = new int[N];
		for (int i = 0; i < N; i++) { // scan everything in
			log[i] = Integer.parseInt(st.nextToken());
			System.out.println(log[i]);
		}
		br.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));

		pw.println();
		pw.close();
	}


}

