/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package old.data_structures;

/**
 * Represents a distance array in BFS. It should be sorted before searching to allow for log n search time using
 * binary search.
 */
public class DisList extends BinaryList<DisListElement> {
    @Override
    public String getKey(DisListElement element) {
        return element.key;
    }
}
