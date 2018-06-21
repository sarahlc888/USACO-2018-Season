import java.io.*;
import java.util.*;
/*
 * 10/10 test cases
 * O(LC+N)
 * for every cost you iterate through all N
 */
public class CowRollerCoast {
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int L = Integer.parseInt(st.nextToken()); // length
		int N = Integer.parseInt(st.nextToken()); // num comps
		int B = Integer.parseInt(st.nextToken()); // budget
		
		Comp[] comps = new Comp[N];
		
		// maps an index to components that end there
		HashMap<Integer, ArrayList<Integer>> ends = new HashMap<Integer, ArrayList<Integer>>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken()); 
			int b = Integer.parseInt(st.nextToken()); 
			int c = Integer.parseInt(st.nextToken()); 
			int d = Integer.parseInt(st.nextToken()); 
			comps[i] = new Comp(a, b, c, d);
			
			int end = comps[i].start + comps[i].length;
			if (ends.containsKey((Integer)end)) { // if already init
				ends.get(end).add(i); // add component i 
			} else { // init
				ends.put(end, new ArrayList<Integer>());
				ends.get(end).add(i); // add component i 
			}
			
		}
		
		// DP[length][cost] = max fun using 0...length and having cost cost
		// basecase is just 0s everywhere
		int[][] DP = new int[L+1][B+1];
		for (int i = 0; i <= L; i++) {
			for (int j = 0; j <= B; j++) {
				DP[i][j] = -1;
			}
		}
		
		DP[0][0] = 0;
		
		for (int i = 0; i <= L; i++) { // current length
			//System.out.println("i: " + i);
			
			ArrayList<Integer> endHere = ends.get(i); // components ending at i
			
			if (endHere == null) 
				continue; // if no components, skip i
			
			for (int cost = 0; cost <= B; cost++) { // current cost
				//System.out.println("  cost: " + cost);
				
				for (int j = 0; j < endHere.size(); j++) { // loop through components
					int comp = endHere.get(j);
					//System.out.println("    comp: " + comp);
					
					//System.out.println("    DP " + comps[comp].start + "  " + (cost-comps[comp].cost));
					
					if (cost-comps[comp].cost >= 0)
						//System.out.println("    " + DP[comps[comp].start][cost-comps[comp].cost]);
					if (cost-comps[comp].cost >= 0 &&
							DP[comps[comp].start][cost-comps[comp].cost] != -1) { // check if could come before
						
						DP[i][cost] = Math.max(DP[i][cost], 
								DP[comps[comp].start][cost-comps[comp].cost]+comps[comp].fun);
						//System.out.println("    reassign to " + DP[i][cost]);
						
					}
				}
				
			}
			
		}
		int max = 0;
		for (int i = 0; i <= B; i++) {
			if (DP[L][i] > max) max = DP[L][i];
		}
		if (max == 0) {
			System.out.println(-1);
		} else {
			System.out.println(max);

		}
		////System.out.println(DP[L][B]);
	}
	public static class Comp {
		int start;
		int length;
		int end;
		int fun;
		int cost;
		public Comp(int a, int b, int c, int d) {
			start = a;
			length = b;
			fun = c;
			cost = d;
			end = start+length;
		}
	}
}
