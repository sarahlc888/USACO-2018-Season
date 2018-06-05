import java.io.*;
import java.util.*;
/*
 * USACO 2016 January Contest, Gold
 * Problem 2. Radio Contact
 * 
 * DP
 * 
 * untested, 1st test case is 32 instead of 28
 * 
 * 10:45-11:30
 */
public class Radio {

	public static int[] fposx;
	public static int[] fposy;
	public static int[] bposx;
	public static int[] bposy;

	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("radio.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of steps for FJ
		int M = Integer.parseInt(st.nextToken()); // number of steps for Bessie

		// FJ's starting point
		st = new StringTokenizer(br.readLine());
		int fx = Integer.parseInt(st.nextToken()); 
		int fy = Integer.parseInt(st.nextToken());

		// Bessie's starting point
		st = new StringTokenizer(br.readLine());
		int bx = Integer.parseInt(st.nextToken()); 
		int by = Integer.parseInt(st.nextToken());

		// respective paths
		String fpathStr = br.readLine();
		String bpathStr = br.readLine();
		br.close();

		int[] fpath = new int[fpathStr.length()+1];
		int[] bpath = new int[bpathStr.length()+1];
		fpath[0] = bpath[0] = -1; // empty move at the start

		for (int i = 1; i <= fpathStr.length(); i++) {
			fpath[i] = processDir(fpathStr.charAt(i-1));
		}
		for (int i = 1; i <= bpathStr.length(); i++) {
			bpath[i] = processDir(bpathStr.charAt(i-1));
		}

		// returns x or y position of f or b after move i
		fposx = new int[fpath.length];
		fposy = new int[fpath.length];
		bposx = new int[bpath.length];
		bposy = new int[bpath.length];	

		fposx[0] = fx;
		fposy[0] = fy;
		bposx[0] = bx;
		bposy[0] = by;

		for (int i = 1; i < fpath.length; i++) {
			fposx[i] = fposx[i-1] + processNumDirX(fpath[i]);
			fposy[i] = fposy[i-1] + processNumDirY(fpath[i]);
		}
		for (int i = 1; i < bpath.length; i++) {
			bposx[i] = bposx[i-1] + processNumDirX(bpath[i]);
			bposy[i] = bposy[i-1] + processNumDirY(bpath[i]);
		}
		/*
		System.out.println(Arrays.toString(fposx));
		System.out.println(Arrays.toString(fposy));
		System.out.println(Arrays.toString(bposx));
		System.out.println(Arrays.toString(bposy));
		 */

		// DP array DP[i][j]
		// i = FJ's progress; j = Bessie's progress; once moves i and j are complete
		// stores cumulative energy cost

		int[][] DP = new int[fpath.length][bpath.length]; 
		for (int i = 0; i < fpath.length; i++) {
			for (int j = 0; j < bpath.length; j++) {
				DP[i][j] = Integer.MAX_VALUE;
			}
		}
		DP[0][0] = energy(0, 0);
		System.out.println(DP[0][0]);

		for (int i = 0; i < fpath.length; i++) {
			for (int j = 0; j < bpath.length; j++) {
				System.out.println("i: " + i + " j: " + j + " val: " + DP[i][j]);
				// move F and B
				if (i+1 < fpath.length && j+1 < bpath.length) {
					System.out.println("  here1");
					DP[i+1][j+1] = Math.min(DP[i+1][j+1], DP[i][j] + energy(i+1, j+1));
					System.out.println("  " + DP[i+1][j+1]);
				}
			
				// move just F
				if (i+1 < fpath.length) {
					System.out.println("  here2");
					DP[i+1][j] = Math.min(DP[i+1][j], DP[i][j] + energy(i+1, j));
					System.out.println("  " + DP[i+1][j]);
				}
				
				// move just B
				if (j+1 < bpath.length) {
					System.out.println("  here3");
					DP[i][j+1] = Math.min(DP[i][j+1], DP[i][j] + energy(i, j+1));
					System.out.println("  " + DP[i][j+1]);
				}
				
			}
		}
		System.out.println();
		for (int i = 0; i < DP.length; i++) {
			System.out.println(Arrays.toString(DP[i]));
		}
		System.out.println();
		
		System.out.println(DP[fpath.length-1][bpath.length-1]);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("radio.out")));

		pw.println(DP[fpath.length-1][bpath.length-1]);
		pw.close();
	}
	public static int energy(int f, int b) {
		// return the energy consumed when f is at (fposx[f], fposy[f])
		// and b is at (bposx[b], bposy[b])
		return ((int)(fposx[f]-bposx[b]))*(fposx[f]-bposx[b]) + ((int)(fposy[f]-bposy[b]))*(fposy[f]-bposy[b]);
	}
	public static int processDir(char dir) {
		// return a number for each letter direction
		if (dir == 'N') return 0;
		else if (dir == 'E') return 1;
		else if (dir == 'S') return 2;
		else return 3;
	}
	public static int processNumDirY(int dir) {
		// return the change in Y for each direction
		if (dir == 0) return 1;
		else if (dir == 2) return -1;
		else return 0;
	}
	public static int processNumDirX(int dir) {
		// return the change in X for each direction
		if (dir == 1) return 1;
		else if (dir == 3) return -1;
		else return 0;
	}

}
