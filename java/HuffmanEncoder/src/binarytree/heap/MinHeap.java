package binarytree.heap;

import binarytree.huffmantree.HuffTree;

/* Max-heap implementation */
public class MinHeap {

	private HuffTree[] Heap;// Pointer to the heap array
	private int size;	// Maximum size of the heap
	private int n;		// Number of elements now in heap
	
	// Constructor
	public MinHeap(HuffTree[] h, int num, int max) {
		Heap = h;
		n = num;
		size = max;
		buildHeap();
	}
	
	// Help functions
	/* Put element in its correct place */
	private void siftdown(int pos) {
		assert pos >= 0 && pos < n : "Illegal heap position";
		while(! isLeaf(pos) ) {
			int j = leftChild(pos);
			if( ( j<(n-1) ) && ( Heap[j].freq() > Heap[j+1].freq() ) )
				j++;	// j is now index of child with miner value
			if(Heap[pos].freq() <= Heap[j].freq())
				return;
			// Swap the elements of j and pos
			swap(pos, j);
			
			pos = j; // Move down
		}
	}
	
	private void swap(int a, int b) {
		assert a >=0 && a < n && b >= 0 && b < n : "No such element";
		if(a == b)
			return;
		HuffTree temp = Heap[a];
		Heap[a] = Heap[b];
		Heap[b] = temp;
	}
	
	// Other functions
	/* Return current size of the heap */
	public int heapSize() {
		return n;
	}
	
	/* Is pos a leaf position? */
	public boolean isLeaf(int pos) {
		return (pos >= n/2) && (pos < n);
	}
	
	/* Return position for left child of pos */
	public int leftChild(int pos) {
		assert pos < n/2 : "Position has no left child";
		return 2*pos + 1;
	}
	
	/* Return position for right child of pos */
	public int rightChild(int pos) {
		assert pos < (n-2)/2 : "Position has no right child";
		return 2*pos+2;
	}
	
	/* Return position for parent */
	public int parent(int pos) {
		assert pos > 0 : "Position has no parent";
		return (pos - 1) / 2;
	}
	
	/* Heapify contents of Heap */
	public void buildHeap() {
		for (int i=n/2-1; i>=0; i--)
			siftdown(i);
	}
	
	
	
	/* Insert into heap */
	public void insert(HuffTree val) {
		assert n < size : "Heap is full";
		int curr = n++;
		Heap[curr] = val;
		// Now sift up until curr's parent's key > curr's key
		while(( curr != 0 ) && ( Heap[curr].freq() < Heap[parent(curr)].freq() ) ) {
			swap(curr, parent(curr));
			curr = parent(curr);			
		}			
	}
	
	/* Remove the maximum value */
	public HuffTree removeMin() {
		assert n > 0 : "Removing from empty heap";
		// Swap maximum with last value
		swap(0, --n);
		if(n != 0)	// Not on last element
			// Put new heap root val in correct place
			siftdown(0);
		return Heap[n];		
	}
	
	/* Remove element at specified position */
	public HuffTree remove(int pos) {
		assert (pos>0) && (pos<n) : "Illegal heap position";
		swap(pos, --n);		// Swap with last value
		if(n != 0)			// Not on last element
			siftdown(pos);	// Put new heap root val in correct place
		return Heap[n];
	}
	
	/* Return element with biggest key */
	public HuffTree topHeap() {
		if(heapSize() <= 0)
			return null;
		else
			return Heap[0];
	}

}
