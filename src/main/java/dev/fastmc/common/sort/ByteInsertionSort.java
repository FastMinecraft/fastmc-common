package dev.fastmc.common.sort;

@SuppressWarnings("DuplicatedCode")
public class ByteInsertionSort implements ByteSort {
    public static void sort(byte[] array, int from, int to) {
        for (int i1 = from; i1 < to; i1++) {
            byte v = array[i1];
            int i2 = i1;
            for (;i2 > from && v < array[i2 - 1]; i2--) {
                array[i2] = array[i2 - 1];
            }
            array[i2] = v;
        }
    }

    public static void sort(byte[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(byte[] array, int from, int to, int[] keys) {
        for (int i1 = from; i1 < to; i1++) {
            byte v1 = array[i1];
            int d1 = keys[(int) v1 & 0xFF];
            int i2 = i1;
            byte v2;
            for (;i2 > from && d1 < keys[(int) (v2 = array[i2 - 1]) & 0xFF];i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(byte[] array, int[] keys) {
        sort(array, 0, array.length, keys);
    }

    public static void sort(byte[] array, int from, int to, float[] keys) {
        for (int i1 = from; i1 < to; i1++) {
            byte v1 = array[i1];
            float d1 = keys[(int) v1 & 0xFF];
            int i2 = i1;
            byte v2;
            for (;i2 > from && d1 < keys[(int) (v2 = array[i2 - 1]) & 0xFF];i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(byte[] array, float[] keys) {
        sort(array, 0, array.length, keys);
    }

    public static void sort(byte[] array, int from, int to, ByteComparator comp) {
        for (int i1 = from; i1 < to; i1++) {
            byte v1 = array[i1];
            int i2 = i1;
            byte v2;
            for (;i2 > from && comp.compare(v1, (v2 = array[i2 - 1])) < 0;i2--) {
                array[i2] = v2;
            }
            array[i2] = v1;
        }
    }

    public static void sort(byte[] array, ByteComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private ByteInsertionSort() {}

    public static final ByteInsertionSort INSTANCE = new ByteInsertionSort();

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
