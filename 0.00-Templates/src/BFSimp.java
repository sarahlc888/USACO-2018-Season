import java.util.LinkedList;

public class BFSimp {
	public static int N;
	public static boolean condition = true;
	
	public static int BFScount(int x) { // BFS
		// returns how many cows the broadcast can reach with cur x val (amt of $)
		
		int count = 1; // can reach initial cow
		int start = 0; // starting cow
		
		LinkedList<Integer> toVisit = new LinkedList<Integer>();	
		toVisit.add(start);
		
		boolean[] hasBeenVisited = new boolean[N];
		hasBeenVisited[start] = true;
		
		while (!toVisit.isEmpty()) {
			int cur = toVisit.removeFirst(); // current cow
			
			for (int i = 0; i < N; i++) { // loop through all other cows
				if (!hasBeenVisited[i] && condition) {
					// if it's not visited and is within condition
					// add i to toVisit, mark visited, increment count
					toVisit.add(i);
					hasBeenVisited[i] = true;
					count++;
				}
			}
		}

		return count;	
	}
}
