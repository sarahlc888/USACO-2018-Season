
public class BSTConfusion<E extends Comparable<E>> {
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
	public BSTConfusion() {
		root = null;
	}
	public BSTConfusion(BinaryNode<E> R) {
		root = R;
	}
	public BSTConfusion(BinaryNode<E> R, E dat) {
		root = R;
		root.data = dat;
	}
	
	// methods
	// confusion...
	public BinaryNode<E> change(BinaryNode<E> R, E key) {
		R = new BinaryNode<E>(key);
		System.out.println("RD1: " + R.data);
		return R;
	}
	public void change2(BinaryNode<E> R, E key) {
		R.right = new BinaryNode<E>(key);
		System.out.println("Rd: " + R.right.data);
		System.out.println();
	}
	public void change3(BinaryNode<E> R, E key) {
		R = new BinaryNode<E>(key);
		
		System.out.println("Rd: " + R.data);
		System.out.println();
	}
	public void change4(BinaryNode<E> R, E key) {
		change(R, key);
		System.out.println("Rd2: " + R.data);
		System.out.println();
	}
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
	public BinaryNode<E> insert(BinaryNode<E> R, E key) {
		if (R == root && R == null) { // if R is the root and R is null, make it not null
			root = new BinaryNode<E>(key);
			return R;
		} else if (R == null) { // if it's an empty node, fill it
			R = new BinaryNode<E>(key);
			return R;
		} else if (R.data.equals(key)) {
			return R;
		} else if (key.compareTo(R.data) > 0) { 
			R.right = insert(R.right, key); 
			// TODO: why does it have to be this way?
		} else {
			// TODO: why do you have to reassign it to R.left? It doesn't work otherwise, but why? Is it because of pass by value etc?
			R.left = insert(R.left, key); // why does this fix it?
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
			
		}
		return R;
	}
	public static void main(String[] args) {
		BSTConfusion<Integer> T = new BSTConfusion<Integer>(new BinaryNode<Integer>(10));
		BinaryNode<Integer> a = new BinaryNode<Integer>(7);
		T.root.left = a;
		T.delete2(T.root, 7);
		System.out.println(T.root.left);
		
		System.out.println();
		
		T.change2(T.root, 43);
		//T.change3(T.root.right, 43);
		//T.change4(T.root.right, 43);
		T.inOrderT(T.root);
		System.out.println();
		System.out.println(T.root.right);
		
	}
	
	
}
