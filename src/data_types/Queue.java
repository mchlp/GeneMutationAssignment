/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/6
 *  Course: ICS4U
 */

package data_types;

public class Queue implements QueueIntADT {

    Element frontElement;
    Element backElement;

    @Override
    public void enqueue(int num) {
        Element newElement = new Element();
        newElement.value = num;

        if (frontElement != null) {
            newElement.next = frontElement;
            frontElement.before = newElement;
        }

        if (backElement == null) {
            backElement = newElement;
        }
        frontElement = newElement;
    }

    @Override
    public int dequeue() {
        Element popElement = backElement;
        backElement = backElement.before;
        return popElement.value;
    }

    @Override
    public int peek() {
        return backElement.value;
    }

    @Override
    public int size() {
        if (backElement == null) {
            return 0;
        } else {
            int sizeCount = 1;
            Element cur = backElement;
            while (cur.before != null) {
                cur = cur.before;
                sizeCount++;
            }
            return sizeCount;
        }
    }

    @Override
    public boolean isEmpty() {
        return backElement == null;
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
