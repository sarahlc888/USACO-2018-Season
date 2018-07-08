import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
// 10/10
public class VacationPlanning {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of farms
		int M = Integer.parseInt(st.nextToken()); // number of 1-way flights
		int K = Integer.parseInt(st.nextToken()); // number of hubs
		int Q = Integer.parseInt(st.nextToken()); // number of queries
		
		int[][] graph = new int[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(graph[i], Integer.MAX_VALUE); // no edge
		}
		for (int i = 0; i < M; i++) { // scan in 1-way flights
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken())-1; 
			int dest = Integer.parseInt(st.nextToken())-1; 
			int cost = Integer.parseInt(st.nextToken()); 
			graph[start][dest] = cost; // scan in edgess
		}
		
		for (int k = 0; k < N; k++) { // intermediate point (hub only)
			for (int i = 0; i < N; i++) { // start
				for (int j = 0; j < N; j++) { // end
					// check if the distance from i to j passing through k
					// is shorter than the current
					
					// make sure edge exists
					if (graph[i][k] == Integer.MAX_VALUE || graph[k][j] == Integer.MAX_VALUE) continue;
					
					if (i == j) {
						graph[i][j] = 0;
						continue;
					}
					
					graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
				}
			}
		}
		
		for (int i = 0; i < N; i++) { // make sure diagonals are 0
			graph[i][i] = 0;
		}
		
		int numWorking = 0;
		long sum = 0;
		for (int i = 0; i < Q; i++) { // process queries
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken())-1; 
			int dest = Integer.parseInt(st.nextToken())-1; 
			
			//System.out.println("start: " + start + " dest: " + dest);
			
			int minDist = Integer.MAX_VALUE;
			
			for (int j = 0; j < K; j++) { // loop through all the hubs
				if (graph[start][j] != Integer.MAX_VALUE &&
						graph[j][dest] != Integer.MAX_VALUE) { // edge present
					int d = graph[start][j]+graph[j][dest];
					minDist = Math.min(minDist, d);
				}
			}
			//System.out.println(minDist);
			if (minDist != Integer.MAX_VALUE) { // edge present
				numWorking++;
				sum += minDist;
			}
		}
		br.close();
		System.out.println(numWorking);
		System.out.println(sum);
		
		/*
		for (int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(graph[i]));
		}
		*/
		
	}
}
