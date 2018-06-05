import java.io.*;
import java.util.*;
/*
 * USACO 2017 US Open Contest, Gold
 * Problem 3. Modern Art 2
 * 
 * 7/10 test cases, 3 runtime errors
 */
public class MA {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("art2.in"));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N]; // array of colors
		int[] lastIndOf = new int[N+1]; // last index of the color i, 0 is open bc it's not a color
		Arrays.fill(lastIndOf, -1); // default -1 for never appearing
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine()); // mark color in arr
			lastIndOf[arr[i]] = i; // mark last ind of - by end, it will be right
		}
		br.close();

		
		
		//System.out.println(Arrays.toString(arr));
		//System.out.println(Arrays.toString(lastIndOf));
		
		boolean[] opened = new boolean[N]; // if the interval for color i has been opened
		
		int curLayers = 0; // current number of layers
		int maxSize = 0;
		
		for (int i = 0; i < N; i++) {
			//System.out.println("i : " + i);
			
			if (arr[i] == 0) continue; // empty
			if (i == lastIndOf[arr[i]] && !opened[arr[i]]) {
				//System.out.println("c1");
				// do nothing, it would be ++ then --
				curLayers++;
				if (curLayers > maxSize) maxSize = curLayers;
				curLayers--;
			}
			else if (i == lastIndOf[arr[i]]) {
				//System.out.println("c2");
				// if this is the closing of a color interval
				curLayers--;
			} else if (!opened[arr[i]]) {
				//System.out.println("c3");
				// if the color interval at arr[i] hasn't started
				opened[arr[i]] = true; // mark it as opened
				curLayers++;
			} 
			//System.out.println(curLayers);
			if (curLayers > maxSize) maxSize = curLayers;
		}
		//System.out.println(maxSize);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("art2.out")));

		pw.println(maxSize);
		pw.close();
	}


}
