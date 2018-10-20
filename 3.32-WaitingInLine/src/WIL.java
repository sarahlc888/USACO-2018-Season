import java.io.*;
import java.util.*;
/*
 * VERY SIMPLE PROBLEM
 * 10/16 class, see lesson screenshots folder P1 for problem statement
 * greedy algorithm to fill booths with people sorted from shortest to longest time
 * untested but should be working
 */
public class WIL {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("WIL.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of people in line <= 100000
		int R = Integer.parseInt(st.nextToken()); // number of booths <= 1000
		int[] time = new int[N]; // processing time for each person
		for (int i = 0; i < N; i++) 
			time[i] = Integer.parseInt(br.readLine());
		br.close();
		// task: find min total waiting and processing time for the N people

		Arrays.sort(time);
		int[] booth = new int[R]; 
		
		int totalTime = 0; // count waiting and processing of each person
		int i = 0; // index in N
		int j = 0; // index in R
		while (i < N) { // put person i in booth j
			booth[j] += time[i];
			totalTime += booth[j]; // update total time 
			i++;
			j++;
			j %= R;
		}
		
		System.out.println(totalTime);
		
//		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("WIL.out")));
//		pw.println();
//		pw.close();
	}
}
