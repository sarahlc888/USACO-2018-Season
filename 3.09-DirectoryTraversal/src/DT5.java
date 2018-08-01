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
 * 10/10 test cases
 * fixed us typos
 */
public class DT5 {
	static int N;
	static ArrayList<ArrayList<Integer>> adj;
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
		
		filecount = 0; // total files
		isFile = new boolean[N]; // if cur node is a file
		nodes = new String[N]; // names of nodes
		
		adj = new ArrayList<ArrayList<Integer>>(); // adj list for tree
		par = new int[N]; // par[i] = parent of i
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Integer>());
			par[i] = -1; // mark as uninitialized
			us[i] = ds[i] = -1l; // mark as uninitialized
		}
		for (int i = 0; i < N; i++) { // scan in each file or dir
			StringTokenizer st = new StringTokenizer(br.readLine());
			nodes[i] = st.nextToken(); // name
			int numc = Integer.parseInt(st.nextToken()); // num children inside
			if (numc == 0) { // mark i as a file
				isFile[i] = true;
				numFiles[i] = 1;
				filecount++;
			}
			for (int j = 0; j < numc; j++) { // add children to the tree
				int child = Integer.parseInt(st.nextToken()) - 1; // to fix indexing 
				adj.get(child).add(i); 
				adj.get(i).add(child);
				par[child] = i;
			}
		}
		br.close();
				
		// calculate down sums
		ds(0); 

		
		// calculate upsums
		us(0, 0);
//		System.out.println("adj: " + adj);
//		System.out.println("num files: " + Arrays.toString(numFiles));
//		System.out.println("ds: " + Arrays.toString(ds));
//		System.out.println("us: " + Arrays.toString(us));
		
		long minval = Long.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			if (isFile[i]) continue;
			long cur = ds[i] + us[i] - filecount;
			minval = Math.min(minval, cur);
		}
//		System.out.println(minval);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
		pw.println(minval);
		pw.close();
	}
	public static long ds(int u) { // calculate dist to all leaves in subtree of u
		if (ds[u] != -1) return ds[u]; // memoization
		// calculate downsum
		ds[u] = 0; 
		for (int i = 0; i < adj.get(u).size(); i++) { // loop through children
			int child = adj.get(u).get(i);
			if (par[u] == child) continue; 
			
			// (ds of children + dist from child to u * num files)
			long cost = nodes[child].length()+1; // dist from u to child
			long add = ds(child) + cost * numFiles[child];
			ds[u] += add; // update ds 
			
			numFiles[u] += numFiles[child]; // update number of files in subtree
		}
		return ds[u];
	}
	public static long us(int u, int curpar) { // calculate dist to all leaves not in subtree of u
		if (us[u] != -1) return us[u]; // memoize
		if (u == 0) {
			us[u] = 0;
		} else {
			// calc upsum
			us[u] = 0;
			// us(u) = ds(par) - ds(u) + us(par)
			us[u] = ds[curpar]-ds[u];
//			System.out.println("u: " + u + " part1 us: " + us[u]);
			us[u] -= (numFiles[u]) * (nodes[u].length()+1);
//			System.out.println("u: " + u + " part2 us: " + us[u]);
			us[u] += (numFiles[curpar] - numFiles[u]) * 3; // add back the connection from cur to par
//			System.out.println("u: " + u + " part3 us: " + us[u]);
			us[u] += us(curpar, par[curpar]);
			us[u] += 3*(filecount-numFiles[curpar]); // account for path from cur to par
//			System.out.println("u: " + u + " us: " + us[u]);
		}
		
		
		for (int i = 0; i < adj.get(u).size(); i++) { // loop through children
			int child = adj.get(u).get(i);
			if (isFile[child]) continue; // don't calc for the children
			us(child, u);
		}
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
