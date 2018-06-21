import java.awt.Point;
import java.io.*;
import java.util.*;

/*
 * 
 * finish debugging this later
 * 4/10 test cases
 */
public class SnailTrails {
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int N;
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		N = Integer.parseInt(st.nextToken()); // dimensions of the square
		int B = Integer.parseInt(st.nextToken()); // number of barriers
		
		int[][] grid = new int[N][N];
		
		// mark the obstacles
		for (int i = 0; i < B; i++) {
			String s = scan.nextLine();
			char letter = s.charAt(0);
			int col = Integer.valueOf(letter) - 65;
			int row = Integer.parseInt((String.valueOf(s.charAt(1)))) - 1;
			//System.out.println(row);
			grid[row][col] = -2;
		}
		scan.close();
		
		// starting states, also mark the paths
		State start1 = new State(0, 0, 1);
		State start2 = new State(0, 0, 2);
		start1.path[0][0] = 1;
		start2.path[0][0] = 1;
		
		// DFS
		Stack<State> toVisit = new Stack<State>(); // to visit stack
		toVisit.add(start1);
		toVisit.add(start2);
		
		int[][][] visited = new int[N][N][4]; // visited array
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < 4; k++) {
					visited[i][j][k] = -1;
				}
			}
		}
		
		// no moves yet, but mark visited (reach one square)
		visited[0][0][1] = 1; 
		visited[0][0][2] = 1;
		
		int max = 0;
		
		while (!toVisit.isEmpty()) {
			State cur = toVisit.pop(); // current state

			System.out.println("(" + cur.x + ", " + cur.y + ") dir: " + cur.facing);
			// next coordinates
			int newx = cur.x+dx[cur.facing];
			int newy = cur.y+dy[cur.facing];

			// boundaries or obstacle: turn
			if (newx >= N || newy >= N || newx < 0 || newy < 0 || grid[newx][newy] == -2) {
				System.out.println("turn");
				
				boolean turnFail1 = false;
				boolean turnFail2 = false;
				
				for (int j = -1; j <= 1; j+= 2) {
					// add to visited, mark visited
					//if (visited[cur.x][cur.y][(cur.facing+j+4)%4] < 0) {
					
					// if the turn would send you out of bounds
					if (cur.x+dx[(cur.facing+j+4)%4] < 0 || cur.y+dy[(cur.facing+j+4)%4] < 0 ||
							cur.x+dx[(cur.facing+j+4)%4] >= N || cur.y+dy[(cur.facing+j+4)%4] >= N) {
						
						System.out.println("fail");
						// mark turnFails
						if (!turnFail1) turnFail1 = true;
						else turnFail2 = true;
						
						continue;
					}
					// if the turn would send you into an obstacle
					if (grid[cur.x+dx[(cur.facing+j+4)%4]][cur.y+dy[(cur.facing+j+4)%4]] == -2) {
						
						System.out.println("fail");
						// mark turnFails
						if (!turnFail1) turnFail1 = true;
						else turnFail2 = true;
						
						continue;
					}
					
					
					if (cur.path[cur.x+dx[(cur.facing+j+4)%4]][cur.y+dy[(cur.facing+j+4)%4]] == 0) {
						// if it's on the path already
						//System.out.println((cur.facing+j+4)%4);
						// same number of moves
						visited[cur.x][cur.y][(cur.facing+j+4)%4] = visited[cur.x][cur.y][cur.facing]; 
						
						// next state
						State nextState = new State(cur.x, cur.y, (cur.facing+j+4)%4); 
						
						nextState.path = copy(cur.path); // copy over the path
						
						toVisit.add(nextState);
					} else {
						//System.out.println("already visited");
					}
				}
				System.out.println(turnFail1 + " " + turnFail2);
				if (turnFail1 && turnFail2) {
					// if both turns fail and you have no more moves
					System.out.println("end");
					// no obstacle, so don't turn, but the next square is already on the path
					int finalDist = visited[cur.x][cur.y][cur.facing]; // final dist travelled
					if (finalDist > max) {
						System.out.println("new max " + finalDist);
						max = finalDist; // update max
					}
				}
				
			} else if (cur.path[newx][newy] == 1) {
				System.out.println("end");
				// no obstacle, so don't turn, but the next square is already on the path
				int finalDist = visited[cur.x][cur.y][cur.facing]; // final dist travelled
				if (finalDist > max) {
					System.out.println("new max " + finalDist);
					max = finalDist; // update max
				}
				
			} else {
				System.out.println("cont");
				// keep going straight
				
				//if (visited[newx][newy][cur.facing] < 0) { 
				if (cur.path[newx][newy] == 0) { // if not on the path yet
					
					visited[newx][newy][cur.facing] = visited[cur.x][cur.y][cur.facing] + 1; // mark visited
					State nextState = new State(newx, newy, cur.facing); // next state
					nextState.path = copy(cur.path); // add the path
					nextState.path[newx][newy] = 1; // mark the path
					
					toVisit.add(nextState);
				}
			}
		}
		System.out.println(max);
	}
	public static int sum(int[] arr) {
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}
	public static int[][] copy (int[][] orig) { // clones the grid
		int[][] c = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				c[i][j] = orig[i][j];
			}
		}
		return c;
	}
	public static class State {
		int x;
		int y;
		int facing; // 0 is N
		int[][] path = new int[N][N]; 
		
		public State(int xin, int yin, int fin) {
			x = xin;
			y = yin;
			facing = fin;
		}
	}
}
