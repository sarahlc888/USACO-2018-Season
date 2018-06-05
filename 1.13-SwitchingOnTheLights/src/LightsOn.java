import java.util.*;
import java.io.*;

// All 15 test cases correct!!!
// the bug: incrementing the count when a room was visited instead of when it was lit (didn't count unvisited lit rooms)

public class LightsOn {
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	public static void main(String args[]) throws IOException {
		// scan in the rooms and light switches
		
		Scanner scan = new Scanner(new File("lightson.in"));
		int N = scan.nextInt(); // N by N grid of rooms
		int M = scan.nextInt(); // number of lines that detail a room (x, y) with a switch for room (a, b)
		
		ArrayList<Room> rooms = new ArrayList<Room>();

		// Initialize every room
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// rooms is created in order, so binary search works
				Room r = new Room(i, j);
				rooms.add(r);
			}
		}
		
		for (int i = 0; i < M; i++) { // change indexing from 1...N to 0...N-1 by scanning in the values - 1
			// search rooms for the room with position (x, y)
			int ind = Collections.binarySearch(rooms, new Room(scan.nextInt() - 1, scan.nextInt() -1)); 
			// add the light switch to the list of switches in the room
			// no need to catch exceptions - all rooms initialized
			rooms.get(ind).switches.add(new Room(scan.nextInt() - 1, scan.nextInt() - 1));
		}
		
		int count = 1; // count of how many rooms are lit (room 0, 0 is initially lit)
		
		LinkedList<Room> ToBeVisited = new LinkedList<Room>(); // to be visited
		
		int ind = Collections.binarySearch(rooms, new Room(0, 0)); // find room 0, 0
		rooms.get(ind).lit = true; // mark it as lit
		ToBeVisited.add(rooms.get(ind)); // add to ToBeVisited
		
		// DO NOT LOOP THROUGH EVERY POSITION - NOT THAT KIND OF PROBLEM - QUESTION DOESN'T ASK FOR THAT
		// JUST USE A WHILE LOOP
		
		while (!ToBeVisited.isEmpty()) { 
			Room curRoom = ToBeVisited.removeFirst(); // visit curRoom
			
			curRoom.HasBeenVisited = true; // mark the room as visited (it's already lit)
			
			// loop through all of the light switches in the room
			for (int i = 0; i < curRoom.switches.size(); i++) {
				// position of the room that the switch lights up
				int sx = curRoom.switches.get(i).x;
				int sy = curRoom.switches.get(i).y;
				
				// find the room
				ind = Collections.binarySearch(rooms, new Room(sx, sy));
				Room roomToBeLit = rooms.get(ind);
				
				// light the room if wasn't already lit and increment the number of lit rooms
				if (!roomToBeLit.lit) {
					roomToBeLit.lit = true;
					count++;
				}
				
				// if the roomToBeLit has an immediate neighbor that HasBeenVisited, add it to ToBeVisited
				// because it must therefore also be accessible (there's a lit path) - don't miss it!

				for (int j = 0; j < 4; j++) {
					// position of the neighbor
					int nx = sx + dx[j];
					int ny = sy + dy[j];
					
					ind = Collections.binarySearch(rooms, new Room(nx, ny));
					
					// if the neighbor is a valid room
					if (ind > -1) { 
						Room neighbor = rooms.get(ind);
						// if the neighbor has been visited and the roomToBeLit hasn't been visited
						if (neighbor.HasBeenVisited && !roomToBeLit.HasBeenVisited) { 
							roomToBeLit.HasBeenVisited = true; // mark the roomToBeLit as visited
							// ^^ you need to do this in order to prevent overcounting/overlooping etc
							ToBeVisited.addLast(roomToBeLit); // add it to ToBeVisited
						}
					}
				}
			}
			
			// check four directions for lit rooms to be added to ToBeVisited
			for (int i = 0; i < 4; i++) {
				// neighbor position
				int nx = curRoom.x + dx[i]; 
				int ny = curRoom.y + dy[i];
				
				ind = Collections.binarySearch(rooms, new Room(nx, ny));
				if (ind > -1) { // if the neighbor exists
					Room neighbor = rooms.get(ind);
					
					// if the neighbor hasn't been visited and is lit, visit it
					if (!neighbor.HasBeenVisited && neighbor.lit) { 
						neighbor.HasBeenVisited = true;
						ToBeVisited.add(neighbor);
					}
				}
			}
		}
		PrintWriter pw = new PrintWriter(new File("lightson.out"));
		pw.println(count);
		pw.close();
	}
	
	public static class Room implements Comparable<Room> {
		int x;
		int y;
		ArrayList<Room> switches = new ArrayList<Room>();
		boolean HasBeenVisited;
		boolean lit;
		
		public Room(int xin, int yin) {
			x = xin;
			y = yin;
		}
		public Room(int xin, int yin, int a, int b) {
			x = xin;
			y = yin;
			Room SFR = new Room(a, b);
			switches.add(SFR);
		}
		public void addSF(int a, int b) {
			Room SFR = new Room(a, b);
			switches.add(SFR);
		}
		@Override
		public int compareTo (Room A) {
			if (x > A.x) {
				return 1;
			} else if (x < A.x) {
				return -1;
			} else if (y > A.y) {
				return 1;
			} else if (y < A.y) {
				return -1;
			} else {
				return 0;
			}
		}
		public boolean equals (Room A) {
			return x == A.x && y == A.y;
		}
	}
}
