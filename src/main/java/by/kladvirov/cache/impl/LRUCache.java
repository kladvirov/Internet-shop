package by.kladvirov.cache.impl;

import by.kladvirov.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

public class LRUCache implements Cache {

    private Integer capacity;

    private List<Node> nodeList = new LinkedList<>();

    public LRUCache(Integer capacity){
        this.capacity = capacity;
    }

    public Object getElementFromCache(Object key) {
        Node current = getNode(key);
        return (current != null) ? current.getValue() : null;
    }

    public void putElementInCache(Object key, Object value) {
        Node current = getNode(key);
        if(current != null){
            nodeList.remove(current);
        } else {
            if(nodeList.size() == capacity) {
                nodeList.removeFirst();
            }
        }
        Node newItem = new Node(key, value);
        nodeList.addLast(newItem);
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

    @AllArgsConstructor
    @Data
    public static class Node {

        private Object key;

        private Object value;

    }

}
