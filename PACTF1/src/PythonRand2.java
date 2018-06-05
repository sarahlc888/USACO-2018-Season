import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PythonRand2 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("PyLot.in"));
		long[] vals = new long[1000];
		for (int i = 0; i < 1000; i++) {
			vals[i] = Long.parseLong(br.readLine());
		}
		int[] state = new int[1000];
		for (int i = 0; i < 1000; i++) {
			long value = vals[i];
			int value2 = unBitshiftRightXor(value, 18);
			value2 = unBitshiftLeftXor(value2, 15, 0xefc60000);
			value2 = unBitshiftLeftXor(value2, 7, 0x9d2c5680);
			value2 = unBitshiftRightXor(value2, 11);
			state[i] = (int)value2;
		}
		
		// Iterate through the state, find the new state
		for (int i = 0; i < 624; i++) {
			// y is the first bit of the current number,
			// and the last 31 bits of the next number
			int y = (state[i] & 0x80000000) + (state[(i + 1) % 624] & 0x7fffffff);
			// first bitshift y by 1 to the right
			int next = y >>> 1;
			// xor it with the 397th next number
			next ^= state[(i + 397) % 624];
			// if y is odd, xor with magic number
			if ((y & 1L) == 1L) {
				next ^= 0x9908b0df;
			}
			// now we have the result
			state[i] = next;
		}
		
		// using the next state, calc the 1001 random number
		
		int tmp = state[376];
		//int tmp = state[0];
		tmp ^= (tmp >>> 11);
		tmp ^= (tmp << 7) & 0x9d2c5680;
		tmp ^= (tmp << 15) & 0xefc60000;
		tmp ^= (tmp >>> 18);
		long unsigned = tmp & 0x00000000ffffffffL;
		
		System.out.println(tmp);
		System.out.println(unsigned);
	}
	
	static int unBitshiftRightXor(int value, int shift) {
		// we part of the value we are up to (with a width of shift bits)
		int i = 0;
		// we accumulate the result here
		int result = 0;
		// iterate until we've done the full 32 bits
		while (i * shift < 32) {
			// create a mask for this part
			int partMask = (-1 << (32 - shift)) >>> (shift * i);
			// obtain the part
			int part = value & partMask;
			// unapply the xor from the next part of the integer
			value ^= part >>> shift;
			// add the part to the result
			result |= part;
			i++;
		}
		return result;
	}
	static int unBitshiftLeftXor(int value, int shift, int mask) {
		// we part of the value we are up to (with a width of shift bits)
		int i = 0;
		// we accumulate the result here
		int result = 0;
		// iterate until we've done the full 32 bits
		while (i * shift < 32) {
			// create a mask for this part
			int partMask = (-1 >>> (32 - shift)) << (shift * i);
			// obtain the part
			int part = value & partMask;
			// unapply the xor from the next part of the integer
			value ^= (part << shift) & mask;
			// add the part to the result
			result |= part;
			i++;
		}
		return result;
	}
	static int unBitshiftRightXor(long value, int shift) {
		// we part of the value we are up to (with a width of shift bits)
		int i = 0;
		// we accumulate the result here
		int result = 0;
		// iterate until we've done the full 32 bits
		while (i * shift < 32) {
			// create a mask for this part
			int partMask = (-1 << (32 - shift)) >>> (shift * i);
			// obtain the part
			long part = value & partMask;
			// unapply the xor from the next part of the integer
			value ^= part >>> shift;
			// add the part to the result
			result |= part;
			i++;
		}
		return result;
	}
	static int unBitshiftLeftXor(long value, int shift, int mask) {
		// we part of the value we are up to (with a width of shift bits)
		int i = 0;
		// we accumulate the result here
		int result = 0;
		// iterate until we've done the full 32 bits
		while (i * shift < 32) {
			// create a mask for this part
			int partMask = (-1 >>> (32 - shift)) << (shift * i);
			// obtain the part
			long part = value & partMask;
			// unapply the xor from the next part of the integer
			value ^= (part << shift) & mask;
			// add the part to the result
			result |= part;
			i++;
		}
		return result;
	}

}
