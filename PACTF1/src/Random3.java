import java.io.*;
import java.util.*;

public class Random3 {
	public static void main(String[] args) throws IOException {
		int n1 = -2069001995;
		int n2 = -1997362081;

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("randomFile.out")));
		
		//for (long i = -1000000; i < 1000000; i++) {
		//for (long i = -2000000; i < -1000000; i++) {
		//for (long i = 1000000; i < 2000000; i++) {
		for (long i = 2000000; i < 9000000; i++) {
			Random rand = new Random(i);
			//if (rand.nextInt() == n1 && rand.nextInt() == n2) {
			int rand1 = rand.nextInt();
			int rand2 = rand.nextInt();
			int rand3 = rand.nextInt();
			
			if (rand1 == n1 && rand2 == n2) {
				System.out.println("seed: " +  i + " 3: " + rand3);
				pw.println("seed: " +  i + " 3: " + rand3);
			}

			pw.close();
			
		}
		
	}
}
