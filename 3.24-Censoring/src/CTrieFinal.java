import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Gold
 * Problem 2. Censoring (Gold)
 * 8/23
 * 
 * Aho-Corasick algo
 * 
 * 8/15 correct in C3 but only 5 here (times out)
 * 
 * copied off of the answer basically
 * times out
 */
public class CTrieFinal {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		int N = Integer.parseInt(br.readLine()); // num censored words
		String[] C = new String[N]; // list of words
		for (int i = 0; i < N; i++) {
			C[i] = br.readLine();
		}
		br.close();
		Arrays.sort(C, (a, b) -> Integer.compare(a.length(), b.length()));

		// Trie
		Node root = new Node(); // root of the trie
		ArrayList<Node> trie = new ArrayList<Node>(); // keep track of end of C[i] so far
		for (int i = 0; i < N; i++) { // start everything at the root
			trie.add(root);
		}
		
		int j = 0; // index in C (goes up with size)
		for (int i = 0; j < C.length; i++) { // loop through chars
			for (int k = j; k < N; k++) { // loop through words
				String word = C[k];
				if (i >= word.length()) continue; // catch oob
				char ch = C[k].charAt(i);
				int chval = ch-'a';
				
//				System.out.println("word: " + word + " ind: " + i + " char: " + ch);
				
				Node curnode = trie.get(k); // get end of the word so far
				
				// if ch does not exist in trie, initialize
				if (curnode.next[chval] == null) {
//					System.out.println("  init");
					curnode.next[chval] = new Node();
				}
					
		
				curnode.next[chval].suff = curnode.suff; // transfer suff to the next node (temporary, will update)
				curnode = curnode.next[chval]; // move to the proper node
				
				// identify proper suff
				while (curnode.suff != null && curnode.suff.next[chval] == null) {
					// while current node has suffix 
					// while the suff doesn't have the chval as a child
					// move up to the next suff
					curnode.suff = curnode.suff.suff;
				}
				if (curnode.suff != null) { // if a suffix is found, update it to include ch
					curnode.suff = curnode.suff.next[chval];
				} else { // if no suffix, make it the root
					curnode.suff = root;
				}
				
				curnode.c = ch;
				
				trie.set(k, curnode); // update end of the word so far
			}
			while (j < C.length && i == C[j].length()-1) {
				// while j is in range and while i is the last index of C[j]
				trie.get(j).ind = j; // mark it as a completed word
				j++;
			}
		}

		
		// go through string S
		String ret = "";
		List<Node> trie2 = new ArrayList<Node>(); 
		trie2.add(root);
		
		
		for (int i = 0; i < S.length(); i++) {
			
			char ch = S.charAt(i);
			int chval = ch-'a';
			Node n = trie2.get(trie2.size()-1).step(chval); // look for chval off of the last node n
			ret += ch;
			trie2.add(n);
			
//			System.out.println("i: " + i + " ch: " + ch);
			
			if (n.ind != -1) {
				// if this is the end of a word
//				System.out.println("  END of word " + C[n.ind]);
				ret = ret.substring(0, ret.length()-C[n.ind].length());
//				System.out.println("  ret: " + ret);
				trie2 = trie2.subList(0, ret.length()+1);
//				System.out.println("  trie size: " + trie2.size());
			}
		}
		
//		System.out.println(ret);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

		pw.println(ret);
		pw.close();
	}
	public static class Node { // node in the trie

		char c;
		int ind = -1; // index in C
		Node[] next; // children
		Node suff; // "fall" in the solution

		public Node() {
			next = new Node[26]; // 26 child characters
			
		}
		public Node step(int ch) {
			// proceed from cur node to character 'ch'
//			System.out.println("  find char: " + ch + " from char: " + c);
			if (next[ch] != null) { // if ch is already present
//				System.out.println("    present");
				return next[ch];
			} else if (suff != null) { // if there is suff, go to suff
//				System.out.println("    to suff " + suff.c);
				return suff.step(ch);
			} else { // NO CH ANYWHERE
//				System.out.println("    absent");
				return this;
			}
		}
//		public String toString() {
//			return "ind: " + ind + " suff: " + suff + " next: [" + Arrays.toString(next);
//		}
	}


}
