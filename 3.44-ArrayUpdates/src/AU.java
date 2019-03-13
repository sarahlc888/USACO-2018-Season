import java.io.*;
import java.util.*;
/*
 * 
 */
public class AU {
	static long[] ms;
	static long[] cs;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // array size
		int M = Integer.parseInt(st.nextToken()); // num updates
		
		int[] arr = new int[N]; // array of numbers
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		ms = new long[N]; // ms[i] = max sum at index i
		cs = new long[N]; // cs[i] = cur sum at index i
		Arrays.fill(cs, Long.MAX_VALUE);
		Arrays.fill(ms, Long.MAX_VALUE);
		
		for (int i = 0; i < M; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			int ind = Integer.parseInt(st.nextToken())-1; // adjust indexing
			int newVal = Integer.parseInt(st.nextToken());
			arr[ind] = newVal;
			System.out.println(maxSum(arr, ind));
		}
		br.close();
//		System.out.println(maxSum(arr));
	}
	public static long maxSum(int[] nums) {
		long curSum = 0;
		long maxSum = 0;
		for (int i = 0; i < nums.length; i++) {
			curSum += nums[i];
			if (curSum < 0) curSum = 0;
			maxSum = Math.max(maxSum, curSum);
			ms[i] = maxSum;
		}
		return maxSum;
	}
	public static long maxSum(int[] nums, int start) {
		if (start == 0) return maxSum(nums);
		long curSum = cs[start-1];
		long maxSum = ms[start-1];
		if (curSum == Long.MAX_VALUE) {
			return maxSum(nums);
		} else {
			for (int i = start; i < nums.length; i++) {
				curSum += nums[i];
				if (curSum < 0) curSum = 0;
				maxSum = Math.max(maxSum, curSum);
				ms[i] = maxSum;
				cs[i] = curSum;
			}
			return maxSum;
		}
	}

}
