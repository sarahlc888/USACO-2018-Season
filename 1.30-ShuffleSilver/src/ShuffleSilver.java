import java.io.*;
import java.util.*;

public class ShuffleSilver {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
		int N = Integer.parseInt(br.readLine()); // number of cows
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int[] shuffle = new int[N+1];
		for (int i = 1; i <= N; i++) {
			shuffle[i] = Integer.parseInt(st.nextToken());
			//System.out.println(shuffle[i]);
		}
		br.close();
		
		// INDEXING IS FROM 1 TO N
		boolean[] hasBeenVisited = new boolean[N+1];
		
		int count = 0;
		
		for (int i = 1; i <= N; i++) {
			// if already visited and put into a cycle, move on
			if (hasBeenVisited[i]) continue;
			// otherwise mark as visited
			hasBeenVisited[i] = true;
			
			LinkedList<Integer> cycle = new LinkedList<Integer>();
			
			cycle.add(i);
			
			int curNode = cycle.getLast();
			
			while (true) {
				// while the shuffle doesn't close the cycle
				// keep looking for the rest of the cycle
				//curNode = cycle.getLast();
				
				int nextNode = shuffle[curNode];
				
				if (cycle.contains(nextNode)) {
					count += cycle.size() - cycle.indexOf(nextNode);
					break;
				} else if (hasBeenVisited[nextNode]) {
					// it's already been visited but it's not part of the cycle because
					// former condition wasn't met
					cycle.clear();
					break;
				} 
				
				cycle.add(nextNode);
				hasBeenVisited[nextNode] = true; // eventually everything will be visited
				curNode = cycle.getLast();
				
			}
			
			
			
		}
		
		//System.out.println(count);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));

		pw.println(count);
		pw.close();
	}


}
