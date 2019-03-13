import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 1. Censoring (Silver)
 * 
 * tried to circumvent substring but it just made it slower
 * 
 * 2 cases
 */
public class C {
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		String T = br.readLine(); // substring to censor
		br.close();
		
		char first = T.charAt(0);

		ArrayList<ArrayList<Integer>> track = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < S.length(); i++) {
			track.add(new ArrayList<Integer>());
		}
		
		int[] prev = new int[S.length()]; // the prev index
		prev[0] = -2; // mark as start
		for (int i = 1; i < S.length(); i++) {
			prev[i] = i-1;
		}
		
		for (int ind = 0; ind < S.length(); ind++) {
			char curChar = S.charAt(ind);
//			System.out.println("ind: " + ind + " curChar: " + curChar + " prev: " + Arrays.toString(prev));
//			System.out.println(track);
			
			// check for the start of T
			if (S.charAt(ind)==first) { // mark start of word
//				System.out.println("  start");
				track.get(ind).add(0);
			}
			// check for the middle of T (continuation from prev index)
			// if not the first letter and not deleted
			if (prev[ind] <= -1) { 
				continue;
			}
			
			for (int i = 0; i < track.get(prev[ind]).size(); i++) {
				int lastInd = track.get(prev[ind]).get(i);
				if (curChar == T.charAt(lastInd+1)) { // check for the next character
//					System.out.println("  next char " + (lastInd+1));
					track.get(ind).add(lastInd+1);
					// check for the end of T
					if (lastInd+1 == T.length()-1) { // if that character is the last character
//						System.out.println("  last char");
						int charToDelete = ind;
						for (int j = 0; j < T.length(); j++) {
							// delete the last T characters
							int temp = charToDelete;
							charToDelete = prev[charToDelete];
							prev[temp] = -1;
						}
//						prev[ind+1] = ind-T.length();
						prev[ind+1] = charToDelete;
						break;
						// aaaaBBBBBa
						// 0123456789
					}
					
				}
			}
		}
		System.out.println(track);
//		System.out.println(Arrays.toString(prev));
		
		String ret = "";
		int ind = S.length()-1;
		while (ind >= 0) {
			if (prev[ind] != -1) {
				ret = S.charAt(ind)+ret;
				ind = prev[ind];
			} else {
				ind--;
			}
		}
		System.out.println(ret);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

		pw.println(ret);
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
