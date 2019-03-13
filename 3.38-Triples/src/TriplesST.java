import java.io.*;
import java.util.*;
/*
 * from 12/11 lesson
 * segment tree or bit
 * 
 * 2 segment trees, idea similar to the idea in balanced photo:
 * count how many cows to the left are greater than entry i
 * count how many cows to the right are less than entry i
 * multiply the above values
 * add in the triples formed from all the same number
 * 
Use two segment trees. Go in ascending or descending order to check 
how many cows are greater than or less than the current one on the 
left and right respectively. number of triples for a given cow = L*R

Make sure to not mark duplicates as less than or greater than just because of the order they appear 
 */
public class TriplesST {
	public static void run(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		Pair[] parr = new Pair[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			parr[i] = new Pair(arr[i], i);
		}
		br.close();
//		System.out.println("arr: " + Arrays.toString(arr));
		
		// count triples that are all identical
		// count occurrences of each value
		TreeMap<Integer, Integer> countMap = new TreeMap<Integer, Integer>();
		for (int i = 0; i < N; i++) {
			if (countMap.containsKey(arr[i])) {
				countMap.put(arr[i], countMap.get(arr[i])+1);
			} else {
				countMap.put(arr[i], 1);
			}
		}
//		System.out.println("countmap: " + countMap);
		long numEqs = 0;
		Iterator it = countMap.keySet().iterator();
		while (it.hasNext()) {
			int curKey = (int) it.next();
			int numVals = countMap.get(curKey);
			 if (numVals >= 3) {
				 numEqs += numVals-2;
			 }
		}
//		System.out.println("NUMEQS: " + numEqs);
		Arrays.sort(parr);
		
		
		
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		int[] firstVal = new int[N]; // index of first entry in parr with same value as parr[i]
		for (int i = 0; i < N; i++)
			firstVal[i] = -1;
		for (int i = 0; i < N; i++) {
			int curVal = parr[i].val;
			if (map.containsKey(curVal)) {
				firstVal[i] = map.get(curVal);
			} else {
				map.put(curVal, i);
				firstVal[i] = i;
			}
		}
			
		int[] lastVal = new int[N]; // index of last entry in parr with same value as parr[i]
		
		int i = 0;
		while (i < N) {
			int curInd = i;
			if (curInd + 1 >= N) {
				lastVal[curInd] = curInd;
				break;
			}
			int curVal = parr[curInd].val;
			
			// proceed until a new value is found or the array ends
			i++;
			while (i < N && curVal == parr[i].val) 
				i++;
			int curLastInd = i-1;
			
			// mark the last index in the streak of common values
			for (int j = curInd; j <= curLastInd; j++) 
				lastVal[j] = curLastInd;
		}
		
//		System.out.println("parr: " + Arrays.toString(parr));
//		System.out.println("firsVal: " + Arrays.toString(firstVal));
//		System.out.println("lastVal: " + Arrays.toString(lastVal));
		
		// build segment tree
		SegmentTreeSum stree = new SegmentTreeSum(N);
		stree.buildtree(new int[N]);
		
		// go in ascending order
		int[] numLess = new int[N]; // number of elements less than curVal to the RIGHT of curVal
		for (i = 0; i < N; i++) { // index in parr
			int curInd = parr[i].ind; // index in arr
			int curVal = parr[i].val;
			
//			System.out.print("i: " + i + "  curVal: " + curVal + "  curInd: " + curInd);
//			System.out.println("  " + Arrays.toString(stree.numarr));
			int less; 
			less = stree.sumFrom(curInd+1, N-1);
			numLess[curInd] = less;
			
			// mark values
			if (i == lastVal[i]) { // if cur value is the last appearance of that value
				stree.setValue(curInd, 1);
				int j = i-1; // check entries before i in parr
				while (j >= 0 && lastVal[j] == i) {
					stree.setValue(parr[j].ind, 1); // mark the proper indices in arr
					j--;
				}
			}
		}
//		System.out.println("numless: " + Arrays.toString(numLess));
		
		// build segment tree
		stree = new SegmentTreeSum(N);
		stree.buildtree(new int[N]);

		// descending order
		int[] numGreater = new int[N]; // number of elements GREATER than curVal to the LEFT of curVal
		for (i = N-1; i >= 0; i--) { // index in parr
			int curInd = parr[i].ind; // index in arr
			int curVal = parr[i].val;
			
//			System.out.print("i: " + i + "  curVal: " + curVal + "  curInd: " + curInd);
//			System.out.println("  " + Arrays.toString(stree.numarr));
			int greater; 
			greater = stree.sumFrom(0, curInd-1);
			numGreater[curInd] = greater;

			// mark values
			if (i == firstVal[i]) { // if cur value is the first appearance of that value
				stree.setValue(curInd, 1);
				int j = i+1; // check entries after i in parr
				while (j < N && firstVal[j] == i) {
					stree.setValue(parr[j].ind, 1); // mark the proper indices in arr
					j++;
				}
			}
		}
//		System.out.println("numgreater: " + Arrays.toString(numGreater));
		
		long numTrips = numEqs;
		for (i = 0; i < N; i++) {
			numTrips += numLess[i]*numGreater[i];
		}
		
		
		
		System.out.println(numTrips);
		
		
		
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("triples.out")));

		pw.println();
		pw.close();
	}
	public static class Pair implements Comparable<Pair> {
		int val;
		int ind;

		public Pair(int a, int b) {
			val = a;
			ind = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by val
			if (val == o.val) return ind-o.ind;
			return val-o.val;
		}
		public String toString() {
//			return val + " " + ind;
			return val+"";
		}
	}
	public static class SegmentTreeSum {
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // num layers of the tree
		int maxsize; // max num nodes for a tree of height height
		int startind; // start of the tree
		int endind; // end of the tree
		
		public SegmentTreeSum(int n) { // constructor, n == length of "input" array
			numarr = new int[n]; // initialize
			
			// num layers needed to fit all nums on the bottom layer = 1 + log base 2 n
			height = (int) Math.ceil(Math.log(n)/Math.log(2)) + 1;  
			
			maxsize = (int) (Math.pow(2, height) - 1); // 1+2+4+8...  = 2^height - 1
			treearr = new int[maxsize]; // initialize the binary tree
			endind = n-1; // mark the end of the nums
		}
		public void buildtree(int[] numsIn) {
			for (int i = 0; i < numarr.length; i++)
				numarr[i] = numsIn[i]; // copy in the nums array
			
			buildTreeRec(startind, endind, 0); 
		}
		public int buildTreeRec(int lo, int hi, int cur) {
			// start from the root, use divide and conquer
			
			//  A		numarr:
			// B C		1 2
				
			// lo = 0, hi = 2, mid = 1
			// build(0, 1, 1) -> build(0, 0, 1) node[1] = 1 -> build(1, 2, 2) -> build(1, 1, 2) node[2] = 2

			// case: leafnode
			if (lo == hi) { // if all sections have been covered, assign current node the value and return
				treearr[cur] = numarr[lo]; 
				return treearr[cur]; 
			}
			
			int mid = (lo + hi)/2;
			
			// cur node = sum of its children. half the range as you go down a level
			treearr[cur] = buildTreeRec(lo, mid, leftind(cur)) + buildTreeRec(mid + 1, hi, rightind(cur));
			
			return treearr[cur];
		}
		public int sumFrom(int q, int p) { // returns sum of numarr[q] through numarr[p] inclusive
			return sumFromRec(startind, endind, q, p, 0);
		}
		public int sumFromRec(int sind, int eind, int x, int y, int cur) {
			// get sum [x, y] using divide and conquer (over numarr)
			// [startind, endind] = range of coverage in numarr for tree w/ root cur
			
			if (x <= sind && y >= eind)  // [x, y] contains [startIndex, endIndex]
				return treearr[cur]; // everything is in range

			if (eind < x || sind > y)   // [x, y] doesn't overlap [startIndex, endIndex]
				return 0; // don't contribute to sum

			int mid = (sind + eind) / 2; // split the range

			// check&add the children using the appropriate ranges
			return sumFromRec(sind, mid, x, y, leftind(cur)) + sumFromRec(mid + 1, eind, x, y, rightind(cur));
		}
		
		public void setValue(int pos, int val) {
			// arr[pos] = val;
			int diff = val - numarr[pos]; // difference between new and old value
			numarr[pos] = val; // re-assign in numarr
			setValueRec(startind, endind, pos, diff, 0); // recursion
		}
		public void setValueRec(int sind, int eind, int pos, int diff, int cur) {
			if (pos >= sind && pos <= eind) { // if pos is in the subtree of cur
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
