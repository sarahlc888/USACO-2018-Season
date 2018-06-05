import java.io.*;
import java.util.*;
/*
 * USACO 2017 US Open Contest, Gold
 * Problem 3. Modern Art 2
 * 
 * properly uses the stack idea
 * 
 * 10/10 test cases
 */
public class MA3 {
	public static int N;
	public static int[] arr;
	public static int[] lastIndOf;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("art2.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/2.in"));
		N = Integer.parseInt(br.readLine());
		arr = new int[N]; // array of colors
		lastIndOf = new int[N+1]; // last index of the color i
		Arrays.fill(lastIndOf, -1); // default -1 for never appearing
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine()); // mark color in arr
			lastIndOf[arr[i]] = i; // mark ind of - by end, it will be the last inds
		}
		br.close();

		int max = calc();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("art2.out")));

		pw.println(max);
		pw.close();
	}
	public static int calc() {
		boolean[] opened = new boolean[N+1]; // if the interval for color i has been opened

		Stack<Integer> s = new Stack<Integer>(); // stack to track open intervals
		
		int maxSize = 0; // of the stack

		for (int i = 0; i < N; i++) { // loop through the color array
			
			if (arr[i] == 0) continue; // empty space, no open or close

			if (i == lastIndOf[arr[i]] && !opened[arr[i]]) { // open and close
				opened[arr[i]] = true;
				s.add(arr[i]); // add the color to the stack
				if (s.size() > maxSize) maxSize = s.size();
				s.pop(); // take it back off
			} else if (i == lastIndOf[arr[i]]) { // close
				if (s.pop() != arr[i]) {
					return -1;
				}
			} else if (!opened[arr[i]]) { // open
				opened[arr[i]] = true; // mark it as opened
				s.push(arr[i]); // add the color to the stack
			} 
			//System.out.println(curLayers);
			if (s.size() > maxSize) maxSize = s.size();
		}
		//System.out.println(maxSize);
		return maxSize;

	}

}
