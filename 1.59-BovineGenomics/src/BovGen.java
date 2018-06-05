import java.io.*;
import java.util.*;
/*
 * USACO 2017 US Open Contest, Gold
 * Problem 1. Bovine Genomics
 * 
 * 4/17/18
 */
public class BovGen {
	public static void main(String args[]) throws IOException {
		// INPUT
		
		BufferedReader br = new BufferedReader(new FileReader("cownomics.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of cows in each
		int M = Integer.parseInt(st.nextToken()); // length of genome

		// spotty cows
		String[] c1 = new String[N];
		for (int i = 0; i < N; i++) {
			c1[i] = br.readLine();
		}
		// plain cows
		String[] c2 = new String[N];
		for (int i = 0; i < N; i++) {
			c2[i] = br.readLine();
		}
		
		br.close();
		
		int minLen = Integer.MAX_VALUE;
		
		for (int i = 0; i < M; i++) {
			for (int j = i; j < M; j++) {
				if (j-i+1 > minLen) continue;
				
				//System.out.println("i: " + i + " j: " + j);
				// hashsets of the substrings from i to j inclusive for EVERY COW
				HashSet<String> s1 = new HashSet<String>(); 
				HashSet<String> s2 = new HashSet<String>();
				
				for (int k = 0; k < N; k++) {
					//System.out.println(k);
					s1.add(c1[k].substring(i, j+1));
					s2.add(c2[k].substring(i, j+1));
				}
				boolean useS1 = s1.size() < s2.size(); // iterate through the smaller set
				boolean noRepeats = true;
				if (useS1) { 
					Iterator<String> s1it = s1.iterator(); // iterate through and check overlaps
					while (s1it.hasNext()) {
						if (s2.contains(s1it.next())) {
							noRepeats = false;
						}
					}
				} else {
					Iterator<String> s2it = s2.iterator();
					while (s2it.hasNext()) {
						if (s1.contains(s2it.next())) {
							noRepeats = false;
						}
					}
				}
				
				if (noRepeats && j-i+1 < minLen) {
					// if the substrings are valid and len < minLen, update
					
					minLen = j-i+1;
					//System.out.println("new min: " + minLen);
				}
				
			}
		}
		//System.out.println(minLen);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));

		pw.println(minLen);
		pw.close();
	}


}
