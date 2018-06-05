import java.util.*;
import java.io.*;

// problem: n triples of (a, b, K)
// return a^b % K
// a^b % K == (a%K)^b % K (proved with binomial expansion)


public class ModuloProblem {
	public static void main(String args[]) throws IOException {
		//System.out.println(processTriple(329465671, 831117906, 3194104));
		for (int i = 1; i <= 10; i++) {
			processOneFile("testData/" + i + ".in", i);
		}
	}
	public static void processOneFile(String file, int r) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		PrintWriter pw = new PrintWriter(new File("mod" + r + ".out"));
		int N = Integer.parseInt(br.readLine()); // number of triples to process
		
		// process all the triples
		for (int i = 0; i < N; i++) {
			
			String triple = br.readLine();
			String[] trip = triple.split(" ");
			int K = Integer.parseInt(trip[2]);
			// mod a by K (take it out now rather than later to reduce size of the numbers)
			int a = Integer.parseInt(trip[0]) % K; 
			int b = Integer.parseInt(trip[1]);
			
			//System.out.println(processTriple(a, b, K));
			pw.println(processTriple(a, b, K));
		}
		br.close();
		pw.close();
	}
	public static int processTriple(int a, int b, int K) {
		//System.out.println(a + " " + b + " " + K);
		a = a % K;
		//System.out.println(a + " " + b + " " + K);
		String binB = Integer.toString(b, 2); // string value of b in binary
		//System.out.println("binB: " + binB);
		int cnt = binB.length();
		
		// powers of a
		int[] powers = new int[cnt]; // powers[0] = a^1; powers[1] = a^2; powers[2] = a^4...
		powers[0] = a % K;
		for (int j = 1; j < cnt; j++) {
			
			long sqr = (long) powers[j-1] * (long) powers[j-1];
			powers[j] = (int) (sqr % K);
			powers[j] = powers[j] % K;
		}
		//System.out.println("powers");
		//for (int j = 0; j < cnt; j++) {
			//System.out.println(powers[j]);
		//}
		//System.out.println("");
		int count = 1;
		
		for (int j = 0; j < binB.length(); j++) {
			
			int ind = binB.length() - 1 - j; // index within binB to match with powers[j]
			
			//System.out.println("ind: " + ind);
			//System.out.println("char: " + binB.charAt(ind));
			
			//System.out.println("j: " + j);
			
			// if there is a one in that place value, add the proper power to the count
			if (binB.charAt(ind) == '1') {
				//long newcount = ((long)count * (long)powers[j]);
				//count = (int) (newcount % K);
				count = (int) (((long)count * (long)powers[j]) % K);
				//System.out.println("count: " + count);
				count = count % K;
				//System.out.println("count: " + count);
			} 
		}
		//System.out.println(count);
		return count;
	}
}
