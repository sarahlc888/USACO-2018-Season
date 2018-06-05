import java.io.*;
import java.util.*;

/*
 * USACO 2016 December Contest, Gold
 * Problem 1. Moocast
 * 
 * 10/10 test cases
 * But why?
 * circumnavigate stack overflow error on the linkedlist??
 */


public class Moocast {
	
	public static int N;
	public static int[] cowx;
	public static int[] cowy;
	
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("moocast.in"));
		//BufferedReader br = new BufferedReader(new FileReader("testData/10.in"));
		N = Integer.parseInt(br.readLine()); // number of cows
		
		cowx = new int[N]; // x coordinates of cows
		cowy = new int[N]; // y coordinates
		
		int maxNum = 0;
		int minNum = Integer.MAX_VALUE;
		
		for (int i = 0; i < N; i++) { // scan in
			StringTokenizer st = new StringTokenizer(br.readLine());
			cowx[i] = Integer.parseInt(st.nextToken());
			cowy[i] = Integer.parseInt(st.nextToken());
			
			if (cowx[i] > maxNum) maxNum = cowx[i];
			else if (cowx[i] < minNum) minNum = cowx[i];
			
			if (cowy[i] > maxNum) maxNum = cowy[i];
			else if (cowy[i] < minNum) minNum = cowy[i];
			
		}
		
		br.close();

		// get lowest x (money) to allow communication between all cows
		// initial upper bound is the greatest amt of money possible to spend
		// = 25000^2 * 2
		
		// that's too big, so instead use 
		//int ub = 1250000000; // not enough space
		//int ub = 104700000; // times out on 4 and 8
		//int ub = Math.min( (maxNum-minNum)*(maxNum-minNum)*2+1 , 104700000);
		int ub = (maxNum-minNum)*(maxNum-minNum)*2+1 ;
		System.out.println(ub);
		int x = bSearchMod(N, ub, 0);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));

		pw.println(x);
		System.out.println(x);
		
		pw.close();
	}
	public static int cost(int a, int b) { 
		// returns cost to transmit between cows a and b (= distance^2)
		// dist = sqrt( (y2-y1)^2 + (x2-x1)^2 );
		if (a == b) return 0; // same point
		return (cowy[a] - cowy[b])*
				(cowy[a] - cowy[b]) + 
				(cowx[a] - cowx[b])*
				(cowx[a] - cowx[b]);
	}
	public static int reach(int x) { // BFS
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
				if (!hasBeenVisited[i] && (x >= cost(cur, i))) {
					// if it's not visited and is within cost
					// add i to toVisit, mark visited, increment count
					toVisit.add(i);
					hasBeenVisited[i] = true;
					count++;
				}
			}
		}

		return count;	
	}
	public static int bSearchMod(int lbound, int hi, int lo) { // mod from counting haybales
		// returns first value >= desired condition
		
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (lbound <= reach(lo)) {
			return lo;
		} else if (lbound > reach(hi)) { // not in any
			System.out.println("here");
			return -1;
		}
		
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		int curval = reach(mid); // current value
		
		// narrow the range
		if (curval < lbound) { // go bigger
			lo = mid + 1;
			return bSearchMod(lbound, hi, lo);
		} else if (curval > lbound) { // go smaller
			hi = mid; 
			return bSearchMod(lbound, hi, lo);
		} else {
			hi = mid;
			//hi = mid+1;
			
			return bSearchMod(lbound, hi, lo);
			/*
			while (curval == reach(mid-1)) 
				mid--;
			*/
			// equals --> desired result
		}
		
	}

}
