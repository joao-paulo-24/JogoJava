package ed_project.DataStructures;

import java.util.Iterator;

import ed_project.Exceptions.EmptyCollectionException;
import ed_project.Exceptions.NoSuchElementException;
import ed_project.Interfaces.ListADT;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class ArrayList<T> implements ListADT<T>, Iterable<T> {
    private final int DEFAULT_CAPACITY = 100;
    private final int ExpandBy = 2;
    private final int NotFound = -1;
    protected int rear;
    protected T[] list;

    protected int modCount;

    // Criar uma arrayList vazia com a capacidade default
    public ArrayList() {
        this.rear = 0;
        this.list = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    // Cria uma arrayList vazia com a capacidade inicial
    public ArrayList(int initialCapacity) {
        this.modCount = 0;
        this.rear = 0;
        this.list = (T[]) (new Object[initialCapacity]);

    }

    protected void expandCapacity() {
        T[] newList = (T[]) (new Object[list.length * ExpandBy]);

        System.arraycopy(this.list, 0, newList, 0, this.list.length);

        this.list = newList;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("list");
        } else {

            T result = this.list[0];

            for (int i = 0; i < this.rear; i++) {
                this.list[i] = this.list[i + 1];
            }

            this.list[rear] = null;
            --this.rear;
            this.modCount++;
            return result;
        }
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("list");
        } else {
            T result = list[rear];
            list[rear] = null;
            --this.rear;
            ++this.modCount;
            return result;
        }
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        int index = this.find(element);

        if (index == NotFound) {
            throw new EmptyCollectionException("list");
        }

        T result = this.list[index];
        this.rear--;

        // Shift nos elementos apropriados
        for (int scan = index; scan < this.rear; scan++) {
            this.list[scan] = this.list[scan + 1];
        }

        this.list[this.rear] = null;
        this.modCount++;
        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("list");

        return this.list[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("list");

        return this.list[this.rear - 1];
    }

    @Override
    public boolean contains(T target) {
        return (find(target) != NotFound);
    }

    @Override
    public boolean isEmpty() {
        return (rear == 0);
    }

    @Override
    public int size() {
        return rear;
    }

    private class customArrayIterator<E> implements Iterator<E> {
        private final int count;
        private int current;
        private final T[] items;

        public customArrayIterator(T[] collection, int size) {
            items = collection;
            count = size;
            current = 0;
        }

        public boolean hasNext() {
            return (current < count);
        }

        public E next() {
            if (!hasNext())
                try {
                    throw new NoSuchElementException("No next element");
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }

            current++;
            return (E) items[current - 1];

        }
    }

    @Override
    public Iterator<T> iterator() {

        return new customArrayIterator<T>(this.list, this.rear);

    }

    public int find(T target) {
        int position = 0, result = -1;
        boolean found = false;

        if (isEmpty()) {
            while (!found && position < rear) {
                if (target.equals(list[position])) {
                    found = true;
                } else {
                    position++;
                }
            }
        }

        if (found) {
            result = position;
        }

        return result;
    }

    public String toString() {
        String result = "";

        for (int scan = 0; scan < rear; scan++)
            result = result + list[scan].toString() + "\n";

        return result;
    }

}