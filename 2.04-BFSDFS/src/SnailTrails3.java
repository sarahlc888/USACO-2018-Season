import java.awt.Point;
import java.io.*;
import java.util.*;

/*
 * fixed INPUT MISTAKE
 * 9/10 test cases
 */
public class SnailTrails3 {
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int N;
	static boolean[][] visited;
	static boolean[][][] visitedDir;
	static int max = 0;
	static int[][] grid;
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		N = Integer.parseInt(st.nextToken()); // dimensions of the square
		int B = Integer.parseInt(st.nextToken()); // number of barriers
		
		grid = new int[N][N];
		visited = new boolean[N][N];
		visitedDir = new boolean[N][N][4];
		//System.out.println();
		// mark the obstacles
		for (int i = 0; i < B; i++) {
			String s = scan.nextLine();
			char letter = s.charAt(0);
			int col = Integer.valueOf(letter) - 65;
			int row = Integer.parseInt(s.substring(1)) - 1;
			//System.out.println(row + " " + col);
			//System.out.println(row);
			grid[row][col] = -2;
		}
		scan.close();
		
		// starting states, also mark the paths
		State start = new State(0, 0);
		
		
		// DFS
		DFS(start, 1, 1); // start east
		DFS(start, 2, 1); // start south
		
		
		System.out.println(max);
	}
	
	public static void DFS(State s, int dir, int squares) {
		// coords, direction (N == 0), sqrs visited so far
		//System.out.println(s.x + " " + s.y + " d: " + dir);
		visited[s.x][s.y] = true;

		// next coordinates (if proceeding forward)
		int newx = s.x+dx[dir];
		int newy = s.y+dy[dir];
		
		//System.out.println("  " + newx + " " + newy);
		//System.out.println(grid[newx][newy]);
		
		// turn: next square is a wall or an obstacle
		if (newx >= N || newy >= N || newx < 0 || newy < 0 || grid[newx][newy] == -2) {
			//System.out.println("  turn");
			for (int d = dir-1; d <= dir+1; d += 2) {
				// d = new direction (only 90 degree turns allowed)
				// same square, same number of squares covered
				//System.out.println("  d: " + (d+4)%4);
				if (!visitedDir[s.x][s.y][(d+4)%4]) { // if the turn hasn't been done yet
					//System.out.println("    go");
					visitedDir[s.x][s.y][(d+4)%4] = true; // mark the turn as true
					DFS(s, (d+4)%4, squares); // do it
					visitedDir[s.x][s.y][(d+4)%4] = false; // unmark it once the path is done
				} else {
					//System.out.println("    already done");
				}
			}
		} else if (visited[newx][newy]) { 
			// next square has already been visited
			//System.out.println("  end");
			// no obstacle, so don't turn, but the next square is already on the path
			// squares final dist travelled
			if (squares > max) {
				//System.out.println("new max " + squares);
				max = squares; // update max
			}
		} else {
			// don't turn, keep going forward
			//System.out.println("  cont");
			visited[newx][newy] = true; // mark the path as visited
			State next = new State(newx, newy); // next state
			DFS(next, dir, squares+1);
			visited[newx][newy] = false; // backtrack and erase the path
		}
		
	}
	
	public static int sum(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
	public static class State {
		int x;
		int y;
		
		public State(int xin, int yin) {
			x = xin;
			y = yin;
		}
		
	}
}
