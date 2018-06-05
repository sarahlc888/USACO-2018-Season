/*
 * USACO 2017 US Open Contest, Silver
 * Problem 2. Bovine Genomics
 * 9 out of 10 test cases?
 */

import java.io.*;
import java.util.*;

public class BovineGenomics {
	public static boolean rank(String key, ArrayList<String> arrayToSearch) {
	    int low = 0;
	    int high = arrayToSearch.size() - 1;

	    while (low <= high) {
	        int midItem = (low + high) / 2;
	        if (key.compareTo(arrayToSearch.get(midItem)) > 0) {
	            low = midItem + 1;
	        } else if (key.compareTo(arrayToSearch.get(midItem)) < 0) {
	            high = midItem - 1;
	        } else { // The element has been found
	            return true;
	        }
	    }
	    return false;
	}
	
	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(new File("cownomics.in"));
		
		int N = in.nextInt();
		int M = in.nextInt();
		in.nextLine();   // finish reading the first line
		
		// scan in all the genomes
		String[] genomes = new String[2*N]; // plain cows are first, then spotty
		for(int i=0; i<2*N; i++)
			genomes[i] = in.nextLine();
		
		in.close();
		
		int count = 0; // number of acceptable sets of positions
		
		// go through all the ***combinations*** of indices in the genomes
		for(int i=0; i<M; i++) {
			for(int j=i+1; j<M; j++) {	
				for(int k=j+1; k<M; k++) {  // M^3 * (N * logN) = 125*10^3 * 500 * 8

					// check the 3-letter subsequences from the two groups	
					
					ArrayList<String> spottyGenes = new ArrayList<String>();
					
					for (int l = 0; l < N; l++) { // loop through the spotty cows and add their codes to the list
						spottyGenes.add("" + genomes[l].charAt(i) + genomes[l].charAt(j) + genomes[l].charAt(k));
					}

					Collections.sort(spottyGenes);
					
					boolean valid = true;
					
					for (int l = N; l < genomes.length; l++) { // loop through the plain cows and see if they are in the spotty cows already
						String gene = "" + genomes[l].charAt(i) + genomes[l].charAt(j) + genomes[l].charAt(k);
						
						if (rank(gene, spottyGenes)) { // if the plain cow shares a gene with a spotty cow, it's not that position
							valid = false;
							break;
						}
					}
					
					if (valid) count++;
				}
			}
		}
		// System.out.println(count);
		
		PrintWriter pw = new PrintWriter(new File("cownomics.out"));
		pw.println(count);
		pw.close();
		
	}

}
