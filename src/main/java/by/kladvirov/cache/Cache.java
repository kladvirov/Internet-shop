package by.kladvirov.cache;

public interface Cache {

    Object getElementFromCache(Object key);

    void putElementInCache(Object key, Object value);

    boolean containsKey(Object key);

    void delete(Object key);
}
