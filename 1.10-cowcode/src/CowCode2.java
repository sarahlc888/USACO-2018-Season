import java.io.*;
import java.util.*;

// doesn't work either

public class CowCode2 {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(new File("cowcode.in"));
		String S = scan.next(); // the string to be encoded
		long N = scan.nextLong(); // the character to find
		scan.close();
		
		ArrayList<String> code = new ArrayList<String>();
		
		int len = S.length(); // how many characters there are in the code
		code.add(S);
		
		
		while (len * 2 < N) { // while the code isn't long enough
			len *= 2;
			ArrayList<String> code2 = (ArrayList<String>) code.clone();
			String first = code.get(0);
			String last = code.get(code.size() - 1);
			
			code2.remove(0);
			code2.add(0, (last.charAt(last.length() - 1)) + first);
			code2.remove(code.size() - 1);
			code2.add(last.substring(0, last.length() - 1));
			
			code.addAll(code2);
		}
		
		
		String ans = "";
		// after the next iteration will include the desired index, do the same thing but count
		long findInd = N - (long)len;
		for (int i = 0; i < code.size(); i++) {
			String cur = code.get(i);
			if (findInd > cur.length()) { // if there thing you're looking for is not in the current, decrease and move on
				findInd -= cur.length();
			} else {
				long curInd = 0;
				for (int j = 0; j < cur.length(); j++) {
					curInd++;
					if (curInd == findInd) {
						ans = String.valueOf(cur.charAt(j));
					}
				}
			}
		}
		
		
		
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
