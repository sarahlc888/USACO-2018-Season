import java.io.*;
import java.util.*;
/*
 * 10/10
 * 
 * directed graph
 * find sum of edges in an eulerian cycle 
 * 
 * eulerian cycle criteria:
 * - weakly connected, needs to be strongly connected
 * - indegree and outdegree of every vertex is the same
 * 
 * 
 * build extra edges. length of pos[i]-pos[j]
 * IDEA
 * 
 * O(E)
 * for each node, see whether indegree is bigger than outdegree or vice versa.
 * if equal, don't do anything.
 * Otherwise, you need to add a edge going in or out of the node
 * 
 * total indegree - outdegree always == 0
 * 
 * keep lists: node that need edges going in, keep multiple versions inside if it needs mult edges
 * the other list of nodes that need edges going out
 * 
 * O(N)
 * get locations of the node ids, map them and replace the node ids in the list
 * 
 * O(N log N) bc sorting
 * pair them so the abs value is greedy
 * sort them in ascending order, match them one to one (greedy)
 * map smaller to smaller, bigger to bigger. Otherwise, there will be "overlap"
 * if you drew the connections on a number line
 * 
 * 
 * O(N log N) overall
 */
public class WaterSlides {
	static int N;
	static int M;
	static int[] pos; // stores x positions of the nodes
	static int[] deg;
	
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		N = Integer.parseInt(st.nextToken()); // number of nodes
		M = Integer.parseInt(st.nextToken()); // number of edges
		
		pos = new int[N];
		deg = new int[N]; // keep track of degree (+ for in, - for out)
		
		for (int i = 0; i < N; i++) {  // scan in x positions
			pos[i] = Integer.parseInt(scan.nextLine()); 
		}
		for (int i = 0; i < M; i++) { // scan in edges
			st = new StringTokenizer(scan.nextLine());
			int start = Integer.parseInt(st.nextToken())-1; // fix indexing
			int end = Integer.parseInt(st.nextToken())-1; // fix indexing
			// update degree array
			deg[start]--;
			deg[end]++;
		}
		
		// ArrayLists for what nodes need more edges in and out
		ArrayList<Integer> needIn = new ArrayList<Integer>();
		ArrayList<Integer> needOut = new ArrayList<Integer>();
		
		// loop through degree array and fill needIn and needOut
		for (int i = 0; i < N; i++) {
			if (deg[i] == 0) continue; // if indegrees = outdegrees, do nothing
			
			if (deg[i] < 0) { // outdegrees > indegrees, need more indegrees
				while (deg[i] < 0) {
					needIn.add(i);
					deg[i]++;
				}
			} else { // indegrees > outdegrees
				while (deg[i] > 0) {
					needOut.add(i);
					deg[i]--;
				}
			}
		}
		
		// replace the ids in need arraylists with positions
		// loop through needIn and needOut. they should be the same size
		for (int i = 0; i < needIn.size(); i++) {
			// replace the ID with the position
			needIn.set(i, pos[needIn.get(i)]);
			needOut.set(i, pos[needOut.get(i)]);
		}
		// sort need lists
		Collections.sort(needIn);
		Collections.sort(needOut);
		
		int sum = 0;
		
		// greedy matchup
		for (int i = 0; i < needIn.size(); i++) {
			sum += Math.abs(needIn.get(i) - needOut.get(i));
		}
		
		System.out.println(sum);
		
	}
}
