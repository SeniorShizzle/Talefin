public class SequentialSearchST<K, V>  {


	private Node first;
	private int sz;


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
		Node newNode = new Node(key,value, first);
		first=newNode;
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

        for (Node n = first; n != null; n = n.next) {
            if (key.equals(n.key)) {
                if (n.value instanceof Integer) { // If we're an
                    Integer temp = (Integer)n.value; // Cannot do in one line, because of generics casting
                    temp++;
                    n.value = (V)temp;
                } else {
                    throw new UnsupportedOperationException("Type Integer not found as value for " + key);
                }
                return;
            }
        }

        first = new Node(key, (V)new Integer(1), first); // Assumes we are using type Integer as V
        sz++;
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
	 * deletes the key-value pair associated with the key from the symbol table
	 * @param key the key
	 * @throws NullPointerException if the key is null
	 */
	public void delete(K key) {
		first = delete(first,key);
	}

	/**
	 * recursive delete implementation.
	 *  what is wrong here?
	 * @param nd the current node
	 * @param key the key
	 * @return the node the current node should attach to
	 */
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
	 * @param key the key
	 * @return returns true if the a key-value pair is associated with the key, otherwise false
	 * @throws NullPointerException if the key is null
	 */
	public boolean contains(K key) {
		return (get(key) != null);
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
		// TODO: implement they keys() method
		return null;
	}
}
