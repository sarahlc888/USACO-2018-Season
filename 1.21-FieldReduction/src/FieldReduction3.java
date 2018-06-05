import java.io.*;
import java.util.*;

// 9/10 test cases
// combine cows3 and cows4

public class FieldReduction3 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("reduce.in"));
		int N = Integer.parseInt(br.readLine()); // number of cows
		
		int[][] cows = new int[N][2];
		for (int i = 0; i < N; i++) { // scan everything in
			StringTokenizer st = new StringTokenizer(br.readLine());
			cows[i][0] = Integer.parseInt(st.nextToken());
			cows[i][1] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		int minArea = Integer.MAX_VALUE;
		
		int[][] cows1;
		int[][] cows2;
		int[][] cows3;
		
		// loop through all four sides and remove from 0 to 3 cows from each
		for (int i = 0; i <= 3; i++) {
			// kick from 0 to 3 cows from the left side
			
			// copy of cows that can be edited
			cows1 = new int[cows.length][2];
			for (int n = 0; n < cows.length; n++) {
				cows1[n][0] = cows[n][0];
				cows1[n][1] = cows[n][1];
			}
			
			for (int n = 0; n < i; n++) { // kick off n cows
				cows1 = deleteCow(0, cows1, n);
			}
			
			for (int j = 0; j <= 3-i; j++) {
				// kick from 0 to 3 cows from the bottom side
				cows2 = new int[cows1.length][2];
				for (int n = 0; n < cows1.length; n++) {
					cows2[n][0] = cows1[n][0];
					cows2[n][1] = cows1[n][1];
				}
				
				for (int n = 0; n < j; n++) { // kick off n cows
					cows2 = deleteCow(1, cows2, i+n);
				}
				
				for (int k = 0; k <= 3-i-j; k++) {
					// right
					cows3 = new int[cows2.length][2];
					for (int n = 0; n < cows2.length; n++) {
						cows3[n][0] = cows2[n][0];
						cows3[n][1] = cows2[n][1];
					}
					
					for (int n = 0; n < k; n++) { // kick off n cows
						cows3 = deleteCow(2, cows3, i+j+n);
					}
					
					// set m to the highest possible value that will yield the min size
					int m = 3-i-j-k;

					// remove from top
					
					for (int n = 0; n < m; n++) { // kick off n cows
						cows3 = deleteCow(3, cows3, i+j+k+n);
					}

					Arrays.sort(cows3, (a, b) -> Integer.compare(a[0], b[0]));

					
					/*
					while (cows3[p][0] < 0) {
						p++;
					}*/
					
					int minx = cows3[3][0]; // 3 deleted -1s
					int maxx = cows3[cows3.length-1][0];

					Arrays.sort(cows3, (a, b) -> Integer.compare(a[1], b[1]));
					/*
					int miny = cows3[p][1];
					int maxy = cows3[cows3.length-1][1];

					int area = (maxx - minx)*(maxy-miny);
					 */
					int area = (maxx - minx)*(cows3[cows3.length-1][1]-cows3[3][1]);
					if (area < minArea) {
						minArea = area;
					}
				}
			}
		}
		//System.out.println(minArea);
		
		PrintWriter pw = new PrintWriter(new File("reduce.out"));
		pw.println(minArea);
		pw.close();
	}
	public static int[][] deleteCow(int dir, int[][] curcows, int numPriorDeletions) {
		int n = 0;
		if (dir == 0) {
			// delete one from the left side by making negative
			Arrays.sort(curcows, (a, b) -> Integer.compare(a[0], b[0]));
			
			/*n = 0;
			while (curcows[n][0] < 0) {
				n++;
			}
			curcows[n][0] = -1;
			curcows[n][1] = -1;*/
			curcows[numPriorDeletions][0] = -1;
			curcows[numPriorDeletions][1] = -1;
		} else if (dir == 1) {
			// delete one from the bottom
			Arrays.sort(curcows, (a, b) -> Integer.compare(a[1], b[1]));
			/*n = 0;
			while (curcows[n][0] < 0) {
				n++;
			}
			curcows[n][0] = -1;
			curcows[n][1] = -1;
			*/
			curcows[numPriorDeletions][0] = -1;
			curcows[numPriorDeletions][1] = -1;
			
		} else if (dir == 2) {
			// delete one from the right side
			Arrays.sort(curcows, (a, b) -> Integer.compare(a[0], b[0]));
			
			curcows[curcows.length - 1][0] = -1;
			curcows[curcows.length - 1][1] = -1;
		} else if (dir == 3) {
			// delete one from the top
			Arrays.sort(curcows, (a, b) -> Integer.compare(a[1], b[1]));
			curcows[curcows.length - 1][0] = -1;
			curcows[curcows.length - 1][1] = -1;
		}
		
		return curcows;
	}
}
