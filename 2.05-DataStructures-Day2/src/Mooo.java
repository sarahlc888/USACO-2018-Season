import java.io.*;
import java.util.*;
import java.awt.Point;

/*
 * uses bad hair strategy in both directions
 * reverse the array and do it again
 * 
 * 3/10 test cases
 */
public class Mooo {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine());
		int[] height = new int[N+1];
		int[] volume = new int[N+1];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			height[i] = Integer.parseInt(st.nextToken());
			volume[i] = Integer.parseInt(st.nextToken());
		}
		scan.close();
		
		// set an end cow
		height[N] = Integer.MAX_VALUE;
		volume[N] = 0;

		// stack of cow heights, always in descending order
		// height is x, ind is y
		Stack<Point> s1 = new Stack<Point>(); 
		
		int[] volHeard1 = new int[N+1]; // how much volume cow i receives

		for (int i = 0; i <= N; i++) { // loop through the cows
			Point p = new Point(height[i], i); 

			while (!s1.empty() && s1.peek().x < height[i]) { 
				// if this new cow is taller than the last cow in the stack, 
				// it will break the descending order.
				// pop until descending order is restored
				Point lastCow = s1.pop();
				// add to the volume heard for the cow
				volHeard1[i] +=
						volume[lastCow.y];
			}

			// push the cow point
			s1.push(p);
		}
		//System.out.println(Arrays.toString(volHeard1));
		
		// reverse the arrays and do it again
		int[] height2 = new int[N+1];
		int[] volume2 = new int[N+1];
		for (int i = 0; i <= N; i++) {
			if (N-i-1 >= 0) {
				height2[i] = height[N-i-1];
				volume2[i] = volume[N-i-1];
			}
			
			
		}
		height2[N] = Integer.MAX_VALUE;
		
		Stack<Point> s2 = new Stack<Point>(); 

		int[] volHeard2 = new int[N+1]; // how much volume cow i receives

		for (int i = 0; i <= N; i++) { // loop through the cows
			Point p = new Point(height2[i], i); 

			while (!s2.empty() && s2.peek().x < height2[i]) { 
				// if this new cow is taller than the last cow in the stack, 
				// it will break the descending order.
				// pop until descending order is restored
				Point lastCow = s2.pop();
				// add to the volume heard for the cow
				volHeard2[i] += volume2[lastCow.y];
			}

			// push the cow point
			s2.push(p);
		}
		
		int[] volHeard3 = new int[N];
		for (int i = 0; i < N; i++) {
			volHeard3[i] = volHeard2[N-1-i];
		}
		
		/*
		System.out.println();
		System.out.println(Arrays.toString(volHeard3));
		System.out.println();
		*/
		int max = 0;
		
		int[] totalVolH = new int[N];
		for (int i = 0; i < N; i++) {
			totalVolH[i] += volHeard1[i];
			totalVolH[i] += volHeard3[i];
			if (totalVolH[i] > max) max = totalVolH[i];
		}
		
		
		
		//System.out.println(Arrays.toString(totalVolH));
		System.out.println(max);
		
		
	}
}
