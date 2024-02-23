package ed_project.DataStructures;

import ed_project.Exceptions.EmptyCollectionException;
import ed_project.Interfaces.QueueADT;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class LinkedQueue<T> implements QueueADT<T>{
    private int count = 0;
    private LinearNode<T> front = null;
    private LinearNode<T> rear = null;

    public LinkedQueue() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    public void enqueue(T element) {
        LinearNode<T> node = new LinearNode<T>(element);

        if (this.isEmpty()) {
            this.front = node;
            this.rear = node;
        } else {
            this.rear.setNext(node);
            this.rear = node;
        }

        ++this.count;

    }

    public T dequeue() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("LinkedQueue");
        } else {
            T result = this.front.getElement();
            this.front = this.front.getNext();
            --this.count;
            return result;
        }
    }

    public T first() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("LinkedQueue");
        } else {
            return this.front.getElement();
        }
    }

    public boolean isEmpty() {
        return (this.count == 0);
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        String result = "LinkedQueue {";
        LinearNode<T> current = this.front;
        if (!this.isEmpty()) {
            do {
                result = result + " " + current.getElement();
            } while ((current = current.getNext()) != null);
        }

        result = result + " }";
        return result;
    }

    public LinearNode<T> getFront() {
        return front;
    }

    public LinearNode<T> getRear() {
        return rear;
    }
}
