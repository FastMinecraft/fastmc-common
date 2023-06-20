package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class ShortInsertionSort implements ShortSort {
    public static void sort(short[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            short v = array[i1];
            int i2 = i1;
            for (;i2 > from && v < array[i2 - 1]; i2--) {
                array[i2] = array[i2 - 1];
            }
            array[i2] = v;
        }
    }

    public static void sort(short[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(short[] array, int from, int to, ShortComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            short v1 = array[i1];
            int i2 = i1;
            short v2;
            for (;i2 > from && comp.compare(v1, (v2 = array[i2 - 1])) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(short[] array, ShortComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private ShortInsertionSort() {}

    public static final ShortInsertionSort INSTANCE = new ShortInsertionSort();

    @Override
    public void iSort(short[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(short[] array) {
        sort(array);
    }

    @Override
    public void iSort(short[] array, int from, int to, ShortComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(short[] array, ShortComparator comp) {
        sort(array, comp);
    }
}
