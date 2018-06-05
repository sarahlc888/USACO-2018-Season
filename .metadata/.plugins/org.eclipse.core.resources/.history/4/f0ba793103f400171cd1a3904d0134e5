import java.io.*;
import java.util.*;

public class FileCompare {
	public static void main(String args[]) throws IOException {
		for (int i = 1; i <= 6; i++) {
			boolean identical = true;
			BufferedReader br = new BufferedReader(new FileReader("testData/adjW" + i + ".in"));
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // number of output lines
			BufferedReader br1 = new BufferedReader(new FileReader("testData/adjW" + i + ".out"));
			BufferedReader br2 = new BufferedReader(new FileReader("2" + i+".out"));
			for (int j = 0; j < N; j++) {
				int a = Integer.parseInt(br1.readLine());
				int b = Integer.parseInt(br2.readLine());
				if (a != b) identical = false;
			}
			System.out.println(identical);
		}
		
	}
}
