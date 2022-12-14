package dev.fastmc.common.collection;

import dev.fastmc.common.UtilsKt;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("unused")
public final class Long2ByteCacheMap implements Long2ByteMap {
    private final int capacity;
    private final Long2ByteLinkedOpenHashMap backingMap;

    public Long2ByteCacheMap(int capacity, byte defaultReturnValue) {
        this.capacity = capacity;
        this.backingMap = new Long2ByteLinkedOpenHashMap(capacity, 0.9999999f) {
            @Override
            protected void rehash(int newN) {}
        };
        this.backingMap.defaultReturnValue(defaultReturnValue);
    }

    public Long2ByteCacheMap(int capacity) {
        this.capacity = capacity;
        this.backingMap = new Long2ByteLinkedOpenHashMap(capacity, 0.9999999f) {
            @Override
            protected void rehash(int newN) {}
        };
    }

    @NotNull
    @Override
    public ObjectSet<Map.Entry<Long, Byte>> entrySet() {
        return backingMap.entrySet();
    }

    @Override
    public ObjectSet<Entry> long2ByteEntrySet() {
        return backingMap.long2ByteEntrySet();
    }

    @NotNull
    @Override
    public LongSet keySet() {
        return backingMap.keySet();
    }

    @NotNull
    @Override
    public ByteCollection values() {
        return backingMap.values();
    }

    @Override
    public boolean containsValue(byte value) {
        return backingMap.containsValue(value);
    }

    @Override
    public byte put(long key, byte value) {
        if (backingMap.size() >= capacity) {
            backingMap.removeLastByte();
        }
        return backingMap.putAndMoveToFirst(key, value);
    }

    public void put(long key, boolean value) {
        if (backingMap.size() >= capacity) {
            backingMap.removeLastByte();
        }
        backingMap.putAndMoveToFirst(key, value ? UtilsKt.BYTE_TRUE : UtilsKt.BYTE_FALSE);
    }

    @Override
    public byte get(long key) {
        return backingMap.getAndMoveToFirst(key);
    }

    @Override
    public byte remove(long key) {
        return backingMap.remove(key);
    }

    @Override
    public boolean containsKey(long key) {
        return backingMap.containsKey(key);
    }

    @Override
    public void defaultReturnValue(byte rv) {
        backingMap.defaultReturnValue(rv);
    }

    @Override
    public byte defaultReturnValue() {
        return backingMap.defaultReturnValue();
    }

    @Override
    public Byte put(Long key, Byte value) {
        return this.put(key.longValue(), value.byteValue());
    }

    @Override
    public Byte get(Object key) {
        return this.get(((Long) key).longValue());
    }

    @Override
    public boolean containsKey(Object key) {
        return this.containsKey(((Long) key).longValue());
    }

    @Override
    public boolean containsValue(Object value) {
        return this.containsValue(((Byte) value).byteValue());
    }

    @Override
    public Byte remove(Object key) {
        return this.remove(((Long) key).longValue());
    }

    @Override
    public void putAll(@NotNull Map<? extends Long, ? extends Byte> m) {
        int mapSize = m.size();
        while (backingMap.size() + mapSize > capacity) {
            backingMap.removeLastByte();
        }
        backingMap.putAll(m);
    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public void clear() {
        backingMap.clear();
    }

    public int getCapacity() {
        return capacity;
    }
}
