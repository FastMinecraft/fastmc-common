package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class DoubleHeapsort implements DoubleSort {
    private static void siftDown(double[] array, int p, double value, int from, int to) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || array[k] < array[k - 1]) {
                --k;
            }
            if (array[k] <= value) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(double[] array, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to);
        }
        while (--to > from) {
            double max = array[from];
            siftDown(array, from, array[to], from, to);
            array[to] = max;
        }
    }

    public static void sort(double[] array) {
        sort(array, 0, array.length);
    }

    private static void siftDown(double[] array, int p, double value, int from, int to, DoubleComparator comp) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || comp.compare(array[k], array[k - 1]) < 0) {
                --k;
            }
            if (comp.compare(array[k], value) <= 0) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(double[] array, int from, int to, DoubleComparator comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, comp);
        }
        while (--to > from) {
            double max = array[from];
            siftDown(array, from, array[to], from, to, comp);
            array[to] = max;
        }
    }

    public static void sort(double[] array, DoubleComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private DoubleHeapsort() {}

    public static final DoubleHeapsort INSTANCE = new DoubleHeapsort();

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
