package dev.fastmc.common.sort;

@FunctionalInterface
public interface ShortComparator {
    int compare(short a, short b);

    ShortComparator NATURAL_ORDER = Short::compare;
    ShortComparator REVERSE_ORDER = (a, b) -> Short.compare(b, a);
}
