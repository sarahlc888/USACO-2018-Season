import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 1. Censoring (Silver)
 * 
 * deleted linked list (must store all states)
 * build up instead of substring
 * 
 * 8 cases
 */
public class C4 {
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		String T = br.readLine(); // substring to censor
		br.close();
		
		char first = T.charAt(0);

		// track all states at all times
		State[] states = new State[S.length()];
		
		char[] retArr = new char[S.length()];
		int retInd = 0;

here:	for (int ind = 0; ind < S.length(); ind++) {
			char curChar = S.charAt(ind);
			retArr[retInd] = curChar;
			retInd++;

			states[retInd-1] = new State();
			
//			System.out.print("ret: ");
//			for (int i = 0; i < retInd; i++) {
//				System.out.print(retArr[i]);
//			}
//			System.out.println();
			
			if (retInd-2 >= 0 && states[retInd-2].s.size() != 0) {
				ArrayList<Integer> prevInds = states[retInd-2].s;
//				System.out.println("  previnds: " + prevInds);
				
				states[retInd-1] = new State();
				
				// check for the last character
				if (prevInds.get(0) == T.length()-2) {
					if (curChar == T.charAt(T.length()-1)) { 
						// last character is present! the word ends!
						retInd -= T.length();
//						System.out.println("SNIP: " + S);

						continue here; // go to the next index
					}
				}
				// check for the middle of T (continuation from prev index)
				for (int i = 0; i < prevInds.size(); i++) {
					int lastInd = prevInds.get(i);
					if (lastInd == T.length()-2) continue;
					if (curChar == T.charAt(lastInd+1)) { // check for the next character
						states[retInd-1].s.add(lastInd+1);
					}
				}
			}
			
			// check for the start of T
			if (S.charAt(ind)==first) { // mark start of word
				states[retInd-1].s.add(0);
			}
//			System.out.println("  " + track);
}
//		System.out.println(track);
//		System.out.println(Arrays.toString(prev));
		
		
//		System.out.println();
		
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
