package dev.fastmc.common.sort;

public interface ByteSort {
    void iSort(byte[] array, int from, int to);

    void iSort(byte[] array);

    void iSort(byte[] array, int from, int to, int[] keys);

    void iSort(byte[] array, int[] keys);

    void iSort(byte[] array, int from, int to, float[] keys);

    void iSort(byte[] array, float[] keys);

    void iSort(byte[] array, int from, int to, ByteComparator comp);

    void iSort(byte[] array, ByteComparator comp);
}
