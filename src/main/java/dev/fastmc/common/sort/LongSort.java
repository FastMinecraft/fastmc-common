package dev.fastmc.common.sort;

public interface LongSort {
    void iSort(long[] array, int from, int to);

    void iSort(long[] array);

    void iSort(long[] array, int from, int to, LongComparator comp);

    void iSort(long[] array, LongComparator comp);
}
