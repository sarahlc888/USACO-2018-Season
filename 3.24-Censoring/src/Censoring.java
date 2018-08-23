import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Gold
 * Problem 2. Censoring (Gold)
 * 8/23
 */
public class Censoring {
	static ArrayList<ArrayList<Integer>> adj;
	static int[] par;
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
		
		System.out.println(S);
		System.out.println(Arrays.toString(C));
		
		// Tree
		adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < totLen; i++) 
			adj.add(new ArrayList<Integer>()); // init
		par = new int[totLen];
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
//				System.out.println("    cur: " + cur + " stub: " + stub);
				nodes[id] = cur; // create node
				adj.get(stub).add(id); // add to tree
				par[id] = stub; // mark parent
				stub = id; // update stub
				id++;
			}
		}
		
		System.out.println("adj: " + adj);
		System.out.println("nodes: " + Arrays.toString(nodes));
		
		boolean[] remove = new boolean[S.length()];
		
		// nodes to visit
		LinkedList<State> toVisit = new LinkedList<State>();
		LinkedList<State> toVisit2;
		
		for (int i = 0; i < S.length(); i++) { // loop through S
			
			toVisit2 = new LinkedList<State>(); // next round of toVisit
			
			char nextChar = S.charAt(i); // char to look for
			
			System.out.println("i: " + i + " nextchar: " + nextChar);
			
			while (!toVisit.isEmpty()) { // loop through prev states
				State ns = toVisit.removeFirst(); // stepping off point
				int nodeID = ns.nodeID;
				
				System.out.println("  nodeid: " + nodeID);
				
				
				for (int j = 0; j < adj.get(nodeID).size(); j++) {
					int neighNode = adj.get(nodeID).get(j);
					char neighChar = nodes[neighNode];
					if (neighChar == nextChar) {
//						System.out.println("c is in string: " + c);
						if (adj.get(neighNode).isEmpty()) {
							// if it's the end of a word
							System.out.println("END OF A WORD");
							
							for (int k = ns.start; k <= i; k++) {
								remove[k] = true;
							}
							
						}
						toVisit2.add(new State(neighNode, ns.start));
					}
				}
			}
			
			// see if this new char can be the start of anything
			int nodeID = 0; // start
			
			for (int j = 0; j < adj.get(nodeID).size(); j++) {
				int neighNode = adj.get(nodeID).get(j);
				char neighChar = nodes[neighNode];
				if (neighChar == nextChar) {
//					System.out.println("c is in string: " + c);
					if (adj.get(neighNode).isEmpty()) {
						// if it's the end of a word (already)
						System.out.println("END OF A WORD");
						remove[i] = true;
					}
					toVisit2.add(new State(neighNode, i));
				}
			}
			
			
			toVisit = toVisit2;
		}
		
		
		System.out.println(Arrays.toString(remove));
		String ret = "";
		for (int i = 0; i < S.length(); i++) {
			if (!remove[i]) ret += S.charAt(i);
		}
		System.out.println(ret);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

		pw.println();
		pw.close();
	}
	
	public static int exists(String s) {
		// if string s exists in the current tree, return the end of path there
		// -1 means doesn't exist
		int cur = 0;
		
		for (int i = 0; i < s.length(); i++) {
			boolean found = false;
			for (int j = 0; j < adj.get(cur).size(); j++) {
				int neigh = adj.get(cur).get(j);
				char c = nodes[neigh];
				if (c == s.charAt(i)) {
//					System.out.println("c is in string: " + c);
					cur = neigh;
					found = true;
					break;
				}
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
			boolean found = false;
			for (int j = 0; j < adj.get(cur).size(); j++) {
				int neigh = adj.get(cur).get(j);
				char c = nodes[neigh];
				if (c == s.charAt(i)) {
					cur = neigh;
					found = true;
					break;
				}
			}
			if (!found) return i-1;
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
