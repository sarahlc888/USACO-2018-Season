import java.io.*;
import java.util.*;
/*
 * USACO 2017 February Contest, Gold
 * Problem 1. Why Did the Cow Cross the Road
 * 
 * 2/10 test cases
 * try using long if you get weird answers
 */
public class VFJ2 {
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

		// DP array, states = field positions (i, j), stores the time
		int[][] DP = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				DP[i][j] = -1; // -1 means unreachable / has not been visited
			}
		}
		// start at (0, 0) and BFS into every third field
		Point start = new Point(0, 0);
		
		LinkedList<Point> toVisit = new LinkedList<Point>();	
		toVisit.add(start);
		DP[0][0] = 0; // start at time 0
		start.adjs();
		
		int min = Integer.MAX_VALUE;
		
		while (!toVisit.isEmpty()) {
			Point cur = toVisit.removeFirst(); // current point
			int curTime = DP[cur.x][cur.y]; 
			//System.out.println(cur + " : " + curTime);

			if ((N-1 - cur.x) + (N-1 - cur.y) <= 2) {
				// if at the end point or near the end point, add in the final steps and update min
				int finalTime = curTime + Math.abs(N-1 - cur.x)*T + Math.abs(N-1 - cur.y)*T;
				//System.out.println("end square " + finalTime);

				if (finalTime < min) {
					min = finalTime;
					//System.out.println("!!!!new min!!!! " + min);
				}
				
			}
			Point[] nexts = cur.adjs(); // next possible grid points
			for (int i = 0; i < nexts.length; i++) {	// loop through & process
				int nx = nexts[i].x;
				int ny = nexts[i].y;
				
				// if the point is out of bounds, skip it
				if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
				
				
				int ogTime = DP[nx][ny]; // old time to reach nexts[i]
				int newTime = curTime + 3*T + grid[nx][ny]; // new time to reach nexts[i]
				
				if (ogTime != -1 && newTime < ogTime || ogTime == -1) {
						DP[nx][ny] = newTime;
						toVisit.addLast(new Point(nx, ny));
				}
			//System.out.println("  " + nexts[i] + " : " + ogTime + " -> " + newTime + " , " + DP[ny][nx]);
			}
			
				
				
		}
		
		System.out.println(min);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));

		pw.println(min);
		pw.close();
	}
	public static class Point {
		int x;
		int y;
		
		public Point(int xin, int yin) {
			x = xin;
			y = yin;
		}
		public Point[] adjs() {
			// returns 12 possible coords 3 steps away from the current one
			
			// number of rows / cols away from current
			int rDiff;
			int cDiff;
			
			Point[] retAdj = new Point[12]; // 12 possible points
			int ind = 0; // running ind to fill retAdj
			
			for (rDiff = 0; rDiff <= 3; rDiff++) {
				// |rDiff + cDiff| MUST = 3 at all times
				// the reachable squares make a diamond around the cur
				cDiff = 3-rDiff;
				
				retAdj[ind] = new Point(this.x + cDiff, this.y + rDiff);
				ind++;
				if (cDiff != 0) { // if -cDiff would be different, do it
					retAdj[ind] = new Point(this.x - cDiff, this.y + rDiff);
					ind++;
				}
				
				if (rDiff != 0) { // if -rDiff would be different, do it
					retAdj[ind] = new Point(this.x + cDiff, this.y - rDiff);
					ind++;
					if (cDiff != 0) {
						retAdj[ind] = new Point(this.x - cDiff, this.y - rDiff);
						ind++;
					}
				}
			}
			
			////System.out.println(Arrays.toString(retAdj));
			return retAdj;
		}
		public String toString() {
			return x + " " + y;
		}
	}

}
