import java.io.*;
import java.util.*;
/*
 * USACO 2017 January Contest, Gold
 * Problem 3. Cow Navigation
 * 
 * 7/10 test cases after 1 hour 15 min
 */
public class CowNavigation {
	static boolean[][] grid;
	static int N;
	static int[][][][][][] DP;
	
	public static void main(String args[]) throws IOException {

		// scan everything in
		BufferedReader br = new BufferedReader(new FileReader("cownav.in"));
		N = Integer.parseInt(br.readLine()); // N by N square grid
		
		grid = new boolean[N][N]; // grid, true = haybale, false = empty
		
		// bessie starts in the lower left 0, 0) and goes to upper right (N-1, N-1)
		for (int i = N-1; i >= 0; i--) {
			String line = br.readLine();
			for (int j = 0; j < N; j++) {
				if (line.charAt(j) == 'H') { // mark haybales true
					grid[i][j] = true;
				}
				//System.out.print(grid[i][j] + " ");
			}
			//System.out.println();
		}
		
		br.close();
		
		// DP array
		// args = states of B1 (starts facing north) and B2 (starts facing east)
		// NESW = 0123
		// stores min num of steps needed to reach the state
		DP = new int[N][N][4][N][N][4];
		for (int i1 = 0; i1 < N; i1++) {
			for (int i2 = 0; i2 < N; i2++) {
				for (int i3 = 0; i3 < 4; i3++) {
					for (int i4 = 0; i4 < N; i4++) {
						for (int i5 = 0; i5 < N; i5++) {
							for (int i6 = 0; i6 < 4; i6++) {
								DP[i1][i2][i3][i4][i5][i6] = Integer.MAX_VALUE;
							}
						}
					}
				}
				
			}
		}
		
		// BFS
		LinkedList<State> toVisit = new LinkedList<State>();	
		State startState = new State(0, 0, 0, 0, 0, 1); // b1: 0, 0, N; b2: 0, 0, E
		toVisit.add(startState);
		// DP serves as the hasBeenVisited[]; maxval means not visited, >= 0 means visited
		DPassign(startState, 0); // takes zero steps to get to start pt
		//DP[0][0][0][0][0][1] = 0; 
		
		int minToEnd = Integer.MAX_VALUE; // min num of steps to end
		
		while (!toVisit.isEmpty()) {
			State curstate = toVisit.removeFirst(); // current state
			//System.out.println(curstate + " : " + DPvalFor(curstate));
			
			// update min
			if (curstate.x1 == N-1 && curstate.y1 == N-1 &&
					curstate.x2 == N-1 && curstate.y2 == N-1 &&
						DPvalFor(curstate) < minToEnd) {
				//System.out.println("HERE!!!!");
				minToEnd = DPvalFor(curstate);
			}
			
			
			State[] nexts = curstate.adjs(); // adjacent states
			
			//System.out.println("  " + Arrays.toString(nexts));
			for (int i = 0; i < 3; i++) { // loop through
				State nextstate = nexts[i]; // next state
				//System.out.print("  " + nextstate + " : " + DPvalFor(nextstate));

				
				// assign DP[nextstate] = min(new val, original value)
				DPassign(nextstate, Math.min(DPvalFor(curstate) + 1, DPvalFor(nextstate)));
				
				//System.out.println(" -> " + DPvalFor(nextstate));

				
				if (DPvalFor(curstate) + 1 == DPvalFor(nextstate)) {
					// if it got reassigned, add nextstate to toVisit
					toVisit.addLast(nextstate);
				}
					
			}
		}
		
		//System.out.println(minToEnd);

		

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownav.out")));

		pw.println(minToEnd);
		pw.close();
	}
	/*
	public static void printBoard() {
		// print with [0][0] at bottom left, [N-1][N-1] at top right
		for (int i = N-1; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
	*/
	public static void DPassign(State s, int val) {
		// assigns DP[s] = val
		DP[s.x1][s.y1][s.dir1][s.x2][s.y2][s.dir2] = val;
	}
	public static int DPvalFor(State s) {
		// returns DP[s]
		return DP[s.x1][s.y1][s.dir1][s.x2][s.y2][s.dir2];
	}
	public static class State {
		// info of B1
		int x1;
		int y1;
		int dir1;
		// info of B2
		int x2;
		int y2;
		int dir2;
		// constructor
		public State(int bx1, int by1, int bdir1, int bx2, int by2, int bdir2) {
			x1 = bx1;
			y1 = by1;
			dir1 = bdir1;
			x2 = bx2;
			y2 = by2;
			dir2 = bdir2;
		}
		public String toString() {
			return x1 + " " + y1 + " " + dir1 + " " + x2 + " " + y2 + " " + dir2;
		}
		public State[] adjs() {
			// returns an array of all the states within 1 step of the current
			// remember, both cows must take the same step combos
			// if a cow reaches position [N-1][N-1][i], it should stop moving!!!
			State[] retstates = new State[3]; // 3 possible directions

			// new vals for each state
			int nx1 = this.x1;
			int ny1 = this.y1;
			int ndir1 = this.dir1;
			int nx2 = this.x2;
			int ny2 = this.y2;
			int ndir2 = this.dir2;
			
			// MOVE FORWARD
			// cow 1's prospective new position
			if (!(this.x1 == N-1 && this.y1 == N-1)) {
				// as long as cow 1 hasn't reached the end
				
				if (this.dir1 == 0) { // going north
					ny1++;
				} else if (this.dir1 == 1) { // east
					nx1++;
				} else if (this.dir1 == 2) { // south
					ny1--;
				} else { // west
					nx1--;
				}
			}
			
			if (!(this.x2 == N-1 && this.y2 == N-1)) {
				// as long as cow 2 hasn't reached the end
				
				// cow 2's prospective new position
				if (this.dir2 == 0) { // going north
					ny2++;
				} else if (this.dir2 == 1) { // east
					nx2++;
				} else if (this.dir2 == 2) { // south
					ny2--;
				} else { // west
					nx2--;
				}
			}
			
			
			// if there's something blocking cow1 or cow2 (either out of bounds or haybale)
			boolean blocked1 = nx1 >= N || nx1 < 0 || ny1 >= N || ny1 < 0 || grid[ny1][nx1]; 
			boolean blocked2 = nx2 >= N || nx2 < 0 || ny2 >= N || ny2 < 0 || grid[ny2][nx2]; 

			if (!blocked1 && !blocked2) { // the way is clear, use new values
				retstates[0] = new State(nx1, ny1, ndir1, nx2, ny2, ndir2);
			} else if (blocked1 &&  blocked2) { // both cows are blocked, no change
				retstates[0] = new State(this.x1, this.y1, this.dir1, this.x2, this.y2, this.dir2);
			} else if (blocked1) { // only cow 1 is blocked
				retstates[0] = new State(this.x1, this.y1, this.dir1, nx2, ny2, ndir2);
			} else { // only cow 2 is blocked
				retstates[0] = new State(nx1, ny1, ndir1, this.x2, this.y2, this.dir2);
			}
			
			
			// TURN RIGHT
			retstates[1] = new State(this.x1, this.y1, fitDir(this.dir1 + 1), this.x2, this.y2, fitDir(this.dir2 + 1));
			
			// TURN LEFT
			retstates[2] = new State(this.x1, this.y1, fitDir(this.dir1 - 1), this.x2, this.y2, fitDir(this.dir2 - 1));
			
			
			
			
			
			return retstates;
		}
	}
	public static int fitDir(int a) {
		// fits a into a valid dir 0-3
		a = a%4;
		if (a < 0) a += 4;
		return a;
	}


}
