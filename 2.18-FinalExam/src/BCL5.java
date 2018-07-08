import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

// trying a non-DP alternative, 0...N indexing
// 4/10 test cases, optimizations are making it worse

public class BCL5 {
	static Pair p;
	static Pair p2;
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // num cows
		String cows = "";
		for (int i = 1; i <= N; i++) {
			cows += br.readLine().charAt(0);
		}
		
		// DP[i][j] = optimal new string possible using 0...i and j...N-1

		String[] DP1 = new String[N+2];
		String[] DP2 = new String[N+2]; // index i
		
		int i = 0;
		

		DP2[N+1] = "";
	//	DP[0][N+1] = ""; // basecase, redundant
		String best = "a"; // will always come after
		
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		
		p = new Pair(cows.charAt(0) + "", cows.substring(1));
		p2 = new Pair(cows.charAt(N-1) + "", cows.substring(0, N-1));
		
		// add the better one
		if (p.compareTo(p2) < 0) 
			pq.add(p);
		else
			pq.add(p2);
		
		Pair cur;
		String ns;
		String os;
		while (!pq.isEmpty()) {
			//System.out.println(pq);
			cur = pq.poll(); // get the next one
			//System.out.println("cur: " + cur);
			
			if (cur.n.length() == N) {
				best = (cur.n); // ext
				break;
			}
			
			// new and old strings
			ns = cur.n;
			os = cur.o;
			
			if (!pq.isEmpty() && pq.peek().o.compareTo(os) > 0 &&
					pq.peek().o.length() >= os.length()) pq.poll();
			
			
			p = new Pair(ns + os.charAt(0), os.substring(1));
			p2 = new Pair(ns + os.charAt(os.length()-1), os.substring(0, os.length()-1));
			

			pq.add(p);
			pq.add(p2);
		}
		
		//System.out.println(best.length());
		//System.out.println(best.charAt(1));
		int ind1 = 0;
		int ind2 = 80;
		while (ind2 <= best.length()) {
			System.out.println(best.substring(ind1, ind2));
			
			ind1 += 80;
			ind2 += 80;
		}
		System.out.println(best.substring(ind1));

		//System.out.println(best);
		
	}
	public static class Pair implements Comparable<Pair> {
		String n; // new string
		String o; // old string

		public Pair(String a, String h) {
			n = a;
			o = h;
		}
		@Override
		public int compareTo(Pair p) {
			return (n.compareTo(p.n)); // sort by new string
		}
		public String toString() {
			return (n + " " + o);
		}
	}
	public static String minString(String a, String b) {
		if (a.length() > b.length()) return a;
		if (b.length() > a.length()) return b;
		
		if (a.compareTo(b) < 0) return a;
		else return b;
	}
}
