import java.io.*;
import java.util.*;
/*

input:
4 10
1
9
11
19

 */
public class OTR2 {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());

		int N = Integer.parseInt(st.nextToken()); // length of field
		int L = Integer.parseInt(st.nextToken()); // starting location

		int[] clumps = new int[N]; // stores locations of clumps

		for (int i = 0; i < N; i++) {
			clumps[i] = Integer.parseInt(scan.nextLine());
		}
		scan.close();


		// L is the starting location
		int below = greatestBelow(clumps, L);
		int above = leastAbove(clumps, L);

		// going to above first
		int firstDist = clumps[above]-L;
		
		// uses base 2
		// DP[b2 state][last haybale eaten] = staleness of haybales eaten so far
		// find min in DP[Math.pow(2, N)-1]
		int[][] DP = new int[(int)Math.pow(2, N)][N];

		String start = "";
		for (int i = 0; i < above; i++) {
			start += "0";
		}
		start += "1";
		while (start.length() < N) {
			start += "0";
		}
		
		for (int l = 0; l < N; l++) {
			for (int i = 0; i < Math.pow(2, N); i++) { 
				DP[i][l] = -1;
			}
		}
		
		DP[Integer.parseInt(start, 2)][above] = firstDist;
		
		// loop through DP
		for (int l = 0; l < N; l++) {
			for (int i = 0; i < Math.pow(2, N); i++) { 

				if (DP[i][l] == -1 || i == 0 && l != 0) {
					continue; // skip if not yet reached 
				}

				String a = Integer.toString(i, 2);
				while (a.length() < N) // pad the front with 0s
					a = "0"+a;


				//System.out.println("i: " + a + " l: " + l + " val: " + DP[i][l]);


				for (int j = 0; j < N; j++) {
					// which field to visit next
					if (a.charAt(j) == '0') {
						// if the haybale isn't eaten, try eating it

						// copy of a, except replace the desired 0 with a 1
						String b = new String();
						for (int k = 0; k < N; k++) {
							if (k == j) {
								b += "1";
							} else {
								b += a.charAt(k);
							}
						}
						//System.out.println("  b: " + b);
						int ind = Integer.parseInt(b, 2); // convert to base 10
						DP[ind][j] = 2*DP[i][l] + Math.abs(clumps[j] - clumps[l]); // calc the next staleness

					}
				}

			}
		}
		for(int i = 0; i < (int)Math.pow(2, N); i++) {
			System.out.println(Arrays.toString(DP[i]));
		}
		//System.out.println();
		// going to below first
		
		if (below > -1) {
			firstDist = L-clumps[below];
			
			// uses base 2
			// DP[b2 state][last haybale eaten] = staleness of haybales eaten so far
			// find min in DP[Math.pow(2, N)-1]
			DP = new int[(int)Math.pow(2, N)][N];

			for (int l = 0; l < N; l++) {
				for (int i = 0; i < Math.pow(2, N); i++) { 
					DP[i][l] = -1;
				}
			}
			
			start = "";
			for (int i = 0; i < below; i++) {
				start += "0";
			}
			start += "1";
			
			while (start.length() < N) {
				start += "0";
			}
			
			DP[Integer.parseInt(start, 2)][below] = firstDist;
			
			// loop through DP
			for (int l = 0; l < N; l++) {
				for (int i = 0; i < Math.pow(2, N); i++) { 

					if (DP[i][l] == -1 || i == 0 && l != 0) {
						continue; // skip if not yet reached 
					}

					String a = Integer.toString(i, 2);
					while (a.length() < N) // pad the front with 0s
						a = "0"+a;


					//System.out.println("i: " + a + " l: " + l + " val: " + DP[i][l]);


					for (int j = 0; j < N; j++) {
						// which field to visit next
						if (a.charAt(j) == '0') {
							// if the haybale isn't eaten, try eating it

							// copy of a, except replace the desired 0 with a 1
							String b = new String();
							for (int k = 0; k < N; k++) {
								if (k == j) {
									b += "1";
								} else {
									b += a.charAt(k);
								}
							}
							//System.out.println("  b: " + b);
							int ind = Integer.parseInt(b, 2); // convert to base 10
							DP[ind][j] = 2*DP[i][l] + Math.abs(clumps[j] - clumps[l]); // calc the next staleness

						}
					}

				}
			}

		}
		
		
		/*for(int i = 0; i < (int)Math.pow(2, N); i++) {
			System.out.println(Arrays.toString(DP[i]));
		}*/
		//System.out.println();

		
		

		//System.out.println(Arrays.toString(DP[(int)Math.pow(2, N)-1]));
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < DP[(int)Math.pow(2, N)-1].length; i++) {
			if (DP[(int)Math.pow(2, N)-1][i] > -1 &&
					DP[(int)Math.pow(2, N)-1][i] < min) {
				min = DP[(int)Math.pow(2, N)-1][i];
			}
			
		}
		System.out.println(min);

	}
	public static int greatestBelow(int[] arr, long val) {
		// returns greatest i <= val

		int lo = -1;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if

			if (arr[mid] <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;

	}
	public static int leastAbove(int[] arr, long val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if

			if (arr[mid] >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
	}
}
