import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class dawg_node<K> {
    public K key;
    public boolean canBeEndOfWord = false;
    int traversalWeight = 0;

    public Map<K, dawg_node<K>> children = new TreeMap<>();
    public Map<K, dawg_node<K>> parents = new TreeMap<>();

    public dawg_node(K key) {
        this.key = key;
    }

    public void add_child(K child, dawg_node<K> node){
        ++this.traversalWeight;
        this.children.put(child, node);
    }

    public void add_parent(K parent, dawg_node<K> node){
        this.parents.put(parent, node);
    }
}
