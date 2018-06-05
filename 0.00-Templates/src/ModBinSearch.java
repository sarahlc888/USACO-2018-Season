
public class ModBinSearch {
	// int startInd = bSearch1(ptsx, LLC[0], N-1, 0);
	public static int bSearch1(int[] pts, int lbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (lbound <= pts[lo]) {
			return lo;
		} else if (lbound > pts[hi]) { // not in any 
			return -1;
		}
		
		// basecase
		// now lbound > pts[lo] and lbound <= pts[hi]
		if (hi - lo == 1) {
			return hi;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid] < lbound) {
			// go bigger
			lo = mid + 1;
			return bSearch1(pts, lbound, hi, lo);
		} else if (pts[mid] > lbound) {
			// go smaller
			hi = mid;     // lbound = 5;    pts[mid] = 6   pts[mid-1] = 4
			return bSearch1(pts, lbound, hi, lo);
		} else {
			while (pts[mid] == pts[mid-1]) {
				mid--;
			}
			return mid;
			// equals --> desired result
		}
		
	}
	public static int bSearch2(int[] pts, int hbound, int hi, int lo) {
		// values -- basecases for when hi - lo range reduced already
		
		// exception catching
		if (hbound >= pts[hi]) {
			//System.out.println("t1");
			return hi;
		} else if (hbound < pts[lo]) { // no point are in the interval
			//System.out.println("t2");
			return -1;
		}
		
		// basecase for when hi - lo range is tiny
		// now hbound < pts[hi] and hbound >= pts[lo]
		if (hi - lo == 1) {
			return lo;
		}
		
		int mid = (hi + lo)/2;
		
		// narrow down
		if (pts[mid] < hbound) {
			// go bigger
			lo = mid;
			//System.out.println("t4");
			return bSearch2(pts, hbound, hi, lo);
		} else if (pts[mid] > hbound) {
			// go smaller
			hi = mid - 1;
			return bSearch2(pts, hbound, hi, lo);
		} else {
			while (pts[mid] == pts[mid+1]) {
				mid++;
			}
			return mid;
			// last case is that pts[mid] == hbound --> desired result
		}
		
	}
}
