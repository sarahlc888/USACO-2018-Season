import java.io.*;
import java.util.*;
/*
 * USACO 2015 US Open, Silver
 * Problem 3. Bessie's Birthday Buffet
 * 
 * 1 hr, came up with idea all by myself!
 */
public class BBB {
	static int N;
	static long E;
	static Node[] field;
	static Node[] sortedField;
	static long[][] dist;
	
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("buffet.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // nodes
		E = Long.parseLong(st.nextToken()); // edgeweight
		
		field = new Node[N];
		
		for (int i = 0; i < N; i++) { // scan in the nodes
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); // grass
			int b = Integer.parseInt(st.nextToken()); // num neighbors
			field[i] = new Node(a, b, i);
			for (int j = 0; j < b; j++) { // scan in edges
				int c = Integer.parseInt(st.nextToken())-1; // adjust indexing
				field[i].edges[j] = c;
			}
		}
		br.close();
		sortedField = field.clone();
		
		Arrays.sort(sortedField);
//		System.out.println("field");
//		System.out.println(Arrays.toString(field));
//		System.out.println(Arrays.toString(sortedField));
		
		dist = new long[N][N]; // dist from i to j
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dist[i][j] = -1;
			}
		}
		for (int i = 0; i < N; i++) {
			BFS(i);
		}
//		System.out.println("dist");
//		for (int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(dist[i]));
//		}
		
		// DP using sortedField (go from smallest to biggest quality)
		long[] DP = new long[N]; // DP[i] = maxEnergy after eating at ind i
		for (int i = 0; i < N; i++) { 
			DP[i] = sortedField[i].grass; // basecase: start and end at i
		}
		long maxVal = 0;
		for (int i = 0; i < N; i++) { 
			for (int j = 0; j < i; j++) { // can previously eat at 0...i-1
				Node curGrass = sortedField[i];
				Node prevGrass = sortedField[j];
	
				long curDist = dist[prevGrass.id][curGrass.id];
				if (curDist == -1) continue; // skip if you cannot travel from prev to cur
				
				long val = DP[j]+curGrass.grass-E*curDist;
//				System.out.println("i: " + i + " j: " + j + " val: " + val);
				
				DP[i] = Math.max(DP[i], val);
			}
			maxVal = Math.max(DP[i], maxVal);
		}
//		System.out.println(maxVal);
//		System.out.println(Arrays.toString(DP));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("buffet.out")));

		pw.println(maxVal);
		pw.close();
	}
	public static void BFS(int startInd) {
		boolean[] visited = new boolean[N];
		LinkedList<Pair> toVisit = new LinkedList<Pair>(); // (ind, dist from og)
		toVisit.add(new Pair(startInd, 0));
		visited[startInd] = true;
		while (!toVisit.isEmpty()) {
			Pair curPair = toVisit.poll();
			dist[startInd][curPair.x] = curPair.y;
			Node c = field[curPair.x];
			for (int i = 0; i < c.numEdges; i++) {
				int nextInd = c.edges[i];
				if (!visited[nextInd]) {
					visited[nextInd] = true;
					toVisit.add(new Pair(nextInd, curPair.y+1));
				}
			}
		}
	}
	public static class Node implements Comparable<Node> {
		int id;
		int grass;
		int numEdges;
		int[] edges;

		public Node(int a, int b, int c) {
			grass = a;
			numEdges = b;
			edges = new int[numEdges];
			id = c;
		}
		@Override
		public int compareTo(Node o) { // sort by grass
			return grass-o.grass;
		}
		public String toString() {
			return grass+"";
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
