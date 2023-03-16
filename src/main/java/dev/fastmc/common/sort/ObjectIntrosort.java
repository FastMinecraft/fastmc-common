package dev.fastmc.common.sort;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class ObjectIntrosort implements ObjectSort {
    private static final int INSERTION_SORT_SIZE = 64;

    private static <T> void sort(T[] array, int from, int to, int depth) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            ObjectInsertionSort.sort(array, from, to);
            return;
        }

        if (depth == 0) {
            ObjectInsertionSort.sort(array, from, to);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        T s3v = array[s3];

        if (((Comparable<T>) array[s5]).compareTo(array[s2]) < 0) {
            Utils.swap(array, s5, s2);
        }
        if (((Comparable<T>) array[s4]).compareTo(array[s1]) < 0) {
            Utils.swap(array, s4, s1);
        }
        if (((Comparable<T>) array[s5]).compareTo(array[s4]) < 0) {
            Utils.swap(array, s5, s4);
        }
        if (((Comparable<T>) array[s2]).compareTo(array[s1]) < 0) {
            Utils.swap(array, s2, s1);
        }
        if (((Comparable<T>) array[s4]).compareTo(array[s2]) < 0) {
            Utils.swap(array, s4, s2);
        }

        if (((Comparable<T>) s3v).compareTo(array[s2]) < 0) {
            if (((Comparable<T>) s3v).compareTo(array[s1]) < 0) {
                array[s3] = array[s2];
                array[s2] = array[s1];
                array[s1] = s3v;
            } else {
                array[s3] = array[s2];
                array[s2] = s3v;
            }
        } else if (((Comparable<T>) s3v).compareTo(array[s4]) > 0) {
            if (((Comparable<T>) s3v).compareTo(array[s5]) > 0) {
                array[s3] = array[s4];
                array[s4] = array[s5];
                array[s5] = s3v;
            } else {
                array[s3] = array[s4];
                array[s4] = s3v;
            }
        }

        if (((Comparable<T>) array[s1]).compareTo(array[s2]) < 0 && ((Comparable<T>) array[s2]).compareTo(array[s3]) < 0 && ((Comparable<T>) array[s3]).compareTo(array[s4]) < 0 && ((Comparable<T>) array[s4]).compareTo(array[s5]) < 0) {
            Utils.swap(array, from, s1);
            Utils.swap(array, last, s5);
            T p = array[from];
            T q = array[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                T m = array[mid];
                if (((Comparable<T>) m).compareTo(q) > 0) {
                    T r;
                    do {
                        r = array[--right];
                    } while (((Comparable<T>) r).compareTo(q) > 0 && right > mid);
                    if (((Comparable<T>)  r).compareTo(p) < 0) {
                        array[mid] = array[++left];
                        array[left] = r;
                    } else {
                        array[mid] = r;
                    }
                    array[right] = m;
                } else if (((Comparable<T>)  m).compareTo(p) < 0) {
                    array[mid] = array[++left];
                    array[left] = m;
                }
            }

            array[from] = array[left];
            array[left] = p;
            array[last] = array[right];
            array[right] = q;

            sort(array, from, left, depth - 1);
            sort(array, left + 1, right, depth - 1);
            sort(array, right + 1, to, depth - 1);
        } else {
            Utils.swap(array, from, s3);

            T p = array[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                T l = array[left];
                if (((Comparable<T>) l).compareTo(p) > 0) {
                    T r;
                    do {
                        r = array[--right];
                    } while (((Comparable<T>) r).compareTo(p) >= 0 && right > left);
                    array[left] = r;
                    array[right] = l;
                }
            }

            array[from] = array[--right];
            array[right] = p;

            sort(array, from, right, depth - 1);
            sort(array, right + 1, to, depth - 1);
        }
    }

    public static <T> void sort(T[] array, int from, int to) {
        int n = to - from;
        int depth = (int) (Math.log(n) / Math.log(2)) * 2;
        sort(array, from, to, depth);
    }

    public static <T> void sort(T[] array) {
        sort(array, 0, array.length);
    }

    private static <T> void sort(T[] array, int from, int to, int depth, Comparator<T> comp) {
        int n = to - from;

        if (n < INSERTION_SORT_SIZE) {
            ObjectInsertionSort.sort(array, from, to, comp);
            return;
        }

        if (depth == 0) {
            ObjectInsertionSort.sort(array, from, to, comp);
            return;
        }

        int step = (n >> 3) * 3 + 3;

        int last = to - 1;
        int s1 = from + step;
        int s5 = last - step;
        int s3 = (s1 + s5) >>> 1;
        int s2 = (s1 + s3) >>> 1;
        int s4 = (s3 + s5) >>> 1;

        T s3v = array[s3];

        if (comp.compare(array[s5], array[s2]) < 0) {
            Utils.swap(array, s5, s2);
        }
        if (comp.compare(array[s4], array[s1]) < 0) {
            Utils.swap(array, s4, s1);
        }
        if (comp.compare(array[s5], array[s4]) < 0) {
            Utils.swap(array, s5, s4);
        }
        if (comp.compare(array[s2], array[s1]) < 0) {
            Utils.swap(array, s2, s1);
        }
        if (comp.compare(array[s4], array[s2]) < 0) {
            Utils.swap(array, s4, s2);
        }

        if (comp.compare(s3v, array[s2]) < 0) {
            if (comp.compare(s3v, array[s1]) < 0) {
                array[s3] = array[s2];
                array[s2] = array[s1];
                array[s1] = s3v;
            } else {
                array[s3] = array[s2];
                array[s2] = s3v;
            }
        } else if (comp.compare(s3v, array[s4]) > 0) {
            if (comp.compare(s3v, array[s5]) > 0) {
                array[s3] = array[s4];
                array[s4] = array[s5];
                array[s5] = s3v;
            } else {
                array[s3] = array[s4];
                array[s4] = s3v;
            }
        }

        if (comp.compare(array[s1], array[s2]) < 0 && comp.compare(array[s2], array[s3]) < 0 && comp.compare(array[s3], array[s4]) < 0 && comp.compare(array[s4], array[s5]) < 0) {
            Utils.swap(array, from, s1);
            Utils.swap(array, last, s5);
            T p = array[from];
            T q = array[last];

            int left = from;
            int right = to - 1;

            for (int mid = left; mid < right; mid++) {
                T m = array[mid];
                if (comp.compare(m, q) > 0) {
                    T r;
                    do {
                        r = array[--right];
                    } while (comp.compare(r, q) > 0 && right > mid);
                    if (comp.compare(r, p) < 0) {
                        array[mid] = array[++left];
                        array[left] = r;
                    } else {
                        array[mid] = r;
                    }
                    array[right] = m;
                } else if (comp.compare(m, p) < 0) {
                    array[mid] = array[++left];
                    array[left] = m;
                }
            }

            array[from] = array[left];
            array[left] = p;
            array[last] = array[right];
            array[right] = q;

            sort(array, from, left, depth - 1, comp);
            sort(array, left + 1, right, depth - 1, comp);
            sort(array, right + 1, to, depth - 1, comp);
        } else {
            Utils.swap(array, from, s3);

            T p = array[from];
            int right = to;

            for (int left = from + 1; left < right; left++) {
                T l = array[left];
                if (comp.compare(l, p) > 0) {
                    T r;
                    do {
                        r = array[--right];
                    } while (comp.compare(r, p) >= 0 && right > left);
                    array[left] = r;
                    array[right] = l;
                }
            }

            array[from] = array[--right];
            array[right] = p;

            sort(array, from, right, depth - 1, comp);
            sort(array, right + 1, to, depth - 1, comp);
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
    public <T> void iSort(T[] array, int from, int to) {
        sort(array, from, to);
    }

    @Override
    public <T> void iSort(T[] array) {
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
