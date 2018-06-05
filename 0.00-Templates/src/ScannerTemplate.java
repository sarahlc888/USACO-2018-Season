import java.io.*;
import java.util.*;

public class ScannerTemplate {
	public static void main(String args[]) throws IOException {

		Scanner scan = new Scanner(new File("file.in"));

		int N = scan.nextInt();
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = scan.nextInt();
		}
		scan.close();

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

		pw.println();
		pw.close();
	}


}
