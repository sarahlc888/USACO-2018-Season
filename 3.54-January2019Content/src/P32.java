import java.io.*;
import java.util.*;

/*
 * ARCHIVE, SOME CASES RIGHT
 */
public class P32 {
	static ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>(); // (weight, dest)
	static HashMap<Integer, ArrayList<Integer>> paths = new HashMap<Integer, ArrayList<Integer>>(); // (weight, dest)
	static int[] pars;
	static int[] dist;
	static boolean[] visited; // visited for the dij
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("shortcut.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int T = Integer.parseInt(st.nextToken());
		
		pars = new int[N];
		Arrays.fill(pars, -1);
		
		int[] numcows = new int[N]; // num cows at each node
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			numcows[i] = Integer.parseInt(st.nextToken());
			adj.add(new ArrayList<Pair>());
		}
		for (int i = 0; i < M; i++) { // paths
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken());
			adj.get(a).add(new Pair(c, b));
			adj.get(b).add(new Pair(c, a));
		}
		br.close();
		
		visited = new boolean[N];
		dist = new int[N]; // distance array from source to i
		Arrays.fill(dist, Integer.MAX_VALUE);
		dij(0); // get initial distances and paths
		
//		System.out.println(Arrays.toString(pars));
//		System.out.println(Arrays.toString(dist));
		
		// trace the tree
		LinkedList<State> toVisit = new LinkedList<State>();
		ArrayList<Integer> emptyAL = new ArrayList<Integer>();
		toVisit.add(new State(0, emptyAL));
		visited = new boolean[N];
		visited[0] = true;
		
		ArrayList<ArrayList<Integer>> treeAdj = new ArrayList<ArrayList<Integer>>(); // (weight, dest)
		for (int i = 0; i < N; i++) { // convert from par to children
			treeAdj.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < pars.length; i++) {
			int node = i;
			int par = pars[i];
			if (node == par) continue; // weird exception
			treeAdj.get(par).add(node);
		}
//		System.out.println(treeAdj);
		
		while (!toVisit.isEmpty()) {
			State curState = toVisit.removeFirst();
			ArrayList<Integer> children = treeAdj.get(curState.curnode);
			for (int i = 0; i < children.size(); i++) {
				int curChild = children.get(i); // get the child
				if (!visited[curChild]) { // if not visited, visit it
					ArrayList<Integer> curPath = (ArrayList<Integer>) curState.path.clone();
					curPath.add(curState.curnode);
					toVisit.add(new State(curChild, curPath));
					ArrayList<Integer> curPath2 = (ArrayList<Integer>) curPath.clone();
					curPath2.add(curChild);
					paths.put(curChild, curPath2);
				}
			}
		}
//		System.out.println("paths: " + paths);
		
		// loop through all paths and find the most heavily weighted node
		// weight = sum of all cows and their weights
		
		// not yet weighted
		int[] timesVisited = new int[N]; // find this for each node
		Iterator it = paths.keySet().iterator();
		while (it.hasNext()) {
			int curNode = (int) it.next();
			ArrayList<Integer> curPath = paths.get(curNode);
			for (int i = 0; i < curPath.size(); i++) {
				timesVisited[curPath.get(i)] += numcows[curNode];
			}
		}
		
		// find the most heavily used node
		int maxDiff = 0;
		int maxInd = -1;
		for (int i = 1; i < N; i++) {
			int diff = timesVisited[i]*dist[i] - timesVisited[i]*T; // build a bridge everywhere
			
			if (diff > maxDiff) {
				maxDiff = diff;
				maxInd = i;
			}
		}
//		timesVisited[maxInd]*T // (new "cost")
//		timesVisited[maxInd]*dist[maxInd] // (old "cost")
//		System.out.println(maxInd);
		
//		System.out.println("DIFF: " + maxDiff);
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));

		pw.println(maxDiff);
		pw.close();
	}
	public static void dij(int S) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(0, S)); // weight, destination
		while (!pq.isEmpty()) {
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
			int weight = p.weight;
			int node = p.dest;
			
			if (visited[node]) {
				continue; // if already visited, don't add it
			} else {
				visited[node] = true; // mark visited and proceed
			}
			
			dist[node] = weight; // assign weight
//			pars.put(node, p.parent);
			pars[node] = p.parent;
			
//			ArrayList<Integer> newPars = (ArrayList<Integer>) p.parent.clone();
//			newPars.add(node);
			// look at the neighbors and push to pq
			for (int i = 0; i < adj.get(node).size(); i++) {
				Pair neigh = adj.get(node).get(i); // neighbor
				// add to PQ
				// add node as a parent
				pq.add(new Pair(neigh.weight + dist[node], neigh.dest, node)); 
			}
		}
	}
	public static class State {
		int curnode;
		ArrayList<Integer> path;
		public State(int a, ArrayList<Integer> b) {
			curnode = a;
			path = b;
		}
	}
	public static class Pair implements Comparable<Pair> {
		int weight; // weight
		int dest; // dest
//		ArrayList<Integer> parent = new ArrayList<Integer>(); // the path so far
		int parent;
		public Pair(int a, int b) {
			weight = a;
			dest = b;
		}
		public Pair(int a, int b, int c) { // add something to the path
			weight = a;
			dest = b;
			parent = c;
		}
		@Override
		public int compareTo(Pair o) { // sort by edge
			if (weight != o.weight) return weight-o.weight;
//			for (int i = 0; i < (int)Math.min(o.parent.size(), parent.size()); i++) {
//			for (int i = (int)Math.min(o.parent.size(), parent.size())-1; i >= 0; i--) {
//				if (parent.get(i) < o.parent.get(i)) return -1;
//				if (parent.get(i) > o.parent.get(i)) return 1;
//			}
//			return 0;
			return parent-o.parent;
		}
		
		
	}

}
