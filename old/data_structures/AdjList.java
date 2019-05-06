/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package old.data_structures;

/**
 * Represents a graph as an adjacency list with the key as the String value of the gene and the value as a list of
 * connected genes. It should be sorted before searching to allow for log n search time using binary search.
 */
public class AdjList extends BinaryList<AdjListElement> {
    @Override
    public String getKey(AdjListElement element) {
        return element.key;
    }
}