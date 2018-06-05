
import java.io.*;
import java.util.*;


public class GenRandomNums {
	
	
	public static void main(String args[]) throws IOException {
		poslines(100000, 2, 1000000000);
	}
	
	
	public static void poslines(int N, int K, int M) throws IOException {
		// generates N lines of K positive numbers up to M and writes them to a file
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nums.out")));

		
		for (int i = 0; i < N; i++) {
			pw.println();
			for (int j = 0; j < K; j++) {
				int a = (int)(Math.random() * M) + 1;
				pw.print(a + " ");
			}
		} 
		
		pw.close();
	}
	
	public static void posneg() throws IOException {
		// generates N positive and N negative numbers and writes them to a file
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nums.out")));

		int N = 10000;
		

		int a = 0;
		
		for (int i = 0; i < N; i++) {
			a = (int)(Math.random() * 10000);
			pw.println(a);
		}
		for (int i = 0; i < N; i++) {
			a = (int)(Math.random() * -10000);
			pw.println(a);
		}
		
		pw.close();
	}


}
