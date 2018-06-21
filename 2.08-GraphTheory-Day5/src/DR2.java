import java.io.*;
import java.util.*;
import java.awt.Point;
/*
 * make a graph out of the grid
 * put in the barns and the 4 adjacents
 * 
 * doesn't work
 */
public class DR2 {
	static int F;
	static ArrayList<Point> pos;
	static Point[] barns;
	static int[][] mat;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	static int[] dist; // dist from source to node i
	static int V; // number of edgess
	static boolean[] visited;
	static boolean[] farm; 

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		F = Integer.parseInt(scan.nextLine()); // number of farms

		barns = new Point[F]; // stores positions of the barns
		pos = new ArrayList<Point>(); // stores positions of the nodes

		// map the farm index to the node index
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		int posInd = 0; // index in pos so far

		for (int i = 0; i < F; i++) { // loop through x y coordinates
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

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
		farm = new boolean[posInd];
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			farm[it.next()] = true; // mark true
		}
		
		mat = new int[posInd][posInd]; // graph
		// no edge means infinity length
		for (int i = 0; i < posInd; i++) {
			for (int j = 0; j < posInd; j++) {
				mat[i][j] = Integer.MAX_VALUE; 
			}
		}
		// set proper edge lengths
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
				
				// coordinates of the "corner" for the straight line paths cur to j
				int x1 = curx;
				int y1 = jy;
				int x2 = jx;
				int y2 = cury;
				
				// if there's barns in the way from
				// cur --> p1 --> j or cur --> p2 --> j
				
				boolean farmPresent = false;
				
				for (int k = 0; k < F; k++) { // loop through all farms
					int nodeInd = map.get(k); // graph index of farms
					int fx = pos.get(nodeInd).x; // x coord of farm
					int fy = pos.get(nodeInd).y; // y coord of farm
					
					if (fx == x1 && between(cury, y1, fy) || 
							fy == y1 && between(curx, x1, fx)) {
						farmPresent = true;
					}
				}
				
				if (farmPresent) {
					// if the farms are on the path
					// don't add an edge
					//System.out.println(i + " " + j);
					mat[i][j] = Integer.MAX_VALUE;
					mat[j][i] = Integer.MAX_VALUE;
					//System.out.println(curx + " " + cury + "  " + jx + " " + jy);
				} else {
					// no farms on the paths, so set matrix distance
					mat[i][j] = Math.abs(curx-jx) + Math.abs(cury-jy);
					mat[j][i] = Math.abs(curx-jx) + Math.abs(cury-jy);
				}
			}
			System.out.println(Arrays.toString(mat[i]));
		}
		//System.out.println();
		V = posInd; // number of nodes

		int totalSum = 0;

		for (int i = 0; i < F; i++) { // loop through all the farms
			// delete all edges that connect any barns to the rest of the graph
			// other than the source and destination barn

/*
			for (int j = 0; j < posInd; j++) {
				System.out.println(Arrays.toString(mat2[j]));
			}*/
			//System.out.println("i: " + i);
			// calculate distance from every farm to the next one
			dijkstra(map.get(i));
			System.out.println("dist: " + Arrays.toString(dist));

			System.out.println(dist[map.get((i+1)%F)]);
			totalSum += dist[map.get((i+1)%F)]; // add the distance from i to i+1

		}

		System.out.println(totalSum);

	}
	public static boolean between(int a, int b, int c) {
		// returns true if c is between a and b
		if (a-b > 0) {
			return (a > c && b < c);
		} else {
			return (a < c && b > c);
		}
		
	}
	public static void dijkstra(int u) { // starting node u
		System.out.println("u: " + u);
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
		// check if all the non-farms are visited
		boolean allVisited = true;

		for (int i = 0; i < V; i++) {
			if (!visited[i] && !farm[i]) { 
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
				if (!visited[i] && dist[i] < minD && !farm[i]) {
					// if not visited and smaller distance
					minD = dist[i];
					v = i;
				}
			}
			
			if (v == -1) break; // if there's nothing more
			//System.out.println(v);

			// update dist[] for neighbors of v

			for (int w = 0; w < V; w++) { // loop through neighbors
				
				if (mat[v][w] != Integer.MAX_VALUE) { 
					// if there's an edge and w is a neighbor
					dist[w] = Math.min(dist[w], dist[v] + mat[v][w]);
				}
			}

			visited[v] = true;

		}
	}
}
