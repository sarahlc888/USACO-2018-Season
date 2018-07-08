import java.io.*;
import java.util.*;
/*
 * USACO 2015 December Contest, Gold
 * Problem 3. Bessie's Dream
 * 
 * DP
 * BFS
 * Grid
 * 
 * stuck at 12/16 cases
 * 
 * maybe purple tiles only make you non-orange once you step off
 */
public class BessiesDream {
	public static int[] dr = {0, 0, -1, 1};
	public static int[] dc = {1, -1, 0, 0};
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader br = new BufferedReader(new FileReader("testData/12.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num rows
		int M = Integer.parseInt(st.nextToken()); // num cols
		
		int[][] maze = new int[N][M]; // maze

		// scan maze in
		for (int i = 0; i < N; i++) { 
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				maze[i][j] = Integer.parseInt(st.nextToken());
				//System.out.print(maze[i][j] + " ");
			}
			//System.out.println();
		}
		
		br.close();
		
		// DP[row][col][1 or 0 for oranges or not] = number of steps to access the state
		int[][][] DP = new int[N][M][2];
		// fill everything with -1 (inaccessible)
		for (int i = 0; i < N; i++) { 
			for (int j = 0; j < M; j++) {
				DP[i][j][0] = -1;
				DP[i][j][1] = -1;
			}
		}
		// basecase: start at the top left, no orange smell
		DP[0][0][0] = 0;

		// BFS
		State start = new State(0, 0, 0);
		
		LinkedList<State> toVisit = new LinkedList<State>();	
		toVisit.add(start);

		boolean[][][] hasBeenVisited = new boolean[N][M][2];
		hasBeenVisited[0][0][0] = true; // mark start as true

		while (!toVisit.isEmpty()) {
			State cur = toVisit.removeFirst(); // current cow
			//int tileType = maze[cur.row][cur.col]; // type of tile in the maze
			// if tileType == ...
				// 0 == red, something is wrong
				// 1 == pink, normal
				// 2 == orange, makes orange smell
				// 3 == blue, can only pass if orange smell
				// 4 == purple, slide, remove orange smell
			
			// go in all 4 directions using dr and dc arrays for tile changes
			for (int i = 0; i < 4; i++) {
				
				// figure out the next state
				State next; // shell for next state  
				
				int nextRow = cur.row+dr[i];
				int nextCol = cur.col+dc[i];
				
				if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M) continue;
				
				int nextTile = maze[nextRow][nextCol];
				
				int moves = 1; // normally, move just 1 square
				
				if (nextTile == 0) { // red tile, cannot pass
					continue;
				} else if (nextTile == 1) {
					// pink tile, normal move, no change in orange
					next = new State(nextRow, nextCol, cur.orange);
				} else if (nextTile == 2) {
					// orange tile, normal move, do orange smell
					next = new State(nextRow, nextCol, 1);
				} else if (nextTile == 3) {
					if (cur.orange == 1) { // if there's orange smell, move onto it
						next = new State(nextRow, nextCol, cur.orange);
					} else { // cannot pass
						continue;
					}
				} else { // purple tile, sliding!
					// find the first non-purple tile OR the first tile in front of an impassible tile
					
					next = new State(-1, -1, -1);
					moves++;
					
					// next next coordinates
					int nextRow2 = nextRow + dr[i];
					int nextCol2 = nextCol + dc[i];
					
					while (nextRow2 >= 0 && nextRow2 < N && nextCol2 >= 0 && nextCol2 < M 
							&& maze[nextRow2][nextCol2] == 4) { 
						// while it's in range and there's still purple tiles
						// increment position and moves
						nextRow2 += dr[i];
						nextCol2 += dc[i];
						moves++;
					}
					// what's the ending tile
					
					if (nextRow2 < 0 || nextRow2 >= N || nextCol2 < 0 || nextCol2 >= M 
							|| maze[nextRow2][nextCol2] == 0 || maze[nextRow2][nextCol2] == 3) { 
						// if the next tile is red, blue, or out of bounds, stop
						// can't pass blue because Bessie won't have an orange smell
						next.row = nextRow2 - dr[i];
						next.col = nextCol2 - dc[i];
						next.orange = 0; // erase orange smell
						moves--; // decrement bc you're not making the last move
					} else if (maze[nextRow2][nextCol2] == 1) {
						// normal tile, step on
						next.row = nextRow2;
						next.col = nextCol2;
						next.orange = 0; // erase orange smell
					} else if (maze[nextRow2][nextCol2] == 2) {
						// orange tile, step on, get orange smell
						next.row = nextRow2;
						next.col = nextCol2;
						next.orange = 1; // get orange smell
					}
				}
				
				// process the next state
				if (!hasBeenVisited[next.row][next.col][next.orange]) {
					// if not visited, add to toVisit, mark visited
					toVisit.add(next);
					hasBeenVisited[next.row][next.col][next.orange] = true;
					
					if (DP[next.row][next.col][next.orange] == -1) {
						DP[next.row][next.col][next.orange] = DP[cur.row][cur.col][cur.orange] + moves;
					} else {
						DP[next.row][next.col][next.orange] = Math.min(
								DP[next.row][next.col][next.orange],
								DP[cur.row][cur.col][cur.orange] + moves);
					}
				}
			}
		}
		System.out.println(Math.max(DP[N-1][M-1][0], DP[N-1][M-1][1]));
		
		// OUTPUT
		
	}
	public static class State { // state storing DP[row][col][orange]
		int row;
		int col;
		int orange;
		
		public State(int rin, int cin, int oin) {
			row = rin;
			col = cin;
			orange = oin;
		}
	}


}
