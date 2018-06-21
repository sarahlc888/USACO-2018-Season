import java.io.*;
import java.util.*;

/*
 * all test cases correct
 * 
 */
public class BDS2 {
	static ArrayList<ArrayList<Integer>> workingPerms = new ArrayList<ArrayList<Integer>>();
	static int N;
	static int sum;
	static ArrayList<ArrayList<Integer>> pt;
	
	public static void main(String[] args) {
		
		// precompute pascal's triangle
		pt = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 10; i++) { // add right side 1s
			pt.add(new ArrayList<Integer>());
			pt.get(i).add(1);
		}
		pt.get(1).add(1);
		for (int i = 2; i < 10; i++) { // calculate the rows
			
			for (int j = 0; j < pt.get(i-1).size() - 1; j++) {
				pt.get(i).add(pt.get(i-1).get(j) + pt.get(i-1).get(j+1) );
			}
			pt.get(i).add(1);
		}
		
		
		// input
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		N = Integer.parseInt(st.nextToken());
		sum = Integer.parseInt(st.nextToken());
				
		int[] nums = new int[N];
		int numPerms = 1; // N!
		for (int i = 0; i < N; i++) {
			nums[i] = i+1;
			numPerms *= (i+1);
		}
		
        permute(nums, 0);
        
        Collections.sort(workingPerms, permComparator);
       
        for (int j = 0; j < N; j++) {
        		System.out.print(workingPerms.get(0).get(j) + " ");
        }
 	}
	static void permute(int[] a, int k) {
		if (k == a.length) {
			
			// test the perm
			int total = 0;
			for (int j = 0; j < N; j++) {
				total += a[j]*pt.get(N-1).get(j);
			}
			// if the perm works, add it to wokring perms
			if (total == sum) {
				workingPerms.add(new ArrayList<Integer>()); // make the perms
				for (int i = 0; i < a.length; i++) {
					
					workingPerms.get(workingPerms.size()-1).add(
							a[i]);
				}
			}
		} else {
			for (int i = k; i < a.length; i++) {
				int temp = a[k];
				a[k] = a[i];
                a[i] = temp;
 
                permute(a, k + 1);
 
                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
    }
	public static Comparator<ArrayList<Integer>> permComparator = new Comparator<ArrayList<Integer>>(){

		@Override
		public int compare(ArrayList<Integer> x, ArrayList<Integer> y) {
			for (int i = 0; i < x.size(); i++) {
				if (x.get(i) > y.get(i)) {
					return 1;
				} else if (x.get(i) < y.get(i)) {
					return -1;
				}
			}
			return 0;
			
		}
	};
 

}
