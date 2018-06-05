/*
ID: sarahlc1
LANG: JAVA
TASK: namenum
*/

import java.io.*;
import java.util.*;

public class namenum1 {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("namenum.in"));
		String cownum = scan.next();
		scan.close();
		
		// get and sort the list of acceptable names
		Scanner scan2 = new Scanner(new File("dict.txt"));
		ArrayList<String> cowNameDict = new ArrayList<String>();
		for (int i = 0; i < 5000; i++) {
			if (!scan2.hasNext()) break;
			cowNameDict.add(scan2.next());
		}
		Collections.sort(cowNameDict);
		
		HashMap numToLet = new HashMap();
		char[] two = {'A', 'B', 'C'};
		numToLet.put('2', two);
		char[] three = {'D', 'E', 'F'};
		numToLet.put('3', three);
		char[] four = {'G', 'H', 'I'};
		numToLet.put('4', four);
		char[] five = {'J', 'K', 'L'};
		numToLet.put('5', five);
		char[] six = {'M', 'N', 'O'};
		numToLet.put('6', six);
		char[] seven = {'P', 'R', 'S'};
		numToLet.put('7', seven);
		char[] eight = {'T', 'U', 'V'};
		numToLet.put('8', eight);
		char[] nine = {'W', 'X', 'Y'};
		numToLet.put('9', nine);
		
		// use base 3 to get the character from the proper index of []s of possible letters corresponding to letters
		String maxNum = "";
		for (int i = 0; i < cownum.length(); i++) maxNum += "2"; // make maxnum the proper length
		int btmn = Integer.parseInt(maxNum, 3); // base 10 equivalent of the maxNum
		
		boolean isName = false;
		PrintWriter pw = new PrintWriter(new File("namenum.out"));
		
		// loop through and make each number up to btmn base 3 and add it to seqs
		for (int i = 0; i < btmn; i++) { // go through all of the sequences of indexes
			String curSeq = Integer.toString(i, 3); // current sequence of indexes
			while (curSeq.length() < cownum.length()) curSeq = "0" + curSeq;
			String name = ""; // the name yielded from that sequences
			
			// go through each digit in cownum and get the right index
			for (int j = 0; j < cownum.length(); j++) {
				char curnum = cownum.charAt(j);
				char[] lets = (char[])numToLet.get(curnum);
				int curInd = Character.getNumericValue(curSeq.charAt(j));
				name += lets[curInd];
			}
			
			if (Collections.binarySearch(cowNameDict, name) >= 0) {
				pw.println(name);
				isName = true;
			}
		}
		if (!isName) pw.println("NONE");
		pw.close();
	}
}
