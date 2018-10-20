import java.util.Arrays;

/*
 * 10/9/18 lesson
 * problem: given n <= 100 and k <= 10000, 
 * return middle digit or 2 middle digits of n^k
 * solution: use very simple high precision calculator using strings (or arrays)
 * 
 * not fast enough...
 */
public class ArrayCalc {
	public static void main(String args[]) {
		int n = 100;
		int k = 10000;

		System.out.println(pow(String.valueOf(n), k));
		
	}
	public static String pow(String a, int b) { // a <= 100, b <= 10000
		String total = "1";
		for (int i = 0; i < b; i++) {
			total = mult(total, a);
		}
		return total;
	}
	public static String mult(String a, String b) { // multiply 2 numbers
		String total = "";
		for (int i = 1; i <= a.length(); i++) {
			int ai = a.length()-i;
			int x = 0;
			if (ai >= 0 && ai < a.length())
				x = a.charAt(ai)-48;
			
			for (int j = 1; j <= b.length(); j++) {
				int zeros = i-1;
				
				int bj = b.length()-j;
				
				int y = 0;
				if (bj >= 0 && bj < b.length()) 
					y = b.charAt(bj)-48;
				zeros += j-1;
				
				int mult = x*y;
				String next = String.valueOf(mult);
				for (int k=0; k<zeros; k++) {
					next += "0";
				}
				
				total = add(total, next);
			}
		}
		while (total.charAt(0)=='0') {
			total = total.substring(1);
		}
		return total;
	}
	public static String add(String a, String b) { // add 2 numbers
		
		String c = "";
		int carry = 0;
		
		for (int i = 1; i <= Math.max(a.length(), b.length()); i++) {
			int ai = a.length()-i;
			int bi = b.length()-i;
			
			int x = 0;
			int y = 0;
			if (ai >= 0 && ai < a.length())
				x = a.charAt(ai)-48;
			if (bi >= 0 && bi < b.length()) 
				y = b.charAt(bi)-48;
			
//			System.out.println("x: " + x + " y: " + y);
			
			int toAdd = x+y+carry;
//			System.out.println("  toAdd: " + toAdd);
			
			c = String.valueOf(toAdd%10)+c;
//			System.out.println("  c: " + c);
			carry = toAdd/10;
		}
		c = String.valueOf(carry)+c;
		return c;
	}
}
