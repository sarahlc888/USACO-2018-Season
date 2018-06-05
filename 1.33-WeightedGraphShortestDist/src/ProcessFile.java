import java.io.*;
import java.util.StringTokenizer;

public class ProcessFile {
	/*public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("testData/adjW3.out"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		PrintWriter pw = new PrintWriter(new File("adjW32.out"));
		for (int i = 0; i < 1000; i++) {
			int x = Integer.valueOf(st.nextToken());
			System.out.println(x);
			pw.println(x);
		}
		br.close();
		pw.close();
	}*/
	public static void main(String args[]) throws IOException {
		String a = "0112222121222121222122222121222222211212122222223212222222222222222222222222211222222222121122212222";
		BufferedReader br = new BufferedReader(new FileReader("testData/adjW2.out"));
		String b = "";
		for (int i = 0; i < 100; i++) {
			b += br.readLine();
		}
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.equals(b));

	}
}
