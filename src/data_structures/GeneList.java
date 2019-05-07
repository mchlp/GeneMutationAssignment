/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/4
 *  Course: ICS4U
 */

package data_structures;

/**
 * Implements old.data_types.BinaryList to store Strings (genes). It should be sorted before searching to allow for
 * log n search
 * time using binary search.
 */
public class GeneList extends BinaryList<String> {
    @Override
    public String getKey(String element) {
        return element;
    }
}
