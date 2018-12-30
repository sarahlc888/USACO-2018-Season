import java.io.*;
import java.util.*;
/*
 * from 11/6 lesson
 * written on 12/2
 * easy implementation, brute force
 */
public class Nail {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("27.in"));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// how many pieces of length i exist
		// length can range from 1 to 2000 inclusive
		int[] arr = new int[2001]; 
		
		int maxL = 0; // max L that appears
		for (int i = 0; i < N; i++) {
			int curLen = Integer.parseInt(st.nextToken());
			arr[curLen]++;
			maxL = Math.max(maxL, curLen);
		}
		br.close();

//		for (int i = 0; i <= maxL; i++) {
//			System.out.print(arr[i] + " ");
//		}
		
		// if a certain height has been checked
		boolean[] checkedHeight = new boolean[maxL*2+1]; 
		
		int numHeights = 0;
		int maxCount = 0;
		// loop through all possible heights
		for (int i = 0; i <= maxL; i++) {
			if (arr[i] == 0) continue;
			for (int j = i+1; j <= maxL; j++) {
				if (i==j || arr[j] == 0) continue;
				
				int curHeight = i+j;
				if (checkedHeight[curHeight]) continue;
				checkedHeight[curHeight] = true;
				
//				System.out.println("curh: " + curHeight);
						
				int count = 0;
				
				for (int k = 1; k < (int)((float)curHeight/2+0.5); k++) {
					if (k > 2000 || curHeight-k > 2000) continue; // no more fences
					int numAdded = Math.min(arr[k], arr[curHeight-k]);
//					System.out.println("  k : " + k + " numAdded: " + numAdded);
					count += numAdded;
				}
				
				if (curHeight%2==0) {
					count += arr[curHeight/2]/2;
				}
//				System.out.println("curh: " + curHeight + " ct: " + count);
				if (count > maxCount) {
					maxCount = count;
					numHeights = 1;
				} else if (count == maxCount) {
					numHeights++;
				}				
			}
		}
		System.out.println(maxCount + " " + numHeights);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nail.out")));
		pw.println(maxCount);
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
