package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class IntInsertionSort implements IntSort {
    public static void sort(int[] a, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            int v = a[i1];
            int i2 = i1;
            for (;i2 > from && v < a[i2 - 1]; i2--) {
                a[i2] = a[i2 - 1];
            }
            a[i2] = v;
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    public static void sort(int[] x, int from, int to, int[] keys) {
        for (int i1 = from; i1 < to; i1++) {
            int v1 = x[i1];
            int d1 = keys[v1];
            int i2 = i1;
            int v2;
            for (;i2 > from && d1 < keys[(v2 = x[i2 - 1])];i2--) {
                x[i2] = v2;
            }
            x[i2] = v1;
        }
    }

    public static void sort(int[] x, int[] keys) {
        sort(x, 0, x.length, keys);
    }

    public static void sort(int[] x, int from, int to, float[] keys) {
        for (int i1 = from; i1 < to; i1++) {
            int v1 = x[i1];
            float d1 = keys[v1];
            int i2 = i1;
            int v2;
            for (;i2 > from && d1 < keys[(v2 = x[i2 - 1])];i2--) {
                x[i2] = v2;
            }
            x[i2] = v1;
        }
    }

    public static void sort(int[] x, float[] keys) {
        sort(x, 0, x.length, keys);
    }

    public static void sort(int[] x, int from, int to, IntComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            int v1 = x[i1];
            int i2 = i1;
            int v2;
            for (;i2 > from && comp.compare(v1, (v2 = x[i2 - 1])) < 0;i2--) {
                x[i2] = v2;
            }
            x[i2] = v1;
        }
    }

    public static void sort(int[] x, IntComparator comp) {
        sort(x, 0, x.length, comp);
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
