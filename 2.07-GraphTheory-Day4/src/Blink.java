import java.io.*;
import java.util.*;
/*
 * 10/10
 * bash
 */
public class Blink {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of bulbs
		long B = Long.parseLong(st.nextToken()); // time
		//long T = System.currentTimeMillis();
		// stores bulb states at time t
		boolean[][] lights = new boolean[95474][N];
		int lastBulbs = 0;
		String ogState = "";
		
		for (int i = 0; i < N; i++) {
			int s = Integer.parseInt(br.readLine());
			lights[0][i] = (s==1);
			ogState += s;
		}
		int ogNum = Integer.parseInt(ogState, 2);

		br.close();

		/*
		for (int j = 0; j < N; j++) {
			if (lights[lastBulbs][j]) System.out.print(1);
			else System.out.print(0);
			System.out.print(" ");

		}
		System.out.println();
		 */

		boolean az = false;
		boolean reachOg = false;
		
		int prevNum = 0;
		
		while (lastBulbs < B && !az && !reachOg) {
			//System.out.println("lastBulbs: " + lastBulbs);
			lastBulbs++;

			String curS = "";
			
			for (int j = 0; j < N; j++) { // loop through lights
				if (lights[lastBulbs-1][(j-1+N)%N]) { // if the bulb to the left was turned on at time T-1
					lights[lastBulbs][j] = !lights[lastBulbs-1][j]; // toggle bulb j
				} else {
					lights[lastBulbs][j] = lights[lastBulbs-1][j];
				}
				
				if (lights[lastBulbs][j]) curS += "1";
				else curS += "0";
			}
			int curNum = Integer.parseInt(curS, 2);
			/*
			for (int j = 0; j < N; j++) {
				if (lights[lastBulbs][j]) System.out.print(1);
				else System.out.print(0);
				System.out.print(" ");
			}
			System.out.println();
			 */
			//if (eq(lights[0], lights[lastBulbs])) { // if you reach the original state
			if (curNum == ogNum) { // if you reach the original state
				//System.out.println("b");
				reachOg = true;
				break; // end looping
			}
			if (0 == curNum || curNum == prevNum) { 
				// if you reach all zeros or the state's no longer changing
				//System.out.println("b");
				az = true;
				break; // end looping
			}
			prevNum = curNum;
		}
		//System.out.println(lastBulbs);
		//System.out.println(System.currentTimeMillis()-T);

		//System.out.println();
		//System.out.println(Arrays.toString(bulbs2));
		if (az) {
			for (int i = 0; i < N; i++) {
				if (lights[lastBulbs][i]) System.out.println(1);
				else System.out.println(0);
			}
			
		}
		else if (!reachOg) {
			// if you didn't reach the original
			for (int i = 0; i < N; i++) {
				if (lights[lastBulbs][i]) System.out.println(1);
				else System.out.println(0);
			}
		} else {
			// last case: you reached the original
			B %= lastBulbs; // put B < lastBulbs
			for (int i = 0; i < N; i++) {
				if (lights[(int)B][i]) System.out.println(1);
				else System.out.println(0);
			}
		}
		//System.out.print("TIME ! : ");
		//System.out.println(System.currentTimeMillis()-T);
	}

}
