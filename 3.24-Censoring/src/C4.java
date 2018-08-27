import java.io.*;
import java.util.*;

/*
 * USACO 2015 February Contest, Gold
 * Problem 2. Censoring (Gold)
 * 8/23
 * 
 * formerly 8/15 correct
 * DROPPED, NOW ONLY 5/15!?
 * 
 * up to 6 with sublist instead of substring
 */
public class C4 {
	static ArrayList<ArrayList<Integer>> adj;
	static char[] nodes;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		int N = Integer.parseInt(br.readLine()); // num censored words
		String[] C = new String[N]; // list of words
		int longest = 0;
		int totLen = 0;
		for (int i = 0; i < N; i++) {
			C[i] = br.readLine();
			longest = Math.max(longest, C[i].length());
			totLen += C[i].length();
		}
		br.close();

		Arrays.sort(C);
		
		//System.out.println(S);
		//System.out.println(Arrays.toString(C));
		

		// Tree
		adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < totLen; i++) 
			adj.add(new ArrayList<Integer>()); // init
		nodes = new char[totLen]; // nodes[i] = character
		int source = 0; // first node
		
		int id = 1; // cur id
		
		for (int i = 0; i < N; i++) { // loop through  words
			String w = C[i];
			// index 0...ind of w exist in the tree
			int ind = existsUpTo(w); 
			String sub = w.substring(0, ind+1);
			// in the tree, end of where w sub, where to start adding
			int stub = exists(sub); 
			
//			System.out.println("word: " + w);
//			System.out.println("  ind: " + ind);
//			System.out.println("  stub: " + stub);
			
			for (int j = ind+1; j < w.length(); j++) {
				char cur = w.charAt(j);
//				//System.out.println("    cur: " + cur + " stub: " + stub);
				nodes[id] = cur; // create node
				
//				System.out.println(adj.get(stub) + "  " + cur);
//				if (adj.get(stub).size() > 0) System.out.println(nodes[adj.get(stub).get(0)]);
				
				int addind = leastAbove(adj.get(stub), nodes[id]);
				adj.get(stub).add(addind, id); // add to tree
				stub = id; // update stub
				id++;
			}
//			System.out.println("adj: " + adj);
//			System.out.println("nodes: " + Arrays.toString(nodes));
		}

		//System.out.println("adj: " + adj);
		//System.out.println("nodes: " + Arrays.toString(nodes));
		
		
		// nodes to visit
		LinkedList<State> toVisit = new LinkedList<State>();
		LinkedList<State> toVisit2;
		
		
		List<Character> ret = new ArrayList<Character>();
		
		int i = 0;
		int i2 = 0; // furthest one visited so far
		
		boolean catchUp = false;
		
		while (i < S.length() && i2 < S.length()) { // loop through S
			
			if (i >= ret.size()) catchUp = false;
			if (i >= ret.size() && !catchUp) {
				
				ret.add( S.charAt(i2) ); // only add if it's the furthest one so far, don't repeat when i goes back on itself
			}
			
			
			toVisit2 = new LinkedList<State>(); // next round of toVisit
			char nextChar = ret.get(i); // char to look for
		
//			System.out.println("i: " + i + " i2: " + i2 + " nextchar: " + nextChar);
//			System.out.println("  ret: " + ret);
//			System.out.println("  catchup: " + catchUp);
			
			int wordStart = -1;
			
			while (!toVisit.isEmpty()) { // loop through prev states
				State ns = toVisit.removeFirst(); // stepping off point
				int nodeID = ns.nodeID;
				

				int ind = bSearch(adj.get(nodeID), nextChar); // look for next char
				
				if (ind != -1) {
					int neighNode = adj.get(nodeID).get(ind);
					if (adj.get(neighNode).isEmpty()) { // if it's the end of a word		
											
//						S = S.substring(0, ns.start) + S.substring(i+1);
						ret = ret.subList(0, ns.start);
						
						wordStart = ns.start;
					}
					toVisit2.add(new State(neighNode, ns.start));
				}
				
				if (wordStart != -1) break;

			}
			
			if (wordStart == -1) {
				// see if cur char can be the start of any word
				int nodeID = 0; // root
				int ind = bSearch(adj.get(nodeID), nextChar); // look for next char
				
				if (ind != -1) { // if the character is present
					int neighNode = adj.get(nodeID).get(ind);
					if (adj.get(neighNode).isEmpty()) { // if the character is also the end of the word
//						S = S.substring(0, i) + S.substring(i+1);
						ret = ret.subList(0, i);
						wordStart = i;
					} else {
						toVisit2.add(new State(neighNode, i));
					}
					
				}
			}
			
			
			if (wordStart == -1) {
				toVisit = toVisit2;
				
				i++;
				
 			} else { // if found word
				i = wordStart-longest+1;
				i = Math.max(0, i);
				//System.out.println("new i: " + i);
				toVisit = new LinkedList<State>(); // next round of toVisit
				toVisit2 = new LinkedList<State>(); // next round of toVisit
				i2++;
				catchUp = true;
			}
			
			if (!catchUp) i2++;
			
		}
		
		
		//System.out.println(Arrays.toString(remove));
		
//		System.out.println(ret);
//		System.out.println(S);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
		for (i = 0; i < ret.size(); i++) {
			pw.print(ret.get(i));
//			System.out.print(ret.get(i));
		}
		pw.close();
	}
	public static int leastAbove(ArrayList<Integer> arr, char val) {
		// returns smallest i where nodes[i] >= char
		int lo = 0;
		int hi = arr.size()-1;
		
		if (arr.size() == 0) return 0;
		if (nodes[arr.get(hi)] < val) return arr.size();
		
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
			char c = nodes[arr.get(mid)];

			if (c >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
//		System.out.println("lo: " + lo);
//		System.out.println(nodes[arr.get(lo)]);
//		System.out.println(val);
		if (nodes[arr.get(lo)] >= val) return lo; // make sure lo works (catches rare exceptions)
		
		return arr.size();
	}
	public static int bSearch(ArrayList<Integer> arr, char val) {
		// returns i where nodes[i] = char
		int lo = 0;
		int hi = arr.size()-1;
		int mid = 0;
		
//		System.out.println("  arr: " + arr + " character: " + val);
		
		if (arr.size() == 0 || nodes[arr.get(lo)] > val || nodes[arr.get(hi)] < val) {
//			System.out.println("  ind: -1");
//			if (arr.size() > 0) {
//				System.out.println(nodes[arr.get(lo)] > val);
//				System.out.println(nodes[arr.get(hi)] < val);
//			} else {
//				System.out.println("empty array");
//			}
			
			return -1;
		}
		
		while (lo <= hi) {
			mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
//			System.out.println("  lo: " + lo + " hi: " + hi + " mid: " + mid);
			
			char c = nodes[arr.get(mid)];
			
			if (c > val) {
				hi = mid-1;
			} else if (c < val) {
				lo = mid+1;
			} else { // == val
				break;
			}
		}
		
//		System.out.println("  mid: " + mid);
		
		if (nodes[arr.get(mid)] == val) {
//			System.out.println("  ind: " + mid);
			return mid; // make sure it works
		}
//		System.out.println("  ind: -1");
		return -1;
	}
	public static int exists(String s) {
		// if string s exists in the current tree, return the end of path there
		// -1 means doesn't exist
		int cur = 0;
		
		for (int i = 0; i < s.length(); i++) {
			boolean found = false;
			
			int ind = bSearch(adj.get(cur), s.charAt(i));
			
			if (ind != -1) {
				int neighNode = adj.get(cur).get(ind);
				cur = neighNode;
				found = true;
			}
			
			if (!found) return -1;
		}
		return cur;
	}
	public static int existsUpTo(String s) {
		// returns x where indices 0...x inclusive exist in the tree
		// -1 means none exist
		int cur = 0;
		
		for (int i = 0; i < s.length(); i++) {
			int ind = bSearch(adj.get(cur), s.charAt(i));
			
			if (ind != -1) {
				cur = adj.get(cur).get(ind);
			} else {
				return i-1;
			}
		}
		return s.length()-1;
	}
	public static class State {
		int nodeID;
		int start;

		public State(int a, int b) {
			nodeID = a;
			start = b;
		}
		
		public String toString() {
			return nodeID + " " + start;
		}
	}


}