import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 1. Censoring (Silver)
 * 
 * trying to make c4 faster (can't do all possibilities always, have to use knuth)
 * 
 * 9 test cases
 * 
 * the fallback system is wrong 
 */
public class C5 {

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
//		BufferedReader br = new BufferedReader(new FileReader("4.in"));
		String S = br.readLine(); // string
		String T = br.readLine(); // substring to censor
		br.close();

		// if the current "thread" doesn't work out, go to table[i]+1 and keep tracing
		// mostly copied from wikipedia 
		// https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm#%22Partial_match%22_table_(also_known_as_%22failure_function%22)
		int[] table = new int[T.length()+1];
		int pos = 1;
		int cnd = 0;
		table[0] = -1;
		while (pos < T.length()) {
//			System.out.println("pos: " + pos + " " + T.charAt(pos) + "  cnd: " + cnd + " " + T.charAt(cnd));
			if (T.charAt(pos) == T.charAt(cnd)) {
				table[pos] = table[cnd];
			} else {
				table[pos] = cnd;
				cnd = table[cnd];
				while (cnd >= 0 && T.charAt(pos) != T.charAt(cnd)) {
					cnd = table[cnd];
				}
			}
			pos++;
			cnd++;
//			
		}
		table[pos] = cnd;
		table[0] = -1;
//		
//		System.out.println(T);
//		System.out.println(Arrays.toString(table));
		
		// track all states at all times
		int[] states = new int[S.length()];
		for (int i = 0; i < S.length(); i++) {
			states[i] = -1;
		}
		
		char[] retArr = new char[S.length()];
		int retInd = 0;
//		System.out.println(T);
here:	for (int ind = 0; ind < S.length(); ind++) {
			char curChar = S.charAt(ind);
			retArr[retInd] = curChar;
			retInd++;

			states[retInd-1] = -1;

//			System.out.print("ind: " + ind + " ret: ");
//			for (int i = 0; i < retInd; i++) {
//				System.out.print(retArr[i]);
//			}
//			System.out.println();

			
			if (retInd-2 >= 0) { // if there is content before it
				int prevState = states[retInd-2];
//				System.out.println("  prevState: " + prevState + " " + retArr[retInd-2] + "  nowChar: " + retArr[retInd-1] + " " + curChar);

				// check for the last character
				if (prevState == T.length()-2) {
					if (curChar == T.charAt(T.length()-1)) { 
						// last character is present! the word ends!
//						System.out.println("    WORD ENDS");
						retInd -= T.length();
						continue here; // go to the next index
					}
				}
				// check for the middle of T (continuation from prev index)
				if (curChar == T.charAt(prevState+1)) {
					states[retInd-1] = prevState+1;
				} else {
					int fallback = table[prevState+1];
					
					while (true) {
//						System.out.println("    fb: " + fallback);
						if (fallback == -1) {
							if (curChar == T.charAt(0)) {
//								System.out.println("    fallback: " + fallback);
								states[retInd-1] = 0;
							}
							break;
						}
						if (curChar == T.charAt(fallback)) {
//							System.out.println("    fallback: " + fallback);
							states[retInd-1] = fallback;
							break;
						}
						int newfallback = table[fallback];
//						System.out.println("    new fb: " + newfallback);
						if (newfallback == fallback) break;
						fallback = newfallback;
					}
				}
			} else {
				int prevState = -1;
//				System.out.println("  prevState: " + prevState + "  nowChar: " + retArr[retInd-1]);
				if (curChar == T.charAt(prevState+1)) { // check for first character
					states[retInd-1] = prevState+1;
				}
				// check for last character
				if (prevState == T.length()-2) {
					if (curChar == T.charAt(T.length()-1)) { 
						// last character is present! the word ends!
//						System.out.println("    WORD ENDS");
						retInd -= T.length();
						continue here; // go to the next index
					}
				}
			}
		}
//		System.out.println(retInd);
//		System.out.println(S.length());
//		System.out.println(T.length());
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		for (int i = 0; i < retInd; i++) {
//			System.out.print(retArr[i]);
			pw.print(retArr[i]);
		}
		pw.close();
	}
	public static class State {
		ArrayList<Integer> s = new ArrayList<Integer>();
		public State() {
		}
	}
}
