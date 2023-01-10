package dev.fastmc.common.sort;

@FunctionalInterface
public interface ByteComparator {
    int compare(byte a, byte b);

    ByteComparator NATURAL_ORDER = Byte::compare;
    ByteComparator REVERSE_ORDER = (a, b) -> Byte.compare(b, a);
}
