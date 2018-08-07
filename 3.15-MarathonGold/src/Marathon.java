import java.io.*;
import java.util.*;
/* 
 * USACO 2014 December Contest, Gold
 * Problem 2. Marathon
 * 
 * 8/6-7
 * 
 * binary index tree for sums
 * seg tree for maxes
 * 
 * 10/10 test cases
 */
public class Marathon {
	static Pair[] pts;
	public static void main(String args[]) throws IOException {
		// input
		BufferedReader br = new BufferedReader(new FileReader("marathon.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // num checkpoints
		int Q = Integer.parseInt(st.nextToken()); // num queries
		pts = new Pair[N]; // (x, y) locations of points
		int[] d = new int[N]; // d[i] = dist from i-1 to i
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			pts[i] = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			if (i != 0) d[i] = dist(i, i-1);
		}
		
		BIT b = new BIT(N-1); // BIT to keep track of distances
		for (int i = 1; i < N; i++) // init
			b.update(i, d[i]);
		
		// seg tree to track dist added by checkpoint
		// or alternatively how much dist could decrease if that pt is skipped
		STMax sMax = new STMax(N); 
		int[] addedDist = new int[N];
		for (int i = 1; i < N-1; i++) {
			int d1 = d[i]; // dist(i-1, i)
			int d2 = d[i+1]; // dist(i, i+1)
			int d3 = dist(i-1, i+1); // dist when you skip the node
			addedDist[i] = d1+d2-d3; // calc "extra" dist
		}
		sMax.buildtree(addedDist);
				
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));

		for (int i = 0; i < Q; i++) { // process queries
			st = new StringTokenizer(br.readLine());
			String type = st.nextToken(); 
			if (type.equals("Q")) { // query
				int start = Integer.parseInt(st.nextToken())-1; // adjust indexing
				int end = Integer.parseInt(st.nextToken())-1; // adjust indexing
				
				int totDist = b.query(start+1, end); // dist from start to end of subroute
				int maxThere = sMax.maxFrom(start+1, end-1); // can't skip 1st or last pt
				maxThere = Math.max(0, maxThere);
				// return: total distance - max decrease possible from skipping a pt
				pw.println(totDist-maxThere); 
			} else { // update
				int num = Integer.parseInt(st.nextToken())-1; // adjust indexing
				int nx = Integer.parseInt(st.nextToken());
				int ny = Integer.parseInt(st.nextToken());
				// update pts
				pts[num].x = nx;
				pts[num].y = ny;
				
				// update d array and BIT (the update func is based off of how much to add)
				if (num-1 >= 0) { // bounds
					b.update(num, dist(num-1, num) - d[num]);
					d[num] = dist(num-1, num);
				}
				if (num+1 < N) { // bounds
					b.update(num+1, dist(num, num+1) - d[num+1]);
					d[num+1] = dist(num, num+1);
				}
				
				// update seg tree for num-1, num, num+1
				if (num-1 > 0) 
					sMax.setValue(num-1, d[num-1]+d[num]-dist(num-2, num));
				if (num+1 < N && num-1 >= 0) 
					sMax.setValue(num, d[num]+d[num+1]-dist(num-1, num+1));
				if (num+2 < N) 
					sMax.setValue(num+1, d[num+1]+d[num+2]-dist(num, num+2));
			}
		}
		br.close();
		pw.close();
	}
	public static int dist(int i, int j) { // returns dist between pt i and pt j
		return Math.abs(pts[i].x-pts[j].x) + Math.abs(pts[i].y-pts[j].y);
	}
	public static class BIT { // 1...N indexing required here but it's automatic
		int[] a; // array
		int[] bit; // bit
		int MAX;
		
		public BIT(int n) { // 1...n
			a = new int[n+1];
			bit = new int[n+1];
			MAX = n;
		}
		
		public void update(int i, int v) { // O(log N)
			// a[i] += v and proper updates everywhere else
			a[i] += v;
			while (i <= MAX) { // start from i, it's <= if max == length precisely
				bit[i] += v;
				i += (i & (-i)); // add least significant bit (it works out)
			}
		}
		public int query(int i) { // O(log N)
			// returns sum of a[1]... a[i]
			
			int sum = 0;
			while (i > 0) { // start from i
				sum += bit[i];
//				System.out.println("sum: " + sum);
				i -= (i & (-i)); // subtract least significant bit (it works out)
			}
//			System.out.println("SUM: " + sum);
			return sum;
		}
		public int query(int i, int j) { // O(log N) (bc of the other call
			return (query(j)-query(i-1));
		}
	}
	public static class STMax { // seg tree
		int[] numarr; // number array, stores all the original numbers
		int[] treearr; // tree array, stores all the numbers and sums
		int height; // number of layers of the tree
		int maxsize; // max nodes able to fit in the set height
		int startind; // start of the tree
		int endind; // end of the tree
		
		
		public STMax(int n) { // constructor for a tree
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
	public static class Pair implements Comparable<Pair> {
		int x;
		int y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public int compareTo(Pair o) { // sort by x, then y
			if (x == o.x) return y-o.y;
			return o.x-x;
		}
		public String toString() {
			return x + " " + y;
		}
	}


}
