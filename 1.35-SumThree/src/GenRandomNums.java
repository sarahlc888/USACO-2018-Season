
import java.io.*;
import java.util.*;

// generates N positive and N negative numbers within range [-M, M] and writes them to a file

public class GenRandomNums {
	static int N;
	static int M;
	public static void main(String args[]) throws IOException {
		N = 10000; // 2*N numbers
		M = 10000; // range [-M, M]
		
		removeDup();
		
	}
	public static void randomize() throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nums.out")));

		int a = 0;
		
		for (int i = 0; i < N; i++) {
			a = (int)(Math.random() * M);
			pw.println(a);
		}
		for (int i = 0; i < N; i++) {
			a = (int)(Math.random() * -1 * M);
			pw.println(a);
		}
		
		pw.close();
		
	}
	public static void removeDup() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("nums.in"));
		int[] nums = new int[2*N];
		for (int i = 0; i < 2*N; i++) {
			nums[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(nums);
		
		int count = 0; // number of unique numbers
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("nums.out")));
		
		for (int i = 0; i < 2*N-1; i++) {
			if (nums[i] != nums[i+1]) {
				// if the number is not the same as the one after it
				count++;
				pw.println(nums[i]);
			}
		}
		pw.close();
		System.out.println(count);
		
	}

}
