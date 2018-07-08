import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * 10/10 test cases
 * 
 * sweep line with a stack
 * add heights, when there's a dip, pop until <=, if <, add the new one
 * every time you push, ans++
 */
public class CitySkyline {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num coords
		int W = Integer.parseInt(st.nextToken()); // total width

		Stack<Integer> s = new Stack<Integer>(); // increasing stack
		int ans = 0;
		s.push(0);

		for (int i = 0; i < N; i++) {

			st = new StringTokenizer(br.readLine());
			int pos = Integer.parseInt(st.nextToken()); 
			int height = Integer.parseInt(st.nextToken()); 

			//System.out.println("pos: " + pos + " height: " + height);

			if (s.isEmpty() || s.peek() < height) { // if the new building is taller, add it
				s.push(height);
				ans++;
				//System.out.println("  a");
				continue; // proceed
			}

			while (!s.empty() && s.peek() > height) { // if there's a dip, 
				// pop until s.peek() <= height
				s.pop();
			}
			if (!s.empty() && s.peek() == height) {
				// if it was already there, do nothing
				//System.out.println("  b");
			} else {
				// if it is new, add it
				s.push(height);
				ans++;
				//System.out.println("  c");
			}

		}
		System.out.println(ans);
	}
}
