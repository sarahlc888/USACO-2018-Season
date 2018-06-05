import java.io.*;
import java.util.*;

public class CowCode3 {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("cowcode.in"));
		PrintWriter pw = new PrintWriter(new File("cowcode.out"));
		String s = scan.next();
		long index = scan.nextLong();
		char val = determine(s, index-1); // -1 to correct the index from 1...N to 0...N-1
		pw.println(val);
		pw.close();
	}

	public static char determine(String s, long index) {
		if(index < s.length()) { // if the index is within the word itself (no more rotations required)
			return s.charAt((int)index);
		}
		long length = s.length();
		while(2*length <= index) { // multiply the length by 2 until it (doubled) is greater than the index
			length *= 2;
		}
		if(length == index) { // if the index is just after the end of s, it's the same as the last letter of s
			return determine(s, length-1);
		}
		return determine(s, index - length - 1); // decrease a rotation and go back one letter in the word 
		// that's what would be the same letter ^^
	}
}