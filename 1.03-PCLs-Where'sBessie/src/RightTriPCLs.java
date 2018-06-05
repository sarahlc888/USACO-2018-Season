/*
 * Find the maximum PCL (potential cow location) in a rectangular grid. A PCL
 * is defined as a sub-right-triangle, where only two letters appear. One letter 
 * forms a connected component, while the other letter forms more than one
 * connected component. A maximum PCL is one that is not contained in another
 * PCL.
 * 
 * Teacher's solution here
 * modifications: 45-45-90 triangles as PCLs, square grids
 * 
 * USACO 2017 US Open Contest, Silver 
 * Problem 3. Where's Bessie?
 * 
 * In theory, this code should work - not yet tested
*/

import java.util.*;
import java.io.*;

public class RightTriPCLs {

	static int N;
	static char[][] grid;
	
	static int[] dx = { 0,  0, 1, -1};
	static int[] dy = { 1, -1, 0,  0};
	
	public static void main(String[] args) throws IOException {

		// read the input
		Scanner in = new Scanner(new File("where.in"));
		N = in.nextInt();
		in.nextLine();
		
		grid = new char[N][N];
		for(int i=0; i<N; i++) {
			String line = in.nextLine().toUpperCase();
			for(int j=0; j<N; j++)
				grid[i][j] = line.charAt(j);
		}
		in.close();
		
		// Save all PCLs
		ArrayList<Triangle> PCLs = new ArrayList<Triangle>();
		int count = 0;
		
		// Find the PCLs starting from the 2 triangles with the largest size
		for(int r1=0; r1<N; r1++) { // (r1, c1) is upper-left corner
			for(int c1=0; c1<N; c1++) {	

				for(int r2=N-1; r2>=r1; r2--) { // (r2, c2) is lower-right corner
					for(int c2=N-1; c2>=c1; c2--) { 
						
						for (int d = 0; d < 2; d++) {
							// whether this rectangle is in a known PCL
							boolean inOtherPCL = false;
							for(Triangle T : PCLs) {  
								if(T.contains(r1, c1, r2, c2, d)) {
									inOtherPCL = true;
									break;
								}
							}
							
							if( !inOtherPCL ) {
								if( isPCL(r1, c1, r2, c2, d) ) {
									count++;
									PCLs.add(new Triangle(r1, c1, r2, c2, d));
								}
							}
						}
						
						
					}					
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new File("where.out"));
		out.println(count);
		out.close();
	}

	// Whether the rectangle (r1, c1), (r2, c2) is a PCL
	public static boolean isPCL(int r1, int c1, int r2, int c2, int d) {

		// tally marks for the letters - check which letters are present
		int[] tally = new int[26];
		// check the triangle shape
		
		if (d == 0) { // the triangle goes up from the diagonal
			for (int x = c1; x <= c2; x++) { // loop through all of the x coordinates
				for (int a = 0; a <= x - c1; a++) { // loop through the y coordinates
					int c = grid[r1+a][x] - 'A';
					tally[c]++;
				}	
			}
		} else if (d == 1) { // the triangle goes down from the diagonal
			for (int x = c2; x >= c1; x--) { // loop through all of the x coordinates
				for (int a = 0; a <= c2 - x; a++) { // loop through the y coordinates
					int c = grid[r2-a][x] - 'A';
					tally[c]++;
				}
			}
		}
		
		// Can only have two letters
		int numLetters = 0;
		for(int i=0; i<tally.length; i++)
			if(tally[i]!=0)
				numLetters++;
		
		if(numLetters!=2)
			return false;

		// identify one color (or letter) in this triangle
		char letter1='A';
		int id = 0;
		while(id<tally.length){
			if(tally[id]>0) {
				letter1 = (char) (id+'A');
				break;
			}
			id++;
		}

		// Count the number of connected components for the two letters
		int count1 = 0, count2 = 0;
		boolean[][] visited = new boolean[N][N];
		
		if (d == 0) { // the triangle goes up from the diagonal
			for (int x = c1; x <= c2; x++) { // loop through all of the x coordinates
				for (int a = 0; a <= x - c1; a++) { // loop through the y coordinates
					int y = r1 + a;
					int r = y;
					int c = x;
					
				}	
			}
		} else if (d == 1) { // the triangle goes down from the diagonal
			for (int x = c2; x >= c1; x--) { // loop through all of the x coordinates
				for (int a = 0; a <= c2 - x; a++) { // loop through the y coordinates
					int y = r2 - a;
					int r = y;
					int c = x;
					
					if(!visited[r][c]) {

						if( grid[r][c]==letter1 )
							count1++;
						else
							count2++;

						visited[r][c] = true;
						LinkedList<Point> L = new LinkedList<Point>();
						L.add(new Point(r,c));
						
						while( !L.isEmpty() ) {
							Point P = L.removeFirst();
							for(int k=0; k<dx.length; k++) {
								int rn = P.r + dy[k];
								int cn = P.c + dx[k];
								if(rn>=r1 && rn<=r2 && cn>=c1 && cn<=c2) {
									if( !visited[rn][cn] && grid[rn][cn]==grid[r][c] ) {
										visited[rn][cn] = true;
										L.add( new Point(rn, cn) );
									}
								}
							}
						}
					}
					
				}
			}
		}
		
		// loop through the triangles

		if(count1==1 && count2>1 || count2==1 && count1>1)
			return true;
		else
			return false;
		
	}
	
	static class Triangle {
		int r1, c1;   // upper-left corner
		int r2, c2;   // lower-right corner
		int d; // direction - 0 means up, 1 means down
		Triangle(int r1, int c1, int r2, int c2, int dd){
			this.r1 = r1;
			this.c1 = c1;
			this.r2 = r2;
			this.c2 = c2;
			this.d = d;
		}

		// whether this triangle contains the triangle defined by
		// upper-left corner (y1, x1) and lower-right corner (y2, x2)
		public boolean contains(int y1, int x1, int y2, int x2, int dir) {
			return dir == d && y1>=r1 && y1<=r2 && y2>=r1 && y2<=r2 &&
					x1>=c1 && x1<=c2 && x2>=c1 && x2<=c2;
		}
	}
	
	static class Point {
		int r, c;   // row and column indices
		Point(int r, int c){
			this.r = r;
			this.c = c;
		}
	}
}

