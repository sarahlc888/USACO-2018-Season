import package2.*;

public class Test2 extends Test {
	private int a;
	private int b;
	private String c;
	
	public Test2() {
		a = 0;
		b = 0;
		c = "";
	}
	public void incA() {
		a++;
	}
	public static void main(String[] args) {
		Test t = new Test();
		Test t2 = new Test2();
		Test3 t3 = new Test3();
		
		System.out.println(t3.incD());
	}
	
}
