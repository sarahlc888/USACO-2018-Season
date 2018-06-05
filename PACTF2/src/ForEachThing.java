import java.io.IOException;

public class ForEachThing {
	public static void main(String[] args) throws IOException {
		int[][] n = new int[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				n[i][j]=3;
			}
		}
		for (int[] x : n) {
			for (int y : x) {
				System.out.println(y);
			}
		}
	}
}
