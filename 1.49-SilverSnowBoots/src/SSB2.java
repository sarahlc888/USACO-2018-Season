import java.io.*;
import java.util.*;

// silver level Feb problem 2
// 10 test cases correct

public class SSB2 {
	public static int N;
	public static int B;
	public static boolean[][] arr;
	public static int[] snow;
	public static int[] d;
	public static int[] s;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // number of tiles
		B = Integer.parseInt(st.nextToken()); // number of boots
		snow = new int[N]; // depth of snow on tiles
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) 
			snow[i] = Integer.parseInt(st.nextToken());
		
		d = new int[B]; // depth limit of boots
		s = new int[B]; // step limit of boots
		
		for (int i = 0; i < B; i++) {
			st = new StringTokenizer(br.readLine());
			d[i] = Integer.parseInt(st.nextToken());
			s[i] = Integer.parseInt(st.nextToken());
		}
		
		br.close();
		
		arr = new boolean[B][N]; // wear boot i on tile j
		arr[0][0] = true;
		

		for (int b = 0; b < B; b++) {
			for (int t = 0; t < N; t++) {
				arr[0][0] = true;
				if (arr[b][t]) { // if you can wear boot b on tile t
					
					// case: discard boot (don't change) and take all possible steps
					
					for (int i = 1; i <= s[b]; i++) { // for tiles within step limit
						if (t+i < N && snow[t+i] <= d[b]) { // if within depth limit
							arr[b][t+i] = true; // mark true (reachable)
						}
					}
					
					
					// case: change boot
					
					for (int boot = b+1; boot < B; boot++) { // all possible boot changes
						if (d[boot] >= snow[t]) { // if depth allows, change boot
							arr[boot][t] = true; // mark true
							
							for (int i = 1; i <= s[boot]; i++) { // for tiles within step 
								if (t+i < N && snow[t+i] <= d[boot]) { // if within depth 
									arr[boot][t+i] = true; // mark true (reachable)
								}
							}
							
						}
					}
					
					
				}
			}
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));

		for (int b = 0; b < B; b++) {
			if (arr[b][N-1]) { // if valid, boot id = number of changes, so print
				pw.println(b);
				break;
			}
		}

		pw.close();
	}

}
