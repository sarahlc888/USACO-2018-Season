import java.io.*;
import java.util.*;

public class eatpuz2 {
	static int C;
	static int[] feed;
	static boolean[] possible;
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		C = Integer.parseInt(st.nextToken()); // max number of calories
		int B = Integer.parseInt(st.nextToken()); // number of buckets
		
		feed = new int[B]; // calories in the buckets
		
		st = new StringTokenizer(scan.nextLine());
		
		for (int i = 0; i < B; i++) { // scan in calories in the buckets
			feed[i] = Integer.parseInt(st.nextToken());
		}
		scan.close();
		////System.out.println(C + " " + B);
		////System.out.println(Arrays.toString(feed));
		
		
		int max = 0;
		
		// DYNAMIC PROGRAMMING with boolean
		possible = new boolean[C+1];
		possible[0] = true; // starting point
		
		for (int i = 0; i < B; i++) { // loop through the buckets
			//System.out.println("i: " + i);
			
			boolean[] possible2 = new boolean[C+1];
			
			
			for (int cur = 0; cur <= C; cur++) { // loop through the possibles
				if (!possible[cur]) continue; // if false, continue
				
				possible2[cur] = true;
				
				//System.out.println("  cur: " + cur);
				//System.out.println("    next: " + (cur + feed[i]));
				
				if (cur + feed[i] <= C) {
					// if you can eat the current bucket and stay under
					possible2[cur + feed[i]] = true;
					//break; // only can eat it once
				}
			}
			
			
			
			for (int j = 0; j <= C; j++) {

				possible[j] = possible2[j];
				
			}
		}
		
		
		
		for (int i = C; i >= 0; i--) {
			if (possible[i]) {
				System.out.println(i);
				break;
			}
		}
		
		
		//System.out.println(Arrays.toString(possible));
		
		
	}
	

}
/*
40 6
7 13 17 19 29 31
*/