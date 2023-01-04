package dev.fastmc.common.sort;

public class IntIntrosort implements IntSort {
    private static final int INSERTION_SORT_SIZE = 64;

    private static void sort(int[] x, int from, int to, int depth) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            IntInsertionSort.sort(x, from, to);
            return;
        }

        if (depth == 0) {
            IntHeapsort.sort(x, from, to);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        int s3v = x[s3];

        if (x[s5] < x[s2]) {
            Utils.swap(x, s5, s2);
        }
        if (x[s4] < x[s1]) {
            Utils.swap(x, s4, s1);
        }
        if (x[s5] < x[s4]) {
            Utils.swap(x, s5, s4);
        }
        if (x[s2] < x[s1]) {
            Utils.swap(x, s2, s1);
        }
        if (x[s4] < x[s2]) {
            Utils.swap(x, s4, s2);
        }

        if (s3v < x[s2]) {
            if (s3v < x[s1]) {
                x[s3] = x[s2];
                x[s2] = x[s1];
                x[s1] = s3v;
            } else {
                x[s3] = x[s2];
                x[s2] = s3v;
            }
        } else if (s3v > x[s4]) {
            if (s3v > x[s5]) {
                x[s3] = x[s4];
                x[s4] = x[s5];
                x[s5] = s3v;
            } else {
                x[s3] = x[s4];
                x[s4] = s3v;
            }
        }

        if (x[s1] < x[s2] && x[s2] < x[s3] && x[s3] < x[s4] && x[s4] < x[s5]) {
            Utils.swap(x, from, s1);
            Utils.swap(x, last, s5);
            int p = x[from];
            int q = x[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                int m = x[mid];
                if (m > q) {
                    int r;
                    do {
                        r = x[--right];
                    } while (r > q && right > mid);
                    if (r < p) {
                        x[mid] = x[++left];
                        x[left] = r;
                    } else {
                        x[mid] = r;
                    }
                    x[right] = m;
                } else if (m < p) {
                    x[mid] = x[++left];
                    x[left] = m;
                }
            }

            x[from] = x[left];
            x[left] = p;
            x[last] = x[right];
            x[right] = q;

            sort(x, from, left, depth - 1);
            sort(x, left + 1, right, depth - 1);
            sort(x, right + 1, to, depth - 1);
        } else {
            Utils.swap(x, from, s3);

            int p = x[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                int l = x[left];
                if (l > p) {
                    int r;
                    do {
                        r = x[--right];
                    } while (r >= p && right > left);
                    x[left] = r;
                    x[right] = l;
                }
            }

            x[from] = x[--right];
            x[right] = p;

            sort(x, from, right, depth - 1);
            sort(x, right + 1, to, depth - 1);
        }
    }

    public static void sort(int[] array, int from, int to) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth);
    }

    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    private static void sort(int[] x, int from, int to, int depth, int[] keys) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            IntInsertionSort.sort(x, from, to, keys);
            return;
        }

        if (depth == 0) {
            IntHeapsort.sort(x, from, to, keys);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        int s3v = x[s3];
        int s3d = keys[s3v];

        if (keys[x[s5]] < keys[x[s2]]) {
            Utils.swap(x, s5, s2);
        }
        if (keys[x[s4]] < keys[x[s1]]) {
            Utils.swap(x, s4, s1);
        }
        if (keys[x[s5]] < keys[x[s4]]) {
            Utils.swap(x, s5, s4);
        }
        if (keys[x[s2]] < keys[x[s1]]) {
            Utils.swap(x, s2, s1);
        }
        if (keys[x[s4]] < keys[x[s2]]) {
            Utils.swap(x, s4, s2);
        }

        if (s3d < keys[x[s2]]) {
            if (s3d < keys[x[s1]]) {
                x[s3] = x[s2];
                x[s2] = x[s1];
                x[s1] = s3v;
            } else {
                x[s3] = x[s2];
                x[s2] = s3v;
            }
        } else if (s3d > keys[x[s4]]) {
            if (s3d > keys[x[s5]]) {
                x[s3] = x[s4];
                x[s4] = x[s5];
                x[s5] = s3v;
            } else {
                x[s3] = x[s4];
                x[s4] = s3v;
            }
        }

        if (keys[x[s1]] < keys[x[s2]] && keys[x[s2]] < keys[x[s3]] && keys[x[s3]] < keys[x[s4]] && keys[x[s4]] < keys[x[s5]]) {
            Utils.swap(x, from, s1);
            Utils.swap(x, last, s5);
            int p = x[from];
            int pd = keys[p];
            int q = x[last];
            int qd = keys[q];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                int m = x[mid];
                int md = keys[m];
                if (md > qd) {
                    int r;
                    int rd;
                    do {
                        r = x[--right];
                        rd = keys[r];
                    } while (rd > qd && right > mid);
                    if (rd < pd) {
                        x[mid] = x[++left];
                        x[left] = r;
                    } else {
                        x[mid] = r;
                    }
                    x[right] = m;
                } else if (md < pd) {
                    x[mid] = x[++left];
                    x[left] = m;
                }
            }

            x[from] = x[left];
            x[left] = p;
            x[last] = x[right];
            x[right] = q;

            sort(x, from, left, depth - 1, keys);
            sort(x, left + 1, right, depth - 1, keys);
            sort(x, right + 1, to, depth - 1, keys);
        } else {
            Utils.swap(x, from, s3);

            int p = x[from];
            int pd = keys[p];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                int l = x[left];
                int ld = keys[l];
                if (ld > pd) {
                    int r;
                    int rd;
                    do {
                        r = x[--right];
                        rd = keys[r];
                    } while (rd >= pd && right > left);
                    x[left] = r;
                    x[right] = l;
                }
            }

            x[from] = x[--right];
            x[right] = p;

            sort(x, from, right, depth - 1, keys);
            sort(x, right + 1, to, depth - 1, keys);
        }
    }

    public static void sort(int[] array, int from, int to, int[] keys) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth, keys);
    }

    public static void sort(int[] array, int[] keys) {
        sort(array, 0, array.length, keys);
    }

    private static void sort(int[] x, int from, int to, int depth, float[] keys) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            IntInsertionSort.sort(x, from, to, keys);
            return;
        }

        if (depth == 0) {
            IntHeapsort.sort(x, from, to, keys);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        int s3v = x[s3];
        float s3d = keys[s3v];

        if (keys[x[s5]] < keys[x[s2]]) {
            Utils.swap(x, s5, s2);
        }
        if (keys[x[s4]] < keys[x[s1]]) {
            Utils.swap(x, s4, s1);
        }
        if (keys[x[s5]] < keys[x[s4]]) {
            Utils.swap(x, s5, s4);
        }
        if (keys[x[s2]] < keys[x[s1]]) {
            Utils.swap(x, s2, s1);
        }
        if (keys[x[s4]] < keys[x[s2]]) {
            Utils.swap(x, s4, s2);
        }

        if (s3d < keys[x[s2]]) {
            if (s3d < keys[x[s1]]) {
                x[s3] = x[s2];
                x[s2] = x[s1];
                x[s1] = s3v;
            } else {
                x[s3] = x[s2];
                x[s2] = s3v;
            }
        } else if (s3d > keys[x[s4]]) {
            if (s3d > keys[x[s5]]) {
                x[s3] = x[s4];
                x[s4] = x[s5];
                x[s5] = s3v;
            } else {
                x[s3] = x[s4];
                x[s4] = s3v;
            }
        }

        if (keys[x[s1]] < keys[x[s2]] && keys[x[s2]] < keys[x[s3]] && keys[x[s3]] < keys[x[s4]] && keys[x[s4]] < keys[x[s5]]) {
            Utils.swap(x, from, s1);
            Utils.swap(x, last, s5);
            int p = x[from];
            float pd = keys[p];
            int q = x[last];
            float qd = keys[q];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                int m = x[mid];
                float md = keys[m];
                if (md > qd) {
                    int r;
                    float rd;
                    do {
                        r = x[--right];
                        rd = keys[r];
                    } while (rd > qd && right > mid);
                    if (rd < pd) {
                        x[mid] = x[++left];
                        x[left] = r;
                    } else {
                        x[mid] = r;
                    }
                    x[right] = m;
                } else if (md < pd) {
                    x[mid] = x[++left];
                    x[left] = m;
                }
            }

            x[from] = x[left];
            x[left] = p;
            x[last] = x[right];
            x[right] = q;

            sort(x, from, left, depth - 1, keys);
            sort(x, left + 1, right, depth - 1, keys);
            sort(x, right + 1, to, depth - 1, keys);
        } else {
            Utils.swap(x, from, s3);

            int p = x[from];
            float pd = keys[p];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                int l = x[left];
                float ld = keys[l];
                if (ld > pd) {
                    int r;
                    float rd;
                    do {
                        r = x[--right];
                        rd = keys[r];
                    } while (rd >= pd && right > left);
                    x[left] = r;
                    x[right] = l;
                }
            }

            x[from] = x[--right];
            x[right] = p;

            sort(x, from, right, depth - 1, keys);
            sort(x, right + 1, to, depth - 1, keys);
        }
    }

    public static void sort(int[] array, int from, int to, float[] keys) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth, keys);
    }

    public static void sort(int[] array, float[] keys) {
        sort(array, 0, array.length, keys);
    }

    private static void sort(int[] x, int from, int to, int depth, IntComparator comp) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            IntInsertionSort.sort(x, from, to, comp);
            return;
        }

        if (depth == 0) {
            IntHeapsort.sort(x, from, to, comp);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        int s3v = x[s3];

        if (comp.compare(x[s5], x[s2]) < 0) {
            Utils.swap(x, s5, s2);
        }
        if (comp.compare(x[s4], x[s1]) < 0) {
            Utils.swap(x, s4, s1);
        }
        if (comp.compare(x[s5], x[s4]) < 0) {
            Utils.swap(x, s5, s4);
        }
        if (comp.compare(x[s2], x[s1]) < 0) {
            Utils.swap(x, s2, s1);
        }
        if (comp.compare(x[s4], x[s2]) < 0) {
            Utils.swap(x, s4, s2);
        }

        if (comp.compare(s3v, x[s2]) < 0) {
            if (comp.compare(s3v, x[s1]) < 0) {
                x[s3] = x[s2];
                x[s2] = x[s1];
                x[s1] = s3v;
            } else {
                x[s3] = x[s2];
                x[s2] = s3v;
            }
        } else if (comp.compare(s3v, x[s4]) > 0) {
            if (comp.compare(s3v, x[s5]) > 0) {
                x[s3] = x[s4];
                x[s4] = x[s5];
                x[s5] = s3v;
            } else {
                x[s3] = x[s4];
                x[s4] = s3v;
            }
        }

        if (comp.compare(x[s1], x[s2]) < 0 && comp.compare(x[s2], x[s3]) < 0 && comp.compare(
            x[s3],
            x[s4]
        ) < 0 && comp.compare(x[s4], x[s5]) < 0) {
            Utils.swap(x, from, s1);
            Utils.swap(x, last, s5);
            int p = x[from];
            int q = x[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                int m = x[mid];
                if (comp.compare(m, q) > 0) {
                    int r;
                    do {
                        r = x[--right];
                    } while (comp.compare(r, q) > 0 && right > mid);
                    if (comp.compare(r, p) < 0) {
                        x[mid] = x[++left];
                        x[left] = r;
                    } else {
                        x[mid] = r;
                    }
                    x[right] = m;
                } else if (comp.compare(m, p) < 0) {
                    x[mid] = x[++left];
                    x[left] = m;
                }
            }

            x[from] = x[left];
            x[left] = p;
            x[last] = x[right];
            x[right] = q;

            sort(x, from, left, depth - 1, comp);
            sort(x, left + 1, right, depth - 1, comp);
            sort(x, right + 1, to, depth - 1, comp);
        } else {
            Utils.swap(x, from, s3);

            int p = x[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                int l = x[left];
                if (comp.compare(l, p) > 0) {
                    int r;
                    do {
                        r = x[--right];
                    } while (comp.compare(r, p) >= 0 && right > left);
                    x[left] = r;
                    x[right] = l;
                }
            }

            x[from] = x[--right];
            x[right] = p;

            sort(x, from, right, depth - 1, comp);
            sort(x, right + 1, to, depth - 1, comp);
        }
    }

    public static void sort(int[] array, int from, int to, IntComparator comp) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth, comp);
    }

    public static void sort(int[] array, IntComparator comp) {
        sort(array, 0, array.length, comp);
    }

    private IntIntrosort() {}

    public static final IntIntrosort INSTANCE = new IntIntrosort();

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
