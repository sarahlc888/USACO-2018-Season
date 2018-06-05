import java.util.*;
import java.io.*;

// segment tree sum structure
// useful for when you need to CHANGE the numarr (faster than ps array in that case)

public class STSumImp {
	public static void main(String[] args) {
		int[] nums = {1, 2, 5, 7, 9};
		int n = nums.length;
		SegmentTreeSum A = new SegmentTreeSum(n);
		
		A.buildtree(nums);
		System.out.println(A.height);
		System.out.println(A.maxsize);
		System.out.println(Arrays.toString(A.treearr));
		System.out.println(A.sumFrom(0, 4));
		A.setValue(0, 3);
		System.out.println(A.sumFrom(0, 4));
	}
	public static class SegmentTreeSum {
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // number of layers of the tree
		int maxsize; // max nodes able to fit in the set height
		int startind; // start of the tree
		int endind; // end of the tree
		
		
		public SegmentTreeSum(int n) { // constructor for a tree
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
			
			// build the tree, cur node is the sum of its children
			// half the range as you go down a level
			// when you reach a leaf node, lo will == hi
			treearr[cur] = buildTreeRec(lo, mid, leftind(cur)) 
	        		+ buildTreeRec(mid + 1, hi, rightind(cur));
			
			return treearr[cur];
			
		}
		public int sumFrom(int q, int p) { // returns sum of tarr[q] through tarr[p] inclusive
			return sumFromRec(startind, endind, q, p, 0);
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
