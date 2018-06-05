import java.io.*;
import java.util.*;

public class FileCompare {
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 10; i++) {
			boolean identical = true;
			BufferedReader br = new BufferedReader(new FileReader("testData/" + (i+20) + ".in"));
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // number of points
			int Q = Integer.parseInt(st.nextToken()); // number of queries
			BufferedReader br1 = new BufferedReader(new FileReader("testData/" + (i+20) + ".out"));
			BufferedReader br2 = new BufferedReader(new FileReader(i+".out"));
			for (int j = 0; j < Q; j++) {
				int a = Integer.parseInt(br1.readLine());
				int b = Integer.parseInt(br2.readLine());
				if (a != b) identical = false;
			}
			System.out.println(identical);
		}
		
	}
}
