package dev.fastmc.common.sort;

public interface DoubleSort {
    void iSort(double[] array, int from, int to);

    void iSort(double[] array);

    void iSort(double[] array, int from, int to, DoubleComparator comp);

    void iSort(double[] array, DoubleComparator comp);
}
