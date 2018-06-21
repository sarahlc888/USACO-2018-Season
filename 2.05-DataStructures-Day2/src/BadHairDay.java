import java.io.*;
import java.util.*;

/*
 * 3/10 test cases,,,?? 
 */
public class BadHairDay {
	public static void main(String args[]) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine()); // number of cows
		int[] height = new int[N+1]; // heights of the cows
		for (int i = 0; i < N; i++) { // scan in cow heights
			height[i] = Integer.parseInt(scan.nextLine());
		}
		height[N] = Integer.MAX_VALUE; // add an infinity height cow at the end
		
		// stack of cow heights, always in descending order
		Stack<Point> s = new Stack<Point>(); 
		
		int[] visible = new int[N]; // visible[i] = how many cows cow i can see
		
		for (int i = 0; i <= N; i++) { // loop through the cows
			Point p = new Point(height[i], i);
			
			while (!s.empty() && s.peek().height <= height[i]) { 
				// if this new cow is taller or same height as the last cow, 
				// it will break the descending order.
				// pop until descending order is restored
				Point lastCow = s.pop();
				// mark how many cows are visible
				visible[lastCow.ind] = i - lastCow.ind - 1;
			}
			
			// push the cow point
			s.push(p);
		}
		
		long sum = 0;
		
		for (int i = 0; i < N; i++) {
			sum += visible[i];
		}
		System.out.println(sum);
		
	}
	public static class Point {
		int height;
		int ind;
		
		public Point(int xin, int yin) {
			height = xin;
			ind = yin;
		}
	}
}
/*
input:
6
10
3
7
4
12
2
*/