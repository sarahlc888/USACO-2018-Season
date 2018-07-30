import java.io.*;
import java.util.*;
/* 
 * USACO 2017 December Contest, Gold
 * Problem 2. Barn Painting
 * 
 * 7/24 lesson
 * 
 * non-recursive idea, uses topological sort
 * 
 * 2/10 test cases first try
 * 3/10 had to look at the test data, was constructing the tree wrong
 * 10/10 test cases, changed tree set up (plain adj lists then walked down the tree)
 * 
 * took quite a long time...
 */

public class BP2 {
	static ArrayList<ArrayList<Integer>> adj;
	static int[] indegrees;
	static int N;
	static int MOD = 1000000007;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("barnpainting.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // num barns
		int K = Integer.parseInt(st.nextToken()); // num barns already painted
		
		
		int[] par = new int[N]; // par[i] = parent of barn i
		adj = new ArrayList<ArrayList<Integer>>(); // links par to child
		indegrees = new int[N]; // for topo sort
		int[] color = new int[N]; // color[i] = color of i (0 to 2 if it has one, -1 if not)
		for (int i = 0; i < N; i++) {
			par[i] = color[i] = -1; // init as no parent and no color
			adj.add(new ArrayList<Integer>()); // init with empty children
		}
		
		for (int i = 0; i < N-1; i++) { // connections between barns
			st = new StringTokenizer(br.readLine());
			// connected barns with adjusted indexing
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			adj.get(a).add(b);
			adj.get(b).add(a);
		}
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int barn = Integer.parseInt(st.nextToken()) - 1;
			int col = Integer.parseInt(st.nextToken()) - 1;
			color[barn] = col;
		}
		br.close();
		
		
		// walk down the tree and fill out a par[]
		
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		toVisit.add(0); // arbitrary root
		boolean[] visitedT = new boolean[N];
		visitedT[0] = true;
		
		while (!toVisit.isEmpty()) {
			int i = toVisit.removeFirst();
			for (int j = 0; j < adj.get(i).size(); j++) {
				int child = adj.get(i).get(j);
				
				if (!visitedT[child]) {
					par[child] = i;
					indegrees[child]++;
					toVisit.add(child);
					visitedT[child] = true;
				}
				
			}
		}
		
//		System.out.println("parents: " + Arrays.toString(par));
		
		// TOPO SORT
		int[] sortedBarns = topoSort();
//		System.out.println(Arrays.toString(sortedBarns));
		
		// DP
		// DP[N][3] where DP[node][color] = number of ways to color
		long[][] DP = new long[N][3];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 3; j++) {
				DP[i][j] = -1; // mark as -1 to mark as unvisited
			}
		}
		for (int i = N-1; i >= 0; i--) { // barn index, "lowest" children first
			int b = sortedBarns[i]; // current barn
//			System.out.println("i: " + i + " b: " + b);
			
			if (color[b] != -1) { // if color is preassigned, mark other colors as 0
//				System.out.println("PREASSIGNED COLOR");
				DP[b][(color[b]+1)%3] = 0l;
				DP[b][(color[b]+2)%3] = 0l;
			}
			for (int j = 0; j < 3; j++) { // color
//				System.out.println("  color: " + j);
				if (par[b] != -1 && color[par[b]] == j) { // if cur parent has this color
//					System.out.println("    PAR has color");
					DP[b][j] = 0l;
					continue;
				}
				
				if (DP[b][j] == -1) // init unvisited ones as having just 1 way
					DP[b][j] = 1l;
//				System.out.println("    init: " + DP[b][j]);
				
				for (int k = 0; k < adj.get(b).size(); k++) { // children
					int child = adj.get(b).get(k); // cur child
					if (child == par[b]) continue;
//					System.out.println("    child: " + child);
					
					long val = (DP[child][(j+1)%3] + DP[child][(j+2)%3]);
					val %= MOD;
//					System.out.println("   factor: " + val);
					DP[b][j] *= val; // update DP
					DP[b][j] %= MOD;
//					System.out.println("    val: " + DP[b][j]);
					// + bc the child is one color or another
					// * bc the children have AND relationships with each other
				}
				DP[b][j] %= MOD;
			}
		}
		long tot = 0;
//		long tot = DP[0][0] + DP[0][1] + DP[0][2];
//		System.out.println(tot);
		for (int i = 0; i < N; i++) {
			if (par[i] == -1) {
				tot += (DP[i][0] + DP[i][1] + DP[i][2]) ;
				tot %= MOD;
			}
			if (tot < 0) {
				System.out.println("FAIL " + i);
				break;
			}
		}
		
//		tot %= 1000000007l;
		System.out.println(tot);

//		for (int i = 0; i < N; i++) {
//			System.out.println("i: " + i + " " + Arrays.toString(DP[i]));
//		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));

		pw.println(tot);
		pw.close();
	}
	public static int[] topoSort() {
		int[] sorted = new int[N]; // final array
		int ind = 0;
		
		Queue<Integer> inDegZero = new LinkedList<Integer>(); // all nodes with indegrees 0
		for (int i = 0; i < N; i++) {
			if (indegrees[i] == 0) {
				inDegZero.add(i);
			}
		}
		
		while (!inDegZero.isEmpty()) {
			int cur = inDegZero.poll(); // a node with indeg 0
			sorted[ind] = cur;
			ind++;
			for (int i = 0; i < adj.get(cur).size(); i++) { // loop through edges from cur
				int dest = adj.get(cur).get(i); 
				indegrees[dest]--;
				if (indegrees[dest] == 0) { // if indeg = 0, add to queue
					inDegZero.add(dest);
				}
			}
		}
		
		
		return sorted;
	}

}
