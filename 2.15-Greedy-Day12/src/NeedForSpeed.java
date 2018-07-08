import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
// O(N log N), 10/10 test cases
public class NeedForSpeed {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int F = Integer.parseInt(st.nextToken()); // force
		int M = Integer.parseInt(st.nextToken()); // mass
		int N = Integer.parseInt(st.nextToken()); // number of parts
		
		int curF = F;
		int curM = M;
		double curAcc = (double)F/(double)M;

		Pair[] parts = new Pair[N];
		int[] used = new int[N];
		Arrays.fill(used, Integer.MAX_VALUE);
		int usedInd = 0;
		
		for (int i = 0; i < N; i++) { // scan in parts
			st = new StringTokenizer(br.readLine());
			int force = Integer.parseInt(st.nextToken()); // force
			int mass = Integer.parseInt(st.nextToken()); // mass
			parts[i] = new Pair(force, mass, i+1);
		}
		
		Arrays.sort(parts); // sort by highest acceleration
		
		//System.out.println(Arrays.toString(parts));
		for (int i = 0; i < N; i++) { // loop through all the parts
			
			// if the acc is greater, add it
			if (parts[i].acc > curAcc) {
				curF += parts[i].x;
				curM += parts[i].y;
				curAcc = (double)curF / (double) curM;
				
				used[usedInd] = parts[i].id;
				
				usedInd++;
			} else {
				break;
			}
			
		}
		boolean printed = false;
		Arrays.sort(used);
		for (int i = 0; i < N; i++) {
			if (used[i] != Integer.MAX_VALUE) {
				System.out.println(used[i]);
				printed = true;
			} else {
				
				break;
			}
		}
		if (!printed) {
			System.out.println("NONE");
		}
	}
	public static class Pair implements Comparable<Pair> {
		int x; // force
		int y; // mass
		int id; // original id
		double acc;
		
		public Pair(int a, int b, int c) {
			x = a;
			y = b;
			id = c;
			acc = (double)x/(double)y;
		}
		@Override
		public int compareTo(Pair o) { 
			// sort by highest to lowest acceleration (F/m)
			return (int)(o.acc-acc); 
		}
		public String toString() {
			return x + " " + y;
		}

	}
}
