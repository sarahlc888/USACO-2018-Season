import java.io.*;
import java.util.*;
/*
 * USACO 2017 US Open Contest, Gold
 * Problem 3. Modern Art 2
 * 
 * 8/10 test cases, using try catch block got case 10, but 2 and 5 are wrong from index out of bounds
 */
public class MA2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("art2.in"));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N]; // array of colors
		int[] lastIndOf = new int[N+1]; // last index of the color i
		Arrays.fill(lastIndOf, -1); // default -1 for never appearing
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine()); // mark color in arr
			lastIndOf[arr[i]] = i; // mark ind of - by end, it will be the last inds
		}
		br.close();
		
		int maxSize = 0;
		
		try {
			boolean[] opened = new boolean[N]; // if the interval for color i has been opened
			
			int curLayers = 0; // current number of layers
			
			
			for (int i = 0; i < N; i++) {
				//System.out.println("i : " + i);
				
				if (arr[i] == 0) continue; // empty space, no open or close
				
				if (i == lastIndOf[arr[i]] && !opened[arr[i]]) { // open and close
					//System.out.println("c1");
					opened[arr[i]] = true;
					curLayers++;
					if (curLayers > maxSize) maxSize = curLayers;
					curLayers--;
					
				} else if (i == lastIndOf[arr[i]]) { // close
					//System.out.println("c2");
					curLayers--;
					
				} else if (!opened[arr[i]]) { // open
					//System.out.println("c3");
					opened[arr[i]] = true; // mark it as opened
					curLayers++;
				} 
				//System.out.println(curLayers);
				if (curLayers > maxSize) maxSize = curLayers;
			}
			//System.out.println(maxSize);
		} catch (IndexOutOfBoundsException e) {
			maxSize = -1;
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("art2.out")));

		pw.println(maxSize);
		pw.close();
	}


}
