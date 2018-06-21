import java.io.*;
import java.util.*;

/*
 * Day 1
 * all test cases correct
 * 
 */
public class MeteorShower {
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	
	
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(System.in);
		int M = Integer.parseInt(scan.nextLine()); // number of meteors
		
		int[][] field = new int[310][310];
		for (int i = 0; i < 310; i++) {
			for (int j = 0; j < 310; j++) {
				field[i][j] = Integer.MAX_VALUE; // no strike ever
			}
		}

		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			int time = Integer.parseInt(st.nextToken());
			
			// mark field and 4 surrounding fields
			field[x][y] = Math.min(field[x][y],time);
			
			for (int j = 0; j < 4; j++) {
				if (x+dx[j] >= field.length || y+dy[j] >= field[0].length ||
						x+dx[j] < 0 || y+dy[j] < 0) {
					continue;
				}
				field[x+dx[j]][y+dy[j]] = Math.min(time, field[x+dx[j]][y+dy[j]]); 
			}
			
		}
		
		scan.close();
		
		// BFS
		// starting at 0, 0
		LinkedList<Point> toVisit = new LinkedList<Point>();	
		
		int[][] dist = new int[310][310]; // distance to [i][j] from [0][0]
		for (int i = 0; i < 310; i++ ) {
			for (int j = 0; j < 310; j++) {
				dist[i][j] = -1;
			}
		}
		
		// mark visited and distance to origin
		dist[0][0] = 0;
		
		toVisit.add(new Point(0, 0));
		
		boolean breakWhile = false;
		int returnVal = -1;
		
		while (!toVisit.isEmpty()) {
			Point cur = toVisit.removeFirst(); // current point
			//System.out.println(cur.x + " " + cur.y);
			
			for (int i = 0; i < 4; i++) { // loop through adjacent cows
				// continue if our of bounds
				if (cur.x+dx[i] >= field.length || cur.y+dy[i] >= field[0].length ||
						cur.x+dx[i] < 0 || cur.y+dy[i] < 0) {
					continue;
				}
				//System.out.println(field[cur.x+dx[i]][cur.y+dy[i]]);
				if (dist[cur.x+dx[i]][cur.y+dy[i]] < 0 && 
							field[cur.x+dx[i]][cur.y+dy[i]] > dist[cur.x][cur.y] + 1) {
					// if it's not visited and won't be hit with a meteor
					// add i to toVisit, mark visited, increment count
					
					//System.out.println("HERE!");
					
					toVisit.add(new Point(cur.x+dx[i], cur.y+dy[i]));
					dist[cur.x+dx[i]][cur.y+dy[i]] = dist[cur.x][cur.y] + 1;
					
					if (field[cur.x+dx[i]][cur.y+dy[i]] == Integer.MAX_VALUE) {
						
						//System.out.println("HERE!!! " + (cur.x+dx[i]) + " " + (cur.y+dy[i]));
						// if the spot is forever safe, have the return val
						returnVal = dist[cur.x+dx[i]][cur.y+dy[i]];
						breakWhile = true;
						break;
					}
				}
			}
			if (breakWhile) break;
		}
		
		System.out.println(returnVal);
		
		
	}
	public static class Point {
		int x;
		int y;
		public Point(int xin, int yin) {
			x = xin;
			y = yin;
		}
	}
}
