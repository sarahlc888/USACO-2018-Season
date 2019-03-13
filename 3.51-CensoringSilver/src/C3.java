import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Silver
 * Problem 1. Censoring (Silver)
 * 
 * linked list
 * build up instead of substring
 * 
 * IDEA IS WRONG!
 * linked list logic won't work
 * 
 */
public class C3 {
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		String T = br.readLine(); // substring to censor
		br.close();
		
		char first = T.charAt(0);

		// track the last T+2 characters at all times
		LinkedList<ArrayList<Integer>> track = new LinkedList<ArrayList<Integer>>();
//		for (int i = 0; i < T.length(); i++) {
//			track.add(new ArrayList<Integer>());
//		}
		track.add(new ArrayList<Integer>());
//		int[] prev = new int[S.length()]; // prev[i] = index that precedes i
//		prev[0] = -2; // mark as start
//		for (int i = 1; i < S.length(); i++) {
//			prev[i] = i-1;
//		}
		
//		String ret = "";
		char[] retArr = new char[S.length()];
		int retInd = 0;
		System.out.println(S);
here:	for (int ind = 0; ind < S.length(); ind++) {
			char curChar = S.charAt(ind);
			retArr[retInd] = curChar;
			retInd++;
//			ret = String.join("", ret, ""+curChar);
//			ret += curChar;
			System.out.println(Arrays.toString(retArr) + " retind: " + retInd);
//			System.out.println("ret: " + ret);
//			System.out.println("ind: " + ind + " curChar: " + curChar + " S: " + S);
			System.out.println("  " + track);
			
			// check for the last character
			ArrayList<Integer> prevInds = track.peekLast();
			for (int i = prevInds.size()-1; i >= 0; i--) {
				if (prevInds.get(i) == T.length()-2) {
					if (curChar == T.charAt(T.length()-1)) { 
						// last character is present! the word ends!
						retInd -= T.length();
						System.out.println("SNIP: " + S);
						
						// reset track
						ArrayList<Integer> preserve = track.peekFirst();
						track = new LinkedList<ArrayList<Integer>>();
						track.addLast(preserve);
						
						continue here; // go to the next index
					}
					break;
				}
			}
			if (track.size() == T.length())
				track.removeFirst();
			track.add(new ArrayList<Integer>());
			
			// check for the middle of T (continuation from prev index)
			for (int i = 0; i < prevInds.size(); i++) {
				int lastInd = prevInds.get(i);
				if (curChar == T.charAt(lastInd+1)) { // check for the next character
					track.peekLast().add(lastInd+1);
				}
			}
			// check for the start of T
			if (S.charAt(ind)==first) { // mark start of word
				//System.out.println("  start");
				track.peekLast().add(0);
			}
			System.out.println("  " + track);
		}
//		System.out.println(track);
//		System.out.println(Arrays.toString(prev));
		
		
		System.out.println();
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		for (int i = 0; i < retInd; i++) {
			System.out.print(retArr[i]);
			pw.print(retArr[i]);
		}
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
