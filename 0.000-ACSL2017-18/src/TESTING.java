import java.util.ArrayList;

public class TESTING {
	public static void main(String[] args) {
		
		ArrayList<String> digits = new ArrayList<String>();
		digits.add("0");
		digits.add("1");
		digits.add("2");
		digits.add("3");
		digits.add("4");
		digits.add("5");
		digits.add("6");
		digits.add("7");
		digits.add("8");
		digits.add("9");
		
		// if everything within the brackets are digits of one number
		int[] brackets = {0, 12, 2, 6, 1, -1};
		int missing = 1;
		int curpos = 3;
		String[] tokens = {"{", "1", "2", "}"};
		boolean allDig = true; 
		for (int j = 1; j < 3; j++) { 
			// loop from opening brack to curpos
			if (!digits.contains(tokens[j])) { 
				// if any token is not a digit, mark false
				allDig = false;
			}
		}
		System.out.println(allDig);
		
	}
}
