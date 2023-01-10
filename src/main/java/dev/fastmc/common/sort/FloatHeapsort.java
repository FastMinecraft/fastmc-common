package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class FloatHeapsort implements FloatSort {
    private static void siftDown(float[] array, int p, float value, int from, int to) {
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

    public static void sort(float[] array, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to);
        }
        while (--to > from) {
            float max = array[from];
            siftDown(array, from, array[to], from, to);
            array[to] = max;
        }
    }

    public static void sort(float[] array) {
        sort(array, 0, array.length);
    }

    private static void siftDown(float[] array, int p, float value, int from, int to, FloatComparator comp) {
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

    public static void sort(float[] array, int from, int to, FloatComparator comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, comp);
        }
        while (--to > from) {
            float max = array[from];
            siftDown(array, from, array[to], from, to, comp);
            array[to] = max;
        }
    }

    public static void sort(float[] array, FloatComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private FloatHeapsort() {}

    public static final FloatHeapsort INSTANCE = new FloatHeapsort();

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
