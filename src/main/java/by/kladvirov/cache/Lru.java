package by.kladvirov.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

public class Lru {

    private Integer CACHE_CAPACITY;

    private List<Node> list = new LinkedList<>();

    public Lru(Integer CACHE_CAPACITY){
        this.CACHE_CAPACITY = CACHE_CAPACITY;
    }

    public Object getElementFromCache(Object key) {
        Node current = list.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if(current != null){
            list.remove(current);
            list.addFirst(current);
            return current.getValue();
        }
        return null;
    }

    public void putElementInCache(Object key, Object value) {
        Node current = list.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if(current != null){
            list.remove(current);
        } else {
            if(list.size() == CACHE_CAPACITY) {
                list.removeLast();
            }
        }
        Node newItem = new Node(key, value);
        list.addFirst(newItem);
    }

    @AllArgsConstructor
    @Data
    public static class Node {

        private Object key;

        private Object value;

    }

}
