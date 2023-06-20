package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class LongHeapsort implements LongSort {
    private static void siftDown(long[] array, int p, long value, int from, int to) {
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

    public static void sort(long[] array, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to);
        }
        while (--to > from) {
            long max = array[from];
            siftDown(array, from, array[to], from, to);
            array[to] = max;
        }
    }

    public static void sort(long[] array) {
        sort(array, 0, array.length);
    }

    private static void siftDown(long[] array, int p, long value, int from, int to, LongComparator comp) {
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

    public static void sort(long[] array, int from, int to, LongComparator comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, comp);
        }
        while (--to > from) {
            long max = array[from];
            siftDown(array, from, array[to], from, to, comp);
            array[to] = max;
        }
    }

    public static void sort(long[] array, LongComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private LongHeapsort() {}

    public static final LongHeapsort INSTANCE = new LongHeapsort();

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
