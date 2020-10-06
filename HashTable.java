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
	
	public static void main(String[] args) {
		// test integer hash table
		HashTable<Integer> intTable = new HashTable<>();
		intTable.add(5);
		intTable.add(-10);
		intTable.add(13);
		intTable.add(21);
		intTable.print();
		if (intTable.contains(13)) {
			System.out.println("contains 13");
		}
		System.out.println();
		intTable.remove(13);
		intTable.print();
		if (intTable.contains(13)) {
			System.out.println("contains 13");
		}
		System.out.println();
				// test rehash: 7/8 hash entries means table size should double
		intTable.add(7);
		intTable.add(11);
		intTable.add(98);
		intTable.add(14);
		intTable.print();
		System.out.println();
		System.out.println();
		System.out.println();

		
		// test integer hash table
		HashTable<String> stringTable = new HashTable<>();
		stringTable.add("hi");
		stringTable.add("my name");
		stringTable.add("is");
		stringTable.add("ahmet");
		stringTable.print();
		if (stringTable.contains("ahmet")) {
			System.out.println("contains ahmet");
		}
		System.out.println();
		stringTable.remove("ahmet");
		stringTable.print();
		if (stringTable.contains("ahmet")) {
			System.out.println("contains ahmet");
		}
		System.out.println();
		// test rehash: 7/8 hash entries means table size should double
		stringTable.add("my brother's");
		stringTable.add("name is Sercan");
		stringTable.add("and my sister's");
		stringTable.add("name is Melissa");
		stringTable.print();
																															HashTable<Integer> table = new HashTable<>();
																															table.add(5);
																															table.add(-10);
																															table.add(13);
																															table.add(21);
															


	}

}
