package by.kladvirov.cache.impl;

import by.kladvirov.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LFUCache implements Cache {

    private Integer capacity;

    private List<Node> nodeList = new LinkedList<>();

    public LFUCache(Integer capacity) {
        this.capacity = capacity;
    }

    public Object getElementFromCache(Object key) {
        Node current = getNode(key);
        return (current != null) ? getCurrent(current) : null;
    }

    public void putElementInCache(Object key, Object value) {
        Node current = getNode(key);
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

    public boolean containsKey(Object key) {
        return nodeList.stream().anyMatch(node -> node.getKey().equals(key));
    }

    public void delete(Object key) {
        Node current = getNode(key);
        if (current != null) {
            nodeList.remove(current);
        }
    }

    private Node getNode(Object key) {
        return nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
    }

    private Object getCurrent(Node current) {
        current.setFrequency(current.getFrequency() + 1);
        return current.getValue();
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
