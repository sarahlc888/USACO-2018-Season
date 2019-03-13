import java.io.*;
import java.util.*;
/*
 * 
 */
public class P1 {
	public static long MOD = 1000000007;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("poetry.in"));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		Pair[] arr = new Pair[N]; // words
		int maxRhymeClass = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			maxRhymeClass = Math.max(arr[i].rhyme, maxRhymeClass);
		}
		Arrays.sort(arr);
		int numVals = 0;
		int[] rhymeScheme = new int[M];
		for (int i = 0; i < M; i++) {
			rhymeScheme[i] = (br.readLine()).charAt(0)-65;
			numVals = Math.max(numVals,  rhymeScheme[i]);
		}
		numVals++;
//		System.out.println(Arrays.toString(rhymeScheme));
		
//		HashMap<Integer, ArrayList<Integer>> rhymeToSyll = new HashMap<Integer, ArrayList<Integer>>();
//		for (int i = 0; i < N; i++) {
//			if (!rhymeToSyll.containsKey(arr[i].rhyme)) 
//				rhymeToSyll.put(arr[i].rhyme, new ArrayList<Integer>());
//			rhymeToSyll.get(arr[i].rhyme).add(arr[i].syll);
//		}
//		
		
		int[] DP = new int[K+1]; // DP[i] = number of ways to make a line with i syllables
		DP[0] = 1;
		for (int i = 0; i <= K; i++) {
			for (int j = 0; j < N; j++) {
				Pair curWord = arr[j];
				if (i >= curWord.syll) {
					DP[i] += DP[i-curWord.syll];
					DP[i] %= MOD;
				}
					
			}
		}
//		System.out.println(DP[10]);
		
		// number of ways to make a line with i syllables and last word with class j
		int[][] DP2 = new int[K+1][maxRhymeClass+1]; // 5000^5000
		for (int i = 0; i <= K; i++) { // syllable
			for (int j = 0; j < N; j++) { // word
				Pair curWord = arr[j];
				if (i >= curWord.syll) {
					DP2[i][curWord.rhyme] += DP[i-curWord.syll];
					DP2[i][curWord.rhyme] %= MOD;
				}
			}
		}
//		System.out.println(DP2[10][1]);
//		System.out.println(DP2[10][2]);
		
		// count number of lines in each rhyme scheme letter
		int[] countVals = new int[numVals+1];
		for (int i = 0; i < M; i++ ) {
			countVals[rhymeScheme[i]]++;
		}
		long totalTotal = 1;
		for (int i = 0; i <= numVals; i++ ) {
			long total = 0;
			if (countVals[i] > 0) {
				for (int j = 0; j <= maxRhymeClass; j++) { // looping through classes
					
					long add = powMod(DP2[K][j], countVals[i], MOD);
//					long add = 1;
//					for (int k = 0; k < countVals[i]; k++) { // power
//						add *= DP2[K][j];
//						add %= MOD;
//					}
//					long add = (long)Math.pow(DP2[K][j], countVals[i]);
					total += add;
					total %= MOD;
				}
			}
			if (total != 0) {
				totalTotal *= total;
				totalTotal %= MOD;
			}
		}
//		System.out.println(totalTotal);
		while (totalTotal < 0) totalTotal += MOD;
		
		br.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("poetry.out")));

		pw.println(totalTotal);
		pw.close();
	}
	public static long powMod(long a, long b, long k) {
		// binary form of integer b
		String s = Long.toBinaryString(b);

		int m = s.length();
		
		// store the powers a^(2^j), j=0, ... , m-1
		long[] ap = new long[m];
		
		ap[m-1] = a%k;
		for(int j=1; j<m; j++) {
			ap[m-j-1] = (long) ((long)ap[m-j] * ap[m-j] % k);
		}

		int prod = 1;
		for(int j=0; j<m; j++) {
			int digit = s.charAt(j) - '0';
			if( digit==1 )
				prod = (int) ((long)prod * ap[j]%k); 
		}
		return prod;
	}
	
	public static class Pair implements Comparable<Pair> {
		int syll;
		int rhyme;

		public Pair(int a, int b) {
			syll = a;
			rhyme = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (syll == o.syll) return rhyme-o.rhyme;
			return o.syll-syll;
		}
		public String toString() {
			return syll + " " + rhyme;
		}
	}


}
