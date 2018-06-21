import java.util.*;
import java.io.*;
import java.awt.Point;

/*
 * 10/10 test cases
 */

public class Overfencing2 {
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int W = Integer.parseInt(st.nextToken());
		int H = Integer.parseInt(st.nextToken());

		char[][] matrix = new char[2*H+1][2*W+1]; // matrix

		Point[] doors = new Point[2];

		doors[0] = new Point(-1, -1);
		doors[1] = new Point(-1, -1);


		// read in the maze
		for (int i = 0; i < 2*H+1; i++) {
			String line = "";
			if (scan.hasNext()) {
				line = scan.nextLine();
			} else {
				break;
			}

			for (int j = 0; j < 2*W+1; j++) {
				if (line.length() <= j) { // if there's no more string to scan
					matrix[i][j] = ' ';
				} else {
					matrix[i][j] = line.charAt(j);
				}

				// track the doors, put them in correct spots
				if ((i == 0) && matrix[i][j] == ' ') {
					if (doors[0].x == -1) { // door 1 is uninitialized
						doors[0].x = i+1;
						doors[0].y = j;
					} else {
						doors[1].x = i+1;
						doors[1].y = j;
					}
				}
				if ((i == 2*H) &&
						matrix[i][j] == ' ') {
					if (doors[0].x == -1) { // door 1 is uninitialized
						doors[0].x = i-1;
						doors[0].y = j;
					} else {
						doors[1].x = i-1;
						doors[1].y = j;
					}
				}
				if ((j == 0) && matrix[i][j] == ' ') {

					if (doors[0].x == -1) { // door 1 is uninitialized
						doors[0].x = i;
						doors[0].y = j+1;
					} else {
						doors[1].x = i;
						doors[1].y = j+1;
					}
				}
				if ((j == 2*W) && matrix[i][j] == ' ') {

					if (doors[0].x == -1) { // door 1 is uninitialized
						doors[0].x = i;
						doors[0].y = j-1;
					} else {
						doors[1].x = i;
						doors[1].y = j-1;
					}
				}

			}
			//System.out.println(Arrays.toString(matrix[i]));
		}

		int tempMax = 0;
		int minMax = Integer.MAX_VALUE;


		// BFS, start from both doors in the SAME SEARCH
		// System.out.println("d: " + d);

		LinkedList<Point> toVisit = new LinkedList<Point>(); // to visit array

		int[][] visited = new int[2*H+1][2*W+1];
		for (int i = 0; i <= 2*H; i+=2) {
			for (int j = 0; j <= 2*W; j+=2) {
				visited[i][j] = -2; // wall or boundary
			}
		}
		for (int i = 1; i <= 2*H; i+=2) {
			for (int j = 1; j <= 2*W; j+=2) {
				visited[i][j] = -1; // square not yet visited
			}
		}

		// process door
		toVisit.add(doors[0]);
		toVisit.add(doors[1]);
		visited[doors[0].x][doors[0].y] = 0;
		visited[doors[1].x][doors[1].y] = 0;

		while (!toVisit.isEmpty()) {
			Point cur = toVisit.removeFirst();
			//System.out.println("x: " + cur.x + " y: " + cur.y + " dist: " + visited[cur.x][cur.y]);

			for (int i = 0; i < 4; i++) {
				int newx = cur.x + dx[i];
				int newy = cur.y + dy[i];
				int nextx = newx + dx[i];
				int nexty = newy + dy[i];

				if (newx >= matrix.length || newx < 0 || newy >= matrix[0].length || newy < 0
						|| nextx >= matrix.length || nextx < 0 || nexty >= matrix[0].length || nexty < 0) {
					continue;
				}

				if (matrix[newx][newy] == '|' ||
						matrix[newx][newy] == '-') {
					continue; // there's a wall
				} else {
					// no wall, increment so that you're at the space, not the edge
					//System.out.println(" nextx: " + nextx + " nexty: " + nexty);
					if (visited[nextx][nexty] < 0) {
						// if not visited, add and mark visited
						toVisit.add(new Point(nextx, nexty));
						visited[nextx][nexty] = visited[cur.x][cur.y] + 1;

						if (visited[nextx][nexty] > tempMax) tempMax = visited[nextx][nexty];

					}
				}

			}
		}
		System.out.println(tempMax + 1); // to account for stepping into the room

	}


}

