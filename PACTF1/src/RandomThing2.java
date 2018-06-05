import java.io.*;
import java.util.*;

public class RandomThing2 {
	public static void main(String[] args) throws IOException {
		//Random random = new Random();
		//long v1 = random.nextInt();
		//long v2 = random.nextInt();
		long v1 = -2069001995;
		long v2 = -1997362081;
		
		long seed = 0;
		long multiplier = 25214903917l;
		long addend = 11l;
		for (int i = 0; i < 65536; i++) {
		    seed = v1 * 65536 + i;
		    //if ((((seed * multiplier) + 11) >>> 16) == v2) {
		    if (((((seed * multiplier) + addend) & 281474976710655l) >>> 16) == v2) {
		        System.out.println("Seed found: " + seed);
		        
		        seed = v2 * 65536 ;
		        long third = ((((seed * multiplier) + 11) & 281474976710655l) >>> 16);
				System.out.println(third);
				//System.out.println(random.nextInt());
				
				
		        break;
		    }
		}
		
		
	}
	
	
	
}
