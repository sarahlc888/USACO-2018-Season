import java.io.*;
import java.util.*;
/*
 * USACO 2018 February Contest, Gold
 * Problem 2. Directory Traversal
 * 
 * 7/31 lesson
 * 
 * based off of the solution
 * - make tree tree
 * - calculate downsums as sum of downsums below, adjusted
 * - calculate upsums as ds(par)-ds(cur)+us(par), adjusted
 * 
 * 2/10 test cases
 * something is clearly wrong
 */
public class DT4 {
	static int N;
	static ArrayList<ArrayList<Pair>> adj;
	static boolean[] isFile;
	static String[] nodes;
	static int filecount;
	static long[] ds;
	static long[] us;
	static int[] par;
	static int[] numFiles;
	
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("dirtraverse.in"));
		N = Integer.parseInt(br.readLine()); // total number of files and dirs

		ds = new long[N]; // downsum (dist to files in subtree)
		us = new long[N]; // upsum (dist to other files)
		numFiles = new int[N]; // num files in subtree of i
		
		filecount = 0;
		isFile = new boolean[N]; // if false, then it's a dir
		nodes = new String[N];
		
		// set up graph/tree
		adj = new ArrayList<ArrayList<Pair>>(); // (dest, weight)
		par = new int[N]; // par[i] = parent of i
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Pair>());
			par[i] = -1; // mark as uninitialized
			us[i] = ds[i] = -1l; // mark as uninitialized
		}
		
		for (int i = 0; i < N; i++) { // scan in each file or dir
			StringTokenizer st = new StringTokenizer(br.readLine());
			String name = st.nextToken(); // name
			nodes[i] = name;
			int numc = Integer.parseInt(st.nextToken()); // num children inside
			if (numc == 0) {
				isFile[i] = true;
				filecount++;
				numFiles[i] = 1;
			}
			for (int j = 0; j < numc; j++) {
				int child = Integer.parseInt(st.nextToken()) - 1; // to fix indexing 
				adj.get(child).add(new Pair(i, 3)); // "../"
				adj.get(i).add(new Pair(child, -1)); // not known yet
			}
		}
		br.close();
		
		for (int i = 0; i < N; i++) { // finish assigning weights
			for (int j = 0; j < adj.get(i).size(); j++) {
				if (adj.get(i).get(j).cost == -1) { // if the weight is uninitialized
//					System.out.println("i: " + i + " dest: " + adj.get(i).get(j));
//					System.out.println(nodes[adj.get(i).get(j).dest]);
					adj.get(i).get(j).cost = nodes[adj.get(i).get(j).dest].length()+1; // "name/"
				}
			}
		}
//		System.out.println("adj: " + adj);
		
		// walk down the tree
		LinkedList<Integer> toVisit = new LinkedList<Integer>();
		toVisit.add(0); // root at bessie
		boolean[] visitedT = new boolean[N];
		visitedT[0] = true;
		while (!toVisit.isEmpty()) {
			int i = toVisit.removeFirst();
			for (int j = 0; j < adj.get(i).size(); j++) {
				int child = adj.get(i).get(j).dest;
				
				if (!visitedT[child]) {
					par[child] = i;
					toVisit.add(child);
					visitedT[child] = true;
				}
			}
		}
		
//		System.out.println(adj);
		ds(0);
//		System.out.println("num files: " + Arrays.toString(numFiles));
//		System.out.println("ds: " + Arrays.toString(ds));
		for (int i = 0; i < N; i++) {
			if (!isFile[i]) continue;
			us(i, par[i]);
		}
//		System.out.println("us: " + Arrays.toString(us));
		
		long minval = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			if (isFile[i]) continue;
			long cur = ds[i] + us[i] - filecount;
			minval = Math.min(minval, cur);
		}
		System.out.println(minval);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		pw.println(minval);
		pw.close();
	}
	public static long ds(int u) { // calculate dist to all leaves in subtree of u
		if (ds[u] != -1) return ds[u]; // memoization
		// calculate downsum
		ds[u] = 0; 
		for (int i = 0; i < adj.get(u).size(); i++) { // loop through children
			int child = adj.get(u).get(i).dest;
			if (par[u] == child) continue; 
			
			// (ds of children + dist from child to u * num files)
			long add = ds(child) + adj.get(u).get(i).cost * numFiles[child];
			ds[u] += add; // update ds 
			
			numFiles[u] += numFiles[child]; // update number of files in subtree
		}
		return ds[u];
	}
	public static long us(int u, int curpar) { // calculate dist to all leaves not in subtree of u
		if (us[u] != -1) return us[u]; // memoize
		if (u == 0) {
			us[u] = 0;
//			System.out.println("u: " + u + " us: " + us[u]);
			return us[u];
		}
		// calc upsum
		us[u] = 0;
		// us(u) = ds(par) - ds(u) + us(par)
		us[u] = ds[curpar]-ds[u];
//		System.out.println("u: " + u + " part us: " + us[u]);
		us[u] -= numFiles[u] * (nodes[u].length()+1-3);
//		System.out.println("u: " + u + " part2 us: " + us[u]);
		us[u] += us(curpar, par[curpar]);
		us[u] += 3*(filecount-numFiles[curpar]); // account for path from cur to par
//		System.out.println("u: " + u + " us: " + us[u]);
		return us[u];
	}
	public static class Pair implements Comparable<Pair> {
		int dest;
		int cost;

		public Pair(int a, int b) {
			dest = a;
			cost = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (dest == o.dest) return cost-o.cost;
			return dest-o.dest;
		}
		public String toString() {
			return dest + " " + cost;
		}
	}
}
