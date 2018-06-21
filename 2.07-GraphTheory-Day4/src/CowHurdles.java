import java.io.*;
import java.util.*;
/*
 * floyd warshall
 * 10/10 test cases
 */
public class CowHurdles {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int N = Integer.parseInt(st.nextToken()); // number of nodes
		int M = Integer.parseInt(st.nextToken()); // number of edges
		int T = Integer.parseInt(st.nextToken()); // number of queries
		
		// matrix
		int[][] dist = new int[N][N]; // n by n matrix
		
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		
		for (int i = 0; i < M; i++) {
			// scan in all the edges
			st = new StringTokenizer(scan.nextLine());
			// adjust indexing
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			int weight = Integer.parseInt(st.nextToken());
			// add to matrix
			dist[start][end] = weight;
		}
		/*for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(dist[i]));
		}*/
		// calculate all pairs shortest distance
		for (int k = 0; k < N; k++) { // intermediate point
			for (int i = 0; i < N; i++) { // start
				for (int j = 0; j < N; j++) { // end
					// check if the max hurdle from i to j passing through k
					// is shorter than the current
					dist[i][j] = Math.min(dist[i][j], Math.max(dist[i][k], dist[k][j]));
				}
			}
		}
		
		for (int i = 0; i < T; i++) {
			// scan in all the queries
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			if (dist[start][end] == Integer.MAX_VALUE) {
				System.out.println(-1);
			} else {
				System.out.println(dist[start][end]);
			}
		}
		scan.close();
	}
}
