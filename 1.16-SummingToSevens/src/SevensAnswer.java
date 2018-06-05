import java.io.*;
import java.util.*;

// straight from USACO

public class SevensAnswer {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("div7.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("div7.out")));
		
		int n = Integer.parseInt(br.readLine());
		int[] first = new int[7];
		int[] last = new int[7];
		Arrays.fill(first, Integer.MAX_VALUE);
		first[0] = 0;
		int currPref = 0;
		for(int i = 1; i <= n; i++) {
			currPref += Integer.parseInt(br.readLine());
			currPref %= 7;
			first[currPref] = Math.min(first[currPref], i);
			last[currPref] = i;
		}
		int ret = 0;
		for(int i = 0; i < 7; i++) {
			if(first[i] <= n) {
				ret = Math.max(ret, last[i] - first[i]);
			}
		}
		pw.println(ret);
		pw.close();
	}
}