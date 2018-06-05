import java.io.*;
import java.util.*;


public class snowbootsOG {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// constants
		int N = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		
		int maxsnow = 0;
		
		// holds amount of snow on the tiles
		int[] tiles = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			tiles[i] = Integer.parseInt(st.nextToken());
			//System.out.println(tiles[i]);
			if (tiles[i] > maxsnow) maxsnow = tiles[i];
		}
		
		// holds max [depth] and [step]
		int[][] boots = new int[B][2];
		for (int i = 0; i < B; i++) {
			st = new StringTokenizer(br.readLine());
			boots[i][0] = Integer.parseInt(st.nextToken());
			boots[i][1] = Integer.parseInt(st.nextToken());
			//System.out.println(boots[i][0] + "  " + boots[i][1]);
		}
		br.close();
		
		
		// segment tree of snow on tiles
		SegmentTreeMin A = new SegmentTreeMin(N);
		A.buildtree(tiles);
		
		

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));

		for (int i = 0; i < B; i++) {
			// go through all the boots
			
			
			
			int depth = boots[i][0];
			int step = boots[i][1];
			//System.out.println("d: " + depth);
			//System.out.println("s: " + step);
			
			
			int curpos = 0;
			boolean path = true; // is there still hope for a possible path
			
			if (!(step >= N-1) && !(depth >= maxsnow)) {

				while (curpos < N-1 && path) { // while not at the end yet
					
					if (A.minFrom(curpos+1, curpos+step) > depth) {
						// if the smallest value in the possible range is > depth
						// there's no where to go
						path = false;
						break;
					}
					
					
					//System.out.println("curpos: " + curpos);
					for (int j = Math.min(curpos + step, N-1); j > curpos; j--) { // loop through 
						//System.out.println(tiles[j]);
						if (tiles[j] <= depth) {
							// if you can take the step, take it
							curpos = j;
							break;
						}
						if (j == curpos+1) path = false;
					}
				}
				
			}

			if (path) {
				//System.out.println("ans: " + 1);
				pw.println(1);
			} else {
				//System.out.println("ans: " + 0);
				pw.println(0);
			}
			System.out.println();


		}
		
		
		pw.close();
	}

	public static class SegmentTreeMin {
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // number of layers of the tree
		int maxsize; // max nodes able to fit in the set height
		int startind; // start of the tree
		int endind; // end of the tree
		
		
		public SegmentTreeMin(int n) { // constructor for a tree
			numarr = new int[n]; // initialize numarr
			
			// num layers needed to fit all nums on the bottom layer = 1 + log base 2 n
			height = (int) Math.ceil(Math.log(n)/Math.log(2)) + 1;  
			
			// 1 + 2 + 4 + 8 ...  = 2^(num layers) - 1
			maxsize = (int) (Math.pow(2, height) - 1); 
			
			treearr = new int[maxsize]; // initialize the full binary tree
			Arrays.fill(treearr, Integer.MAX_VALUE); // fill with  min value so the 0s don't interfere w/ neg
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
			
			//  A
			// B C
			
			// 1 2
			
			// lo = 0, hi = 2, mid = 1
			// build(0, 1, 1)
			// build(0, 0, 1) node[1] = 1
			// build(1, 2, 2)
			// build(1, 1, 2) node[2] = 2

			
			if (lo == hi) { // if all sections have been covered
				treearr[cur] = numarr[lo]; // assign current node as the value
				return treearr[cur]; // return the value
			}
			
			int mid = (lo + hi)/2;
			
			// build the tree, cur node is the max of its children
			// half the range as you go down a level
			// when you reach a leaf node, lo will == hi
			treearr[cur] = Math.min(buildTreeRec(lo, mid, leftind(cur)), 
	        		buildTreeRec(mid + 1, hi, rightind(cur)));
			
			return treearr[cur];
			
		}
		public int minFrom(int q, int p) { // returns sum of tarr[q] through tarr[p] inclusive
			
			return minFromRec(0, endind, q, p, 0);
		}
		public int minFromRec(int sind, int eind, int x, int y, int cur) {
			// get sum [x, y] using divide and conquer
			// [startind, endind] = range of tree with root cur, max = treearr[cur]
			
			if (x <= sind && y >= eind)  // [x, y] contains [startIndex, endIndex]
				return treearr[cur]; // everything is in range

			if (eind < x || sind > y)   // [x, y] doesn't overlap [startIndex, endIndex]
				return Integer.MAX_VALUE;

			int mid = (sind + eind) / 2; // split the range

			// check the children using the appropriate ranges, then pick the larger one
			return Math.min(minFromRec(sind, mid, x, y, leftind(cur)),
					minFromRec(mid + 1, eind, x, y, rightind(cur)));
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
					treearr[cur] = Math.min(setValueRec(sind, mid, pos, diff, leftind(cur)),
					setValueRec(mid+1, eind, pos, diff, rightind(cur)));
					return treearr[cur];
				} else {
					// leaf node of pos, altered above, still return
					
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
