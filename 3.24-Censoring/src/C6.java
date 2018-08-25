import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Gold
 * Problem 2. Censoring (Gold)
 * 8/23
 * 
 * 8/15 correct
 * attempt to reduce recalculation by storing old versions of toVisit
 * added bsearch in the main loop
 * a bit faster...
 * 
 * stop deleting stuff and just build the string up from the ground
 */
public class C6 {
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

		// Tree
		adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < totLen; i++) 
			adj.add(new ArrayList<Integer>()); // init
		nodes = new char[totLen]; // nodes[i] = character
		
		int id = 1; // cur id (root node is 0, reserved)
		
		for (int i = 0; i < N; i++) { // loop through  words
			String w = C[i];
			
			int ind = existsUpTo(w); // index 0...ind of w exist in the tree
			String sub = w.substring(0, ind+1);
			int stub = exists(sub); // the end of sub in the tree, where to start adding
			
			for (int j = ind+1; j < w.length(); j++) {
				char cur = w.charAt(j);
				nodes[id] = cur; // create node
				
				int addind = leastAbove(adj.get(stub), nodes[id]);
				adj.get(stub).add(addind, id); // add to tree
				stub = id; // update stub
				id++;
			}
		}

		// nodes to visit
		LinkedList<State> toVisit = new LinkedList<State>();
		LinkedList<State> toVisit2;
		// history
		HashMap<Integer, LinkedList<State>> prevs = new HashMap<Integer, LinkedList<State>>();

//		String ret = ""; // string to return
//		ret += S.charAt(0);
		
		int i = 0; // index in ret
		while (i < S.length()) { // loop through S
			char nextChar = S.charAt(i); // char to look for
			
//			System.out.println("i: " + i + " char: " + nextChar);
//			
			LinkedList<State> toVisitCopy = new LinkedList<State>(); // copy of toVisit
			toVisit2 = new LinkedList<State>(); // next round of toVisit
			
			int wordStart = -1; // start of the word that was found, -1 if N/A
			
			while (!toVisit.isEmpty()) { // loop through prev states
				State ns = toVisit.removeFirst(); // stepping off point
				toVisitCopy.add(ns);
				int nodeID = ns.nodeID;
	
//				System.out.println("  nodeID: " + nodeID + " start: " + ns.start);
				
				
				int ind = bSearch(adj.get(nodeID), nextChar); // look for next char
				
				if (ind != -1) {
					int neighNode = adj.get(nodeID).get(ind);
					if (adj.get(neighNode).isEmpty()) { // if it's the end of a word		
											
						S = S.substring(0, ns.start) + S.substring(i+1);
						
//						ret = ret.substring(0, ns.start);
						
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
						S = S.substring(0, i) + S.substring(i+1);
						
						wordStart = i;
					} else {
						toVisit2.add(new State(neighNode, i));
					}
					
				}
			}
			
			if (wordStart == -1) {
				toVisit = toVisit2;
				
				prevs.put(i, toVisitCopy);
				
				i++;
				
			} else { // found a word!
				i = wordStart-1; // move the pointer back
				i = Math.max(0, i);
				toVisit = prevs.get(i); // set toVisit
			}
		}
		
		
//		System.out.println(ret);
//		System.out.println(S);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

		pw.println(S);
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
