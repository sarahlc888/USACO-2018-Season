import java.io.*;
import java.util.*;

// doesn't work

public class CowCode {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("cowcode.in"));
		String S = scan.next(); // the string to be encoded
		long N = scan.nextLong(); // the character to find
		scan.close();
		
		String code = S;
		
		while (code.length() < N) {
			code += rotate(code);
		}
		
		char ans = code.charAt((int)(N-1));
		
		for (int i = 0; i < code.length(); i++) {
			System.out.println(i+1+ ": " + code.charAt(i));
		}
		
		System.out.println(code);
		System.out.println(ans);
		
		PrintWriter pw = new PrintWriter(new File("cowcode.out"));
		pw.println(ans);
		pw.close();
		
	}
	public static String rotate(String A) {
		// System.out.println("A: " + A);
		String x = String.valueOf(A.charAt(A.length() - 1));
		String y = A.substring(0, A.length() - 1);
		
		// System.out.println(x);
		// System.out.println(y);
		
		return x+y;
	}
	public static String encode(String A) {
		String ret = A + A.charAt(A.length() - 1) + A.substring(0, A.length() - 1);
		return ret;
	}
}
