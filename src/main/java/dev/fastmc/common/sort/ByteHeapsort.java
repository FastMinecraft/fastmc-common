package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class ByteHeapsort implements ByteSort {
    private static void siftDown(byte[] array, int p, byte value, int from, int to) {
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

    public static void sort(byte[] array, int from, int to) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to);
        }
        while (--to > from) {
            byte max = array[from];
            siftDown(array, from, array[to], from, to);
            array[to] = max;
        }
    }

    public static void sort(byte[] array) {
        sort(array, 0, array.length);
    }

    private static void siftDown(byte[] array, int p, byte value, int from, int to, int[] keys) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || keys[(int) array[k] & 0xFF] < keys[(int) array[k - 1] & 0xFF]) {
                --k;
            }
            if (keys[(int) array[k] & 0xFF] <= keys[(int) value & 0xFF]) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(byte[] array, int from, int to, int[] keys) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, keys);
        }
        while (--to > from) {
            byte max = array[from];
            siftDown(array, from, array[to], from, to, keys);
            array[to] = max;
        }
    }

    public static void sort(byte[] array, int[] keys) {
        sort(array, 0, array.length, keys);
    }

    private static void siftDown(byte[] array, int p, byte value, int from, int to, float[] keys) {
        for (int k; ; array[p] = array[p = k]) {
            k = (p << 1) - from + 2; // Index of the right child

            if (k > to) {
                break;
            }
            if (k == to || keys[(int) array[k] & 0xFF] < keys[(int) array[k - 1] & 0xFF]) {
                --k;
            }
            if (keys[(int) array[k] & 0xFF] <= keys[(int) value & 0xFF]) {
                break;
            }
        }
        array[p] = value;
    }

    public static void sort(byte[] array, int from, int to, float[] keys) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, keys);
        }
        while (--to > from) {
            byte max = array[from];
            siftDown(array, from, array[to], from, to, keys);
            array[to] = max;
        }
    }

    public static void sort(byte[] array, float[] keys) {
        sort(array, 0, array.length, keys);
    }

    private static void siftDown(byte[] array, int p, byte value, int from, int to, ByteComparator comp) {
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

    public static void sort(byte[] array, int from, int to, ByteComparator comp) {
        for (int k = (from + to) >>> 1; k > from; ) {
            siftDown(array, --k, array[k], from, to, comp);
        }
        while (--to > from) {
            byte max = array[from];
            siftDown(array, from, array[to], from, to, comp);
            array[to] = max;
        }
    }

    public static void sort(byte[] array, ByteComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private ByteHeapsort() {}

    public static final ByteHeapsort INSTANCE = new ByteHeapsort();

    @Override
    public void iSort(byte[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public void iSort(byte[] array) {
        sort(array);
    }

    @Override
    public void iSort(byte[] array, int from, int to, int[] keys) {
        sort(array, from, to, keys);
    }

    @Override
    public void iSort(byte[] array, int[] keys) {
        sort(array, keys);
    }

    @Override
    public void iSort(byte[] array, int from, int to, float[] keys) {
        sort(array, from, to, keys);
    }

    @Override
    public void iSort(byte[] array, float[] keys) {
        sort(array, keys);
    }

    @Override
    public void iSort(byte[] array, int from, int to, ByteComparator comp) {
        sort(array, from, to, comp);
    }

    @Override
    public void iSort(byte[] array, ByteComparator comp) {
        sort(array, comp);
    }
}
