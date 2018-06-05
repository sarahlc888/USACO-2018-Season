import java.io.*;
import java.util.*;

// 2017 feb silver problem 3

// all test cases correct
// be careful of doubling the grid size



public class CowCrossIII {
	
	static int[] rs = {0, 0, -1, 1};
	static int[] cs = {1, -1, 0, 0};
	
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("countcross.in"));
		int N = scan.nextInt(); // n by n grid
		int K = scan.nextInt(); // k cows
		int R = scan.nextInt(); // number of roads
		
		Point[][] grid = new Point[2*N][2*N]; // twice as big to make the roads a grid cell too
		
		// go through and initialize everything as empty
		for (int i = 0; i < 2*N; i++) {
			for (int j = 0; j < 2*N; j++) {
				grid[i][j] = new Point(i, j, 0);
			}
		}
		// IMPORTANT: THIS IS WHERE THE BUG WAS!!!!!!!
		
		// all road intersections are also marked 2 to catch other cases
		// 1 0 1
		// 2 0 2 <-- should be 2 2 2 because it's a road cell
		// 1 0 1
		for (int i = 1; i < 2*N; i+=2) {
			for (int j = 1; j < 2*N; j+=2) {
				grid[i][j].val = 2;
			}
		}
		
		
		for (int i = 0; i < R; i++) { // scan in the roads as 2s
			// correct from 1...N indexing to 0...N-1
			int r1 = (scan.nextInt() - 1) * 2;
			int c1 = (scan.nextInt() - 1) * 2;
			int r2 = (scan.nextInt() - 1) * 2;
			int c2 = (scan.nextInt() - 1) * 2;
			
			if (r1 == r2) {
				grid[r1][Math.max(c1, c2) - 1].val = 2;
			} else if (c1 == c2) {
				grid[Math.max(r1, r2) - 1][c1].val = 2;
			}
			
		}
		for (int i = 0; i < K; i++) { // scan in the cows as 1s
			int r = (scan.nextInt() - 1) * 2; // correct the indexing again
			int c = (scan.nextInt() - 1) * 2;
			grid[r][c].val = 1;
		}
		
		scan.close(); 
		
		/*
		
		for (int i = 0; i < 2*N - 1; i++) {
			for (int j = 0; j < 2*N - 1; j++) {
				System.out.print(grid[i][j].val + " ");
			}
			System.out.println();
		}
		*/
		
		// list of clusters
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		
		// keeps track of if a point has already been visited
		boolean[][] hasBeenVisited = new boolean[2*N][2*N];
		
		// loop through every grid spot and see if it's part of a cluster
		
		for (int r = 0; r < 2*N - 1; r++) {
			for (int c = 0; c < 2*N - 1; c++) {
				if (hasBeenVisited[r][c]) { // if the point has been visited, continue
					//System.out.println("has been visited: " + r + ", " + c);
					continue; 
				} else if (grid[r][c].val == 2 || grid[r][c].val == 0) {  
					//System.out.println("is road or empty");
					// only start cluster when there's a cow there
					continue;
				}

				Cluster curClust = new Cluster(); // otherwise it is the start of a new cluster
				hasBeenVisited[r][c] = true; // mark it as visited
				
				LinkedList<Point> toBeVisited = new LinkedList<Point>(); // to be visited (in same cluster)
				toBeVisited.add(grid[r][c]); // starting point of the visits
				
				while (!toBeVisited.isEmpty()) { // while there are still points to be visited
					Point curPt = toBeVisited.removeFirst(); // remove the first point from the list
					hasBeenVisited[curPt.r][curPt.c] = true; // mark the point as visited
					curClust.points.add(grid[curPt.r][curPt.c]); // add it to the cluster
					
					//System.out.println("  point: " + curPt.r + ", " + curPt.c);
					
					for (int i = 0; i < 4; i++) { // check NSEW of curPt to see if those points are open
						int rVal = curPt.r+rs[i];
						int cVal = curPt.c+cs[i];
						
						if (rVal >= 0 && rVal < 2*N - 1 && cVal >= 0 && cVal < 2*N - 1) { // in bounds
							
							//System.out.println("  neighbor: " + rVal + ", " + cVal);
							
							if (grid[rVal][cVal].val != 2 && !hasBeenVisited[rVal][cVal]) { // as long as it's not 2 (not a road)
								toBeVisited.add(grid[rVal][cVal]);
								hasBeenVisited[rVal][cVal] = true;
								//System.out.println("  point added");
							} else {
								//if (grid[rVal][cVal].val == 2) System.out.println("  is road");
								//if (hasBeenVisited[rVal][cVal]) System.out.println("  already visited");
							}
						}
					}
				}
				clusters.add(curClust);
				
				/*
				for (int i = 0; i < curClust.points.size(); i++) {		
					System.out.println(curClust.points.get(i).r + ", " + curClust.points.get(i).c);
				}*/
			}
		}
		
		int[] cowCs = new int[clusters.size()]; // number of cows in each cluster
		//System.out.println("cluster num: " + clusters.size());
		
		for (int i = 0; i < clusters.size(); i++) {
			// count how many cows are in each cluster
			int cowCount = 0; // count for that cluster
			
			for (int j = 0; j < clusters.get(i).points.size(); j++) {
				Point curPoint = clusters.get(i).points.get(j);
				//System.out.println("  " + curPoint.r + ", " + curPoint.c);
				//System.out.println("   " + curPoint.val);
				
				if (curPoint.val == 1) cowCount++; // if it's a cow, increment the count
				
			}
			cowCs[i] = cowCount;
		}
		int pairs = 0;
		for (int i = 0; i < cowCs.length; i++) {
			
			//System.out.println(cowCs[i]);
			
			for (int j = i+1; j < cowCs.length; j++) {
				pairs += (cowCs[i] * cowCs[j]);
			}
		}
		
		//System.out.println(pairs);
		PrintWriter pw = new PrintWriter(new File("countcross.out"));
		pw.println(pairs);
		pw.close();
	}
	public static class Point {
		// row and column indices
		int r;
		int c;   
		int val; // 0 means empty, 1 means cow, 2 means road
		
		Point(int r1, int c1) {
			this.r = r1;
			this.c = c1;
		}
		Point(int r1, int c1, int v) {
			this.r = r1;
			this.c = c1;
			this.val = v;
		}
	}
	public static class Cluster {
		// row and column indices
		ArrayList<Point> points;
		
		Cluster() {
			points = new ArrayList<Point>();
		}
		
	}
}
