public class linked_dawg_node<K> {
    public dawg_node<K> dawgNode;
    public linked_dawg_node<K> prev;
    public linked_dawg_node<K> next;

    public linked_dawg_node(dawg_node<K> node) {
        this.dawgNode = node;
    }
}
