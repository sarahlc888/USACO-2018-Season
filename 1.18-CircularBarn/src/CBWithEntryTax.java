import java.io.*;
import java.util.*;

// costs money to enter the barn initially - this is not the case... wrong...

public class CBWithEntryTax {
	// loop through every possible starting point
	// loop through all the rooms
	// leave one cow in the starting room
	// fill subsequent rooms until original cows are "out"
	
	// go to next room, start at first empty room, fill subsequent rooms
	// repeat until all rooms filled
	// calculate movement cost for each using summation of squares formula
	// find minimum cost
	
	static int[] sqrsums;
	static int[] initBarn;
	static int N;
	
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("cbarn.in"));
		N = Integer.parseInt(br.readLine()); 
		
		// all cows walk through one door upon entry
		
		initBarn = new int[N];
		for (int i = 0; i < N; i++) {
			initBarn[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		
		
		sqrsums = new int[N+1]; // array of sums of squares up to i
		// a cow can take at most N+1 steps 
		// N - 1 steps to go through all the rooms (but not back to its original spot)
		// 1 step upon entry
		
		int minSteps = Integer.MAX_VALUE;
		
		for (int start = 0; start < N; start++) { // loop through every possible starting point
			boolean abort = false;
			int stepCount = 0; // number of steps taken
			
			System.out.println("start: " + start);
			
			if (initBarn[start] == 0) {
				System.out.println("room empty, continue");
				continue;
			}
			
			int firstVacantRoom = posMod(start + initBarn[start]); 
			System.out.println("  first firstVacantRoom: " + firstVacantRoom);
			
			// the cow that stays in the 1st room takes 1 step, the farthest cow takes initBarn[start] steps
			
			int cost = calcStepCost(1, initBarn[start]);
			
			/*
			if (sqrsums[initBarn[start]] == 0) {
				// if the value has not already been calced, calc and store it
				sqrsums[initBarn[start]] = sumSquares(initBarn[start]);
			}
			int cost = sqrsums[initBarn[start]];
			*/
			stepCount += cost;
			
			
			for (int cur = start + 1; cur < start + N; cur++) { // loop through each room
				
				// room that is currently being emptied
				int curRoom = posMod(cur); 
				System.out.println("  curRoom: " + curRoom);
				
				
				if (initBarn[curRoom] == 0) {
					// if the room has no cows to draw from
					System.out.println("  empty room");
					continue;
				}
				//System.out.println("  supposed firstVacantRoom: " + firstVacantRoom);
					
				/*
				// not right strategy:
				// find the first actually empty room
				while (initBarn[posMod(firstVacantRoom)] != 0) {
					firstVacantRoom = (firstVacantRoom+1);
				}
				*/
				System.out.println("  firstVacantRoom: " + firstVacantRoom);
				
				if (posMod(firstVacantRoom - start)  < posMod(curRoom - start)) {
					// the cows will have to circle all the way around
					abort = true; // not fast enough, so just quit calculating that starting point
					break;
				}
				
				firstVacantRoom = posMod(firstVacantRoom);
				System.out.println("  modded firstVacantRoom: " + firstVacantRoom);
								
				// number of steps from the outside of the barn to the firstVacantRoom
				// there is one step from just entering the barn alone!!!
				int numSteps1 = posMod(firstVacantRoom - curRoom) + 1;
				// most steps taken
				int numSteps2 = posMod(numSteps1 + initBarn[curRoom] - 1);

				System.out.println("  numSteps1: " + numSteps1);
				System.out.println("  numSteps2: " + numSteps2);
				
				System.out.println(" cost: " + calcStepCost(numSteps1, numSteps2));
				stepCount+=calcStepCost(numSteps1, numSteps2);
				System.out.println(" stepCount: " + stepCount);
				firstVacantRoom += initBarn[curRoom]; // drop the cows off
				firstVacantRoom = posMod(firstVacantRoom);
				
				if (firstVacantRoom == start) {
					// if the fVR loops back to the first room, then it's all been filled
					break;
				}
				System.out.println();
			}
			if (abort) {
				System.out.println("  aborted\n");
				continue;
			}
			System.out.println(stepCount);
			if (stepCount < minSteps) {
				minSteps = stepCount;
				System.out.println("new MIN!");
			}
		}		
		
		System.out.println(minSteps);
		
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
	public static int calcStepCost(int s1, int s2) {
		// calculate to cost of s1^2 + ... + s2^2
		
		
		if (s1 == s2) { // if the two are the same, that means there is only one cow moving
			// manual calc
			return s1*s1;
		}
		if (s1 == 0 && s2 == 0) {
			return 0;
		}
		
		if (sqrsums[s1 - 1] == 0) {
			// if the value has not already been calced, calc and store it
			sqrsums[s1 - 1] = sumSquares(s1 - 1);
		}
		if (sqrsums[s2] == 0) {
			// if the value has not already been calced, calc and store it
			sqrsums[s2] = sumSquares(s2);
		}
		int n = sqrsums[s2];
		int m = sqrsums[s1 - 1];
		
		return n - m;
	}
	
	

}
