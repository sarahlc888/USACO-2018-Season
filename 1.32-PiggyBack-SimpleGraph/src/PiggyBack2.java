import java.io.*;
import java.util.*;

// 11/11 test cases
// 1...N indexing
// issue from version 1: forgot to mark A as hasBeenVisited initially in sDist()
public class PiggyBack2 {
	
	static Neighbors[] connect;
	static int N;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("piggyback.in"));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int B = Integer.parseInt(st.nextToken()); // bessie cost
		int E = Integer.parseInt(st.nextToken()); // elsie cost
		int P = Integer.parseInt(st.nextToken()); // piggyback cost
		N = Integer.parseInt(st.nextToken()); // number of fields
		int M = Integer.parseInt(st.nextToken()); // number of connections
		
		// trying to reach field N
		
		connect = new Neighbors[N+1]; // array of lists of neighbors for field 1...N
		for (int i = 1; i < N+1; i++) {
			connect[i] = new Neighbors();
		}
		for (int i = 0; i < M; i++) { // scan in all the neighbors
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			connect[x].nbs.add(y);
			connect[y].nbs.add(x);
		}
		
		br.close();
		
		// loop through all possible meeting points K (where B & E meet)
		
		int[] s1 = sDist(1);
		int[] s2 = sDist(2);
		int[] sN = sDist(N);
		
		int min = Integer.MAX_VALUE;

		for (int k = 1; k <= N; k++) {
			// merging point can any field on the farm
			// including the last field (effectively don't merge ever)
			// or field 1 or 2 (one cow goes directly to the other's field)
			
			int tot = s1[k]*B + s2[k]*E + sN[k]*P;
			if (tot < min) {
				min = tot;
				//System.out.println(k);
			}
			
		}
		
		System.out.println(min);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("piggyback.out")));

		pw.println(min);
		pw.close();
	}
	public static class Neighbors {
		ArrayList<Integer> nbs;
		public Neighbors() {
			 nbs = new ArrayList<Integer>();
		}
	}
	public static int[] sDist(int A) {
		// returns shortest distance between field A to any other field
		
		int[] steps = new int[N+1]; // number of steps from field A to field i
		LinkedList<Integer> LL = new LinkedList<Integer>(); // to visit
		LL.add(A);
		boolean[] hasBeenVisited = new boolean[N+1];
		hasBeenVisited[A] = true;
		
		while (!LL.isEmpty()) {
			int cur = LL.removeFirst(); // get a field out
			
			for (int nb : connect[cur].nbs) { // gets all neighbors of cur
				if (!hasBeenVisited[nb]) {
					// if the neighbor has not been visited, then process it
					hasBeenVisited[nb] = true; // mark it
					steps[nb] = steps[cur] + 1; // calc steps
					
					LL.add(nb);
				}
			}
		}
		
		return steps;
		
	}

}
