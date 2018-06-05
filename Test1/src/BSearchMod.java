import java.io.IOException;

public class BSearchMod {
	static int[] nums = {1, 2, 3, 3, 4, 4, 4, 7, 9};
	public static void main(String args[]) throws IOException {
		int lb = 4;
		System.out.println(bSearchMod(lb, 8, 0));
		
	}
	public static int bSearchMod(int lbound, int hi, int lo) { // mod from counting haybales
		// returns first value >= desired condition
		
		
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (lbound <= nums[lo]) {
			System.out.println("here1");
			return lo;
		} else if (lbound > nums[hi]) { // not in any
			System.out.println("here2");
			return -1;
		}
		
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		
		System.out.println("hi: " + hi + " lo: " + lo + " mid: " + mid);
		
		int curval = nums[mid]; // current value
		
		// narrow the range
		if (curval < lbound) { // go bigger
			lo = mid + 1;
			return bSearchMod(lbound, hi, lo);
		} else if (curval > lbound) { // go smaller
			hi = mid; 
			return bSearchMod(lbound, hi, lo);
		} else {
			hi = mid;
			//hi = mid+1;
			
			return bSearchMod(lbound, hi, lo);
			/*
			while (curval == reach(mid-1)) 
				mid--;
			*/
			// equals --> desired result
		}
		
	}
}
