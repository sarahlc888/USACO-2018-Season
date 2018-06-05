import java.io.*;
import java.util.*;

// USACO 2017 December Contest, Gold
// Problem 3. Haybale Feast

// partial sum array & max segment tree & binary search

// 10/10 test cases (formerly 6/10 test cases, errors on scanning in bc nums too long)
// USE LONG!!

public class HayFeast {
	public static void main(String args[]) throws IOException {
		
		//BufferedReader br = new BufferedReader(new FileReader("testData/4.in"));
		BufferedReader br = new BufferedReader(new FileReader("hayfeast.in"));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // number of haybales
		Long M = Long.parseLong(st.nextToken()); // min total flavor USE LONG
		
		long[] fps = new long[N]; // partial sum [] of flavor, fps[i] = fps[0] + ... fps[i]
		int[] spice = new int[N]; // array of spiciness
		
		for (int i = 0; i < N; i++) { // scan in values
			st = new StringTokenizer(br.readLine());
			// update partial sum
			if (i == 0) fps[i] = Integer.parseInt(st.nextToken());
			else fps[i] = fps[i-1] + Integer.parseInt(st.nextToken());
			// scan in spice
			spice[i] = Integer.parseInt(st.nextToken());
		}
		
		br.close();
		
		SegmentTreeMax spiceMax = new SegmentTreeMax(N); // max segment tree for the space
		spiceMax.buildtree(spice); // build the tree
		
		//System.out.println(Arrays.toString(spiceMax.treearr));
		
		int minSpice = Integer.MAX_VALUE;
		
		for (int k = 0; k < N; k++) { // k = starting point of the interval
			
			// j = first index where fps[j] - fps[k] >= M
			
			
			int j = bSearch1(fps, M, N-1, 0, k); 
			if (j < 0) continue; // if there are no results, move on
			
			int curspice = spiceMax.maxFrom(k, j); // calculate highest spice within k...j
			if (curspice < minSpice) 
				minSpice = curspice; // update if necessary
			
		}
		//System.out.println(minSpice);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hayfeast.out")));

		pw.println(minSpice);
		pw.close();
	}
	public static int bSearch1(long[] pts, long lbound, int hi, int lo, int k) {
		// returns the lowest index j where ps[j] - ps[k] > lbound (the minimum / lower bound)
		// k is the starting point of the interval
		
		// exception catching
		if (lbound <= pts[lo] - pts[k]) {
			return lo;
		} else if (lbound > pts[hi] - pts[k]) { // not in any 
			return -1;
		}
		
		// basecase
		// now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) {
			return hi;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid] - pts[k] < lbound) { // go bigger
			lo = mid + 1;
			return bSearch1(pts, lbound, hi, lo, k);
		} else if (pts[mid] - pts[k]  > lbound) { // go smaller
			hi = mid;    
			return bSearch1(pts, lbound, hi, lo, k);
		} else {
			while (pts[mid] - pts[k] == pts[mid-1] - pts[k]) {
				mid--;
			}
			return mid;
			// equals --> desired result
		}
		
	}
	public static class SegmentTreeMax {
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // number of layers of the tree
		int maxsize; // max nodes able to fit in the set height
		int startind; // start of the tree
		int endind; // end of the tree
		
		
		public SegmentTreeMax(int n) { // constructor for a tree
			numarr = new int[n]; // initialize numarr
			
			// num layers needed to fit all nums on the bottom layer = 1 + log base 2 n
			height = (int) Math.ceil(Math.log(n)/Math.log(2)) + 1;  
			
			// 1 + 2 + 4 + 8 ...  = 2^(num layers) - 1
			maxsize = (int) (Math.pow(2, height) - 1); 
			
			treearr = new int[maxsize]; // initialize the full binary tree
			Arrays.fill(treearr, Integer.MIN_VALUE); // fill with  min value so the 0s don't interfere w/ neg
			endind = n-1; // mark the end of the nums
		}
		
		
		public void buildtree(int[] numsIn) { // build the tree
			// copy in the nums array
			for (int i = 0; i < numarr.length; i++) {
				numarr[i] = numsIn[i];
			}
			
			buildTreeRec(startind, endind, 0); // build the tree
		}
		public int buildTreeRec(int lo, int hi, int cur) {
			// start from the root, build with divide and conquer
			
			if (lo == hi) { // if all sections have been covered
				treearr[cur] = numarr[lo]; // assign current node as the value
				return treearr[cur]; // return the value
			}
			
			int mid = (lo + hi)/2;
			
			// build the tree, cur node is the max of its children
			// half the range as you go down a level
			// when you reach a leaf node, lo will == hi
			treearr[cur] = Math.max(buildTreeRec(lo, mid, leftind(cur)), 
	        		buildTreeRec(mid + 1, hi, rightind(cur)));
			
			return treearr[cur];
			
		}
		public int maxFrom(int q, int p) { // returns sum of tarr[q] through tarr[p] inclusive
			
			return maxFromRec(0, endind-1, q, p, 0);
		}
		public int maxFromRec(int sind, int eind, int x, int y, int cur) {
			// get sum [x, y] using divide and conquer
			// [startind, endind] = range of tree with root cur, max = treearr[cur]
			
			if (x <= sind && y >= eind)  // [x, y] contains [startIndex, endIndex]
				return treearr[cur]; // everything is in range

			if (eind < x || sind > y)   // [x, y] doesn't overlap [startIndex, endIndex]
				return Integer.MIN_VALUE;

			int mid = (sind + eind) / 2; // split the range

			// check the children using the appropriate ranges, then pick the larger one
			return Math.max(maxFromRec(sind, mid, x, y, leftind(cur)),
					maxFromRec(mid + 1, eind, x, y, rightind(cur)));
		}
		
		public void setValue(int pos, int val) {
			// arr[pos] = val;
			
			int diff = val - numarr[pos]; // difference between new and old value
			numarr[pos] = val; // re-assign in numarr
			setValueRec(startind, endind, pos, diff, 0); // recursion
		}

		public int setValueRec(int sind, int eind, int pos, int diff, int cur) {
			// position in array, sind and eind as intervals of the array covered by cur node
			
			if( pos >= sind && pos <= eind ) { // if pos is in the subtree
				treearr[cur] = treearr[cur] + diff; // reassign in the tree, account for changes

				if (sind != eind ) { // until you go through everything, split and go through the whole subtree
					int mid = (sind + eind) / 2;
					treearr[cur] = Math.max(setValueRec(sind, mid, pos, diff, leftind(cur)),
					setValueRec(mid+1, eind, pos, diff, rightind(cur)));
					return treearr[cur];
				} else { // leaf node of pos, altered above, still return
					return treearr[cur];
				}
				
			} else { // out range, no changes (pos < sind || pos > eind)
				return treearr[cur]; 
			}
		}
		
		public int parind(int j) { // returns ind of parent of node j
			return (j-1)/2;
		}
		public int leftind(int j) { // returns ind of left child of node j
			return 2*j + 1;
		}
		public int rightind(int j) { // returns ind of right child of node j
			return 2*j + 2;
		}
	}


}
