package dev.fastmc.common.sort;

import java.util.Comparator;

public interface ObjectSort {
    <T extends Comparable<T>> void iSort(T[] array, int from, int to);

    <T extends Comparable<T>> void iSort(T[] array);

    <T> void iSort(T[] array, int from, int to, Comparator<T> comparator);

    <T> void iSort(T[] array, Comparator<T> comparator);
}
