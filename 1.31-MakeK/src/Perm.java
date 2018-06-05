import java.util.*;

// permutates an array of numbers

public class Perm {
	public static int[][] perms;
	public static int ind;
	public static int N;
	public static void main(String[] args) {
		N = 3; // number of nums
		perms = new int[factorial(N)][N];
		int[] nums = {1, 2, 3};
		permut(nums);
		
		for (int i = 0; i < perms.length; i++) {
			for (int j = 0; j < N; j++) {
				System.out.println(perms[i][j]);
			}
			System.out.println();
		}
		
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
	
}
