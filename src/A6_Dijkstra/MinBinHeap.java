package A6_Dijkstra;

public class MinBinHeap implements Heap_Interface {
	private EntryPair[] array; // load this array
	private int size;
	private static final int arraySize = 199999; // Everything in the array will
													// initially
													// be null. This is ok! Just
													// build out
													// from array[1]

	public MinBinHeap() {
		this.array = new EntryPair[arraySize];
		this.array[0] = new EntryPair(null, -199999); // 0th will be unused for
														// simplicity
														// of child/parent
														// computations...
														// the book/animation
														// page
														// both do this.
	}

	// Please do not remove or modify this method! Used to test your entire
	// Heap.
	@Override
	public EntryPair[] getHeap() {
		return this.array;
	}

	@Override
	public void insert(EntryPair entry) {
		if (entry.getValue() == null) {
			System.out.println("Entry value is null!");
		}
		for (int i = 0; i <= arraySize; i++) {
			if (this.array[i] == null) { // Put the entry into the first empty
											// slot
				this.array[i] = entry;
				this.size++;
				int k = i;
				while (k > 0) {
					if (entry.priority < this.array[k / 2].priority) {
						EntryPair temp = this.array[k];
						this.array[k] = this.array[k / 2];
						this.array[k / 2] = temp;
					} else {
						// System.out.println("Found the right place to
						// insert!");
						break;
					}
					k = k / 2;
				}
				// System.out.println("Inserted at root!");
				break;
			}
		}
	}

	@Override
	public void delMin() {
		if (this.size == 1) {
			this.array[1] = null;
			this.size--;
		}
		if (this.size > 0) {
			this.array[1] = null;
			int pointer = 1;
			this.array[pointer] = this.array[this.size()]; // It is not
															// this.size()
															// - 1 because we
															// start
															// the array at
															// position
															// 1, not 0.
			this.array[this.size()] = null;
			while (pointer * 2 < 9999 && this.array[pointer * 2] != null) {
				if (this.array[pointer * 2 + 1] != null) {
					if (this.array[2 * pointer].priority < this.array[2 * pointer + 1].priority) {
						if (this.array[2 * pointer].priority < this.array[pointer].priority) {
							EntryPair current = this.array[pointer];
							this.array[pointer] = this.array[2 * pointer];
							this.array[2 * pointer] = current;
						}
						pointer = pointer * 2;
					} else {
						if (this.array[2 * pointer + 1].priority < this.array[pointer].priority) {
							EntryPair current = this.array[pointer];
							this.array[pointer] = this.array[2 * pointer + 1];
							this.array[2 * pointer + 1] = current;
						}
						pointer = pointer * 2 + 1;
					}
				} else {
					if (this.array[2 * pointer].priority < this.array[pointer].priority) {
						EntryPair current = this.array[pointer];
						this.array[pointer] = this.array[2 * pointer];
						this.array[2 * pointer] = current;
					}
					pointer = pointer * 2;
				}
			}
			this.size--;
		}
	}

	@Override
	public EntryPair getMin() {
		if (this.size == 0) {
			return null;
		}
		return this.array[1];
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void build(EntryPair[] entries) {
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] != null) {
				this.array[i + 1] = entries[i];
				this.size++;
			}
		}
		for (int j = this.size / 2; j > 0; j--) {
			this.bubbleDown(j); // Start bubble down process at the farthest
								// parent and
								// work all the way up to the root (remember
								// root is at position 1!
		}
	}

	private void bubbleDown(int parentToSwap) {
		int childToSwap = parentToSwap * 2;
		// Set the default child to swap to
		// left child
		if (childToSwap > 9999) {
			return;
		}
		int rightChild = parentToSwap * 2 + 1;
		if (rightChild > 9999) {
			return;
		}
		if (this.array[rightChild] != null
				&& this.array[childToSwap].getPriority() > this.array[rightChild].getPriority()) {
			// If right child is not null and has a higher priority than left
			// child,
			childToSwap = rightChild; // change the child to swap to right child
										// (this, makes sure that we are
										// swapping the parent with the smaller
										// one of its child)
		}
		if (this.array[parentToSwap].getPriority() > this.array[childToSwap].getPriority()) {
			// If parent has lower priority than left child,
			EntryPair tmp = this.array[childToSwap]; // swap parent and left
														// child
			this.array[childToSwap] = this.array[parentToSwap];
			this.array[parentToSwap] = tmp;
			if (childToSwap * 2 < 9999 && this.array[childToSwap * 2] != null) {
				// If further left child is not null, continue bubble down
				// (check if needs to bubble down
				// and does so when
				// needed)
				this.bubbleDown(childToSwap);
			}
		}

	}
}
