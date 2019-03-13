import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class sort {
	public static void main(String args[]) throws IOException {
		// acceptable names
		String[] names = new String[5000];
		Scanner scan = new Scanner(new FileReader("dict.txt"));
		int i = 0;
		while (scan.hasNextLine()) {
			names[i] = scan.nextLine();
			i++;
		}
		scan.close();
		System.out.println(bSearch(names, "GRE"));
		System.out.println("!: " + comp("GREG", "GRE"));
//		System.out.println("GRE".compareTo("GRF"));
	}
	public static int bSearch(String[] arr, String val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;
		int mid = 0;
		
		while (lo <= hi) {
			mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
			System.out.println("lo: " + lo + " mid: " + mid + " hi: " + hi);
			if (comp(arr[mid],val) > 0) { // if mid is in range
				hi = mid-1;
			} else if (comp(arr[mid],val) < 0){ // mid is not in range
				lo = mid+1;
			} else { // == val
				break;
			}
			
		}
		if (comp(arr[mid],val)==0) {
			return mid; // make sure it works
		} else {
			return -1;
		}
	}
	public static int comp(String a, String b) {
		int curLen = Math.min(a.length(), b.length());
		for (int i = 0; i < curLen; i++ ) {
			if (a.charAt(i) != b.charAt(i)) {
				return a.charAt(i)-b.charAt(i);
			}
		}
		return 0;
	}
}
