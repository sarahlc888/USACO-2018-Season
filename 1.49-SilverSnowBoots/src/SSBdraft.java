import java.io.*;
import java.util.*;

// draft version, see SSB2

public class SSBdraft {
	public static boolean[][] arr;
	public static int[] snow;
	public static int[] d;
	public static int[] s;
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("snowboots.in"));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		
		snow = new int[N]; // depth of snow on tiles
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			snow[i] = Integer.parseInt(st.nextToken());
		}
		d = new int[B]; // depth limit of boots
		s = new int[B]; // step limit of boots
		for (int i = 0; i < B; i++) {
			st = new StringTokenizer(br.readLine());
			d[i] = Integer.parseInt(st.nextToken());
			s[i] = Integer.parseInt(st.nextToken());
		}
		
		br.close();
		
		arr = new boolean[B][N]; // wear boot i on tile j
		// if you can wear boot i on tile j
		// you can wear 
		

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

		pw.println();
		pw.close();
	}
	public static boolean move(int b, int t) {
		if (arr[b][t]) { // if you can wear boot b on tile t
			
			// case: discard boot, don't change boot
			for (int i = 1; i <= s[b]; i++) { // for tiles within step limit
				if (snow[t+i] <= d[b]) { // if within depth limit
					arr[b][t+i] = true; // mark true (reachable)
				}
			}
			
			
			// case: change boot
			if (d[b+1] >= snow[t]) {
				// if you can change boots, change
				arr[b+1][t] = true; // mark true
				
				
			}
			
			
		}
		
		// TODO: replace this line
		return true; 
	}

}
