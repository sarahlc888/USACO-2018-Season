import java.io.*;
import java.util.*;
/*
 * USACO 2016 December Contest, Gold
 * Problem 3. Lasers and Mirrors
 * 
 * NESW = 0123 respectively
 * 
 * 5/11 !!!
 * 
 * works but is slow
 * 
 * 
 * old problems, some of them still relevant:
 * 1/10 test cases
 * it counts wrong and counts paths that don't end at the barn
 * make a real counting System
 * make sure no cycles can happen
 * recursion is wrong
 */
public class Lasers4 {
	// hash maps
	public static HashMap<Integer, ArrayList<Integer>> XV; 
	public static HashMap<Integer, ArrayList<Integer>> YV;

	public static int[][] posts; 

	public static int N; // number of posts

	public static int mincnt = Integer.MAX_VALUE;

	// dp array[post][direction of beam]
	// store: min step count to reach the state, -1 if it's not possible
	public static int[][] steps; 


	public static void main(String args[]) throws IOException {
		// INPUT

		BufferedReader br = new BufferedReader(new FileReader("lasers.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/6.in"));
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
		mapVals();


		// dfs element
		steps = new int[N+2][4];
		for (int i = 0; i < steps.length; i++) {
			for (int j = 0; j < 4; j++) {
				steps[i][j] = -1;
			}
		}


		int[] dir = {0, 1, 2, 3};
		dpBFS(0, dir, 0, true);

		// OUTPUT

		System.out.println(mincnt);
		/*
		for (int i = 0; i < steps.length; i++) {
			for (int j = 0; j < steps[0].length; j++) {
				System.out.print(steps[i][j] + " ");
			}
			System.out.println();
		}*/
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lasers.out")));
		pw.println(mincnt);
		pw.close();
	}
	public static void mapVals() {
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
		Set<Integer> xkeys = XV.keySet();
		Iterator itx = xkeys.iterator();
		while (itx.hasNext()) {
			Collections.sort(XV.get(itx.next()), lineCompX);
		}
		Set<Integer> ykeys = YV.keySet();
		Iterator ity = ykeys.iterator();
		while (ity.hasNext()) {
			Collections.sort(YV.get(ity.next()), lineCompY);
		}
	}

	public static Comparator<Integer> lineCompX = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (posts[x][0] > posts[y][0]) return 1;
			else if (posts[x][0] < posts[y][0]) return -1;
			else return 0;
		}
	};
	public static Comparator<Integer> lineCompY = new Comparator<Integer>(){

		@Override
		public int compare(Integer x, Integer y) {
			if (posts[x][1] > posts[y][1]) return 1;
			else if (posts[x][1] < posts[y][1]) return -1;
			else return 0;
		}
	};
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


	public static int bSearchLTMod(ArrayList<Integer> pts, int hbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already

		// exception catching
		if (hbound >= pts.get(hi)) {
			System.out.println("t1");
			return hi;
		} else if (hbound < pts.get(lo)) { // no point are in the interval
			System.out.println("t2");
			return -1;
		}

		// basecase for when hi - lo range is tiny
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			return lo;
		}

		int mid = (hi + lo)/2;

		// narrow down
		if (pts.get(mid) < hbound) {
			// go bigger
			lo = mid;
			System.out.println("t4");
			return bSearchLTMod(pts, hbound, hi, lo);
		} else if (pts.get(mid) > hbound) {
			// go smaller
			hi = mid - 1;
			return bSearchLTMod(pts, hbound, hi, lo);
		} else {
			lo = mid;
			return bSearchGTMod(pts, hbound, hi, lo);
		}

	}
	public static int bSearchGTMod(ArrayList<Integer> nums, int lbound, int hi, int lo) { // mod from counting haybales
		// returns first value >= desired condition


		// values -- basecases for when hi - lo range reduced already

		// exception catching
		if (lbound <= nums.get(lo)) {
			return lo;
		} else if (lbound > nums.get(hi)) { // not in any

			return -1;
		}

		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;

		int mid = (hi + lo)/2;

		System.out.println("hi: " + hi + " lo: " + lo + " mid: " + mid);

		int curval = nums.get(mid); // current value

		// narrow the range
		if (curval < lbound) { // go bigger
			lo = mid + 1;
			return bSearchGTMod(nums, lbound, hi, lo);
		} else if (curval > lbound) { // go smaller
			hi = mid; 
			return bSearchGTMod(nums, lbound, hi, lo);
		} else {
			hi = mid;
			//hi = mid+1;

			return bSearchGTMod(nums, lbound, hi, lo);
			/*
			while (curval == reach(mid-1)) 
				mid--;
			 */
			// equals --> desired result
		}

	}
	public static int findFirst(ArrayList<Integer> neigh, int curxy, int dir) { // returns the first index in neigh
		int ind;
		if (dir == 0 || dir == 1) {
			ind = bSearchGTMod(neigh, curxy, 0, neigh.size()-1);
		} else {
			ind = bSearchLTMod(neigh, curxy, 0, neigh.size()-1);
		}
		System.out.println("first neigh: " + ind);

		return ind;
	}
	
	public static void dpBFS(int curPost, int[] dir, int count, boolean origin) {
		// function will only be called if state is valid 
		// dir = 3 element array where 0 = pass, 1 = /, 2 = \
		// if origin, then it's a 4 element array
		// traces the current post and calls subsequent states

		System.out.println("--{-func--curpost: " + curPost + " count: " + count);

		int x = posts[curPost][0];
		int y = posts[curPost][1];

		int ogcount = count; // keep track of the original count

		for (int i = 0; i < dir.length; i++) { // loop through the directions

			count = ogcount;

			// make sure this state hasn't been done before
			if (steps[curPost][dir[i]] > -1) { 
				// if the state has been done before
				if (count >= steps[curPost][dir[i]]) { 
					// if this one is less efficient
					System.out.println("skip");
					continue; // don't go through it
				} else { // otherwise, re assign and then go through it
					steps[curPost][dir[i]] = count;
				}
			} else { // assign the value, go through
				steps[curPost][dir[i]] = count;
			}

			System.out.println("  Cp!: " + curPost + " dir: " + dir[i] + " cnt: " + count);

			boolean[][] visited = new boolean[N+2][4];

			boolean cont = false; // if the beam went on somewhere else, or just died

			if (dir[i] == 0) { // north (up)
				// get northern neighbors, loop through indices with the same x
				ArrayList<Integer> neigh = XV.get(x);
				
				if (neigh.size() <= 1) {
					System.out.println("--dead end");
					return;
				} else {
					
					System.out.println("  neigh: " + neigh);
					int first = findFirst(neigh, y, dir[i]);
					if (first != -1 && first < neigh.size()) {
						for (int j = first; j < neigh.size(); j++) {
							if (curPost == neigh.get(j) || 
									posts[neigh.get(j)][1] < y || visited[neigh.get(j)][dir[i]]) {
								System.out.println("continue");
								continue;
							}

							// neigh.get(j) is index of a potential reachable post
							System.out.println("  cp: " + curPost + " neigh: " + neigh.get(j) + " cnt: " + count);

							// if above current post and not yet true
							// mark DP array true and call recursively
							cont = true;
							visited[neigh.get(j)][dir[i]] = true;

							if (neigh.get(j) == N+1) {
								if (i != 0 && !origin) {
									System.out.println("    increment bc i != 0");
									System.out.println("    Success! count: " + count+1);
									if (count + 1 < mincnt) mincnt = count + 1;
								} else {
									System.out.println("    PASS by, no inc");
									System.out.println("    Success! count: " + count);
									if (count < mincnt) mincnt = count;
								}
								System.out.println("--}");
								return;
							} else if (i==0 || origin){
								dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 


							} else {
								System.out.println("HERE");
								dpBFS(neigh.get(j), calcDir(dir[i]), count+1, false); 

							}
						}
					}

				}

			} else if (dir[i] == 1) { // east (right)

				// get eastern neighbors, loop through indices with same y
				ArrayList<Integer> neigh = YV.get(y);
				if (neigh.size() > 1) {
					System.out.println("  neigh: " + neigh);

					int first = findFirst(neigh, x, dir[i]);
					if (first != -1 && first < neigh.size()) {
						for (int j = first; j < neigh.size(); j++) {
							if (curPost == neigh.get(j) || posts[neigh.get(j)][0] < x ||
									visited[neigh.get(j)][dir[i]]) continue;
							System.out.println("  cp: " + curPost + " neigh: " + neigh.get(j) + " cnt: " + count);
							// neigh.get(j) is index of a potential reachable post

							// if the post is east of cur and not yet true, call rec

							cont = true;
							visited[neigh.get(j)][dir[i]] = true;
							if (neigh.get(j) == N+1) {
								if (i != 0 && !origin) {
									System.out.println("    increment bc i != 0");
									System.out.println("    Success! count: " + count+1);
									if (count + 1 < mincnt) mincnt = count + 1;
								} else {
									System.out.println("    PASS by, no inc");
									System.out.println("    Success! count: " + count);
									if (count < mincnt) mincnt = count;
								}
								System.out.println("--}");
								return;
							} else if (i==0 || origin){
								dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 


							} else {
								System.out.println("HERE");
								dpBFS(neigh.get(j), calcDir(dir[i]), count+1, false); 

							}

						}
					}
				}

			} else if (dir[i] == 2) { // south (down)
				// get southern neighbors, loop through indices with same x

				ArrayList<Integer> neigh = XV.get(x);

				if (neigh.size() > 1) {
					System.out.println("  neigh: " + neigh);

					int first = findFirst(neigh, y, dir[i]);
					if (first != -1 && first < neigh.size()) {
						for (int j = first; j < neigh.size(); j++) {
							if (curPost == neigh.get(j) || 
									posts[neigh.get(j)][1] > y || visited[neigh.get(j)][dir[i]]) continue;
							// neigh.get(j) is index of a potential reachable post
							System.out.println("  cp: " + curPost + " neigh: " + neigh.get(j) + " cnt: " + count);
							// if below current post

							// mark DP array true and call recursively
							cont = true;
							visited[neigh.get(j)][dir[i]] = true;

							if (neigh.get(j) == N+1) {
								if (i != 0 && !origin) {
									System.out.println("    increment bc i != 0");
									System.out.println("    Success! count: " + count+1);
									if (count + 1 < mincnt) mincnt = count + 1;
								} else {
									System.out.println("    PASS by, no inc");
									System.out.println("    Success! count: " + count);
									if (count < mincnt) mincnt = count;
								}
								System.out.println("--}");
								return;
							} else if (i==0 || origin){
								dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 


							} else {
								System.out.println("HERE");
								dpBFS(neigh.get(j), calcDir(dir[i]), count+1, false); 

							}
						}
					}
				}

			} else { // west (left)
				// get western neighbors, loop through indices with same y
				ArrayList<Integer> neigh = YV.get(y);

				if (neigh.size() > 1) {
					System.out.println("  neigh: " + neigh);
					int first = findFirst(neigh, x, dir[i]);
					if (first != -1 && first < neigh.size()) {
						for (int j = first; j < neigh.size(); j++) {
							if (curPost == neigh.get(j) || 
									posts[neigh.get(j)][0] > x || visited[neigh.get(j)][dir[i]]) continue;
							System.out.println("  cp: " + curPost + " neigh: " + neigh.get(j) + " cnt: " + count);
							// neigh.get(j) is index of a potential reachable post

							// if the post is west of cur, mark true and call rec
							cont = true;
							visited[neigh.get(j)][dir[i]] = true;
							if (neigh.get(j) == N+1) {
								if (i != 0 && !origin) {
									System.out.println("    increment bc i != 0");
									System.out.println("    Success! count: " + count+1);
									if (count + 1 < mincnt) mincnt = count + 1;
								} else {
									System.out.println("    PASS by, no inc");
									System.out.println("    Success! count: " + count);
									if (count < mincnt) mincnt = count;
								}
								System.out.println("--}");
								return;
							} else if (i==0 || origin){
								dpBFS(neigh.get(j), calcDir(dir[i]), count, false); 


							} else {
								System.out.println("HERE");


								dpBFS(neigh.get(j), calcDir(dir[i]), count+1, false); 

							}

						}
					}

				}


				if (!cont) {
					// if the beam didn't go on somewhere
					System.out.println("  -dead end");
				}
			}

			// take the smallest of the possible counts that reach the barn
			// if it doesn't reach the barn


			System.out.println("--}");
			return;

		}



	}
}
