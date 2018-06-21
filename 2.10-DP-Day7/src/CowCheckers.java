import java.io.*;
import java.util.*;
/*
 * winning positions: the cow who has the next turn will win
 * losing positions: the cow who has the next turn loses, ex: (0, 0) (2, 1)
 * if there's a move to an L position, it's a winning position
 * look for a pattern on the board
 * 
 * every row has 1 L
 * Every col has 1 L
 * every diag has 1 L
 * 
 * you have at most min(M, N) Ls
 * 
 * got the pattern from the teacher
 * 
 * 10/10 test cases
 */
public class CowCheckers {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int M = Integer.parseInt(st.nextToken()); // x coords
		int N = Integer.parseInt(st.nextToken()); // y coords
		int T = Integer.parseInt(scan.nextLine()); // number of games
		int[][] starts = new int[T][2];
		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(scan.nextLine());
			starts[i][0] = Integer.parseInt(st.nextToken());
			starts[i][1] = Integer.parseInt(st.nextToken());
		}

		ArrayList<Point> losing = new ArrayList<Point>(); // arraylist of losing
		/*int[][] grid = new int[M][N]; // 0 is winning position, 1 is losing position
		grid[0][0] = 1; // losing position
		*/
		int k = 1;
		double d = (1 + Math.sqrt(5))/2;
		while (true) {
			int px = (int) (k*d);
			int py = (int) (k*Math.pow(d, 2));
			
			//System.out.println(px + " " + py);
			//System.out.println(py + " " + px);
			if (px >= M || py >= N) break;
			//grid[px][py] = 1;
			losing.add(new Point(px, py));
			if (py >= M || px >= N) break;
			//grid[py][px] = 1; // same on the reflection
			losing.add(new Point(py, px));
			k++;
		}
		losing.add(new Point(0, 0));
		Collections.sort(losing);
		
		for (int i = 0; i < T; i++) {
			int sx = starts[i][0];
			int sy = starts[i][1];
			//if (grid[sx][sy] == 0) {
			if (!(Collections.binarySearch(losing, new Point(sx, sy)) > -1)) {
				// if a winning pos
				System.out.println("Bessie");
			} else {
				System.out.println("Farmer John");
			}
		}
		
		
	}
	public static class Point implements Comparable<Point> {
		int x;
		int y;
		public Point(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Point o) {
			if (x == o.x) return y - o.y;
			return x-o.x;
		}
		
	}
}
