import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * on paper only, not on LMS
 * THE ROCK GAME
 * 
 * take n-1 and add 0s and 1s in the front
 * write 2 copies side by side and go in a U shape and add 000 at the end
 * 
 * theoretically done
 */
public class RockGame {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		// want 2^N binary strings
		
		int length = 1;
		ArrayList<String> P = new ArrayList<String>(); // all together
		
		P.add("0");
		P.add("1");
		
		while (length < N) { // increase the length
			ArrayList<String> newP = new ArrayList<String>(); // all together
			
			
			// loop through the stuff from the previous length
			for (int i = 0; i < P.size(); i++) {
				// add 0 at front
				String cur = P.get(i);
				newP.add("0" + cur); // add a 0
			}
			for (int i = P.size()-1; i >= 0; i--) {
				// add 1 at front
				String cur = P.get(i);
				newP.add("1" + cur); // add a 1
			}
			P = newP;
			
			length++;
		}
		
		for (int i = 0; i < P.size(); i++) {
			System.out.println(P.get(i));
		}
		for (int i = 0; i < N; i++) {
			System.out.print("0");
		}
	}
}
