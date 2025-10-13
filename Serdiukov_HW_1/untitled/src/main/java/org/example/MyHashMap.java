package org.example;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;

    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        final int hash;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.table = new Entry[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public V put(K key, V value) {
        if (key == null) {
            return putForNullKey(value);
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);


        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (e.key == key || key.equals(e.key))) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        addEntry(hash, key, value, index);
        return null;
    }

    private V putForNullKey(V value) {
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        addEntry(0, null, value, 0);
        return null;
    }

    private void addEntry(int hash, K key, V value, int index) {
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(hash, key, value, e);

        if (size++ >= threshold) {
            resize(2 * table.length);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == Integer.MAX_VALUE) {
            threshold = Integer.MAX_VALUE;
            return;
        }


        Entry<K, V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    private void transfer(Entry<K, V>[] newTable) {
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> e = table[i];
            while (e != null) {
                Entry<K, V> next = e.next;
                int index = indexFor(e.hash, newTable.length);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
    }

    public V get(K key) {
        if (key == null) {
            return getForNullKey();
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (e.key == key || key.equals(e.key))) {
                return e.value;
            }
        }
        return null;
    }

    private V getForNullKey() {

        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                return e.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            return removeForNullKey();
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);
        Entry<K, V> prev = null;

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (e.key == key || key.equals(e.key))) {
                V oldValue = e.value;

                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }

                size--;
                return oldValue;
            }
            prev = e;
        }
        return null;
    }

    private V removeForNullKey() {
        Entry<K, V> prev = null;

        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;

                if (prev == null) {
                    table[0] = e.next;
                } else {
                    prev.next = e.next;
                }

                size--;
                return oldValue;
            }
            prev = e;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        table = new Entry[DEFAULT_CAPACITY];
        size = 0;
        threshold = (int) (DEFAULT_CAPACITY * loadFactor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean first = true;
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(e.key).append("=").append(e.value);
                first = false;
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
