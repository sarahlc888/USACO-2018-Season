/*
 * floyd warshall
 * O(v^3)
 * 
 * should be fully correct
 * 
 * dist[]:
 * 0s on diagonals
 * infinities for no connection
 * edgeweights for edges
 */
public class FloydWarshall {
	public static void main(String[] args) {
		int N = 10;
		int[][] dist = new int[N+1][N+1];
		for (int k = 1; k <= N; k++) { // intermediate point
			for (int i = 1; i <= N; i++) { // start
				for (int j = 1; j <= N; j++) { // end
					// check if the distance from i to j passing through k
					// is shorter than the current
					dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
				}
			}
		}
	}
}
