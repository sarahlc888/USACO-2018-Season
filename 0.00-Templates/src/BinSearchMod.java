import java.util.Arrays;
/*
 * pretty much tested
 */
public class BinSearchMod {
	public static boolean[] arr1;
	public static boolean[] arr2;
	
	public static void main(String[] args) {
		int N = 5; // length of array
		int M = 2; // index of first false entry
		arr1 = new boolean[N];
		for (int i = 0; i < M; i++) {
			arr1[i] = true;
		}
		arr2 = new boolean[N];
		for (int i = M; i < N; i++) {
			arr2[i] = true;
		}
		System.out.println(Arrays.toString(arr1));
		System.out.println(bSearchMod1(N-1, 0));
		//System.out.println(Arrays.toString(arr2));
		//System.out.println(bSearchMod2(N-1, 0));
	}

	public static int bSearchMod1(int hi, int lo) { // modified bsearch
		// returns highest i that is true arr1[i]
		
		// values -- basecases for when hi - lo range reduced already
		
		// catch outer cases
		if (arr1[hi]) { // if hi already works
			return hi;
		} else if (!arr1[lo]) { // if even lo doesn't work
			System.out.println("lo: " + lo + " hi: " + hi);
			return -1;
		}
		
		// basecase: now hi doesn't work, so lo must work
		if (hi - lo == 1) {
			System.out.println("lo: " + lo + " hi: " + hi);

			return lo;
		}
		
		int mid = (hi + lo)/2;
		boolean curWorks = arr1[mid]; // current status
			
		// narrow the range
		if (curWorks) { // go bigger
			lo = mid; // don't exclude bc the mid could be the last one that works!
			return bSearchMod1(hi, lo);
		} else if (!curWorks) { // go smaller
			hi = mid - 1; 
			return bSearchMod1(hi, lo);
		} else {
			hi = mid;
			//hi = mid+1;
			
			return bSearchMod1(hi, lo);
			/*
			while (curval == reach(mid-1)) 
				mid--;
			*/
			// equals --> desired result
		}
		
	}
	public static int bSearchMod2(int hi, int lo) { // modified bsearch
		// returns lowest i that is true for arr2[i]
		
		// values -- basecases for when hi - lo range reduced already
		
		// catch outer cases
		if (arr2[lo]) { // if lo already works
			return lo;
		} else if (!arr2[hi]) { // if even hi doesn't work
			return -1;
		}
		
		// basecase: now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) return hi;
		
		int mid = (hi + lo)/2;
		boolean curWorks = arr2[mid]; // current status
			
		// narrow the range
		if (!curWorks) { // go bigger
			lo = mid + 1;
			return bSearchMod2(hi, lo);
		} else if (curWorks) { // go smaller
			hi = mid; 
			return bSearchMod2(hi, lo);
		} else {
			hi = mid;
			
			return bSearchMod2(hi, lo);
			/*
			while (curval == reach(mid-1)) 
				mid--;
			*/
			// equals --> desired result
		}
		
	}
}
