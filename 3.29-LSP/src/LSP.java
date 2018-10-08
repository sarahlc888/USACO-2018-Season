import java.io.*;
import java.util.*;
/*
 * longest suffix prefix: longest string at front and back of input string
 * cannot equal the string itself
 * 
 * 10/2/18 lesson, see screenshot folder for idea
 * works, not thoroughly tested
 */
public class LSP {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("lsp.in"));
		String s = br.readLine();
		br.close();

		int[] lsp = new int[s.length()]; // length of LSP of substring 0...i (inclusive)
		lsp[0] = 0; // basecase
		
		for (int i=1; i<s.length(); i++) {
			int prevlen = lsp[i-1]; // length of previous LSP
			
			System.out.println("i: " + i + " prevlen: " + prevlen);
			
			while (prevlen > 0 && s.charAt(prevlen) != s.charAt(i)) {
				// if the string demarcated by prevlen is no longer valid 
				// (the prefix and suffix diverge)
				// backtrack example: aba d aba where LSP is aba
				// aba d aba b would break the pattern and enter the while loop
				// the while loop would get the lsp for 'aba' which is a
				// and then check if that one stands. if not, continue. crash wall is 0
				
				prevlen = lsp[prevlen-1];
				System.out.println("  back up to " +prevlen);
			}
			if (s.charAt(prevlen)==s.charAt(i)) { // inc if the next letter matches
				System.out.println("  increment");
				prevlen++;
			}
			lsp[i]=prevlen;
		}
		
		System.out.println(s.substring(0, lsp[s.length()-1]));
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lsp.out")));
		pw.println(s.substring(0, lsp[s.length()-1]));
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
