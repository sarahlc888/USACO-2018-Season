import java.io.*;
import java.util.*;

/*
 * USACO 2015 January Contest, Silver
 * Problem 2. Cow Routing
 * 
 * 12/12 cases 2 hours
 * alternative idea (a little more complicated) but came up with idea early on, thought it wouldn't work
 */
public class CowRouting {
	static HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	static Route[] routes;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowroute.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int A = Integer.parseInt(st.nextToken())-1; // source
		int B = Integer.parseInt(st.nextToken())-1; // dest
		int N = Integer.parseInt(st.nextToken()); // num routes
		
		int maxCity = Math.max(A, B);
		
		routes = new Route[N];
		for (int i = 0; i < N; i++) { // routes
			st = new StringTokenizer(br.readLine());
			long cost = Long.parseLong(st.nextToken());
			int length = Integer.parseInt(st.nextToken());
			
			routes[i] = new Route(cost, length, i);
			
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < length; j++) {
				int curCity = Integer.parseInt(st.nextToken())-1;
				maxCity = Math.max(curCity, maxCity);
				// add city to route
				routes[i].cities[j] = curCity; 
				// map city to route
				if (!map.containsKey(curCity)) map.put(curCity, new ArrayList<Integer>());
				map.get(curCity).add(i);
			}
		}
		br.close();

//		System.out.println(Arrays.toString(routes));
//		System.out.println(map);
		
		// dijkstra
		dist = new LongPair[maxCity+1]; 
		for (int i = 0; i < dist.length; i++) {
			dist[i] = new LongPair(Long.MAX_VALUE, Long.MAX_VALUE);
		}
		visited = new boolean[maxCity+1];
		dij(A);
//		System.out.println(Arrays.toString(dist));
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowroute.out")));

		if (dist[B].cost == Long.MAX_VALUE) {
			pw.println("-1 -1");
//			System.out.println("-1 -1");
		}
		else {
			pw.println(dist[B]);
//			System.out.println(dist[B]);
		}
		pw.close();
	}
	static LongPair[] dist;
	static boolean[] visited; // visited for the dij
	public static void dij(int S) { // S = starting node
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		// add the edges that cross from S to VS
		pq.add(new Pair(new LongPair(0, 0), S, -1)); // weights, destination, prevRoute
		while (!pq.isEmpty()) {
//			System.out.println(pq);
			//System.out.println(Arrays.toString(dist));
			// let (weight, node) be at top
			Pair p = pq.poll();
			long weight = p.x.cost;
			long stops = p.x.stops;
			int node = p.destNote;
			
			if (visited[node]) continue; // if already visited, don't add it
			else visited[node] = true; // mark visited and proceed
			
			dist[node].cost = weight; // assign weight
			dist[node].stops = stops; // track stops
//			System.out.println("  pair: " + p + " prevRt: " + p.prevRoute);
//			System.out.println("  assign: " + node + " weight: " + weight);
			
			// look at the neighbors and push to pq
			if (!map.containsKey(node)) continue; // no neighbors
			for (int i = 0; i < map.get(node).size(); i++) {
				int curRouteInd = map.get(node).get(i);
				Route curRoute = routes[curRouteInd];
				
				int nodeFound = -1;
				
				for (int j = 0; j < curRoute.cities.length; j++) {
					int nextCity = curRoute.cities[j];
					
					if (nextCity == node) {
						nodeFound = j;
						continue;
					}
					if (nodeFound == -1) continue;
					
					if (curRouteInd == p.prevRoute) { // same route as last time, no extra cost
						pq.add(new Pair(new LongPair(dist[node].cost, stops+j-nodeFound), nextCity, curRouteInd));
					} else { // new route
						pq.add(new Pair(new LongPair(curRoute.cost + dist[node].cost, stops+j-nodeFound), nextCity, curRouteInd)); // add to PQ
					}
				}
			}
		}
	}
	public static class Route {
		long cost;
		int length;
		int[] cities;
		int id;

		public Route(long a, int b, int c) {
			cost = a;
			length = b;
			cities = new int[b];
			id = c;
		}
		public String toString() {
			return cost + " " + Arrays.toString(cities);
		}
	}
	public static class LongPair {
		long cost;
		long stops;
		public LongPair(long a, long b) {
			cost = a;
			stops = b;
		}
		public String toString() {
			return cost + " " + stops;
		}
	}
	public static class Pair implements Comparable<Pair> {
		LongPair x;
		int destNote;
		int prevRoute;
		public Pair(LongPair a, int b) {
			x = a;
			destNote = b;
		}
		public Pair(LongPair a, int b, int c) {
			x = a;
			destNote = b;
			prevRoute = c;
		}
		@Override
		public int compareTo(Pair o) { // sort by x cost, then x stops, then y
			if (x.cost < o.x.cost) return -1;
			if (x.cost > o.x.cost) return 1;
			if (x.stops < o.x.stops) return -1;
			if (x.stops > o.x.stops) return 1;
			
			return destNote-o.destNote;
		}
		public String toString() {
			return x + " dest: " + destNote;
		}
	}


}
