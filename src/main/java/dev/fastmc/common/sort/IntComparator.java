package dev.fastmc.common.sort;

@FunctionalInterface
public interface IntComparator {
    int compare(int a, int b);

    IntComparator NATURAL_ORDER = Integer::compare;
    IntComparator REVERSE_ORDER = (a, b) -> Integer.compare(b, a);
}
