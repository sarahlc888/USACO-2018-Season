import java.io.*;
import java.util.*;
/*
 * USACO 2015 December Contest, Gold
 * Problem 2. Fruit Feast
 * 
 * loop
 * DP boolean[]
 * 12/12 test cases
 * 
 * 20 min time
 */
public class FF2 {
	public static boolean[][] DP;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("feast.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken()); // max fullness
		int A = Integer.parseInt(st.nextToken()); // fullness 1
		int B = Integer.parseInt(st.nextToken()); // fullness 2
		
		br.close();
		
		DP = new boolean[T+1][2]; // fullness, [][1] means drank already
		
		boolean[][] DPdone = new boolean[T+1][2]; // has been visited

		// basecase: start with eating nothing
		DP[0][0] = true;
		
		for (int i = 0; i <= 2*T; i++) { // loop through
			// before water
			
			if (i <= T) {
				if (DP[i][0] && !DPdone[i][0]) {
					// if reachable and not visited, process
					
					// mark eating as true, if valid
					if (i+A <= T) {
						DP[i+A][0] = true; 
					}
					if (i+B <= T) {
						DP[i+B][0] = true; 
					}
					// mark drinking water (will automatically round down for odd j)
					DP[i/2][1] = true;
					
				}
			}
			
			
			int j = i/2;
			// after water
			if (DP[j][1] && !DPdone[j][1]) {
				// if reachable and not visited, process
				
				// mark eating as true, if valid
				if (j+A <= T) {
					DP[j+A][1] = true; 
				}
				if (j+B <= T) {
					DP[j+B][1] = true; 
				}
				
			}
		}
		// find the maximum true index of DP2
		int maxInd = T;
		while (!DP[maxInd][1] && maxInd >= 0) {
			maxInd--;
		}
		
		//System.out.println(maxInd);
			

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("feast.out")));

		pw.println(maxInd);
		pw.close();
	}

	public static int bSearchMod(int hi, int lo) { // modified bsearch
		// returns highest i that is true for DP[i][1]
		
		// values -- basecases for when hi - lo range reduced already
		
		// catch outer cases
		if (DP[hi][1]) { // if hi already works
			return hi;
		} else if (!DP[lo][1]) { // if even lo doesn't work
			return -1;
		}
		
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		boolean curWorks = DP[mid][1]; // current status
			
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
