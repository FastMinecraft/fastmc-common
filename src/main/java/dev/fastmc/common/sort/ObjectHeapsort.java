package dev.fastmc.common.sort;

import java.util.Comparator;

@SuppressWarnings({ "DuplicatedCode", "unchecked" })
public class ObjectHeapsort implements ObjectSort {
    private static <T> void siftDown(T[] array, int p, T value, int from, int to) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || ((Comparable<T>) array[k]).compareTo(array[k - 1]) < 0) {
                --k;
            }
            if (((Comparable<T>) array[k]).compareTo(value) <= 0) {
                break;
            }
        }
        array[p] = value;
    }

    public static <T> void sort(T[] array, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to);
        }
        while (--to > from) {
            T max = array[from];
            siftDown(array, from, array[to], from, to);
            array[to] = max;
        }
    }

    public static <T> void sort(T[] array) {
        sort(array, 0, array.length);
    }

    private static <T> void siftDown(T[] array, int p, T value, int from, int to, Comparator<T> comp) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || comp.compare(array[k], array[k - 1]) < 0) {
                --k;
            }
            if (comp.compare(array[k], value) <= 0) {
                break;
            }
        }
        array[p] = value;
    }

    public static <T> void sort(T[] array, int from, int to, Comparator<T> comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, comp);
        }
        while (--to > from) {
            T max = array[from];
            siftDown(array, from, array[to], from, to, comp);
            array[to] = max;
        }
    }

    public static <T> void sort(T[] array, Comparator<T> comp) {
        sort(array, 0, array.length, comp);
    }

    private ObjectHeapsort() {}

    public static final ObjectHeapsort INSTANCE = new ObjectHeapsort();

    @Override
    public <T> void iSort(T[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public <T> void iSort(T[] array) {
        sort(array);
    }

    @Override
    public <T> void iSort(T[] array, int from, int to, Comparator<T> comparator) {
        sort(array, from, to, comparator);
    }

    @Override
    public <T> void iSort(T[] array, Comparator<T> comparator) {
        sort(array, comparator);
    }
}
