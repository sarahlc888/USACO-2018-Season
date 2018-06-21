import java.io.*;
import java.util.*;

/*
 * not finished
 */

public class CowLineup {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		int N = Integer.parseInt(scan.nextLine());
		
		TreeSet<Integer> types = new TreeSet<Integer>();
		
		TreeSet<Integer> ids = new TreeSet<Integer>(); // ids at the current location
		// line: <x val, id>
		HashMap<Integer, Integer> line = new HashMap<Integer, Integer>(); 
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(scan.nextLine());
			
			int x = Integer.parseInt(st.nextToken()); // location
			int id = Integer.parseInt(st.nextToken()); // id
			
			// add them to the line map
			line.put(x, id);
			
			types.add(id);
		}
		
		int numTypes = types.size(); // number of different IDs
		
		// locations to visit: the locations to loop through from the cow line
		
		
		int ind = 0;
		
		Set keys = line.keySet();
		Iterator it = keys.iterator();
		int[] locs = new int[keys.size()];
		
		while (it.hasNext()) {
			locs[ind] = (int) it.next();
			ind++;
		}
		
		
		int minCost = Integer.MAX_VALUE;
		
		int startInd = 0; // the starting index

		System.out.println(ids.size());
		for (int i = 0; i < locs.length; i++) { // ending index
			// loop through the locations

			int curID = line.get(locs[i]); // get the id at the location
			System.out.println(ids.size());
			if (ids.size() > 0 && line.get(startInd) == (curID)) {
				// if is already there and it's at the starting index
				// delete the starting index from the set
				startInd++; 
			} else {
				// it's not already there
				ids.add(curID);
			}
			
			if (ids.size() == numTypes) {
				// if all types are present, see if it's a min
				if (i - startInd + 1 < minCost) minCost = i - startInd + 1;
			}
		}
		System.out.println(minCost);
		
		
	}
	public static Comparator<int[]> aComparator = new Comparator<int[]>(){

		@Override
		public int compare(int[] x, int[] y) {
			if (x[0] > y[0]) return 1;
			else if (x[0] < y[0]) return -1;
			else return 0;
		}
	};
}
