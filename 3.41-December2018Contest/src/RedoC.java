import java.io.*;
import java.util.*;
/*
 * 
 */
public class RedoC {
	static int[] coeff = {0, 1, -1, 1, -1, 1};
	static int i;
	static int j;
	static int k;
	static Subset cur;
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("cowpatibility.in"));
//		long t = System.currentTimeMillis();
		int N = Integer.parseInt(br.readLine());
	
		TreeMap<Subset, Integer> ct = new TreeMap<Subset, Integer>();

		int[] nums2 = new int[5];
		StringTokenizer st;
		for (i = 0; i < N; i++) { // scan everything in
			st = new StringTokenizer(br.readLine());
			
			for (j = 0; j < 5; j++) {
				nums2[j] = Integer.parseInt(st.nextToken());
			}
			Arrays.sort(nums2);
					
			for (j = 0; j < 32; j++) { // binary i is the subset (1 means include, 0 means exclude)
				cur = new Subset();
				
				for (k = 0; k < 5; k++) {
					if (((1<<k) & j) > 0) {
						cur.vals[cur.ind++] = nums2[k];
					}
				}
				
				if (ct.containsKey(cur)) {
					ct.put(cur, ct.get(cur)+1);
				} else {
					ct.put(cur, 1);
				}
				
			}	
		}
		br.close();

		long sum = (long)N*((long)(N-1))/2;
		
		Iterator it = ct.keySet().iterator();
		long amt;
		while (it.hasNext()) {
			cur = (Subset) it.next();
			amt = (ct.get(cur));
			sum -= ((long)coeff[cur.ind]) * amt * (amt-1) / 2;
		}
//		System.out.println(sum);
//		System.out.println(ct);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		pw.println(sum);
		pw.close();
//		System.out.println("time2: " + (System.currentTimeMillis()-t));
	}
	public static class Subset implements Comparable<Subset> {
		int[] vals;
		int ind;
		public Subset() {
			vals = new int[5];
		}
		public int compareTo(Subset o) {
			int rel = 0;
			for (int i = 0; i < 5; i++) {
				if (vals[i] < o.vals[i]) {
					rel = -1;
					break;
				} else if (vals[i] > o.vals[i]) {
					rel = 1;
					break;
				}
			}
			return rel;
		}	
		public boolean equals(Subset o) { // sort ct descending
			if (ind == o.ind) {
				boolean eqVals = true;
				for (int i = 0; i < 5; i++) {
					if (vals[i] != o.vals[i]) {
						eqVals = false;
						break;
					} 
				}
				return eqVals;
			} 
			return false;
		}
		public String toString() {
			return ind + " " + Arrays.toString(vals) + "\n";
		}
	}
	public static class Pair implements Comparable<Pair> {
		int num;
		int ct;

		public Pair(int a, int b) {
			num = a;
			ct = b;
		}
		@Override
		public int compareTo(Pair o) { // sort ct descending
			return o.ct-ct;
		}
		public String toString() {
			return num + " " + ct;
		}
	}


}
