import java.io.*;
import java.util.*;

// make K using (+, -, *, /) out of N numbers

public class MakeK3 {
	
	public static int[][] perms;
	public static int ind = 0;
	public static int N;
	public static int K;
	
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
		K = Integer.parseInt(st.nextToken()); // target value
		N = Integer.parseInt(st.nextToken()); // number of nums
		int[] nums = new int[N]; // array of nums
		st = new StringTokenizer(br.readLine());
		
		
		for (int i = 0; i < N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
			
		}
		
		br.close();
		perms = new int[factorial(N)][N];
		
		permut(nums); // shuffle nums in all N! possible ways
		/*
		for (int i = 0; i < perms.length; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(perms[i][j] + " ");
			}
			System.out.println();
		}
				
		int[] operations = new int[4];
		for (int i = 0; i < operations.length; i++) {
			operations[i] = i;
		}
		// 0 = + ; 1 = - ; 2 = * ; 3 = /
		*/
		
		// apply operations in all possible ways
		for (int i = 0; i < perms.length; i++) {
			applyOp(0, 0, perms[i]);
		}
		PrintWriter pw = new PrintWriter((new File("testData/" + k + ".out")));

		pw.println();
		pw.close();
	}
	         
	
	public static void permut(int[] nums) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			b.add(nums[i]);
		}
		perm1(a, b);
	}
	public static void perm1(ArrayList<Integer> pref, ArrayList<Integer> body) {
		int n = body.size();
		if (n == 0) { // report that perm
			for (int i = 0; i < N; i++) {
				perms[ind][i] = pref.get(i);
			}
			ind++;
		} else {
			for (int i = 0; i < n; i++) {
				ArrayList<Integer> c = new ArrayList<Integer>();
				c.addAll(pref);
				c.add(body.get(i));
				ArrayList<Integer> d = new ArrayList<Integer>();
				d.addAll(body);
				d.remove(i);
				
				perm1(c, d);
			}
				
		}
	}
	public static int factorial(int n) {
		int running = 1;
		for (int i = n; i > 0; i--) {
			running *= i;
		}
		return running;
	}
	public static void applyOp(float running, int curInd, int[] curNums) {
		if (curInd == N) {
			//System.out.println("running: " + running);
			// evaluate and end that recursive branch
			if (running == K) {
				// goal achieved
				System.out.println("goal!");
			} else {
				// not achieved
				System.out.println("not goal");
			}
		} else {
			
			applyOp(running + (float) curNums[curInd], curInd+1, curNums);
			applyOp(running - (float) curNums[curInd], curInd+1, curNums);
			applyOp(running * (float) curNums[curInd], curInd+1, curNums);
			applyOp(running / (float) curNums[curInd], curInd+1, curNums);
		}
	}

}
