import java.io.*;
import java.util.*;

public class DrK {
	public static void main(String[] args) throws IOException {
		File file = new File("input.wav");
		byte[] data = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		in.read(data);
		in.close();

		//encrypt data

		FileOutputStream out = new FileOutputStream(new File("DrK2.out"));
		out.write(data);
		out.close();
	}
	
}
