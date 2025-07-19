import java.io.Serializable;
import java.util.*;

public class dawg<K> {
    public Map<K, dawg_node<K>> node_map = new TreeMap<>();
    public List<linked_dawg_node<K>> sentence_heads = new ArrayList<>();

    public void insert(List<K> nodes) {
        if (nodes.isEmpty()) {
            return;
        }

        // 0 == root
        K node_at_zero = nodes.get(0);
        dawg_node<K> current = node_map.get(node_at_zero);
        if (current == null) {
            current = new dawg_node<K>(node_at_zero);
            node_map.put(node_at_zero, current);
        }

        linked_dawg_node<K> head = new linked_dawg_node<>(current);
        linked_dawg_node<K> cursor = head;

        // 1 == next node after root
        for (int i = 1; i < nodes.size(); ++i) {
            K node_at_i = nodes.get(i);
            dawg_node<K> next = current.children.get(node_at_i);
            if (next == null) {
                // Use global root map to deduplicate
                next = node_map.get(node_at_i);
                if (next == null) {
                    next = new dawg_node<K>(node_at_i);
                    node_map.put(node_at_i, next);
                }
                current.add_child(node_at_i, next);
            }

            // Linked list build-up
            linked_dawg_node<K> nextLinked = new linked_dawg_node<>(next);
            cursor.next = nextLinked;
            nextLinked.prev = cursor;
            cursor = nextLinked;

            current = next; // move to next node via reference
        }

        // Set final word in list as end node.
        node_map.get(nodes.get(nodes.size()-1)).canBeEndOfWord = true;
        sentence_heads.add(head);  // Save head of sentence
    }

    public void insert(K prev, K current) {
        // ENSURES THAT YOU DO NOT OVERWRITE CURRENT NODES IN THE NODE MAP
        // You need to perform a check if the node exists. Otherwise if you
        // just add it, the node will overwrite the value. This prevents that.

        // Use existing node so that we can have a &-refernece pointer
        // borrowed and then later added t the child node below.
        dawg_node<K> current_node = node_map.get(current);
        if (current_node == null) {
            current_node = new dawg_node<K>(current);
            node_map.put(current, current_node);
        }

        // Only add edge if prev exists
        dawg_node<K> prev_node = node_map.get(prev);
        if (prev_node != null) {
            prev_node.add_child(current, current_node);
            current_node.add_parent(prev, prev_node);
        }
    }

    public void insert(K current) {
        // Use existing node so that we can have a &-refernece pointer
        // borrowed and then later added t the child node below.
        dawg_node<K> current_node = node_map.get(current);
        if (current_node == null) {
            current_node = new dawg_node<K>(current);
            node_map.put(current, current_node);
        }
    }

    public boolean contains(List<K> sequence) {
        if (sequence.isEmpty()) return false;
        dawg_node<K> current = node_map.get(sequence.get(0));
        if (current == null) return false;

        for (int i = 1; i < sequence.size(); i++) {
            current = current.children.get(sequence.get(i));
            if (current == null) return false;
        }
        return true;
    }
}

