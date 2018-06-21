import java.io.*;
import java.util.*;
/*
 * can't loop backward, must loop forward... see v3
 */
public class WS2 {
	static int V;
	static Edge[][] graph;
	static int[][] DP;
	static int K;
	public static class Edge {
		ArrayList<Integer> fun;
		public Edge() {
			fun = new ArrayList<Integer>();
		}
	}
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		V = Integer.parseInt(st.nextToken()); // number of pools
		int E = Integer.parseInt(st.nextToken()); // number of slides
		K = Integer.parseInt(st.nextToken()); // number of times to lose control
		graph = new Edge[V][V];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			int fun = Integer.parseInt(st.nextToken());
			if (graph[start][end] == null) graph[start][end] = new Edge();
			graph[start][end].fun.add(fun); // add the fun
		}
		
		// current pool pool, number of times lost control
		// store the current fun value
		DP = new int[V][K+1];
		// DP[0][0] = 0;
		for (int i = 0; i < V; i++) { // mark as unvisited
			Arrays.fill(DP[i], -1);
		}
		//for (int i = 1; i <= K; i++) {
		//	DP[0][i] = Integer.MIN_VALUE; // can never happen
		//}
		DP[0][0] = 0;
		func(V-1, K);
		System.out.println(DP[V-1][K]);
		for (int i = 0; i < V; i++) {
			//System.out.println(Arrays.toString(DP[i]));
		}

	}
	public static int func(int pool, int slips) {
		// loop through for prev nodes
		System.out.println("pool: " + pool + " loss: " + slips + " START");
		
		
		if (pool == 0 && slips != 0) return Integer.MIN_VALUE;
		if (DP[pool][slips] != -1) { // if there's a value already
			System.out.println("  value present: " + DP[pool][slips]);
			return DP[pool][slips];
		}

		// no slip
		System.out.println("pool: " + pool + " loss: " + slips+" NO SLIP");
		int maxFunNS = Integer.MIN_VALUE;
		// loop through slides to current pool, get the max fun
		for (int i = 0; i < V; i++) { 
			if (graph[i][pool] == null) continue; 
			// loop through diff edges
			for (int s = 0; s < graph[i][pool].fun.size(); s++) { 
				int prevFun = func(i, slips);
				if (prevFun == Integer.MIN_VALUE) continue; // skip if past == unreachable
				int tempFun = prevFun + graph[i][pool].fun.get(s);
				System.out.println("pool: " + pool + " loss: " + slips + " i: " + i + " s: " + s + "  tempFunNS: " + tempFun);
				maxFunNS = Math.max(maxFunNS, tempFun); 
			}
		}

		System.out.println("pool: " + pool + " loss: " + slips+" no slip: " + maxFunNS);

		// slip
		System.out.println("pool: " + pool + " loss: " + slips+" SLIP");
		int slip = Integer.MAX_VALUE;
		if (slips > 0) {
			for (int i = 0; i < V; i++) {
				if (graph[i][pool] == null) continue; // if there's a slide
				// loop through the possible slides with different funs
				for (int s = 0; s < graph[i][pool].fun.size(); s++) {
					// get the min fun
					int tempFun = func(i, slips-1) + graph[i][pool].fun.get(s);
					System.out.println("pool: " + pool + " loss: " + slips + " i: " + i + "  tempFunS: " + tempFun);
			
					slip = Math.min(slip, tempFun);
				}


			}
			System.out.println("pool: " + pool + " loss: " + slips+" loss: " + slip);
		}
		if (slip == Integer.MAX_VALUE) { // if there's no case for slip
			slip = Integer.MIN_VALUE; // make it min
		}


		DP[pool][slips] = Math.max(maxFunNS, slip);
		System.out.println("END pool: " + pool + " loss: " + slips+"  val: " + DP[pool][slips]);
		return DP[pool][slips];
	}
}
