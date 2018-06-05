import java.io.*;
import java.util.*;
/*
 * includes all three versions of the problem
 * 
 * Given a sequence of distinct integers A1, A2, ..., AN , 
 * where each integer is in the range [-104, 104], 
 * find out the number of triples (i,j,k) such that 
 * Ai + Aj + Ak = 0, where 1 ≤ i < j < k ≤ N
 * 
 * 
 * O(n) = n^2 = 10^8
 * 
 */
public class SumThree2 {
	
	static int[] arr;
	static int[] pres;
	static int N;
	static int M;
	public static void main(String args[]) throws IOException {
		for (int i = 4; i <=4; i++) {
			long t = System.currentTimeMillis();
			String filename = "testData/" + i + ".in";
			processOneFile(filename, i);
			System.out.println("time: " + (System.currentTimeMillis() - t));
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // number of ints to follow
		M = Integer.parseInt(st.nextToken()); // target value
		
		arr = new int[N]; // scan in array of numbers
		pres = new int[20001]; // fill in presence array [-10000, 10000] --> [0, 20000]
		// pres[k+10000] = i where arr[i] = k
		for (int i = 0; i < pres.length; i++) pres[i] = -1; // default = -1 (not present)
		
		//int min = 0;
		
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
			//if (arr[i] < min) min = arr[i];
			pres[arr[i]+10000] = i;
		}
		
		//System.out.println("min: " + min);
		br.close();

		System.out.println("sum to M: " + sumToM());
		System.out.println("sum to ArrK: " + sumToArrK());

		PrintWriter pw = new PrintWriter((new File("results/" + k + ".out")));

		pw.println();
		pw.close();
	}
	
	public static int sumToM() {
		// returns number of triples (i, j, k) where arr[i] + arr[j] + arr[k] == M
		int count = 0;

		for (int i = 0; i < N; i++) { // i
			for (int j = i+1; j < N; j++) { // i cannot equal j
				int target = M-(arr[i] + arr[j]);
				int k = checkPres(target);
				
				if (k > j) { 
					// k = M-(arr[i] + arr[j] so that they would all sum to 0
					count++;
					
				}
			}
		}
		return count;
	}
	public static int sumToArrK() {
		// returns number of triples (i, j, k) where arr[i] + arr[j] = arr[k]
		int count = 0;

		//int outOfRange = 0;
		
		for (int i = 0; i < N; i++) { // i
			for (int j = i+1; j < N; j++) { // i cannot equal j
				int t = arr[i] + arr[j]; // target val for arr[k]
				if (t > 10000 || t < -10000) {
					//outOfRange++;
					continue;
				}
				int k = checkPres(t); // check for index k where arr[k] == t
				if (k >= 0 && k != i && k != j) { 
					// search for any k, just find a trio 
					//System.out.println(k);
					count++;
				}
			}
		}
		//System.out.println("out of range: " + outOfRange);
		return count;
	}
	public static int checkPres(int t) {
		// checks which index i arr[i] = t
		t += 10000; // scale up for present[]
		if (t < 0 || t > 20000) { // out of range
			return -1;
		} else {
			return pres[t];
		}
		
	}

}
