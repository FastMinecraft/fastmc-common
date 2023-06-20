package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class LongInsertionSort implements LongSort {
    public static void sort(long[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            long v = array[i1];
            int i2 = i1;
            for (;i2 > from && v < array[i2 - 1]; i2--) {
                array[i2] = array[i2 - 1];
            }
            array[i2] = v;
        }
    }

    public static void sort(long[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(long[] array, int from, int to, LongComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            long v1 = array[i1];
            int i2 = i1;
            long v2;
            for (;i2 > from && comp.compare(v1, (v2 = array[i2 - 1])) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(long[] array, LongComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private LongInsertionSort() {}

    public static final LongInsertionSort INSTANCE = new LongInsertionSort();

    @Override
    public void iSort(long[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(long[] array) {
        sort(array);
    }

    @Override
    public void iSort(long[] array, int from, int to, LongComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(long[] array, LongComparator comp) {
        sort(array, comp);
    }
}
