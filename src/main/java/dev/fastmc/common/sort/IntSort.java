package dev.fastmc.common.sort;

public interface IntSort {
    void iSort(int[] array, int from, int to);

    void iSort(int[] array);

    void iSort(int[] array, int from, int to, int[] keys);

    void iSort(int[] array, int[] keys);

    void iSort(int[] array, int from, int to, float[] keys);

    void iSort(int[] array, float[] keys);

    void iSort(int[] array, int from, int to, IntComparator comp);

    void iSort(int[] array, IntComparator comp);
}
