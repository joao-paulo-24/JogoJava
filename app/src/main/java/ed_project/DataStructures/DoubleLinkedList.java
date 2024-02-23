package ed_project.DataStructures;

import java.util.Iterator;

import ed_project.Exceptions.*;
import ed_project.Interfaces.ListADT;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class DoubleLinkedList<T> implements ListADT<T> {
    protected DoubleNode<T> head;
    protected DoubleNode<T> tail;
    protected int count;
    protected int modCount;

    public DoubleLinkedList() {
        this.head = this.tail = null;
        this.count = 0;
        this.modCount = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("empty list");
        }

        T value = this.head.getData();

        if (count == 1) {
            this.head = this.tail = null;

        } else {

            this.head = this.head.getNext();
            this.head.setPrevious(null);
        }
        this.count--;
        modCount++;

        return value;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        T value = this.tail.getData();

        if (count == 1) {
            this.head = this.tail = null;
        } else {
            this.tail = this.tail.getPrevious();
            this.tail.setNext(null);
        }
        this.count--;
        modCount++;

        return value;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        T value = null;
        boolean found = false;
        DoubleNode current = new DoubleNode();
        current = this.getHead();

        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        while (current != null && !found) {
            if (element.equals(current.getData())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        if (!found) {
            throw new NullPointerException("Elemento não existe");
        } else {
            if (size() == 1) {
                this.head = this.tail = null;
            } else {
                if (current.equals(this.head)) {
                    this.head = current.getNext();
                } else {
                    if (current.equals(this.tail)) {
                        this.tail = current.getPrevious();
                        this.tail.setNext(null);
                    } else {
                        current.getPrevious().setNext(current.getNext());
                        current.getNext().setPrevious(current.getPrevious());
                    }
                }
            }
        }
        this.count--;
        this.modCount++;
        return (T) current.getData();
    }

    @Override
    public T first() throws EmptyCollectionException {
        return this.head.getData();
    }

    @Override
    public T last() throws EmptyCollectionException {
        return this.tail.getData();
    }

    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        DoubleNode temp = new DoubleNode();
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        while (temp.getNext() != null) {
            if (temp.getData() == target) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (this.count == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.count;
    }

    private class customIterator<E> implements Iterator<E> {
        private DoubleNode current;
        private int expectedModCount;

        public customIterator() {
            this.current = head;
            this.expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            if (modCount != expectedModCount) {
                try {
                    throw new NoSuchElementException("mod count diff from expected");
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            }
            return (current != null);
        }

        @Override
        public E next() {
            E data = null;
            if (modCount != expectedModCount) {
                try {
                    throw new NoSuchElementException("mod count diff from expected");
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            }
            if (hasNext()) {
                data = (E) current.getData();
                current = current.getNext();
                return data;
            }
            return null;
        }

    }

    public static <T extends Comparable<? super T>> void selectionSort(DoubleLinkedList<T> x) {
        DoubleNode<T> current = x.getHead();
        DoubleNode<T> current1 = current.getNext();
        DoubleNode<T> min = null;
        while (current.getNext() != null) {
            min = current;
            while (current1.getNext() != null) {
                if (current.getData().compareTo(current1.getData()) > 0) {
                    min = current1;
                }

            }
            DoubleNode<T> temp = current;
            current.setData(min.getData());
            min.setData(temp.getData());
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new customIterator();
    }

    public DoubleNode<T> getHead() {
        return head;
    }

    public DoubleNode<T> getTail() {
        return tail;
    }

    public int getCount() {
        return count;
    }

    public int getModCount() {
        return modCount;
    }

    public void setHead(DoubleNode<T> head) {
        this.head = head;
    }

    public void setTail(DoubleNode<T> tail) {
        this.tail = tail;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setModCount(int modCount) {
        this.modCount = modCount;
    }
}
