import java.io.*;
import java.util.*;

// ten out of ten 

public class mm2 {
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
		boolean[] topCowPrev = new boolean[3];
		
		for (int i = 0; i < N; i++) {
			// go through all measurements
			
			// boolean of the new cow(s)
			boolean[] topCowNew = new boolean[3];
			
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
			
			int maxVal = Math.max(Math.max(e, b), m);

			if (b == maxVal) {
				topCowNew[0] = true;
			}
			if (e == maxVal) {
				topCowNew[1] = true;
			}
			if (m == maxVal) {
				topCowNew[2] = true;
			}
			
			if (topCowPrev[0] != topCowNew[0] || 
					topCowPrev[1] != topCowNew[1] || 
							topCowPrev[2] != topCowNew[2]) {
				changes++;
			}
			
			topCowPrev = topCowNew;
			
			/*
			if (b == maxVal && e == maxVal && m == maxVal
					&& (!topCow[0] || !topCow[1] || !topCow[2])) {
				changes++;
				topCow[0] = true;
				topCow[1] = true;
				topCow[2] = true;
			}
			else if (b==maxVal && e==maxVal && (!topCow[0] || !topCow[1])) {
				changes++;
				topCow[0] = true;
				topCow[1] = true;
				topCow[2] = false;
			}
			else if (b==maxVal && m==maxVal && (!topCow[0] || !topCow[2])) {
				changes++;
				topCow[0] = true;
				topCow[1] = false;
				topCow[2] = true;
			}
			else if (e==maxVal && m==maxVal && (!topCow[1] || !topCow[2])) {
				changes++;
				topCow[0] = false;
				topCow[1] = true;
				topCow[2] = true;
			} else if (b == maxVal && !topCow[0]) {
				changes++;
				topCow[0] = true;
				topCow[1] = false;
				topCow[2] = false;
			} else if (e == maxVal && !topCow[1]) {
				changes++;
				topCow[0] = false;
				topCow[1] = true;
				topCow[2] = false;
			}
			
			if (b > e && b > m && !topCow[0]) { // b is the NEW top cow

				topCow[0] = true;
				topCow[1] = false;
				topCow[2] = false;
				changes++;

			} else if (e > b && e > m && !topCow[1]) { // e is the NEW top cow
				
				topCow[0] = false;
				topCow[1] = true;
				topCow[2] = false;
				changes++;

			} else if (m > b && m > e && !topCow[2]) { // m is the NEW top cow

				topCow[0] = false;
				topCow[1] = false;
				topCow[2] = true;
				changes++;

			} else if (b == e && b == m && !(topCow[0] && topCow[1] && topCow[2])) { // all the cows are equal and top

				topCow[0] = true;
				topCow[1] = true;
				topCow[2] = true;
				changes++;

			}
			else if (b == e && b > m && !(topCow[0] && topCow[1])) { // b == e and top

				topCow[0] = true;
				topCow[1] = true;
				topCow[2] = false;
				changes++;

			} 
			else if (e == m && e > b && !(topCow[1] && topCow[2])) { // e == m and top

				topCow[0] = false;
				topCow[1] = true;
				topCow[2] = true;
				changes++;

				
			} else if (b == m && b > e && !(topCow[0] && topCow[2])) {

				topCow[0] = true;
				topCow[1] = false;
				topCow[2] = true;
				changes++;
			}*/
			
		}
		System.out.println(changes);
		
		br.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));

		pw.println(changes);
		pw.close();
	}


}
