import java.io.IOException;

public class ModBinSearch2 {
	
	public static void main(String args[]) throws IOException {
		int[] arr1 = {1, 2, 6, 8};
		System.out.println(leastAbove(arr1, 5));
		
	}
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
