import java.io.*;
import java.util.*;
/*
 * 
 */
public class BarnPainting {
	static int[] layer;
	public static void main(String args[]) throws IOException {
		int MOD = 1000000007;
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		
		BufferedReader br = new BufferedReader(new FileReader("barnpainting.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num barns
		int K = Integer.parseInt(st.nextToken()); // num already painted

		for (int i = 0; i < N; i++) adj.add(new ArrayList<Integer>());

		for (int i = 0; i < N-1; i++) { // scan in paths
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			adj.get(a).add(b);
			adj.get(b).add(a);
		}
		int[] color = new int[N+1];
		for (int i = 0; i < K; i++) { // scan in existing colors
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken());
			color[a] = b;
		}
		br.close();
		
		PriorityQueue<Integer> leaves = new PriorityQueue<Integer>(IntComp);
		boolean[] lvisited = new boolean[N];
		// build a tree
		layer = new int[N]; // how many layers from the root something is
		int[] par = new int[N]; // par[i] = parent of node i
		int root = 0;
		
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		boolean[] visited = new boolean[N];
		toVisit.add(root);
		visited[root] = true;
		par[root] = -1;
		layer[root] = 0;
		while (!toVisit.isEmpty()) {
			int curNode = toVisit.poll();
			if (adj.get(curNode).size() == 1) {
				leaves.add(curNode);
				lvisited[curNode] = true;
			}
			for (int i = 0; i < adj.get(curNode).size(); i++) {
				int next = adj.get(curNode).get(i);
				if (!visited[next]) {
					layer[next] = layer[curNode]+1;
					par[next] = curNode;
					visited[next] = true;
					toVisit.add(next);
				}
			}
		}
//		System.out.println(Arrays.toString(par));
//		System.out.println(leaves);
//		System.out.println("layer: " + Arrays.toString(layer));
		
		// DP[barn][color] = num ways to paint 
		// DP[barn][color] = DP[barn child][color]
		long[][] DP = new long[N][4];
		for (int i = 0; i < N; i++) {
			for (int j = 1; j < 4; j++) {
				DP[i][j] = 1;
			}
		}
		
		while (!leaves.isEmpty()) {
			int node = leaves.poll();
			if (node == -1) continue;
			
//			System.out.println("node: " + node);
			
			if (adj.get(node).size() == 1) {
				// if there are no children and no fixed color, any color works at first
				DP[node][1] = 1;
				DP[node][2] = 1;
				DP[node][3] = 1;
			}
			
			
//			System.out.println("node: " + node);
//			
			// if already colored
			if (color[node] != 0) {
				DP[node][color[node]] = 1;
				for (int i = 1; i <= 3; i++) {
					if (i==color[node]) continue;
					DP[node][i] = 0;
				}
//				System.out.println("  fixed color: " + Arrays.toString(DP[node]));
			}
			
			// if parent has color, node cannot be the same color
			int curPar = par[node];
			if (curPar != -1 && color[par[node]] != 0) { 
				DP[node][color[par[node]]] = 0;
			}
			
			for (int i = 0; i < adj.get(node).size(); i++) {
				// get number of possibilities from children
				
				int child = adj.get(node).get(i);
				if (child == curPar) continue;
//				System.out.println("  child: " + child);
				
				if (color[child] != 0) { // if child has a fixed color
					DP[node][color[child]] = 0; // cannot be the same color
				} 
				DP[node][1] *= (DP[child][2] + DP[child][3])%MOD;
				DP[node][2] *= (DP[child][1] + DP[child][3])%MOD;
				DP[node][3] *= (DP[child][1] + DP[child][2])%MOD;
				DP[node][1] %= MOD;
				DP[node][2] %= MOD;
				DP[node][3] %= MOD;

			}
			
			
			if (curPar != -1 && !lvisited[curPar]) {
				leaves.add(curPar);
				lvisited[curPar] = true;
			}
			
		}
//		for (int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(DP[i]));
//		}

//		System.out.println((DP[root][1] + DP[root][2] + DP[root][3])%MOD);
	
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));

		pw.println((DP[root][1] + DP[root][2] + DP[root][3])%MOD);
		pw.close();
	}
	public static Comparator<Integer> IntComp = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			return layer[y]-layer[x];
		}
	};
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
