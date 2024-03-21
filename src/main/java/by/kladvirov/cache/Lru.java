package by.kladvirov.cache;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Lru {

    private final Integer CACHE_CAPACITY = 4;

    private Deque<Integer> q = new LinkedList<>();

    private List<Cache> list = new ArrayList<>();

    public String getElementFromCache(Integer key) {
        Cache current = list.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if(current != null){
            q.remove(current.getKey());
            q.addFirst(current.getKey());
            return current.getValue();
        }
        return "not exists";
    }

    public void putElementInCache(Integer key, String value) {
        Cache current = list.stream().filter(cache -> cache.getKey().equals(key)).findFirst().orElse(null);
        if(current != null){
            q.remove(current.getKey());
        } else {
            if(q.size() == CACHE_CAPACITY) {
                Integer temp = q.removeLast();
                Cache toRemove = list.stream().filter(cache -> cache.getKey().equals(temp)).findFirst().orElse(null);
                list.remove(toRemove);
            }
        }
        Cache newItem = new Cache(key, value);
        q.addFirst(newItem.getKey());
        list.add(newItem);
    }


}
