import java.io.*;
import java.util.*;

public class Kosarajus {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("scc.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // nodes
		int M = Integer.parseInt(st.nextToken()); // edges
		
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(); // graph
		for (int i = 0; i < N; i++) adj.add(new ArrayList<Integer>());
		
		for (int i = 0; i < M; i++) { // scan everything in, adjust indexing
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; 
			int b = Integer.parseInt(st.nextToken()) - 1;
			adj.get(a).add(b);
		}
		br.close();
		
		System.out.println(adj);
		

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("scc.out")));

		pw.println();
		pw.close();
	}
}
