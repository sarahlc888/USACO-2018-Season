import java.io.*;
import java.util.*;

public class BlockedBillboard {
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
		// make sure b1[0] > b1[2]

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

		// calculate area
		int area = 0;
		area += calcArea(b1[0], b1[1], b1[2], b1[3]);
		area += calcArea(b2[0], b2[1], b2[2], b2[3]);
		
		// check for overlaps of the truck
		
		// if UL truck and LL truck inside b1
		if (inside(b1[0], b1[1], b1[2], b1[3], truck[0], truck[3]) && inside(b1[0], b1[1], b1[2], b1[3], truck[0], truck[1])) {
			//System.out.println("overlap1");
			area -= calcArea(truck[0], truck[1], b1[2], truck[3]);
		}
		// if just UL truck is inside b1
		else if (inside(b1[0], b1[1], b1[2], b1[3], truck[0], truck[3])) {
			//System.out.println("overlap2");
			area -= calcArea(truck[0], b1[1], b1[2], truck[3]);
		}
		
		// if just LL truck is inside b1
		else if (inside(b1[0], b1[1], b1[2], b1[3], truck[0], truck[1])) {
			//System.out.println("overlap3");
			area -= calcArea(truck[0], truck[1], b1[2], b1[3]);
		}
		
		// if UR and LR truck inside b2
		if (inside(b2[0], b2[1], b2[2], b2[3], truck[2], truck[3]) && inside(b2[0], b2[1], b2[2], b2[3], truck[2], truck[1])) {
			//System.out.println("overlap4");
			area -= calcArea(b2[0], truck[1], truck[2], truck[3]);
		}
		// if only UR inside b2
		else if (inside(b2[0], b2[1], b2[2], b2[3], truck[2], truck[3])) {
			//System.out.println("overlap5");
			area -= calcArea(b2[0], b2[1], truck[2], truck[3]);
		}
		// if only LR inside b2
		else if (inside(b2[0], b2[1], b2[2], b2[3], truck[2], truck[1])) {
			//System.out.println("overlap6");
			area -= calcArea(b2[0], truck[1], truck[2], b2[3]);
		}
		//System.out.println(area);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("billboard.out")));

		pw.println(area);
		pw.close();
	}
	public static int calcArea(int x1, int y1, int x2, int y2) {
		return (y2 - y1)*(x2-x1);
	}
	public static boolean inside (int x1, int y1, int x2, int y2, int tx, int ty) {
		return (x1 < tx && x2 > tx && y1 < ty && y2 > ty);
	}


}
