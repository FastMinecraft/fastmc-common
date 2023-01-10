package dev.fastmc.common.sort;

public interface FloatSort {
    void iSort(float[] array, int from, int to);

    void iSort(float[] array);

    void iSort(float[] array, int from, int to, FloatComparator comp);

    void iSort(float[] array, FloatComparator comp);
}
