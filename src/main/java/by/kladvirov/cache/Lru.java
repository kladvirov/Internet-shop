package by.kladvirov.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

public class Lru {

    private Integer CACHE_CAPACITY;

    private List<Node> nodeList = new LinkedList<>();

    public Lru(Integer CACHE_CAPACITY){
        this.CACHE_CAPACITY = CACHE_CAPACITY;
    }

    public Object getElementFromCache(Object key) {
        Node current = nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if(current != null){
            nodeList.remove(current);
            nodeList.addFirst(current);
            return current.getValue();
        }
        return null;
    }

    public void putElementInCache(Object key, Object value) {
        Node current = nodeList.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if(current != null){
            nodeList.remove(current);
        } else {
            if(nodeList.size() == CACHE_CAPACITY) {
                nodeList.removeLast();
            }
        }
        Node newItem = new Node(key, value);
        nodeList.addFirst(newItem);
    }

    @AllArgsConstructor
    @Data
    public static class Node {

        private Object key;

        private Object value;

    }

}
