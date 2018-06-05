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
 * O(n) = n(n+1) log n --> 6*10^8 --> too long!!!! 10^8 max
 * 
 */
public class SumThree1 {
	
	static int[] arr;
	static int N;
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
		int M = Integer.parseInt(st.nextToken()); // target value
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		br.close();

		Arrays.sort(arr);

		if (M == 0) System.out.println("sum to 0: " + sumToZero());
		else System.out.println("sum to M: " + sumToM(M));
		
		System.out.println("sum to ArrK: " + sumToArrK());

		PrintWriter pw = new PrintWriter((new File("results/" + k + ".out")));

		pw.println();
		pw.close();
	}
	public static int sumToZero() {
		// returns number of triples (i, j, k) where arr[i] + arr[j] + arr[k] == 0
		int count = 0;

		for (int i = 0; i < N; i++) { // i
			for (int j = i+1; j < N; j++) { // i cannot equal j
				//if (Arrays.binarySearch(arr, -1*(arr[i] + arr[j])) >= 0) { // search for a k
				
				if (Arrays.binarySearch(arr, -1*(arr[i] + arr[j])) > j) { 
					// NEW: search for a k bigger than j to avoid duplicates
					// k = -1(arr[i] + arr[j] so that they would all sum to 0
					count++;
				}
			}
		}
		return count;
		//return count/3; // account for overcounting
	}
	public static int sumToM(int M) {
		// returns number of triples (i, j, k) where arr[i] + arr[j] + arr[k] == M
		int count = 0;

		for (int i = 0; i < N; i++) { // i
			for (int j = i+1; j < N; j++) { // i cannot equal j
				if (Arrays.binarySearch(arr, M-(arr[i] + arr[j])) > j) { 
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
				int t = arr[i] + arr[j];
				
				if (t > 10000 || t < -10000) {
					//outOfRange++;
					continue;
				}
				int k = Arrays.binarySearch(arr, t);
				if (k >= 0 && k != i && k != j) { 
					// search for any k, just find a trio 
					count++;
					//System.out.println(k);
				}
			}
		}
		//System.out.println("out of range: " + outOfRange);
		return count;
	}

}
