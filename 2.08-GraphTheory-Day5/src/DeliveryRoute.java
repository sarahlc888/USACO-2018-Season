import java.io.*;
import java.util.*;
import java.awt.Point;
/*
 * make a graph out of the grid
 * put in the barns and the 4 adjacents
 * 
 * 0.64 credit
 */
public class DeliveryRoute {
	static int F;
	static ArrayList<Point> pos;
	static Point[] barns;
	static int[][] mat;
	static int[][] mat2;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	static int[] dist; // dist from source to node i
	static int V; // number of edgess
	static boolean[] visited;
	
	
	public static boolean notBlocked(int x1, int y1, int x2, int y2) {
		// always make y2 >= y1
		if (y2 < y1) { 
			int tempx = x2;
			int tempy = y2;
			x2 = x1;
			y2 = y1;
			x1 = tempx;
			y1 = tempy;
		}
		
		boolean block1 = false;
		boolean block2 = false;
		
		for (int i = 0; i < barns.length; i++) {
			int bx = barns[i].x;
			int by = barns[i].y;
			// upper path (y = y2)
			if (by == y2) { // same y
				if (x1 < bx && bx < x2 || x2 < bx && bx < x1) {
					// if it's between the xs
					block1 = true;
				}
			}
			if (bx == x1) { // same x
				if (y1 < by && by < y2 || y2 < by && by < y1) {
					// if it's between the ys
					block1 = true;
				}
			}
			
			// lower path (y = y2)
			if (by == y1) { // same y
				if (x1 < bx && bx < x2 || x2 < bx && bx < x1) {
					// if it's between the xs
					block2 = true;
				}
				
			}
			if (bx == x2) { // same x
				// between the yx
				if (y1 < by && by < y2 || y2 < by && by < y1) {
					// if it's between the ys
					block2 = true;
				}
			}
		}
		if (block1 && block2) { // if both are blocked
			return false;
		} else { // otherwise, one is not blocked
			return true;
		}
	}
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		F = Integer.parseInt(scan.nextLine()); // number of farms

		boolean mentioned1 = false;
		
		barns = new Point[F]; // stores positions of the barns
		pos = new ArrayList<Point>(); // stores positions of the nodes

		// map the farm index to the node index
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		int posInd = 0; // index in pos so far

		for (int i = 0; i < F; i++) { // loop through x y coordinates
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			if (x == 1 || y == 1) mentioned1 = true;
			
			Point p = new Point(x, y);

			// put into barns
			barns[i] = p;

			// put into pos (the graph)
			if (pos.contains(p)) { // if p is already in pos, update the map
				map.put(i, pos.indexOf(p));
			} else { // otherwise add it in
				pos.add(p);
				map.put(i, posInd); // map the farm index to the graph index
				posInd++;
			}

			for (int j = 0; j < 4; j++) { // all four directions
				// (for when you can't go into the actual farm again)
				Point p2 = new Point(x+dx[j], y+dy[j]);

				if (!pos.contains(p2)) { 
					// if the point isn't already in the graph, add it
					pos.add(p2);
					posInd++;
				} 
			}
		}
		
		
		mat = new int[posInd][posInd]; // graph
		// no edge means infinity length
		for (int i = 0; i < posInd; i++) {
			for (int j = 0; j < posInd; j++) {
				mat[i][j] = Integer.MAX_VALUE; 
			}
		}
		
		// loop through all pairs to set proper edge lengths
		for (int i = 0; i < posInd; i++) { // node i
			// current coordinates
			int curx = pos.get(i).x;
			int cury = pos.get(i).y;
			//System.out.println(i + ": " + curx + " " + cury);
			for (int j = 0; j < posInd; j++) { // node j
				// j coordinates
				if (i == j) continue;

				int jx = pos.get(j).x;
				int jy = pos.get(j).y;
				
				
				if (notBlocked(curx, cury, jx, jy)) { // if there's a path
					// set matrix distance
					mat[i][j] = Math.abs(curx-jx) + Math.abs(cury-jy);
					mat[j][i] = Math.abs(curx-jx) + Math.abs(cury-jy);
				}

				
				//System.out.println("i: " + i + " j: " + j + "  " + mat[i][j]);
				//System.out.println("  " + jx + " " + jy);
			}
			//System.out.println(Arrays.toString(mat[i]));
		}
		//System.out.println();
		V = posInd; // number of nodes

		int totalSum = 0;

		for (int i = 0; i < F; i++) { // loop through all the farms
			// delete all edges that connect any barns to the rest of the graph
			// other than the source and destination barn

			mat2 = new int[posInd][posInd]; // new version of mat
			for (int j = 0; j < posInd; j++) {
				for (int k = 0; k < posInd; k++) {
					mat2[j][k] = mat[j][k];
				}
			}

			for (int j = 0; j < F; j++) { // loop through the other farms
				// not i and not i + 1 because those are the source and dest
				if (i == j) continue;
				if ((i + 1)%F == j) continue;
				//System.out.println("cut out: " + map.get(j));
				for (int k = 0; k < posInd; k++) { // loop through all nodes
					// get rid of the edges to map.get(j)
					mat2[map.get(j)][k] = Integer.MAX_VALUE;
					mat2[k][map.get(j)] = Integer.MAX_VALUE;
				}
			}

			//System.out.println("i: " + i);
			// calculate distance from every farm to the next one
			dijkstra(map.get(i));
			//System.out.println("dist: " + Arrays.toString(dist));

			//System.out.println(dist[map.get((i+1)%F)]);
			
			totalSum += dist[map.get((i+1)%F)]; // add the distance from i to i+1
			//System.out.println(totalSum);
		}

		System.out.println(totalSum);

	}
	public static void dijkstra(int u) { // starting node u

		// O(N^2) for all nodes (only process once each), look at all neighbors
		dist = new int[V];
		visited = new boolean[V];

		// init dist
		for (int i = 0; i < V; i++) {
			if (i == u) {
				dist[i] = 0;
			} else {
				dist[i] = Integer.MAX_VALUE;
			}
		}
		// check if all the ones are visited
		boolean allVisited = true;

		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				allVisited = false;
			}
		}
		while (!allVisited) { // not all nodes visited
			//System.out.println(Arrays.toString(dist));
			//System.out.println(Arrays.toString(visited));

			// int v = node so that dist[v] is min and v is unvisited

			int v = -1; // current node
			int minD = Integer.MAX_VALUE; // min dist
			for (int i = 0; i < V; i++) {
				// loop through all the nodes
				if (!visited[i] && dist[i] < minD) {
					// if not visited and smaller distance
					minD = dist[i];
					v = i;
				}
			}

			if (v == -1) break; // if there's nothing more
			//System.out.println(v);

			// update dist[] for neighbors of v

			for (int w = 0; w < V; w++) { // loop through neighbors
				if (mat2[v][w] != Integer.MAX_VALUE) { 
					// if there's an edge and w is a neighbor
					dist[w] = Math.min(dist[w], dist[v] + mat2[v][w]);
				}
			}

			visited[v] = true;

		}
	}
}
