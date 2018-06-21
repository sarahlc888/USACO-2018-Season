import java.io.*;
import java.util.*;

/*
 * not done
 * 
 * pads and jumps definitely don't work, paths is a maybe
 */

public class SilverLily {
	static int[] dx = {2, 2, 1, 1, -2, -2, -1, -1};
	static int[] dy = {1, -1, 2, -2, 1, -1, 2, -2};
	
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int M = Integer.parseInt(st.nextToken()); // num rows
		int N = Integer.parseInt(st.nextToken()); // num cols
		
		int[][] pond = new int[M][N]; // pond
		// pond with nodes
		Node[][] nodePond = new Node[M][N];
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				nodePond[i][j] = new Node();
				nodePond[i][j].r = i;
				nodePond[i][j].c = j;
			}
		}
		
		// start and end coordinates
		int startr = -1;
		int startc = -1;
		int endr = -1;
		int endc = -1;
		
		for (int i = 0; i < M; i++) { // scan in the pond
			st = new StringTokenizer(scan.nextLine());
			for (int j = 0; j < N; j++) {
				pond[i][j] = Integer.parseInt(st.nextToken());
				if (pond[i][j] == 3) { // starting point
					startr = i;
					startc = j;
				}
				if (pond[i][j] == 4) { // dest
					endr = i;
					endc = j;
				}
			}
		}
		
		// starting and ending nodes
		Node start = nodePond[startr][startc];
		Node end = nodePond[endr][endc];
		
		// BFS
		// initialize the start: everything is 0. Pads and jumps = 0.
		start.paths = 1;
		start.pads = 0;
		start.jumps = 0;
		
		PriorityQueue<Node> toVisit = new PriorityQueue<Node>(nodeComparator);	
		toVisit.add(start); // add the start
		
		boolean[][] visited = new boolean[M][N];
		visited[startr][startc] = true;
		
		while (!toVisit.isEmpty()) {
			Node cur = toVisit.poll(); // current node
			
			visited[cur.r][cur.c] = true; // mark visited
			
			for (int i = 0; i < dx.length; i++) { // loop through possible next locations
				// next coordinates
				int nr = cur.r + dx[i];
				int nc = cur.c + dy[i];
				
				// boundary
				if (nr >= M || nc >= N || nr < 0 || nc < 0) continue;
				
				if (!visited[nr][nc] && pond[nr][nc] != 2) {
					// if it's not visited and is not a rock
					// add i to toVisit, mark visited, increment count
					
					Node next = nodePond[nr][nc];
					
					// TODO: restrict this reassignment? to the min jumps?
					
					if (cur.jumps + 1 < next.jumps) {
						// if the new jumps < the old next.jumps, update
						
						next.jumps = cur.jumps + 1;
						if (pond[nr][nc] == 0) {
							next.pads = cur.pads + 1;
						} else {
							next.pads = cur.pads;
						}
					}
					/*
					if (pond[nr][nc] == 0) {
						// empty pond, need to add a new lily pad
						if (next.pads >= 0) {
							next.pads = Math.min(next.pads, cur.pads + 1);
						} else {
							next.pads = cur.pads + 1;
						}
						
					}
					*/
					next.paths += cur.paths;
					
					toVisit.add(next);
					
				}
			}
		}
		System.out.println(nodePond[M-1][N-1].pads);
		System.out.println(nodePond[M-1][N-1].jumps);
		System.out.println(nodePond[M-1][N-1].paths);
	}
	public static class Node {
		int r;
		int c;
		int pads;
		int jumps;
		int paths;
		public Node() { // default state (will sort to the end of the queue)
			pads = Integer.MAX_VALUE;
			jumps = Integer.MAX_VALUE;
			paths = 0;
		}
	}
	public static Comparator<Node> nodeComparator = new Comparator<Node>(){

		@Override
		public int compare(Node x, Node y) {
			// sort by lowest pads, then lowest jumps, then highest paths
			
			if (x.pads < y.pads) return 1;
			else if (x.pads > y.pads) return -1;
			else if (x.jumps < y.jumps) return 1;
			else if (x.jumps > y.jumps) return -1;
			else if (x.paths > y.paths) return 1;
			else if (x.paths < y.paths) return -1;
			else return 0;
			
		}
	};

}
