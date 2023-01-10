package dev.fastmc.common.sort;

@FunctionalInterface
public interface FloatComparator {
    int compare(float a, float b);

    FloatComparator NATURAL_ORDER = Float::compare;
    FloatComparator REVERSE_ORDER = (a, b) -> Float.compare(b, a);
}
