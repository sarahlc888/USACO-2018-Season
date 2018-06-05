import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

import javax.imageio.ImageIO;

public class TrueBlue {
	public static void main(String[] args) throws IOException {
		File pic = new File("true-blue.08c0cb787f26.png");
		BufferedImage a = ImageIO.read( pic );

		ArrayList<Integer> vals = new ArrayList<Integer>();
		
		int red = 0;
		int green = 0;
		int blue = 0;

		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 128; j++) {
				Color mycolor = new Color(a.getRGB(0, 0));
				red = mycolor.getRed();
				green = mycolor.getGreen();
				blue = mycolor.getBlue();
				
				if (!vals.contains(red) || !vals.contains(green) || !vals.contains(blue)) {
					vals.add(red);
					vals.add(green);
					vals.add(blue);
					
					System.out.println("red: " + red);
					System.out.println("green: " + green);
					System.out.println("blue: " + blue);
					
					System.out.println();
				}
				
				
			}
		}
		

		System.out.println(new BigInteger(String.valueOf(red), 10).toString(16));
		System.out.println(new BigInteger(String.valueOf(green), 10).toString(16));
		System.out.println(new BigInteger(String.valueOf(blue), 10).toString(16));
		//Raster r = a.getData();
		//r.getpi
		
		String x = "00000000\n" + 
				"00110001\n" + 
				"10011100";
	}
}
