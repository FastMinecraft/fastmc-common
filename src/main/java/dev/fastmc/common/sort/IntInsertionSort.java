package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class IntInsertionSort implements IntSort {
    public static void sort(int[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            int v = array[i1];
            int i2 = i1;
            for (;i2 > from && v < array[i2 - 1]; i2--) {
                array[i2] = array[i2 - 1];
            }
            array[i2] = v;
        }
    }

    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(int[] array, int from, int to, int[] keys) {
        for (int i1 = from; i1 < to; i1++) {
            int v1 = array[i1];
            int d1 = keys[v1];
            int i2 = i1;
            int v2;
            for (;i2 > from && d1 < keys[(v2 = array[i2 - 1])];i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(int[] array, int[] keys) {
        sort(array, 0, array.length, keys);
    }

    public static void sort(int[] array, int from, int to, float[] keys) {
        for (int i1 = from; i1 < to; i1++) {
            int v1 = array[i1];
            float d1 = keys[v1];
            int i2 = i1;
            int v2;
            for (;i2 > from && d1 < keys[(v2 = array[i2 - 1])];i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(int[] array, float[] keys) {
        sort(array, 0, array.length, keys);
    }

    public static void sort(int[] array, int from, int to, IntComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            int v1 = array[i1];
            int i2 = i1;
            int v2;
            for (;i2 > from && comp.compare(v1, (v2 = array[i2 - 1])) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(int[] array, IntComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private IntInsertionSort() {}

    public static final IntInsertionSort INSTANCE = new IntInsertionSort();

    @Override
    public void iSort(int[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(int[] array) {
        sort(array);
    }

    @Override
    public void iSort(int[] array, int from, int to, int[] keys) {
        sort(array, from, to, keys);
    }

    @Override
    public void iSort(int[] array, int[] keys) {
        sort(array, keys);
    }

    @Override
    public void iSort(int[] array, int from, int to, float[] keys) {
        sort(array, from, to, keys);
    }

    @Override
    public void iSort(int[] array, float[] keys) {
        sort(array, keys);
    }

    @Override
    public void iSort(int[] array, int from, int to, IntComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(int[] array, IntComparator comp) {
        sort(array, comp);
    }
}
