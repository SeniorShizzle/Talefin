import java.util.Iterator;
import java.util.NoSuchElementException;

public class SequentialSearchST<K, V>  {


	private Node first;
	private int sz;

    private K min;
    private K max;


	private class Node {
		K key;
		V value;
		Node next;

		public Node(K k, V v, Node n) {
			this.key=k;
			this.value=v;
			this.next=n;
		}
	}

	public SequentialSearchST() {}


	/**
	 * add key-value pair to the symbol table
	 * @param key the key
	 * @param value the value
	 * @throws NullPointerException if key is null
	 */
    @SuppressWarnings("unchecked")
	public void put(K key, V value) {
		if (key == null) throw new NullPointerException("null keys are not defined");

		if (value == null) {
			delete(key);
			return;
		}
		for(Node n=first;n!=null;n=n.next)
		{
			if (key.equals(n.key)) {
				n.value=value;
				return;
			}
		}

        if (key instanceof Comparable) {
            // If we're comparable, we can set min and max
            if (min == null || ((Comparable) key).compareTo(min) < 0)      min = key;
            else if (max == null || ((Comparable) key).compareTo(max) > 0) max = key;
        }

		first = new Node(key,value, first);
		sz++;
	}

    /**
     * NOTE: Requires V generic type to be Integer
     *
     * Increments or inserts the value associated with the given key. Requires V value to be Integer type
     * If the key is not present, creates a new entry by casting Integer to generic V
     *
     * Speeds up the program by eliminating two iterations of the list needed to see if the table contains()
     * and if so to get() the value to increment it.
     *
     *
     * @param key the key, of type K, at which to insert the value
     * @throws UnsupportedOperationException if value associated with key cannot be incremented (not Integer)
     *         or NullPointerException if key is null, or ClassCastingException if V is not compatible with Integer
     */
    @SuppressWarnings("unchecked")
    public void incrementOrInsert(K key) throws UnsupportedOperationException{
        if (key == null) throw new NullPointerException("null keys are not defined");

        if (!outOfBounds(key)) { // key isn't necessarily out of bounds
            for (Node n = first; n != null; n = n.next) {
                if (key.equals(n.key)) {
                    if (n.value instanceof Integer) { // If we're an
                        Integer temp = (Integer) n.value; // Cannot do in one line, because of generics casting
                        temp++;
                        n.value = (V) temp;
                    } else {
                        throw new UnsupportedOperationException("Type Integer not found as value for " + key);
                    }
                    return;
                }
            }
        }

        if (key instanceof Comparable) {
            // If we're comparable, we can set min and max
            if (min == null || ((Comparable) key).compareTo(min) < 0)      min = key;
            else if (max == null || ((Comparable) key).compareTo(max) > 0) max = key;
        }

        first = new Node(key, (V)new Integer(1), first); // Assumes we are using type Integer as V
        sz++;
    }

    /**
     * Returns TRUE if the key is outside of the bounds, and therefore guaranteed not in the data set
     * @param key the K type key to test, should implement Comparable
     * @return TRUE if the key is outside the data set, FALSE otherwise, or
     */
    @SuppressWarnings("unchecked")
    private boolean outOfBounds(K key){
        if (min == null || max == null) return false; // We should not assume

        if (key instanceof Comparable) {
            // If we're comparable, we can set min and max
            if (((Comparable) key).compareTo(min) < 0) return true;
            else if (((Comparable) key).compareTo(max) > 0) return true;
        }

        return false; // We're not EXPLICITLY out of the bounds
    }


	/**
	 * retrieve the value associated with the key
	 * @param key the key
	 * @return returns the value associated with the key, otherwise it returns null
	 * @throws NullPointerException if the key is null
	 */
	public V get(K key) {
		for(Node n=first;n!=null;n=n.next) {
			if (key.equals(n.key)) {
				return n.value;
			}
		}
		return null;
	}

	/**
	 * deletes the key-value pair associated with the key from the symbol table.
     * Deprecates
	 * @param key the key
	 * @throws NullPointerException if the key is null
	 */
	public void delete(K key) {
        if (key == null) return;
        if (first == null) return; // No list
        if (key.equals(first.key)){ // Catch the case where there is only one node
            sz--;
            first = first.next; // Garbage Collection will take care of this, don't worry
            return;
        }

        for (Node current = first; current.next != null; current = current.next) {
            if (key.equals(current.next.key)) {
                sz--;
                current.next = current.next.next; // ARC/Garbage Collection away!
                return;
            }
        }
		//first = delete(first, key); // No longer ned the recursive implementation
	}

	/**
     * NOTE: This method has been deprecated. Please use {@link #delete(Object)} instead.
     *       This method has been found to have significant recursive overhead; the new
     *       implementation does not rely on recursion and is suitable for large lists.
     *       
	 * recursive delete implementation.
     *
	 *
	 * @param nd the current node
	 * @param key the key
	 * @return the node the current node should attach to
	 */
    @Deprecated
	private Node delete(Node nd, K key) {
		if (nd == null) return null;

		if (key.equals(nd.key)) {
			sz--;
			return nd.next;
		}
		nd.next=delete(nd.next, key);
		return nd;
	}


	/**
	 * is a key in the symbol table or not?
     *
     * Newly optimized to check min and max values for insertions if K extends Comparable
	 * @param key the key
	 * @return returns true if the a key-value pair is associated with the key, otherwise false
	 * @throws NullPointerException if the key is null
	 */
    @SuppressWarnings("unchecked")
	public boolean contains(K key) {
		return !outOfBounds(key) && get(key) != null; // WOW Java
	}

	/**
	 *
	 * @return true if the table is empty, otherwise false
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * returns the number of key-value pairs in the table
	 * @return the size of the symbol table
	 */
	public int size() {
		return sz;
	}

	/**
	 *
	 * @return iterator of keys in the table
	 */
    public Iterable<K> keys() {
        /*return new Iterable<>(Iterator ->
                new Iterator<K>() {
                    Node currentNode = first;
                    K current = currentNode.key;

                    *//**
                     * Returns {@code true} if the iteration has more elements.
                     * (In other words, returns {@code true} if {@link #next} would
                     * return an element rather than throwing an exception.)
                     *
                     * @return {@code true} if the iteration has more elements
                     *//*
                    @Override
                    public boolean hasNext() {
                        return currentNode != null && currentNode.next != null;
                    }

                    *//**
                     * Returns the next element in the iteration.
                     *
                     * @return the next element in the iteration
                     * @throws NoSuchElementException if the iteration has no more elements
                     *//*
                    @Override
                    public K next() {
                        currentNode = currentNode.next;
                        current = currentNode.key;
                        return current;
                    }

                    *//**
                     * Removes from the underlying collection the last element returned
                     * by this iterator (optional operation).  This method can be called
                     * only once per call to {@link #next}.  The behavior of an iterator
                     * is unspecified if the underlying collection is modified while the
                     * iteration is in progress in any way other than by calling this
                     * method.
                     *
                     * @throws UnsupportedOperationException if the {@code remove}
                     *                                       operation is not supported by this iterator
                     * @throws IllegalStateException         if the {@code next} method has not
                     *                                       yet been called, or the {@code remove} method has already
                     *                                       been called after the last call to the {@code next}
                     *                                       method
                     * @implSpec The default implementation throws an instance of
                     * {@link UnsupportedOperationException} and performs no other action.
                     *//*
                    @Override
                    public void remove() {


                    }
                });*/

        return null;
    }
}

