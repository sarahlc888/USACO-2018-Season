import java.io.*;
import java.util.*;

// make K using (+, -, *, /) out of N numbers

public class MakeK2 {
	
	public static int[] nums; // numbers to draw from
	public static int[][] numPerms; // permutations of the numbers
	public static int ind = 0; // running ind in numPerms
	
	public static void main(String args[]) throws IOException {
		//for (int i = 1; i <=10; i++) {
		for (int i = 1; i <=1; i++) {
			String filename = "testData/" + i + ".in";
			processOneFile(filename, i);
		}
	}
	public static void processOneFile(String filename, int k) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int K = Integer.parseInt(st.nextToken()); // target value
		int N = Integer.parseInt(st.nextToken()); // number of nums
		nums = new int[N]; // array of nums
		st = new StringTokenizer(br.readLine());
		
		
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
			
		}
		
		br.close();
		
				
		int[] operations = new int[4];
		for (int i = 0; i < operations.length; i++) {
			operations[i] = i;
		}
		// 0 = + ; 1 = - ; 2 = * ; 3 = /
		
		
		
		// shuffle nums in all N! possible ways
		numPerms = new int[factorial(N)][N];
		
		
		// shuffle usedoperations in all possible ways
		
		PrintWriter pw = new PrintWriter((new File("testData/" + k + ".out")));

		pw.println();
		pw.close();
	}
	   
	private static void comb1(String prefix, String s) {
        if (s.length() > 0) {
            System.out.println(prefix + s.charAt(0));
            comb1(prefix + s.charAt(0), s.substring(1));
            comb1(prefix,               s.substring(1));
        }
    }  
	public static int factorial(int n) {
		int running = 1;
		for (int i = n; i > 0; i--) {
			running *= i;
		}
		return running;
	}


}
