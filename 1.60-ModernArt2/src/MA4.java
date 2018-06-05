import java.io.*;
import java.util.*;
/*
 * USACO 2017 US Open Contest, Gold
 * Problem 3. Modern Art 2
 * 
 * properly uses the stack idea
 * simpler if statements
 * 
 * 10/10 test cases
 */
public class MA4 {
	public static int N;
	public static int[] arr;
	public static int[] last;
	public static int[] first;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("art2.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/2.in"));
		N = Integer.parseInt(br.readLine());
		arr = new int[N]; // array of colors
		last = new int[N+1]; // last index of the color i
		first = new int[N+1]; // first index of the color i
		Arrays.fill(last, -1); // default -1 for never appearing
		Arrays.fill(first, -1); // default -1 for never appearing
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine()); // mark color in arr
			last[arr[i]] = i; // mark ind; by end, it will be the last inds
		}
		br.close();
		
		for (int i = N-1; i >= 0; i--) {
			first[arr[i]] = i; // mark ind; by end, it will be the last inds
		}
		
		int max = calc();
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("art2.out")));
		System.out.println(max);
		pw.println(max);
		pw.close();
	}
	public static int calc() {
		Stack<Integer> s = new Stack<Integer>(); // stack to track open intervals
		int maxSize = 0; // of the stack

		for (int i = 0; i < N; i++) { // loop through the color array
			if (arr[i] == 0) continue; // ignore the 0s

			if (i == first[arr[i]]) { // open
				s.push(arr[i]); // add the color to the stack
				if (s.size() > maxSize) maxSize = s.size(); // update
			}
			if (s.peek() != arr[i]) { // make sure that the intervals are right and lined up
				System.out.println("i : " + i);
				return -1;
			}
			if (i == last[arr[i]]) { // close
				s.pop();
			}
			
		}
		return maxSize;

	}

}
