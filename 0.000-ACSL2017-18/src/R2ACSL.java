
/*
 * Sarah Chen
 * Phillips Academy
 * Senior-3 Contest #2
 */

// PASTE THE TEST DATA INTO THE CONSOLE IN A BLOCK
// MAKE SURE TO HAVE A RETURN AFTER THE LAST LINE AS WELL

// does not work

import java.io.*;
import java.util.*;

public class R2ACSL {

	static ArrayList<String> operations;
	static ArrayList<String> digits;
	static ArrayList<String> brackTypes;
	
	public static void main(String[] args) throws IOException {
		
		
		// list of operations
		operations = new ArrayList<String>();
		operations.add("+");
		operations.add("-");
		operations.add("/");
		operations.add("*");
		
		// list of digits
		digits = new ArrayList<String>();
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
		
		// list of brackets
		brackTypes = new ArrayList<String>();
		brackTypes.add("{");
		brackTypes.add("}");
		brackTypes.add("(");
		brackTypes.add(")");
		brackTypes.add("[");
		brackTypes.add("]");
				
		Scanner scan = new Scanner(System.in);
		
		// Suppose you are given 5 input lines and you need 
		// to process them the same way and print the result for each line...

		for (int k = 1; k <= 5; k++)
		{
			try
			{
				System.out.println("k: " + k);
				String line = scan.nextLine();
				//System.out.println("line: " + line);
				String[] tokens = new String[line.length()];
				
				//System.out.println();
				//System.out.println(line.length());
				//System.out.println();
				
				for (int i = 0; i < line.length(); i++) {
					tokens[i] = String.valueOf(line.charAt(i));
				}
				
				ArrayList<Integer> locations = processCase(tokens);
				Collections.sort(locations);
				//System.out.println(locations.size());
				
				
				PrintWriter pw = new PrintWriter(new File(k + ".out"));
				
				for (int i = 0; i < locations.size(); i++) {
					pw.println(locations.get(i));
					System.out.println(locations.get(i));
				}
				
				pw.close();
				System.out.println();
				
			}
			catch (Exception e)
			{
				System.out.println("Exception in processing: line #" + (k));
			}
		}
		scan.close();
	}
	
	public static ArrayList<Integer> processCase(String[] tokens) throws IOException {
		// returns the locations that the brackets could be in
		
		ArrayList<Integer> loc = new ArrayList<Integer>(); // arraylist of possible locations
		
		// array of which brackets are present (b[i] = index of b)
		int[] brackets = new int[6]; 
		// b[0] = "{"; 
		// b[1] = "}"; 
		// b[2] = "("; 
		// b[3] = ")";
		// b[4] = "[";
		// b[5] = "]";
		
		for (int i = 0; i < 6; i++) // mark all brackets as not present (-1)
			brackets[i] = -1; 

		for (int i = 0; i < tokens.length; i++) { // go through all tokens
			if (brackTypes.contains(tokens[i])) { // if the token is a bracket
				// see which kind of bracket it is, mark the proper position i in brackets
				if (tokens[i].equals("{")) brackets[0] = i;
				else if (tokens[i].equals("}")) brackets[1] = i;
				else if (tokens[i].equals("(")) brackets[2] = i;
				else if (tokens[i].equals(")")) brackets[3] = i;
				else if (tokens[i].equals("[")) brackets[4] = i;
				else if (tokens[i].equals("]")) brackets[5] = i;
			}
		}

		// determine which bracket is missing
		int missing = -1; 
		
		for (int i = 0; i < 6; i = i + 2) { // go through all the pairs of brackets
			// brackets[i] = opening bracket
			// brackets[i+1] = closing bracket
			
			// if (brackets[i] < 0 && brackets[i+1] < 0) // both not present, do nothing
			
			if (brackets[i] >= 0 && brackets[i+1] < 0) { // closing bracket is missing
				missing = i + 1; 
				break; // only one bracket can be missing, so break
			} else if (brackets[i] < 0 && brackets[i+1] >= 0) { // opening bracket is missing
				missing = i; 
				break; // only one bracket can be missing, so break
			}
		}
		
		
		// determine where to place the missing bracket
		
		boolean reversed = false; // flipped
		
		
		if (missing%2 == 0) { // if it's an opening bracket missing, reverse everything
			reversed = true;
			//System.out.println(reversed);
			String[] tokensOG = new String[tokens.length];
			for (int i = 0; i < tokens.length; i++) {
				tokensOG[i] = tokens[i];
			}
			for (int i = 0; i < tokens.length; i++) {
				tokens[i] = tokensOG[tokens.length - 1 - i];
			}
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i] == "}") tokens[i] = "{";
				else if (tokens[i].equals("}")) tokens[i] = "{";
				else if (tokens[i].equals("{")) tokens[i] = "}";
				else if (tokens[i].equals("(")) tokens[i] = ")";
				else if (tokens[i].equals(")")) tokens[i] = "(";
				else if (tokens[i].equals("[")) tokens[i] = "]";
				else if (tokens[i].equals("]")) tokens[i] = "[";
			}
			
			// re do the brackets array
			
			brackets = new int[6]; 
			for (int i = 0; i < 6; i++) {
				brackets[i] = -1;
			}
			
			for (int i = 0; i < tokens.length; i++) { // go through all the tokens
				if (!operations.contains(tokens[i]) && !digits.contains(tokens[i])) { 
					// if the token is not an operation and not a digit, it's a bracket
					// see which kind of bracket it is
					if (tokens[i].equals("{")) brackets[0] = i;
					else if (tokens[i].equals("}")) brackets[1] = i;
					else if (tokens[i].equals("(")) brackets[2] = i;
					else if (tokens[i].equals(")")) brackets[3] = i;
					else if (tokens[i].equals("[")) brackets[4] = i;
					else if (tokens[i].equals("]")) brackets[5] = i;
				}
			}
			
			missing++; // the type of bracket changes from opening to closing, so change missing
		}
		System.out.println(reversed);
		
		System.out.println("missing: " + missing);
		
		for (int i = 0; i < tokens.length; i++) { // step through all the tokens
						
			
			// insert the bracket @ i (between i-1 and the former i)
			boolean canClose = true; // default say that you could close at this spot


			// if the bracket set has not been opened, you cannot close it yet
			if (brackets[missing-1] >= i) {
				canClose = false;
				
			}
			

			// if curpos IS the opening bracket
			else if (i == brackets[missing-1]) { 
				canClose = false;
			}

			// if curpos is right after the opening bracket
			else if (i == brackets[missing-1] + 1) { 
				canClose = false;
			}

			// if the next item is a digit (no operation)
			else if (digits.contains(tokens[i])) {
				canClose = false;
			}

			// if the next item is an opening bracket (no operation)
			else if (tokens[i].equals("{") || tokens[i].equals("(") || tokens[i].equals("[")) {
				canClose = false;
			}


			// if everything within the brackets are digits of one number
			if (canClose) {
				boolean allDig = true; 
				for (int j = brackets[missing-1] + 1; j < i; j++) { 
					// loop from right after opening brack to right before curpos
					if (!digits.contains(tokens[j])) { 
						// if any token is not a digit, mark false
						allDig = false;
					}
				}
				if (allDig) {
					canClose = false;
				}
			}


			// if everything within the brackets is somehow brackets or operations only
			if (canClose) {
				boolean allNonDig = true;
				for (int j = brackets[missing-1] + 1; j < i; j++) { 
					// loop from right after opening brack to right before curpos
					if (digits.contains(tokens[j])) { 
						// if any token is a digit (not an operation or bracket), mark false
						allNonDig = false;
					}
				}
				if (allNonDig) {
					canClose = false;
				}
			}


			// if everything within the brackets has no operations
			if (canClose) {
				boolean hasNoOperations = true;
				for (int j = brackets[missing-1] + 1; j < i; j++) { 
					// loop from right after opening brack to right before curpos

					if (operations.contains(tokens[j])) {
						// if there is an operation
						hasNoOperations = false;
					}
				}
				if (hasNoOperations) {
					canClose = false;
				}
			}

			// if another bracket was opened earlier or later
			if (canClose) {
				for (int j = 0; j < 3; j++) { // loop through types of brackets
					if (j == missing/2) continue; // if it's the missing type, cont
					
					else if (brackets[j*2] >= i) { // if the bracket has not been opened, cont
						continue;
					}
					
					// the bracket type has been opened

					// if bracket j was opened before the missing type
					if (brackets[j*2] < brackets[missing-1]) {
						// then the missing type must close before bracket j
						// if bracket j is closed before curpos, the missing bracket cannot go there
						if (brackets[j*2 + 1] < i) {
							canClose = false;
						}
					}

					// if bracket j was opened after the missing type
					else if (brackets[j*2] > brackets[missing-1]) {
						// then bracket j must be closed before the missing bracket
						if (brackets[j*2 + 1] >= i) {
							// if the bracket is not closed yet
							canClose = false;
						}
					}
				}
			}


			if (canClose && !reversed) {
				// if you can place the missing CLOSING bracket here, do it
				loc.add(i+1); // i + 1 to account for 0...n-1 to 1...n indexing
			}
			if (canClose && reversed) {
				// if you can place the missing OPENING bracket here, do it
				loc.add(tokens.length + 2 - (i+1)); // to account for the reversal
			}

		}
		
		if (brackets[missing - 1] == 0) {
			
			if (!loc.contains(tokens.length + 1)) {
				loc.add(tokens.length + 1);
			}
		}
		
		return loc;
		
		
		
	}
}