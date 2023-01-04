package dev.fastmc.common.sort;

class Utils {
    static void swap(int[] a, int i1, int i2) {
        int t = a[i1];
        a[i1] = a[i2];
        a[i2] = t;
    }

    static void swap(Object[] a, int i1, int i2) {
        Object t = a[i1];
        a[i1] = a[i2];
        a[i2] = t;
    }
}
