package by.kladvirov.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Lfu {

    private Integer capacity;

    private List<Node> nodeList = new LinkedList<>();

    public Lfu(Integer capacity) {
        this.capacity = capacity;
    }

    public Object getElementFromCache(Object key) {
        Node current = nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if (current != null) {
            current.setFrequency(current.getFrequency() + 1);
            return current.getValue();
        }
        return null;
    }

    public void putElementInCache(Object key, Object value) {
        Node current = nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if (current != null) {
            nodeList.remove(current);
        } else {
            if (nodeList.size() == capacity) {
                Node lfu = nodeList.stream()
                        .min(Comparator.comparing(Node::getFrequency)
                                .thenComparing(Node::getDateTime, Comparator.reverseOrder()))
                        .get();
                nodeList.remove(lfu);
            }
        }
        Node newItem = new Node(key, value, 1, LocalDateTime.now());
        nodeList.add(newItem);
    }

    @AllArgsConstructor
    @Data
    public static class Node {

        private Object key;

        private Object value;

        private Integer frequency;

        private LocalDateTime dateTime;

    }

}
