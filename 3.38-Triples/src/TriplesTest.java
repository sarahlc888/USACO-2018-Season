import java.io.*;
import java.util.*;
/*
 * from 12/11 lesson
 * segment tree or bit
 * 
 * 2 segment trees, idea similar to the idea in balanced photo:
 * count how many cows to the left are greater than entry i
 * count how many cows to the right are less than entry i
 * multiply the above values
 * add in the triples formed from all the same number
 */
public class TriplesTest {
	public static void main(String args[]) throws IOException {
		for (int num = 1; num <= 10; num++) {
			String fname = "testData/"+num+".in";
//			TriplesST.run(fname); // correct
			TriplesBIT.run(fname); // correct
		}
	}
	

}
