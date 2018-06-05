import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class Convert {
	
	public static void main(String[] args) throws IOException {
		String myString = "МОРЕ РОЕТ АТОМ";
		byte ptext[] = myString.getBytes("UTF-8");
		//String value = new String(ptext, " ISO-8859");
		//System.out.println(Arrays.toString(ptext));
		//System.out.println(value);
		
		String ans = "HaH";
		
		
		//NOT  + FACE.
		String bin = new BigInteger("BAD", 16).toString(2);
		System.out.println(bin);
		//int bin = 0x101110101101;
		
		
		/*
		int bitmask = 0x000F; 
        int val = 0x2222;
        
        System.out.println(bitmask);
        System.out.println(val);
        
     // prints "2"
        System.out.println(val & bitmask);
        */
		
		
		
		int b1 = 0x0BAD;
		String bin32 = new BigInteger("BAD", 16).toString(2);
		System.out.println(bin32);
		int b15 = ~0x0BAD;
		System.out.println(b15);
		
		int b2 = 0xFACE;
		int x = ~0x0BAD+b2;
		System.out.println("x:" + x);
		String bin2 = new BigInteger("61216", 10).toString(16);
		//String bin2 = new BigInteger("-67196", 16).toString(2);
		System.out.println(bin2);
		
		
		
	}
	
	
}
