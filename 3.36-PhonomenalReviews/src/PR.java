import java.io.*;
import java.util.*;
/*
 * Phonomenal Reviews, 12/4/18 lesson, written on 12/9
 * 
 * 
 */
public class PR {
	static int N;
	static int M;
	static int[] rootDist;
	static int[] par;
	static boolean[] isLeaf;
	static int root;
	static boolean[] isPho;
	static int[] numChilds;
	
	static ArrayList<ArrayList<Integer>> adj;
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("testData/1.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // total restaurants
		M = Integer.parseInt(st.nextToken()); // pho restaurants
		isPho = new boolean[N];
		int[] pho = new int[M];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) { // pho restaurants 
			pho[i] = Integer.parseInt(st.nextToken());
			isPho[pho[i]] = true;
		}
		adj = new ArrayList<ArrayList<Integer>>(); // graph
		for (int i = 0; i < N; i++)
			adj.add(new ArrayList<Integer>());
		for (int i = 0; i < N-1; i++) { // paths
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			adj.get(a).add(b);
			adj.get(b).add(a);
		}
		br.close();

		root = pho[0];
//		System.out.println("ROOT: " + root);
		buildTree(root); // arbitrary root
		pruneNonPho();
		
		// count the actual nodes after pruning
		int activeNodes = 0;
		for (int i = 0; i < N; i++) {
			if (par[i] >= 0) activeNodes++;
		}
//		System.out.println(Arrays.toString(par));

		// store children
		adj = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < N; i++)
			adj.add(new ArrayList<Integer>());
		for (int i = 0; i < N; i++) {
			int curNode = i;
			int curPar = par[curNode];
			if (curPar < 0) continue; // invalid
			adj.get(curPar).add(curNode); 
			adj.get(curNode).add(curPar); 
		}
//		System.out.print("CHILDREN ");
//		for (int i = 0; i < N; i++) System.out.print(adj.get(i) + " ");
//		System.out.println();
		for (int i = 0; i < N; i++) {
			if (isLeaf[i] && !isPho[i]) System.out.println("something is wrong"); 
		}
		
		// find the leaf farthest from the root
		rootDist = new int[N];
		for (int i = 0; i < N; i++) rootDist[i] = -1;
		rootDist[root] = 0;
//		System.out.println("finding anchor1");
		int anchor1 = farLeaf(); // farthest leaf root, one side of the backbone (which will be travelled only once)
//		System.out.println("anchor1: " + anchor1);
		
		// reroot the tree at anchor1, find farthest leaf from new root, that is the backbone
//		System.out.println();
		root = anchor1;
//		System.out.println("ROOT: " + root);
		buildTree(root);
//		System.out.println(Arrays.toString(par));
//		System.out.println("finding anchor2");
		rootDist = new int[N];
		for (int i = 0; i < N; i++) rootDist[i] = -1;
		rootDist[root] = 0;
		int anchor2 = farLeaf();
//		System.out.println("anchors2: " + anchor2);
		
		int backbone = dist(anchor2);
		System.out.println(backbone+2*(activeNodes-backbone));
		
		for (int i = 0; i < N; i++) rootDist[i] = -1;
		rootDist[anchor1] = 0;
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pho.out")));

		pw.println();
		pw.close();
	}
	public static int farLeaf() {
		// returns the leaf farthest from the root
		int ret = root;
		int retDist = 0;
		for (int i = 0; i < N; i++) {
			if (isLeaf[i]) {
				int curDist = dist(i);
//				System.out.println("  i: " + i + " " + curDist);

				if (curDist > retDist) {
					retDist = curDist;
					ret = i;
				}
			}
		}
		return ret;
	}
	public static void buildTree(int root) {
		// construct the tree (N nodes, N-1 paths)
		par = new int[N]; // every node has 1 parent
		Arrays.fill(par, -2);
		par[root] = -1;
		isLeaf = new boolean[N]; // true if node i is a leaf
		numChilds = new int[N]; // number of children of each node

		boolean[] visited = new boolean[N]; // visited array
		LinkedList<Integer> toVisit = new LinkedList<Integer>(); // toVisit
		toVisit.add(root);
		visited[root] = true;

		int phoCount = 0; // number of pho restaurants

		while (!toVisit.isEmpty()) { // BFS to construct the tree
			int curNode = toVisit.poll();
			int numChildren = 0;

			for (int i = 0; i < adj.get(curNode).size(); i++) {
				int nextNode = adj.get(curNode).get(i);
				if (!visited[nextNode]) {
					numChildren++;
					visited[nextNode] = true;
					toVisit.add(nextNode);
					par[nextNode] = curNode;

					if (isPho[nextNode]) {
						phoCount++;
					}
					if (phoCount == M) { // if all pho restaurants are in the tree already
						toVisit = new LinkedList<Integer>();
						break;
					}
				}
			}
			numChilds[curNode] = numChildren;
			if (numChildren == 0) {
				isLeaf[curNode] = true;
			}
		}

	}
	public static void pruneNonPho() {
		// get rid of non-pho restaurants that are leaves
		for (int i = 0; i < N; i++) {
			int curNode = i;
			while (isLeaf[curNode] && !isPho[curNode] && par[curNode] >= 0) {
				int curPar = par[curNode];
				// cut off the leaf
				isLeaf[curNode] = false;
				par[curNode] = -2; 
				// check if parent is new leaf
				numChilds[curPar]--;
				if (numChilds[curPar] == 0) { // no other children, it's a leaf
					isLeaf[curPar] = true; 
					curNode = curPar;
				}
			}
		}
	}
	public static int dist(int curNode) {
		if (curNode < 0) return 0; // not valid node
		if (rootDist[curNode] >= 0) {
			return rootDist[curNode];
		}
		return 1+dist(par[curNode]);
	}


}
