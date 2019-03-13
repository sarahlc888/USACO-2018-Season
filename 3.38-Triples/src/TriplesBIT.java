import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
 * see TriplesST for notes
 */
public class TriplesBIT {
	public static void main(String[] args) throws IOException {
		run("triples.in");
	}
	public static void run(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N+1];
		Pair[] parr = new Pair[N+1];
		parr[0] = new Pair(-100, 0); // pad the first index
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			parr[i] = new Pair(arr[i], i);
		}
		br.close();
		
		// count triples that are all identical
		// count occurrences of each value
		TreeMap<Integer, Integer> countMap = new TreeMap<Integer, Integer>();
		for (int i = 1; i <= N; i++) {
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
		
//		System.out.println("arr: " + Arrays.toString(arr));
//		System.out.println("parr: " + Arrays.toString(parr));
		
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		int[] firstVal = new int[N+1]; // index of first entry in parr with same value as parr[i]
		for (int i = 1; i <= N; i++)
			firstVal[i] = -1;
		for (int i = 1; i <= N; i++) {
			int curVal = parr[i].val;
			if (map.containsKey(curVal)) {
				firstVal[i] = map.get(curVal);
			} else {
				map.put(curVal, i);
				firstVal[i] = i;
			}
		}
			
		int[] lastVal = new int[N+1]; // index of last entry in parr with same value as parr[i]
		
		int i = 1;
		while (i <= N) {
			int curInd = i;
			if (curInd + 1 >= N) {
				lastVal[curInd] = curInd;
				break;
			}
			int curVal = parr[curInd].val;
			
			// proceed until a new value is found or the array ends
			i++;
			while (i <= N && curVal == parr[i].val) 
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
		BIT bit = new BIT(N);
		
		
		// go in ascending order
		int[] numLess = new int[N+1]; // number of elements less than curVal to the RIGHT of curVal
		for (i = 1; i <= N; i++) { // index in parr
			int curInd = parr[i].ind; // index in arr
			int curVal = parr[i].val;
			
//			System.out.print("i: " + i + "  curVal: " + curVal + "  curInd: " + curInd);
//			System.out.println("  " + Arrays.toString(bit.a));
			int less; 
			less = bit.query(curInd+1, N);
			numLess[curInd] = less;
			
			// mark values
			if (i == lastVal[i]) { // if cur value is the last appearance of that value
				bit.update(curInd, 1);
				int j = i-1; // check entries before i in parr
				while (j > 0 && lastVal[j] == i) {
					bit.update(parr[j].ind, 1); // mark the proper indices in arr
					j--;
				}
			}
		}
//		System.out.println("numless: " + Arrays.toString(numLess));
		
		// build segment tree
		bit = new BIT(N);

		// descending order
		int[] numGreater = new int[N+1]; // number of elements GREATER than curVal to the LEFT of curVal
		for (i = N; i > 0; i--) { // index in parr
			int curInd = parr[i].ind; // index in arr
			int curVal = parr[i].val;
			
//			System.out.print("i: " + i + "  curVal: " + curVal + "  curInd: " + curInd);
//			System.out.println("  " + Arrays.toString(stree.numarr));
			int greater; 
			greater = bit.query(1, curInd-1);
			numGreater[curInd] = greater;

			// mark values
			if (i == firstVal[i]) { // if cur value is the first appearance of that value
				bit.update(curInd, 1);
				int j = i+1; // check entries after i in parr
				while (j <= N && firstVal[j] == i) {
					bit.update(parr[j].ind, 1); // mark the proper indices in arr
					j++;
				}
			}
		}
//		System.out.println("numgreater: " + Arrays.toString(numGreater));
		
		long numTrips = numEqs;
		for (i = 1; i <= N; i++) {
			numTrips += numLess[i]*numGreater[i];
		}
		
		
		
		System.out.println(numTrips);
		
		
		
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("triples.out")));

		pw.println();
		pw.close();
	}
	public static class BIT {
		// 1...n indexing REQUIRED, 0 just does nothing
		int[] a;
		int[] bit;
		int MAX;
		
		public BIT(int n) { // 1...n
			a = new int[n+1];
			bit = new int[n+1];
			MAX = n;
		}
		public void update(int i, int v) { // O(log N)
			// a[i] += v and proper updates everywhere else
			a[i]+=v;
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
				i -= (i & (-i)); // subtract least significant bit (it works out)
				// that brings you to the "parent" 
				// bit[i] has sum of parent+1 to i
				// will eventually get back to root 0
			}
			return sum;
		}
		public int query(int i, int j) { // O(log N) (bc of the other call
			// inclusive
			return (query(j)-query(i-1));
		}
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
}
