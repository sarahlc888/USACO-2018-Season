import java.io.*;
import java.util.*;
// 10/10 i think
public class WS3 {
	static int V;
	static Edge[] graph;
	static long[][] DP;
	static int K;
	public static class Edge {
		ArrayList<Integer> dest;
		ArrayList<Integer> fun;
		public Edge() {
			dest = new ArrayList<Integer>();
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

		graph = new Edge[V];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			int fun = Integer.parseInt(st.nextToken());
			if (graph[start] == null) {
				graph[start] = new Edge();
			}
			graph[start].fun.add(fun); // add the fun
			graph[start].dest.add(end); // add the end
		}
		/*for (int i = 0; i < V; i++) {
			System.out.println("i: " + i);
			if (graph[i] == null) {
				System.out.println("null here");
				continue;
			}
			System.out.println(graph[i].dest);
			System.out.println(graph[i].fun);
			
			
		}*/

		// current pool pool, number of times left to lose control
		// store the amount of fun after the pool
		DP = new long[V][K+1];
		// DP[0][0] = 0;
		for (int i = 0; i < V; i++) { // mark as unvisited
			Arrays.fill(DP[i], -1);
		}

		func(0, K);

		System.out.println(DP[0][K]);
		/*for (int i = 0; i < V; i++) {
			System.out.println(Arrays.toString(DP[i]));
		}*/

	}
	public static long func(int pool, int slips) {

		// memoization
		if (DP[pool][slips] != -1) { // if there's a value already
			return DP[pool][slips];
		}

		//System.out.println("pool: " + pool + " loss: " + slips + " START");

		long val = 0;

		if (graph[pool] == null) return 0l;
		
		// NO SLIP

		// loop through the next nodes
		
			for (int i = 0; i < graph[pool].dest.size(); i++) { 
				int end = graph[pool].dest.get(i); // end index of the slide
				int funCur = graph[pool].fun.get(i);  // fun


				// fun = fun of the slide from pool to i + the fun after i
				// no change in slips

				long fun = funCur + func(end, slips); 
				if (fun > val) val = fun; // update

				//System.out.println("pool: " + pool + " loss: " + slips + " i: " + i + " fun: " + fun);

			}
		
		

		// SLIP
		// loop through slides from current pool
		if (slips > 0) {
			for (int i = 0; i < graph[pool].dest.size(); i++) { 
				int end = graph[pool].dest.get(i); // end index of the slide
				int funCur = graph[pool].fun.get(i);  // fun


				long fun = funCur + func(end, slips-1);
				if (fun < val) val = fun;
			}
		}




		return DP[pool][slips] = val;
	}
}
/*
import java.io.*;
import java.util.*;

public class WS3 {
	static int V;
	static Edge[][] graph;
	static long[][] DP;
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

		// current pool pool, number of times left to lose control
		// store the amount of fun after the pool
		DP = new long[V][K+1];
		// DP[0][0] = 0;
		for (int i = 0; i < V; i++) { // mark as unvisited
			Arrays.fill(DP[i], -1);
		}

		func(0, K);

		System.out.println(DP[0][K]);
		

	}
	public static long func(int pool, int slips) {
		
		// memoization
		if (DP[pool][slips] != -1) { // if there's a value already
			return DP[pool][slips];
		}
		
		//System.out.println("pool: " + pool + " loss: " + slips + " START");
		
		long val = 0;
		
		// NO SLIP

		// loop through the next nodes
		for (int i = 0; i < V; i++) { 
			if (graph[pool][i] == null) continue; 
			// if there's a slide from pool to i, loop through that edge
			for (int j = 0; j < graph[pool][i].fun.size(); j++) {
				// fun = fun of the slide from pool to i + the fun after i
				// no change in slips

				long fun = graph[pool][i].fun.get(j) + func(i, slips); 
				if (fun > val) val = fun; // update
				
				//System.out.println("pool: " + pool + " loss: " + slips + " i: " + i + " fun: " + fun);
			}
		}

		// SLIP
		// loop through slides from current pool
		if (slips > 0) {
			for (int i = 0; i < V; i++) { 
				if (graph[pool][i] == null) continue; 
				// if there's a slide from pool to i, loop through that edge
				for (int s = 0; s < graph[pool][i].fun.size(); s++) { 
					// fun is the slide + what comes after
					long fun = graph[pool][i].fun.get(s) + func(i, slips-1);
					if (fun < val) val = fun;
				}
			}

		}
		

		return DP[pool][slips] = val;
	}
}
*/
