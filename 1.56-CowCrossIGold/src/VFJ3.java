import java.io.*;
import java.util.*;
/*
 * USACO 2017 February Contest, Gold
 * Problem 1. Why Did the Cow Cross the Road
 * 
 * 11/11 test cases
 * uses every step instead of every three steps
 * had bugs involving x and y, use r and c in the future
 * 
 */
public class VFJ3 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("visitfj.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken()); // N by N field
		int T = Integer.parseInt(st.nextToken()); // amt of time it takes to cross
		
		int[][] grid = new int[N][N]; // holds amount of time to eat grass in field [i][j]
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
				////System.out.print(grid[i][j] + " ");
			}
			////System.out.println();
		}
		br.close();

		// DP array, states = positions (i, j) and numsteps%3, stores time
		int[][][] DP = new int[N][N][3]; // number of steps
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// -1 means unreachable / has not been visited
				DP[i][j][0] = -1; 
				DP[i][j][1] = -1; 
				DP[i][j][2] = -1; 
			}
		}
		// start at (0, 0, 0) and BFS into every field
		State start = new State(0, 0, 0);
		
		LinkedList<State> toVisit = new LinkedList<State>();	
		toVisit.add(start);
		// start at time 0
		DP[0][0][0] = 0; 
		
		start.adjs();
		
		int min = Integer.MAX_VALUE;
		
		while (!toVisit.isEmpty()) {
			State cur = toVisit.removeFirst(); // current point
			int curTime = DP[cur.row][cur.col][cur.numsteps]; 
			//System.out.println(cur + " : " + curTime + "  step: " + cur.numsteps);

			if (cur.col == N-1 && cur.row == N-1) { // at the destination
				if (curTime < min) {
					min = curTime;
					//System.out.println("!!!!new min!!!! " + min);
				}
			}
			
			State[] nexts = cur.adjs(); // next possible grid points
			//if (cur.numsteps == 2) System.out.println("third step");
			for (int i = 0; i < nexts.length; i++) {	// loop through & process
				int nx = nexts[i].col;
				int ny = nexts[i].row;
				int nsteps = nexts[i].numsteps;
				
				// if the point is out of bounds, skip it
				if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
				
				int ogTime = DP[ny][nx][nsteps]; // old time to reach nexts[i]
				int newTime;
				if (nsteps == 0) {
					//System.out.println("  eat " + grid[ny][nx]);
					newTime = curTime + T + grid[ny][nx]; // new time to reach nexts[i]
				} else {
					newTime = curTime + T;
				}

				if (ogTime != -1 && newTime < ogTime || ogTime == -1) {
					//System.out.println("add");
					DP[ny][nx][nsteps] = newTime;
					toVisit.addLast(new State(nx, ny, nsteps));
				}
				//System.out.println("  " + nexts[i] + " : " + ogTime + " -> " + newTime + " , " + DP[ny][nx][nsteps] + "  step: " + nsteps);
			}	
		}
		
		//System.out.println("ans: " + min);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));

		pw.println(min);
		pw.close();
	}
	public static class State {
		int col;
		int row;
		int numsteps;
		
		public State(int xin, int yin, int steps) {
			col = xin;
			row = yin;
			numsteps = steps;
		}
		public State[] adjs() {
			// returns 4 possible coords 1 step away from the current one
			
			State[] retAdj = new State[4]; // 12 possible points
			retAdj[0] = new State(this.col+1, this.row, (this.numsteps+1)%3);
			retAdj[1] = new State(this.col-1, this.row, (this.numsteps+1)%3);
			retAdj[2] = new State(this.col, this.row+1, (this.numsteps+1)%3);
			retAdj[3] = new State(this.col, this.row-1, (this.numsteps+1)%3);
			////System.out.println(Arrays.toString(retAdj));
			return retAdj;
		}
		public String toString() {
			return row + " " + col + " " + numsteps;
		}
	}

}
