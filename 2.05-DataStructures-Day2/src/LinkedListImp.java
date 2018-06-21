import java.io.*;
import java.util.*;
/*
 * untested, not finished
 */
public class LinkedListImp {
	public static void main(String args[]) throws IOException {
		LL a = new LL();
	}
	public static class LL {
		ArrayList<Node> nodes; // nodes
		
		public LL() {
			nodes = new ArrayList<Node>();
		}
		
		public void remove(int ind) {
			Node rem = nodes.get(ind); // node to remove
			rem.backward.forward = rem.forward;
			rem.forward.backward = rem.backward;
		}
		public void add(int ind, Node add) {
			Node back = nodes.get(ind-1); // push this node back
			Node front = nodes.get(ind); // push this node forward
			
			// connect add to front and back
			add.forward = front;
			add.backward = back;
			
			// connect front and back to add
			back.forward = add;
			front.backward = add;
			
		}
	}
	public static class Node {
		Node forward;
		Node backward;
		int val;
		public Node(Node a, Node b) {
			forward = a;
			backward = b;
		}
		public String toString() {
			return "" + val;
		}
	}
}
