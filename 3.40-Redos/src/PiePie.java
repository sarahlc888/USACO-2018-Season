import java.io.*;
import java.util.*;
/*
 * unfinished
 * USACO 2017 December Contest, Gold
 * Problem 1. A Pie for a Pie
 */
public class PiePie {
	static boolean[] visited;
	static int[] dist;
	static ArrayList<ArrayList<Integer>> adj;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("piepie.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of pies each cow has
		int D = Integer.parseInt(st.nextToken()); // tastiness threshold (diff in tasty <= D to connect)
		
		// indices of pies w/ value 0 when received
		ArrayList<Integer> zeros = new ArrayList<Integer>(); 
		
		Pair[] bpies = new Pair[N]; // 0...N-1
		Pair[] epies = new Pair[N]; // N...2N
		
		// bessie's pies
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			bpies[i] = new Pair(a, b, i);
			if (b == 0) { // if else values bessie's pie as 0
				zeros.add(i);
			}
		}
		// elsie's pies
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			epies[i] = new Pair(a, b, i+N);
			if (a == 0) { // if bessie values elsie's pie as 0
				zeros.add(N+i);
			}
		}
		br.close();
		
//		System.out.println(zeros);

		// sort by value of each
		Arrays.sort(bpies, (a, b)-> a.bval-b.bval);
		Arrays.sort(epies, (a, b)-> a.eval-b.eval);
		
//		System.out.println(Arrays.toString(bpies));
//		System.out.println(Arrays.toString(epies));
		
		// init graph -- put all connections in backward (give a after b? connect b--a)
		adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 2*N; i++) adj.add(new ArrayList<Integer>());
		
		// give bessie's pies to elsie, elsie gives back
		for (int i = 0; i < N; i++) {
			// last elsie pie that is valid to be given back
			int uplim = greatestBelowEval(epies, bpies[i].eval+D);
//			System.out.println("i: " + i + " uplim: " + uplim);
			
			while (uplim >= 0 && epies[uplim].eval >= bpies[i].eval) {
//				System.out.println("  " + epies[j].origInd + "  add: " + bpies[i].origInd);
				adj.get(epies[uplim].origInd).add(bpies[i].origInd); // connect elsie's pie to bessie's given pie
				uplim--;
			}
		}
		// give elsie's pies to bessie, bessie gives back
		for (int i = 0; i < N; i++) {
			int uplim = greatestBelowBval(bpies, epies[i].bval+D);
//			System.out.println("i: " + i + " ulim: " + uplim);
			while (uplim >= 0 && bpies[uplim].bval >= epies[i].bval) {
//				System.out.println("  " + bpies[j].origInd + "  add: " + epies[i].origInd);
				adj.get(bpies[uplim].origInd).add(epies[i].origInd); // connect bessie's pie to elsie's given pie
				uplim--;
			}
		}

		visited = new boolean[2*N];
		dist = new int[2*N];
		Arrays.fill(dist, -1);
		
		dij(zeros);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
		for (int i = 0; i < N; i++) {
			
			if (dist[i] == -1) {
				pw.println(-1);
//				System.out.println(-1);
			} else {
				pw.println(dist[i]+1);
//				System.out.println(dist[i]+1);
			}
			
		}
		
		
		pw.close();
	}
	public static int greatestBelowBval(Pair[] arr, int val) {
		// returns greatest i <= val

//		int lo = -1;
		int lo = 0;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			if (arr[mid].bval <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (arr[hi].bval <= val) return hi;
		return -1;
	}
	public static int greatestBelowEval(Pair[] arr, int val) {
		// returns greatest i <= val

//		int lo = -1;
		int lo = 0;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			if (arr[mid].eval <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		if (arr[hi].eval <= val) return hi;
		return -1;
	}
	
	public static void dij(ArrayList<Integer> S) { // S = starting nodes
		LinkedList<Integer> pq = new LinkedList<Integer>();
		// starting points
		for (int i = 0; i < S.size(); i++) {
			pq.add(S.get(i)); 
			dist[S.get(i)]=0;
			visited[S.get(i)] = true;
		}
		
		while (!pq.isEmpty()) {
			int node = pq.poll();
//			System.out.println("node: " + node);
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				int neigh = adj.get(node).get(i); // neighbor
			
				// if already visited, don't add it
				if (visited[neigh]) continue; 
				
				// otherwise, visit and proceed
				visited[neigh] = true; 
				dist[neigh] = dist[node]+1;
				pq.add(neigh); 
			}
		}
	}
	public static class Pair {
		// tastiness values for bessie and elsie
		int bval;
		int eval;
		int origInd;

		public Pair(int a, int b, int c) {
			bval = a;
			eval = b;
			origInd = c;
		}
		public String toString() {
			return "b: " + bval + " e: " +eval +" ind: " + origInd;
		}
	}


}
