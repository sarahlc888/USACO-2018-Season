import java.io.*;
import java.util.*;

public class MilkMeasurement {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
		int N = Integer.parseInt(br.readLine()); // number of measurements made

		int[][] measures = new int[N][3];
		// measures[0] = accesses the proper day
		// measures[i][0] = day
		// measures[i][1] = cow
		// measures[i][2] = diff

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int day = Integer.parseInt(st.nextToken());
			
			String cow = (st.nextToken());
			// bessie -> 0, elsie -> 1, mildred -> 2
			int cownum = -1;
			if (cow.equalsIgnoreCase("Bessie")) {
				cownum = 0;
			} else if (cow.equalsIgnoreCase("Elsie")) {
				cownum = 1;
			} else {
				cownum = 2;
			}
			
			int diff = Integer.parseInt(st.nextToken());
			
			measures[i][0] = day;
			measures[i][1] = cownum;
			measures[i][2] = diff;
		}
		
		Arrays.sort(measures, (a, b) -> Integer.compare(a[0], b[0])); // sort by day
		
		// number of changes
		int changes = 0;
		
		// amounts produced by each cow
		int b = 7;
		int e = 7;
		int m = 7;
		
		// boolean of the top cow(s)
		boolean[] topCow = new boolean[3];
		
		for (int i = 0; i < N; i++) {
			// go through all measurements
			
			// identify cow and subsequent production change
			if (measures[i][1] == 0) {
				b += measures[i][2];
			}
			if (measures[i][1] == 1) {
				e += measures[i][2];
			}
			if (measures[i][1] == 2) {
				m += measures[i][2];
			}
			
			
			if (b == e && b == m) { // all the cows are equal and top
				
				if (!(topCow[0] && topCow[1] && topCow[2])) {
					topCow[0] = true;
					topCow[1] = true;
					topCow[2] = true;
					changes++;
				}
			}
			else if (b == e && b > m) { // b == e and top
				
				if (!(topCow[0] && topCow[1])) {
					topCow[0] = true;
					topCow[1] = true;
					topCow[2] = false;
					changes++;
				}
			} 
			else if (e == m && e > b) { // e == m and top
				
				if (!(topCow[1] && topCow[2])) {
					topCow[0] = false;
					topCow[1] = true;
					topCow[2] = true;
					changes++;
				}
				
			} else if (b == m && b > e) {
				
				if (!(topCow[0] && topCow[2])) {
					topCow[0] = true;
					topCow[1] = false;
					topCow[2] = true;
					changes++;
				}
			}
			
			else if (b > e && b > m) { // b is the NEW top cow
				
				if (!topCow[0]) {
					topCow[0] = true;
					topCow[1] = false;
					topCow[2] = false;
					changes++;
				}
			} else if (e > b && e > m) { // e is the NEW top cow
				
				if (!topCow[1]) {
					topCow[0] = false;
					topCow[1] = true;
					topCow[2] = false;
					changes++;
				}
			} else if (m > b && m > e) { // m is the NEW top cow
				
				if (!topCow[2]) {
					topCow[0] = false;
					topCow[1] = false;
					topCow[2] = true;
					changes++;
				}
			}
			
			
			
		}
		System.out.println(changes);
		
		br.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));

		pw.println(changes);
		pw.close();
	}


}
