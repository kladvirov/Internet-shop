package by.kladvirov.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

public class Lru {

    private Integer capacity;

    private List<Node> nodeList = new LinkedList<>();

    public Lru(Integer capacity){
        this.capacity = capacity;
    }

    public Object getElementFromCache(Object key) {
        Node current = nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        return (current != null) ? current.getValue() : null;
    }

    public void putElementInCache(Object key, Object value) {
        Node current = nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
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

    @AllArgsConstructor
    @Data
    public static class Node {

        private Object key;

        private Object value;

    }

}
