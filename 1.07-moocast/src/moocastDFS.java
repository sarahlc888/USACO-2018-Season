import java.io.*;
import java.util.*;

// works

public class moocastDFS {
	
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("moocast.in"));
		int N = scan.nextInt(); // num of cows
		int[] cowx = new int[N]; // xpos
		int[] cowy = new int[N]; // ypos
		int[] power = new int[N]; // broadcast power
		
		for (int i = 0; i < N; i++) {
			cowx[i] = scan.nextInt();
			cowy[i] = scan.nextInt();
			power[i] = scan.nextInt();
		}
		scan.close();
		
		int maxVisit = 0;
		
		for (int i = 0; i < N; i++) {
			
			// cow[i] is the starting point of the broadcast
			
			Stack<Integer> toVisit = new Stack<Integer>();
			
			toVisit.add(i);
			
			boolean[] hasBeenVisited = new boolean[N];
			hasBeenVisited[i] = true;
			
			int count = 1;
			
			while (!toVisit.isEmpty()) {
				int cur = toVisit.pop(); // current cow
				
				for (int j = 0; j < N; j++) {
					// loop through all other cows
					if (!hasBeenVisited[j] && (power[cur] * power[cur] >= (cowy[j] - cowy[cur]) * (cowy[j] - cowy[cur]) + 
							(cowx[j] - cowx[cur]) * (cowx[j] - cowx[cur]))) {
						// if it's not visited and in the distance, add j
						toVisit.add(j);
						count++;
						hasBeenVisited[j] = true;
					}
				}
			}
			
			if (count > maxVisit) {
				maxVisit = count;
			}
		}
		PrintWriter PW = new PrintWriter(new File("moocast.out"));
		PW.println(maxVisit);
		PW.close();
		//System.out.println(maxVisit);
	}
}
