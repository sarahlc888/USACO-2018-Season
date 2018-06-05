import java.io.*;
import java.util.*;
/*
 * 
 */
public class MM {
	public static int[] dr = {0, 0, -1, 1};
	public static int[] dc = {-1, 1, 0, 0};
	
	public static int N;
	public static boolean condition = true;
	public static int[][] grid;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("multimoo.in"));
		N = Integer.parseInt(br.readLine());
		grid = new int[N][N];
		int type = 0; // the next "type" of cow
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		
		// FIND ALL DISCRETE REGIONS
		ArrayList<Region> regs = new ArrayList<Region>(); // list of regions
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				State s = new State(i, j);
				Region cur = BFS(s);
			}
			
		}
		System.out.println(regs);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("multimoo.out")));

		pw.println();
		pw.close();
	}
	public static class Region {
		ArrayList<State> sqrs;
		int type;
		
		public Region(ArrayList<State> s) {
			sqrs = s;
			type = grid[s.get(0).r][s.get(0).r];
		}
		public Region() {
			sqrs = new ArrayList<State>();
		}
		public String toString() {
			return sqrs.toString();
		}
		
	}
	
	
	public static Region BFS(State start) { 
		// BFS for squares of the same type as type x
		// start = starting cow
		LinkedList<State> toVisit = new LinkedList<State>();	
		toVisit.add(start);
		
		Region r = new Region(); // region
		
		boolean[][] hasBeenVisited = new boolean[N][N];
		hasBeenVisited[start.r][start.c] = true;
		
		while (!toVisit.isEmpty()) {
			State cur = toVisit.removeFirst(); // current cow
			r.sqrs.add(cur); // add to the region
			
			// check n, e, s, w
			
			for (int i = 0; i < 4; i++) { // loop through all other cows
				if (cur.r + dr[i] >= 0 && cur.r + dr[i] < N &&
						cur.c + dc[i] >= 0 && cur.c + dc[i] < N &&
						!hasBeenVisited[cur.r + dr[i]][cur.c + dc[i]] && 
						grid[cur.r + dr[i]][cur.c + dc[i]] == grid[cur.r][cur.c]) {
					// if the adjacent is not visited and of the same type
					toVisit.add(new State(cur.r + dr[i], cur.c + dc[i]));
					hasBeenVisited[cur.r + dr[i]][cur.c + dc[i]] = true;
				}
			}
		}

		return r;	
	}
	public static class State {
		int r;
		int c;
		public State(int rin, int cin) {
			r = rin;
			c = cin;
		}
	}

}
