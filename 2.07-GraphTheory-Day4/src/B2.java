import java.io.*;
import java.util.*;
/*
 * 10/10
 * bash
 */
public class B2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of bulbs
		long B = Long.parseLong(st.nextToken()); // time
		//long T = System.currentTimeMillis();
		// stores bulb states at time t
		boolean[] lights1 = new boolean[N];
		boolean[] lights2 = new boolean[N];
		int lastBulbs = 0;
		String ogState = "";

		for (int i = 0; i < N; i++) {
			int s = Integer.parseInt(br.readLine());
			lights1[i] = (s==1);
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

		long numLeft = -1;
		int lastVisit = -1; // time between the last two things that repeated
		int prevNum = 0;

		int[] visited = new int[1<<(N+1)]; // stores at what time you visited it

		while (lastBulbs < B && !az && !reachOg) {
			//if (lastBulbs%1000 == 0) System.out.println("lastBulbs: " + lastBulbs);
			lastBulbs++;

			String curS = "";

			for (int j = 0; j < N; j++) { // loop through lights
				if (lights1[(j-1+N)%N]) { // if the bulb to the left was turned on at time T-1
					lights2[j] = !lights1[j]; // toggle bulb j
				} else {
					lights2[j] = lights1[j];
				}

				if (lights2[j]) curS += "1";
				else curS += "0";
			}
			//System.out.println(curS);
			int curNum = Integer.parseInt(curS, 2);
			//System.out.println(curNum);
			//System.out.println(visited[curNum]);
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
			if (visited[curNum] != 0) { // if you visited the same state before
				lastVisit = lastBulbs-visited[curNum];
				numLeft = B-lastBulbs; // number left to do
				break;
			}
			prevNum = curNum;


			for (int j = 0; j < N; j++) { 
				lights1[j] = lights2[j];
			}
			visited[curNum] = lastBulbs; // mark the time you visited it
		}
		//System.out.println(lastBulbs);
		//System.out.println(System.currentTimeMillis()-T);

		//System.out.println();
		//System.out.println(Arrays.toString(bulbs2));
		if (az) {
			for (int i = 0; i < N; i++) {
				if (lights2[i]) System.out.println(1);
				else System.out.println(0);
			}
			return;
		}
		if (!reachOg && lastVisit == -1) {
			// if you didn't reach the original
			for (int i = 0; i < N; i++) {
				if (lights2[i]) System.out.println(1);
				else System.out.println(0);
			}
			return;
		} 
		if (lastVisit == -1) {
			// last case: you reached the original
			B %= lastBulbs; // put B < lastBulbs

			lastBulbs = 0;
			for (int i = 0; i <= B; i++) { // go up to B

				for (int j = 0; j < N; j++) { // loop through lights
					if (lights1[(j-1+N)%N]) { // if the bulb to the left was turned on at time T-1
						lights2[j] = !lights1[j]; // toggle bulb j
					} else {
						lights2[j] = lights1[j];
					}
				}
				for (int j = 0; j < N; j++) { 
					lights1[j] = lights2[j];
				}
				lastBulbs++;

			}

			for (int i = 0; i < N; i++) {
				if (lights2[i]) System.out.println(1);
				else System.out.println(0);
			}
			return;
		}
		numLeft %= lastVisit;
		lastBulbs = 0;
		for (int i = 0; i <= numLeft; i++) { // go up to numleft

			for (int j = 0; j < N; j++) { // loop through lights
				if (lights1[(j-1+N)%N]) { // if the bulb to the left was turned on at time T-1
					lights2[j] = !lights1[j]; // toggle bulb j
				} else {
					lights2[j] = lights1[j];
				}
			}
			for (int j = 0; j < N; j++) { 
				lights1[j] = lights2[j];
			}
			lastBulbs++;

		}

		for (int i = 0; i < N; i++) {
			if (lights2[i]) System.out.println(1);
			else System.out.println(0);
		}
		return;

		//System.out.print("TIME ! : ");
		//System.out.println(System.currentTimeMillis()-T);
	}

}
