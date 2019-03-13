import java.io.*;
import java.util.*;
/*
 * post comp 12/24 redo
 */
public class RedoCNaive {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowpatibility.in"));
		int N = Integer.parseInt(br.readLine());
		
		int[][] nums = new int[N][5]; 
		int[] count = new int[1000001];
		// entry, rows that have that entry
		TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<Integer, ArrayList<Integer>>(); 
				
		for (int i = 0; i < N; i++) { // scan everything in
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 5; j++) {
				nums[i][j] = Integer.parseInt(st.nextToken());
				count[nums[i][j]]++;
				if (!map.containsKey(nums[i][j])) map.put(nums[i][j], new ArrayList<Integer>());
				map.get(nums[i][j]).add(i);
			}
		}
		br.close();
		
		long compCt = 0;
		
		boolean[][] isPair = new boolean[N][N];
		for (int i = 0; i < count.length; i++) {
			if (count[i] == 0) continue;
			ArrayList<Integer> curNums = map.get(i);
			for (int j = 0; j < curNums.size(); j++) {
				for (int k = j+1; k < curNums.size(); k++) {
					int a = curNums.get(j);
					int b = curNums.get(k);
					if (!isPair[a][b]) {
						compCt++;
						isPair[a][b] = true;
					}
				}
			}
		}
		
		long ret = N*(N-1)/2-compCt;
		
//		System.out.println(ret);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		pw.println(ret);
		pw.close();
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
