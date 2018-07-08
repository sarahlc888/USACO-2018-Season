import java.io.*;
import java.util.*;

/*
 * 10/10 test cases
 * DFS
 * until you run into a node already on the stack
 * O(N^2)
 * 
 * not tested
 */
public class JobHunt {
	static int C;
	static int root;
	static int[][] mat;
	static boolean[] visited;
	static int[] parent;
	static int[] mark; // visited and still in the stack

	static int min = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int D = Integer.parseInt(st.nextToken()); // max money in each city
		int P = Integer.parseInt(st.nextToken()); // number of paths
		C = Integer.parseInt(st.nextToken()); // number of cities
		int F = Integer.parseInt(st.nextToken()); // number of flights
		int S = Integer.parseInt(st.nextToken())-1; // current city

		mark = new int[C];
		mat = new int[C][C]; // everything *-1
		for (int i = 0; i < C; i++) { // no path means max cost
			Arrays.fill(mat[i], Integer.MAX_VALUE);
		}
		
		for (int i = 0; i < P; i++) {
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			//System.out.println("start: " + start + " end: " + end);
			mat[start][end] = -1*D; // path means no travel cost, just the max money
			
		}
		for (int i = 0; i < F; i++) {
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			int cost = Integer.parseInt(st.nextToken());

			mat[start][end] = -1*D + cost; // cost of the flight
		}

		/*for (int i = 0; i < C; i++) {
			System.out.println(Arrays.toString(mat[i]));
		}*/
		if (cycle(S, -1)) { // if there's a cycle, there's no limit
			//System.out.println("here");
			System.out.println(-1);
			return;
		}

		// find the shortest distance (which is
		for (int k = 0; k < C; k++) { // intermediate point
			for (int i = 0; i < C; i++) { // start
				for (int j = 0; j < C; j++) { // end
				
					//System.out.println("k: " + k + " i: " + i + " j: " + j);
					
					// check if the distance from i to j passing through k
					// is shorter than the current
					if (mat[i][k] == Integer.MAX_VALUE || mat[k][j] == Integer.MAX_VALUE) {
						continue; // skip if there's no edge
					}
					
					mat[i][j] = Math.min(mat[i][j], mat[i][k] + mat[k][j]);
					//System.out.println(mat[i][j]);


					if (mat[i][j] < min && i == S) {
						min = mat[i][j];
						//System.out.println("here new min");

					}
				}
			}
		}
		for (int i = 0; i < C; i++) {
			System.out.println(Arrays.toString(mat[i]));
		}
		System.out.println(-1*min + D);
	}
	public static boolean cycle(int u, int p) {
		// u is current node, p is parent
		mark[u] = 1; 
		// 0 means not visited yet
		// 1 means node has been visited but we haven't "gone back from" the node yet
		// 2 means that we've "left" the node (gone back up in the DFS)

		for (int i = 0; i < C; i++) {
			if (i == u) continue;
			if (mat[u][i] != 0) { // loop through the neighbors
				if (mark[i] == 0) { // if unvisited
					cycle(i, u);
				} else if (mark[i] == 1) { 
					//System.out.println("u: " + u + " i: " + u);
					// other node was already visited in the branch, it's a cycle
					return true;
				} /*else if (mark[i] == 2) {
					// visited and left it, should never happen, wouldn't go there

				}*/


			}
		}
		return false;

	}
}
