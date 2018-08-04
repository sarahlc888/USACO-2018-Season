import java.io.*;
import java.util.*;
/*
 * DP
 * not plain knapsack
 * order matters
 * 
 * BASH
 * 
 * 4/14 cases
 */
public class GuardMark {
	static int N;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("guard.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num cows
		int H = Integer.parseInt(st.nextToken()); // goal height
		
		// cows
		int[] height = new int[N];
		int[] weight = new int[N];
		int[] strength = new int[N];
		
		for (int i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			height[i] = Integer.parseInt(st.nextToken());
			weight[i] = Integer.parseInt(st.nextToken());
			strength[i] = Integer.parseInt(st.nextToken());
		}
		br.close();

		int maxSF = -1*Integer.MAX_VALUE;
		
		// "DP"
		// TODO: convert to long if needed
		LinkedList<S> states = new LinkedList<S>();
		states.add(new S(0, Integer.MAX_VALUE));
		while (!states.isEmpty()) {
			S cur = states.removeFirst();
			for (int i = 0; i < N; i++) { // cows
				if (cur.hasCow[i]) continue;
				// if cow i is not present in cur state, 
				// add cow i on top of the current stack
				if (cur.strength < weight[i]) continue; 
				// if stack can hold the weight, proceed
				// height gets added on, strength is either new strength or modified old strength
				S next = new S(cur.height + height[i], Math.min(cur.strength-weight[i], strength[i]));
				next.hasCow[i] = true;
				states.add(next);
				if (next.height >= H && next.strength > maxSF) {
					maxSF = next.strength;
				}
			}
			
		}
		
//		System.out.println(maxSF);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		if (maxSF == -1*Integer.MAX_VALUE) {
			pw.println("Mark is too tall");
			pw.close();
			return;
		}
		pw.println(maxSF);
		pw.close();
	}
	public static class S implements Comparable<S> {
		int height;
		int strength;
		boolean[] hasCow = new boolean[N]; // if cows are present
		TreeSet<Integer> cows = new TreeSet<Integer>(); // cows used (not in order)
		// TODO: if needed, cows in order
		
		public S(int a, int b) {
			height = a;
			strength = b;
		}
		@Override
		public int compareTo(S o) { // sort by x, then y
			if (height == o.height) return strength-o.strength;
			return o.height-height;
		}
		public String toString() {
			return height + " " + strength;
		}
	}


}
