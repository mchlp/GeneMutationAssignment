/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovich
 *  Date: 2019/5/6
 *  Course: ICS4U
 */

package data_types;

public interface QueueIntADT {
		public void enqueue(int num);	// Insert an number at the end of the queue.
		public int dequeue();			// Remove and return the first number of the queue, if the queue is not empty.
		public int peek(); 				// Return the first number of the queue without removing it, if the queue is not empty.
		public int size(); 				// Return the number of elements in the queue.
		public boolean isEmpty(); 		// Return true if the queue is empty, otherwise return false.
		public boolean isFull(); 		// Return true if the queue is full, otherwise return false
}