import java.util.*;
import java.io.*;

// done, hopefully works, similar to sum segment tree
// segment tree max structure

// node will hold maximum value over its covered interval

public class STMaxImp {
	public static void main(String[] args) {
		int[] nums = {-5, -2, -5, -1, 9};
		int n = nums.length;
		SegmentTreeMax A = new SegmentTreeMax(n);
		
		A.buildtree(nums);
		System.out.println(A.height);
		System.out.println(A.maxsize);
		System.out.println(Arrays.toString(A.treearr));
		System.out.println(A.maxFrom(0, 2));
		A.setValue(0, 5);
		System.out.println(Arrays.toString(A.treearr));
		System.out.println(A.maxFrom(0, 2));
	}
	public static class SegmentTreeMax {
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // number of layers of the tree
		int maxsize; // max size of binary tree with the set height
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
		public void buildtree(int[] numsIn) { 
			for (int i = 0; i < numarr.length; i++)
				numarr[i] = numsIn[i]; // copy in the nums array
			buildTreeRec(startind, endind, 0); 
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
			treearr[cur] = Math.max(buildTreeRec(lo, mid, leftind(cur)), 
	        		buildTreeRec(mid + 1, hi, rightind(cur)));
			
			return treearr[cur];
			
		}
		public int maxFrom(int q, int p) { // returns sum of tarr[q] through tarr[p] inclusive
			
			return maxFromRec(0, endind, q, p, 0);
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
				} else {
					// leaf node of pos, altered above, still return
					return treearr[cur];
				}
				
			} else { // out of range, no changes (pos < sind || pos > eind)
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
