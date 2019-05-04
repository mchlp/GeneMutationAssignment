/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/3
 *  Course: ICS4U
 */

/**
 * Implements BinaryList to store Strings.
 */
public class GeneList extends BinaryList<String> {
    @Override
    public int compare(String ele1, String ele2) {
        return ele1.compareTo(ele2);
    }
}
