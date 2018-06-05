
public class Test1 {
	
	public static int table;
	
	
	int tree;
	public Test1() {
		// constructor
		tree = 1;
		
	}
	public void incTree() {
		tree++;
	}
	
	public static void adjust(String a) {
		a = "42";
	}
	public static void test() {
		System.out.println("Hello, world!");
		System.out.println("My name is Sarah Chen.");
		Integer x = 4;
		Integer y = 5;
		System.out.println(x.compareTo(y));
		
		String b = "10";
		System.out.println(b);
		adjust(b);
		System.out.println(b);
	}
	public static void div() {
		int x = 5;
		int y = 2;
		System.out.println(x/y);
		System.out.println((double) x/y);
		double a = 5;
		int b = 2;
		System.out.println(a/b);
		int c = 5;
		double d = 2;
		System.out.println(c/d);
	}
	public static void cast() {
		int a = 2;
		double b = (double) a;
		System.out.println(a);
		System.out.println(b);
	}
	public static void cTesting() {
		System.out.println("ab");
		Test1 n = new Test1();
		System.out.println(n.tree);
		
		table = 3;
		System.out.println("here: " + table);
		System.out.println(table);
		System.out.println(n.toString());
	}
	public static void decl() {
		int x = 1, y = 2;
		int a, b;
		b = 2;
		System.out.println(x);
		System.out.println(y);
	}
	public static void infin() {
		double a = -10.0;
		double b = 0.0;
		System.out.println(a/b);
	}
	public static void fin() {
		final int x;
		x = 5;
	}
	public static void orderOfOp() {
		int x = 3;
		int y = 5;
		System.out.println(x += y *= 2);
	}
	public static void printTest() {
		System.out.println("a");
		System.out.print("b");
		System.out.print("c");
		System.out.println("d");
	}
	public static void div0() {
		int x = 2;
		int y = 0;
		System.out.println(3/0);
	}
	public static void addd(int a) {
		a += 2;
		System.out.println(a);
	}
	public static void stringtest() {
		String a = "abc";
		String b = new String("abc");
		System.out.println(a == b);
		System.out.println(a.equals(b));
	}
	public static void main(String args[]) {
		/*
		int x;
		x = 4;
		System.out.println(x);
		//double x = 5.234266545463546354634562356234532543252345234523452345234565463456342345235234520;
		//System.out.println(3.0==x*(3.0/x));
		 * 
		 */
		int[] b;
		System.out.println(b);
	}
	
}
