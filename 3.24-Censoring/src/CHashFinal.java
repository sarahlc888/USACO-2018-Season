import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Gold
 * Problem 2. Censoring (Gold)
 * 8/23
 * 
 * From solution
 * 4/15 test cases, gave up! Couldn't find library functions fast enough, I think?
 * 8/15 when replace substring with sublist
 */
public class CHashFinal {
	static int HM = 1000000007;
	static int HA = 100000007;
	static int HB = 101;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		int N = Integer.parseInt(br.readLine()); // num censored words
		String[] C = new String[N]; // list of words
		
		// stored by length and then by hash value
		TreeMap<Integer, TreeMap<Integer, ArrayList<String>>> m = new TreeMap<Integer, TreeMap<Integer, ArrayList<String>>>();
		
		for (int i = 0; i < N; i++) {
			C[i] = br.readLine();
			
			int hash = 0; // start as blank
			for (int j = 0; j < C[i].length(); j++) {
				hash = hext(hash, C[i].charAt(j)-'a');
			}
//			System.out.println(C[i] + " " + hash);
			
			// add to treemap
			if (m.get(C[i].length()) != null) {
				if (m.get(C[i].length()).get(hash) != null) {
					// if the hash map is already fully set up
					m.get(C[i].length()).get(hash).add(C[i]); // add C[i] to String list
				} else {
					ArrayList<String> a = new ArrayList<String>();
					a.add(C[i]);
					m.get(C[i].length()).put(hash, a);
				}
			} else {
				TreeMap<Integer, ArrayList<String>> t = new TreeMap<Integer, ArrayList<String>>();
				ArrayList<String> a = new ArrayList<String>();
				a.add(C[i]);
				t.put(hash, a);
				m.put(C[i].length(), t);
			}
			
		}
		br.close();
		
//		System.out.println(m);
		
		List<Character> ret = new ArrayList<Character>();
		List<Integer> H = new ArrayList<Integer>(); // H.get(i) is hash of S[0:i]
		H.add(0); 
		List<Integer> HAPW = new ArrayList<Integer>(); // HAPW.get(i) is HA ^ i
		HAPW.add(1);
		
		
		for (int i = 0; i < S.length(); i++) { // loop through string
			int ch = S.charAt(i)-'a'; // current char
			
//			System.out.println("i: " + i + " ch: " + ch + " H: " + H);
			
			// update hashes and ret
			ret.add( S.charAt(i) );
			H.add(hext(H.get(H.size()-1), ch));
			HAPW.add((int) (((long) HAPW.get(HAPW.size()-1) * (long) HA) % HM));
			
//			System.out.println(ret.length());
//			System.out.println(H.size());
//			System.out.println();
			// loop through hashes
			for (int hashLen : m.keySet()) { // string lengths
				if (hashLen > ret.size()) continue; // catch oob
				
				boolean found = false;
				
				// calculate the hash of suffix of ret with length == hashLen
				int preHash = (int) (( (long) H.get(ret.size()-hashLen) * (long) HAPW.get(hashLen) ) % HM);
				int realHash = (HM + H.get(H.size()-1) - preHash) % HM;
				
				if (m.get(hashLen).get(realHash) != null) { // get hash
					ArrayList<String> a = m.get(hashLen).get(realHash);
					for (String x : a) {
						
						boolean eq = true;
						
						
						for (int k = 0; k < x.length(); k++) {
							if (x.charAt(k) == ret.subList(ret.size()-hashLen, ret.size()).get(k)) {
								
							} else {
								eq = false;
								break;
							}
						}
						
						if (eq) {
							// if they are the same string
							ret = ret.subList(0, ret.size()-hashLen);
							H = (List<Integer>) H.subList(0, H.size()-hashLen);
							HAPW = (List<Integer>) HAPW.subList(0, HAPW.size()-hashLen);

				            found = true;
				            break;
						}
					}
				}
				
				if (found) break;
			}
			
		}
		
		
//		System.out.println(ret);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

		for (int i = 0; i < ret.size(); i++) {
			pw.print(ret.get(i));
//			System.out.print(ret.get(i));
		}
		pw.close();
	}
	// Given the hash 'h' of string S, computes the hash of S + 'ch'.
	static int hext(int h, int ch) {
		return (int) ( ( (long) h * (long)HA + (long)ch + (long)HB ) % HM );
	}
}
