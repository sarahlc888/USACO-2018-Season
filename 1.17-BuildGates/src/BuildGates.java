import java.io.*;
import java.util.*;

// all 10 test cases correct! first try!

public class BuildGates {
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {1, -1, 0, 0};
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("gates.in"));
		int N = Integer.parseInt(br.readLine()); // number of moves
		String M = br.readLine(); // string of moves
		//System.out.println(M);
		br.close();
		
		char[] moves = new char[N]; // array of moves
		// numbers of moves in each direction
		int nc = 0;
		int ec = 0;
		int sc = 0;
		int wc = 0;
		// maximum movement to each side (north is positive, west is positive)
		int curVertDiff = 0;
		int curHorDiff = 0;
		int maxVertDiff = 0;
		int maxHorDiff = 0;
		int minVertDiff = Integer.MAX_VALUE;
		int minHorDiff = Integer.MAX_VALUE;
		
		for (int i = 0; i < N; i++) {
			moves[i] = M.charAt(i);
			if (moves[i] == 'N') {
				nc++;
				curVertDiff++;
			} else if (moves[i] == 'E') {
				ec++;
				curHorDiff--;
			} else if (moves[i] == 'S') {
				sc++;
				curVertDiff--;
			} else {
				wc++;
				curHorDiff++;
			}
			
			if (curVertDiff > maxVertDiff) {
				maxVertDiff = curVertDiff;
			}
			if (curHorDiff > maxHorDiff) {
				maxHorDiff = curHorDiff;
			}
			if (curVertDiff < minVertDiff) {
				minVertDiff = curVertDiff;
			}
			if (curHorDiff < minHorDiff) {
				minHorDiff = curHorDiff;
			}
		}
		minVertDiff *= -1;
		minHorDiff *= -1;
		
		/*
		System.out.println("nc: " + nc);
		System.out.println("ec: " + ec);
		System.out.println("sc: " + sc);
		System.out.println("wc: " + wc);
		
		System.out.println("maxVertDiff: " + maxVertDiff);
		System.out.println("minVertDiff: " + minVertDiff);
		System.out.println("maxHorDiff: " + maxHorDiff);
		System.out.println("minHorDiff: " + minHorDiff);
		*/
		
		// in order to hold everything, you need an array of (nc - sc + 3) by (ec - wc + 3)
		// n&s and e&w moves "cancel out" - the board must be at least 1 sqr wide - need open edges (2)
		
		
		// increase the size of the farm so that the fences can be squares too
		int[][] farm = new int[2*(maxVertDiff + minVertDiff + 1) + 1][2*(maxHorDiff + minHorDiff + 1) + 1];
		// 1 means fence, 0 means empty, 2 means has been visited
		
		
		// starting point is based off the of the number of north and west movements there are
		int curR = 1 + 2*maxVertDiff; // original row
		int curC = 1 + 2*maxHorDiff; // original column;
		/*
		System.out.println(curR);
		System.out.println(curC);
		*/
		farm[curR][curC] = 1;
		
		for (int i = 0; i < N; i++) {
			if (moves[i] == 'N') {
				curR--;
				farm[curR][curC] = 1;
				curR--;
			} else if (moves[i] == 'E') {
				curC++;
				farm[curR][curC] = 1;
				curC++;
			} else if (moves[i] == 'S') {
				curR++;
				farm[curR][curC] = 1;
				curR++;
			} else {
				curC--;
				farm[curR][curC] = 1;
				curC--;
			}
			farm[curR][curC] = 1;
		}
		/*
		for (int i = 0; i < farm.length; i++) {
			for (int j = 0; j < farm[0].length; j++) {
				System.out.print(farm[i][j] + " ");
			}
			System.out.println();
		}
		*/
		
		// count the clusters - the number of gates needed is clustercount - 1;
		int clustercount = 0;
		
		
		// BFS
		for (int i = 0; i < farm.length; i++) {
			for (int j = 0; j < farm[0].length; j++) {
				// loop through every position
				int curPos = farm[i][j];
				// don't count the gates
				if (curPos != 0) {
					// if it's not an empty space, it can't be a cluster
					// 1 means gate
					// 2 means has been visited

					continue;
				}
				// otherwise, it is the start of a new cluster
				clustercount++;
				
				// to be visited row and column coordinates
				LinkedList<Integer> tbvR = new LinkedList<Integer>();
				LinkedList<Integer> tbvC = new LinkedList<Integer>();
				
				// add the current coordinates
				tbvR.add(i);
				tbvC.add(j);
				
				while (!tbvR.isEmpty() && !tbvC.isEmpty()) {
					// while the lists are both not empty, process the next position
					curR = tbvR.removeFirst();
					curC = tbvC.removeFirst();
					// mark the spot as visited
					farm[curR][curC] = 2;
					// check neighboring positions to be added to the cluster
					for (int k = 0; k < dx.length; k++) {
						int newR = curR + dx[k];
						int newC = curC + dy[k];
						
						if (newR > -1 && newR < farm.length && newC > -1 && newC < farm[0].length && farm[newR][newC] == 0) {
							// if the spot is open and empty
							tbvR.add(newR);
							tbvC.add(newC);
							farm[newR][newC] = 2; // mark as visited
						}
						
					}
				}
			}
		}
		
		//System.out.println(clustercount - 1);
		PrintWriter pw = new PrintWriter(new File("gates.out"));
		pw.println(clustercount - 1);
		pw.close();
	}
}
