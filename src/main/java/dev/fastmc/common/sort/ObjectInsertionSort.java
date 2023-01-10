package dev.fastmc.common.sort;

import java.util.Comparator;

@SuppressWarnings("DuplicatedCode")
public class ObjectInsertionSort implements ObjectSort {
    public static <T extends Comparable<T>> void sort(T[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            T v1 = array[i1];
            int i2 = i1;
            T v2;
            for (;i2 > from && v1.compareTo(v2 = array[i2 - 1]) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static <T extends Comparable<T>> void sort(T[] array) {
        sort(array, 0, array.length);
    }

    public static <T> void sort(T[] array, int from, int to, Comparator<T> comp) {
        for (int i1 = from; i1 < to; i1++) {
            T v1 = array[i1];
            int i2 = i1;
            T v2;
            for (;i2 > from && comp.compare(v1, v2 = array[i2 - 1]) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static <T> void sort(T[] array, Comparator<T> comp) {
        sort(array, 0, array.length, comp);
    }

    private ObjectInsertionSort() {}

    public static final ObjectInsertionSort INSTANCE = new ObjectInsertionSort();

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
