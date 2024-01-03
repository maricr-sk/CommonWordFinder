package DataStructuresSourceCode;

/**
 * Class for a Node containing a key-value mapping. Each node contains a
 * reference to the left child, right child, and parent.
 * @author Brian S. Borowski
 * @version 1.0 October 19, 2022
 */
public class Node<K, V> extends Entry<K, V> {
    Node<K, V> left, right, parent;
    int height; // Used only in AVLTreeMap.

    /**
     * Creates a node with a key-value mapping.
     * @param key   the specified key to uniquely represent this Node
     * @param value the specified value to associate with the key
     */
    public Node(K key, V value) {
        super(key, value);
    }

    /**
     * Returns a String representation of the Node with the key and value
     * inside angled brackets.
     * @return a String representation of the Node
     */
    public String toString() {
        return "<" + key + ", " + value + ">";
    }
}
