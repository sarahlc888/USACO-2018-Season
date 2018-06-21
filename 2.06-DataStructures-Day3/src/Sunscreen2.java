import java.io.*;
import java.util.*;

/*
 * doesn't work
 * idea:
 * greedy for sunscreens and cows
 * 
 */
public class Sunscreen2 {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(System.in);
		StringTokenizer st = new StringTokenizer(scan.nextLine());
		int C = Integer.parseInt(st.nextToken()); // number of cows
		int L = Integer.parseInt(st.nextToken()); // number of bottles of lotion
		TreeSet<Cow> cows = new TreeSet<Cow>(a1Comparator); 
		
		for (int i = 0; i < C; i++) {
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken()); // min
			int b = Integer.parseInt(st.nextToken()); // max
			cows.add(new Cow(a, b));
		}
		
		PriorityQueue<Integer> bottles = new PriorityQueue<Integer>(1, a2Comparator);
		
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(scan.nextLine());
			int a = Integer.parseInt(st.nextToken()); // SPF
			int b = Integer.parseInt(st.nextToken()); // cover
			for (int j = 0; j < b; j++) {
				bottles.add(a);
			}
		}
		int count = 0;
		
		// move through the bottles
		while (!bottles.isEmpty() && !cows.isEmpty()) {
			int curBottle = (int) bottles.peek();
			//System.out.println(curBottle);
			//System.out.println(curBottle);
			// move through the cows
			Iterator<Cow> it = cows.iterator();
			Cow curCow = new Cow(-1, -1);
			
			while (it.hasNext()) {
				curCow = it.next();
				
				// if the bottle is above the curCow's min
				if (curCow.minS <= curBottle) {
					//cows.remove(curCow);
					
					break;
				}
			}
			
			if (curBottle <= curCow.maxS) {
				//System.out.println("work");
				bottles.poll();

				count++;
			}
			if (curBottle < curCow.minS) {
				bottles.poll();
			}
			//
			cows.remove(curCow);
		}
			
			/*
			// get the greatest less than curBottle
			int ind = greatestBelow(lbounds, curBottle.SPF); 
			
			//System.out.println();
			//System.out.println(curCow);
			//System.out.println(curBottle);
			
			
			if (curCow.minS <= curBottle.SPF && curBottle.SPF <= curCow.maxS) {
				//System.out.println("works");
				// if the bottle works for the cow
				cows.poll(); // take out the cow
				if (curBottle.cover == 1) {
					//System.out.println("  a");
					bottles.poll();
				} else {
					//System.out.println("  b");
					curBottle.cover -= 1;
				}
				count++;
			} else if (curBottle.SPF < minMinSPF) {
				// bottle doesn't work, too small for ANY cow
				bottles.poll();
			} else {
				cows.poll();
			}
			*/
		
		System.out.println(count);
		
	}
	public static class Bottle implements Comparable<Bottle> {
		int SPF;
		int cover;
		public Bottle(int a, int b) {
			SPF = a;
			cover = b;
		}
		public int compareTo(Bottle c) {
			if (SPF < c.SPF) return -1;
			if (SPF > c.SPF) return 1;
			return 0;
		}
		public String toString() {
			return SPF + " " + cover;
		}
	}
	public static class Cow implements Comparable<Cow> {
		int minS;
		int maxS;
		public Cow(int a, int b) {
			minS = a;
			maxS = b;
		}
		public int compareTo(Cow c) {
			
			if (maxS < c.maxS) return -1;
			if (maxS > c.maxS) return 1;
			if (minS < c.minS) return -1;
			if (minS > c.minS) return 1;
			return 0;
		}
		public String toString() {
			return minS + " " + maxS;
		}
	}
	public static Comparator<Cow> a1Comparator = new Comparator<Cow>(){

		public int compare(Cow a, Cow c) {
			if (a.maxS == c.maxS) return a.minS - c.minS;
			return a.maxS - c.maxS;
		}

	};
	public static Comparator<Integer> a2Comparator = new Comparator<Integer>(){

		public int compare(Integer a, Integer c) {
			return a-c;
		}

	};
	public static int greatestBelow(int[] arr, long val) {
		// returns greatest i <= val

		int lo = -1;
		int hi = arr.length-1;

		while (lo < hi) {
			int mid = (lo + hi + 1)/2; // +1 so lo can become hi in 1st if
			
			if (arr[mid] <= val) { // if mid is in range, increase
				lo = mid;
			} else { // mid is not in range, decrease
				hi = mid - 1;
			}
		}
		return hi;
		
	}
	public static int leastAbove(int[] arr, long val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;
		
		while (lo < hi) {
			int mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
			System.out.println("lo: " + lo + " hi: " + hi + " mid: " + mid);

			if (arr[mid] >= val) { // if mid is in range
				hi = mid;
			} else { // mid is not in range
				lo = mid + 1;
			}
		}
		return lo;
	}
}
