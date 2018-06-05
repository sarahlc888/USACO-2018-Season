import java.util.*;
import java.io.*;

// array implementation of binary heap
// all methods seem to be working, not fully tested yet
// full binary tree (all levels full except the last, which is filled from the left)

public class MinHeapImp {
	public static void main(String[] args) {

		MinHeap h = new MinHeap(6);
		System.out.println(Arrays.toString(h.harr));
		h.insertKey(3);
		System.out.println(Arrays.toString(h.harr));
		h.insertKey(1);
		System.out.println(Arrays.toString(h.harr));
		h.insertKey(4);
		System.out.println(Arrays.toString(h.harr));
		h.insertKey(2);
		System.out.println(Arrays.toString(h.harr));
		h.removeRoot();
		System.out.println(Arrays.toString(h.harr));
		h.increaseKey(0, 4);
		System.out.println(Arrays.toString(h.harr));
		h.increaseKey(1, 2);
		System.out.println(Arrays.toString(h.harr));
	}
	public static class MinHeap {
		// indexing goes left to right, top to bottom in order of the tree

		int[] harr; // heap array, stores all the numbers
		int heapsize; // number of elements in heap
		int capacity; // max number of elements in heap (capacity >= heapsize)

		public MinHeap(int c) { // constructor
			capacity = c;
			harr = new int[capacity]; // initialize capacity
			heapsize = 0;
		}
		
		public void removeRoot() {
			// remove root element
			// SHL
			harr[0] = harr[heapsize - 1]; // make the last element first
			harr[heapsize-1] = 0; // invalidate the last element
			heapsize--; // decrease heap size
			System.out.println("a: " + Arrays.toString(harr));
			 
			checkDown(0);
			
		}
		
		public void insertKey(int val) {
			heapsize++; // increase heap size
			harr[heapsize - 1] = val; // insert the value at last index, leaf node
			checkUp(heapsize-1); // check up the tree to make sure everything is in order
			if (heapsize == capacity) {
				System.out.println("heap is at full capacity");
			}
		}
		public void increaseKey(int j, int val) { // increase value of node j to val
			harr[j] = val;
			checkDown(j);
		}
		public void decreaseKey(int j, int val) { // decrease value of node j to val
			harr[j] = val;
			checkUp(j);
		}
		public void checkDown(int curind) { // fix / verify the subheap with root curind
			int smallestInd = curind; // index of smallest value out of left, right, cur
			
			// check left child
			if (leftind(curind) < heapsize && harr[leftind(curind)] < harr[curind]) {
				smallestInd = leftind(curind);
			}
			// check right child
			if (rightind(curind) < heapsize && harr[rightind(curind)] < harr[curind]) {
				smallestInd = rightind(curind);
			}
			// swap with the smallest child, if applicable
			if (smallestInd != curind) {
				swap(curind, smallestInd);
				checkDown(smallestInd); // recursively check down
			}

		}
		public void checkUp(int curind) { // the the tree by swapping upward
			while (curind > 0 && harr[curind] < harr[parind(curind)]) { // while the curind < parent, swap
				swap(curind, parind(curind)); 
				curind = parind(curind);
			}
		}
		public void swap(int a, int b) { // swap elements at index a and b
			int temp = harr[a];
			harr[a] = harr[b];
			harr[b] = temp;
		}
		public int parind(int j) { // returns ind of parent of node j
			return (j-1)/2;
		}
		public int leftind(int j) { // returns ind of left child of node j
			return 2*j + 1;
		}
		public int rightind(int j) { // returns ind of right child of node j
			return 2*j + 2;
		}
		public int getMinVal() { // element at harr[0] = min
			return harr[0];
		}
	}


}


