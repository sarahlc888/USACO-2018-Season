import java.io.*;
import java.util.*;
/*
 * USACO 2015 February Contest, Gold
 * Problem 2. Censoring (Gold)
 * 8/23/18
 * 
 * From solution
 * 9/15 cases on a good run
 */
public class CHashFinal {
	static int HM = 1000000007;
	static int HA = 100000007;
	static int HB = 101;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("censor.in"));
		String S = br.readLine(); // string
		int N = Integer.parseInt(br.readLine()); // num censored words
		
		// stored by length and then by hash value
		TreeMap<Integer, TreeMap<Integer, ArrayList<String>>> m = new TreeMap<Integer, TreeMap<Integer, ArrayList<String>>>();
		
		for (int i = 0; i < N; i++) {
			String cur = br.readLine();
			int hash = 0; // start as blank

			for (int j = 0; j < cur.length(); j++) {
				hash = hext(hash, cur.charAt(j)-'a');
			}

			// add to treemap
			if (m.get(cur.length()) != null && m.get(cur.length()).get(hash) != null) { // all defined
				m.get(cur.length()).get(hash).add(cur);
			} else {
				ArrayList<String> a = new ArrayList<String>();
				if (m.get(cur.length()) != null) { // first defined
					a.add(cur);
					m.get(cur.length()).put(hash, a);
				} else { // none defined
					TreeMap<Integer, ArrayList<String>> t = new TreeMap<Integer, ArrayList<String>>();
					a.add(cur);
					t.put(hash, a);
					m.put(cur.length(), t);
				}
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
			HAPW.add((int) ((long) (HAPW.get(HAPW.size()-1) * (long) HA) % HM));
			
//			System.out.println(ret.length());
//			System.out.println(H.size());
//			System.out.println();
			// loop through hashes
			for (int hashLen : m.keySet()) { // string lengths
				if (hashLen > ret.size()) continue; // catch oob
				
				boolean found = false;
				
				// calculate the hash of suffix of ret with length == hashLen
				int preHash = (int) (( (long) H.get(ret.size()-hashLen) * (long) HAPW.get(hashLen) ) % HM);
				int realHash = (HM + ( H.get(H.size()-1) - preHash)) % HM;
				
				ArrayList<String> a = m.get(hashLen).get(realHash);
				if (a != null) { // get hash
					
//					List<Character> b = ret.subList(ret.size()-hashLen, ret.size());
					
					for (String x : a) {
						
						boolean eq = true;
						
						for (int k = 0; k < x.length(); k++) {
//							if (x.charAt(k) != b.get(k)) {
							if (x.charAt(k) != ret.get(ret.size()-hashLen+k)) {
//								ret.get(ret.size()-hashLen+k
								eq = false;
								break;
							}
						}
						
						if (eq) {
							// if they are the same string
							ret = ret.subList(0, ret.size()-hashLen);
							H = H.subList(0, H.size()-hashLen);
							HAPW = HAPW.subList(0, HAPW.size()-hashLen);

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
			System.out.print(ret.get(i));
		}
		pw.close();
	}
	// Given the hash 'h' of string S, computes the hash of S + 'ch'.
	static int hext(int h, int ch) {
		return (int) ( ( (long) h * (long)HA + (long)ch + (long)HB ) % HM );
	}
}
