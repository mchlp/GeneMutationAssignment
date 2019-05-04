/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

/**
 * Represents a graph as an adjacency list with the key as the String value of the gene and the value as a list of
 * connected genes. It should be sorted before searching to allow for log n search time using binary search.
 */
public class AdjList extends BinaryList<AdjListElement> {

    @Override
    public int compare(AdjListElement ele1, AdjListElement ele2) {
        return ele1.key.compareTo(ele2.key);
    }

    public AdjListElement getElement(String key) {

        if (!sorted) {
            throw new AssertionError("The list must be sorted before this method can be called.");
        }

        // Binary search for element with key
        int start = 0;
        int end = this.size() - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int diff = key.compareTo(this.get(middle).key);
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
