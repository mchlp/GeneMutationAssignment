/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package old.data_structures;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A wrapper class around ArrayList which stores elements in sorted order to allow for log n search time.
 * The data type of the key of the objects stored in this list must be a String. Implementing classes should define a
 * compare and getKey method for the class that is stored in the list.
 *
 * @param <T> The class to be stored in the BinaryList.
 */
public abstract class BinaryList<T> extends ArrayList<T> {

    boolean sorted;

    /**
     * Adds an element to the binary list and sets the sorted flag to false.
     */
    @Override
    public boolean add(T element) {
        sorted = false;
        return super.add(element);
    }

    /**
     * Sorts the list and sets the sorted flag to true to allow for searching.
     */
    public void sort() {
        this.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return BinaryList.this.compare(o1, o2);
            }
        });
        sorted = true;
    }

    /**
     * Compares two elements in the list by their key value (must be a String)
     *
     * @return The difference between the order of element 1 and element 2. Follows the same specifications as
     * {@link java.util.Comparator#compare(Object, Object)}.
     */
    public int compare(T ele1, T ele2) {
        return getKey(ele1).compareTo(getKey(ele2));
    }

    /**
     * Returns the key of the object. Must be of type String.
     */
    public abstract String getKey(T element);

    /**
     * Determine if an object exists in the list.
     *
     * @param o object to look for.
     * @return <code>true</code> if the object is in the list, <code>false</code> otherwise.
     */
    @Override
    public boolean contains(Object o) {
        T element = (T) o;
        T foundElement = binarySearch(getKey(element));
        return foundElement != null;
    }

    /**
     * Retrieve an object from the list.
     *
     * @param key the key of the object to be retrieved. This must be a String.
     * @return the object, if it is found, <code>null</code> otherwise.
     */
    public T getElement(String key) {
        return binarySearch(key);
    }

    /**
     * Finds an object in the list by its key using binary search. The list must be sorted.
     *
     * @param key key to search by.
     * @return the object with the specified key, or <code>null</code> if not found.
     */
    private T binarySearch(String key) {

        if (!sorted) {
            throw new AssertionError("The list must be sorted before this method can be called.");
        }

        // binary search for element with key
        int start = 0;
        int end = this.size() - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int diff = key.compareTo(getKey(this.get(middle)));
            if (diff > 0) {
                start = middle + 1;
            } else if (diff < 0) {
                end = middle - 1;
            } else {
                return this.get(middle);
            }
        }
        return null;
    }
}
