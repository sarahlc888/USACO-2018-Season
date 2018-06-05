import java.io.*;
import java.util.*;

// 10/10

public class BB2 {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("billboard.in"));
		
		// stores coordinates (0, 1) lower left and (2, 3) upper right
		int[] b1 = new int[4]; 
		int[] b2 = new int[4];
		int[] truck = new int[4];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 4; i++) { 
			b1[i] = Integer.parseInt(st.nextToken());
		}
		StringTokenizer st1 = new StringTokenizer(br.readLine());
		for (int i = 0; i < 4; i++) { 
			b2[i] = Integer.parseInt(st1.nextToken());
		}
		StringTokenizer st2 = new StringTokenizer(br.readLine());
		for (int i = 0; i < 4; i++) { 
			truck[i] = Integer.parseInt(st2.nextToken());
		}
		br.close();
		
		
		if (b1[0] > b2[0]) {
			//System.out.println("switched");
			int x1 = b1[0];
			int y1 = b1[1];
			int x2 = b1[2];
			int y2 = b1[3];
			
			b1[0] = b2[0];
			b1[1] = b2[1];
			b1[2] = b2[2];
			b1[3] = b2[3];
			b2[0] = x1;
			b2[1] = y1;
			b2[2] = x2;
			b2[3] = y2;
		}
		
		int b1x1 = b1[0];
		int b1y1 = b1[1];
		int b1x2 = b1[2];
		int b1y2 = b1[3];
		
		int b2x1 = b2[0];
		int b2y1 = b2[1];
		int b2x2 = b2[2];
		int b2y2 = b2[3];
		
		int tx1 = truck[0];
		int ty1 = truck[1];
		int tx2 = truck[2];
		int ty2 = truck[3];

		// calculate area
		int area = calcArea(b1x1, b1y1, b1x2, b1y2) + calcArea(b2x1, b2y1, b2x2, b2y2);
		//System.out.println("area: " + area);
		
		//int xOverlap = 0;
		
		int c1 = 0;
		for (int y = b1y1; y <= b1y2; y++) {
			if (y >= ty1 && y <= ty2) {
			
				for (int x = b1x1; x <= b1x2; x++) {
					if (x <= tx2 && x >= tx1) {
						c1++;
					}
				}
				break;
			}
		}
		int c2 = 0;
		for (int x = b1x1; x <= b1x2; x++) {
			if (x >= tx1 && x <= tx2) {
				
				for (int y = b1y1; y <= b1y2; y++) {
					if (y <= ty2 && y >= ty1) {
						c2++;
					}
				}
				break;
			}
		}
		if (!(c1 == 0 || c2 == 0)) {
			area -= (c1 - 1)*(c2 - 1);
		}
		
		
		
		c1 = 0;
		for (int y = b2y1; y <= b2y2; y++) {
			if (y >= ty1 && y <= ty2) {
			
				for (int x = b2x1; x <= b2x2; x++) {
					if (x <= tx2 && x >= tx1) {
						c1++;
					}
				}
				break;
			}
		}
		c2 = 0;
		for (int x = b2x1; x <= b2x2; x++) {
			if (x >= tx1 && x <= tx2) {
				
				for (int y = b2y1; y <= b2y2; y++) {
					if (y <= ty2 && y >= ty1) {
						c2++;
					}
				}
				break;
			}
		}
		
		if (!(c1 == 0 || c2 == 0)) {
			area -= (c1 - 1)*(c2 - 1);
		}
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("billboard.out")));
		pw.println(area);
		pw.close();
	}
	public static int calcArea(int x1, int y1, int x2, int y2) {
		return (y2 - y1)*(x2-x1);
	}
}
