
public class Lab3 {

	public static void main(String [] args) {

		int minlen = Integer.parseInt(args[0]);
		In in = new In(args[1]);

		SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
		Stopwatch sw = new Stopwatch();
		String word;

		// read loop
        while (in.exists() && in.hasNextChar() && (word = in.readString()) != null)
        {
           if (word.length() < minlen) continue;  // filter tokens based on word length.

           if (!st.contains(word)) st.put(word, 1);
           else                    st.put(word, st.get(word) + 1);

            // New implementation
            //st.incrementOrInsert(word); // Eliminates two iterations of the entire dictionary
        }

        StdOut.println(sw.elapsedTime() + " seconds to index " + st.size() +" words");
     }
}
