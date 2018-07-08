import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class CowHopscotch {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[][] grid = new int[R][C]; 
		
		for (int i = 0; i < R; i++) { // scan in rows
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		

	}
}
