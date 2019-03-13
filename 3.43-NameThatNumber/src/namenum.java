/*
ID: sarahlc1
LANG: JAVA
TASK: namenum
*/
import java.io.*;
import java.util.*;
// from usaco training gateway, easy problem
public class namenum {
	public static void main(String args[]) throws IOException {
		// number
		BufferedReader br = new BufferedReader(new FileReader("namenum.in"));
		String numString = br.readLine();
		br.close();

		// acceptable names
		String[] names = new String[5000];
		Scanner scan = new Scanner(new FileReader("dict.txt"));
		int i = 0;
		while (scan.hasNextLine()) {
			names[i] = scan.nextLine();
			i++;
		}
		br.close();
		
		ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
		// 0 and 1 map to empty lists
		map.add(new ArrayList<String>()); 
		map.add(new ArrayList<String>());
		int curChar = 65;
		for (i = 2; i < 10; i++) {
			ArrayList<String> curList = new ArrayList<String>();
			
			for (int j = 0; j < 3; j++) {
				if (curChar == 81) {
					curChar++;
					j--;
					continue;
				}
				curList.add(String.valueOf((char)curChar));
				curChar++;
			}
			map.add(curList);
		}

		ArrayList<String> pos = (ArrayList<String>) map.get(numString.charAt(0)-48).clone();
//		System.out.println(map.get(numString.charAt(0)-48));
		for (i = 1; i < numString.length(); i++) {
			ArrayList<String> pos2 = new ArrayList<String>();
			
			int curVal = (int)numString.charAt(i)-48;
//			System.out.println(map.get(curVal));
			for (int j = 0; j < 3; j++) {
				String add = map.get(curVal).get(j);
				for (int k = 0; k < pos.size(); k++) {
					String s = pos.get(k)+add;
					if (bSearch(names, s) >= 0) {
						pos2.add(s);
					}
				}
			}
			pos = pos2;
		}
//		System.out.println(pos);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
		Collections.sort(pos);
		int ct = 0;
		for (i = 0; i < pos.size(); i++) {
			String cur = pos.get(i);
			if (bSearchReal(names, cur) >= 0) {
				pw.println(cur);
				ct++;
//				System.out.println(cur);
			}
		}
		if (ct == 0) pw.println("NONE");
		pw.close();
	}
	public static int bSearchReal(String[] arr, String val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;
		int mid = 0;
		
		while (lo <= hi) {
			
			mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if

			if (arr[mid].compareTo(val) > 0) { // if mid is in range
				hi = mid-1;
			} else if (arr[mid].compareTo(val) < 0){ // mid is not in range
				lo = mid+1;
			} else { // == val
				break;
			}
			
		}
		if (arr[mid].equals(val)) {
			return mid; // make sure it works
		} else {
			return -1;
		}
	}
	public static int bSearch(String[] arr, String val) {
		// returns smallest i >= val
		int lo = 0;
		int hi = arr.length-1;
		int mid = 0;
		
		while (lo <= hi) {
			mid = (lo + hi)/2; // no +1 because you want hi to become lo in 1st if
//			System.out.println("lo: " + lo + " mid: " + mid + " hi: " + hi);
			if (comp(arr[mid],val) > 0) { // if mid is in range
				hi = mid-1;
			} else if (comp(arr[mid],val) < 0){ // mid is not in range
				lo = mid+1;
			} else { // == val
				break;
			}
			
		}
		if (comp(arr[mid],val)==0) {
			return mid; // make sure it works
		} else {
			return -1;
		}
	}
	public static int comp(String a, String b) {
		int curLen = Math.min(a.length(), b.length());
		for (int i = 0; i < curLen; i++ ) {
			if (a.charAt(i) != b.charAt(i)) {
				return a.charAt(i)-b.charAt(i);
			}
		}
		return 0;
	}
}
