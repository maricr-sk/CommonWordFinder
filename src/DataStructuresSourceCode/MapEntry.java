package DataStructuresSourceCode;

/**
 * Class for encapsulating a key-value entry in a hash map.
 * Contains a reference to the next entry in the chain.
 * @author Brian S. Borowski
 * @version 1.0 December 10, 2022
 */
public class MapEntry<K, V> extends Entry<K, V> {
    MapEntry<K, V> next;

    MapEntry(K key, V value) {
        super(key, value);
    }
}
