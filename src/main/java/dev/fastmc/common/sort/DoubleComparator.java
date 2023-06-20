package dev.fastmc.common.sort;

@FunctionalInterface
public interface DoubleComparator {
    int compare(double a, double b);

    DoubleComparator NATURAL_ORDER = Double::compare;
    DoubleComparator REVERSE_ORDER = (a, b) -> Double.compare(b, a);
}
