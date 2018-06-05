import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> {
	public static class BinaryNode<E extends Comparable<E>> {
		E data;
		BinaryNode<E> left;
		BinaryNode<E> right;
		
		BinaryNode(E d) {
			data = d;
			left = null;
			right = null;
		}
		BinaryNode() {
			data = null;
			left = null;
			right = null;
		}
	}
	
	// instance var
	BinaryNode<E> root;
	
	// constructors
	public BinarySearchTree() {
		root = null;
	}
	public BinarySearchTree(BinaryNode<E> R) {
		root = R;
	}
	public BinarySearchTree(BinaryNode<E> R, E dat) {
		root = R;
		root.data = dat;
	}
	
	// methods
	public void insert(BinaryNode<E> R, E key) {
		insertRec(R, key);
	}
	
	public BinaryNode<E> insertRec(BinaryNode<E> R, E key) {
		if (R == root && R == null) { // if R is the root and R is null, insert the key
			root = new BinaryNode<E>(key);
			// new object
			return R;
		} else if (R == null) { // if it's an empty leaf node, fill it
			R = new BinaryNode<E>(key);
			return R;
		} else if (R.data.equals(key)) { // if the key is already present, return
			return R;
		} else if (key.compareTo(R.data) > 0) { // if it's to the right, narrow down the tree
			R.right = insertRec(R.right, key); 
			// reassigned because there is a new object
			// pass by reference - old objects can be altered, new objects must be named
		} else { // same but to the left
			R.left = insertRec(R.left, key); // why does this fix it?
		}
		return R;
	}
	
	public BinaryNode<E> delete2(BinaryNode<E> R, E key) {
		// keep going down the tree until you find the node with the key value
		if (key.compareTo(R.data) > 0) {
			R.right = delete2(R.right, key);
		} else if (key.compareTo(R.data) < 0) {
			R.left = delete2(R.left, key);
		} else {
			// if it's a leaf, just get rid of it
			if (R.right == null && R.left == null) {
				R = null;
			} 
			// if it only has a right child, replace it with its right child (all the references shift up)
			else if (R.right != null && R.left == null) {
				R = R.right;
			}
			// if it only has a left child, replace with left
			else if (R.right == null && R.left != null) {
				R = R.left;
			}
			// if it has both children, take the smallest value from the right tree
			else {
				R.data = findMin(R.right).data; // move the value
				R.right = delete2(R.right, R.data); // get rid of the leaf
				// why do you have to reassign????
			}
		}
		return R;
	}
	/*
	public void delete(BinaryNode<E> R, E key) {
		BinaryNode<E> parNode = parentsearch(R, key);
		
		if (parNode.left.data.equals(key)) {
			BinaryNode<E> delNode = parNode.left;
			// if it's a leaf node, just cut it out
			if (delNode.right == null && delNode.left == null) {
				parNode.left = null;
			} else if (delNode.left == null) {
				// if it only has a right child, reassign the parNode.left to be delNode.right
				parNode.left = delNode.right;
			} else if (delNode.right == null) {
				// if it only has a left child, reassign
				parNode.left = delNode.left;
			} else {
				// it has two children, replace the delNode data with the data of the smallest item in the right subtree
				delNode.data = findMin(delNode.right).data;
				BinaryNode<E> parNode2 = parentsearch(delNode.right, delNode.data);
				if (parNode2.left.data == delNode.data) {
					parNode2.left = null;
				} else {
					parNode2.right = null;
				}
			}
		} else if (parNode.right.data.equals(key)) {
			BinaryNode<E> delNode = parNode.right;
			if (delNode.right == null && delNode.left == null) {
				parNode.right = null;
			} else if (delNode.left == null) {
				// if it only has a right child, reassign the parNode.left to be delNode.right
				parNode.right = delNode.right;
			} else if (delNode.right == null) {
				// if it only has a left child, reassign
				parNode.right = delNode.left;
			} else {
				// it has two children, replace the delNode data with the data of the smallest item in the right subtree
				delNode.data = findMin(delNode.right).data;
				BinaryNode<E> parNode2 = parentsearch(delNode.right, delNode.data);
				if (parNode2.left.data == delNode.data) {
					parNode2.left = null;
				} else {
					parNode2.right = null;
				}
			}
		}
	}
	*/
	public void inOrderT(BinaryNode<E> R) {
		if (R == null) {
			return;
		}
		if (R.left != null)  // left-most is first priority
			inOrderT(R.left);
		System.out.println(R.data); // print data
		if (R.right != null)  // then go right
			inOrderT(R.right);
	}
	public boolean boolsearch(BinaryNode<E> R, E key) {
		if (R.data.equals(key)) { // found it means true
			return true;
		} else if (R.left == null && R.right == null) { // at a leaf and didn't find it means false
			return false;
		} else {
			if (R.data.compareTo(key) > 0) {
				return boolsearch(R.left, key); // if R.data > key, take R.left
			} else {
				return boolsearch(R.right, key); // if R.data < key, take R.right
			}
		}
	}
	public BinaryNode<E> parentsearch(BinaryNode<E> R, E key) {
		if (R.left.data.equals(key) || R.right.data.equals(key)) { // found the parent means return the parent
			return R;
		} else {
			if (R.data.compareTo(key) > 0) {
				return parentsearch(R.left, key); // if R.data > key, take R.left
			} else {
				return parentsearch(R.right, key); // if R.data < key, take R.right
			}
		}
	}
	
	// find the minimum node in a sorted binary search tree with root R
	public BinaryNode<E> findMin(BinaryNode<E> R) {
		if (R.left == null) { // termination condition
			return R;
		} else {
			return findMin(R.left); // recursion
		}
	}
	
	// find the maximum node in a sorted binary search tree with root R
	public BinaryNode<E> findMax(BinaryNode<E> R) {
		if (R.right == null) { // termination condition
			return R;
		} else {
			return findMin(R.right); // recursion
		}
	}
	public static void main(String[] args) {
		BinarySearchTree<Integer> T = new BinarySearchTree<Integer>(new BinaryNode<Integer>(10));
		
		Integer[] nums = new Integer[10];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = (int) (Math.random() * 1000);
			T.insert(T.root, nums[i]);
		}
		
		System.out.println("first list");
		T.inOrderT(T.root);
		
		for (int i = 0; i < nums.length / 2; i++) {
			T.delete2(T.root, nums[i]);
		}
		
		System.out.println("after first deletions");
		T.inOrderT(T.root);
		
		for (int i = nums.length / 2; i < nums.length; i++) {
			T.delete2(T.root, nums[i]);
		}
		System.out.println("after all deletions");
		T.inOrderT(T.root);
	}
}