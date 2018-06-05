import java.io.*;
import java.util.*;

// root = smallest value
// not yet complete, not yet tested
// how do you find max??
// swap method does not work, add method does not add correctly
// add method just tacks on to the last indexed node, need to check if there's space

// tree version

// does not work at all

public class XBHMin {
	public static void main(String args[]) throws IOException {
		
		BinaryHeap b = new BinaryHeap();
		
		
		b.addNode(3);
		b.addNode(2);
		b.addNode(1);
		
		System.out.println(b.heap.get(0).id);
		System.out.println(b.heap.get(0).val);
		System.out.println(b.heap.get(0).left);
		System.out.println(b.heap.get(0).right);
		System.out.println(b.heap.get(0).parent);
		
		System.out.println();
		
		
		System.out.println(b.heap.get(1).id);
		System.out.println(b.heap.get(1).val);
		System.out.println(b.heap.get(1).left);
		System.out.println(b.heap.get(1).right);
		System.out.println(b.heap.get(1).parent);	

		
		System.out.println();
		
		
		System.out.println(b.heap.get(2).id);
		System.out.println(b.heap.get(2).val);
		System.out.println(b.heap.get(2).left);
		System.out.println(b.heap.get(2).right);
		System.out.println(b.heap.get(2).parent);	
		
		//b.deleteMin();
		b.swap(b.heap.get(2), b.heap.get(1));
		System.out.println();
		System.out.println();
		
		
		System.out.println(b.heap.get(0).id);
		System.out.println(b.heap.get(0).val);
		System.out.println(b.heap.get(0).left);
		System.out.println(b.heap.get(0).right);
		System.out.println(b.heap.get(0).parent.id);
		
		System.out.println();
		
		
		System.out.println(b.heap.get(1).id);
		System.out.println(b.heap.get(1).val);
		System.out.println(b.heap.get(1).left.id);
		System.out.println(b.heap.get(1).right);
		System.out.println(b.heap.get(1).parent);	

		
		System.out.println();
		
		
		System.out.println(b.heap.get(2).id);
		System.out.println(b.heap.get(2).val);
		System.out.println(b.heap.get(2).left.id);
		System.out.println(b.heap.get(2).right);
		System.out.println(b.heap.get(2).parent.id);	
	}
	public static class BinaryHeap {
		ArrayList<BHNode> heap; // heap
		
		public BinaryHeap() { // constructor
			heap = new ArrayList<BHNode>();
		}
		public void addNode(int value) { // add a node to the heap
			// make a new node w/ proper id and value
			BHNode newnode = new BHNode(); // curnode
			newnode.id = heap.size(); // proper id 
			newnode.val = value;
			
			// add it to the end of the heap
			heap.add(newnode);
			
			// give the new node a parent node
			BHNode parnode; 
			if (heap.size() == 1) parnode = null; // if there are no other nodes, no parent
			else parnode = heap.get(heap.size() - 2); // otherwise, give it the node before it
			newnode.parent = parnode;
			
			while (parnode != null && newnode.val < parnode.val) {
				// while new node < parent, swap to move the node up the tree
				swap(parnode, newnode);
				// update parnode for next iteration
				parnode = newnode.parent; 
			}
		}
		
		public BHNode findMin() { // return id of node with min value (the root)
			int start = 0;
			
			while (heap.get(start).left != null && heap.get(start).right != null &&
					heap.get(start).parent != null) {
				// while the node at index start has no connections to the tree
				// (it's been cut off or deleted), increment
				start++;
			}
			
			BHNode curnode = heap.get(start);
			
			while (curnode.parent != null) {
				// while there is a parent, keep climbing the tree
				curnode = curnode.parent;
			}
			return curnode;
		}
		public BHNode findLeaf() {
			int start = 0;
			
			while (heap.get(start).left != null && heap.get(start).right != null &&
					heap.get(start).parent != null) {
				// while the node at index start has no connections to the tree
				// (it's been cut off or deleted), increment
				start++;
			}
			BHNode curnode = heap.get(start);
			
			while (curnode.left != null || curnode.right != null) {
				// while there is a child, keep going down the tree
				if (curnode.left != null) {
					curnode = curnode.left;
				} else {
					curnode = curnode.right;
				}
			}
			return curnode;
		}
		
		public void deleteMin() {
			BHNode ogroot = findMin();
			BHNode newroot = findLeaf();
			
			// store values, cut off the root
			BHNode rootleft = ogroot.left;
			BHNode rootright = ogroot.right;
			ogroot.left = null;
			ogroot.right = null;
			ogroot.id = -1;
			ogroot.val = -1;
			
			
			// change connections to make a leaf node the new root - reassign
			newroot.left = rootleft;
			newroot.right = rootright;
			newroot.parent = null;
			if (rootleft != null) rootleft.parent = newroot;
			if (rootright != null) rootright.parent = newroot;
			
			// float and sink as appropriate
			BHNode leftchild = newroot.left;
			BHNode rightchild = newroot.right;
			
			System.out.println("newroot: " + newroot.val);
			System.out.println("leftchild: " + leftchild.val);
			System.out.println("rightchild: " + rightchild);
			
			
			
			while ((leftchild != null || rightchild != null) && 
					(leftchild != null && newroot.val > leftchild.val || 
					rightchild != null && newroot.val > rightchild.val)) {
				
				//System.out.println("adjust");
				
				if (newroot.val > leftchild.val) {
					// swap
					swap(newroot, leftchild);
				} else {
					// swap
					swap(newroot, rightchild);
				}
				// update the children
				leftchild = newroot.left;
				rightchild = newroot.right;
			}
			
		}
		public void swap(BHNode a, BHNode b) { // switch a and b, where a is the original parent of b			
			System.out.println("swap " + a.val + " and " + b.val);
			
			BHNode aleft = a.left;
			BHNode aright = a.right;
			BHNode apar = a.parent;
			BHNode bleft = b.left;
			BHNode bright = b.right;
			BHNode bpar = b.parent;
			
			
			
			System.out.println(aleft);
			System.out.println(aright);
			System.out.println(apar);
			System.out.println(bleft);
			System.out.println(bright);
			System.out.println(bpar);
			
			// reassign a below b, get a's other child
			// b is a's new parent
			if (aleft != null && aleft.equals(b)) {
				System.out.println("t1");
				b.left = a;
				if ((aright) != null) 
					b.right = (aright); // get a's other child
			} else if (aright != null) {
				System.out.println("t2");
				b.right = a;
				if (aleft != null) 
					b.left = (aleft); // get a's other child
				
			}
			// put b's old children under a
			if ((bleft) != null) a.left = (bleft);
			if ((bright) != null) a.right = (bright);
			
			// assign a's old parent as b's new parent
			if (apar != null && (apar).left == a) {
				(apar).left = b;
			} else if (apar != null) {
				(apar).right = b;
			}
			b.parent = (apar);
			
		
			
		}
 		public int findMax() { // return id of node with max value
			
			// how do you find max???
			
			return 0;
			
		}
 	}
	public static class BHNode {
		int id;
		int val;
		BHNode left;
		BHNode right;
		BHNode parent;
		public BHNode(int i, int v, BHNode l, BHNode r, BHNode p) { // construct w/ all vals
			id = i;
			val = v;
			left = l;
			right = r;
			parent = p;
		}
		public BHNode() { // construct w/ all 0
			id = -1;
			val = -1;
			left = null;
			right = null;
			parent = null;
		}
	}
	
}
