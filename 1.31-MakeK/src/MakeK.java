import java.io.*;
import java.util.*;

// make K using (+, -, *, /) out of N numbers


public class MakeK {
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
		int[] nums = new int[N]; // array of nums
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		
		/*
		System.out.println("K: " + K);
		System.out.println("N: " + N);
		for (int i = 0; i < N; i++) {
			System.out.println("nums " + i + ": " + nums[i]);
		}
		*/
		int[] ops = new int[N-1];
		

		
		int[] operations = new int[4];
		for (int i = 0; i < operations.length; i++) {
			operations[i] = i;
		}
		// 0 = + ; 1 = - ; 2 = * ; 3 = /
		
		int[] usedOp = new int[N-1]; // the operations to plug into the equation
		for (int i = 0; i < usedOp.length; i++) {
			usedOp[i] = operations[i];
		}
		
		// shuffle nums in all possible ways
		// shuffle usedoperations in all possible ways
		
		
		ArrayList<Integer> equation = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			equation.add(nums[i]);
			if (i != N-1) { // don't put an operation after the last number
				equation.add((Integer)(-1));
			}
		}
		
		// even indices are numbers, odd indices are operations
		// plug in the operations
		for (int i = 1; i < equation.size(); i = i+2) {
			equation.remove(i);
			equation.add(i, usedOp[(int)((i-1)/2)]);
		}
		
		
		for (int i = 0; i < equation.size(); i++) {
			System.out.println(equation.get(i));
		}
		
		
		
		
		
		br.close();
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


}
