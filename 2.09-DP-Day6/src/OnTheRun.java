import java.io.*;
import java.util.*;

/* 10/10 test cases on IDEA 2
 * add a clump at the starting point
 * 
 * IDEA 1 (not used)
 * all haybales eaten will form a continuous sequence (if you pass one, you eat one)
 * DP[start][end] = minimum staleness of eating everything in that range start to end
 * every range should contain 10 bc you start at 10
 * 
 * in the range, the cow would only stop at start or end
 * break up a left and right DP
 * L[i][j] = min stalesness of eating everything in the range and ending at i
 * R[i][j] is the same thing but ending at j
 * 
 * L[i-1][j] = L[i][j] + 
 * 
 * L[2][4] + 19-9 --> R[2][5]
 * 
 * s is total time, f is total staleness
 * R[i][j].s = p[j]-p[i]+L[i][j-1].s
 * R[i][j].f = 
 * 2 cases to get R[i][j]: L[i][j-1] to R[i][j];	R[i][j-1] to R[i][j];
 * 		L[i][j-1].f + L[i][j-1].s + p[j] - p[i]
 * 		
 * 
 * Same thing on left side
 * 
 * to get the time, check if the staleness update was actually updated or if it was just skipped
 * 
 * 
 * O(N^2) bc 2d array with '
 * 
 * 
 * IDEA 2 (used)
 * increment staleness of all uneaten clumps after you do any move
 * 
 * unused clumps: N-(j-i) bc don't count destination yet
 * 
 * R[i][j] can come from, take min of all 3 choices
 * 		R[i][j-1] + (N-(j-i))*(p[j]-p[j-1])
 * 		L[i][j-1] + (N-(j-i))*(p[j]-p[i])
 * L[i][j] 
 * 		L[i+1][j] + (N-(j-i))*(p[i+1]-p[i])
 * 		R[i+1][j] + (N-(j-i))*(p[j]-p[i])
 * loop through, always include starting point, need the inner stuff
 * 	
 * 
 */
public class OnTheRun {
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int N = Integer.parseInt(st.nextToken()); // number of clumps
		int S  = Integer.parseInt(st.nextToken()); // bessie's starting location
		N++; // add a clump at bessie's starting location
		
		int[] pos = new int[N]; // positions of the clumps
		
		boolean startAdded = false;
		int sind = -1; // index of starting point in the pos[]
		
		for (int i = 0; i < N-1; i++) { // scan in the clumps
			pos[i] = Integer.parseInt(scan.nextLine()); // scan in position
		}
		pos[N-1] = S; // add the starting position
		Arrays.sort(pos); // sort the array
		sind = Arrays.binarySearch(pos, S); // find sind
		// left and right DP arrays
		// L[i][j] = min total staleness of eating interval [i, j] and ending at i
		long[][] L = new long[N][N];  
		// R[i][j] = same but ending at j
		long[][] R = new long[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(L[i], Long.MAX_VALUE);
			Arrays.fill(R[i], Long.MAX_VALUE);
		}
		
		
		// looping stuff, all based on indices
		for (int i = sind; i >= 0; i--) { // left bound, start at starting pt
			for (int j = sind; j < N; j++) { // right bound, also start at starting pt
				// basecase at the starting point
				//System.out.println("i: " + i + " j: " + j);
				if (i == j) { // basecase
					L[i][j] = 0;
					R[i][j] = 0;
					//System.out.println("  " + R[i][j] + " " + L[i][j]);
					continue;
				}
				
				//System.out.println("  " + R[i][j-1] + "  " + L[i][j-1]);
				// updates if valid
				if (R[i][j-1] != Long.MAX_VALUE) {
					R[i][j] = Math.min(R[i][j], R[i][j-1] + (N-(j-i))*(pos[j]-pos[j-1]));
				}
				if (L[i][j-1] != Long.MAX_VALUE) {
					R[i][j] = Math.min(R[i][j], L[i][j-1] + (N-(j-i))*(pos[j]-pos[i]));
				}
				if (L[i+1][j] != Long.MAX_VALUE) {
					L[i][j] = Math.min(L[i][j], L[i+1][j] + (N-(j-i))*(pos[i+1]-pos[i]));
				}
				if (R[i+1][j] != Long.MAX_VALUE) {
					L[i][j] = Math.min(L[i][j], R[i+1][j] + (N-(j-i))*(pos[j]-pos[i]));
				}
				
				//System.out.println("  " + R[i][j] + " " + L[i][j]);
			}
		}
		
		
		//System.out.println(Math.max(L[0][N-1], R[0][N-1]));
		System.out.println(Math.min(L[0][N-1], R[0][N-1]));
		
		
	}

}
