import java.io.*;
import java.util.*;
/*
 * USACO 2016 December Contest, Gold
 * Problem 3. Lasers and Mirrors
 * 
 * NESW = 0123 respectively
 * 
 * it currently doesn't work at all
 * it counts wrong and counts paths that don't end at the barn
 */
public class Lasers {
	// hash maps
	public static HashMap<Integer, ArrayList<Integer>> XV; 
	public static HashMap<Integer, ArrayList<Integer>> YV;
	
	public static int[][] posts; 
	public static boolean[][] dp; 
	
	public static void main(String args[]) throws IOException {
		// INPUT
		
		BufferedReader br = new BufferedReader(new FileReader("lasers.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // number of fence posts
		
		// coords of laser and barn
		int[] laser = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())}; 
		int[] barn = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())}; 
		
		posts = new int[N+2][2]; // (x, y) of fence posts
		for (int i = 1; i <= N; i++) { // scan in coords
			st = new StringTokenizer(br.readLine());
			posts[i][0] = Integer.parseInt(st.nextToken());
			posts[i][1] = Integer.parseInt(st.nextToken());
		}
		// include laser and barn as posts
		posts[0] = laser;
		posts[N+1] = barn;
		
		br.close();
		
		// BODY
		// mapping an x or y coordinate to all indices in posts that have that x or y
		XV = new HashMap<Integer, ArrayList<Integer>>();
		YV = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i < N+2; i++) { // loop through all points
			// map x
			if (!XV.containsKey(posts[i][0]))  // if the arraylist isn't inited yet, init
				XV.put(posts[i][0], new ArrayList<Integer>());
			XV.get(posts[i][0]).add(i); // add index i to the x map
			
			// map y
			if (!YV.containsKey(posts[i][1])) // if the arraylist isn't inited yet, init
				YV.put(posts[i][1], new ArrayList<Integer>());
			YV.get(posts[i][1]).add(i); // add  index i to the y map
			
		}
		
		// dp element
		// args: post, direction the beam is going
		// store: boolean for if the state is possible or not
		
		dp = new boolean[N+2][4];
		
		// functions to fill
		int count0 = dpBFS(0, 0, 0, true);
		dp = new boolean[N+2][4];
		int count1 = dpBFS(0, 1, 0, true);
		dp = new boolean[N+2][4];
		int count2 = dpBFS(0, 2, 0, true);
		dp = new boolean[N+2][4];
		int count3 = dpBFS(0, 3, 0, true);
		
		System.out.println();
		System.out.println(count0);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
		System.out.println();
		
		int min = Math.min(Math.min(count0, count1), Math.min(count2, count3));

		// OUTPUT
		
		System.out.println(min);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lasers.out")));

		pw.println();
		pw.close();
	}
	public static int[] calcDir(int inDir) {
		// calculates the 3 possible directions any fencepost can send a certain beam
		// inDir = direction the beam comes from
		
		// direction the beam would go in each case
		
		int[] dir = new int[3]; // ind 0 = no mirror, 1 = /, 2 = \
		dir[0] = inDir; // no mirror, beam passes by
		
		int fslash; // mirror with forward slash orientation
		int bslash; // mirror with backward slash orientation

		if (inDir == 0) { // north
			fslash = 1; // east
			bslash = 3; // west
		} else if (inDir == 1) { // east
			fslash = 0; // north
			bslash = 2; // south
		} else if (inDir == 2) { // south
			fslash = 3; // west
			bslash = 1; // east
		} else { // west
			fslash = 2;
			bslash = 0; // north
		}
		dir[1] = fslash;
		dir[2] = bslash;
		
		return dir;
	}
	public static int dpBFS(int curPost, int inDir, int count) {
		System.out.println("cp: " + curPost + " inDir: " + inDir + " c: " + count);
		
		int x = posts[curPost][0];
		int y = posts[curPost][1];
		// only call the function if this is a valid orientation
		
		// calculate the possible directions
		int[] dir = new int[3];
		dir = calcDir(inDir);
		
		for (int i = 0; i < 3; i++) {
			// loop through the three possible directions
			
			boolean cont = false; // if the beam went on somewhere else, or just died
			
			if (dir[i] == 0) { // north (up)
				// get northern neighbors, loop through indices with the same x
				
				ArrayList<Integer> neigh = XV.get(x);
				
				
				
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					
					if (posts[neigh.get(j)][1] > y && !dp[neigh.get(j)][dir[i]]) { // if above current post and not yet true
						// mark DP array true and call recursively
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						count = dpBFS(neigh.get(j), dir[i], count + 1); 
					}
				}
				
			} else if (dir[i] == 1) { // east (right)
				
				// get eastern neighbors, loop through indices with same y
				ArrayList<Integer> neigh = YV.get(y);
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					if (posts[neigh.get(j)][0] > x && !dp[neigh.get(j)][dir[i]]) {
						// if the post is east of cur and not yet true, mark true and call rec
						System.out.println(neigh.get(j) + " " + dir[i]);
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						count = dpBFS(neigh.get(j), dir[i], count + 1); 
					}
				}
				
			} else if (dir[i] == 2) { // south (down)
				// get southern neighbors, loop through indices with same x
				
				ArrayList<Integer> neigh = XV.get(x);
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					
					if (posts[neigh.get(j)][1] < y && !dp[neigh.get(j)][dir[i]]) { // if below current post
						// mark DP array true and call recursively
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						count = dpBFS(neigh.get(j), dir[i], count + 1); 
					}
				}
				
				
				
			} else { // west (left)
				// get western neighbors, loop through indices with same y
				ArrayList<Integer> neigh = YV.get(y);
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					if (posts[neigh.get(j)][0] < x && !dp[neigh.get(j)][dir[i]]) {
						// if the post is west of cur, mark true and call rec
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						count = dpBFS(neigh.get(j), dir[i], count + 1); 
					}
				}
			}
			
			
			if (cont) {
				// if the beam went on somewhere
			}
		}
		
		/*
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		toVisit.add(0); // starting point at the laser
		boolean[] hasBeenVisited = new boolean[N];
		hasBeenVisited[0] = true; // mark as visited

		while (!toVisit.isEmpty()) {
			int cur = toVisit.removeFirst(); // current pos

			for (int i = 0; i < N; i++) { // loop through all other cows
				if (!hasBeenVisited[i] && condition) {
					// if it's not visited and is within condition
					// add i to toVisit, mark visited, increment count
					toVisit.add(i);
					hasBeenVisited[i] = true;
					
				}
			}
		}
		*/
		
		return count;
	}
	// overloaded function for initial use, counts
	public static int dpBFS(int curPost, int outDir, int count, boolean a) {
		System.out.println("cp: " + curPost + " outDir: " + outDir + " c: " + count);
		
		int x = posts[curPost][0];
		int y = posts[curPost][0];
		// only call the function if this is a valid orientation
		
		/*
		int count0 = count;
		int count1 = count;
		int count2 = count;
		int count3 = count;
		*/
		
		if (outDir == 0) { // north (up)
			// get northern neighbors, loop through indices with the same x
			ArrayList<Integer> neigh = XV.get(x);
			for (int j = 0; j < neigh.size(); j++) {
				// neigh.get(j) is index of a potential reachable post

				if (posts[neigh.get(j)][1] > y) { // if above current post
					// mark DP array true and call recursively
					dp[neigh.get(j)][outDir] = true;
					count = dpBFS(neigh.get(j), outDir, count + 1); 
				}
			}

		} else if (outDir == 1) { // east (right)

			// get eastern neighbors, loop through indices with same y
			ArrayList<Integer> neigh = YV.get(y);
			for (int j = 0; j < neigh.size(); j++) {
				// neigh.get(j) is index of a potential reachable post
				if (posts[neigh.get(j)][0] > x) {
					// if the post is east of cur, mark true and call rec
					dp[neigh.get(j)][outDir] = true;
					count = dpBFS(neigh.get(j), outDir, count + 1); 
				}
			}

		} else if (outDir == 2) { // south (down)
			// get southern neighbors, loop through indices with same x

			ArrayList<Integer> neigh = XV.get(x);
			for (int j = 0; j < neigh.size(); j++) {
				// neigh.get(j) is index of a potential reachable post

				if (posts[neigh.get(j)][1] < y) { // if below current post
					// mark DP array true and call recursively
					dp[neigh.get(j)][outDir] = true;
					count = dpBFS(neigh.get(j), outDir, count + 1); 
				}
			}



		} else { // west (left)
			// get western neighbors, loop through indices with same y
			ArrayList<Integer> neigh = YV.get(y);
			for (int j = 0; j < neigh.size(); j++) {
				// neigh.get(j) is index of a potential reachable post
				if (posts[neigh.get(j)][0] < x) {
					// if the post is west of cur, mark true and call rec
					dp[neigh.get(j)][outDir] = true;
					count = dpBFS(neigh.get(j), outDir, count + 1); 
				}
			}
		}


		
		/*
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		toVisit.add(0); // starting point at the laser
		boolean[] hasBeenVisited = new boolean[N];
		hasBeenVisited[0] = true; // mark as visited

		while (!toVisit.isEmpty()) {
			int cur = toVisit.removeFirst(); // current pos

			for (int i = 0; i < N; i++) { // loop through all other cows
				if (!hasBeenVisited[i] && condition) {
					// if it's not visited and is within condition
					// add i to toVisit, mark visited, increment count
					toVisit.add(i);
					hasBeenVisited[i] = true;
					
				}
			}
		}
		*/
		return count;
		//return Math.min(Math.min(count0, count1), Math.min(count2, count3));
	}


}
