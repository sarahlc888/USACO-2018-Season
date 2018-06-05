/*
 * USACO 2017 December Contest, Gold
 * Problem 1. A Pie for a Pie
 * 
 * does not work, no idea yet... just the bare structure
 * 
 * ideas
 * ------
 * connect gifted pie to all possible pies that could be a return gift
 * somehow do all possible cases
 * 
 */

import java.io.*;
import java.util.*;


/*
Find all pies within [x, x+d]
Sort those pies by the intended recipient’s tastiness

Pie = node
* Attribute = baked by bessie or elsie, tastiness from bessie, tastiness from elsie
* Neighbors/connections = all pies within D units based on the other cow’s (non-baker) 
* Pies baked by elsie = connected to pies baked by bessie within D units of bessie’s tastiness ratings
    * One directional
When gifted, delete / get rid of edges

 */
public class PiePie {
	static AdjList[] adjs;
	static int N;
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("piepie.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // number of pies baked by each
		int D = Integer.parseInt(st.nextToken()); // max variance in rating for return gifts
		
		/*System.out.println("N: " + N);
		System.out.println("D: " + D);*/
		Pie[] pies = new Pie[2*N]; // unsorted pies
		Pie[] piesB = new Pie[2*N]; // pies sorted by Bessie's ratings (1st N baked by bessie, 2nd N baked by elsie)
		Pie[] piesE = new Pie[2*N]; // pies sorted by Elsie's ratings (1st N baked by bessie, 2nd N baked by elsie)
		
		for (int i = 0; i < N; i++) {
			// bessie's pies
			st = new StringTokenizer(br.readLine());
			int bv = Integer.parseInt(st.nextToken()); // bessie's rating
			int ev = Integer.parseInt(st.nextToken()); // elsie's rating
			pies[i] = new Pie(bv, ev, 0, i);
			piesB[i] = new Pie(bv, ev, 0, i); // 0 = baked by bessie, i = ID
			piesE[i] = new Pie(bv, ev, 0, i);
		}
		for (int i = N; i < 2*N; i++) {
			// elsie's pies
			st = new StringTokenizer(br.readLine());
			int bv = Integer.parseInt(st.nextToken()); // bessie's rating
			int ev = Integer.parseInt(st.nextToken()); // elsie's rating
			pies[i] = new Pie(bv, ev, 1, i);
			piesB[i] = new Pie(bv, ev, 1, i); // 1 = baked by elsie, i = ID
			piesE[i] = new Pie(bv, ev, 1, i);
		}
		br.close();
		
		// sort piesB based on Bessie's ratings
		Arrays.sort(piesB, 0, N, Pie.BessiePieComparator); 
		Arrays.sort(piesB, N, 2*N, Pie.BessiePieComparator); 
		// sort piesE based on Elsie's ratings
		Arrays.sort(piesE, 0, N, Pie.ElsiePieComparator); 
		Arrays.sort(piesE, N, 2*N, Pie.ElsiePieComparator); 
		
		/*
		System.out.println("pies");
		for (int i = 0; i < 2*N; i++) {
			System.out.println(pies[i].id);
		}
		System.out.println();
		System.out.println("piesB");
		for (int i = 0; i < 2*N; i++) {
			System.out.println(piesB[i].id);
		}
		System.out.println();
		System.out.println("piesE");
		for (int i = 0; i < 2*N; i++) {
			System.out.println(piesE[i].id);
		}
		System.out.println();

		for (int i = 0; i < 2*N; i++) {
		System.out.println("pie " + i + "  bval: " + pies[i].bVal + "  eval: " + pies[i].eVal + "  baker: " + pies[i].baker);
		}*/
		
		
		// adjacency matrix
		int[][] steps = new int[2*N][2*N];
		for (int i = 0; i < 2*N; i++) {
			for (int j = 0; j < 2*N; j++) {
				steps[i][j] = -1;
			}
		}
		
		adjs = new AdjList[2*N]; // array of lists of neighbors for node 0...2*N-1
		for (int i = 0; i < 2*N; i++) {
			adjs[i] = new AdjList();
		}
		
		// connect each of elsie's pies with bessie's regiftable pies within the range and vice versa
		// ONE DIRECTIONAL!!!
		/*
		System.out.println();
		System.out.println("bessie's pies");
		System.out.println();
		*/
		for (int i = 0; i < N; i++) { // loop through pies baked by bessie
			
			Pie curPie = pies[i]; // current pie (baked by bessie)
			//System.out.println("curPie: " + curPie.id);
			//System.out.println("  eval: " + curPie.eVal);
			
			// curPie gifted to E, E will return a pie with eval in [lbound, hbound]
			// find ALL possible regiftable pies
			
			if (curPie.eVal != 0) {
				// if the pie has Elsie rating == 0, gift exchange ends immediately (desirable outcome)
				
				int lbound = curPie.eVal; // rating of elsie's lowest regiftable pie
				int hbound = curPie.eVal + D; // rating of elsie's highest regiftable pie
				//System.out.println("  lbound: " + lbound);
				//System.out.println("  hbound: " + hbound);
				int loInd = bSearch1E(piesE, lbound, 2*N-1, N);
				int hiInd = bSearch2E(piesE, hbound, 2*N-1, N);
				
				for (int j = loInd; j <= hiInd; j++) {
					// add piesE[j].id to adjacency list of curPie.id
					adjs[curPie.id].adj.add(piesE[j].id);
					//System.out.println("  choice: " + piesE[j].id);
					steps[curPie.id][piesE[j].id] = 1;
				}
			}
			
		}
		/*
		System.out.println();
		System.out.println("elsie's pies");
		System.out.println();
		*/
		for (int i = N; i < 2*N; i++) { // loop through elsie's baked pies (last N pies)
			Pie curPie = pies[i]; // current pie baked by elsie
			//System.out.println("curPie: " + curPie.id);
			//System.out.println("  bval: " + curPie.bVal);
			
			// curPie gifted to B, B will return a pie with bval in [lbound, hbound]
			// find ALL possible regiftable pies
			
			if (curPie.bVal != 0) {
				// if the pie has Bessie rating == 0, gift exchange ends (desirable outcome)
				
				int lbound = curPie.bVal; // rating of bessie's lowest regiftable pie
				int hbound = curPie.bVal + D; // highest of Bessie's regiftables
				//System.out.println("  lbound: " + lbound);
				//System.out.println("  hbound: " + hbound);
				int loInd = bSearch1B(piesB, lbound, N-1, 0);
				int hiInd = bSearch2B(piesB, hbound, N-1, 0);
				
				for (int j = loInd; j <= hiInd; j++) {
					// add piesE[j].id to adjacency list of curPie.id
					adjs[curPie.id].adj.add(piesB[j].id);
					//System.out.println("  choice: " + piesB[j].id);
					steps[curPie.id][piesB[j].id] = 1;
				}
			}
		}
		
		/*
		for (int i = 0; i < 2*N; i++) {
			System.out.println();
			for (int j = 0; j < 2*N; j++) {
				System.out.print(steps[i][j] + " ");
			}
		}
		System.out.println();
		*/
		
		steps = sDist(steps);
		/*
		for (int i = 0; i < 2*N; i++) {
			System.out.println();
			for (int j = 0; j < 2*N; j++) {
				System.out.print(steps[i][j] + " ");
			}
		}
		System.out.println();
		*/
		
		ArrayList<Integer> zeroes = new ArrayList<Integer>(); // ids of pies with 0s
		for (int i = 0; i < 2*N; i++) {
			if (pies[i].bVal == 0 || pies[i].eVal == 0) {
				zeroes.add(i);
				//System.out.println("i: " + i);
			}
		}
		 
		
		int[] numGifts = new int[N]; 
		for (int i = 0; i < N; i++) {
			numGifts[i] = -1;
		}
		// at numGifts[i], min gifts that must be exchanged before cows are happy
		// when pie id = i (baked by bessie) is gifted first


		for (int i = 0; i < N; i++) { // starting ID
			//System.out.println("start: " + i);
			int minSteps = Integer.MAX_VALUE;
			
			for (int j = 0; j < zeroes.size(); j++) {
				//System.out.println("end: " + j);
				int k = zeroes.get(j);
				if (steps[i][k] != -1 && steps[i][k] < minSteps) {
					minSteps = steps[i][k];
				}
				if (i == k) minSteps = 0;
			}
			numGifts[i] = minSteps + 1;
			//System.out.println(numGifts[i]);
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
		
		for (int i = 0; i < N; i++) { 
			//System.out.println(numGifts[i]);
			pw.println(numGifts[i]);
		}
		
		pw.close();
		/*
		for (int i = 0; i < 2*N; i++) {
			System.out.println("ID: " + pies[i].id);
			// for cow of ID = i, print out its neighbors
			for (int j = 0; j < adjs[i].adj.size(); j++) {
				System.out.print(adjs[i].adj.get(j) + " ");
			}
			System.out.println();
		}
		// sort adjacency lists of bessie's pies 
		// for pies with elsie rating in range, sort by bessie rating
		
		for (int i = 0; i < N; i++) { // for the 1st adj lists, sort by Bessie
			adjs[i].adj.sort((Integer a, Integer b)->pies[a].bVal - pies[b].bVal);
		}
		for (int i = 0; i < N; i++) { // for the 2nd adj lists, sort by Elsie
			adjs[i].adj.sort((Integer a, Integer b)->pies[a].eVal - pies[b].eVal);
		}
		
		// loop through all possible starting pies gifts (from Bessie only)
		for (int i = 0; i < N; i++) {
			
			int numTurns = 0;
			
			Pie sPie = pies[i]; // starting pie
			System.out.println("start pie: " + sPie.id);
			
			LinkedList<Pie> toGift = new LinkedList<Pie>(); // pies to gift
			toGift.add(sPie);
			
			int turn = -1; // who is gifting, bessie = 0, elsie = 1
			
			while(!toGift.isEmpty()) {
				// while there are still pies to be gifted
				turn = (turn + 1) % 2; // switch var turn from 0 to 1 or vice versa
				
				numTurns++; // increment numturns
				
				// pie being gifted
				Pie curPie = toGift.removeFirst();
				
				// basecase: gift exchange ENDS
				if (turn == 0 && curPie.eVal == 0) break; // bessie's turn (b --> e)
				else if (turn == 1 && curPie.bVal == 0) break; // if it's elsie's turn (e --> b)
				
				
				// otherwise, select a gift for the other cow to give back
				int regiftID = adjs[curPie.id].adj.get(0); // get lowest rated val
				Pie regift = pies[regiftID];
				toGift.add(regift);
				
				adjs[curPie.id].adj.remove(0); // remove the adjacency (no double use)
				
				// TO SPEED UP / MAKE FASTER - maybe don't precalc everything???
				
			}
			
			// ??? add an if condition for if it never completes
			
			numGifts[i] = numTurns;
			
		}
		*/
		
		

	}
	public static class AdjList {
		ArrayList<Integer> adj;
		//ArrayList<Integer> diff;
		public AdjList() {
			adj = new ArrayList<Integer>();
			//diff = new ArrayList<Integer>();
		}
	}
	public static class Pie {
		int bVal; // bessie's tastiness value
		int eVal; // elsie's tastiness value
		int baker; // 0 for bessie, 1 for elsie
		int id; // unique for each pie, never changes even when pies are sorted
		public Pie(int b, int e, int bak, int inID) {
			bVal = b;
			eVal = e;
			baker = bak;
			id = inID;
		}
		public static Comparator<Pie> BessiePieComparator = new Comparator<Pie>() {

			public int compare(Pie p1, Pie p2) {
				
				//ascending order
				
				if (p1.bVal > p2.bVal) {
					return 1;
				} else if (p1.bVal < p2.bVal) {
					return -1;
				} else if (p1.eVal > p2.eVal) {
					return 1;
				} else if (p1.eVal < p2.eVal) {
					return -1;
				} else {
					return 0;
				}
				
			}

		};
		public static Comparator<Pie> ElsiePieComparator = new Comparator<Pie>() {

			public int compare(Pie p1, Pie p2) {
				
				//ascending order
				
				if (p1.eVal > p2.eVal) {
					return 1;
				} else if (p1.eVal < p2.eVal) {
					return -1;
				} else if (p1.bVal > p2.bVal) {
					return 1;
				} else if (p1.bVal < p2.bVal) {
					return -1;
				} else {
					return 0;
				}
				

			}

		};
	}
	public static int[][] sDist(int[][] steps) {
		for (int k = 0; k < 2*N; k++) { // intermediate point
			for (int i = 0; i < 2*N; i++) {
				if (i==k) continue; // i, j, and k must be unique
				for (int j = 0; j < 2*N; j++) {
					if (j==i) continue; // i, j, and k must be unique
					if (j==k) continue; // i, j, and k must be unique
					
					// check the cost of going from i to j through node k
					
					if ((steps[i][j] == -1 || steps[i][k] + steps[k][j] < steps[i][j])
							&& steps[i][k] != -1 && steps[k][j] != -1) {
						// if...
						//  - the dist from i to j (is yet unknown) or (would decrease if updated)
						//  - AND the dists from i to k and k to j are known
						
						// update the cost of distCost[i][j] to the new value going through k
						steps[i][j] = steps[i][k] + steps[k][j];
					}
					
				}
			}
		}
		return steps;
	}
	public static int bSearch1E(Pie[] pts, int lbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		// for elsie vals
		
		// exception catching
		if (lbound <= pts[lo].eVal) {
			return lo;
		} else if (lbound > pts[hi].eVal) { // not in any 
			return -1;
		}
		
		// basecase
		// now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) {
			return hi;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid].eVal < lbound) {
			// go bigger
			lo = mid + 1;
			return bSearch1E(pts, lbound, hi, lo);
		} else if (pts[mid].eVal > lbound) {
			// go smaller
			hi = mid;     // lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			return bSearch1E(pts, lbound, hi, lo);
		} else {
			while (pts[mid].eVal == pts[mid-1].eVal) {
				mid--;
			}
			return mid;
			// equals --> desired result
		}
		
	}
	public static int bSearch2E(Pie[] pts, int hbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (hbound >= pts[hi].eVal) {
			//System.out.println("t1");
			return hi;
		} else if (hbound < pts[lo].eVal) { // no point are in the interval
			//System.out.println("t2");
			return -1;
		}
		
		// basecase for when hi - lo range is tiny
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			return lo;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid].eVal < hbound) {
			// go bigger
			lo = mid;
			//System.out.println("t4");
			return bSearch2E(pts, hbound, hi, lo);
		} else if (pts[mid].eVal > hbound) {
			// go smaller
			hi = mid - 1;
			return bSearch2E(pts, hbound, hi, lo);
		} else {
			while (pts[mid].eVal == pts[mid+1].eVal) {
				mid++;
			}
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}
	public static int bSearch1B(Pie[] pts, int lbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		// for elsie vals
		
		// exception catching
		if (lbound <= pts[lo].bVal) {
			return lo;
		} else if (lbound > pts[hi].bVal) { // not in any 
			return -1;
		}
		
		// basecase
		// now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) {
			return hi;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid].bVal < lbound) {
			// go bigger
			lo = mid + 1;
			return bSearch1B(pts, lbound, hi, lo);
		} else if (pts[mid].bVal > lbound) {
			// go smaller
			hi = mid;     // lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			return bSearch1B(pts, lbound, hi, lo);
		} else {
			while (pts[mid].bVal == pts[mid-1].bVal) {
				mid--;
			}
			return mid;
			// equals --> desired result
		}
		
	}
	public static int bSearch2B(Pie[] pts, int hbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (hbound >= pts[hi].bVal) {
			//System.out.println("t1");
			return hi;
		} else if (hbound < pts[lo].bVal) { // no point are in the interval
			//System.out.println("t2");
			return -1;
		}
		
		// basecase for when hi - lo range is tiny
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			return lo;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid].bVal < hbound) {
			// go bigger
			lo = mid;
			//System.out.println("t4");
			return bSearch2B(pts, hbound, hi, lo);
		} else if (pts[mid].bVal > hbound) {
			// go smaller
			hi = mid - 1;
			return bSearch2B(pts, hbound, hi, lo);
		} else {
			while (pts[mid].bVal == pts[mid+1].bVal) {
				mid++;
			}
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}
}

