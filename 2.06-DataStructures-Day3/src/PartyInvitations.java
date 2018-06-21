import java.io.*;
import java.util.*;

/*
 * in progress
 */

public class PartyInvitations {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		
		int N = Integer.parseInt(st.nextToken()); // number of cows
		int G = Integer.parseInt(st.nextToken()); // number of groups
		
		// groups
		ArrayList<ArrayList<Integer>> groups = new ArrayList<ArrayList<Integer>>();
		// map cow ids to the indices of the groups
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i < N; i++) {
			// set up the hashmap for cows 1...N
			map.put(i+1, new ArrayList<Integer>());
		}
		// visited array for the processing later
		HashMap<Integer, Integer> visited = new HashMap<Integer, Integer>(); // visited array

		for (int i = 0; i < G; i++) {
			st = new StringTokenizer(scan.nextLine());
			groups.add(new ArrayList<Integer>()); // initialize group container
			int s = Integer.parseInt(st.nextToken()); // size of group
			for (int j = 0; j < s; j++) { // scan in the cows in the group
				int nextCow = Integer.parseInt(st.nextToken());
				groups.get(i).add(nextCow);
				map.get(nextCow).add(i); // add the group index for the cows
				
				if (!visited.containsKey(nextCow)) {
					visited.put(nextCow, 0); // mark unvisited
				}
				
			}
		}
		
		// queue or linkedlist of toVisit (don't remove stuff though, just keep a running index)
		Queue<Integer> toVisit = new LinkedList<Integer>(); // stores cows processed
		
		toVisit.add(1); // starting point is the first cow
		int count = 0;
		
		while (!toVisit.isEmpty()) { // process each cow
			count++;
			
			int cowID = toVisit.poll(); // current cow
			
			//System.out.println("id: " + cowID + " count: " + count);
			//System.out.println(groups);
			
			ArrayList<Integer> groupsIn = map.get(cowID); // groups the cur cow is in
			//System.out.println(groupsIn);
			for (int i = 0; i < groupsIn.size(); i++) {
				// remove the cow ID from the groups the cow is in
				
				groups.get(groupsIn.get(i)).remove((Integer)cowID); 

				//System.out.println("  " + groups.get(groupsIn.get(i)));

				if (groups.get(groupsIn.get(i)).size() == 1 &&
						visited.get(groups.get(groupsIn.get(i)).get(0)) == 0) {
					//System.out.println("here");
					// only one cow left in the group AND not visited
					// push the last cow onto the queue
					toVisit.add(groups.get(groupsIn.get(i)).get(0));
					// mark visited
					visited.put(groups.get(groupsIn.get(i)).get(0), 1); 
					
				}
			}
			/*
			System.out.println();
			for (int i = 0; i < groups.size(); i++) {
				System.out.println("  " + groups.get(i));
			}*/
			// make sure you don't invite or push into the queue twice, use a visited array
				
		}
		System.out.println(count);
	}
}
