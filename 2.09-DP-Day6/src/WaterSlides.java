import java.io.*;
import java.util.*;

public class WaterSlides {
	static int V;
	static int[][] graph;
	static int[][] DP;
	static int K;
	public static void main(String[] args) throws IOException {
		// INPUT
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		V = Integer.parseInt(st.nextToken()); // number of pools
		int E = Integer.parseInt(st.nextToken()); // number of slides
		K = Integer.parseInt(st.nextToken()); // number of times to lose control
		graph = new int[V][V];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			int fun = Integer.parseInt(st.nextToken());
			graph[start][end] = fun;
		}
		for (int i = 0; i < V; i++) {
			System.out.println(Arrays.toString(graph[i]));
		}
		
		// current pool pool, number of times lost control
		// store the current fun value
		DP = new int[V][K+1];
		// DP[0][0] = 0;
		for (int i = 0; i < V; i++) { // mark as unvisited
			Arrays.fill(DP[i], -1);
		}
		for (int i = 0; i <= K; i++) {
			DP[0][K] = Integer.MIN_VALUE; // can never happen
		}
		DP[0][0] = 0;
		func(V-1, K);
		System.out.println(DP[V-1][K]);
		for (int i = 0; i < V; i++) {
			//System.out.println(Arrays.toString(DP[i]));
		}
		
	}
	public static int func(int pool, int loss) {
		// loop through for prev nodes
		System.out.println("pool: " + pool + " loss: " + loss + " START");
		
		if (DP[pool][loss] != -1) {
			// if there's a value
			System.out.println("  value present: " + DP[pool][loss]);
			return DP[pool][loss];
		}
		
		
		// slip
		if (loss > 0) {
			for (int i = 0; i < V; i++) {
				if (graph[i][pool] != 0) { // if there's a connection
					// get the min fun
					int tempFun = func(i, loss-1) + graph[i][pool];
					System.out.println("pool: " + pool + " loss: " + loss + "  tempFun: " + tempFun);
					if (tempFun < DP[pool][loss] && tempFun != -1 || DP[pool][loss] == -1) {
						// if uninitialized
						DP[pool][loss] = tempFun;
					}
					 
				}
			}
			System.out.println("pool: " + pool + " loss: " + loss+" loss: " + DP[pool][loss]);
		}
		// no slip
		for (int i = 0; i < V; i++) {
			if (graph[i][pool] != 0) { // if there's a connection
				// get the max fun
				DP[pool][loss] = Math.max(DP[pool][loss], func(i, loss) + graph[i][pool]); 
			}
		}
		System.out.println("pool: " + pool + " loss: " + loss+" no loss: " + DP[pool][loss]);

		
		
		System.out.println("pool: " + pool + " loss: " + loss+"  val: " + DP[pool][loss]);
		return DP[pool][loss];
	}
}
