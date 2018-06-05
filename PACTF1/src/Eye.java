import java.io.*;
import java.util.*;

public class Eye {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Eye.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Eye.out")));

		
		for (int i = 0; i < 8; i++) {
			String line = br.readLine();
			line = line.substring(1);
			String[] vals = line.split(";&");
			System.out.println(Arrays.toString(vals));
			String bin = "";
			for (int j = 0; j < vals.length; j++) {
				if (vals[j].equals("#8203")) {
					bin += "0";
				} else {
					bin += "1";
				}
			}
			
			pw.println(bin);
		}
		
		
		pw.close();
		
	}
}
