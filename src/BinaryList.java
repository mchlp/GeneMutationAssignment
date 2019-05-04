/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

import java.util.ArrayList;

/**
 * A wrapper class around ArrayList which stores elements in sorted order to allow for log n search time.
 *
 * @param <T> The class to be stored in the BinaryList.
 */
public abstract class BinaryList<T> extends ArrayList<T> {

    boolean sorted;

    @Override
    public boolean add(T element) {
        sorted = false;
        return super.add(element);
    }

    public void sort() {
        this.sort(this::compare);
        sorted = true;
    }

    /**
     * To be implemented by extending classes.
     *
     * @param ele1 Element 1
     * @param ele2 Element 2
     * @return The difference between the order of element 1 and element 2. Follows the same specifications as
     * {@link java.util.Comparator#compare(Object, Object)}
     */
    public abstract int compare(T ele1, T ele2);

    @Override
    public boolean contains(Object o) {

        if (!sorted) {
            throw new AssertionError("The list must be sorted before this method can be called.");
        }

        Comparable<T> element = (Comparable<T>) o;

        // Binary search for matching element in sorted list
        int start = 0;
        int end = this.size() - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int diff = element.compareTo(this.get(middle));
            if (diff > 0) {
                start = middle + 1;
            } else if (diff < 0) {
                end = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
