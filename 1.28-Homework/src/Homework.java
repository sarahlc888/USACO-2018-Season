import java.io.*;
import java.util.*;

public class Homework {
	public static void main(String args[]) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("homework.in"));
		int N = Integer.parseInt(br.readLine()); // number of homeworks
		int[] homework = new int[N]; // score of all the homeworks
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			homework[i] = Integer.parseInt(st.nextToken());
		}
		br.close();
		int[] ps = new int[N]; // ps[i] = homework[i] + .. + homework[N-1]
		
		ps[N-1] = homework[N-1];
		for (int i = N-2; i >= 0; i--) {
			ps[i] = ps[i+1] + homework[i];
		}
		
		int[] lowest = new int[N]; // lowest[i] = homework[i:].min()
		lowest[N-1] = homework[N-1];
		for (int i = N-2; i >=0; i--) {
			lowest[i] = Math.min(lowest[i+1], homework[i]);
		}
		
		// int maxGrade = 0;
		long maxnum = 0;
		long maxden = 1;
		
		ArrayList<Integer> answers = new ArrayList<Integer>();
		
		for (int k = 0; k < N - 2; k++) {
			// k is the highest position dropped
			// ps[k+1] is sum of everything
			// - lowest[k+1] is lowest grade to be dropped
			// number of assignments left is N - k - 2 (smallest one is dropped)
			
			long curnum = (ps[k+1] - lowest[k+1]);
			long curden = (N-k-2);
			
			if (curnum*maxden - maxnum*curden > 0) {
				maxnum = curnum;
				maxden = curden;
				answers.clear();
				answers.add(k + 1);
			} else if (curnum*maxden - maxnum * curden == 0) {
				answers.add(k+1);
			}
			
		}
		
		
		for (int i = 0; i < answers.size(); i++) {
			System.out.println(answers.get(i));
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("homework.out")));
		for (int i = 0; i < answers.size(); i++) {
			pw.println(answers.get(i));
		}
		
		pw.close();
		
		
	}


}
