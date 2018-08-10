import java.io.*;
import java.util.*;
/*
 * USACO 2014 December Contest, Gold
 * Problem 3. Cow Jog
 *
 * had to look at solution (but it's a less literal take compared to v3)
 * 14/14 cases
 * needed to use longs! also one of the conditions was > instead of >=
 */
public class CowJog2 {
	static long N;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("cowjog.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Long.parseLong(st.nextToken()); // num cows
		long T = Long.parseLong(st.nextToken()); // num minutes

		// stores max ending position of any cow in that lane so far
		ArrayList<Long> laneMax = new ArrayList<Long>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			long start = Long.parseLong(st.nextToken()); 
			long speed = Long.parseLong(st.nextToken()); 
			long end = start + T*speed;
			
//			System.out.println(start + " " + end);
			
			if (laneMax.isEmpty() || end <= laneMax.get(0)) { 
				// if no lanes yet, or if it can't go into ANY lane 
				laneMax.add(end); // need to create a new lane
			} else { // you can add this cow to an existing lane
				int ind = 0; // get the highest ind < end
				while (ind < laneMax.size()-1) {
					ind++;
					if (laneMax.get(ind) >= end) { 
						ind--;
						break;
					}
				}
				ind = Math.min(ind, laneMax.size()-1); // bound
				laneMax.set(ind, end); // put that cow in that position
			} 
			
			Collections.sort(laneMax); // sort
		}
		br.close();

		System.out.println(laneMax.size());
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));

		pw.println(laneMax.size());
		pw.close();
	}
}
