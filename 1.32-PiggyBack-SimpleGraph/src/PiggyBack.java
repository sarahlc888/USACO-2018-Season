import java.io.*;
import java.util.*;

// 0...N-1 indexing
// not full test cases - about half

public class PiggyBack {
	
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
		
		connect = new Neighbors[N]; // array of lists of neighbors
		for (int i = 0; i < N; i++) {
			connect[i] = new Neighbors();
		}
		for (int i = 0; i < M; i++) { // scan in all the neighbors
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			connect[x].nbs.add(y);
			connect[y].nbs.add(x);
		}
		
		br.close();
		
		// loop through all possible meeting points K (where B & E meet)
		
		int[] s0 = sDist(0);
		int[] s1 = sDist(1);
		int[] sN = sDist(N-1);
		
		int min = Integer.MAX_VALUE;

		for (int k = 0; k < N; k++) {
			// merging point can any field on the farm
			// including the last field (effectively don't merge ever)
			// or field 0 or 1 (one cow goes directly to the other's field)
			
			int tot = s0[k]*B + s1[k]*E + sN[k]*P;
			if (tot < min) {
				min = tot;
				System.out.println(k);
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
		
		int[] steps = new int[N]; // number of steps from field A to field i
		LinkedList<Integer> LL = new LinkedList<Integer>(); // to visit
		LL.add(A);
		boolean[] hasBeenVisited = new boolean[N];
		
		while (!LL.isEmpty()) {
			int cur = LL.removeFirst(); // get a field out
			
			for (int nb : connect[cur].nbs) {
				// gets all neighbors of cur (out of nbs)
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
