import java.io.*;
import java.util.*;

// 10/10 test cases correct

// same except it doesn't cost anything to go into the barn initially
//loop through every possible starting point
// loop through all the rooms
// leave one cow in the starting room
// fill subsequent rooms until original cows are "out"

// go to next room, start at first empty room, fill subsequent rooms
// repeat until all rooms filled
// calculate movement cost for each using summation of squares formula
// find minimum cost
public class CBarn {
	static int[] sqrsums;
	static int[] initBarn;
	static int N;
	
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("cbarn.in"));
		N = Integer.parseInt(br.readLine()); // number of rooms
		initBarn = new int[N]; // initial state of the barn
		for (int i = 0; i < N; i++) { // scan in the cows
			initBarn[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		
		sqrsums = new int[N]; // array of sums of squares (1^2 + ... + i^2)
		// a cow can take at most N-1 steps (before it just cirlces around back to the original)

		// N - 1 steps to go through all the rooms (any more would bring it back to its original spot)
		// NO step upon entry
		
		int minSteps = Integer.MAX_VALUE; // minimum number of total steps needed to fill the barn
		
		for (int start = 0; start < N; start++) { // loop through every possible starting point
			boolean abort = false;
			int stepCount = 0; // number of steps taken
			
			//System.out.println("start: " + start);
			
			if (initBarn[start] == 0) { // can't start on an empty room
				//System.out.println("room empty, continue");
				continue;
			}
			
			// next room to be filled (after dispensing the cows and leaving one in the starting room)
			int nextRoom = posMod(start + initBarn[start]); 
			//System.out.println("  first nextRoom: " + nextRoom);
			
			// the cow that stays in the starting room takes 0 steps
			// the cow that moves the farthest takes initBarn[start] - 1 steps
			
			// calculate and add the cost of the movement
			int cost = calcStepCost(0, initBarn[start] - 1);
			stepCount += cost;
			
			for (int cur = start + 1; cur < start + N; cur++) { // loop through the other rooms
				
				int curRoom = posMod(cur); // room that is currently being emptied
				//System.out.println("  curRoom: " + curRoom);
				
				if (initBarn[curRoom] == 0) {
					// if the room has no cows to disperse
					//System.out.println("  empty room");
					continue;
				}
				
				//System.out.println("  nextRoom: " + nextRoom);
				
				if (posMod(nextRoom - start)  < posMod(curRoom - start)) {
					// if the nextRoom is behind the curRoom relative to the starting point,
					// the cows would have to circle all the way around, taking too long
					// that's not fast enough, so stop considering the cur starting point
					abort = true; 
					break;
				}
				
				nextRoom = posMod(nextRoom);
				
				//System.out.println("  modded nextRoom: " + nextRoom);
				
				// number of steps from the initial room to the nextRoom
				// there is NO STEP from entering the barn!!
				
				int minNumSteps = posMod(nextRoom - curRoom);
				// most steps taken
				int maxNumSteps = posMod(minNumSteps + initBarn[curRoom] - 1);

				//System.out.println("  minNumSteps: " + minNumSteps);
				//System.out.println("  maxNumSteps: " + maxNumSteps);
				
				// update the cost and then the stepCount
				cost = calcStepCost(minNumSteps, maxNumSteps);
				stepCount+=calcStepCost(minNumSteps, maxNumSteps);
				
				//System.out.println(" stepsCost: " + cost);
				//System.out.println(" stepCount: " + stepCount);
				
				// update nextRoom
				nextRoom += initBarn[curRoom]; 
				nextRoom = posMod(nextRoom);
				
				if (nextRoom == start) {
					// if nextRoom loops back to the first room, then everything has been filled
					break;
				}
				//System.out.println();
			}
			if (abort) {
				//System.out.println("  aborted\n");
				continue;
			}
			//System.out.println(stepCount);
			if (stepCount < minSteps) {
				minSteps = stepCount;
				//System.out.println("new MIN!");
			}
		}		
		
		//System.out.println(minSteps);
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));

		pw.println(minSteps);
		pw.close();

		
	}
	public static int posMod(int a) {
		int mod = a % N;
		while (mod < 0) {
			mod+=N;
		}
		return mod;
	}
	public static int sumSquares(int n) {
		// sums the squares from 1 to n inclusive
		return n * (n + 1) * (2 * n + 1) / 6;
	}
	public static int calcStepCost(int minStep, int maxStep) {
		// parameters: smallest number of steps, biggest number of steps
		// calculate to cost of minStep^2 + ... + maxStep^2
		
		// special cases:
		if (minStep == maxStep) { // if only one cow moves, then manually calculate
			return minStep*minStep;
		}
		if (minStep == 0 && maxStep == 0) { // if nothing moves, there is no cost
			return 0;
		}
		
		// change the minimum from 0 to 1 in order to avoid out of bounds errors
		if (minStep == 0) {
			minStep = 1;
		}
		
		// if sqrsums[minStep] and sqrsums[maxStep] have not already been calculated, calculate them
		if (sqrsums[minStep - 1] == 0) {
			sqrsums[minStep - 1] = sumSquares(minStep - 1);
		}
		if (sqrsums[maxStep] == 0) {
			sqrsums[maxStep] = sumSquares(maxStep);
		}
		
		return sqrsums[maxStep] - sqrsums[minStep - 1];
	}
	
	

}
