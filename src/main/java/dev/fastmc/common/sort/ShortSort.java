package dev.fastmc.common.sort;

public interface ShortSort {
    void iSort(short[] array, int from, int to);

    void iSort(short[] array);

    void iSort(short[] array, int from, int to, ShortComparator comp);

    void iSort(short[] array, ShortComparator comp);
}
