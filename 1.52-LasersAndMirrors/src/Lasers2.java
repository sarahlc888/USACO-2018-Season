import java.io.*;
import java.util.*;
/*
 * USACO 2016 December Contest, Gold
 * Problem 3. Lasers and Mirrors
 * 
 * NESW = 0123 respectively
 * 
 * 1/10 test cases
 * it counts wrong and counts paths that don't end at the barn
 * make a real counting system
 * make sure no cycles can happen
 */
public class Lasers2 {
	// hash maps
	public static HashMap<Integer, ArrayList<Integer>> XV; 
	public static HashMap<Integer, ArrayList<Integer>> YV;
	
	public static int[][] posts; 
	public static boolean[][] dp; 
	
	public static int N; // number of posts
	
	public static void main(String args[]) throws IOException {
		// INPUT
		
		BufferedReader br = new BufferedReader(new FileReader("lasers.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // number of fence posts
		
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
		int[] dir = {0, 1, 2, 3};
		int count = dpBFS(0, dir, 0, true);
		
		// OUTPUT
		
		System.out.println(count);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lasers.out")));
		pw.println(count);
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
	
	public static int dpBFS(int curPost, int[] dir, int count, boolean origin) {
		// function will only be called if state is valid 
		// dir = 3 element array where 0 = pass, 1 = /, 2 = \
		// if origin, then it's a 3 element array
		// traces the current post and calls subsequent states
		
		System.out.println("curpost: " + curPost + " count: " + count);
		
		int x = posts[curPost][0];
		int y = posts[curPost][1];
		
		int[] dirCount = new int[dir.length]; // counts for the respective directions
		Arrays.fill(dirCount, -1); // start with everything as invalid
		int ogcount = count; // keep track of the original count
		
		for (int i = 0; i < dir.length; i++) { // loop through the directions
			count = ogcount;
			System.out.println("  cp: " + curPost + " dir: " + dir[i] + " cnt: " + count);
			
			boolean[][] visited = new boolean[N+2][4];
			
			boolean cont = false; // if the beam went on somewhere else, or just died
			
			if (dir[i] == 0) { // north (up)
				// get northern neighbors, loop through indices with the same x
				
				ArrayList<Integer> neigh = XV.get(x);
				
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					
					if (posts[neigh.get(j)][1] > y && !visited[neigh.get(j)][dir[i]]) { // if above current post and not yet true
						// mark DP array true and call recursively
						System.out.println("  neigh: " + neigh.get(j));
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						visited[neigh.get(j)][dir[i]] = true;
						
						if (neigh.get(j) == N+1) {
							if (i != 0) count++;
							System.out.println("    Success! count: " + count);
							return count;
						} else if (i==0 || origin){
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 
						} else {
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count + 1, false); 
						}
					} 
				}
				
				if (!cont) count = Integer.MAX_VALUE;
				
			} else if (dir[i] == 1) { // east (right)
				
				// get eastern neighbors, loop through indices with same y
				ArrayList<Integer> neigh = YV.get(y);
				System.out.println("neigh: " + neigh);
				for (int j = 0; j < neigh.size(); j++) {
					System.out.println("j: " + j + " cnt: " + count);
					// neigh.get(j) is index of a potential reachable post
					if (posts[neigh.get(j)][0] > x && !visited[neigh.get(j)][dir[i]]) {
						// if the post is east of cur and not yet true, mark true and call rec
						System.out.println("  neigh: " + neigh.get(j));
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						visited[neigh.get(j)][dir[i]] = true;
						if (neigh.get(j) == N+1) {
							if (i != 0) {
								System.out.println("cp: " + curPost + " cnt: " + count);
								System.out.println("inc i: " + i);
								count++;
							}
							System.out.println("    Success! count: " + count);
							return count;
						} else if (i==0 || origin){
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 
						} else {
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count + 1, false); 
						}
					}
				}
				if (!cont) count = Integer.MAX_VALUE;
			} else if (dir[i] == 2) { // south (down)
				// get southern neighbors, loop through indices with same x
				
				ArrayList<Integer> neigh = XV.get(x);
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					
					if (posts[neigh.get(j)][1] < y && !visited[neigh.get(j)][dir[i]]) { // if below current post
						// mark DP array true and call recursively
						cont = true;
						dp[neigh.get(j)][dir[i]] = true;
						visited[neigh.get(j)][dir[i]] = true;
						
						System.out.println("  neigh: " + neigh.get(j));
						
						if (neigh.get(j) == N+1) {
							if (i != 0) count++;
							System.out.println("    Success! count: " + count);
							return count;
						} else if (i==0 || origin){
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 
						} else {
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count + 1, false); 
						}
					}
				}
				
				if (!cont) count = Integer.MAX_VALUE;
				
			} else { // west (left)
				// get western neighbors, loop through indices with same y
				ArrayList<Integer> neigh = YV.get(y);
				for (int j = 0; j < neigh.size(); j++) {
					// neigh.get(j) is index of a potential reachable post
					if (posts[neigh.get(j)][0] < x && !visited[neigh.get(j)][dir[i]]) {
						// if the post is west of cur, mark true and call rec
						cont = true;
						System.out.println("  neigh: " + neigh.get(j));
						dp[neigh.get(j)][dir[i]] = true;
						visited[neigh.get(j)][dir[i]] = true;
						if (neigh.get(j) == N+1) {
							if (i != 0) count++;
							System.out.println("    Success! count: " + count);
							return count;
						} else if (i==0 || origin){
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 
						} else {
							count = dpBFS(neigh.get(j), calcDir(dir[i]), count + 1, false); 
						}
					}
				}
				if (!cont) count = Integer.MAX_VALUE;
			}
			
			
			if (cont) {
				// if the beam went on somewhere
				
				dirCount[i] = count;
			} else {
				System.out.println("  -dead end");
			}
		}
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < dirCount.length; i++) {
			
			if (dirCount[i] != -1 && dirCount[i] < min) min = dirCount[i];
		}
		System.out.println("  " + Arrays.toString(dirCount));
		System.out.println("ret: " + min);
		return min;
		
		//return count;
	}
	


}
