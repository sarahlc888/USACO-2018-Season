import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 10/10 test cases
 * 
 * O(N log L)
 * binary search for the lowest number k
 * so that all jump lengths except two have length >= k
 * find the last factor that has answer yes it works
 */
public class RiverHopscotch {
	static int L;
	static int N;
	static int M;

	static int[] rocks;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken()); // distance from start to end rock
		N = Integer.parseInt(st.nextToken()); // num other rocks 
		M = Integer.parseInt(st.nextToken()); // num rocks to remove

		N += 2; // add the start and end into the rocks

		rocks = new int[N];
		rocks[0] = 0; // start 
		rocks[N-1] = L; // end

		for (int i = 1; i < N-1; i++) { // scan in rocks
			rocks[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(rocks);
		//System.out.println(Arrays.toString(rocks));

		System.out.println(greatestBelow());
		
		//System.out.println(works(4));
	}
	public static boolean works(int k) {
		// returns if it is possible for all jumps to have length >= k
		// by removing <= M rocks

		int prevInd = 0;
		int ind = 1; // loop through rocks
		int violations = 0;
		
		
		while (ind < N) {
			//System.out.println(rocks[prevInd]);
			//System.out.println(rocks[ind]);
			
			if (rocks[ind] < rocks[prevInd] + k) {
				//System.out.println("violation");
				violations++;
				//ind++;
			} else { // only reassign prevind if it works
				prevInd = ind;
			}
			ind++;
			//System.out.println();
		}
		/*
		while (ind + 1 < N) {
			if (rocks[ind+1] < rocks[ind] + k) { // if the next rock is too small
				violations++;
				ind++; // move forward
			} 
			// if the next rock works, proceed

			ind++;
		}*/
		//System.out.println("vs: " + violations);
		if (violations > M) {
			// too many violations
			return false;
		} else {
			return true;
		}

	}
	public static int greatestBelow() {
		// returns greatest i where works(i) == true

		int lo = -1;
		int hi = L;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if

			if (works(mid)) { // if mid works, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;

	}
}
