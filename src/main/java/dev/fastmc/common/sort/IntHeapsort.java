package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class IntHeapsort implements IntSort {
    private static void siftDown(int[] a, int p, int value, int from, int to) {
        for (int k; ; a[p] = a[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || a[k] < a[k - 1]) {
                --k;
            }
            if (a[k] <= value) {
                break;
            }
        }
        a[p] = value;
    }

    public static void sort(int[] a, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(a, --k, a[k], from, to);
        }
        while (--to > from) {
            int max = a[from];
            siftDown(a, from, a[to], from, to);
            a[to] = max;
        }
    }

    public static void sort(int[] a) {
        sort(a, 0, a.length);
    }

    private static void siftDown(int[] x, int p, int value, int from, int to, int[] keys) {
        for (int k; ; x[p] = x[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || keys[x[k]] < keys[x[k - 1]]) {
                --k;
            }
            if (keys[x[k]] <= keys[value]) {
                break;
            }
        }
        x[p] = value;
    }

    public static void sort(int[] a, int from, int to, int[] keys) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(a, --k, a[k], from, to, keys);
        }
        while (--to > from) {
            int max = a[from];
            siftDown(a, from, a[to], from, to, keys);
            a[to] = max;
        }
    }

    public static void sort(int[] a, int[] keys) {
        sort(a, 0, a.length, keys);
    }

    private static void siftDown(int[] x, int p, int value, int from, int to, float[] keys) {
        for (int k; ; x[p] = x[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || keys[x[k]] < keys[x[k - 1]]) {
                --k;
            }
            if (keys[x[k]] <= keys[value]) {
                break;
            }
        }
        x[p] = value;
    }

    public static void sort(int[] a, int from, int to, float[] keys) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(a, --k, a[k], from, to, keys);
        }
        while (--to > from) {
            int max = a[from];
            siftDown(a, from, a[to], from, to, keys);
            a[to] = max;
        }
    }

    public static void sort(int[] a, float[] keys) {
        sort(a, 0, a.length, keys);
    }

    private static void siftDown(int[] x, int p, int value, int from, int to, IntComparator comp) {
        for (int k; ; x[p] = x[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || comp.compare(x[k], x[k - 1]) < 0) {
                --k;
            }
            if (comp.compare(x[k], value) <= 0) {
                break;
            }
        }
        x[p] = value;
    }

    public static void sort(int[] a, int from, int to, IntComparator comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(a, --k, a[k], from, to, comp);
        }
        while (--to > from) {
            int max = a[from];
            siftDown(a, from, a[to], from, to, comp);
            a[to] = max;
        }
    }

    public static void sort(int[] a, IntComparator comp) {
        sort(a, 0, a.length, comp);
    }

    private IntHeapsort() {}

    public static final IntHeapsort INSTANCE = new IntHeapsort();

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
