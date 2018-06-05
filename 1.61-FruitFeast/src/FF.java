import java.io.*;
import java.util.*;
/*
 * USACO 2015 December Contest, Gold
 * Problem 2. Fruit Feast
 * 
 * Loop
 * DP boolean[]
 * 8/12 test cases
 * 
 * doesn't work
 */
public class FF {
	public static boolean[] DP2;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("feast.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken()); // max fullness
		int A = Integer.parseInt(st.nextToken()); // fullness 1
		int B = Integer.parseInt(st.nextToken()); // fullness 2
		
		br.close();
		
		boolean[] DP1 = new boolean[T+1]; // fullness before drinking water
		DP2 = new boolean[T+1]; // fullness after drinking water
		
		boolean[] DP1done = new boolean[T+1]; // whether or not a state has already been processed
		boolean[] DP2done = new boolean[T+1]; // whether or not a state has already been processed
		
		// basecases: eat nothing
		DP1[0] = true; 
		
		for (int i = 0; i <= T; i++) { // for track 2
			for (int j = 0; j <= T; j++) { // for track 1
				// process all track 1 before track 2 so as not to miss anything
				
				if (DP1[j] && !DP1done[j]) { // if it's reachable, process
					DP1done[j] = true;
					
					// mark eating as true, if valid
					if (j+A <= T) {
						DP1[j+A] = true; 
					}
					if (j+B <= T) {
						DP1[j+B] = true; 
					}
					// mark drinking water (will automatically round down for odd j)
					DP2[j/2] = true;
				}
				if (DP2[i] && !DP2done[i]) { // if it's reachable, process
					DP2done[i] = true;
					if (i+A <= T) {
						DP2[i+A] = true; 
					}
					if (i+B <= T) {
						DP2[i+B] = true; 
					}
				}
			}
		}
		// find the maximum true index of DP2
		int maxInd = bSearchMod(T, 0);
		
		System.out.println(maxInd);
			

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("feast.out")));

		pw.println(maxInd);
		pw.close();
	}
	public static int bSearchMod(int hi, int lo) { // modified bsearch
		// returns highest i that is true DP2[i]
		
		// values -- basecases for when hi - lo range reduced already
		
		// catch outer cases
		if (DP2[hi]) { // if hi already works
			return hi;
		} else if (!DP2[lo]) { // if even lo doesn't work
			return -1;
		}
		
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		boolean curWorks = DP2[mid]; // current status
			
		// narrow the range
		if (curWorks) { // go bigger
			lo = mid + 1;
			return bSearchMod(hi, lo);
		} else if (!curWorks) { // go smaller
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

}
