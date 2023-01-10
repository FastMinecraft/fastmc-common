package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class FloatInsertionSort implements FloatSort {
    public static void sort(float[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            float v = array[i1];
            int i2 = i1;
            for (;i2 > from && v < array[i2 - 1]; i2--) {
                array[i2] = array[i2 - 1];
            }
            array[i2] = v;
        }
    }

    public static void sort(float[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(float[] array, int from, int to, FloatComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            float v1 = array[i1];
            int i2 = i1;
            float v2;
            for (;i2 > from && comp.compare(v1, (v2 = array[i2 - 1])) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(float[] array, FloatComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private FloatInsertionSort() {}

    public static final FloatInsertionSort INSTANCE = new FloatInsertionSort();

    @Override
    public void iSort(float[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(float[] array) {
        sort(array);
    }

    @Override
    public void iSort(float[] array, int from, int to, FloatComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(float[] array, FloatComparator comp) {
        sort(array, comp);
    }
}
