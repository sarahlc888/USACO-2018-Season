import java.io.*;
import java.math.*;
import java.util.*;

// 10 test cases correct!

public class citystates {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("citystate.in"));
		int N = scan.nextInt(); // number of cities (automatically paired with states)
		String[][] pairs = new String[N][2];
		for (int i = 0; i < N; i++) {
			pairs[i][0] = scan.next();
			pairs[i][1] = scan.next();
		}
		scan.close();
		
		int[][] grid = new int[676][676]; // grid of all possible 2 letter combos for state and city (first 2 letters)
		// put the number of occurrences of the city and state combos 
		
		for (int i = 0; i < N; i++) {
			// grid indexes from base 26 --> base 10 - increment with each appearance of the pair in input
			grid[LetterIndexConvert(pairs[i][0])][LetterIndexConvert(pairs[i][1])]++;	
		}
		
		int count = 0;
		
		for (int i = 0; i < 676; i++) {
			for (int j = i+1; j < 676; j++) {
				// goes through above the diag (diag doesn't count because the pair will be from the same state)
				if (grid[i][j] > 0) {
					count += grid[i][j] * grid[j][i]; // multiply the first part of the pair with the second part of the pair
					// the opposite indexing should fulfill the requirements for a pair
				}
			}
		}
		
		
		System.out.println(count);
		
		PrintWriter pw = new PrintWriter(new File("citystate.out"));
		pw.println(count);
		pw.close();
		
		// System.out.println(count);
		
	}
	public static int fact(int n) {
		int ret = 1;
		for (int i = 1; i <= n; i++) {
			ret *= i;
		}
		return ret;
	}
	public static int LetterIndexConvert(String name) {
		int ind = 26 * (name.charAt(0) - 'A') + (name.charAt(1) - 'A'); // use base 26 to index the array
		return ind;
	}
}
