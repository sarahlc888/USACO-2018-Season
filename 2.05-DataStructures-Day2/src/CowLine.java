import java.io.*;
import java.util.*;

/*
 * basically make a makeshift deque
 * 
 * 10/10 test cases
 */

public class CowLine {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int S = Integer.parseInt(scan.nextLine()); // number of queries
		
		int[] line = new int[250000]; // make it much larger
		
		// next open index on the left or right
		int left = 250000/2;
		int right = 250000/2+1;
		
		int cow = 1;
		
		
		// process the queries
		for (int i = 0; i < S; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			String move = st.nextToken();
			String dir = st.nextToken();
			int num = 1;
			if (st.hasMoreTokens()) { // if there's more
				// scan in the number
				num = Integer.parseInt(st.nextToken());
			}
			
			//System.out.println(move + " " + dir);
			
			// process it num times
			while (num > 0) {
				if (move.equals("A")) { 
					if (dir.equals("L")) { 
						// add an element on the left
						line[left] = cow;
						left--;
						
					} else {
						// add an element on the right
						line[right] = cow;
						right++;
					}
					cow++; // increment the cow
				} else {
					if (dir.equals("L")) { 
						// remove an element on the left
						left++;
					} else { 
						// remove an element on the right
						right--;
					}
				}
				
				num--;
			}
			/*
			for (int j = left+1; j < right; j++) {
				System.out.print(line[j] + " ");
			}
			System.out.println();*/
		}
		
		scan.close();
		//System.out.println();
		for (int i = left+1; i < right; i++) {
			System.out.println(line[i]);
		}
		
	}
}
