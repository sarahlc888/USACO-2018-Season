import java.io.*;
import java.util.*;
/*
 * USACO 2017 February Contest, Gold
 * Problem 3. Why Did the Cow Cross the Road III
 * 
 * 10/10
 * 20 minutes
 */
public class CCIIIG {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("circlecross.in"));
		int N = Integer.parseInt(br.readLine());
		
		int[][] cows = new int[N][2]; // cows[i][0] = entry point for cow i; [i][1] = exit point
		
		for (int i = 0; i < N; i++) { // mark all positions unfilled
			cows[i][0] = -1;
			cows[i][1] = -1;
		}
		
		int ind = 0; // starting ind or position
		
		for (int i = 0; i < 2*N; i++) {
			int cow = Integer.parseInt(br.readLine()) - 1;
			//System.out.println(cow);
			// assign the current index as an entry or exit point for the cow
			if (cows[cow][0] == -1) {
				cows[cow][0] = i;
			} else {
				cows[cow][1] = i;
			}
			//System.out.println(cows[cow][0] + " | " + cows[cow][1]);
		}
		br.close();

		// sort cows by entry ind using comparator arrComp
		Arrays.sort(cows, arrComp);
		
		
		// array to build a segment tree off of
		int[] stArr = new int[2*N];
		
		// build the segment tree
		STsum st = new STsum(2*N);
		st.buildtree(stArr);
		
		// count of intersections
		int count = 0;
		
		
		// loop through cows
		for (int i = 0; i < N; i++) {
			////System.out.println("i: " + i);
			
			int entry = cows[i][0];
			int exit = cows[i][1];
			
			////System.out.println("  " + curInd + "| " + curH);
			
			// get sum from entry to exit because in that range,
			// if there's another cow marked as present,
			// it will be an intersection (no pairs within because it's sorted by ind)
			count += st.sumFrom(entry, exit);

			// mark the indices in the tree
			st.setValue(entry, 1); 
			st.setValue(exit, 1); 

		}
		
		//System.out.println(count);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));
		pw.println(count);
		pw.close();
	}
	public static Comparator<int[]> arrComp = new Comparator<int[]>(){

		@Override
		public int compare(int[] x, int[] y) {
			if (x[0] > y[0]) return 1;
			else if (x[0] < y[0]) return -1;
			else return 0;
		}
	};
	public static class STsum {
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // number of layers of the tree
		int maxsize; // max nodes able to fit in the set height
		int startind; // start of the tree
		int endind; // end of the tree
		
		
		public STsum(int n) { // constructor for a tree
			numarr = new int[n]; // initialize numarr
			
			// num layers needed to fit all nums on the bottom layer = 1 + log base 2 n
			height = (int) Math.ceil(Math.log(n)/Math.log(2)) + 1;  
			
			// 1 + 2 + 4 + 8 ...  = 2^(num layers) - 1
			maxsize = (int) (Math.pow(2, height) - 1); 
			
			treearr = new int[maxsize]; // initialize the full binary tree
			endind = n-1; // mark the end of the nums
		}
		public void buildtree(int[] numsIn) { // build the tree
			// copy in the nums array
			for (int i = 0; i < numarr.length; i++) {
				numarr[i] = numsIn[i];
			}
			
			buildTreeRec(startind, endind, 0); // build the tree
		}
		private int buildTreeRec(int lo, int hi, int cur) {
			// start from the root, build with divide and conquer
			
			if (lo == hi) { // if all sections have been covered
				treearr[cur] = numarr[lo]; // assign current node as the value
				return treearr[cur]; // return the value
			}
			
			int mid = (lo + hi)/2;
			
			// build the tree, cur node is the sum of its children
			// half the range as you go down a level
			// when you reach a leaf node, lo will == hi
			treearr[cur] = buildTreeRec(lo, mid, leftind(cur)) 
	        		+ buildTreeRec(mid + 1, hi, rightind(cur));
			
			return treearr[cur];
			
		}
		public int sumFrom(int q, int p) { // returns sum of tarr[q] through tarr[p] inclusive
			return sumFromRec(startind, endind, Math.max(q, startind), Math.min(p, endind), 0);
		}
		public int sumFromRec(int sind, int eind, int x, int y, int cur) {
			// get sum [x, y] using divide and conquer
			// [startind, endind] = range of tree with root cur, sum = treearr[cur]
			
			if (x <= sind && y >= eind)  // [x, y] contains [startIndex, endIndex]
				return treearr[cur]; // everything is in range

			if (eind < x || sind > y)   // [x, y] doesn't overlap [startIndex, endIndex]
				return 0;

			int mid = (sind + eind) / 2; // split the range

			// check the children using the appropriate ranges, then add
			return sumFromRec(sind, mid, x, y, leftind(cur))
					+ sumFromRec(mid + 1, eind, x, y, rightind(cur));
		}
		
		public void setValue(int pos, int val) {
			// arr[pos] = val;
			
			int diff = val - numarr[pos]; // difference between new and old value
			numarr[pos] = val; // re-assign in numarr
			setValueRec(startind, endind, pos, diff, 0); // recursion
		}

		public void setValueRec(int sind, int eind, int pos, int diff, int cur) {
			if( pos >= sind && pos <= eind ) { // if pos is in the subtree
				treearr[cur] = treearr[cur] + diff; // reassign in the tree, account for changes

				if (sind != eind ) { // until you go through everything, split
					int mid = (sind + eind) / 2;
					setValueRec(sind, mid, pos, diff, leftind(cur)); // reassign all children
					setValueRec(mid+1, eind, pos, diff, rightind(cur));
				}
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
