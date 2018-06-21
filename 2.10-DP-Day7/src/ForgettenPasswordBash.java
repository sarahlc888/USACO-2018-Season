import java.io.*;
import java.util.*;

public class ForgettenPasswordBash {
	static String pw = "";
	static boolean b = false;
	public static void main(String args[]) throws IOException {
		// INPUT
		Scanner scan = new Scanner((System.in));
		StringTokenizer st = new StringTokenizer(scan.nextLine());

		int L = Integer.parseInt(st.nextToken()); // length of password
		int NW = Integer.parseInt(st.nextToken()); // number of words
		pw = scan.nextLine(); // password
		String[] dict = new String[NW];
		for (int i = 0; i < NW; i++) {
			dict[i] = scan.nextLine();
		}
		scan.close();

		// DP
		permute(dict, 0);


	}
	static boolean same(String a, String b) {
		if (a.length() != b.length()) return false;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == b.charAt(i) || a.charAt(i) == '?' || b.charAt(i) == '?') {
				// same
			} else { 
				return false;
			}
		}
		return true;
		
	}
	static void permute(String[] a, int k) {
		if (b) return;
		//System.out.println(Arrays.toString(a));
		if (k == 3) {

			// test the perm
			String p = "";
			for (int i = 0; i < k; i++) {
				p += a[i];
			}
			//System.out.println(p);
			if (same(pw, p)) { 	// if the perm works, add it to wokring perms
				System.out.println(p);
				b = true;
				return;
			}
			
		} else {
			for (int i = k; i < a.length; i++) {
				String temp = a[k];
				a[k] = a[i];
				a[i] = temp;

				permute(a, k + 1);

				temp = a[k];
				a[k] = a[i];
				a[i] = temp;
			}
		}

	}
}
