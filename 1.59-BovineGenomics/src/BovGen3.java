import java.io.*;
import java.util.*;
/*
 * USACO 2017 US Open Contest, Gold
 * Problem 1. Bovine Genomics
 * 
 * 9/10 test cases
 */
public class BovGen3 {
	public static int M;
	public static int N;
	public static String[] c1;
	public static String[] c2;
	
	public static void main(String args[]) throws IOException {
		// INPUT

		BufferedReader br = new BufferedReader(new FileReader("cownomics.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/10.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // number of cows in each
		M = Integer.parseInt(st.nextToken()); // length of genome

		// spotty cows
		c1 = new String[N];
		for (int i = 0; i < N; i++) {
			c1[i] = br.readLine();
		}
		// plain cows
		c2 = new String[N];
		for (int i = 0; i < N; i++) {
			c2[i] = br.readLine();
		}

		br.close();

		int minLen = bSearchMod(M-1, 0);
		
		//System.out.println(works(3));
		// length of substring bsearch
			

		System.out.println(minLen);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));

		pw.println(minLen);
		pw.close();
	}
	
	public static int bSearchMod(int hi, int lo) { // modified bsearch
		// returns lowest len that works
		
		// values -- basecases for when hi - lo range reduced already
		
		// catch outer cases
		if (works(lo)) { // if lo already works
			return lo;
		} /*else if (!works(hi)) { // if even hi doesn't work
			return -1;
		}
		*/
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		boolean curWorks = works(mid); // current status
			
		// narrow the range
		if (!curWorks) { // go bigger
			lo = mid + 1;
			return bSearchMod(hi, lo);
		} else if (curWorks) { // go smaller
			hi = mid; 
			return bSearchMod(hi, lo);
		} else {
			hi = mid;
			//hi = mid+1;
			
			return bSearchMod(hi, lo);
			/*
			while (curval == reach(mid-1)) 
				mid--;
			*/
			// equals --> desired result
		}
		
	}
	public static boolean works(int len) {
		boolean w = false; // if it works; default: no
		
		for (int i = 0; i <= M-len; i++) { // starting index of substring
			//System.out.println("i: " + i);
			int j = i+len-1; // end index
			//System.out.println("i: " + i + " j: " + j);
			// hashsets of the substrings from i to j inclusive for EVERY COW
			HashSet<String> s1 = new HashSet<String>(); 
			HashSet<String> s2 = new HashSet<String>();

			boolean noRepeats = true; // default: yes, it works
			
			for (int k = 0; k < N; k++) {
				//System.out.println(k);
				s1.add(c1[k].substring(i, j+1));
				s2.add(c2[k].substring(i, j+1));
			}
			boolean useS1 = s1.size() < s2.size(); // iterate through the smaller set
			
			if (useS1) { 
				Iterator<String> s1it = s1.iterator(); // iterate through and check overlaps
				while (s1it.hasNext()) {
					if (s2.contains(s1it.next())) {
						noRepeats = false;
						break;
					}
				}
			} else {
				Iterator<String> s2it = s2.iterator();
				while (s2it.hasNext()) {
					if (s1.contains(s2it.next())) {
						noRepeats = false;
						break;
					}
				}
			}
			if (noRepeats) w = true;
		}
		return w;
	}

}
