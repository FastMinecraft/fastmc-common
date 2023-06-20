package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class DoubleInsertionSort implements DoubleSort {
    public static void sort(double[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            double v = array[i1];
            int i2 = i1;
            for (;i2 > from && v < array[i2 - 1]; i2--) {
                array[i2] = array[i2 - 1];
            }
            array[i2] = v;
        }
    }

    public static void sort(double[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(double[] array, int from, int to, DoubleComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            double v1 = array[i1];
            int i2 = i1;
            double v2;
            for (;i2 > from && comp.compare(v1, (v2 = array[i2 - 1])) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(double[] array, DoubleComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private DoubleInsertionSort() {}

    public static final DoubleInsertionSort INSTANCE = new DoubleInsertionSort();

    @Override
    public void iSort(double[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(double[] array) {
        sort(array);
    }

    @Override
    public void iSort(double[] array, int from, int to, DoubleComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(double[] array, DoubleComparator comp) {
        sort(array, comp);
    }
}
