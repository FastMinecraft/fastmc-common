package dev.fastmc.common.sort;

import java.util.Comparator;

public class ObjectIntrosort implements ObjectSort {
    private static final int INSERTION_SORT_SIZE = 64;

    private static <T extends Comparable<T>> void sort(T[] x, int from, int to, int depth) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            ObjectInsertionSort.sort(x, from, to);
            return;
        }

        if (depth == 0) {
            ObjectInsertionSort.sort(x, from, to);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        T s3v = x[s3];

        if (x[s5].compareTo(x[s2]) < 0) {
            Utils.swap(x, s5, s2);
        }
        if (x[s4].compareTo(x[s1]) < 0) {
            Utils.swap(x, s4, s1);
        }
        if (x[s5].compareTo(x[s4]) < 0) {
            Utils.swap(x, s5, s4);
        }
        if (x[s2].compareTo(x[s1]) < 0) {
            Utils.swap(x, s2, s1);
        }
        if (x[s4].compareTo(x[s2]) < 0) {
            Utils.swap(x, s4, s2);
        }

        if (s3v.compareTo(x[s2]) < 0) {
            if (s3v.compareTo(x[s1]) < 0) {
                x[s3] = x[s2];
                x[s2] = x[s1];
                x[s1] = s3v;
            } else {
                x[s3] = x[s2];
                x[s2] = s3v;
            }
        } else if (s3v.compareTo(x[s4]) > 0) {
            if (s3v.compareTo(x[s5]) > 0) {
                x[s3] = x[s4];
                x[s4] = x[s5];
                x[s5] = s3v;
            } else {
                x[s3] = x[s4];
                x[s4] = s3v;
            }
        }

        if (x[s1].compareTo(x[s2]) < 0 && x[s2].compareTo(x[s3]) < 0 && x[s3].compareTo(x[s4]) < 0 && x[s4].compareTo(x[s5]) < 0) {
            Utils.swap(x, from, s1);
            Utils.swap(x, last, s5);
            T p = x[from];
            T q = x[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                T m = x[mid];
                if (m.compareTo(q) > 0) {
                    T r;
                    do {
                        r = x[--right];
                    } while (r.compareTo(q) > 0 && right > mid);
                    if (r.compareTo(p) < 0) {
                        x[mid] = x[++left];
                        x[left] = r;
                    } else {
                        x[mid] = r;
                    }
                    x[right] = m;
                } else if (m.compareTo(p) < 0) {
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

            T p = x[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                T l = x[left];
                if (l.compareTo(p) > 0) {
                    T r;
                    do {
                        r = x[--right];
                    } while (r.compareTo(p) >= 0 && right > left);
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

    public static <T extends Comparable<T>> void sort(T[] array, int from, int to) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth);
    }

    public static <T extends Comparable<T>> void sort(T[] array) {
        sort(array, 0, array.length);
    }

    private static <T> void sort(T[] x, int from, int to, int depth, Comparator<T> comp) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            ObjectInsertionSort.sort(x, from, to, comp);
            return;
        }

        if (depth == 0) {
            ObjectInsertionSort.sort(x, from, to, comp);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        T s3v = x[s3];

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

        if (comp.compare(x[s1], x[s2]) < 0 && comp.compare(x[s2], x[s3]) < 0 && comp.compare(x[s3], x[s4]) < 0 && comp.compare(x[s4], x[s5]) < 0) {
            Utils.swap(x, from, s1);
            Utils.swap(x, last, s5);
            T p = x[from];
            T q = x[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                T m = x[mid];
                if (comp.compare(m, q) > 0) {
                    T r;
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

            T p = x[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                T l = x[left];
                if (comp.compare(l, p) > 0) {
                    T r;
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
    
    public static <T> void sort(T[] array, int from, int to, Comparator<T> comp) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth, comp);
    }
    
    public static <T> void sort(T[] array, Comparator<T> comp) {
        sort(array, 0, array.length, comp);
    }

    private ObjectIntrosort() {}

    public static final ObjectIntrosort INSTANCE = new ObjectIntrosort();

    @Override
    public <T extends Comparable<T>> void iSort(T[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public <T extends Comparable<T>> void iSort(T[] array) {
        sort(array);
    }

    @Override
    public <T> void iSort(T[] array, int from, int to, Comparator<T> comparator) {
        sort(array, from, to, comparator);
    }

    @Override
    public <T> void iSort(T[] array, Comparator<T> comparator) {
        sort(array, comparator);
    }
}
