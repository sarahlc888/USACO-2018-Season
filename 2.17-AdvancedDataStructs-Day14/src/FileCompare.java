import java.io.*;
import java.util.*;

public class FileCompare {
	public static void main(String args[]) throws IOException {
		boolean identical = true;
		BufferedReader br1 = new BufferedReader(new FileReader("f1.in"));
		BufferedReader br2 = new BufferedReader(new FileReader("f2.in"));
		for (int j = 0; j < 190; j++) {
			String a = br1.readLine();
			String b = br2.readLine();
			if (a == null & b == null) continue;
			if ((a == null ^ b == null) || 
					!a.equals(b)) {
				System.out.println("line: " + (j+1));
				identical = false;
			}
		}
		System.out.println(identical);


	}
}
