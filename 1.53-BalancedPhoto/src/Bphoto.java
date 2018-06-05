import java.io.*;
import java.util.*;
/*
 * USACO 2017 January Contest, Gold
 * Problem 1. Balanced Photo
 * 
 * 10/10 test cases
 * 
 * Uses:
 * comparator
 * segment tree
 */
public class Bphoto {
	public static void main(String args[]) throws IOException {
		// INPUT
		BufferedReader br = new BufferedReader(new FileReader("bphoto.in"));
		int N = Integer.parseInt(br.readLine());
		
		int[] h = new int[N]; // original height array
		int[][] sortedh = new int[N][2]; // sorted h; sortedh[i][0] = original index
		
		for (int i = 0; i < N; i++) {
			h[i] = Integer.parseInt(br.readLine());
			sortedh[i][0] = i;
			sortedh[i][1] = h[i];
		}
		br.close();
		
		for (int i = 0; i < N; i++) {
			//System.out.println(sortedh[i][0] + " | " + sortedh[i][1]);
		}
		//System.out.println();
		
		// sort sortedh with comparator arrComp
		Arrays.sort(sortedh, arrComp);
		
		// array to build a segment tree off of
		int[] stArr = new int[N];
		
		// build the segment tree
		STsum st = new STsum(N);
		st.buildtree(stArr);
		
		// count of unbalanced cows
		int count = 0;
		
		//System.out.println(st.sumFrom(0, 2));
		
		// loop through sortedh from the end to the start
		for (int i = N-1; i >= 0; i--) {
			//System.out.println("i: " + i);
			
			int curInd = sortedh[i][0];
			int curH = sortedh[i][1];
			
			//System.out.println("  " + curInd + "| " + curH);
			
			// mark the index in the tree
			st.setValue(curInd, 1); 
			
			//System.out.println("  " + Arrays.toString(st.numarr));
			
			// calculate left and right
			int left = st.sumFrom(0, curInd-1);
			int right = st.sumFrom(curInd+1, N-1);
			//System.out.println(st.sumFrom(0, N-1));
			//int right = N - curInd - left;
			
			
			// if unbalanced, increment count
			if (left > right && left > right * 2 || 
					right > left && right > left * 2) {
				count++;
			}
			
			//System.out.println("  l: " + left + " r: " + right);
		}
		
		//System.out.println(count);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
		pw.println(count);
		pw.close();
	}
	public static Comparator<int[]> arrComp = new Comparator<int[]>(){

		@Override
		public int compare(int[] x, int[] y) {
			if (x[1] > y[1]) return 1;
			else if (x[1] < y[1]) return -1;
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
