package hashTable;
/**
 * 
 */

/**
 * @author ahmetucar
 *
 */
public class HashTable<E> {
	private static final double MAX_LOAD_FACTOR = 0.75;
	private HashEntry<E>[] table = new HashEntry[8];
	private int size = 0;
	
	private int hashFunction(Object o) {
		return Math.abs(o.hashCode() % table.length);
	}
	
	// Compares the size of the hash table to its capacity
	private double loadFactor() {
		return (double) size / table.length;
	}	
	
	// A single HashEntry represents an value entered into the hash table,
	// stored in a chain in a specific bucket.
	private class HashEntry<E> {
		private E data;
		private HashEntry<E> next;
		
		// constructors
		public HashEntry(E data) {
			this.data = data;
			this.next = null;
		}
		
		public HashEntry(E data, HashEntry<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	// Adds a new value to the hash table
	public void add(E x) {
		if (!contains(x)) {
			if (loadFactor() >= MAX_LOAD_FACTOR) {
				rehash();
			}
			int bucket = hashFunction(x);
			table[bucket] = new HashEntry<E>(x, table[bucket]);
			size++;
		}
	}
	
	// Checks to see if specified value is already stored in hash table
	public boolean contains(E x) {
		int bucket = hashFunction(x);
		HashEntry<E> current = table[bucket];
		while (current != null) {
			if (current.data == x) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	// Removes a hash entry if it is in the hash table
	public void remove(E x) {
		int bucket = hashFunction(x);
		HashEntry<E> current = table[bucket];
		if (current != null) {
			// check first entry in bucket
			if (current.data == x) {
				table[bucket] = current.next;
				size--;
			}
			else {
			// check rest of the bucket list
				while (current.next != null) {
					if (current.next.data == x) {
						current.next = current.next.next;
						size--;
						break;
					}
					current = current.next;
				}
			}
		}
	}
	
	// If hash table's size exceeds load factor, then 
	// create a larger internal table
	private void rehash() {
		// replace old hash table with larger one
		HashEntry<E>[] oldTable = table;
		table = new HashEntry[table.length * 2];
		size = 0;
		
		// add all of the previous hash entries into new table
		for (int i = 0; i < oldTable.length; i++) {
			HashEntry<E> current = oldTable[i];
			while (current != null) {
				add(current.data);
				current = current.next;
			}
		}
		
	}
	
	// Outputs the contents of the hash table on a 
	// bucket-by-bucket basis
	private void print() {
		System.out.println("table length: " + table.length);
		System.out.println("size: " + size);
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				System.out.print("Index " + i + ": ");
				HashEntry<E> current = table[i];
				while (current != null) {
					System.out.print(current.data + ", ");
					current = current.next;
				}
				System.out.println();
			}
		}
		System.out.println();
	}
}
