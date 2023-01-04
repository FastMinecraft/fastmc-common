package dev.fastmc.common.sort;

import java.util.Comparator;

@SuppressWarnings("DuplicatedCode")
public class ObjectHeapsort implements ObjectSort {
    private static <T extends Comparable<T>> void siftDown(T[] a, int p, T value, int from, int to) {
        for (int k; ; a[p] = a[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || a[k].compareTo(a[k - 1]) < 0) {
                --k;
            }
            if (a[k].compareTo(value) <= 0) {
                break;
            }
        }
        a[p] = value;
    }

    public static <T extends Comparable<T>> void sort(T[] a, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(a, --k, a[k], from, to);
        }
        while (--to > from) {
            T max = a[from];
            siftDown(a, from, a[to], from, to);
            a[to] = max;
        }
    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, 0, a.length);
    }

    private static <T> void siftDown(T[] x, int p, T value, int from, int to, Comparator<T> comp) {
        for (int k; ; x[p] = x[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || comp.compare(x[k], x[k - 1]) < 0) {
                --k;
            }
            if (comp.compare(x[k], value) <= 0) {
                break;
            }
        }
        x[p] = value;
    }

    public static <T> void sort(T[] a, int from, int to, Comparator<T> comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(a, --k, a[k], from, to, comp);
        }
        while (--to > from) {
            T max = a[from];
            siftDown(a, from, a[to], from, to, comp);
            a[to] = max;
        }
    }

    public static <T> void sort(T[] a, Comparator<T> comp) {
        sort(a, 0, a.length, comp);
    }

    private ObjectHeapsort() {}

    public static final ObjectHeapsort INSTANCE = new ObjectHeapsort();

    @Override
    public <T extends Comparable<T>> void iSort(T[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public <T extends Comparable<T>> void iSort(T[] array) {
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
