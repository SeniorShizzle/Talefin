
public class SequentialSearchST<K,V>  {
	
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
	
	public SequentialSearchST() { }
	

	/**
	 * add key-value pair to the symbol table
	 * @param key
	 * @param value
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
	 * retrieve the value associated with the key
	 * @param key
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
	 * @param key
	 * @throws NullPointerException if the key is null
	 */
	public void delete(K key) {
		first = delete(first,key);	
	}

	/**
	 * recursive delete implementation. 
	 *  what is wrong here?
	 * @param nd
	 * @param key
	 * @return
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
	 * @param key
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
	 * @return
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
