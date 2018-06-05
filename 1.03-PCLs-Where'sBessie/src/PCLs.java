/*
 * Find the maximum PCL (potential cow location) in a rectangular grid. A PCL
 * is defined as a sub-rectangle, where only two letters appear. One letter 
 * forms a connected component, while the other letter forms more than one
 * connected component. A maximum PCL is one that is not contained in another
 * PCL.
 * 
 * Teacher's solution here
 * 
 * USACO 2017 US Open Contest, Silver 
 * Problem 3. Where's Bessie?
*/

import java.util.*;
import java.io.*;

public class PCLs {

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
		ArrayList<Rectangle> PCLs = new ArrayList<Rectangle>();
		int count = 0;
		
		// Find the PCLs starting from the rectangle with the largest size
		for(int r1=0; r1<N; r1++) { // (r1, c1) is upper-left corner
			for(int c1=0; c1<N; c1++) {	

				for(int r2=N-1; r2>=r1; r2--) { // (r2, c2) is lower-right corner
					for(int c2=N-1; c2>=c1; c2--) { 
						
						// whether this rectangle is in a known PCL
						boolean inOtherPCL = false;
						for(Rectangle T : PCLs) {  
							if(T.contains(r1, c1, r2, c2)) {
								inOtherPCL = true;
								break;
							}
						}
						
						if( !inOtherPCL ) {
							if( isPCL(r1, c1, r2, c2) ) {
								count++;
								PCLs.add(new Rectangle(r1, c1, r2, c2));
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
	public static boolean isPCL(int r1, int c1, int r2, int c2) {

		// tally marks for the letters
		int[] tally = new int[26];
		for(int y=r1; y<=r2; y++) {
			for(int x=c1; x<=c2; x++) {
				int c = grid[y][x] - 'A';
				tally[c]++;
			}
		}
		
		// Can only have two letters
		int numLetters = 0;
		for(int i=0; i<tally.length; i++)
			if(tally[i]!=0)
				numLetters++;
		
		if(numLetters!=2)
			return false;

		// identify one color (or letter) in this rectangle
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
		for(int r=r1; r<=r2; r++) {
			for(int c=c1; c<=c2; c++) { // for every location
				
		
				if(!visited[r][c]) { // if it's not visited, increment letter 1 or letter 2 (the number of clusters of L1 or 2)
					if( grid[r][c]==letter1 )
						count1++;
					else
						count2++;

					visited[r][c] = true; // mark it as visited
					LinkedList<Point> L = new LinkedList<Point>(); // to be visited
					L.add(new Point(r,c));
					
					while( !L.isEmpty() ) {
						Point P = L.removeFirst();
						for(int k=0; k<dx.length; k++) { // check all the neighbors
							int rn = P.r + dy[k];
							int cn = P.c + dx[k];
							if(rn>=r1 && rn<=r2 && cn>=c1 && cn<=c2) { // if it's within the rectangle's borders
								if( !visited[rn][cn] && grid[rn][cn]==grid[r][c] ) { // if it's not visited and the same letter
									visited[rn][cn] = true; // mark it as visited
									L.add( new Point(rn, cn) ); // add it to to be visited
								}
							}
						}
					}
				}
			}
		}

		if(count1==1 && count2>1 || count2==1 && count1>1) // only 1 cluster of 1 and 2 or more clusters of the other
			return true; // it's a PCL
		else
			return false; // it's not a PCL
	}
	
	static class Rectangle {
		int r1, c1;   // upper-left corner
		int r2, c2;   // lower-right corner

		Rectangle(int r1, int c1, int r2, int c2){
			this.r1 = r1;
			this.c1 = c1;
			this.r2 = r2;
			this.c2 = c2;
		}

		// whether this rectangle contains the rectangle defined by
		// upper-left corner (y1, x1) and lower-right corner (y2, x2)
		public boolean contains(int y1, int x1, int y2, int x2) {
			return y1>=r1 && y1<=r2 && y2>=r1 && y2<=r2 &&
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

