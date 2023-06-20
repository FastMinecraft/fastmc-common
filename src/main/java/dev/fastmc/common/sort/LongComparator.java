package dev.fastmc.common.sort;

@FunctionalInterface
public interface LongComparator {
    int compare(long a, long b);

    LongComparator NATURAL_ORDER = Long::compare;
    LongComparator REVERSE_ORDER = (a, b) -> Long.compare(b, a);
}
